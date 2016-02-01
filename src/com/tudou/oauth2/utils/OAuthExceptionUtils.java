package com.tudou.oauth2.utils;

import java.util.LinkedHashMap;

import com.tudou.oauth2.consts.OAuthParams;
import com.tudou.oauth2.exception.OAuthError;
import com.tudou.oauth2.exception.OAuthException;
import com.tudou.open.utils.JSONUtils;

/**
 * OAuth异常工具
 * 
 * @author myao
 */
public class OAuthExceptionUtils {

	/**
	 * 从返回值中取得异常
	 * 
	 * @param jsonString
	 * @return
	 */
	public static OAuthException fromJsonString(String jsonString) {
		jsonString = jsonString.trim();
		if (-1 == jsonString.indexOf(OAuthParams.ERROR_CODE) || -1 == jsonString.indexOf(OAuthParams.ERROR_INFO)) {
			return null;
		}
		final int errorCode;
		final String errorInfo;
		if (jsonString.startsWith("{")) {
			LinkedHashMap<?, ?> jsonMap = JSONUtils.parseObject(jsonString, LinkedHashMap.class);
			errorCode = (Integer) jsonMap.get("error_code");
			errorInfo = (String) jsonMap.get("error_info");
		} else {
			throw new RuntimeException("Invalid json string: " + jsonString);
		}

		return new OAuthException(new OAuthError(errorCode, errorInfo));
	}
}
