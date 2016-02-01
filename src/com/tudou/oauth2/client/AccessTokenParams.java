package com.tudou.oauth2.client;

/**
 * oauth2.0/access_token 接口参数
 * 
 * @author yaoming
 */
public class AccessTokenParams {

	private String client_id;
	private String client_secret;
	private String code;
 
	/**
	 * 构造一个参数类（其它参数使用默认配置）
	 * 
	 * @param code
	 *            authorize接口得到的code参数
	 */
	public AccessTokenParams(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getClient_secret() {
		return client_secret;
	}

	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}

}
