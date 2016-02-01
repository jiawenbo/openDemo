package com.china.jwb.common.tools.config;
import java.io.IOException;
import java.util.Properties;

/**
 * 加载配置文件
 * 
 * @author TYW
 * 
 */
public class ConfigurationUtil {

	private static final String FILE_NAME = "/config/uploadfilepath.properties";
	private static final Properties properties = new Properties();
	static {
		try {
			properties.load(ConfigurationUtil.class.getResourceAsStream(FILE_NAME));
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}

	/**
	 * 获取配置值
	 * 
	 * @param key
	 * @return
	 */
	public static String getConfigValue(String key) {
		return properties.getProperty(key);
	}

	/**
	 * 获取配置值，支持默认值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getConfigValue(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

}