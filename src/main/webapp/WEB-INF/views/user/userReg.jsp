<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
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