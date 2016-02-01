<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println("basePath="+basePath);
%>
<html>
  <head>
    <title></title>
    <script type="text/javascript" src="<%=basePath%>/js/jquery-1.10.2.js"></script>
    <script type="text/javascript">
    	$(function(){
    		$("#go").click(function(){
    			var url=$("#url").val();
    			var data=$("#Data").val();
    			if(data==''){data='{}'};
    			if(url!=""){
    				$.ajax({
    					type: $("#type").val(),
    					cache:false,
    					url: url,
    					data:data,
    					dataType: $("#dataType").val(),
    					headers: {
    			            "Accept":$("#Accept").val(),
    			            "Content-Type":$("#Content_Type").val()
    			        },
    					success:function(data){
    						$("#textarea").val(data+"");
    					},
    					error:function (request, textStatus, errorThrown) {
    						$("#textarea").val("状态码："+request.status+"\n内容：\n"+request.responseText);
    					}
    				});
    			}
    		});
    	});
    </script>
  </head>
  <body>
  	<table style="text-align: right;">
  		<tr>
  			<td>
  				url
  			</td>
  			<td>
  				<input type="text" id="url" value="spring/test" size="100" />
  			</td>
  		</tr>
  		<tr>
  			<td>
  				type:
  			</td>
  			<td>
  				<input type="text" id="type" value="POST" size="100"/>
  			</td>
  		</tr>
  		<tr>
  			<td>
  				dataType:
  			</td>
  			<td>
  				<input type="text" id="dataType" value="text" size="100"/>
  			</td>
  		</tr>
  		<tr>
  			<td>
  				Content-Type:
  			</td>
  			<td>
  				<input type="text" id="Content_Type" value="application/json;charset=utf-8" size="100"/>
  			</td>
  		</tr>
  		<tr>
  			<td>
  				Accept:
  			</td>
  			<td>
  				<input type="text" id="Accept" value="application/json;charset=utf-8" size="100"/>
  			</td>
  		</tr>
  		<tr>
  			<td>
  				Data:
  			</td>
  			<td>
  				<textarea id="Data" cols="100" rows="5" >{"testName":"测试中文","count":0}</textarea>
  			</td>
  		</tr>
  		<tr>
  			<td>
  			</td>
  			<td>
  					<button id="go">访问</button><button onclick='$("#textarea").val("")'>清空</button><br/>
  			</td>
  		</tr>
  		<tr>
  			<td>
  				结果:
  			</td>
  			<td>
  				<textarea rows="30" cols="100" id="textarea"></textarea><br/><br/><br/>
  			</td>
  		</tr>
  		
  	</table>
  </body>
</html>
