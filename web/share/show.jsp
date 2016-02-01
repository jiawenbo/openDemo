<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
 <head>
  <meta charset="UTF-8">
  <title>发布反馈-门户文稿系统页面</title>
  <link href="/webframework/css/design.style.css" rel="stylesheet" />
  <link rel="stylesheet" href="/webframework/css/jquery.fs.boxer.css">
  <script src="/webframework/js/jquery-1.11.1.min.js"></script>
  <script src="/webframework/js/jquery.form.js"></script>
  <script language="javascript" type="text/javascript">
  	function closeWin(){
  		window.close();
  	}
  </script>
 </head>
 <body>
 	<%--
  	<table>
  		<tr>
  		  <td>
  		  	 结果：
  		  </td>
  		  <td colspan="2">
  		  	 <textarea rows="20" cols="80" id="resultDiv">${result.status}</textarea>
  		  </td>
  		</tr>
  		<tr>
  		  <td>
  		  	 信息：
  		  </td>
  		  <td colspan="2">
  		  	 <textarea rows="20" cols="80" id="resultDiv">${result.msg}</textarea>
  		  </td>
  		</tr>
  		<tr>
  		  <td colspan="3" align="center">
  		  		<input type="button" value="关闭" onclick="closeWin()"/>
  		  </td>
  		</tr>
  	</table>
  	 --%>
  	
  	
  	<div class="comHeader indexcomHeader">
	  <div class="logo_location">发布反馈</div>
	</div><!--/comheader-->
  	<div class="main">
	  <div class="rightForm">
	  <c:if test ="${result.status=='sucess'}">
	  	<div class="feedback_success"><i></i>发布成功</div>
	  </c:if>
	  <c:if test ="${result.status!='sucess'}">
	  	<div class="feedback_fail"><i></i>发布失败</div>
	  </c:if>
	  <form method="post" action="#">
	    <div class="feedback_window">
	     <h2 class="title">反馈信息</h2>
	     <textarea name="words" class="feedback_window_ar">${result.msg}</textarea>
	    </div>
	    <div class="fnSubmit">
	    	<input class="common_btn" value="关闭" type="button" onclick="closeWin()"/>
	    </div>
	  </form> 
	  </div> 
	</div><!--/main-->
	<div class="footer">
	</div><!--/footer-->
 </body>
</html>
