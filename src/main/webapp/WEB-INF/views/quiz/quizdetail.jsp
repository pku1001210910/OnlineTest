<%@ page language="java" import="java.util.*, com.fivestars.websites.onlinetest.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
    		+ path + "/";
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>在线测试系统</title>
        <!-- core CSS -->
        <link href="<%=path%>/css/bootstrap.min.css" rel="stylesheet">
        <link href="<%=path%>/css/font-awesome/css/font-awesome.min.css" rel="stylesheet">
        <link href="<%=path%>/css/admin/home.css" rel="stylesheet">
        <link href="<%=path%>/css/main.css" rel="stylesheet">
        <!-- quiz detail -->
        <link href="<%=path%>/css/quiz/quizdetail.css" rel="stylesheet">
        <link href="<%=path%>/css/quiz/quizdetailBase.css" rel="stylesheet">
        <!-- support ie8 & ie9 & ie10 -->
        <script src="<%=path%>/js/html5shiv.min.js" type="text/javascript"></script>
        <script src="<%=path%>/js/respond.min.js" type="text/javascript"></script>
        <script src="<%=path%>/js/ie10-viewport-bug-workaround.js" type="text/javascript"></script>
        <script src="<%=path%>/js/ie-elements.js" type="text/javascript"></script>
       	<script src="<%=path%>/js/json2.js" type="text/javascript"></script>
    </head>
    <body>
        <nav class="navbar bg-primary navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand white" href="<%=basePath%>">北京科力民享科技有限公司 </a> <a class="navbar-brand white font-12" href="<%=basePath%>article/all.action?pageNo=1"> 文章列表 </a> <a class="navbar-brand white font-12" href="<%=basePath%>quiz/startQuiz.action"> 测试列表 </a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <jsp:include flush="true" page="../user/userlogin.jsp"></jsp:include>
                </div>
            </div>
        </nav>
        <div class="wjContent clear" id="survey_page">
            <div class="content" id="begin_content" style="">
                <div class="wjtitle mtop project_title">
                    <h1>${quiz.title}</h1>
                </div>
                <div class="wjintro mtop desc_begin">
                    ${quiz.description}
                </div>
                <div class="wjhr mtop"></div>
            </div>
            <div id="question_box">
                <div class="maxtop btns title" id="loader_div_1" style="display: none; text-align: center;">
                </div>
                <!-- Append question's html code here. -->
                <s:bean name="com.fivestars.websites.onlinetest.util.SubjectItemComparator" id="subjectItemComparator"></s:bean>
                <s:bean name="com.fivestars.websites.onlinetest.util.QuizSubjectComparator" id="quizSubjectComparator"></s:bean>
                
                <s:sort comparator="quizSubjectComparator" source="quiz.quizSubjects">
                    <s:iterator id="quizSubject">
                        <div class="wjques maxtop question jqtransformdone" id="">
                            <div class="title">
                                ${quizSubject.question}
                            </div>
                            <div class="matrix">
                            <div class="red"></div>
                            <!-- single radio -->
                            <s:if test="#quizSubject.type == 0">
                                <s:sort comparator="subjectItemComparator" source="#quizSubject.subjectItems">
                                    <s:iterator id="subjectItem">
                                        <div class="radio">
                                            <label>
                                            <input type="radio" value="${subjectItem.itemId}" name="${quizSubject.subjectId}" /> ${subjectItem.choice }
                                            </label>
                                        </div>
                                    </s:iterator>
                                </s:sort>
                            </s:if>
                            <!-- multiple choice -->
                            <s:if test="#quizSubject.type == 1">
                                <s:sort comparator="subjectItemComparator" source="#quizSubject.subjectItems">
                                    <s:iterator id="subjectItem">
                                        <div class="checkbox">
                                            <label>
                                            <input type="checkbox" value="${subjectItem.itemId}" name="${quizSubject.subjectId}"> ${subjectItem.choice }
                                            </label>
                                        </div>
                                    </s:iterator>
                                </s:sort>
                            </s:if>
                            </div>
                        </div>
                    </s:iterator>
                </s:sort>
                
            </div>
            
            <div class="feedbackContainer">
	            <div class="wjintro mtop desc_begin">
	                   	您的评价结果是：
	            </div>
	            <div class="wjhr mtop"></div>
	            <div class="feedbackContent wjintro mtop desc_begin">${feedbackContent}</div>
            </div>
            
            <div class="maxtop btns" id="control_panel">
                <button type="button" class="wj_color btn btn-primary" data-loading-text="正在处理试卷..." autocomplete="off" id="btn_finish_quiz">提交</button>
            </div>
        </div>
        <!-- JavaScript -->
        <script src="<%=path%>/js/jquery.min.js"></script>
        <script src="<%=path%>/js/bootstrap.min.js"></script>
        <script src="<%=path%>/js/reg/reg.js"></script>
        <script src="<%=path%>/js/main.js"></script>
        <script src="<%=path%>/js/quizDetail/quizdetail.js"></script>
    </body>
</html>