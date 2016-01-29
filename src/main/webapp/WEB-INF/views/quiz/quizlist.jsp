<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
        <link href="<%=path%>/css/quiz/quiz.css" rel="stylesheet">
        <link href="<%=path%>/css/quiz/style.css" rel="stylesheet">
        <!-- user registration -->
        <link href="<%=path%>/css/reg/form-elements.css" rel="stylesheet">
        <link href="<%=path%>/css/reg/style.css" rel="stylesheet">
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
                    <jsp:include flush="true" page="../user/userlogin.jsp"></jsp:include>
                </div>
            </div>
        </nav>
        <div class="wjContent">
            <div class="content Tj clearfix">
                <div class="wjContent">
                    <div class="content clearfix">
                        <div class="mb_con">
                            <div class="mb_con_l">
                                <div class="mb_con_group liblue">
                                    <div class="h1">课题分类</div>
                                    <ul class="ul-capability gn_list">
                                    	<s:iterator id="category" value="categoryList">
                                    		<li title="${category.categoryName}" class="category-${category.categoryId}"><a href="<%=basePath%>quiz/startQuiz.action?categoryId=${category.categoryId}">${category.categoryName}</a></li>
                                    	</s:iterator>
                                    	<li class="category--2" title="所有"><a href="<%=basePath%>quiz/startQuiz.action?categoryId=-2">所有</a></li>
                                    </ul>
                                </div>
                                <!-- <div class="mb_con_group">
                                    <div class="h1">已付费试题</div>
                                    <ul class="ul-capability liorange">
                                        <li title="大学生消费"><a href="/topic_detail/54116fb1f7405b5b46d2f822">大学生消费</a></li>
                                        <li title="培训需求"><a href="/topic_detail/54116af0f7405b58ba9a7b3d">培训需求</a></li>
                                        <li title="大学生就业"><a href="/topic_detail/54116d09f7405b5a57e58fc4">大学生就业</a></li>
                                        <li title="员工满意度"><a href="/topic_detail/54116a46f7405b5a05beddca">员工满意度</a></li>
                                        <li title="新员工调查"><a href="/topic_detail/54116cb2f7405b5a3497fe30">新员工调查</a></li>
                                        <a href="/topic_list" class="more">更多</a>
                                    </ul>
                                </div> -->
                            </div>
                            <div class="mb_con_z">
                                <div class="Mbdiv">
                                    <div class="Mbgen_title">
                                        <ul>
                                            <li>
                                                <a href="/lib">课题分类</a>
                                                <s class="arrowr"></s>
                                            </li>
                                            <li>
                                                <a href="/share/more_share_1">${categoryName}</a>
                                                <%-- <s class="arrowr"></s> --%>
                                            </li>
                                            <!-- <li>
                                                <a href="/share/other_f_share_1" class="active">其他</a>
                                            </li> -->
                                        </ul>
                                    </div>
                                </div>
                                <div class="Mbdiv">
                                    <div class="Mbdiv_list">
                                    	<s:iterator id="quizVo" value="quizVoList">
                                    		<div class="tem">
	                                    		<div class="temtitle">
	                                    			<a href="#" onclick="checkQuizOwner(${quizVo.quiz.quizId}, '${session.user_session == null ? -1 :  session.user_session.userName}')">
	                                                    <p>${quizVo.quiz.title}</p>
	                                                </a>
	                                                <s:if test="#quizVo.quiz.price != 0">
	                                                 	<i class="fa fa-lock"></i>
	                                                	<span class="label label-info">${quizVo.quiz.price}元</span>
	                                                	<s:if test="#quizVo.quizOwnership == null">
	                                                		<button class="btn btn-warning" onclick="buyQuiz(${quizVo.quiz.quizId}, '${session.user_session == null ? -1 : session.user_session.userName}')">支付</button>
	                                                	</s:if>
	                                                	<s:else>
	                                                		<div class="btn btn-primary">已付费</div>
	                                                	</s:else>
	                                                </s:if>
	                                               
	                                                <%-- <span>共1页14个问题</span> --%>
	                                    		</div>
	                                    		<div class="temcon">
	                                                <span>分类：<a href="/classify/other_f_1" style="display:inline;">${quizVo.quizCategory.categoryName }</a></span> |
	                                                <span>作者：ztx12315</span> |
	                                                <%-- <span>被引用次数：0</span> --%>
	                                            </div>
	                                            <div class="text">
	                                          		${quizVo.quiz.description}
	                                            </div>
                                            </div>
                                    	</s:iterator>
                                  	</div>
                                </div>
                                <div class="pagenum" id="pagenum">
                                	<s:if test="firstPageNum==curPageNum">
                                		<span class="active">
                                			<a href="<%=basePath %>quiz/startQuiz.action?curPageNum=${firstPageNum}&categoryId=${categoryId}">${firstPageNum}</a>
                                		</span>	
                                	</s:if>
                                	<s:else>
                                		<span>
                                			<a href="<%=basePath %>quiz/startQuiz.action?curPageNum=${firstPageNum}&categoryId=${categoryId}">${firstPageNum}</a>
                                		</span>	
                                	</s:else>
                                	<s:if test="beginMore">
                                		<span class="">..</span>
                                	</s:if>
                                	<s:if test="pageNum1 > firstPageNum && pageNum1 < lastPageNum">
                                		<span>
                                			<a href="<%=basePath %>quiz/startQuiz.action?curPageNum=${pageNum1}&categoryId=${categoryId}">${pageNum1}</a>
                                		</span>
                                	</s:if>
                                	<s:if test="pageNum2 > firstPageNum && pageNum2 < lastPageNum">
                                		<span>
                                			<a href="<%=basePath %>quiz/startQuiz.action?curPageNum=${pageNum2}&categoryId=${categoryId}">${pageNum2}</a>
                                		</span>
                                	</s:if>
                                	<s:if test="pageNum3 > firstPageNum && pageNum3 < lastPageNum">
                                		<span class="active">
                                			<a href="<%=basePath %>quiz/startQuiz.action?curPageNum=${pageNum3}&categoryId=${categoryId}">${pageNum3}</a>
                                		</span>
                                	</s:if>
                                	<s:if test="pageNum4 > firstPageNum && pageNum4 < lastPageNum">
                                		<span>
                                			<a href="<%=basePath %>quiz/startQuiz.action?curPageNum=${pageNum4}&categoryId=${categoryId}">${pageNum4}</a>
                                		</span>
                                	</s:if>
                                	<s:if test="pageNum5 > firstPageNum && pageNum5 < lastPageNum">
                                		<span>
                                			<a href="<%=basePath %>quiz/startQuiz.action?curPageNum=${pageNum5}&categoryId=${categoryId}">${pageNum5}</a>
                                		</span>
                                	</s:if>
                                	<s:if test="lastMore">
                                		<span class="">..</span>
                                	</s:if>
                                	<s:if test="lastPageNum == curPageNum && lastPageNum > firstPageNum">
                                		<span class="active">
                                			<a href="<%=basePath %>quiz/startQuiz.action?curPageNum=${lastPageNum}&categoryId=${categoryId}">${lastPageNum}</a>
                                		</span>	
                                	</s:if>
                                	<s:if test="lastPageNum != curPageNum && lastPageNum > firstPageNum">
                                		<span>
                                			<a href="<%=basePath %>quiz/startQuiz.action?curPageNum=${lastPageNum}&categoryId=${categoryId}">${lastPageNum}</a>
                                		</span>	
                                	</s:if>
                                    <span>共${totalPage}页</span>
                                </div>
                            </div>
                        </div>
                        <!--mb_con-->
                    </div>
                </div>
            </div>
        </div>
        <!-- user registration modal -->
		<div id="regModal"" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		    <div class="modal-dialog">
		        <div class="modal-content">
		            <div class="modal-body">
		                <div class="row">
		                    <div class="form-box">
		                        <div class="form-top">
		                            <div class="form-top-left">
		                                <h3>用户注册</h3>
		                                <p>请填写个人详细信息:</p>
		                            </div>
		                            <div class="form-top-right">
		                                <i class="fa fa-lock"></i>
		                            </div>
		                        </div>
		                        <div class="form-bottom">
		                            <form role="form" action="<%=basePath%>user/userReg.action" method="post" class="login-form">
		                                <div class="form-group">
		                                    <input type="text" name="userName" placeholder="用户名" class="form-username form-control" id="form-username">
		                                </div>
		                                <div class="form-group">
		                                    <input type="password" name="userPw" placeholder="密码" class="form-password form-control" id="form-password">
		                                </div>
		                                <div class="form-group">
		                                    <input type="password" name="passwordConfirm" placeholder="密码确认" class="form-password form-control" id="form-password-confirm">
		                                </div>
		                                <button type="submit" class="btn">提交</button>
		                                <span class="error-msg-container hide"><i class="fa fa-exclamation-circle"></i><span class='error-msg-content'> 邮箱或手机号格式不正确</span></span>
		                            </form>
		                        </div>
		                    </div>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>
		<div class="modal fade" id="needLoginModal">  
		  <div class="modal-dialog">  
		    <div class="modal-content message_align">  
		      <div class="modal-header">  
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>  
		        <h4 class="modal-title">提示信息</h4>  
		      </div>  
		      <div class="modal-body">  
		        <p>请首先登陆或注册</p>  
		      </div>  
		      <div class="modal-footer">  
		         <input type="hidden" id="url"/>  
		         <a onclick="urlSubmit()" class="btn btn-success" data-dismiss="modal">确定</a>  
		      </div>  
		    </div><!-- /.modal-content -->  
		  </div><!-- /.modal-dialog -->  
		</div><!-- /.modal -->  
		
		<div class="modal fade" id="buySuccessModal">  
		  <div class="modal-dialog">  
		    <div class="modal-content message_align">  
		      <div class="modal-header">  
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>  
		        <h4 class="modal-title">提示信息</h4>  
		      </div>  
		      <div class="modal-body text-align-center">  
		        <p>当前仅支持支付宝转账</p>  
		        <img src="<%=path%>/images/alipay/aex02552o50ena4vmgwxdfc.png" />
		        <div class="red buyInfoContainer">*请注明您的用户名为 <p class="buyUserName"></p> 和试卷编号 <p class="buyQuizId"></p></div>
		      </div>  
		      <div class="modal-footer">  
		         <input type="hidden" id="url"/>  
		         <a onclick="refresh()" class="btn btn-success" data-dismiss="modal">确定</a>  
		      </div>  
		    </div><!-- /.modal-content -->  
		  </div><!-- /.modal-dialog -->  
		</div><!-- /.modal -->  
		
		<div class="modal fade" id="buyFailureModal">  
		  <div class="modal-dialog">  
		    <div class="modal-content message_align">  
		      <div class="modal-header">  
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>  
		        <h4 class="modal-title">提示信息</h4>  
		      </div>  
		      <div class="modal-body">  
		        <p>您付费失败</p>  
		      </div>  
		      <div class="modal-footer">  
		         <input type="hidden" id="url"/>  
		         <a onclick="urlSubmit()" class="btn btn-success" data-dismiss="modal">确定</a>  
		      </div>  
		    </div><!-- /.modal-content -->  
		  </div><!-- /.modal-dialog -->  
		</div><!-- /.modal -->  
        <!-- JavaScript -->
        <script src="<%=path%>/js/jquery.min.js"></script>
        <script src="<%=path%>/js/bootstrap.min.js"></script>
        <script src="<%=path%>/js/reg/reg.js"></script>
        <script src="<%=path%>/js/main.js"></script>
        <script src="<%=path%>/js/quizList/quizlist.js"></script>
        <script type="text/javascript">
          $(function(){
            var pagenum=document.getElementById('pagenum').innerHTML;
            pagenum=pagenum.replace("..","<span>...</span>");
            pagenum=pagenum.replace(" .. ","<span>...</span>");
            document.getElementById('pagenum').innerHTML=pagenum;
          })
        </script>
    </body>
</html>