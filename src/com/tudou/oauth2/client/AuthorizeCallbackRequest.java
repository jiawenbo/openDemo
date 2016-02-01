package com.tudou.oauth2.client;

import yao.util.type.IntegerUtil;

import com.tudou.oauth2.consts.OAuthParams;
import com.tudou.oauth2.exception.OAuthError;

/**
 * authorize_callback 接口参数
 * 
 * @author yaoming
 */
public class AuthorizeCallbackRequest {

	private String code;
	private String state;
	private OAuthError oauthError;

	public OAuthError getOauthError() {
		return oauthError;
	}

	private AuthorizeCallbackRequest() {
	}

	public static AuthorizeCallbackRequest fromRequest(javax.servlet.http.HttpServletRequest request) {
		// 正常返回
		String code;
		if (null != (code = request.getParameter("code"))) {
			AuthorizeCallbackRequest bean = new AuthorizeCallbackRequest();
			bean.code = code;
			bean.state = request.getParameter("state");
			return bean;
		}
		// 错误返回
		String error_code;
		if (null != (error_code = request.getParameter(OAuthParams.ERROR_CODE))) {
			AuthorizeCallbackRequest bean = new AuthorizeCallbackRequest();
			if (IntegerUtil.isInt(error_code)) {
				bean.oauthError = new OAuthError(Integer.parseInt(error_code), request.getParameter(OAuthParams.ERROR_INFO));
			}
			return bean;
		}
		// 无返回
		return null;
	}

	public String getCode() {
		return code;
	}

	public String getState() {
		return state;
	}

	/**
	 * 正确的callback
	 * 
	 * @return
	 */
	public boolean isGood() {
		return null != code;
	}

	/**
	 * 错误的callback
	 * 
	 * @return
	 */
	public boolean isError() {
		return null != oauthError;
	}

};
