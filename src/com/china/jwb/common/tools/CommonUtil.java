package com.china.jwb.common.tools;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;

import com.china.jwb.common.tools.config.ConfigurationUtil;
import com.china.jwb.common.tools.img.ImgUtil;
import com.china.jwb.openplatform.entity.Article;
import com.china.jwb.openplatform.entity.TaskInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtil {

	private static final Logger logger = Logger.getLogger(CommonUtil.class);
	private final static String SECRET = ConfigurationUtil.getConfigValue("SECRET");
	private final static long OUT_TIME_BEFORE = -600000L;
	private final static long OUT_TIME_LATE = 600000L;

	/**
	 * md5加密
	 * 
	 * @param plainText
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws Exception
	 */
	public static String md5s(String plainText) {
		// 获得MD5摘要算法的 MessageDigest 对象
		MessageDigest md = null;
		StringBuffer buf = new StringBuffer("");
		try {
			md = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			md.update(plainText.getBytes());
			// 获得密文
			byte b[] = md.digest();
			int i;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return buf.toString();
	}

	public static boolean auth(String appKey, Integer randomId,
			Long currentTime, String veryCode) {
		long now = System.currentTimeMillis();
		long totalTiem = now - currentTime;
		if (OUT_TIME_BEFORE > totalTiem) {
			logger.warn("请求时间：" + currentTime + "小于服务器时间：" + now + "!");
			return false;
		} else if (OUT_TIME_LATE < totalTiem) {
			logger.warn("请求时间超时，请求时间：" + currentTime + "，服务器时间：" + now + "!");
			return false;
		}
		String strmd5 = MD5FileUtil.getMD5(appKey + randomId + currentTime
				+ SECRET);
		if (veryCode.equalsIgnoreCase(strmd5)) {
			return true;
		} else {
			logger.warn("传递的veryCode值：" + veryCode + "与服务器获取的veryCode值:"
					+ strmd5 + "不一致,验证不通过");
			return false;
		}
	}

	public static JSONArray Json2JSONArray(String s) {
		JSONArray array = null;
		try {
			array = new JSONArray(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}

	/**
	 * json转Map
	 * 
	 * @param json
	 * @return
	 */
	public static boolean authTaskInfo(TaskInfo taskInfo) {
		if (null == taskInfo) {
			return false;
		}
		/*
		 * if(null == taskInfo.getResUrl() || "".equals(taskInfo.getResUrl())){
		 * return false; }
		 */
		if (null == taskInfo.getTargetSite()
				|| "".equals(taskInfo.getTargetSite())) {
			return false;
		}
		if (null == taskInfo.getResName() || "".equals(taskInfo.getResName())) {
			return false;
		}
		if (null == taskInfo.getResType() || "".equals(taskInfo.getResType())) {
			return false;
		}
		if (null == taskInfo.getpId() || "".equals(taskInfo.getpId())) {
			return false;
		}

		return true;
	}

	/**
	 * 验证CMS是否合法
	 * 
	 * @param json
	 * @return
	 */
	public static boolean authCMSTaskInfo(TaskInfo taskInfo) {
		if (null == taskInfo) {
			return false;
		}
		if (null == taskInfo.getTargetSite()
				|| "".equals(taskInfo.getTargetSite())) {
			return false;
		}
		if (null == taskInfo.getResName() || "".equals(taskInfo.getResName())) {
			return false;
		}
		if (null == taskInfo.getpId() || "".equals(taskInfo.getpId())) {
			return false;
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> readJSON2Map(String json) {
		Map<String, Object> map = null;
		if (null != json && !"".equals(json)) {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				map = objectMapper.readValue(json, Map.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	public static String uploudUrl(TaskInfo taskInfo) {
		FileOutputStream fos = null;
		InputStream input = null;
		String sourceUrl = taskInfo.getResUrl();

		String tempFile = null;
		if (sourceUrl.substring(0, 4).equalsIgnoreCase("http")
				|| sourceUrl.substring(0, 3).equalsIgnoreCase("ftp")) {
			URL url = null;
			URLConnection urlconn = null;
			logger.info("视频或者图片开始下载");
			try {
				System.out.println("转换前==" + sourceUrl);
				sourceUrl = StringUtils.getChineseUrl(sourceUrl);
				System.out.println("转换后==" + sourceUrl);
				url = new URL(sourceUrl);
				urlconn = url.openConnection();
				input = urlconn.getInputStream();
			} catch (MalformedURLException e) {
				logger.info("视频或图片地址有误");
				e.printStackTrace();
				return null;
			} catch (UnsupportedEncodingException e) {
				logger.info("中文路径转码失败");
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				logger.info("读取视频或图片错误");
				e.printStackTrace();
				return null;
			}
			String fileName = taskInfo.getId()
					+ sourceUrl.substring(sourceUrl.lastIndexOf('.'), sourceUrl
							.length());
			tempFile = ConfigurationUtil
					.getConfigValue(OdpConstants.TEMP_FILE_PATH)
					+ fileName;
			ImgUtil.mkdirPath(tempFile);
			byte[] buf = new byte[2048];
			int size = 0;
			try {
				fos = new FileOutputStream(tempFile);
				while ((size = input.read(buf)) != -1) {
					fos.write(buf, 0, size);
				}
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				logger.info("视频或者图片下载完成");
			}
		} else {
			tempFile = taskInfo.getResUrl();

		}
		return tempFile;
	}

	/**
	 * 上传到临时目录，返回值
	 * 
	 * @param sourceUrl
	 *            网络路径
	 * @return
	 */
	public static String uploudUrl(String sourceUrl) {
		FileOutputStream fos = null;
		InputStream input = null;
		String tempFile = null;
		if (sourceUrl.substring(0, 4).equalsIgnoreCase("http")
				|| sourceUrl.substring(0, 3).equalsIgnoreCase("ftp")) {
			URL url = null;
			URLConnection urlconn = null;
			logger.info("视频或者图片开始下载");
			try {
				System.out.println("转换前==" + sourceUrl);
				sourceUrl = StringUtils.getChineseUrl(sourceUrl);
				System.out.println("转换后==" + sourceUrl);
				url = new URL(sourceUrl);
				urlconn = url.openConnection();
				input = urlconn.getInputStream();
			} catch (MalformedURLException e) {
				logger.info("视频或图片地址有误");
				e.printStackTrace();
				return null;
			} catch (UnsupportedEncodingException e) {
				logger.info("中文路径转码失败");
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				logger.info("读取视频或图片错误");
				e.printStackTrace();
				return null;
			}
			String fileName = UUID.randomUUID()
					+ sourceUrl.substring(sourceUrl.lastIndexOf('.'), sourceUrl
							.length());
			tempFile = ConfigurationUtil
					.getConfigValue(OdpConstants.TEMP_FILE_PATH)
					+ fileName;
			ImgUtil.mkdirPath(tempFile);
			byte[] buf = new byte[2048];
			int size = 0;
			try {
				fos = new FileOutputStream(tempFile);
				while ((size = input.read(buf)) != -1) {
					fos.write(buf, 0, size);
				}
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				logger.info("视频或者图片下载完成");
			}
		} else {
			tempFile = sourceUrl;

		}
		return tempFile;
	}

	public static Article[] myArticLes(String pram) {
		// 把articles参数转换为数组对象.
		Article[] myArticles = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < Json2JSONArray(pram).length(); i++) {
			Map<String, Object> map = null;
			try {
				map = readJSON2Map(Json2JSONArray(pram).get(i).toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			list.add(map);
			myArticles = new Article[list.size()];
			pListMap(list, myArticles);
		}
		return myArticles;
	}

	public static void main(String[] args) {
		String ces = "Duang!一下年就过完了，不过没到正月十五这个年就还没完。虽然晚了点儿，但小编还是借着羊年的喜气儿给大家拜年啦！/r"
				+ "祝各路大侠新的一年喜气 羊羊万事吉祥！"
				+ "新年新气象，小编也向大家汇报一下这段时间云视的动态吧。这段时间云视很忙，真的很忙！一起来看看我们的战果："
				+ "Duang1：嘉峪关网络电视台签约上线ONAIR 云平台，目前已完成布署，上线运行。"
				+ "Duang2: 安顺市广播电视台网络电视台签约上线ONAIR 云平台，目前正在布署当中。"
				+ "捷迅网络电视台是新奥特云视帮助传统电视台应对互联网发展大趋势推出的一款成熟产品，将传统的通过广播电视网播出方式拓展为通过互联网播出，将播出屏幕延伸至PC、手机、PAD等多终端，将单一功能转变为分享、评论、微信、订阅等多形式互动。"
				+ "Duang3: 湖南经视两会报道微信电视台上线运营"
				+ "Duang4：榆树台微信电视台项目上线运营"
				+ "炫云微电视是新奥特云视推出的基于微信平台的终端应用产品，以专业化的音视频处理技术及灵活快捷的资源管理发布系统为基础，结合丰富多彩的微信展示页面和多屏互动功能，为电视台、网络新媒体等用户提供微信管理、发布、展现等推广运营服务。"
				+ "Duang5：江苏广电总台两会报道新闻云平台上线了"
				+ "两会会议期间，新奥特云视为江苏广电总台两会报道保驾护航，快速搭建新闻云平台，实现两会新闻内容的海量汇聚，全媒体新闻发布以及多渠道融合、多平台分发。充分利用云资源即开即用，弹性布署的特点，使新闻内容能够同步报道、实时推出。"
				+ "时代性的企业，一定不是单纯卖货的企业，而是服务、引领社会以及行业转型，促进产业革命的企业，小编相信，我们正走在这样的一条路上！";
		String ee = "1234567890";
		if (ee.length() >= 10) {
			String aa = ee.substring(0, 9);
			System.out.println(aa);
		}

	}

	/**
	 * 根据传递过来的List转换为Article,图文数组.转换完毕并返回转换成功与否的结果.
	 * 
	 * @param list
	 * @param articles
	 * @return
	 */
	public static void pListMap(List<Map<String, Object>> list,
			Article[] articles) {

		for (int i = 0; i < list.size(); i++) {
			Article article = new Article();
			article.setAuthor(list.get(i).get("author").toString());
			article.setContent(list.get(i).get("content").toString());
			article.setContentSourceUrl(list.get(i).get("content_source_url")
					.toString());
			article.setDigest(list.get(i).get("digest").toString());
			article.setShowCoverPic(list.get(i).get("show_cover_pic")
					.toString());
			article.setThurmbMediaUrl(list.get(i).get("thumb_media_url")
					.toString());
			article.setTitle(list.get(i).get("title").toString());

			articles[i] = article;
		}
	}

	public static String buildPicTextUploadJson(Article[] articles) {

		StringBuffer sb = new StringBuffer(" {\"articles\": [");
		for (int i = 0; i < articles.length; i++) {
			if (i == articles.length - 1) {
				sb.append(" {");
				sb.append(" \"thumb_media_id\":").append(
						"\"" + articles[i].getThurmbMediaId() + "\"").append(
						",");
				if (articles[i].getAuthor() != null
						&& !"".equals(articles[i].getAuthor()))
					;
				sb.append(" \"author\":").append(
						"\"" + articles[i].getAuthor() + "\"").append(",");
				sb.append(" \"title\":").append(
						"\"" + articles[i].getTitle() + "\"").append(",");
				if (articles[i].getContentSourceUrl() != null
						&& !"".equals(articles[i].getContentSourceUrl()))
					sb.append(" \"content_source_url\":").append(
							"\"" + articles[i].getContentSourceUrl() + "\"")
							.append(",");
				sb.append(" \"content\":").append(
						"\"" + articles[i].getContent() + "\"").append(",");
				sb.append(" \"digest\":").append(
						"\"" + articles[i].getDigest() + "\"").append(",");
				sb.append(" \"show_cover_pic\":").append(
						"\"" + articles[i].getShowCoverPic() + "\"");
				sb.append(" }");
			} else {
				sb.append(" {");
				sb.append(" \"thumb_media_id\":").append(
						"\"" + articles[i].getThurmbMediaId() + "\"").append(
						",");
				sb.append(" \"author\":").append(
						"\"" + articles[i].getAuthor() + "\"").append(",");
				sb.append(" \"title\":").append(
						"\"" + articles[i].getTitle() + "\"").append(",");
				if (articles[i].getContentSourceUrl() != null
						&& !"".equals(articles[i].getContentSourceUrl()))
					sb.append(" \"content_source_url\":").append(
							"\"" + articles[i].getContentSourceUrl() + "\"")
							.append(",");
				sb.append(" \"content\":").append(
						"\"" + articles[i].getContent() + "\"").append(",");
				sb.append(" \"digest\":").append(
						"\"" + articles[i].getDigest() + "\"").append(",");
				sb.append(" \"show_cover_pic\":").append(
						"\"" + articles[i].getShowCoverPic() + "\"");
				sb.append(" },");
			}

		}

		sb.append(" ]}");

		return sb.toString();

	}

	/**
	 * 拼接群发图文的json串 .
	 * 
	 * @param mediaId
	 * @param follwersGroup
	 * @param map
	 * @return
	 */
	public static String buildPicTextDistributeJson(String mediaId,
			JSONArray follwersGroup) {

		StringBuffer sb = new StringBuffer("{\"touser\":[");
		// System.out.println(follwersGroup);
		if (follwersGroup != null) {
			for (int i = 0; i < follwersGroup.length(); i++) {
				if (i == follwersGroup.length() - 1) {
					try {
						sb.append("\"" + follwersGroup.get(i) + "\" ],");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					try {
						sb.append("\"" + follwersGroup.get(i) + "\",");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			logger.info("获取关注着列表错误-----");
			return "error";
		}
		sb.append("\"mpnews\":{");
		sb.append("\"media_id\":");
		sb.append("\"" + mediaId + "\"");
		sb.append(" },");
		sb.append("\"msgtype\":\"mpnews\"");
		sb.append("}");

		return sb.toString();
	}

	public static String buildTextDistributeJson(JSONArray follwersGroup,
			String desc) {

		StringBuffer sb = new StringBuffer("{\"touser\":[");
		// System.out.println(follwersGroup);
		if (follwersGroup != null) {
			for (int i = 0; i < follwersGroup.length(); i++) {
				if (i == follwersGroup.length() - 1) {
					try {
						sb.append("\"" + follwersGroup.get(i) + "\" ],");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					try {
						sb.append("\"" + follwersGroup.get(i) + "\",");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			logger.info("获取关注着列表错误-----");
			return "error";
		}
		sb.append("\"text\":{");
		sb.append("\"content\":");
		sb.append("\"" + desc + "\"");
		sb.append(" },");
		sb.append("\"msgtype\":\"text\"");
		sb.append("}");

		return sb.toString();
	}

	/**
	 * 拼接群发视频的json串 .
	 * 
	 * @param mediaId
	 * @param follwersGroup
	 * @param map
	 * @return
	 */
	public static String buildDistributeJson(String mediaId,
			JSONArray follwersGroup, Map<String, String> map) {
		StringBuffer sb = new StringBuffer("{\"touser\":[");
		if (follwersGroup != null) {
			for (int i = 0; i < follwersGroup.length(); i++) {
				if (i == follwersGroup.length() - 1) {
					try {
						sb.append("\"" + follwersGroup.get(i).toString()
								+ "\" ],");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					try {
						sb.append("\"" + follwersGroup.get(i).toString()
								+ "\",");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			logger.info("获取关注着列表错误-----");
			return "error";
		}
		sb.append(" \"video\":{");
		sb.append("\"media_id\":");
		sb.append("\"" + mediaId + "\",");
		String groupMediaTitle = map.get("title");
		String grouopMediaDes = map.get("description");
		sb.append("\"title\":\"" + groupMediaTitle + "\",");
		sb.append("\"description\":\"" + grouopMediaDes + "\"");
		sb.append(" },");
		sb.append("\"msgtype\":\"video\"");
		sb.append("}");
		logger.info("拼接的json,调用群发---json串为-----" + sb.toString());
		return sb.toString();
	}
}
