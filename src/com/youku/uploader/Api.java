package com.youku.uploader;

import java.util.HashMap;
import java.util.Map;

public class Api implements IRequest {

	private String client_id = null;
	private String client_secret = null;
	private Util uploadUtil = new Util();

	public Api(String client_id, String client_secret) {
		this.client_id = client_id;
		this.client_secret = client_secret;
	}

	public String login(String username, String password) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("client_id", this.client_id);
		params.put("client_secret", this.client_secret);
		params.put("username", username);
		params.put("password", password);
		params.put("grant_type", "password");
		String result = uploadUtil.post(Config.LOGIN_URL, uploadUtil.mapToArrayList(params));
		return result;
	}

	public String refresh_token(String refresh_token) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("client_id", this.client_id);
		params.put("client_secret", this.client_secret);
		params.put("grant_type", "refresh_token");
		params.put("refresh_token", refresh_token);
		String result = uploadUtil.post(Config.LOGIN_URL, uploadUtil.mapToArrayList(params));
		return result;
	}

	public String create(String access_token, Map<String, String> uploadInfo) {
		Map<String, String> params = new HashMap<String, String>(uploadInfo);
		params.put("client_id", this.client_id);
		params.put("access_token", access_token);
		String result = uploadUtil.get(Config.CREATE_URL, uploadUtil.mapToArrayList(params));
		return result;
	}

	public String create_file(String upload_token, String file_size, String ext, String upload_server_uri) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("upload_token", upload_token);
		params.put("file_size", file_size);
		params.put("ext", ext);
		params.put("slice_length", Config.SLICE_LENGTH + "");
		String url = getRealUrl(Config.CREATE_FILE_URL, upload_server_uri);
		String result = uploadUtil.post(url, uploadUtil.mapToArrayList(params));
		return result;
	}

	public String new_slice(String upload_token, String upload_server_uri) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("upload_token", upload_token);
		String result = uploadUtil.get(getRealUrl(Config.NEW_SLICE_URL, upload_server_uri), uploadUtil.mapToArrayList(params));
		return result;
	}

	public String upload_slice(String upload_token, String upload_server_uri, Map<String, String> sliceInfo, byte[] data) {
		Map<String, String> params = new HashMap<String, String>(sliceInfo);
		params.put("upload_token", upload_token);
		String result = uploadUtil.postFile(getRealUrl(Config.UPLOAD_SLICE_URL, upload_server_uri), uploadUtil.mapToArrayList(params), data);
		return result;
	}

	public String check(String upload_token, String upload_server_uri) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("upload_token", upload_token);
		String result = uploadUtil.get(getRealUrl(Config.CHECK_URL, upload_server_uri), uploadUtil.mapToArrayList(params));
		return result;
	}

	public String commit(String access_token, String upload_token, String upload_server_ip) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("client_id", this.client_id);
		params.put("access_token", access_token);
		params.put("upload_token", upload_token);
		params.put("upload_server_ip", upload_server_ip);
		String result = uploadUtil.post(Config.COMMIT_URL, uploadUtil.mapToArrayList(params));
		return result;
	}

	public String cancel(String access_token, String upload_token, String upload_server_ip) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("client_id", this.client_id);
		params.put("access_token", access_token);
		params.put("upload_token", upload_token);
		params.put("upload_server_ip", upload_server_ip);
		String result = uploadUtil.get(Config.CANCEL_URL, uploadUtil.mapToArrayList(params));
		return result;
	}

	private String getRealUrl(String url, String upload_server_uri) {
		return url.replace("upload_server_uri", upload_server_uri);
	}

	public String update_version(String category, String type, String version) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("client_id", this.client_id);
		params.put("category", category);
		params.put("type", type);
		params.put("version", version);
		String result = uploadUtil.get(Config.VERSION_UPADATE_URL, uploadUtil.mapToArrayList(params));
		return result;
	}
}
