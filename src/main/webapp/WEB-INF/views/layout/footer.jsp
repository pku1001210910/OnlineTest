<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
  
  <table width="600" border="0" cellspacing="1" cellpadding="0" align="center">
  <tr>
    <td align="center"><br><br>关于我们|联系我们|<a target="_blank" href="<%=path %>/login.jsp">管理登录</a>
			<br /><br />
			电话：023-10101010 传真：023-1010101
			<br /></td>
  </tr>
</table>
        <div class="foot_pic"></div>
		<div class="left foot_msg" style="">
			
		</div>
		<div style="clear: both"></div>
  </body>
</html>
