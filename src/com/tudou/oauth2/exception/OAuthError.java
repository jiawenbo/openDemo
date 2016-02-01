package com.tudou.oauth2.exception;

import com.tudou.open.error.ErrorInfo;

/**
 * OAuth错误类
 * 
 * @author myao
 */
public class OAuthError extends ErrorInfo {

	private static final byte module = 1;

	public OAuthError(byte type, short value, String info) {
		super(module, type, value, info);
	}

	public OAuthError(int code, String info) {
		super(code, info);
	}

}
