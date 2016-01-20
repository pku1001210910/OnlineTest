<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.fivestars.websites.onlinetest.model.Article" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	Object articles = request.getAttribute("articles");
%>
<!-- CSS -->
<link href="<%=path%>/css/prettify.css" rel="stylesheet">
<link href="<%=path%>/css/font-awesome.min.css" rel="stylesheet">
<link href="<%=path%>/css/admin/articles.css" rel="stylesheet">
<!-- CSS end -->

<h2 class='page-header'>文章管理</h2>
<div class="btn-group">
  <button class='btn btn-default btn-xs ' id='add-article' type='button'>添加文章 </button>
</div>

<div id='articles'></div>

<!-- article dialog -->
<div class='modal fade' id='article-dialog'>
  <div class='modal-dialog modal-lg'>
    <div class='modal-content'>
      <div class='modal-header'>
      	<button type="button" id='article-close-btn' class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="article-dialog-label">添加文章</h4>
      </div>
      <div class='modal-body'> 
      	<form id='article-form'>
      		<!-- article title -->
      		<div class="form-group">
            	<input id='article-title' name='title' type='text' class='form-control' placeholder='请输入文章标题'>
         	</div>
         	
         	<!-- article tool bar -->
         	<div id='tool-bar' class="form-group btn-toolbar" data-role="editor-toolbar" data-target="#article-content">
		      <div class="btn-group">
		        <a class="btn dropdown-toggle" data-toggle="dropdown" title="Font"><i class="icon-font"></i><b class="caret"></b></a>
		          <ul class="dropdown-menu">
		          </ul>
		        </div>
		      <div class="btn-group">
		        <a class="btn dropdown-toggle" data-toggle="dropdown" title="Font Size"><i class="icon-text-height"></i>&nbsp;<b class="caret"></b></a>
		          <ul class="dropdown-menu">
		          <li><a data-edit="fontSize 5"><font size="5">大号</font></a></li>
		          <li><a data-edit="fontSize 3"><font size="3">中号</font></a></li>
		          <li><a data-edit="fontSize 1"><font size="1">小号</font></a></li>
		          </ul>
		      </div>
		      <div class="btn-group">
		        <a class="btn" data-edit="bold" title="Bold (Ctrl/Cmd+B)"><i class="icon-bold"></i></a>
		        <a class="btn" data-edit="italic" title="Italic (Ctrl/Cmd+I)"><i class="icon-italic"></i></a>
		        <a class="btn" data-edit="strikethrough" title="Strikethrough"><i class="icon-strikethrough"></i></a>
		        <a class="btn" data-edit="underline" title="Underline (Ctrl/Cmd+U)"><i class="icon-underline"></i></a>
		      </div>
		      <div class="btn-group">
		        <a class="btn" data-edit="insertunorderedlist" title="Bullet list"><i class="icon-list-ul"></i></a>
		        <a class="btn" data-edit="insertorderedlist" title="Number list"><i class="icon-list-ol"></i></a>
		      </div>
		      <div class="btn-group">
		        <a class="btn" data-edit="justifyleft" title="Align Left (Ctrl/Cmd+L)"><i class="icon-align-left"></i></a>
		        <a class="btn" data-edit="justifycenter" title="Center (Ctrl/Cmd+E)"><i class="icon-align-center"></i></a>
		        <a class="btn" data-edit="justifyright" title="Align Right (Ctrl/Cmd+R)"><i class="icon-align-right"></i></a>
		        <a class="btn" data-edit="justifyfull" title="Justify (Ctrl/Cmd+J)"><i class="icon-align-justify"></i></a>
		      </div>
		    </div>
		    
		    <!-- article content -->
         	<div class="form-group">
            	<div class="form-control" id="article-content"></div>
            	<input hidden name='content' id='article-content-input'/>
          	</div>
		</form>
      </div>
      <div class='modal-footer'>
        <button type='button' class='btn btn-primary btn-sm' id='save-btn'>保存</button>
        <button type='button' class='btn btn-default btn-sm' data-dismiss='modal'>关闭</button>
      </div>
    </div>
  </div>
</div>

<!-- JavaScript -->
<script src='<%=path%>/js/jquery.hotkeys.js'></script>
<script src='<%=path%>/js/prettify.js'></script>
<script src='<%=path%>/js/bootstrap-wysiwyg.js'></script>
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
   		{type: 'control', width:'10%'}
   	],
});

$('#add-article').on('click', function() {
	$('#article-title').val('');
	$('#article-content').val('');
	$('#article-dialog').modal({
	    backdrop: 'static',
	    keyboard: false
	});
	$('#article-content').wysiwyg();
});

$('#save-btn').on('click', function(e) {
	$('#article-content-input').val($('#article-content').html());
	 $.ajax({
      type: "POST",
      url: '<%=path%>/admin/articles/save.action',
      data: $("#article-form").serialize(), // serializes the form's elements.
      success: function(data) {
    	  $('#article-close-btn').click();
      }
    });
	e.preventDefault();
});

$('#article-title').keypress(function(e) {
	if(e.which == 13) {
		$('#article-content').focus();
		e.preventDefault();
	}
});

// init rich editor
function initToolbarBootstrapBindings() {
    var fonts = ['Serif', 'Sans', 'Arial', 'Arial Black', 'Courier', 
          'Courier New', 'Comic Sans MS', 'Helvetica', 'Impact', 'Lucida Grande', 'Lucida Sans', 'Tahoma', 'Times',
          'Times New Roman', 'Verdana'];
    
    var fontTarget = $('[title=Font]').siblings('.dropdown-menu');
    $.each(fonts, function (idx, fontName) {
        fontTarget.append($('<li><a data-edit="fontName ' + fontName +'" style="font-family:\''+ fontName +'\'">'+fontName + '</a></li>'));
    });
    
    $('a[title]').tooltip({container:'body'});
  	$('.dropdown-menu input').click(function() {return false;})
		    .change(function () {$(this).parent('.dropdown-menu').siblings('.dropdown-toggle').dropdown('toggle');})
      .keydown('esc', function () {this.value='';$(this).change();});

    $('[data-role=magic-overlay]').each(function () { 
      var overlay = $(this), target = $(overlay.data('target')); 
      overlay.css('opacity', 0).css('position', 'absolute').offset(target.offset()).width(target.outerWidth()).height(target.outerHeight());
    });
};
initToolbarBootstrapBindings();  
window.prettyPrint && prettyPrint();
</script>