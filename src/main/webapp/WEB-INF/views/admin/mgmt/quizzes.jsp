<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	Object quizzes = request.getAttribute("quizzes");
%>
<!-- CSS -->
<link href="<%=path%>/css/font-awesome.min.css" rel="stylesheet">
<link href="<%=path%>/css/admin/quizzes.css" rel="stylesheet">
<!-- CSS end -->

<h2 class='page-header'>测试管理</h2>
<div class="btn-group">
  <button class="btn btn-default btn-xs" id="add-quiz" type="button">添加测试</button>
</div>

<div id='quizzes'></div>

<!-- add quiz dialog -->
<div class='modal fade' id='quiz-dialog'>
	<div class='modal-dialog modal-lg'>
		<div class='modal-content'>
			<div class='modal-header'>
				<span class="modal-title add-quiz-title" id="quiz-dialog-label">添加测试</span>
				<small id="quiz-save-label">系统已为您自动保存所有的更改</small>
				<span id="quiz-status-group">
					<small class="quiz-status-label">状态: </small>
					<small class="quiz-status-value" id="quiz-status">未发布</small>
 					<!--<button type='button' class='btn btn-primary btn-sm' data-action='publish' id='publish-quiz-btn'>发布测试</button>-->
				</span>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			</div>
			<div class='modal-body'>
			<div id="add-quiz-step-1">
				<h4 class="quiz-navi">第一步: 填写测试信息</h4>
					<form>
						<div class="form-group">
							<label for="quiz-title">测试标题</label>
							<input type="text" class="form-control" id="quiz-title"/>
						</div>
						<div class="form-group">
							<label for="quiz-description">测试描述</label>
							<textarea class="form-control" rows="5" id="quiz-description"></textarea>
						</div>
						<div class="form-group">
							<label for="quiz-category">测试类别</label>
							<div class="dropdown" id="quiz-category">
								<button class="btn btn-default dropdown-toggle" type="button" id="quiz-category-names" data-toggle="dropdown" data-categoryId="" aria-haspopup="true" aria-expanded="true">
									<span class="default-category">教育心理学</span>
									<span class="caret"></span>
								</button>
								<ul id="quiz-category-options" class="dropdown-menu" aria-labelledby="dropdownMenu1">
									<!-- 
									<li><a href="#" data-categoryId="">教育心理学</a></li>
									<li><a href="#" data-categoryId="">辅导心理学</a></li>
									<li><a href="#" data-categoryId="">犯罪心理学</a></li>
									<li><a href="#" data-categoryId="">学校心理学</a></li>
									 -->
								</ul>
							</div>
						</div>
						<div class="form-group">
							<div class="checkbox">
								<label>
									<input type="checkbox" id="need-charge-toggle"> 需要付费参加测试
								</label>
							</div>
						</div>
						<div id="quiz-price-group">
							<label for="quiz-price">价格</label>
							<div class="input-group">
								<input type="text" class="form-control" id="quiz-price">
								<div class="input-group-addon">元</div>
							</div>
						</div>
					</form>
				</div>
				<div id="add-quiz-step-2">
					<h4 class="quiz-navi">第二步: 编辑试题</h4>
					<div id="quiz-subject-container" class="subject-container">
						<div class="col-md-2 subject-type-panel">
							<div class="subject-type-header">
								<h4>
								常见题型<i class="glyphicon glyphicon-minus quiz-icon" id="collapse-type"></i>
								</h4>
							</div>
							<div class="subject-type-body" id="subject-type-body-panel">
								<div id="single-choice-subject-selector" data-type="singleChoice" class="subject-type">
									<i class="glyphicon glyphicon-ok-circle quiz-icon"></i>  单选题
								</div>
								<div class="add-subject-hint">请点击题型添加题目到编辑区域</div>
							</div>
						</div>
						<div class="col-md-10 subject-item-panel">
							<div class="subject-item-header">
								<h4 id="quiz-caption">
								问卷
								</h4>
							</div>
							<!-- dynamic subject goes here -->				
							<div id="subject-item-body-panel">
								<!-- single choice example -->
								<!--  
								<div class="subject-container clearfix" data-subjectId="">
									<div class="subject-left col-md-1">
										<div class="subject-number">第1题</div>
										<div><i class="shift-subject-up glyphicon glyphicon-arrow-up quiz-icon-gray"></i></div>
										<div><i class="shift-subject-down glyphicon glyphicon-arrow-down quiz-icon-gray"></i></div>
										<div><i class="remove-subject glyphicon glyphicon-trash quiz-icon-gray"></i></div>
									</div>
									<div class="subject-right col-md-11">
										<div class="subject-question">单选题</div>
										<div class="subject-items">
											<div class="radio subject-item" data-itemId="">
												<label>
													<input type="radio" name="subject-items">
													选项1
												</label>
												<div class="item-score">该选项分值:  
													<input type="number" name="item-score" min="0" max="100" class="item-score-value"/>
												</div>
											</div>
											<div class="radio subject-item" data-itemId="">
												<label>
													<input type="radio" name="subject-items">
													选项2
												</label>
												<div class="item-score">该选项分值:  
													<input type="number" name="item-score" min="0" max="100" class="item-score-value"/>
												</div>
											</div>
											<div><i class="add-item glyphicon glyphicon-plus-sign quiz-icon-gray"></i></div>
										</div>
									</div>
								</div>
								-->
								<!-- single choice example end -->
							</div>
						</div>
					</div>
				</div>
				<div id="add-quiz-step-3">
					<h4 class="quiz-navi">第三步: 设置反馈模板</h4>
					<div id="quiz-feedback-container" class="feeedback-container">
						<div class="feedback-item" data-feedbackId="">
							<h5>第1条反馈结果:</h5>
							<textarea class="form-control feedback-content" rows="5"></textarea>
							<div class="feedback-score-scope">
							最小分值： <input class="score-from" type="number" name="item-score-min" min="0" max="100" value="0"/>
							&nbsp;&nbsp;&nbsp;&nbsp;最大分值： <input class="score-to" type="number" name="item-score-min" min="0" max="100" value="0"/>
							</div>
							<div>
								<i class="add-feedback glyphicon glyphicon-plus-sign quiz-icon-gray"></i>
								<i class="remove-feedback glyphicon glyphicon-trash quiz-icon-gray"></i>
							</div>
						</div>
						<div class="feedback-item" data-feedbackId="">
							<h5>第2条反馈结果:</h5>
							<textarea class="form-control feedback-content" rows="5"></textarea>
							<div class="feedback-score-scope">
							最小分值： <input class="score-from" type="number" name="item-score-min" min="0" max="100" value="0"/>
							&nbsp;&nbsp;&nbsp;&nbsp;最大分值： <input class="score-to" type="number" name="item-score-min" min="0" max="100" value="0"/>
							</div>
							<div>
								<i class="add-feedback glyphicon glyphicon-plus-sign quiz-icon-gray"></i>
								<i class="remove-feedback glyphicon glyphicon-trash quiz-icon-gray"></i>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class='modal-footer'>
				<button type='button' class='btn btn-primary btn-sm' id='next-btn'>创建</button>
				<button type='button' class='btn btn-default btn-sm' id='prev-btn'>上一步</button>
				<button type='button' class='btn btn-default btn-sm' data-dismiss='modal' id='close-btn'>关闭</button>
			</div>
		</div>
	</div>
</div>
<div id="delete-subject-confirm" title="删除题目确认">
	<p>确定要删除题目吗？</p>
</div>
<script>
	var quizList = <%=quizzes%>;
</script>
<script src="<%=path%>/js/admin/mgmt/quizzes.js"></script>
<script src="<%=path%>/js/admin/mgmt/quiz/subject-manager.js"></script>
<script src="<%=path%>/js/admin/mgmt/quiz/generic-type-subject.js"></script>
<script src="<%=path%>/js/admin/mgmt/quiz/quiz.js"></script>
<script src="<%=path%>/js/admin/mgmt/quiz/single-choice-subject.js"></script>

