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
                    <a class="navbar-brand white" href="#"> 北京大学心理系 </a>
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
                                    		<li title="${category.categoryName}"><a href="/share/${category.categoryId}">${category.categoryName}</a></li>
                                    	</s:iterator>
                                    </ul>
                                </div>
                                <div class="mb_con_group">
                                    <div class="h1">推荐专题</div>
                                    <ul class="ul-capability liorange">
                                        <li title="大学生消费"><a href="/topic_detail/54116fb1f7405b5b46d2f822">大学生消费</a></li>
                                        <li title="培训需求"><a href="/topic_detail/54116af0f7405b58ba9a7b3d">培训需求</a></li>
                                        <li title="大学生就业"><a href="/topic_detail/54116d09f7405b5a57e58fc4">大学生就业</a></li>
                                        <li title="员工满意度"><a href="/topic_detail/54116a46f7405b5a05beddca">员工满意度</a></li>
                                        <li title="新员工调查"><a href="/topic_detail/54116cb2f7405b5a3497fe30">新员工调查</a></li>
                                        <a href="/topic_list" class="more">更多</a>
                                    </ul>
                                </div>
                            </div>
                            <div class="mb_con_z">
                                <div class="Mbdiv">
                                    <div class="Mbgen_title">
                                        <ul>
                                            <li>
                                                <a href="/lib">问卷库</a>
                                                <s class="arrowr"></s>
                                            </li>
                                            <li>
                                                <a href="/share/more_share_1">共享模板</a>
                                                <s class="arrowr"></s>
                                            </li>
                                            <li>
                                                <a href="/share/other_f_share_1" class="active">其他</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="Mbdiv">
                                    <div class="Mbdiv_list">
                                        <div class="tem">
                                            <div class="temtitle">
                                                <a href="/lib_detail_full/569c76fba320fc9a2b63bf78" target="_blank">
                                                    <p>关于财经专业毕业生就业意向的调查问卷</p>
                                                </a>
                                                <span>共1页14个问题</span>
                                            </div>
                                            <div class="temcon">
                                                <span>分类：<a href="/classify/other_f_1" style="display:inline;">其他</a></span> |
                                                <span>作者：ztx12315</span> |
                                                <span>被引用次数：0</span>
                                            </div>
                                            <div class="text">
                                                （以下问题仅供调查参考望如实回答）
                                            </div>
                                        </div>
                                        <div class="tem">
                                            <div class="temtitle">
                                                <a href="/lib_detail_full/569c76fba320fc9a2b63bf77" target="_blank">
                                                    <p>搜题软件的利与弊</p>
                                                </a>
                                                <span>共1页11个问题</span>
                                            </div>
                                            <div class="temcon">
                                                <span>分类：<a href="/classify/other_f_1" style="display:inline;">其他</a></span> |
                                                <span>作者：Takamina</span> |
                                                <span>被引用次数：0</span>
                                            </div>
                                            <div class="text">
                                                老师、同学您好，近期各种搜题软件（如学霸君，作业帮等）盛行，对比不同的声音也此起彼伏。我们旨在集各方观点，探究此类软件到底是利大于弊还是弊大于利。非常感谢您的参与。
                                            </div>
                                        </div>
                                        <div class="tem">
                                            <div class="temtitle">
                                                <a href="/lib_detail_full/569c76f8a320fc9a2b63bf74" target="_blank">
                                                    <p>大学生关于网剧的看法</p>
                                                </a>
                                                <span>共1页12个问题</span>
                                            </div>
                                            <div class="temcon">
                                                <span>分类：<a href="/classify/other_f_1" style="display:inline;">其他</a></span> |
                                                <span>作者：下一个自己53036550</span> |
                                                <span>被引用次数：0</span>
                                            </div>
                                            <div class="text">
                                                欢迎参加本次答题
                                            </div>
                                        </div>
                                        <div class="tem">
                                            <div class="temtitle">
                                                <a href="/lib_detail_full/569c76f7a320fc9a2b63bf72" target="_blank">
                                                    <p>关于CPI对日常生活影响的调查</p>
                                                </a>
                                                <span>共1页6个问题</span>
                                            </div>
                                            <div class="temcon">
                                                <span>分类：<a href="/classify/other_f_1" style="display:inline;">其他</a></span> |
                                                <span>作者：沉潜53035695</span> |
                                                <span>被引用次数：0</span>
                                            </div>
                                            <div class="text">
                                                欢迎参加本次答题
                                            </div>
                                        </div>
                                        <div class="tem">
                                            <div class="temtitle">
                                                <a href="/lib_detail_full/569c76ada320fc9a2b63bf70" target="_blank">
                                                    <p>高校中广告传播效果调查研究</p>
                                                </a>
                                                <span>共1页14个问题</span>
                                            </div>
                                            <div class="temcon">
                                                <span>分类：<a href="/classify/other_f_1" style="display:inline;">其他</a></span> |
                                                <span>作者：Black慕</span> |
                                                <span>被引用次数：0</span>
                                            </div>
                                            <div class="text">
                                                欢迎参加本次答题，为了对高校中广告传播的有效性进行分析，请大家帮忙填写问卷，谢谢合作！
                                            </div>
                                        </div>
                                        <div class="tem">
                                            <div class="temtitle">
                                                <a href="/lib_detail_full/569c76aca320fc9a2b63bf6f" target="_blank">
                                                    <p>合肥学院大学生环保协会之绿色社区部门会员流失率调查</p>
                                                </a>
                                                <span>共1页11个问题</span>
                                            </div>
                                            <div class="temcon">
                                                <span>分类：<a href="/classify/other_f_1" style="display:inline;">其他</a></span> |
                                                <span>作者：。远 妄🍉52951198</span> |
                                                <span>被引用次数：0</span>
                                            </div>
                                            <div class="text">
                                                欢迎参加本次答题
                                            </div>
                                        </div>
                                        <div class="tem">
                                            <div class="temtitle">
                                                <a href="/lib_detail_full/569c76a9a320fc9a2b63bf6c" target="_blank">
                                                    <p>艾森克人格问卷（EPQ）</p>
                                                </a>
                                                <span>共1页86个问题</span>
                                            </div>
                                            <div class="temcon">
                                                <span>分类：<a href="/classify/other_f_1" style="display:inline;">其他</a></span> |
                                                <span>作者：╭緈諨＂53032768</span> |
                                                <span>被引用次数：0</span>
                                            </div>
                                            <div class="text">
                                                欢迎参加本次答题请您回答下列问题，回答问题时不必过多思考，符合您时在（ ）内答“是”，不符时答“否”。问卷结果用作科研用途，我们会对您所填写的信息严格保密。答案没有对错，请根据实际情况如实回答。谢谢。
                                            </div>
                                        </div>
                                        <div class="tem">
                                            <div class="temtitle">
                                                <a href="/lib_detail_full/569c76a7a320fc9a2b63bf6b" target="_blank">
                                                    <p>兰州城市学院幼儿师范学院学前教育专业大学生网络购物与专业学习相关性调查</p>
                                                </a>
                                                <span>共1页16个问题</span>
                                            </div>
                                            <div class="temcon">
                                                <span>分类：<a href="/classify/other_f_1" style="display:inline;">其他</a></span> |
                                                <span>作者：13919090194</span> |
                                                <span>被引用次数：0</span>
                                            </div>
                                            <div class="text">
                                                感谢您的参与 本问卷非实名制 请您如实填写 谢谢
                                            </div>
                                        </div>
                                        <div class="tem">
                                            <div class="temtitle">
                                                <a href="/lib_detail_full/569c75f6a320fc9a2b63bf5a" target="_blank">
                                                    <p>宝贝地图心愿单</p>
                                                </a>
                                                <span>共1页5个问题</span>
                                            </div>
                                            <div class="temcon">
                                                <span>分类：<a href="/classify/other_f_1" style="display:inline;">其他</a></span> |
                                                <span>作者：13524177211</span> |
                                                <span>被引用次数：0</span>
                                            </div>
                                            <div class="text">
                                                欢迎您参加本次宝贝地图心愿单收集~宝贝地图计划于近期与合作的培训机构洽谈一些拼课的福利，既宝贝地图的用户可以在参与拼课的多家商户选择参加相关课程，以便找到适合自己孩子的课程或机构。所以，耽误您几分钟时间填写以下问卷：
                                            </div>
                                        </div>
                                        <div class="tem">
                                            <div class="temtitle">
                                                <a href="/lib_detail_full/569c75f0a320fc9a2b63bf54" target="_blank">
                                                    <p>德道企业基础调研问卷</p>
                                                </a>
                                                <span>共1页31个问题</span>
                                            </div>
                                            <div class="temcon">
                                                <span>分类：<a href="/classify/other_f_1" style="display:inline;">其他</a></span> |
                                                <span>作者：LIREN</span> |
                                                <span>被引用次数：0</span>
                                            </div>
                                            <div class="text">
                                                本调研问卷是上海立人工作室对德道汽车销售服务有限公司为提升员工收入、帮助员工成长、搭建员工创业平台和提升员工综合素质所做的基础信息调研，望真实做答。本调研问卷采用无记名，微信抽样的方式。
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="pagenum" id="pagenum">
                                    <span class="active">1</span> <a href="/share/other_f_share_2">2</a> <a href="/share/other_f_share_3">3</a> <a href="/share/other_f_share_4">4</a> <a href="/share/other_f_share_5">5</a> <span>...</span> <a href="/share/other_f_share_3203">3203</a>
                                    <span><a href="/share/other_f_share_2" class="next"></a></span>
                                    <span>共3203页</span>
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
		                                <div class="form-group">
		                                    <input type="text" name="email" placeholder="Email" class="form-email form-control" id="form-email">
		                                </div>
		                                <div class="form-group">
		                                    <input type="text" name="phone" placeholder="手机号" class="form-phone form-control" id="form-phone">
		                                </div>
		                                <div class="form-group">
		                                    <input type="text" name="graduate" placeholder="学校" class="form-graduate form-control" id="form-graduate">
		                                </div>
		                                <div class="form-group">
		                                    <input type="text" name="major" placeholder="专业" class="form-major form-control" id="form-major">
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
        <!-- JavaScript -->
        <script src="<%=path%>/js/jquery.min.js"></script>
        <script src="<%=path%>/js/bootstrap.min.js"></script>
        <script src="<%=path%>/js/reg/reg.js"></script>
        <script src="<%=path%>/js/main.js"></script>
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