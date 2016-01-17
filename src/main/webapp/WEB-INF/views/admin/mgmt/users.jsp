<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.fivestars.websites.onlinetest.model.User" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	Object users = request.getAttribute("users");
%>
<h2 class='page-header'>用户管理</h2>
<div class="btn-group">
  <button class="btn btn-default btn-xs dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
   	 导出 <span class="caret"></span>
  </button>
  <ul class="dropdown-menu">
    <li><a id="export_csv" href="#">csv</a></li>
  </ul>
</div>

<div id='userlist'></div>

<script>
var userlist = <%=users%>;
$('#userlist').jsGrid({
	data: userlist,
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
    
   	fields:[
   		{name:'userName', title:'用户名', align:'right'},
   		{name:'email', title:'邮箱', align:'right'},
   		{name:'phone', title:'电话', align:'right'},
   		{name:'graduate', title:'学校', align:'right'},
   		{name:'major', title:'专业',align:'right'},		
   		{name:'background', title:'背景', align:'right'}
   	],
});

$('#export_csv').on('click', function() {
	// prepare data
	var row = ['用户名', '邮箱', '电话', '学校', '专业', '背景'];
	var csvContent = 'data:text/csv;charset=UTF-8,' + row.join(',') + "\n";
	for(var i = 0; i < userlist.length; i++) {
		var user = userlist[i];
		row = [user.userName, user.email, user.phone, user.graduate, user.major, user.background];
		csvContent += row.join(",") + "\n";
	}
	
	// start to download
	var encodedUri = encodeURI(csvContent);
	var link = document.createElement("a");
	link.setAttribute("href", encodedUri);
	link.setAttribute("download", "all_users.csv");
	link.click();
});
</script>