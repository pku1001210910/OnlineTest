<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	Object quizzes = request.getAttribute("quizzes");
%>
<h2 class='page-header'>测试管理</h2>
<div class="btn-group">
  <button class="btn btn-default btn-xs" type="button">添加测试 </button>
</div>

<div id='articles'></div>

<script>
	var quizList = <%=quizzes%>;
</script>
<script src="<%=path%>/js/admin/mgmt/quizzes.js"></script>
