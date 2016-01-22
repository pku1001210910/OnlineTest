var initGrid = function(quizList) {
	$('#quizzes').jsGrid({
		data: quizList,
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
	    		
	    noDataContent: '目前没有试卷数据，你可以点击左上角按钮添加新试卷',
	    
	   	fields:[
	   		{name:'quizId', title:'编号', width:'5%', align:'center'},
	   		{name:'title', title:'标题',  width:'15%', align:'left'},
	   		{name:'category', title:'类别', width:'10%', align:'left'},
	   		{name:'description', title:'详细', width:'20%', align:'left'},
	   		{name:'status', title:'发布状态', width:'10%', align:'left'},
	   		{name:'submitDate', title:'修改日期', width:'20%', align:'left'},
	   		{name:'price', title:'价格', width:'10%', align:'left'},
	   		{type: "control", width:'10%'}
	   	],
	});
};

var bindEvent = function() {
	// event handler
	$('#add-quiz').click(function() {
		var quizDialog = new onlineTest.management.Quiz('create');
	});
	
	$('#need-charge-toggle').click(function() {
	
	})
};

$(document).ready(function() {
	initGrid(quizList);
	bindEvent();
});
