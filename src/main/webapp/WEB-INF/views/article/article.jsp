<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Object article = request.getAttribute("article");
	Object prev = request.getAttribute("prev");
	Object next = request.getAttribute("next");
	Object recommends = request.getAttribute("recommends");
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
<link href="<%=path%>/css/article.css" rel="stylesheet">
<link href="<%=path%>/css/main.css" rel="stylesheet">

<!-- support ie8 & ie9 & ie10 -->
<script src="<%=path%>/js/html5shiv.min.js" type="text/javascript"></script>
<script src="<%=path%>/js/respond.min.js" type="text/javascript"></script>
<script src="<%=path%>/js/ie10-viewport-bug-workaround.js" type="text/javascript"></script>
<script src="<%=path%>/js/ie-elements.js" type="text/javascript"></script>
</head>
<body>
	<div class="blog-masthead">
      <div class="container">
        <nav class="blog-nav">
          <a class="blog-nav-item active" href="<%=basePath%>">返回目录</a>
        </nav>
      </div>
    </div>

	<div class="container">
      <div class="row">
      <div class="col-sm-8 blog-main">
      	  <!-- article -->
          <div class="blog-post">
            <h2 class="blog-post-title" id="article-title">Default Title</h2>
            <p class="blog-post-meta" id="article-time">Jan. 1st 1970</p>
            <div id="article-content"></div>
          </div>

		  <!-- pager -->
          <nav>
            <ul class="pager">
              <li id="previous_article"><a href="#">上一篇</a></li>
              <li id="next_article"><a href="#">下一篇</a></li>
            </ul>
          </nav>
        </div>

		<!-- sidebar -->
        <div class="col-sm-3 col-sm-offset-1 blog-sidebar">
          <div class="sidebar-module">
            <h5>推荐文章</h5>
            <ol id="recommend-list" class="list-unstyled">
            </ol>
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
		console.log('display article');
		
		var viewUrl = '<%=basePath %>article/view.action';
		
		function displayArticle() {
			var article = <%=article%>;
			$('#article-title').text(article.title);
			$('#article-time').text(new Date(article.createDate).toLocaleString());
			$('#article-content').html(article.content);
		}
		
		function setPrevNext() {
			var prevId = <%=prev%>;
			var nextId = <%=next%>;
			
			if(!!prevId) {
				$('#previous_article > a').attr('href', viewUrl + '?id=' + prevId);
			} else {
				$('#previous_article').hide();
			}
			
			if(!!nextId) {
				$('#next_article > a').attr('href', viewUrl + '?id=' + nextId);
			} else {
				$('#next_article').hide();
			}
		}
		
		function setRecommends() {
			var recommends = <%=recommends%>;
			$(recommends).each(function(index, article){
				var $li = $('<li>');
				var $a = $('<a>').attr('href', viewUrl + '?id=' + article.articleId).text(article.title);
				$li.append($a);
				$('#recommend-list').append($li);
			});
		}
		
		displayArticle();
		setPrevNext();
		setRecommends();
	</script>
	</body>
</html>
