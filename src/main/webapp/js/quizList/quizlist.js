var checkQuizOwner = function(quizId, userName) {
	if(userName == -1) {
		$("#needLoginModal").modal('show');
	} else {
    	var self = this;
    	$.ajax({
    		type: 'post',
    		url: 'checkUserQuizOwner.action',
    		data: {'userName': userName,
    			'quizId': quizId
    		},
    		dataType: 'text',
    		success: function(json) {
    			var obj = $.parseJSON(json);
    			if(obj.quizValid) {
    				alert('已付费');
    				window.location.href = "../quiz/quizDetail.action?quizId=" + quizId;
    			} else {
    				alert('未付费');
    				window.location.href = "../quiz/quizDetail.action?quizId=" + quizId;
    			}
    		},
    		error: function(json) {
    			alert('error2')
    			return false;
    		}
    	})
	}
};


