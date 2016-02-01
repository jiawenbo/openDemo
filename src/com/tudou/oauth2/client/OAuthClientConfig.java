package com.tudou.oauth2.client;

import com.tudou.oauth2.core.OAuthConfig;

/**
 * 配置类
 * 
 * @author yaoming
 */
public class OAuthClientConfig extends OAuthConfig {

	public static final String APP_KEY;
	public static final String APP_SECRET;
	public static final String REDIRECT_URI;
	public static final String DISPLAY;

	public static final String URL_AUTHORIZE;
	public static final String URL_ACCESS_TOKEN;
	public static final String URL_GET_TOKEN_INFO;
	public static final String URL_REVOKE_ACCESS_TOKEN;

	static {
		APP_KEY = OAuthConfig.config.getString("oauth2.app_key");
		APP_SECRET = OAuthConfig.config.getString("oauth2.app_secret");
		REDIRECT_URI = OAuthConfig.config.getString("oauth2.redirect_uri");
		DISPLAY = OAuthConfig.config.getString("oauth2.display");

		URL_AUTHORIZE = OAuthConfig.config.getString("oauth2.urls.authorize");
		URL_ACCESS_TOKEN = OAuthConfig.config.getString("oauth2.urls.access_token");
		URL_GET_TOKEN_INFO = OAuthConfig.config.getString("oauth2.urls.get_token_info");
		URL_REVOKE_ACCESS_TOKEN = OAuthConfig.config.getString("oauth2.urls.revoke_access_token");
	}

}
