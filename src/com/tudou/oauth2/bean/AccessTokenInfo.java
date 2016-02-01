package com.tudou.oauth2.bean;

import java.io.Serializable;

/**
 * /oauth2/get_token_info 接口的返回类
 * 例如：{"uid":0, "app_key":"appkey", "scope":"", "create_at":0, "expires_in":0}
 * 
 * @author myao
 */
public class AccessTokenInfo implements Serializable {

	private static final long serialVersionUID = 209049998859213734L;

	private Integer uid;// 用户ID
	private String app_key;// 应用appkey
	private String scope;// 用户授予的scope权限。
	private Long create_at;// 令牌创建时间（从1970年1月1日0时0分0秒计算的秒数）
	private Integer expires_in;// 过期时间（有效时长秒数）

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getApp_key() {
		return app_key;
	}

	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public Long getCreate_at() {
		return create_at;
	}

	public void setCreate_at(Long create_at) {
		this.create_at = create_at;
	}

	public Integer getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}
}
