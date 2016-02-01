package com.tudou.oauth2.client;

public class SDKException extends Exception {

	private static final long serialVersionUID = -2872115657869684633L;

	public SDKException(String message, Throwable cause) {
		super(message, cause);
	}

	public SDKException(String message) {
		super(message);
	}

	public SDKException(Throwable cause) {
		super(cause);
	}

}
