package com.china.jwb.openplatform.youku;

import java.util.HashMap;

import com.youku.uploader.YoukuUploader;

/**
 * 调用优酷发布视频DEMO
 * 部分参数为固定参数，具体使用请看API
 * @author jiawenbo
 *
 */
public class UploadYK {
	
	private static YoukuUploader uploader;

	public static void main(String[] args) {
		String result = "";
		String client_id = "782f13514ee1dd5a"; 						//OpenAPI client_id
		String client_secret = "a7e87a2cff6435b16bc64fd3a7411ad4"; 	//OpenAPI client_secret
		//String username = "18311079927"; 							//Youku username or email
		//String password = "******"; 								//youku password
		String access_token = "e48684ceedc3b8ee192e3925f59e5b54"; 
		HashMap<String, String> params, uploadInfo;
		String filename = "D:/test.flv";
		params = new HashMap<String, String>();
		//params.put("username", username);
		//params.put("password", password);
		params.put("access_token", access_token);
		uploadInfo = new HashMap<String, String>();
		uploadInfo.put("file_name", filename); 						// 指定：文件名
		uploadInfo.put("title", "1111111"); 						// 指定：标题
		uploadInfo.put("tags", "Music"); 							// 指定：分类
		uploadInfo.put("public_type", "all"); 						//视频公开类型（all：公开（默认），friend：仅好友，password：需要输入密码才能观看）
		//uploadInfo.put("watch_password", ""); 					//密码，当public_type为password时，必填
		
		
		uploader = new YoukuUploader(client_id, client_secret);
		result = uploader.upload(params, uploadInfo, filename, true); // 第四个参数：boolean类型（true：显示进度 false：不显示进度）
		System.out.print(result); //返回视频id
	}
}
