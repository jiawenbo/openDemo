package com.china.jwb.common.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;


public class StringUtils {
	
	
	//HTML正则
	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; 	// 定义script的正则表达式
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; 		// 定义style的正则表达式
    private static final String regEx_html = "<[^>]+>"; 								// 定义HTML标签的正则表达式
    private static final String regEx_space = "\\s*|\t|\r|\n";							//定义空格回车换行符
    
	/**
	 * 带默认值的获取方法
	 * **/
	public static String getRequestParameter(HttpServletRequest request,String name,String defaultValue){
		String parame = request.getParameter(name);
		if(parame==null||"".equals(parame)){
			parame = defaultValue;
		}
		return parame;
	}
	/**
	 * attribute
	 * 带默认值的获取方法
	 * **/
	public static String getRequestAttributeString(HttpServletRequest request,String name,String defaultValue){
		String attribute = (String)request.getAttribute(name);
		if(attribute==null||"".equals(attribute)){
			attribute = defaultValue;
		}
		return attribute;
	}
	
	/**
	 * 转字符串方法（待完善）
	 * **/
	public static String toString2(Object obj,String defaultValue){
		String str = "";
		if(obj!=null){
			str = obj.toString();
		}else{
			str = defaultValue;
		}
		return str;
	}
	/** 
     * 判断str1中包含str2的个数 
      * @param str1 
     * @param str2 
     * @return counter 
     */  
    public static int containCount(String str1, String str2) {  
        int  counter = 0;
    	if (str1.indexOf(str2) == -1) {  
            return 0;  
        } else if (str1.indexOf(str2) != -1) {
            counter++;  
            containCount(str1.substring(str1.indexOf(str2) + str2.length()), str2);  
            return counter;  
        }  
        return 0;  
    } 
	
	
	/***
	 * 对输入项HTML转义
	 * @return
	 */
	public static String getHtmlStr(String content){
		String str = "";
		str = content.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		return str;
	}
    
    /**
     * @param htmlStr
     * @return
     *  删除HTML标签
     */
    public static String delHTMLTag(String htmlStr) {
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签

        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
        return htmlStr.trim(); // 返回文本字符串
    }
    
    /***
     * 从HTML中得到实际内容
     * @param htmlStr
     * @return
     */
    public static String getTextFromHtml(String htmlStr){
    	if(htmlStr==null){
    		htmlStr = "";
    	}
    	htmlStr = delHTMLTag(htmlStr);
    	htmlStr = htmlStr.replaceAll("&nbsp;", " ");
    	return htmlStr;
    }
    /***
     * 获取中文转码后的URL
     * @param chineseUrl
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getChineseUrl(String chineseUrl) throws UnsupportedEncodingException{
		String url = "";
		String tihuanStr = "!";
		if(chineseUrl.getBytes().length == chineseUrl.length()){
			url = chineseUrl;
		}else{
	    	String reg = "[\u4e00-\u9fa5]";
	    	Pattern pat = Pattern.compile(reg);  
	    	Matcher mat=pat.matcher(chineseUrl); 
	    	String repickStr = mat.replaceAll(tihuanStr);
	        StringBuffer sb = new StringBuffer();
	        List<String> oldList = new ArrayList<String>();
	        for (int i = 0; i < chineseUrl.length(); i++) {
	            if ((chineseUrl.charAt(i)+"").getBytes().length>1) {
	                sb.append(chineseUrl.charAt(i));
	            }
	        }
	        String sbString = sb.toString();
	        for(int i=0;i<sbString.length();i++){
	        	oldList.add(sbString.substring(i, i+1));
	        	String hanzi = sbString.substring(i, i+1);
	        	hanzi = URLEncoder.encode(hanzi,"utf-8");
	        	repickStr = repickStr.replaceFirst(tihuanStr, hanzi);
	        }
	        url = repickStr;
		}
		return url;
		
	}
  //判断不为空
	public static boolean isNotNull(Object o){
		boolean flag = true;
		if(o == null){
			flag = false;
		}
		if("".equals(o)){
			flag = false;
		}
		return flag;
	}
    
    public static void main(String[] args) {
		
	}
}
