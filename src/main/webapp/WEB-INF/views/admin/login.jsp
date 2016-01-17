<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>在线测试系统后台</title>

<!-- core CSS -->
<link href="<%=path%>/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/css/admin/login.css" rel="stylesheet">
<link href="<%=path%>/css/main.css" rel="stylesheet">
</head>

<body>

	<div class="container">
		<form class="form-signin" action="<%=path%>/admin/auth.action">
			<h2 class="form-signin-heading">在线测试系统后台</h2>
			<s:if test="hasActionErrors()">
				<div class="bg-danger">
					<s:iterator value="actionErrors">
						<p>
							<s:property />
						</p>
					</s:iterator>
				</div>
			</s:if>
			<label for="username" class="sr-only">用户名</label> <input
				id="username" class="form-control" placeholder="用户名" required
				autofocus> <label for="password" class="sr-only">密码</label>
			<input type="password" id="password" class="form-control"
				placeholder="密码" required>
			<div class="checkbox">
				<label> <input type="checkbox" value="remember-me">保持登陆</input>
				</label>
			</div>
			<button class="btn btn-lg btn-primary btn-block" type="submit">登陆</button>
		</form>
	</div>
	<!-- /container -->


	<!-- JavaScript -->
	<script src="<%=path%>/js/jquery.min.js"></script>
	<script src="<%=path%>/js/bootstrap.min.js"></script>
</body>
</html>
