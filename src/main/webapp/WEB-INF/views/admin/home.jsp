<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>在线测试后台管理系统</title>

<!-- core CSS -->
<link href="<%=path%>/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/css/admin/home.css" rel="stylesheet">
<link href="<%=path%>/css/main.css" rel="stylesheet">
</head>

<body>

	<nav class="navbar bg-primary navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand white" href="#">在线测试平台后台管理系统</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
        	
        </div>
      </div>
    </nav>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-2 col-md-1 sidebar">
				<ul class="nav nav-sidebar"  data-toggle="offcanvas">
					<li class="active"><a href="#">欢迎界面</a></li>
				</ul>
				<ul class="nav nav-sidebar">
					<li><a href="">用户管理</a></li>
				</ul>
				<ul class="nav nav-sidebar">
					<li><a href="">文章管理</a></li>
				</ul>
				<ul class="nav nav-sidebar">
					<li><a href="">测试管理</a></li>
				</ul>
				<ul class="nav nav-sidebar">
					<li><a href="">支付设置</a></li>
				</ul>
			</div>
			<div class="col-sm-10 col-sm-offset-3 col-md-11 col-md-offset-2 main">
			</div>
		</div>
	</div>

	<!-- JavaScript -->
	<script src="<%=path%>/js/jquery.min.js"></script>
	<script src="<%=path%>/js/bootstrap.min.js"></script>
</body>
</html>
