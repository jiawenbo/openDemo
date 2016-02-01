package com.youku.uploader;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class Util {
	private Logger loger = Logger.getLogger(Util.class);
	private DefaultHttpClient httpClient;

	public Util() {
		httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
	}
	
	/**
	 * get
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public String get(String url, List<NameValuePair> params) {
		// 获取返回数据
		String result = "";
		try {
			HttpGet request = new HttpGet(url);
			String str = EntityUtils.toString(new UrlEncodedFormEntity(params, "UTF-8"));
			request.setURI(new URI(request.getURI().toString() + "?" + str));
			// 发送请求
			HttpResponse response = httpClient.execute(request);
			result = EntityUtils.toString(response.getEntity(), "UTF-8");
			result.replaceAll("\r", "");
			if (null != result) {
				EntityUtils.consume(response.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
			loger.error("get e=" + e);
		}
		return result;
	}
	
	/**
	 * post
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public String post(String url, List<NameValuePair> params) {
		String result = "";
		try {
			HttpPost request = new HttpPost(url);
			request.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			// String res = EntityUtils.toString(request.getEntity(), "UTF-8");
			// System.out.println(res);
			HttpResponse response = httpClient.execute(request);
			result = EntityUtils.toString(response.getEntity(), "UTF-8");
			result.replaceAll("\r", "");
			if (null != result) {
				EntityUtils.consume(response.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * getIp 取主机地址
	 * 
	 * @param url
	 * @return
	 */
	public String getIp(String url) {
		try {
			return InetAddress.getByName(url).getHostAddress();
		} catch (UnknownHostException e) {
			loger.error(e.getMessage(), e);
		}
		return null;
	}

	public String postFile(String url, ArrayList<NameValuePair> params, byte[] sliceBytes) {
		String result;
		HttpPost request = null;
		ByteArrayEntity bin = null;
		try {
			if (null != sliceBytes) {
				bin = new ByteArrayEntity(sliceBytes);
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
			String body = EntityUtils.toString(entity);
			request = new HttpPost(url + "?" + body);
			byte[] checkbs = EntityUtils.toByteArray(bin);
			params.add(new BasicNameValuePair("hash", FileUtil.md5(checkbs)));
			CRC32 crc = new CRC32();
			crc.update(checkbs);
			params.add(new BasicNameValuePair("crc", Long.toHexString(crc.getValue())));
			request.setEntity(bin);

			HttpResponse response = httpClient.execute(request);
			result = EntityUtils.toString(response.getEntity(), "UTF-8");

			if (null != result) {
				EntityUtils.consume(response.getEntity());
			}
			loger.info("postFile to Youku, url:" + url);
			return result;
		} catch (Exception e) {
			loger.error(e.getMessage(), e);
		} finally {
			if (null != request)
				request.abort();
		}
		return null;
	}

	public String getErrorMsg(String errorType, String description, int code) {
		return "{'error':{'type':'" + errorType + "','description':'" + description + "','code':" + code + "}}";
	}

	protected void log(String msg) {
		loger.info(msg);
	}

	protected void error(String msg) {
		loger.error(msg);
	}

	public ArrayList<NameValuePair> mapToArrayList(Map<String, String> params) {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> p : params.entrySet()) {
			pairs.add(new BasicNameValuePair(p.getKey(), p.getValue()));
		}
		return pairs;
	}

	public void shutdown() {
		httpClient.getConnectionManager().shutdown();
	}
}
