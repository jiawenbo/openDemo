package com.tudou.open.utils;

import yao.util.http.HTTPSetting;
import yao.util.http.HTTPUtil;
import yao.util.http.SchemeRegistrys;

/**
 * HTTP工具类
 * 
 * @author yaoming
 */
public class HTTPUtils {

	private final static HTTPUtil httpUtil;

	static {
		httpUtil = new HTTPUtil(new HTTPSetting(SchemeRegistrys.getNoVerifierSSLSchemeRegistry()), "UTF8");
	}

	public static HTTPUtil getHTTPUtil() {
		return httpUtil;
	}

}
