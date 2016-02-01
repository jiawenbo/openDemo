package com.china.jwb.openplatform.tudou;

import java.util.HashMap;
import java.util.Map;

import com.china.jwb.common.tools.YsHttpUtil;


public class UploadTD {

	
	private static boolean process(String tempFile,String app_key,String access_token){
	      /**
	       * 获取上传视频的地址
	       */
		Map<String ,Object> tempUrl=new HashMap<String,Object>();
		tempUrl.put("app_key", app_key);
		tempUrl.put("access_token", access_token);
		tempUrl.put("format", "json");
		tempUrl.put("title", "分享标题");
		tempUrl.put("tags", "新闻");
		tempUrl.put("content", "内容XXXXXX");
		int catchannelId=1;
		tempUrl.put("channelId",catchannelId);
		String tempResJson=YsHttpUtil.doGet("http://api.tudou.com/v6/video/upload_url", tempUrl);
		if(null == tempResJson || "".equals(tempResJson)){
			System.out.println("获取上传地址为空");
			return false;
		}
		Map<String, Object> ResMap = readJSON2Map(tempResJson);
		if(ResMap.get("error_code") != null){
			System.out.println("获取上传视频地址出错 返回参数"+ResMap);
			return false;
		}
		String uploadURL = ResMap.get("uploadUrl").toString();
		String token = ResMap.get("token").toString();
		
		
		Map<String, Object> tempResMap=new HashMap<String, Object>();
    	tempResMap.put("token", token);
    	String resJson=YsHttpUtil.doPost(uploadURL,tempResMap,"file",tempFile);
    	
    	System.out.println("上传返回值=="+resJson);
		if(resJson == null || "".equals(resJson)){
			System.out.println("上传视频失败"+resJson);
			return false;
		}
		Map<String, Object> resJsonMap = readJSON2Map(resJson);
		if(resJsonMap == null || resJsonMap.size() == 0){
			System.out.println("上传视频失败"+resJson);
			return false;
		}
        if(resJsonMap.get("result") == null || !resJsonMap.get("result").equals("ok")){
        	System.out.println("上传视频失败"+resJson);
        	return false;
        }	
        return true;
	}
	public static Map<String, Object> readJSON2Map(String json){
			Map<String, Object> map = new HashMap<String, Object>();
			if (null != json && !"".equals(json)) {
				com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
				try {
					map = objectMapper.readValue(json, Map.class);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
			return map;
	}
	
	/**
	 * 调用土豆发布视频DEMO
	 * 部分参数为固定参数，具体使用请看API
	 * @param args
	 */
	public static void main(String[] args) {
		String tempFile = "D:/test.flv";
		String app_key  ="2f71671e008f4e17";
		String access_token ="y_5oe5ge5oeaJTowJo454yTJZTyomogTpBkJ5_J5emm4kge5Jp4Bg44oo5mp4_gmmpp5gNmm_B4JZ";
		process(tempFile,app_key,access_token);
	}
}
