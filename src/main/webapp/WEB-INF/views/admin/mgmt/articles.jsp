<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.fivestars.websites.onlinetest.model.Article" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	Object articles = request.getAttribute("articles");
%>
<h2 class='page-header'>文章管理</h2>
<div class="btn-group">
  <button class="btn btn-default btn-xs " type="button">添加文章 </button>
</div>

<div id='articles'></div>
	
<script>
var articleList = <%=articles%>;
$('#articles').jsGrid({
	data: articleList,
	height: 'auto',
    width: '95%',
    
    paging: true,
    pageIndex: 1,
    pageSize: 13,
    pageButtonCount: 15,
    pagerFormat: '页码: {first} {prev} {pages} {next} {last} 共 {pageCount} 页',
    pagePrevText: '<<',
    pageNextText: '>>',
    pageFirstText: '首页',
    pageLastText: '尾页',
    pageNavigatorNextText: '...',
    pageNavigatorPrevText: '...',
    
    confirmDeleting: true,
    deleteConfirm: '确定要删除?',
    		
    noDataContent: '目前没有文章，你可以点击左上角按钮添加新文章',
    
   	fields:[
   		{name:'articleId', title:'编号', width:'10%', align:'center'},
   		{name:'title', title:'标题',  width:'65%', align:'left'},
   		{name:'createDate', title:'修改日期', width:'15%', align:'left', type:'date'},
   		{type: "control", width:'10%'}
   	],
});
</script>