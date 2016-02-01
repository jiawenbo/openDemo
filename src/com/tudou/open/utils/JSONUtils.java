package com.tudou.open.utils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import yao.util.json.JSONUtil;

/**
 * json工具类
 * 
 * @author yao
 */
public class JSONUtils {

	private static final JSONUtil jsonUtil = new JSONUtil();

	/**
	 * 将对象转换成json字符串
	 * 
	 * @param o
	 *            对象支持Collection及array
	 * @return jsonString
	 */
	public static String toJsonString(Object o) {
		if (null == o) {
			return null;
		}
		return jsonUtil.parserString(o);
	}

	/**
	 * 将json字符串转换成指定类的对象
	 * 
	 * @param jsonString
	 *            json字符串
	 * @param clazz
	 *            对象类型
	 * @return Object
	 */
	public static <T> T parseObject(String jsonString, Class<T> objectClass) {
		return jsonUtil.parserObject(jsonString, objectClass);
	}

	/**
	 * 将json字符串转换成Map
	 * 
	 * @param jsonString
	 * @param mapType
	 * @return
	 */
	public static Map parseMap(String jsonString, Class<? extends Map> mapType) {
		return jsonUtil.parseMap(jsonString, mapType);
	}

	/**
	 * 将json字符串转换成指定类的对象集合，支持(? extends Collection)
	 * 
	 * @param jsonString
	 *            json字符串
	 * @param objectClass
	 *            对象类型
	 * @return
	 */
	public static <T> List<T> parseList(String jsonString, Class<T> objectClass) {
		return (List<T>) parseCollection(jsonString, objectClass, LinkedList.class);
	}

	/**
	 * 将json字符串转换成指定类的对象集合，支持(? extends Collection)
	 * 
	 * @param <T>
	 * @param jsonString
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Collection<T> parseCollection(String jsonString, Class<T> objectClass, Class<? extends Collection> collectionType) {
		return jsonUtil.parseCollection(jsonString, objectClass, collectionType);
	}
}
