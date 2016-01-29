var deleteQuiz = function(item) {
	var quizId = item['quizId'];
	var quiz = {
		'quizId': quizId
	};
	$.ajax({
		url: './deleteQuiz.action',
		type: 'post',
		data: quiz,
		error: function() {
			$('#request-error').dialog({
				resizable: false,
				height: 160,
				modal: true,
				buttons: {
					"确定": function() {
						$(this).dialog( "close" );
					}
				}
			});
		}
	});
};

var updateQuiz = function(event) {
	var target = event.event.target;
	if(!$(target).hasClass('detail-btn')) {
		return;
	}
	
	var quizId = event.item['quizId'];
	$.ajax({
		url: './loadQuiz.action',
		type: 'post',
		data: {'quizId': quizId},
		success:function (data) {
			var quizData = JSON.parse(data['result']);
			var quizDialog = new onlineTest.management.Quiz('update', quizId);
			quizDialog.setup(quizData);
		},
		error: function() {
			$('#request-error').dialog({
				resizable: false,
				height: 160,
				modal: true,
				buttons: {
					"确定": function() {
						$(this).dialog( "close" );
					}
				}
			});
		}
	});
};

var initGrid = function(quizList) {
	$('#quizzes').jsGrid({
		data: quizList,
		height: 'auto',
	    width: '95%',
	    
	    paging: true,
	    pageIndex: 1,
	    pageSize: 11,
	    pageButtonCount: 15,
	    pagerFormat: '页码: {first} {prev} {pages} {next} {last} 共 {pageCount} 页',
	    pagePrevText: '<<',
	    pageNextText: '>>',
	    pageFirstText: '首页',
	    pageLastText: '尾页',
	    pageNavigatorNextText: '...',
	    pageNavigatorPrevText: '...',
	    
	    rowClick: updateQuiz,
	    confirmDeleting: true,
	    deleteConfirm: '确定要删除?',
	    controller: {
	    	 deleteItem: deleteQuiz,
	    	 updateItem: updateQuiz
	    },
	    		
	    noDataContent: '目前没有试卷数据，你可以点击左上角按钮添加新试卷',
	    
	   	fields:[
	   		{name:'quizId', title:'编号', width:'5%', align:'center'},
	   		{name:'title', title:'标题',  width:'15%', align:'left'},
	   		{name:'category', title:'类别', width:'10%', align:'left'},
	   		{name:'description', title:'详细', width:'20%', align:'left'},
	   		{name:'status', title:'发布状态', width:'10%', align:'left'},
	   		{name:'submitDate', title:'创建日期', width:'17%', align:'left'},
	   		{name:'price', title:'价格', width:'8%', align:'left'},
	   		{name:'quizId', title:'测试内容', width:'10%', align:'center', itemTemplate: quizTmpl},
	   		{type: "control", width:'5%', editButton: false}
	   	],
	});
};

var quizTmpl = function() {
	return $("<div class='btn btn-primary btn-xs detail-btn'>").append('查看详情');
};

var bindEvent = function() {
	// event handler
	$('#add-quiz').click(function() {
		var quizDialog = new onlineTest.management.Quiz('create');
	});
};

$(document).ready(function() {
	initGrid(quizList);
	bindEvent();
});
