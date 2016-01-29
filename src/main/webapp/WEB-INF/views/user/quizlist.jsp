<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Object user = request.getAttribute("json");
    Object infoMsg = request.getAttribute("infoMsg");
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
        <link href="<%=path%>/css/userInfo.css" rel="stylesheet">
        <link href="<%=path%>/css/main.css" rel="stylesheet">
        <link href="<%=path%>/css/quiz/style.css" rel="stylesheet">
        <!-- support ie8 & ie9 & ie10 -->
        <script src="<%=path%>/js/html5shiv.min.js" type="text/javascript"></script>
        <script src="<%=path%>/js/respond.min.js" type="text/javascript"></script>
        <script src="<%=path%>/js/ie10-viewport-bug-workaround.js" type="text/javascript"></script>
        <script src="<%=path%>/js/ie-elements.js" type="text/javascript"></script>
    </head>
    <body>
        <nav class="navbar bg-primary navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed"
                        data-toggle="collapse" data-target="#navbar" aria-expanded="false"
                        aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span> 
                    <span class="icon-bar"></span> 
                    <span class="icon-bar"></span> 
                    <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand white" href="<%=basePath%>"> 北京大学心理系 </a>
                    <a class="navbar-brand white font-12" href="<%=basePath%>article/all.action?pageNo=1"> 文章列表 </a>
                    <a class="navbar-brand white font-12" href="<%=basePath%>quiz/startQuiz.action"> 测试列表 </a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <jsp:include flush="true" page="./userlogin.jsp"></jsp:include>
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="row">
                <div class="col-sm-3 col-md-2 sidebar">
                    <ul class="nav nav-sidebar">
                        <li><a href="<%=basePath%>user/home.action">基本信息</a></li>
                    </ul>
                    <ul class="nav nav-sidebar">
                        <li><a href="<%=basePath%>user/password.action">修改密码</a></li>
                    </ul>
                    <ul class="nav nav-sidebar">
                        <li class="active"><a href="<%=basePath%>user/userquiz.action">测试记录</a></li>
                    </ul>
                </div>
                <div id="main-content" class="col-sm-9 col-md-10 main">
                    <div class="user-info-form">
                        <div class="tem-container">
                            <s:iterator id="userQuizVo" value="userQuizVoList">
                                <div class="tem">
                                    <div class="temtitle">
                                        <a href="<%=basePath %>user/quizDetail.action?quizId=${userQuizVo.quiz.quizId}&recordId=${userQuizVo.userQuiz.recordId}" target="_blank">
                                            <p>${userQuizVo.quiz.title}</p>
                                        </a>
                                        <%-- <span>${userQuizVo.userQuiz.quizDate}</span> --%>
                                    </div>
                                    <div class="temcon">
                                        <span>分类：<a href="#" style="display:inline;">${userQuizVo.quizCategory.categoryName }</a></span> |
                                        <%-- <span>作者：ztx12315</span> | --%>
                                        <span>答题时间：${userQuizVo.userQuiz.quizDate} </span>
                                    </div>
                                    <div class="text">
                                        ${userQuizVo.quiz.description}
                                    </div>
                                </div>
                            </s:iterator>
                        </div>
                        <div class="pagenum item" id="pagenum">
                            <s:if test="firstPageNum==curPageNum">
                                <span class="active">
                                <a href="<%=basePath %>user/userquiz.action?curPageNum=${firstPageNum}">${firstPageNum}</a>
                                </span>	
                            </s:if>
                            <s:else>
                                <span>
                                <a href="<%=basePath %>user/userquiz.action?curPageNum=${firstPageNum}">${firstPageNum}</a>
                                </span>	
                            </s:else>
                            <s:if test="beginMore">
                                <span class="">..</span>
                            </s:if>
                            <s:if test="pageNum1 > firstPageNum && pageNum1 < lastPageNum">
                                <span>
                                <a href="<%=basePath %>user/userquiz.action?curPageNum=${pageNum1}">${pageNum1}</a>
                                </span>
                            </s:if>
                            <s:if test="pageNum2 > firstPageNum && pageNum2 < lastPageNum">
                                <span>
                                <a href="<%=basePath %>user/userquiz.action?curPageNum=${pageNum2}">${pageNum2}</a>
                                </span>
                            </s:if>
                            <s:if test="pageNum3 > firstPageNum && pageNum3 < lastPageNum">
                                <span class="active">
                                <a href="<%=basePath %>user/userquiz.action?curPageNum=${pageNum3}">${pageNum3}</a>
                                </span>
                            </s:if>
                            <s:if test="pageNum4 > firstPageNum && pageNum4 < lastPageNum">
                                <span>
                                <a href="<%=basePath %>user/userquiz.action?curPageNum=${pageNum4}">${pageNum4}</a>
                                </span>
                            </s:if>
                            <s:if test="pageNum5 > firstPageNum && pageNum5 < lastPageNum">
                                <span>
                                <a href="<%=basePath %>user/userquiz.action?curPageNum=${pageNum5}">${pageNum5}</a>
                                </span>
                            </s:if>
                            <s:if test="lastMore">
                                <span class="">..</span>
                            </s:if>
                            <s:if test="lastPageNum == curPageNum && lastPageNum > firstPageNum">
                                <span class="active">
                                <a href="<%=basePath %>user/userquiz.action?curPageNum=${lastPageNum}">${lastPageNum}</a>
                                </span>	
                            </s:if>
                            <s:if test="lastPageNum != curPageNum && lastPageNum > firstPageNum">
                                <span>
                                <a href="<%=basePath %>user/userquiz.action?curPageNum=${lastPageNum}">${lastPageNum}</a>
                                </span>	
                            </s:if>
                            <span>共${totalPage}页</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /container -->
        <!-- JavaScript -->
        <script src="<%=path%>/js/jquery.min.js"></script>
        <script src="<%=path%>/js/bootstrap.min.js"></script>
        <script src="<%=path%>/js/reg/reg.js"></script>
        <script src="<%=path%>/js/main.js"></script>
    </body>
</html>