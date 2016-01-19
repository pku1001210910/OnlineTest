
jQuery(document).ready(function() {
	
    /*
        Fullscreen background
    */
    // $.backstretch("assets/img/backgrounds/1.jpg");
    
    /*
        Form validation
    */
	var userNameUnique = false;
	
    $('.login-form input[type="text"], .login-form input[type="password"], .login-form textarea').on('focus', function() {
    	$(this).removeClass('input-error');
    });
    
    // check every one unempty
    $('.login-form').on('submit', function(e) {
    	$(this).find('input[type="text"], input[type="password"], textarea').each(function(){
    		if( $(this).val() == "" ) {
    			e.preventDefault();
    			setErrorMsg($(this), '数据不能为空');
    			return false;
    		}
    		else {
    			clearErrorMsg();
    		}
    	});
    	
    	var passwordConfirm = $("#form-password-confirm").val().trim();
    	var password = $('#form-password').val().trim();
    	if(password != passwordConfirm) {
    		e.preventDefault();
    		setErrorMsg($(this), '两次密码不一致');
    	} else {
    		clearErrorMsg();
    	}
    	
    	if(isError()) {
    		e.preventDefault();
    		return false;
    	}
    	
    	
    	
    	
    });
    
    // is error 
    var isError = function() {
    	if(!userNameUnique) {
    		setErrorMsg($(self), '用户名已存在');
    		return true;
    	}
    	if($('.error-msg-container').hasClass('hide')) {
    		return false;
    	} else {
    		return true;
    	}
    }
    
    // user name unique
    $('#form-username').on('blur', function() {	
    	var userName = $(this).val().trim();
    	if(userName === '') {
    		setErrorMsg($(this), '用户名不能为空');
    		return;
    	}
    	var self = this;
    	$.ajax({
    		type: 'post',
    		url: 'user/checkUserNameUnique.action',
    		data: {'userName': userName},
    		dataType: 'text',
    		success: function(json) {
    			var obj = $.parseJSON(json);
    			if(obj.existUser) {
    				setErrorMsg($(self), '用户名已存在');
    				userNameUnique = false;
    				return false;
    			} else {
    				userNameUnique = true;
    				if(getErrorMsg() === '用户名已存在') {
    					clearErrorMsg();
    				}
    			}
    		},
    		error: function(json) {
    			$(self).addClass('input-error');
    			return false;
    		}
    	})
	});
    
    // password confirm
    $('#form-password-confirm').on('blur', function() {
    	var passwordConfirm = $(this).val().trim();
    	var password = $('#form-password').val().trim();
    	if(password != passwordConfirm) {
    		setErrorMsg($(this), '两次密码不一致');
    	} else {
    		clearErrorMsg();
    	}
		
	});
    
    var setErrorMsg = function($element, content) {
    	$($element).addClass('input-error');
    	$('.error-msg-container').removeClass('hide');
    	$('.error-msg-container .error-msg-content').text(content);
    };
    
    var clearErrorMsg = function() {
    	$('.error-msg-container').addClass('hide');
    }
    
    var getErrorMsg = function() {
    	return $('.error-msg-container .error-msg-content').text();
    }
    
});
