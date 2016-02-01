package com.tudou.oauth2.exception;

import com.tudou.open.error.OpenException;

/**
 * OAuth异常类
 * 
 * @author myao
 */
public class OAuthException extends OpenException {

	private static final long serialVersionUID = 1093935942707101964L;

	public OAuthException(OAuthError errorInfo) {
		super(errorInfo);
	}

	public OAuthError getError() {
		return (OAuthError) super.getError();
	}

}