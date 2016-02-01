package com.china.jwb.api;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.china.jwb.common.tools.StringUtils;

/**
 * 回调方法
 * @author jiawenbo
 *
 */
public class ApiServlet extends HttpServlet {

	public static final String CALLBACK_YK 		= "CALLBACK_YK";
	public static final String CALLBACK_TD		= "CALLBACK_TD";
	public static final String CALLBACK_WB		= "CALLBACK_WB";
	public static final String CALLBACK_WX		= "CALLBACK_WX";
	
	/**
	 * Constructor of the object.
	 */
	public ApiServlet() {
		super();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	
	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		process(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		process(request, response);
//		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
//		out.println("<HTML>");
//		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
//		out.println("  <BODY>");
//		out.print("    This is ");
//		out.print(this.getClass());
//		out.println(", using the POST method");
//		out.println("  </BODY>");
//		out.println("</HTML>");
//		out.flush();
//		out.close();
	}

	

	/***
	 * 涓昏皟鐢ㄦ柟娉�
	 * @param request
	 * @param response
	 */
	private void process(HttpServletRequest request,HttpServletResponse response) {
		String processID=request.getParameter("processID");
		String sessionID = request.getSession().getId();
		System.out.println("sessionID=="+sessionID);
		System.out.println("processID=="+processID);
		System.out.println("newsServlet ... ... ");
		if(CALLBACK_YK.equals(processID)){
			callBack_YK(request, response);
			return ;
		}else if(CALLBACK_TD.equals(processID)){
			callBack_TD(request, response);
			return ;
		}else if(CALLBACK_WB.equals(processID)){
			callBack_WB(request, response);
			return ;
		}else if(CALLBACK_WX.equals(processID)){
			callBack_WX(request, response);
			return ;
		}
	}
	/***
	 * 杈撳嚭鍓嶅彴
	 * @param result
	 * @param response
	 */
	public void printResult(String result,HttpServletResponse response){
		try
		{
			response.setHeader("Pragma", "No-cache");
			response.setDateHeader("Expires", 0);
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(result);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/***
	 * 杈撳嚭request涓唴瀹�
	 * @param request
	 */
	public JSONObject printAllParameter(HttpServletRequest request){
		JSONObject json =  new JSONObject();
		request.getParameterNames();  
		Enumeration enu=request.getParameterNames();  
		while(enu.hasMoreElements()){  
			String paraName=(String)enu.nextElement();
			String value = StringUtils.getRequestParameter(request, paraName, "");
			System.out.println(paraName+": "+value);  
			json.put(paraName, value);
		}
		if (json!=null) {
			System.out.println(json.toString());
		} else {
			System.out.println("绌哄�");
		}
		return json;
	}
	
	/**
	 * 浼橀叿
	 * @param request
	 * @param response
	 */
	public void callBack_YK(HttpServletRequest request,HttpServletResponse response){
		JSONObject json = printAllParameter(request);
		printResult(json.toJSONString(), response);
	}
	/**
	 * 鍦熻眴
	 * @param request
	 * @param response
	 */
	public void callBack_TD(HttpServletRequest request,HttpServletResponse response){
		JSONObject json = printAllParameter(request);
		printResult(json.toJSONString(), response);
	}
	/**
	 * 寰崥
	 * @param request
	 * @param response
	 */
	public void callBack_WB(HttpServletRequest request,HttpServletResponse response){
		JSONObject json = printAllParameter(request);
		printResult(json.toJSONString(), response);
	}
	/**
	 * 寰俊
	 * @param request
	 * @param response
	 */
	public void callBack_WX(HttpServletRequest request,HttpServletResponse response){
		JSONObject json = printAllParameter(request);
		printResult(json.toJSONString(), response);
	}
	
	
	
	
}
