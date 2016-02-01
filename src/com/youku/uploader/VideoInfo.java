package com.youku.uploader;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.json.JSONObject;

public class VideoInfo {
	/**
	 * 上传步骤flag
	 */
	private int step = -1;

	/**
	 * 上传token
	 */
	private String upload_token;

	/**
	 * 上传服务器URI
	 */
	private String upload_server_uri;
	
	/**
	 * 上传服务器IP，用于commit、cancel
	 */
	private String upload_server_ip;
	
	/**
	 * 上传服务器IP，用于commit、cancel
	 */
	private JSONObject sliceInfo;
	
	/**
	 * check_result check视频状态返回JSON数据
	 */
	private  JSONObject check_result;
	
	/**
	 * 上传文件信息
	 * 
	 * @param file_name
	 * @param file_md5
	 * @param file_size
	 * @param title
	 * @param tags
	 */
	private HashMap <String,String> uploadInfo;

	public VideoInfo(HashMap<String,String> uploadInfo) {
		this.uploadInfo = uploadInfo;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public HashMap <String,String> getUploadInfo() {
		return uploadInfo;
	}

	public String getUploadInfo(String key) {
		
		return uploadInfo.containsKey(key) ? uploadInfo.get(key) : null;
	}

	public String getUploadServerUri() {
		return upload_server_uri;
	}

	public void setUploadServerUri(String upload_server_uri) {
		this.upload_server_uri = upload_server_uri;
	}


	public String getUploadToken() {
		return upload_token;
	}

	public void setUploadToken(String upload_token) {
		this.upload_token = upload_token;
	}

	public Boolean checkUploadInfo(File file) throws IOException, NoSuchAlgorithmException {
		if (file.exists() && file.isFile()) {
            String file_name = uploadInfo.get("file_name");
            uploadInfo.put("file_md5", FileUtil.md5(file));
            uploadInfo.put("file_size", String.valueOf(file.length()));
            uploadInfo.put("ext", file_name.substring(file_name.lastIndexOf(".") + 1));
            return true;
		} else {
			return false;
		}
	}

	public String getUploadServerIp() {
		return upload_server_ip;
	}

	public void setUploadServerIp(String upload_server_ip) {
		this.upload_server_ip = upload_server_ip;
	}
	
	public void setSliceInfo(JSONObject sliceInfo) {
		this.sliceInfo = sliceInfo;
	}
	
	public JSONObject getSliceInfo() {
		return sliceInfo;
	}
	
	public void setCheckResult(JSONObject checkResult) {
		this.check_result = checkResult;
	}
	
	public JSONObject getCheckResult() {
		return check_result;
	}
}
