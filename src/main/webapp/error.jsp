<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>页面找不到</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
* {
	line-height: 1.2;
	margin: 0;
}

html {
	color: #888;
	display: table;
	font-family: sans-serif;
	height: 100%;
	text-align: center;
	width: 100%;
}

body {
	display: table-cell;
	vertical-align: middle;
	margin: 2em auto;
}

h1 {
	color: #555;
	font-size: 2em;
	font-weight: 400;
}

p {
	margin: 0 auto;
	width: 280px;
}

@media only screen and (max-width: 280px) {
	body, p {
		width: 95%;
	}
	h1 {
		font-size: 1.5em;
		margin: 0 0 0.3em;
	}
}
</style>
<link href="<%=path %>/css/main.css" rel="stylesheet">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="error-template">
					<h1>哎哟!</h1>
					<h2>404 页面找不到啦</h2>
					<div class="error-actions">
						<a href="<%=path%>/"> 返回主页 </a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
