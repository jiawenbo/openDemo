package com.tudou.oauth2.bean;

import java.io.Serializable;

/**
 * /oauth2/access_token 接口的返回类
 * 例如：{ "access_token": "ACCESS_TOKEN", "expires_in": 1234, "uid":"12341234"}
 * 
 * @author myao
 */
public class AccessToken implements Serializable {

	private static final long serialVersionUID = -7350377572165140313L;

	private String access_token;
	private Integer expires_in;
	private Integer uid;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Integer getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Integer expire_at) {
		this.expires_in = expire_at;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}
}