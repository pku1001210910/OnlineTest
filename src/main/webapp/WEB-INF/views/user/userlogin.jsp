<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
	
<script type="text/javascript">
	        function reg()
	        {
	                var url="<%=path %>/user/userRegPage.action";
	                var n="";
	                var w="480px";
	                var h="500px";
	                var s="resizable:no;help:no;status:no;scroll:yes";
				    openWin(url,n,w,h,s);
	        }
	        function login()
	        {
	           if(document.userLogin.userName.value=="")
	           {
	               alert("请输入用户名");
	               return;
	           }
	           if(document.userLogin.userPw.value=="")
	           {
	               alert("请输入密码");
	               return;
	           }
	           document.userLogin.submit();
	        }
	       
	</script>
</head>

<body>
	<s:if test="#session.user_session==null">
		<form action="<%=basePath%>user/userLogin.action"
			class="navbar-form navbar-right" name="userLogin" method="post">
			<div class="form-group">
				<font color="red"><s:property value="#request.error" /></font>
			</div>
			<div class="form-group">
				<input name="userName" type="text" placeholder="用户名"
					class="form-control">
			</div>
			<div class="form-group">
				<input type="password" name="userPw" placeholder="密码"
					class="form-control">
			</div>
			<div class="form-group">
				<button type="submit" class="btn btn-success" onclick="login()">登陆</button>
			</div>
			<div class="form-group">
				<a data-target="#regModal" data-toggle="modal" class="white underline" href="#"> 没有账号？</a>
			</div>
		</form>
	</s:if>
	<s:else>
		<form class="navbar-form navbar-right user-info">
			<div class="form-group">
				欢迎您：
				<s:if test="#session.user_session!=null">
					<s:property value="#session.user_session.userName" />
				</s:if>
			</div>
			<div class="form-group">
				<a class="white underline left-15" href="<%=basePath %>user/userLogout.action">退出</a>
			</div>
			<div class="form-group">
				<span class="white separator">|</span>
			</div>
			<div class="form-group">
				<a class="white underline" href="<%=basePath %>user/home.action">个人中心</a>
			</div>
		</form>
	</s:else>
</body>
</html>
