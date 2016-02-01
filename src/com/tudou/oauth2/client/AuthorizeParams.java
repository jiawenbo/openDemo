package com.tudou.oauth2.client;

/**
 * oauth2.0/authorize 接口参数
 * 
 * @author yaoming
 */
public class AuthorizeParams {

	private String client_id;
	private String redirect_uri;
	private String scope;
	private String state;
	private String display;

	/**
	 * 使用默认的配置参数构造一个参数类
	 */
	public AuthorizeParams() {
	}

	/**
	 * 构造一个参数类（其它参数使用默认配置）
	 * 
	 * @param scope
	 *            对应scope参数
	 * @param state
	 *            对应state参数
	 */
	public AuthorizeParams(String scope, String state) {
		this.scope = scope;
		this.state = state;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getRedirect_uri() {
		return redirect_uri;
	}

	public void setRedirect_uri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

}
