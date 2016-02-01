package com.tudou.oauth2.client;

import java.util.LinkedList;
import java.util.List;

import yao.util.array.ArrayItemUtil;
import yao.util.param.Param;
import yao.util.string.StringUtil;
import yao.util.web.UrlUtil;

import com.tudou.oauth2.bean.AccessToken;
import com.tudou.oauth2.bean.AccessTokenInfo;
import com.tudou.oauth2.bean.RevokeAccessToken;
import com.tudou.oauth2.client.util.OAuthPost;
import com.tudou.oauth2.exception.OAuthException;
import com.tudou.open.utils.JSONUtils;

/**
 * TUDOU OAuth 授权
 * 
 * @author yaoming
 */
public class TudouOAuth2 {

	/**
	 * 拼接土豆OAuth2.0授权页面地址
	 * 
	 * @param authorizeParams
	 *            参数
	 * @return URLString
	 */
	public String authorizeURL(AuthorizeParams authorizeParams) {
		String authorizeUrl = OAuthClientConfig.URL_AUTHORIZE;
		List<Param> params = new LinkedList<Param>();
		params.add(new Param("client_id", ArrayItemUtil.getFirstNotNull(new String[] { authorizeParams.getClient_id(), OAuthClientConfig.APP_KEY })));
		params.add(new Param("redirect_uri", ArrayItemUtil.getFirstNotNull(new String[] { authorizeParams.getRedirect_uri(), OAuthClientConfig.REDIRECT_URI })));
		params.add(new Param("display", ArrayItemUtil.getFirstNotNull(new String[] { authorizeParams.getDisplay(), OAuthClientConfig.DISPLAY })));
		params.add(new Param("scope", authorizeParams.getScope()));
		params.add(new Param("state", authorizeParams.getState()));
		return UrlUtil.UTF8.parseUrl(authorizeUrl, params);
	}

	/**
	 * 取得accessToken(授权令牌)
	 * 
	 * @param accessTokenParams
	 *            参数
	 * @return AccessToken
	 * @throws OAuthException
	 * @throws SDKException
	 */
	public AccessToken accessToken(AccessTokenParams accessTokenParams) throws OAuthException, SDKException {
		List<Param> params = new LinkedList<Param>();
		params.add(new Param("code", StringUtil.toString(accessTokenParams.getCode())));
		params.add(new Param("client_id", ArrayItemUtil.getFirstNotNull(new String[] { accessTokenParams.getClient_id(), OAuthClientConfig.APP_KEY })));
		params.add(new Param("client_secret", ArrayItemUtil.getFirstNotNull(new String[] { accessTokenParams.getClient_secret(), OAuthClientConfig.APP_SECRET })));
		String jsonString = OAuthPost.post(OAuthClientConfig.URL_ACCESS_TOKEN, params);
		AccessToken accessToken = JSONUtils.parseObject(jsonString, AccessToken.class);
		if (null == accessToken) {
			throw new SDKException("Invalid json string: " + jsonString);
		}
		return accessToken;
	}

	/**
	 * 得到授权信息
	 * 
	 * @param accessToken
	 *            授权令牌
	 * @return AccessTokenInfo
	 * @throws OAuthException
	 * @throws SDKException
	 */
	public AccessTokenInfo getTokenInfo(AccessToken accessToken) throws OAuthException, SDKException {
		List<Param> params = new LinkedList<Param>();
		params.add(new Param("access_token", accessToken.getAccess_token()));
		String jsonString = OAuthPost.post(OAuthClientConfig.URL_GET_TOKEN_INFO, params);
		AccessTokenInfo accessTokenInfo = JSONUtils.parseObject(jsonString, AccessTokenInfo.class);
		if (null == accessTokenInfo) {
			throw new SDKException("Invalid json string: " + jsonString);
		}
		return accessTokenInfo;
	}

	/**
	 * 取消授权
	 * 
	 * @param accessToken
	 *            授权令牌
	 * @return
	 * @throws OAuthException
	 * @throws SDKException
	 */
	public RevokeAccessToken revokeAccessToken(AccessToken accessToken) throws OAuthException, SDKException {
		List<Param> params = new LinkedList<Param>();
		params.add(new Param("access_token", accessToken.getAccess_token()));
		String jsonString = OAuthPost.post(OAuthClientConfig.URL_REVOKE_ACCESS_TOKEN, params);
		RevokeAccessToken revokeAccessToken = JSONUtils.parseObject(jsonString, RevokeAccessToken.class);
		if (null == revokeAccessToken) {
			throw new SDKException("Invalid json string: " + jsonString);
		}
		return revokeAccessToken;
	}
}
