<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>在线测试系统</title>

<!-- core CSS -->
<link href="<%=path%>/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/css/font-awesome/css/font-awesome.min.css" rel="stylesheet">
<link href="<%=path%>/css/admin/home.css" rel="stylesheet">
<link href="<%=path%>/css/main.css" rel="stylesheet">

<!-- user registration -->
<link href="<%=path%>/css/reg/form-elements.css" rel="stylesheet">
<link href="<%=path%>/css/reg/style.css" rel="stylesheet">

<!-- support ie8 & ie9 & ie10 -->
<script src="<%=path%>/js/html5shiv.js" type="text/javascript"></script>
<script src="<%=path%>/js/respond.min.js" type="text/javascript"></script>
<script src="<%=path%>/js/ie10-viewport-bug-workaround.js" type="text/javascript"></script>
<script src="<%=path%>/js/ie-elements.js" type="text/javascript"></script>
</head>
<body>
	<nav class="navbar bg-primary navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand white" href="#"> 北京大学心理系 </a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<jsp:include flush="true" page="user/userlogin.jsp"></jsp:include>
			</div>
		</div>
	</nav>

	<!-- Main jumbotron for a primary marketing message or call to action -->
	<div class="jumbotron">
		<div class="container">
			<h1>在线测试系统</h1>
			<p>本系统为心理在线测试平台，可以阅读管理员上传的文章和参加测试。</p>
		</div>
	</div>

	<div class="container">
		<!-- Example row of columns -->
		<div class="row">
			<div class="col-md-6">
				<h2>文章模块</h2>
				<p>请在这里填写文章模块的背景、作用</p>
				<p>
					<a class="btn btn-default" href="#" role="button">查看文章 &raquo;</a>
				</p>
			</div>
			<div class="col-md-6">
				<h2>测试模块</h2>
				<p>请在这里填写测试模块的背景、作用</p>
				<p>
					<a class="btn btn-default" href="#" role="button">参加测试 &raquo;</a>
				</p>
			</div>
		</div>

		<hr>

		<footer>
			<div>
				<a>关于我们</a>| <a>联系我们</a>| <a>版权声明</a>| <a>隐私保护</a>| <a
					href="<%=path%>/admin/login.action">管理后台</a>|
			</div>
		</footer>
	</div>
	<!-- /container -->
	
	<!-- user registration modal -->
	<div id="regModal"" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-body">
	                <div class="row">
	                    <div class="form-box">
	                        <div class="form-top">
	                            <div class="form-top-left">
	                                <h3>用户注册</h3>
	                                <p>请填写个人详细信息:</p>
	                            </div>
	                            <div class="form-top-right">
	                                <i class="fa fa-lock"></i>
	                            </div>
	                        </div>
	                        <div class="form-bottom">
	                            <form role="form" action="<%=basePath%>user/userReg.action" method="post" class="login-form">
	                                <div class="form-group">
	                                    <input type="text" name="userName" placeholder="用户名" class="form-username form-control" id="form-username">
	                                </div>
	                                <div class="form-group">
	                                    <input type="password" name="userPw" placeholder="密码" class="form-password form-control" id="form-password">
	                                </div>
	                                <div class="form-group">
	                                    <input type="password" name="passwordConfirm" placeholder="密码确认" class="form-password form-control" id="form-password-confirm">
	                                </div>
	                                <div class="form-group">
	                                    <input type="text" name="email" placeholder="Email" class="form-email form-control" id="form-email">
	                                </div>
	                                <div class="form-group">
	                                    <input type="text" name="phone" placeholder="手机号" class="form-phone form-control" id="form-phone">
	                                </div>
	                                <div class="form-group">
	                                    <input type="text" name="graduate" placeholder="学校" class="form-graduate form-control" id="form-graduate">
	                                </div>
	                                <div class="form-group">
	                                    <input type="text" name="major" placeholder="专业" class="form-major form-control" id="form-major">
	                                </div>
	                                <button type="submit" class="btn">提交</button>
	                                <span class="error-msg-container hide"><i class="fa fa-exclamation-circle"></i><span class='error-msg-content'> 邮箱或手机号格式不正确</span></span>
	                            </form>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>

	<!-- JavaScript -->
	<script src="<%=path%>/js/jquery.min.js"></script>
	<script src="<%=path%>/js/bootstrap.min.js"></script>
	<script src="<%=path%>/js/reg/reg.js"></script>
	<script src="<%=path%>/js/main.js"></script>
</body>
</html>
