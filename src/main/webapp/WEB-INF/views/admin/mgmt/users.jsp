<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.fivestars.websites.onlinetest.model.User" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	Object users = request.getAttribute("users");
	Object quizzes = request.getAttribute("quizzes");
%>
<!-- CSS -->
<link href="<%=path%>/css/fastselect.min.css" rel="stylesheet">
<link href="<%=path%>/css/fastselect-custom.css" rel="stylesheet">
<!-- CSS end -->

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

<!-- quiz owner dialog -->
<div class='modal fade' id='quiz-owner-dialog'>
  <div class='modal-dialog modal-lg'>
    <div class='modal-content'>
      <div class='modal-header'>
      	<button type="button" id='close-btn' class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="dialog-label">测试权限</h4>
      </div>
      <div class='modal-body'> 
      	<p>请选择<span id='user-name-span' style="font-weight:bold;padding:0 5px;"/>可以参加的测试</p>
      	<div id='multi-select-div'></div>
      </div>
      <div class='modal-footer'>
        <button type='button' class='btn btn-primary btn-sm' id='save-btn'>保存</button>
        <button type='button' class='btn btn-default btn-sm' data-dismiss='modal'>关闭</button>
      </div>
    </div>
  </div>
</div>

<!-- JavaScript -->
<script src='<%=path%>/js/alasql.min.js'></script>
<script src='<%=path%>/js/xlsx.core.min.js'></script>
<script src='<%=path%>/js/fastselect.standalone.min.js'></script>
<script src='<%=path%>/js/fastselect.custom.js'></script>
<script>
console.log('user list');

// all data
var userlist = <%=users%>;
var quizlist = <%=quizzes%>;

function init(userlist) {
	$('#userlist').jsGrid({
		data: userlist,
		height: 'auto',
	    width: '95%',
	    
	    sorting: true,
	    
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
	    controller: {
	    	 deleteItem: deleteUser
	    },
	    
	    noDataContent: '找不到用户',
	    
	   	fields:[
	   		{name:'userName', title:'用户名', width:'10%', align:'right'},
	   		{name:'email', title:'Email', width:'20%', align:'right'},
	   		{name:'phone', title:'电话', width:'20%',align:'right'},
	   		{name:'graduate', title:'学校', width:'20%', align:'right'},
	   		{name:'major', title:'专业', width:'15%', align:'right'},
	   		{name:'userName', title:'测试权限', width:'10%', align:'center', itemTemplate:quizzeItem},
	   		{type: 'control', width:'5%', editButton: false}
	   	],
	});

	//delete user
	function deleteUser(item) {
		var userName = item.userName;
		$.ajax({
	      type: "POST",
	      url: '<%=path%>/admin/users/remove.action',
	      data: 'userName=' + userName,
	    });
	};

	// create quizze management button
	function quizzeItem(username) {
		var $div = $("<div class='btn btn-primary btn-xs'>").append('查看详情');
		$div.on('click', function() {
			$.ajax({
		      type: "POST",
		      url: '<%=path%>/admin/quizowner/loadOwnedQuizzes.action',
		      data: 'userName=' + username,
		      success: function(data) {
		    	prepareFastselect(data);
		    	$('#quiz-owner-dialog').modal({
	     		    backdrop: 'static',
	     		    keyboard: false
	     		});
		      }
		    });	
		});
		return $div;
	};
}

function bindEvents() {
	// exports as csv
	$('#export_csv').on('click', function() {
		var rows = [];
		for(var i = 0; i < userlist.length; i++) {
			var user = userlist[i];
			rows.push([user.userName, user.email, user.phone, user.graduate, user.major, user.background]);
		}
		
		alasql("SELECT * INTO CSV('all_users.csv') FROM ?",[rows]);
	});

	//exports as excel
	$('#export_excel').on('click', function() {
		alasql("SELECT * INTO XLSX('all_users.xlsx',{headers:true}) FROM ? ",[userlist]);
	});
	
	// save user quizzes
	$('#save-btn').on('click', function() {
		var quizIds = [];
		$('.fstControls > div').each(function(index, item){
			quizIds.push($(item).data('value'));
		});
		var username = $('#user-name-span').text();
			
		$.ajax({
	      type: "POST",
	      url: '<%=path%>/admin/quizowner/saveOwnedQuizzes.action',
	      data: 'userName=' + username + '&selectedIds=' + quizIds,
	      success: function(data) {
	    	// close
	    	$('#close-btn').click();
	      }
	    });	
	});
}

function prepareFastselect(data) {
	// clean
	$('#multi-select-div').empty();
	$('#multi-select-div').append($('<select class="multipleSelect" id="quiz-select" multiple></select>'));
	
	// init
	$(quizlist).each(function(index, quiz) {
		var find = false;
		for(var i=0; i < data.quizIds.length; i++) {
			if(data.quizIds[i] === quiz.quizId) {
				find = true;
				break;
			}
		}
		var optionStr = '<option ' + (!!find ? ' selected ': '') + 'value="' + quiz.quizId + '">' + quiz.title + '</option>';
		$('#quiz-select').append($(optionStr));
	});
	
	$('#user-name-span').text(data.userName);
	$('#quiz-select').fastselect();
}

init(userlist);
bindEvents();
</script>