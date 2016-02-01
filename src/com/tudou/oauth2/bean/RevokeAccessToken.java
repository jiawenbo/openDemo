package com.tudou.oauth2.bean;

import java.io.Serializable;

/**
 * /oauth2/revoke_access_token 接口的返回类
 * 例如：{"result":true}
 * 
 * @author myao
 */
public class RevokeAccessToken implements Serializable {

	private static final long serialVersionUID = 7711053418307572746L;

	private boolean result;

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
}
