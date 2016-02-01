package com.china.jwb.common.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * HTTP请求工具
 * @author jiawenbo
 *
 */
public class YsHttpUtil {
	private static final Logger logger = Logger.getLogger(YsHttpUtil.class);
	private final static String DEFAULT_CHARSET = "UTF-8";

	/**
	 * 上传文件
	 * 
	 * @param httpUrl
	 *            文件上传地址
	 * @param params
	 *            参数
	 * @param filepath
	 *            文件路径
	 * @return
	 */
	public static String doPost(String httpUrl, Map<String, Object> params,
			String filetype, String filepath) {
		HttpPost httpPost = new HttpPost(httpUrl);
		httpPost.setEntity(createMultipartEntity(params, filepath, filetype));
		return sendRequest(httpPost);
	}

	/**
	 * 上传文件
	 * 
	 * @param httpUrl
	 *            文件上传地址
	 * @param params
	 *            参数只一个
	 * @param filepath
	 *            文件路径
	 * @return
	 */
	public static String doPostOne(String httpUrl, String filetype,
			String filepath) {
		HttpPost httpPost = new HttpPost(httpUrl);
		httpPost.setEntity(createMultipartEntityOne(filepath, filetype));
		return sendRequest(httpPost);
	}

	/**
	 * post请求
	 * 
	 * @param url
	 *            地址
	 * @return
	 */
	public static String doPost(String url) {
		HttpPost httpPost = new HttpPost(url);
		return sendRequest(httpPost);
	}

	public static String doPost(String url, Map<String, Object> params) {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(createEntity(params));
		return sendRequest(httpPost);
	}

	private static MultipartEntity createMultipartEntity(
			Map<String, Object> params, String filePath, String filetype) {

		MultipartEntity mutiEntity = new MultipartEntity();
		Set<String> keys = params.keySet();
		Iterator<String> iterator = keys.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			Object value = params.get(key);
			try {
				mutiEntity.addPart(key, new StringBody(value.toString(),
						Charset.forName(DEFAULT_CHARSET)));
			} catch (UnsupportedEncodingException e) {
				throw new YsUtilException(e.getMessage());
			}
		}
		File file = new File(filePath);
		mutiEntity.addPart(filetype, new FileBody(file));
		return mutiEntity;
	}

	private static MultipartEntity createMultipartEntityOne(String filePath,
			String filetype) {

		MultipartEntity mutiEntity = new MultipartEntity();

		File file = new File(filePath);
		mutiEntity.addPart(filetype, new FileBody(file));
		return mutiEntity;
	}

	private static UrlEncodedFormEntity createEntity(Map<String, Object> params) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		Set<String> keys = params.keySet();
		Iterator<String> iterator = keys.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			list.add(new BasicNameValuePair(key, params.get(key).toString()));
		}
		UrlEncodedFormEntity encodedFormEntity = null;
		try {
			encodedFormEntity = new UrlEncodedFormEntity(list, DEFAULT_CHARSET);
			return encodedFormEntity;
		} catch (UnsupportedEncodingException e) {
			throw new YsUtilException(e.getMessage());
		}
	}

	public static String doGet(String url, Map<String, Object> params) {
		HttpGet httpGet = new HttpGet(getUri(url, params));
		return sendRequest(httpGet);
	}

	public static String doGet(String url) {
		HttpGet httpGet = new HttpGet(url);
		return sendRequest(httpGet);
	}

	public static String getUri(String url, Map<String, Object> params) {
		return url + getParameters(params);
	}

	private static String getParameters(Map<String, Object> params) {
		StringBuilder buf = new StringBuilder();
		Set<String> keys = params.keySet();
		Iterator<String> iterator = keys.iterator();
		int num = 0;
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = params.get(key).toString();
			if (num++ == 0) {
				buf.append("?");
			} else {
				buf.append("&");
			}
			try {
				buf.append(URLEncoder.encode(key, DEFAULT_CHARSET)).append("=")
						.append(
								URLEncoder.encode(value, DEFAULT_CHARSET)
										.replace("+", "%20"));
			} catch (UnsupportedEncodingException e) {
				throw new YsUtilException(e.getMessage());
			}
		}
		return buf.toString();
	}

	private static String sendRequest(HttpUriRequest httpUriRequest) {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpUriRequest);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				if (null != entity) {
					return EntityUtils.toString(entity, DEFAULT_CHARSET);
				}
			}
			throw new YsUtilException();
		} catch (ClientProtocolException e) {
			throw new YsUtilException(e.getMessage());
		} catch (IOException e) {
			throw new YsUtilException(e.getMessage());
		} finally {
			if (null != response) {
				try {
					response.close();
				} catch (IOException e) {
					throw new YsUtilException(e.getMessage());
				}
			}
		}
	}

	public static String getMaterialMediaId(String url, String json) {

		// 调用接口得到群发视频的mediaId.
		String jsonObject = httpRequest(url, "POST", json);

		return jsonObject;
	}

	public static String httpRequest(String requestUrl, String requestMethod,
			String outputStr) {
		String jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			if (requestMethod.equals("POST") || requestMethod.equals("post")) {
				// xinghaifang add 2014年10月29日 信任所有的htts证书.
				System.setProperty("java.protocol.handler.pkgs",
						"javax.net.ssl");
				System.setProperty("jsse.enableSNIExtension", "false");
				HostnameVerifier hv = new HostnameVerifier() {
					public boolean verify(String urlHostName, SSLSession session) {
						return urlHostName.equals(session.getPeerHost());
					}
				};
				HttpsURLConnection.setDefaultHostnameVerifier(hv);
				// 上面 trust all certificate
			}

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpUrlConn.connect();
			}

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			// logger.info("返回结果列表信息为：" + buffer.toString());
			jsonObject = buffer.toString();
		} catch (ConnectException ce) {
			logger.error("Weixin server connection timed out.");
		} catch (Exception e) {
			logger.error("https request error:{" + e + "}");
		}
		return jsonObject;
	}
}
