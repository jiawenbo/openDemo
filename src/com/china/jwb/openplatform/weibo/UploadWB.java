package com.china.jwb.openplatform.weibo;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.china.jwb.common.tools.CommonUtil;
import com.china.jwb.common.tools.JsonUtil;
import com.china.jwb.common.tools.OdpConstants;
import com.china.jwb.common.tools.OpenPlatFormConstants;
import com.china.jwb.common.tools.UrlUtil;
import com.china.jwb.common.tools.YsHttpUtil;
import com.china.jwb.common.tools.config.ConfigurationUtil;
import com.china.jwb.common.tools.img.ImgUtil;
import com.china.jwb.common.tools.img.MakeImg1beifeng;
import com.china.jwb.openplatform.entity.Article;
import com.china.jwb.openplatform.entity.TaskInfo;

/**
 * 调用微博发布DEMO
 * 部分参数为固定参数，具体使用请看API
 * @author jiawenbo
 *
 */
public class UploadWB {
	
	private static final Logger logger = Logger.getLogger(UploadWB.class);

	public static String post_upload_pic = "https://api.weibo.com/2/statuses/upload_pic.json";

	/**
	 * 发微博
	 * 目前支持    SINA_TYPE_Pic
	 * 		 SINA_TYPE_Text
	 * 		 SINA_TYPE_Long
	 * 		 SINA_TYPE_PicAndText
	 */
	private boolean process(TaskInfo taskInfo){
		if(taskInfo.getResType()==OpenPlatFormConstants.SINA_TYPE_Video){
			return process_Video(taskInfo);
        }else if(taskInfo.getResType()==OpenPlatFormConstants.SINA_TYPE_Audio){
			return false;
        }else if(taskInfo.getResType()==OpenPlatFormConstants.SINA_TYPE_PicAndText){
			return process_PicAndText(taskInfo);
        }else if(taskInfo.getResType()==OpenPlatFormConstants.SINA_TYPE_Pic){
			return false;
        }else if(taskInfo.getResType()==OpenPlatFormConstants.SINA_TYPE_Text){
			return process_Text(taskInfo);
        }else if(taskInfo.getResType()==OpenPlatFormConstants.SINA_TYPE_Other){
			return false;
        }else if(taskInfo.getResType()==OpenPlatFormConstants.SINA_TYPE_Long){
        	return process_Long(taskInfo);
        }
        return false;
	}
	
	/**
	 * 发微博视频
	 * 
	 */
	public boolean process_Video(TaskInfo taskInfo){
		Map<String,String> jsonMap = new HashMap<String,String>();		 
    	jsonMap.put("source", taskInfo.getAppId());
    	jsonMap.put("access_token", taskInfo.getAccessToken());
    	jsonMap.put("status", taskInfo.getResDesc()+taskInfo.getUrlContent());
    	
    	if(taskInfo.getResDesc().length()<140&&(taskInfo.getResDesc()+taskInfo.getUrlContent()).length()<140){
    		String tempFile=null;
    		if(taskInfo.getResUrl()!=null&&!"".equals(taskInfo.getResUrl())){//判断发视频的时候 有没有图片 
        		 tempFile= CommonUtil.uploudUrl(taskInfo);
            	if(tempFile==null){
            		logger.info("下载原因！！分发微博失败");
            	   return false;
            	}
            	jsonMap.put("pic", tempFile);
        	}
    		String resJson= null;
    		if(tempFile!=null){//视频图片微博
    			logger.info("分发视频图片微博");
    			taskInfo.setResUrl(tempFile);
    			taskInfo.setResDesc(taskInfo.getResDesc()+taskInfo.getUrlContent());
    			return uploadPic(taskInfo);
    		}else{
    			logger.info("分发视频微博");
    			resJson= UrlUtil.post("https://api.weibo.com/2/statuses/update.json",jsonMap);
    		}
    		Map<String, Object> tempResMap = JsonUtil.readJSON2Map(resJson);
    		if(tempResMap.get("created_at")== null){
    			logger.info("最终分发微博失败返回参数"+tempResMap);
    			return false;
    		}
    		return true;
    	}else{//大于140 就是长微博   转成图片发出去
    		logger.info("带视频的长微博");
    		MakeImg1beifeng.createImge(ConfigurationUtil.getConfigValue("Imges"),ConfigurationUtil.getConfigValue("Imgex"),taskInfo);
    		taskInfo.setResDesc(taskInfo.getResName()+taskInfo.getUrlContent());
    		taskInfo.setResUrl(ConfigurationUtil.getConfigValue(OdpConstants.CREATIMG_PATH));
    	   return uploadPic(taskInfo);
    	 }
	}
	/**
	 * 发微博视频
	 * 測試。是否能直接發佈視頻內容
	 * 
	 */
	public boolean process_Video_test(TaskInfo taskInfo){
		Map<String,Object> jsonMap = new HashMap<String,Object>();		 
    	jsonMap.put("source", taskInfo.getAppId());
    	jsonMap.put("access_token", taskInfo.getAccessToken());
    	jsonMap.put("status", taskInfo.getResDesc()+taskInfo.getUrlContent());
    	jsonMap.put("pic", taskInfo.getResUrl());
    	
    	String filename= CommonUtil.uploudUrl(taskInfo);
		String resJson= YsHttpUtil.doPost("https://api.weibo.com/2/statuses/upload.json",jsonMap,"pic",filename);
		String resJson2= YsHttpUtil.doPost("https://api.weibo.com/2/statuses/upload.json",jsonMap,"vid",filename);
		Map<String, Object> tempResMap = JsonUtil.readJSON2Map(resJson);
		if(tempResMap.get("created_at") == null){
			logger.info("最终分发微博失败返回参数"+tempResMap);
			return false;
		}
		return true;
	}
	/***
	 * 操作——图文类型,
	 * 本系统图文类型发布时候分无图、单图、多图三种情况。
	 * 多图对应微博高级写入接口，需要申请商务合作。
	 * 其它对应微博普通写入接口。
	 * @param taskInfo
	 * @return
	 */
	public boolean process_PicAndText(TaskInfo taskInfo){
		if(null != taskInfo.getArticle()&&taskInfo.getArticle().length>0) {
			if(taskInfo.getArticle().length==1){
				//如果一个图的话 可以使用单图+文字的接口。
				String picHttpUrl = taskInfo.getArticle()[0].getThurmbMediaUrl();
				taskInfo.setResUrl(picHttpUrl);
				return process_Text(taskInfo);
			}else{
				return process_PicAndText_super(taskInfo);
			}
			
		}else{
			return process_Text(taskInfo);
		}
	}
	
	/***
	 * 高级写入接口
	 * 需要商务合作授权。
	 * @param taskInfo
	 * @return
	 */
	public boolean process_PicAndText_super(TaskInfo taskInfo){
		StringBuilder pic_id=new StringBuilder("");
		if(null != taskInfo.getArticle()&&taskInfo.getArticle().length>0) {
			Article[] articles = null;
			articles = taskInfo.getArticle();
			/**
			 * 2、循环 上传图片缩略图（指传送过来的图片）
			 *
			 **/
			//循环图片地址,进行上传.

			Map<String, Object> thumbMap = new HashMap<String, Object>();
			logger.info("articele" + taskInfo.getArticle());

			for (int i = 0; i < taskInfo.getArticle().length; i++) {
				String picHttpUrl = articles[i].getThurmbMediaUrl();
				//将图片下载到本地 .并获得本地地址.
				taskInfo.setResUrl(picHttpUrl);
				String fileSource = CommonUtil.uploudUrl(taskInfo);

				if (fileSource == null) {
					logger.info("下载原因！！分发微信图文失败");
					return false;
				}
				Map<String, Object> tempResMap=new HashMap<String, Object>();
		    	tempResMap.put("source", taskInfo.getAppId());
		    	tempResMap.put("access_token", taskInfo.getAccessToken());
		    	tempResMap.put("pic", fileSource);
//				String resJson = YsHttpUtil.doPost("https://api.weibo.com/2/statuses/upload_pic.json",tempResMap);
		    	//表单提交名必须==pic
				String resJson = YsHttpUtil.doPost("https://api.weibo.com/2/statuses/upload_pic.json",tempResMap,"pic",fileSource);
				thumbMap = JsonUtil.readJSON2Map(resJson);
				if (thumbMap.get("pic_id") == null) {
					logger.info("上传缩略图失败,返回的参数" + thumbMap);
					return false;
				}
				pic_id.append(thumbMap.get("pic_id").toString() + ",");
				articles[i].setThurmbMediaId(thumbMap.get("pic_id").toString());
			}
		}
		Map<String,Object> jsonPicMap = new HashMap<String,Object>();
		jsonPicMap.put("source", taskInfo.getAppId());
		jsonPicMap.put("access_token", taskInfo.getAccessToken());
		jsonPicMap.put("status", taskInfo.getResDesc());
		if(pic_id.toString()!=null&&!"".equals(pic_id.toString())){
			jsonPicMap.put("pic_id", pic_id.toString());
			String resJson = YsHttpUtil.doPost("https://api.weibo.com/2/statuses/upload_url_text.json",jsonPicMap);
			Map<String, Object> tempResMap = JsonUtil.readJSON2Map(resJson);
			if(tempResMap.get("created_at") == null){
				logger.info("最终分发微博失败返回参数"+tempResMap);
				return false;
			}
			return true;
		}else{
			logger.info("无图，不能使用高级发布接口，返回错误");
			return false;
		}
		
	}
	/***
	 * 操作——文字类型（单图+文字）
	 * @param taskInfo
	 * @return
	 */
	public boolean process_Text(TaskInfo taskInfo){
		Map<String,Object> jsonMap = new HashMap<String,Object>();		 
    	jsonMap.put("source", taskInfo.getAppId());
    	jsonMap.put("access_token", taskInfo.getAccessToken());
    	jsonMap.put("status", taskInfo.getResDesc());
    	jsonMap.put("pic", taskInfo.getResUrl());
    	System.out.println(jsonMap);
    	logger.info("发送参数"+jsonMap);
    	if(taskInfo.getResDesc().length()<140){
    		logger.info("分发微博");
    		if(jsonMap.get("pic")!=null&&!"".equals(jsonMap.get("pic"))){
    			String filename= CommonUtil.uploudUrl(taskInfo);
    			String resJson= YsHttpUtil.doPost("https://api.weibo.com/2/statuses/upload.json",jsonMap,"pic",filename);
        		Map<String, Object> tempResMap = JsonUtil.readJSON2Map(resJson);
        		if(tempResMap.get("created_at") == null){
        			logger.info("最终分发微博失败返回参数"+tempResMap);
        			return false;
        		}
    		}else{
    			//纯字
    			//String resJson= UrlUtil.post("https://api.weibo.com/2/statuses/update.json",jsonMap);
    			String resJson= YsHttpUtil.doPost("https://api.weibo.com/2/statuses/update.json",jsonMap);
        		Map<String, Object> tempResMap = JsonUtil.readJSON2Map(resJson);
        		if(tempResMap.get("created_at") == null){
        			logger.info("最终分发微博失败返回参数"+tempResMap);
        			return false;
        		}
    		}
    		
    		return true;
    	}else{//大于140 就是长微博   转成图片发出去
    		logger.info("分发长微博");
    		MakeImg1beifeng.createImge(ConfigurationUtil.getConfigValue("Imges"),ConfigurationUtil.getConfigValue("Imgex"),taskInfo);
			ImgUtil.Html2Image(ConfigurationUtil.getConfigValue(OdpConstants.CREATIMG_PATH), taskInfo.getResDesc());
			taskInfo.setResUrl(ConfigurationUtil.getConfigValue(OdpConstants.CREATIMG_PATH));
    		taskInfo.setResDesc(taskInfo.getResName());
    		return uploadPic(taskInfo);
    	}
	}
	
	/***
	 * 操作——文字类型
	 * @param taskInfo
	 * @return
	 */
	public boolean process_Long(TaskInfo taskInfo){
		Map<String,Object> jsonMap = new HashMap<String,Object>();		 
    	jsonMap.put("source", taskInfo.getAppId());
    	jsonMap.put("access_token", taskInfo.getAccessToken());
    	jsonMap.put("status", taskInfo.getResDesc());
    	jsonMap.put("pic", taskInfo.getResUrl());
    	System.out.println(jsonMap);
    	logger.info("发送参数"+jsonMap);
    	logger.info("分发长微博");
    	String content  = taskInfo.getResDesc();
    	content =  "<div style=\"width: 440px;padding:10px;\" class=\"myContent\">"+content+"</div>";
    	content =  "<style>.myContent p{text-indent: 2em;}</style>" +content;
    	taskInfo.setResDesc(content);
//		MakeImg1beifeng.createImge(ConfigurationUtil.getConfigValue("Imges"),ConfigurationUtil.getConfigValue("Imgex"),taskInfo);
		ImgUtil.Html2Image(ConfigurationUtil.getConfigValue(OdpConstants.CREATIMG_PATH), taskInfo.getResDesc());
		taskInfo.setResUrl(ConfigurationUtil.getConfigValue(OdpConstants.CREATIMG_PATH));
		taskInfo.setResDesc(taskInfo.getResName());
		return uploadPic(taskInfo);
	}
	
	//公共的 上传图片是视频图片方法    微博和长微博都可以
	public  static  boolean uploadPic(TaskInfo taskInfo){
		System.out.println( taskInfo.getAccessToken());
		Map<String, Object> tempResMap=new HashMap<String, Object>();
		tempResMap.put("status", taskInfo.getResDesc());
    	tempResMap.put("access_token", taskInfo.getAccessToken());
    	tempResMap.put("pic", taskInfo.getResUrl());
    	logger.info("分发的图片"+taskInfo.getResUrl());
    	logger.info("分发的参数"+tempResMap);
		String resJsonvp=YsHttpUtil.doPost("https://api.weibo.com/2/statuses/upload.json",tempResMap,"pic",taskInfo.getResUrl());
		tempResMap = JsonUtil.readJSON2Map(resJsonvp);
		if(tempResMap.get("created_at") == null){
			logger.info("最终分发微博失败返回参数"+resJsonvp);
			return false;
		}else{
			return true;
		}
	}
}
