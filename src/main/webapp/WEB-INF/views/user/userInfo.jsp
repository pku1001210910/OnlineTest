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
					<li class="active"><a href="<%=basePath%>user/home.action">基本信息</a></li>
				</ul>
				<ul class="nav nav-sidebar">
					<li><a href="<%=basePath%>user/password.action">修改密码</a></li>
				</ul>
				<ul class="nav nav-sidebar">
					<li><a href="<%=basePath%>user/userquiz.action">测试记录</a></li>
				</ul>
			</div>
			<div id="main-content" class="col-sm-9 col-md-10 main">
				<div class="user-info-form">
                   <form role="form" id="user-info-form" class="login-form">
	                   <div id="message-label" class="form-group bg-success"><span id="message"></span></div>
                   	   <div class="form-group">
	                       	<span class="label-left">用户名:</span> 
	                       	<span name="userName" id="form-username"></span>
	                       	<input type="text" id="form-username-input" name="userName" hidden>
	                   </div>
                       <div class="form-group">
                           <span class="label-left">Email:</span> 
                           <input type="text" name="email" placeholder="Email" class="form-email form-control" id="form-email">
                       </div>
                       <div class="form-group">
                       	   <span class="label-left">电话号码:</span> 
                           <input type="text" name="phone" placeholder="手机号" class="form-phone form-control" id="form-phone">
                       </div>
                       <div class="form-group">
                       	   <span class="label-left">学校:</span> 
                           <input type="text" name="graduate" placeholder="学校" class="form-graduate form-control" id="form-graduate">
                       </div>
                       <div class="form-group">
                           <span class="label-left">专业:</span> 
                           <input type="text" name="major" placeholder="专业" class="form-major form-control" id="form-major">
                       </div>
                       <button type="submit" id="update-btn" class="btn btn-primary disabled">保存</button>
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
			$('#form-username').text(user.userName);
			$('#form-username-input').val(user.userName);
			$('#form-email').val(user.email);
			$('#form-phone').val(user.phone);
			$('#form-graduate').val(user.graduate);
			$('#form-major').val(user.major);
		}
		
		function bindEvent() {
			$('#form-email').on('keydown', function(){
				$('#update-btn').removeClass('disabled');
			});
			$('#form-phone').on('keydown', function(){
				$('#update-btn').removeClass('disabled');
			});
			$('#form-graduate').on('keydown', function(){
				$('#update-btn').removeClass('disabled');
			});
			$('#form-major').on('keydown', function(){
				$('#update-btn').removeClass('disabled');
			});
			
			$('#update-btn').on('click', function(e){
				$('#message-label').hide();
				e.preventDefault();
				$('#update-btn').addClass('disabled');
				$.ajax({
			      type: 'POST',
			      url: '<%=basePath%>user/update.action',
			      data: $('#user-info-form').serialize(),
			      success: function(data) {
			    	  $('#message').text("更新成功");
			    	  $('#message-label').show();
			      }
			   });
			});
		}
		
		init();
		bindEvent();
	</script>
</body>
</html>
