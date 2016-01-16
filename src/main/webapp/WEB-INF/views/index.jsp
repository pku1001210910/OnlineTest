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
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>在线测试系统</title>

    <!-- core CSS -->
    <link href="<%=path %>/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=path %>/css/index.css" rel="stylesheet">
  </head>

  <body>

    <nav class="navbar bg-primary navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand white" href="#">
          	北京大学心理系
      	  </a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <form class="navbar-form navbar-right">
            <div class="form-group">
              <input type="text" placeholder="用户名" class="form-control">
            </div>
            <div class="form-group">
              <input type="password" placeholder="密码" class="form-control">
            </div>
            <button type="submit" class="btn btn-success">登陆</button>
            <a class="white underline" href="#"> 没有账号？</a>
          </form>
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
          <p><a class="btn btn-default" href="#" role="button">查看文章 &raquo;</a></p>
        </div>
        <div class="col-md-6">
          <h2>测试模块</h2>
          <p>请在这里填写测试模块的背景、作用</p>
          <p><a class="btn btn-default" href="#" role="button">参加测试 &raquo;</a></p>
       </div>
      </div>

      <hr>

      <footer>
        <p>关于我们|联系我们|版权声明|隐私保护|</p>
      </footer>
    </div> <!-- /container -->


    <!-- JavaScript -->
    <script src="<%=path %>/js/jquery.min.js"></script>
    <script src="<%=path %>/js/bootstrap.min.js"></script>
  </body>
</html>
