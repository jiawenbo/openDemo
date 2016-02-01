package com.tudou.oauth2.client.util;

import java.util.List;

import yao.util.param.Param;

import com.tudou.oauth2.client.OAuthClientConfig;
import com.tudou.oauth2.client.SDKException;
import com.tudou.oauth2.exception.OAuthException;
import com.tudou.oauth2.utils.OAuthExceptionUtils;
import com.tudou.open.utils.HTTPUtils;

/**
 * 工具类
 * 
 * @author yaoming
 */
public class OAuthPost {

	/**
	 * Post一些参数到指定URL
	 * 
	 * @param url
	 *            URL
	 * @param params
	 *            参数列表
	 * @return body文本
	 * @throws OAuthException
	 * @throws SDKException
	 */
	public static String post(String url, List<Param> params) throws OAuthException, SDKException {
		final String content;
		final OAuthException oauthException;
		try {
			content = HTTPUtils.getHTTPUtil().getStringByPost(url, params, OAuthClientConfig.HTTP_CHARSET);
			oauthException = OAuthExceptionUtils.fromJsonString(content);
		} catch (Exception e) {
			throw new SDKException(e);
		}
		if (null != oauthException) {
			throw oauthException;
		}
		return content;
	}

}
