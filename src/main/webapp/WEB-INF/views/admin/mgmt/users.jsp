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
    <li><a id="export_excel" href="#">excel</a></li>
  </ul>
</div>
<div id='userlist'></div>


<!-- JavaScript -->
<script src='<%=path%>/js/alasql.min.js'></script>
<script src='<%=path%>/js/xlsx.core.min.js'></script>
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
    
    confirmDeleting: true,
    deleteConfirm: '确定要删除?',
    
    noDataContent: '找不到用户',
    
   	fields:[
   		{name:'userName', title:'用户名', align:'right'},
   		{name:'email', title:'邮箱', align:'right'},
   		{name:'phone', title:'电话', align:'right'},
   		{name:'graduate', title:'学校', align:'right'},
   		{name:'major', title:'专业',align:'right'},		
   		{name:'background', title:'背景', align:'right'},
   		{type: "control"}
   	],
});

$('#export_csv').on('click', function() {
	var rows = [];
	for(var i = 0; i < userlist.length; i++) {
		var user = userlist[i];
		rows.push([user.userName, user.email, user.phone, user.graduate, user.major, user.background]);
	}
	
	alasql("SELECT * INTO CSV('all_users.csv') FROM ?",[rows]);
});

$('#export_excel').on('click', function() {
	alasql("SELECT * INTO XLSX('all_users.xlsx',{headers:true}) FROM ? ",[userlist]);
});
</script>