package com.china.jwb.openplatform.weixin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.china.jwb.common.tools.CommonUtil;
import com.china.jwb.common.tools.JsonUtil;
import com.china.jwb.common.tools.OdpConstants;
import com.china.jwb.common.tools.PublishUtil;
import com.china.jwb.common.tools.StringUtils;
import com.china.jwb.common.tools.YsHttpUtil;
import com.china.jwb.openplatform.entity.Article;
import com.china.jwb.openplatform.entity.TaskInfo;


/**
 * 微信发布方法
 * @author jiawenbo
 *
 */
public class UploadWX {
	private static final Logger logger = Logger.getLogger(UploadWX.class);
	//获取token的链接
	public static final String access_token_url = OdpConstants.access_token_url_weixin.trim();
	// 调用上传多媒体接口.（视频、语音、图片、缩略图）
	public static String post_upload = OdpConstants.post_upload_weixin.trim();
	//获取真实的medailid接口
	public static String post_upload_media_id = OdpConstants.post_distribute_media_id_weixin.trim();
	//最终发送
	public static String post_group_send_weixin = OdpConstants.post_group_send_weixin.trim();
	//最终发送——分组群发
	public static String post_group_send_weixin_group = OdpConstants.post_group_send_weixin_group.trim();
	//上传图文消息素材--临时素材。不在微信后台显示
	public static String  post_pic_text = OdpConstants.post_pic_text_weixin.trim();
	//上传图文消息素材--永久素材.
	public static String  post_pic_text_yj = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=ACCESS_TOKEN";
	
	//上传内容里的图片.(不占用素材库5000个容量的限制)
	public static String  post_content_img = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";
	//上传内容里的视频、图片音频.(永久)
	public static String  post_content_forever = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=TYPE";
	//查询素材
	public static String  get_material = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";
	public static String  get_material_list = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
	
	
	/**
	 * 发布接口
	 * @param taskInfo
	 * @return
	 */
	public static boolean process(TaskInfo taskInfo){
		boolean sendFlag = false;
		try {
			/**
			 * 1、获取TaskInfo中的数据。
			 *   根据公共平台的用户名和密码，取得 access_token。（每次分发获取新的access_token即可，因为群发次数只能一次。）
			 **/
			Map<String ,Object> tokenMap=new HashMap<String, Object>();
			tokenMap.put("grant_type", "client_credential");
			tokenMap.put("appid", taskInfo.getAppId());
			tokenMap.put("secret", taskInfo.getAppSecret());
			tokenMap = getAndSetToken(taskInfo,tokenMap);//token
	        taskInfo.setAccessToken(tokenMap.get("access_token").toString());
			/**
			 * 2、循环 上传图片缩略图（指传送过来的图片，外显图）
			 *   
			 **/
	        //Article[] articles = uploadArticle(taskInfo);
	        Article[] articles = uploadArticle_YJ(taskInfo);//永久素材要用到的永久Media_ID
	        //设置content中内容
	        articles = setContent(articles,taskInfo);
			/** 获取列表
			 * 3、根据access_token获取该平台的所有关注着的open_Id。(此openId是针对该平台的唯一的一个，分配给微信用户的一个不同于微信号的id号)。
			 **/
	        
	        JSONArray jsonMainArr = null;
			/** 
			 * 4上传图文
			 **/
	        //String mediaId =  uploadPicAndTextMediaId(taskInfo, articles);
	        String mediaId =  uploadPicAndTextMediaId_YJ(taskInfo, articles);//永久素材
	        
	       
	        /***
	         * 预览(限制100次)调试用预览
	         */
	        //sendFlag =  previewMsg(taskInfo, mediaId);
	        
	        /** 
			 * 5群发(限制每月4次，慎用)
			 **/
	        //5.1按关注人发送。无法添加到历史列表里。
	        /**
	        jsonMainArr = getSendUsers(tokenMap);
	        sendFlag =  sendMsg(taskInfo, mediaId, jsonMainArr);
	        */
	        //5.2按分组发送。无组则是全部发送//分组的无预览接口。但是设置is_to_all为false.不使用4此限制。但是每个用户仍然最多收4条.
	        //sendFlag = sendMsgGroup(taskInfo, mediaId);
	        /*不发送，只是上传到后台永久素材
	        if(StringUtils.isNotNull(taskInfo.getCriCmsGroupId())){
	        	sendFlag = sendMsgGroup(taskInfo, mediaId);
	        }else{
	        	//sendFlag = sendMsgGroup(taskInfo, mediaId);
	        }
	        */
	        if(mediaId!=null&&!"".equals(mediaId)){
	        	sendFlag = true;
	        }else{
	        	sendFlag = false;
	        }
		} catch (Exception e) {
			sendFlag = false;
		}
        return sendFlag;
	}
	
	/**
	 * 预览接口，提供预览功能
	 * @param taskInfo
	 * @return
	 */
	public static boolean preView(TaskInfo taskInfo){
		boolean sendFlag = false;
		try {
			/**
			 * 1、获取TaskInfo中的数据。
			 *   根据公共平台的用户名和密码，取得 access_token。（每次分发获取新的access_token即可，因为群发次数只能一次。）
			 **/
			Map<String ,Object> tokenMap=new HashMap<String, Object>();
			tokenMap.put("grant_type", "client_credential");
			tokenMap.put("appid", taskInfo.getAppId());
			tokenMap.put("secret", taskInfo.getAppSecret());
			tokenMap = getAndSetToken(taskInfo,tokenMap);//token
	        taskInfo.setAccessToken(tokenMap.get("access_token").toString());
			/**
			 * 2、循环 上传图片缩略图（指传送过来的图片，外显图）
			 *   
			 **/
	        Article[] articles = uploadArticle(taskInfo);
	        //Article[] articles = uploadArticle_YJ(taskInfo);//永久素材要用到的永久Media_ID
	        //设置content中内容
	        articles = setContent(articles,taskInfo);
			/** 获取列表
			 * 3、根据access_token获取该平台的所有关注着的open_Id。(此openId是针对该平台的唯一的一个，分配给微信用户的一个不同于微信号的id号)。
			 **/
	        //JSONArray jsonMainArr = getSendUsers(tokenMap);
			/** 
			 * 4上传图文
			 **/
	        String mediaId =  uploadPicAndTextMediaId(taskInfo, articles);
	        //String mediaId =  uploadPicAndTextMediaId_YJ(taskInfo, articles);//永久素材
	        
	        /***
	         * 预览(限制100次)
	         */
	        sendFlag =  previewMsg(taskInfo, mediaId);
		} catch (Exception e) {
			 sendFlag = false;
		}
        return sendFlag;
	}
	
	/***
	 * 获取token
	 * @param taskInfo
	 * @return
	 */
	public static Map<String ,Object>  getAndSetToken(TaskInfo taskInfo,Map<String ,Object> tokenMap){
        String resToken= YsHttpUtil.doGet(access_token_url,tokenMap);
        tokenMap=JsonUtil.readJSON2Map(resToken);
        if(tokenMap.get("access_token")==null){
        	logger.info("获取微信token失败");
        }
        return tokenMap;
	}
	
	/**
	 * 上传素材
	 * @param taskInfo
	 * @return
	 */
	public static Article[] uploadArticle(TaskInfo taskInfo){
		Article[] articles =null;
        articles = taskInfo.getArticle();
		//循环图片地址,进行上传.
		logger.info("articele"+taskInfo.getArticle());
		for(int i=0;i<taskInfo.getArticle().length;i++){
			//将图片下载到本地 .并获得本地地址.
			String picHttpUrl = articles[i].getThurmbMediaUrl();
			String media_id = uploadMedia(picHttpUrl, taskInfo);
			articles[i].setThurmbMediaId(media_id);
		}
		return articles;
	}
	/**
	 * 上传素材_YJ
	 * @param taskInfo
	 * @return
	 */
	public static Article[] uploadArticle_YJ(TaskInfo taskInfo){
		Article[] articles =null;
        articles = taskInfo.getArticle();
		//循环图片地址,进行上传.
		logger.info("articele"+taskInfo.getArticle());
		for(int i=0;i<taskInfo.getArticle().length;i++){
			//将图片下载到本地 .并获得本地地址.
			String picHttpUrl = articles[i].getThurmbMediaUrl();
			String media_id = uploadMedia_YJ(picHttpUrl, taskInfo);
			articles[i].setThurmbMediaId(media_id);
		}
		return articles;
	}
	/**
	 * 修改Article中的content内容
	 * 1、去掉微信不识别的样式
	 * 2、如果有图、视频、音频等上传然后替换
	 * （必须是微信下的路径才可用，否则屏蔽）
	 * @param articles
	 * @param taskInfo
	 * @return
	 */
	public static Article[] setContent(Article[] articles,TaskInfo taskInfo){
		//循环图片地址,进行上传.
		for(int i=0;i<articles.length;i++){
			Article article = articles[i];
			String  content = article.getContent();
			Document doc = Jsoup.parse(content);
			//去除P标签样式
			doc.removeAttr("style");
			//图片元素
			Elements element_IMG=doc.getElementsByTag("img");
			if(element_IMG!=null &&element_IMG.size()>0){
				for(int img=0;img<element_IMG.size();img++){
					logger.info("图片");
					Element element = element_IMG.get(img);
					String srcUrl  = element.attr("src");				//图片路径
					//String imgUrl = uploadContentImg(srcUrl, taskInfo);	//上传微信获取URL
					String imgUrl = uploadContentImg_YJ(srcUrl, taskInfo);	//上传微信获取URL_永久
					if("".equals(imgUrl)){
						element.remove();
					}else{
						//替换路径
						element.attr("src", imgUrl);
						//加样式不如不加呢
						element.removeAttr("width");
						element.removeAttr("id");
						element.removeAttr("class");
						element.removeAttr("name");
					}
				}
			}
			
			//音视频元素
			logger.info("音视频");
			Elements element_object=doc.getElementsByTag("object");
			if(element_object!=null &&element_object.size()>0){
				for(int obj=0;obj<element_object.size();obj++){
					Element element = element_object.get(obj);
					//element.remove();
					String className = element.attr("class");
					if(className.contains(PublishUtil.CLASS_NAME_video)){
						String resURl = "";
						String startHtml = "mediaURL=";
						String endHtml = "\"";
						String oneObj = element.toString();
						int beginIndex = oneObj.indexOf(startHtml);
						if(beginIndex>0){
							beginIndex = beginIndex+startHtml.length();
							oneObj = oneObj.substring(beginIndex);
							int endIndex = oneObj.indexOf(endHtml);
							resURl = oneObj.substring(0, endIndex);
							logger.info("视频地址=="+resURl);
						}
						if(!"".equals(resURl)){
							String media_id = uploadContentVideo_YJ(resURl, taskInfo);	//上传微信获取URL_永久
							if(!"".equals(media_id)){
								String newElementStr = "";
								
//								String material = getMaterial(media_id, taskInfo);
//								logger.info("视频material="+material);
//								com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(material);
//								String down_url = (String) json.get("down_url");
//								logger.info("视频down_url="+down_url);
								
//								newElementStr ="<video width=\"320\" height=\"240\" controls=\"controls\" autoplay=\"autoplay\">"+
//								"<source src=\""+down_url+"\" type=\"video/mp4\" />"+
//								"</video>";
								element.before(newElementStr);
								element.remove();
							}else{
								element.remove();
							}
						}else{
							element.remove();
						}
					}else if(className.contains(PublishUtil.CLASS_NAME_audio)){
						String resURl = "";
						String startHtml = "mediaURL=";
						String endHtml = "\"";
						String oneObj = element.toString();
						int beginIndex = oneObj.indexOf(startHtml);
						if(beginIndex>0){
							beginIndex = beginIndex+startHtml.length();
							oneObj = oneObj.substring(beginIndex);
							int endIndex = oneObj.indexOf(endHtml);
							resURl = oneObj.substring(0, endIndex);
							logger.info("音频地址==="+resURl);
						}
						if(!"".equals(resURl)){
							String media_id = uploadContentVoice_YJ(resURl, taskInfo);	//上传微信获取URL_永久
							if(!"".equals(media_id)){
								//String material = getMaterial(media_id, taskInfo);
								//logger.info("音频material="+material);
								String newElementStr =""; 
								element.before(newElementStr);
								element.remove();
							}else{
								element.remove();
							}
						}else{
							element.remove();
						}
					}
					
				}
			}
			content = doc.toString();
			articles[i].setContent(content);
		}
		return articles;
	}
	
	/***
	 * 获取接受人员
	 * @param tokenMap
	 * @return
	 */
	public static JSONArray  getSendUsers(Map<String ,Object> tokenMap){
		JSONArray jsonMainArr=null;
		String userOpenId= YsHttpUtil.doGet("https://api.weixin.qq.com/cgi-bin/user/get",tokenMap);
	    tokenMap=JsonUtil.readJSON2Map(userOpenId);
	    if(tokenMap.get("data")==null){
        	logger.info("获取微信用户列表失败,返回值"+tokenMap);
        	//return false;
	    }
	    JSONObject userJsion=null;
		try {
			userJsion = new JSONObject(userOpenId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	 	
		try {
			jsonMainArr = userJsion.getJSONObject("data").getJSONArray("openid");
		} catch (JSONException e) {
			logger.info("转换失败");
			//return false;
		}
		logger.info("总共有[" + jsonMainArr.length() + "]个用户，当前获取数[" + jsonMainArr.length() + "]");
		return jsonMainArr;
	}
	
	
	/**
	 * 上传图文素材获取ID
	 * @param taskInfo
	 * @param articles
	 * @return
	 */
	public static String  uploadPicAndTextMediaId(TaskInfo taskInfo,Article[] articles){
//		String jsonPic=CommonUtil.buildPicTextUploadJson(articles);
		com.alibaba.fastjson.JSONObject json = getMediaJson(articles);
		String jsonPic = json.toString();
		logger.info("最终上传图文素材的内容=="+jsonPic);
		String urlPic = post_pic_text.replace("ACCESS_TOKEN", taskInfo.getAccessToken());
		String getMedialId = YsHttpUtil.getMaterialMediaId(urlPic,  jsonPic);
		Map<String ,Object> tokenMap=new HashMap<String, Object>();
		tokenMap=JsonUtil.readJSON2Map(getMedialId);
		if(tokenMap.get("media_id")==null){
			logger.info("微信图文获取media_id失败返回，参数"+getMedialId);
			//return false;
		}
		return tokenMap.get("media_id").toString();
	}
	
	/**
	 * 上传图文素材获取ID_永久
	 * @param taskInfo
	 * @param articles
	 * @return
	 */
	public static String  uploadPicAndTextMediaId_YJ(TaskInfo taskInfo,Article[] articles){
//		String jsonPic=CommonUtil.buildPicTextUploadJson(articles);
		com.alibaba.fastjson.JSONObject json = getMediaJson(articles);
		String jsonPic = json.toString();
		logger.info("最终上传图文素材的内容=="+jsonPic);
		//String urlPic = post_pic_text.replace("ACCESS_TOKEN", taskInfo.getAccessToken());
		String urlPic = post_pic_text_yj.replace("ACCESS_TOKEN", taskInfo.getAccessToken());
		String getMedialId = YsHttpUtil.getMaterialMediaId(urlPic,  jsonPic);
		Map<String ,Object> tokenMap=new HashMap<String, Object>();
		tokenMap=JsonUtil.readJSON2Map(getMedialId);
		if(tokenMap.get("media_id")==null){
			logger.info("微信图文获取media_id失败返回，参数"+getMedialId);
			//return false;
		}
		return tokenMap.get("media_id").toString();
	}
	/**
	 * 发送
	 * @param taskInfo
	 * @param mediaId
	 * @param jsonMainArr
	 * @return
	 */
	public static boolean sendMsg(TaskInfo taskInfo,String mediaId,JSONArray jsonMainArr){
		Map<String,Object>   openidMap=new HashMap<String, Object>();
		String rstJsonUrl = post_group_send_weixin.replace("ACCESS_TOKEN", taskInfo.getAccessToken());
		String getjson= CommonUtil.buildPicTextDistributeJson(mediaId,jsonMainArr);
		String openidMapjson=null;
		//发送
	    openidMapjson = YsHttpUtil.getMaterialMediaId(rstJsonUrl,  getjson);
		openidMap=JsonUtil.readJSON2Map(openidMapjson);
        if(openidMap.get("errcode")==null){
        	logger.info("最终发送失败 返回值 ："+openidMap);
        	return false;
        }
        if(!openidMap.get("errcode").toString().equals("0")){
        	logger.info("最终发送失败 返回值 ："+openidMap);
        	return false;
        }
		return true;
	}
	/**
	 * 预览
	 * @param taskInfo
	 * @param mediaId
	 * @param jsonMainArr
	 * @return
	 */
	public static boolean previewMsg(TaskInfo taskInfo,String mediaId){
		Map<String,Object>   openidMap=new HashMap<String, Object>();
		String priviewUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN";
		String rstJsonUrl = priviewUrl.replace("ACCESS_TOKEN", taskInfo.getAccessToken());
		com.alibaba.fastjson.JSONObject mediaJson =  new com.alibaba.fastjson.JSONObject();
		mediaJson.put("media_id", mediaId);
		com.alibaba.fastjson.JSONObject json =  new com.alibaba.fastjson.JSONObject();
		json.put("towxname", taskInfo.getWeixinAccountId());
		json.put("mpnews", mediaJson);
		json.put("msgtype", "mpnews");
		String openidMapjson=null;
		//发送
	    openidMapjson = YsHttpUtil.getMaterialMediaId(rstJsonUrl,  json.toString());
		openidMap=JsonUtil.readJSON2Map(openidMapjson);
        if(openidMap.get("errcode")==null){
        	logger.info("最终发送失败 返回值 ："+openidMap);
        	return false;
        }
        if(!openidMap.get("errcode").toString().equals("0")){
        	logger.info("最终发送失败 返回值 ："+openidMap);
        	return false;
        }
		return true;
	}
	/**
	 * 发送_分组群发,无组则是全部
	 * @param taskInfo
	 * @param mediaId
	 * @param jsonMainArr
	 * @return
	 */
	public static boolean sendMsgGroup(TaskInfo taskInfo,String mediaId){
		Map<String,Object>   openidMap=new HashMap<String, Object>();
		String rstJsonUrl = post_group_send_weixin_group.replace("ACCESS_TOKEN", taskInfo.getAccessToken());
		com.alibaba.fastjson.JSONObject mediaJson =  new com.alibaba.fastjson.JSONObject();
		mediaJson.put("media_id", mediaId);
		com.alibaba.fastjson.JSONObject filterJson =  new com.alibaba.fastjson.JSONObject();
		if(StringUtils.isNotNull(taskInfo.getCriCmsGroupId())){
			filterJson.put("is_to_all", false);//设置is_to_all为false时是可以多次群发的，但每个用户只会收到最多4条，且这些群发不会进入历史消息列表。如果为true则是全部人员。groupid不生效
			filterJson.put("group_id", taskInfo.getCriCmsGroupId());
        }else{
        	filterJson.put("is_to_all", true);
        }
		com.alibaba.fastjson.JSONObject json =  new com.alibaba.fastjson.JSONObject();
		json.put("filter", filterJson);
		json.put("mpnews", mediaJson);
		json.put("msgtype", "mpnews");
		String openidMapjson=null;
		//发送分组
	    openidMapjson = YsHttpUtil.getMaterialMediaId(rstJsonUrl,  json.toString());
		openidMap=JsonUtil.readJSON2Map(openidMapjson);
        if(openidMap.get("errcode")==null){
        	logger.info("最终发送失败 返回值 ："+openidMap);
        	return false;
        }
        if(!openidMap.get("errcode").toString().equals("0")){
        	logger.info("最终发送失败 返回值 ："+openidMap);
        	return false;
        }
		return true;
	}
	
	/***
	 * 上传素材到微信获取media_id
	 * @param localUrl
	 * @param taskInfo
	 * @return
	 */
	public static String uploadMedia(String imgUrl,TaskInfo taskInfo){
		//将图片下载到本地 .并获得本地地址.
		String picHttpUrl = imgUrl;
		//如果不是http开头则是系统默认图片，添加域名
		if(!picHttpUrl.startsWith("http://")){
			picHttpUrl = "myapp.com:8080/Test/" + picHttpUrl;
		}
		taskInfo.setResUrl(picHttpUrl);
		String fileSource= CommonUtil.uploudUrl(taskInfo);
    	if(fileSource==null){
    	   logger.info("下载原因！！分发微信图文失败");
    	}
        String url = post_upload.replace("ACCESS_TOKEN", taskInfo.getAccessToken()).replace("TYPE", OdpConstants.TYPE_MATRIAL_THUMB);
        String resJson=YsHttpUtil.doPostOne(url,"media",fileSource);
        System.out.println(url);
        Map<String ,Object> thumbMap=JsonUtil.readJSON2Map(resJson);
		if(thumbMap.get("media_id")==null){
			logger.info("上传缩略图失败,返回的参数"+thumbMap);
		}
		return thumbMap.get("media_id").toString();
	}
	/***
	 * 上传素材到微信获取media_id
	 * 永久
	 * @param localUrl
	 * @param taskInfo
	 * @return
	 */
	public static String uploadMedia_YJ(String imgUrl,TaskInfo taskInfo){
		//将图片下载到本地 .并获得本地地址.
		String picHttpUrl = imgUrl;
		//如果不是http开头则是系统默认图片，添加域名
		if(!picHttpUrl.startsWith("http://")){
			picHttpUrl = "myapp.com:8080/Test/" + picHttpUrl;
		}
		taskInfo.setResUrl(picHttpUrl);
		String fileSource= CommonUtil.uploudUrl(taskInfo);
    	if(fileSource==null){
    	   logger.info("下载原因！！分发微信图文失败");
    	}
        //String url = post_upload.replace("ACCESS_TOKEN", taskInfo.getAccessToken()).replace("TYPE", OdpConstants.TYPE_MATRIAL_THUMB);
        String url = post_content_forever.replace("ACCESS_TOKEN", taskInfo.getAccessToken()).replace("TYPE", OdpConstants.TYPE_MATRIAL_THUMB);
        String resJson=YsHttpUtil.doPostOne(url,"media",fileSource);
        System.out.println(url);
        Map<String ,Object> thumbMap=JsonUtil.readJSON2Map(resJson);
		if(thumbMap.get("media_id")==null){
			logger.info("上传缩略图失败,返回的参数"+thumbMap);
		}
		return thumbMap.get("media_id").toString();
	}
	/***
	 * 上传素材到微信获取media_id
	 * 永久
	 * @param localUrl
	 * @param taskInfo
	 * @return
	 */
	public static String uploadContentImg_YJ(String imgUrl,TaskInfo taskInfo){
		//将图片下载到本地 .并获得本地地址.
		String picHttpUrl = imgUrl;
		//如果不是http开头则是系统默认图片，添加域名
		if(!picHttpUrl.startsWith("http://")){
			picHttpUrl = "myapp.com:8080/Test/" + picHttpUrl;
		}
		taskInfo.setResUrl(picHttpUrl);
		String fileSource= CommonUtil.uploudUrl(taskInfo);
		if(fileSource==null){
			logger.info("下载原因！！分发微信图文失败");
		}
		//String url = post_upload.replace("ACCESS_TOKEN", taskInfo.getAccessToken()).replace("TYPE", OdpConstants.TYPE_MATRIAL_THUMB);
		String url = post_content_forever.replace("ACCESS_TOKEN", taskInfo.getAccessToken()).replace("TYPE", OdpConstants.TYPE_MATRIAL_THUMB);
		String resJson=YsHttpUtil.doPostOne(url,"media",fileSource);
		System.out.println(url);
		Map<String ,Object> thumbMap=JsonUtil.readJSON2Map(resJson);
		if(thumbMap.get("url")==null){
			logger.info("文件内容中图片上传失败,返回的参数"+thumbMap);
		}
		return thumbMap.get("url").toString();
	}
	/***
	 * 上传内容中的图片
	 * @param localUrl
	 * @param taskInfo
	 * @return
	 */
	public static String uploadContentImg(String imgUrl,TaskInfo taskInfo){
		//将图片下载到本地 .并获得本地地址.
		String returnUrl = "";
		String picHttpUrl = imgUrl;
		//如果不是http开头则是系统默认图片，添加域名
		if(!picHttpUrl.startsWith("http://")){
			picHttpUrl = "myapp.com:8080/Test/" + picHttpUrl;
		}
		taskInfo.setResUrl(picHttpUrl);
		String fileSource= CommonUtil.uploudUrl(taskInfo);
    	if(fileSource==null){
    	   logger.info("下载原因！！分发微信图文失败");
    	}
        String url = post_content_img.replace("ACCESS_TOKEN", taskInfo.getAccessToken());
        String resJson=YsHttpUtil.doPostOne(url,"media",fileSource);
        System.out.println(url);
        Map<String ,Object> thumbMap=JsonUtil.readJSON2Map(resJson);
		if(thumbMap.get("url")==null){
			logger.info("文件内容中图片上传失败,返回的参数"+thumbMap);
			returnUrl = "";
		}else{
			returnUrl = thumbMap.get("url").toString();
		}
		return returnUrl;
	}
	/***
	 * 上传内容中的视频
	 * @param localUrl
	 * @param taskInfo
	 * @return
	 */
	public static String uploadContentVideo_YJ(String imgUrl,TaskInfo taskInfo){
		//将图片下载到本地 .并获得本地地址.
		String media_id = "";
		String picHttpUrl = imgUrl;
		//如果不是http开头则是系统默认图片，添加域名
		if(!picHttpUrl.startsWith("http://")){
			picHttpUrl = "myapp.com:8080/Test/" + picHttpUrl;
		}
		taskInfo.setResUrl(picHttpUrl);
		String fileSource= CommonUtil.uploudUrl(taskInfo);
    	if(fileSource==null){
    	   logger.info("下载原因！！分发微信图文失败");
    	}
    	String url = post_content_forever.replace("ACCESS_TOKEN", taskInfo.getAccessToken()).replace("TYPE", OdpConstants.TYPE_MATRIAL);
    	String resJson=postFile(url, fileSource, taskInfo.getResName(), taskInfo.getResRemark());//微信上传视频
        System.out.println(url);
        Map<String ,Object> thumbMap=JsonUtil.readJSON2Map(resJson);
		if(thumbMap.get("media_id")==null){
			logger.info("文件内容中图片上传失败,返回的参数"+thumbMap);
			media_id = "";
		}else{
			media_id = thumbMap.get("media_id").toString();
		}
		return media_id;
	}
	/***
	 * 上传内容中的音频
	 * @param localUrl
	 * @param taskInfo
	 * @return
	 */
	public static String uploadContentVoice_YJ(String imgUrl,TaskInfo taskInfo){
		//将图片下载到本地 .并获得本地地址.
		String media_id = "";
		String picHttpUrl = imgUrl;
		//如果不是http开头则是系统默认图片，添加域名
		if(!picHttpUrl.startsWith("http://")){
			picHttpUrl = "myapp.com:8080/Test/" + picHttpUrl;
		}
		taskInfo.setResUrl(picHttpUrl);
		String fileSource= CommonUtil.uploudUrl(taskInfo);
    	if(fileSource==null){
    	   logger.info("下载原因！！分发微信图文失败");
    	}
    	String url = post_content_forever.replace("ACCESS_TOKEN", taskInfo.getAccessToken()).replace("TYPE", OdpConstants.TYPE_MATRIAL_VOICE);
        String resJson=YsHttpUtil.doPostOne(url,"media",fileSource);
        System.out.println(url);
        Map<String ,Object> thumbMap=JsonUtil.readJSON2Map(resJson);
		if(thumbMap.get("media_id")==null){
			logger.info("文件内容中图片上传失败,返回的参数"+thumbMap);
			media_id = "";
		}else{
			media_id = thumbMap.get("media_id").toString();
		}
		return media_id;
	}
	
	/***
	 * 获取图文（素材）JSON数据
	 * @param articles
	 * @return
	 */
	public static com.alibaba.fastjson.JSONObject getMediaJson(Article[] articles){
		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
		com.alibaba.fastjson.JSONArray articleArray =  new com.alibaba.fastjson.JSONArray();
		for (int i = 0; i < articles.length; i++) {
			Article article = articles[i];
			com.alibaba.fastjson.JSONObject itemJson = new com.alibaba.fastjson.JSONObject();
			if(i==articles.length-1){
				itemJson.put("thumb_media_id", article.getThurmbMediaId());
				if(articles[i].getAuthor()!=null&&!"".equals(articles[i].getAuthor())){
					itemJson.put("author", article.getAuthor());
				}
				itemJson.put("title", article.getTitle());
				if(articles[i].getContentSourceUrl()!=null&&!"".equals(articles[i].getContentSourceUrl())){
					itemJson.put("content_source_url", article.getContentSourceUrl());
				}
				itemJson.put("content", article.getContent());
				itemJson.put("digest", article.getDigest());
				itemJson.put("show_cover_pic", article.getShowCoverPic());
				
			}else{
				
				itemJson.put("thumb_media_id", article.getThurmbMediaId());
				itemJson.put("author", article.getAuthor());
				itemJson.put("title", article.getTitle());
				if(articles[i].getContentSourceUrl()!=null&&!"".equals(articles[i].getContentSourceUrl())){
					itemJson.put("content_source_url", article.getContentSourceUrl());
				}
				itemJson.put("content", article.getContent());
				itemJson.put("digest", article.getDigest());
				itemJson.put("show_cover_pic", article.getShowCoverPic());
				
			}
			articleArray.add(itemJson);
		}
		json.put("articles", articleArray);
		return json;
	}
	
	/**
	 * 上传微信视频专用
	 * 注意：：：：2016年1月11日18:08:38 经确认微信视频API有问题，上传视频到微信后台后,视频一直【转码中】
	 * @param url
	 * @param filePath
	 * @param title
	 * @param introduction
	 * @return
	 */
	public static String postFile(String url, String filePath, String title,String introduction) {
		File file = new File(filePath);
		if (!file.exists())
			return null;
		String result = null;
		try {
			URL url1 = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(30000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
			conn.setRequestProperty("Cache-Control", "max-age=0");
			String boundary = "-----------------------------"+ System.currentTimeMillis();
			conn.setRequestProperty("Content-Type","multipart/form-data; boundary=" + boundary);

			OutputStream output = conn.getOutputStream();
			output.write(("--" + boundary + "\r\n").getBytes());
			//output.write(String.format("Content-Disposition: form-data; name=\"media\"; filename=\"%s\"\r\n",file.getName()).getBytes());
			output.write(String.format("Content-Disposition: form-data; name=\"media\"; filename=\"%s\"; filelength=\"%s\"\r\n",file.getName(),file.length()).getBytes());
			output.write("Content-Type: video/mp4 \r\n\r\n".getBytes());
			byte[] data = new byte[1024];
			int len = 0;
			FileInputStream input = new FileInputStream(file);
			while ((len = input.read(data)) > -1) {
				output.write(data, 0, len);
			}
			output.write(("--" + boundary + "\r\n").getBytes());
			output.write("Content-Disposition: form-data; name=\"description\";\r\n\r\n".getBytes());
			output.write(String.format("{\"title\":\"%s\", \"introduction\":\"%s\"}", title,introduction).getBytes());
			output.write(("\r\n--" + boundary + "--\r\n\r\n").getBytes());
			output.flush();
			output.close();
			input.close();
			InputStream resp = conn.getInputStream();
			StringBuffer sb = new StringBuffer();
			while ((len = resp.read(data)) > -1)
				sb.append(new String(data, 0, len, "utf-8"));
			resp.close();
			result = sb.toString();
			System.out.println(result);
		} catch (ClientProtocolException e) {
			logger.error("postFile，不支持http协议", e);
		} catch (IOException e) {
			logger.error("postFile数据传输失败", e);
		}
		logger.info("result="+result);
		return result;
	}
	
	/**
	 * 获取素材信息
	 * @param media_id
	 * @param taskInfo
	 * @return
	 */
	public static String getMaterial(String media_id,TaskInfo taskInfo){
		String url = get_material.replace("ACCESS_TOKEN", taskInfo.getAccessToken());
		logger.info("url="+url);
		com.alibaba.fastjson.JSONObject json =  new com.alibaba.fastjson.JSONObject();
		json.put("media_id", media_id);
		String materialStr = YsHttpUtil.getMaterialMediaId(url, json.toString());
		return materialStr;
	}
	
	/**
	 * 获取素材列表
	 * 
	 * 参数为json字符串不是单个的提交 用  YsHttpUtil.getMaterialMediaId
	 * @param taskInfo
	 * @param type			图片（image）、视频（video）、语音 （voice）、图文（news）
	 * @param offset		偏移量
	 * @param count			总数（20内）
	 * @return
	 */
	public static String getMaterialList(TaskInfo taskInfo,String type,int offset,int count){
		String url = get_material_list.replace("ACCESS_TOKEN", taskInfo.getAccessToken());
		com.alibaba.fastjson.JSONObject json =  new com.alibaba.fastjson.JSONObject();
		json.put("type", type);
		json.put("offset", offset);
		json.put("count", count);
		String materialStr= YsHttpUtil.getMaterialMediaId(url, json.toString());
		return materialStr;
	}
	/**
	 * 获取素材列表
	 * @param taskInfo
	 * @param type			图片（image）、视频（video）、语音 （voice）、图文（news）
	 * @param offset		偏移量
	 * @param count			总数（20内）
	 * @return
	 */
	public static String getMaterialCount(TaskInfo taskInfo){
		String url = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=ACCESS_TOKEN".replace("ACCESS_TOKEN", taskInfo.getAccessToken());
		Map<String ,Object> tokenMap=new HashMap<String, Object>();
		String materialStr = YsHttpUtil.doPost(url, tokenMap);
		return materialStr;
	}
	
	/**
	 * 获取token
	 * @param appid
	 * @param appsecret
	 * @return
	 */
	public static String getAccessToken(String appid,String appsecret){
		String token  = "";
		Map<String ,Object> tokenMap=new HashMap<String, Object>();
		tokenMap.put("grant_type", "client_credential");
		tokenMap.put("appid", appid);
		tokenMap.put("secret", appsecret);
		String resToken= YsHttpUtil.doGet(access_token_url,tokenMap);
		com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(resToken);
		token = json.get("access_token").toString();
		logger.info("token=="+token);
        return token;
	}
	/**
	 * 初始化一个数据
	 * @return
	 */
	public static TaskInfo createTaskInfo(){
		String appid = "wxedae922cdca2cbe6";
		String appSecret ="d6eaae59d8f697932e29c92a899d2840";
		TaskInfo taskInfo = new TaskInfo();
		taskInfo.setAppId(appid);
		taskInfo.setAppSecret(appSecret);
		taskInfo.setAccessToken(getAccessToken(appid, appSecret));
		return taskInfo;
	}
	/**
	 * 打印素材list
	 * @param taskInfo
	 */
	public static void printList(TaskInfo taskInfo){
		String getMaterialCount = getMaterialCount(taskInfo);
		System.out.println("getMaterialCount="+getMaterialCount);
		String materialListimage = getMaterialList(taskInfo, "image", 0, 20);
		System.out.println("materialListimage="+materialListimage);
		String materialListvideo = getMaterialList(taskInfo, "video", 0, 20);
		System.out.println("materialListvideo="+materialListvideo);
		String materialListvoice = getMaterialList(taskInfo, "voice", 0, 20);
		System.out.println("materialListvoice="+materialListvoice);
		String materialListnews = getMaterialList(taskInfo, "news", 0, 20);
		System.out.println("materialListnews="+materialListnews);
		
		
		
		String media_id = "F-K4dO1kr-9hJRJ9tejavHRNjtrzFEp4oVnF5pXEpBQ";
		//视频的返回json.其他的直接输出文件
		String Material = getMaterial(media_id, taskInfo);
		System.out.println("Material="+Material);
	}
	
	/**
	 * Test
	 * @param args
	 */
	public static void main(String[] args) {
		TaskInfo taskInfo = createTaskInfo();
		printList(taskInfo);
		process(taskInfo);
	}

}
