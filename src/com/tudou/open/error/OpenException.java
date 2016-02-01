package com.tudou.open.error;

/**
 * 开放平台异常
 * 
 * @author myao
 */
public abstract class OpenException extends Exception {

	private static final long serialVersionUID = 4858606914325668878L;

	private ErrorInfo error;

	public ErrorInfo getError() {
		return error;
	}

	protected OpenException(ErrorInfo errorInfo) {
		this.error = errorInfo;
	}

	public String getMessage() {
		StringBuffer sb = new StringBuffer();
		if (null != error) {
			sb.append(error.toString());
		}
		if (null != super.getMessage() && !super.getMessage().isEmpty()) {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append(super.getMessage());
		}
		return sb.toString();
	}
}
