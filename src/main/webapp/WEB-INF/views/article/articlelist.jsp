<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Object articles = request.getAttribute("articles");
	Object total = request.getAttribute("total");
	Object pageNo = request.getAttribute("pageNo");
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
<link href="<%=path%>/css/articlelist.css" rel="stylesheet">
<link href="<%=path%>/css/admin/home.css" rel="stylesheet">
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
                <jsp:include flush="true" page="../user/userlogin.jsp"></jsp:include>
            </div>
        </div>
    </nav>

	<div class="background-grey"> 
	<div class="container">
      <div class="row">
      	<div class="col-sm-8 blog-main">
      	  <!-- articles -->
      	  <div id="article-list">
      	  </div>

		  <!-- pager -->
	      <nav>
			  <ul class="pager">
			    <li id="previous_page" class="previous"><a href="#">上一页</a></li>
			    <span id="page_indicator"></span>
			    <li id="next_page" class="next"><a href="#">下一页</a></li>
			  </ul>
		  </nav>
        </div>
      </div>
	</div>
	</div>
	<!-- /container -->

	<!-- JavaScript -->
	<script src="<%=path%>/js/jquery.min.js"></script>
	<script src="<%=path%>/js/bootstrap.min.js"></script>
	<script src="<%=path%>/js/main.js"></script>
	<script src='<%=path%>/js/jquery.hotkeys.js'></script>
	<script src='<%=path%>/js/prettify.js'></script>
	<script src='<%=path%>/js/bootstrap-wysiwyg.js'></script>
	<script>
		console.log('article list');
		
		var viewUrl = '<%=basePath %>article/view.action';
		var pageUrl = '<%=basePath %>article/all.action';
		
		function displayArticles() {
			var itemStr = '<div class="blog-post">' +
            				'<h2 class="blog-post-title"><span class="label-left">文章标题:</span> <a class="title-link" href="#"></a><span class="label-right"/></h2>' + 
            				'<div class="blog-post-meta"><span class="label-left">创建时间:</span><span class="artitle-time"></span></div>' +
          			      '</div>';
			var articles = <%=articles%>;
			$(articles).each(function(index, article){
				var $articleItem = $(itemStr);
				$articleItem.find('.title-link').attr('href', viewUrl + '?id=' + article.articleId);
				$articleItem.find('.title-link').text(article.title);
				$articleItem.find('.label-right').text('编号:' + article.articleId);
				$articleItem.find('.artitle-time').text(new Date(article.createDate).toLocaleString());
				$('#article-list').append($articleItem);
				
				$articleItem.on('click', function(event) {
					 window.location.href = $articleItem.find('.title-link').attr('href');
				});
			});
		}
		
		function setPager() {
			var totalPages = <%=total%>;
			var pageNo = <%=pageNo%>
			
			if(pageNo !== 1) {
				$('#previous_page > a').attr('href', pageUrl + '?pageNo=' + (pageNo - 1));
			} else {
				$('#previous_page').addClass('diabled');
			}
			
			if(pageNo !== totalPages) {
				$('#next_page > a').attr('href', pageUrl + '?pageNo=' + (pageNo + 1));
			} else {
				$('#next_page').addClass('diabled');
			}
			
			$('#page_indicator').text('共' + pageNo + '/' + totalPages + '页');
		}
		
		displayArticles();
		setPager();
	</script>
	</body>
</html>
