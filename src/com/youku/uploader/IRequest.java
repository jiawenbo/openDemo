package com.youku.uploader;

import java.util.Map;

public interface IRequest {
    
	/**
	 * 获取access_token
	 */
	public String login(String username, String password);

	/**
	 * 刷新access_token
	 */
	public String refresh_token(String refresh_token);

	/**
	 * 获取upload_token、upload_server_url
	 */
	public String create(String access_token, Map<String, String> uploadInfo);

	/**
	 * 创建上传文件、提交上传信息
	 */
	public String create_file(String upload_token, String file_size,
			String ext, String upload_server_uri);

	/**
	 * 请求创建slice_task_id, 获取分片offset、长度等
	 */
	public String new_slice(String upload_token, String upload_server_uri);

	/**
	 * 上传分片
	 */
	public String upload_slice(String upload_token, String upload_server_uri,
			Map<String, String> sliceInfo, byte[] data);

	/**
	 * 检查上传任务是否完成
	 */
	public String check(String upload_token, String upload_server_uri);

	/**
	 * 确认上传结束
	 */
	public String commit(String access_token, String upload_token,
			String upload_server_ip);

	/**
	 * 上传取消
	 */
	public String cancel(String access_token, String upload_token,
			String upload_server_ip);

	/**
	 * 更新sdk版本号
	 */
	public String update_version(String category, String type, String version);

}