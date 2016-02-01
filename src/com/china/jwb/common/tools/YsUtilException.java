package com.china.jwb.common.tools;

/**
 * HTTP请求异常处理
 * @author jiawenbo
 *
 */
public class YsUtilException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public YsUtilException() {
		super();
	}

	public YsUtilException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super();
	}

	public YsUtilException(String message, Throwable cause) {
		super(message, cause);
	}

	public YsUtilException(String message) {
		super(message);
	}

	public YsUtilException(Throwable cause) {
		super(cause);
	}
	
	
}
