<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Object user = request.getAttribute("json");
	Object infoMsg = request.getAttribute("infoMsg");
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
<link href="<%=path%>/css/userInfo.css" rel="stylesheet">
<link href="<%=path%>/css/main.css" rel="stylesheet">

<!-- support ie8 & ie9 & ie10 -->
<script src="<%=path%>/js/html5shiv.min.js" type="text/javascript"></script>
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
				<a class="navbar-brand white" href="<%=basePath%>"> 北京大学心理系 </a>
				<a class="navbar-brand white font-12" href="<%=basePath%>article/all.action?pageNo=1"> 文章列表 </a>
                <a class="navbar-brand white font-12" href="<%=basePath%>quiz/startQuiz.action"> 测试列表 </a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<jsp:include flush="true" page="./userlogin.jsp"></jsp:include>
			</div>
		</div>
	</nav>
	
	<div class="container">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<li><a href="<%=basePath%>user/home.action">基本信息</a></li>
				</ul>
				<ul class="nav nav-sidebar">
					<li class="active"><a href="<%=basePath%>user/password.action">修改密码</a></li>
				</ul>
				<ul class="nav nav-sidebar">
					<li><a>测试记录</a></li>
				</ul>
			</div>
			<div id="main-content" class="col-sm-9 col-md-10 main">
				<div class="user-info-form">
                   <form role="form" id="user-info-form" class="login-form">
	                   <div id="message-label" class="form-group"><span id="message"></span></div>
                   	   <div class="form-group">
	                       	<input type="text" id="form-username-input" name="userName" hidden>
	                   </div>
                       <div class="form-group">
                           <span class="label-left">旧密码:</span> 
                           <input type="password" name="passwordOld" placeholder="旧密码" class="form-password form-control" id="form-old-password">
                       </div>
                       <div class="form-group">
                       	   <span class="label-left">新密码:</span> 
                           <input type="password" name="password" placeholder="新密码" class="form-password form-control" id="form-password">
                       </div>
                       <div class="form-group">
                       	   <span class="label-left">密码确认:</span> 
                           <input type="password" name="passwordConfirm" placeholder="请再输入一次" class="form-password form-control" id="form-password-cfm">
                       </div>
                       <button type="submit" id="update-btn" class="btn btn-primary">保存</button>
                   </form>
               </div>
			</div>
		</div>
	</div>
	<!-- /container -->

	<!-- JavaScript -->
	<script src="<%=path%>/js/jquery.min.js"></script>
	<script src="<%=path%>/js/bootstrap.min.js"></script>
	<script src="<%=path%>/js/reg/reg.js"></script>
	<script src="<%=path%>/js/main.js"></script>
	<script>
		console.log('user info');
		function init() {
			var user = <%=user%>;
			$('#form-username-input').val(user.userName);
		}
		
		function bindEvent() {
			$('#update-btn').on('click', function(e){
				$('#message-label').hide();
				$('#update-btn').addClass('disabled');
				e.preventDefault();
				$.ajax({
			      type: 'POST',
			      url: '<%=basePath%>user/updatePwd.action',
			      data: $('#user-info-form').serialize(),
			      success: function(data) {
			    	  $('#message-label').show();
			    	  if(!!data.error) {
			    		  $('#message-label').removeClass('bg-success').addClass('bg-danger');
			    		  $('#message').text(data.error);
			    	  } else {
			    		  $('#message-label').addClass('bg-success').removeClass('bg-danger');
			    		  $('#message').text(data.message);
			    	  }
			    	  $('#update-btn').removeClass('disabled');
			      }
			   });
			});
		}
		
		init();
		bindEvent();
	</script>
</body>
</html>
