jQuery(document).ready(function() {
	var feedbackContainer = $(".feedbackContainer");
	feedbackContainer.css('display', 'none');
	var feedbackContent = $(".feedbackContent", feedbackContainer);
	feedbackContent.text("");
	
	var getUrlParameter = function getUrlParameter(sParam) {
	    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
	        sURLVariables = sPageURL.split('&'),
	        sParameterName,
	        i;

	    for (i = 0; i < sURLVariables.length; i++) {
	        sParameterName = sURLVariables[i].split('=');

	        if (sParameterName[0] === sParam) {
	            return sParameterName[1] === undefined ? true : sParameterName[1];
	        }
	    }
	};
	
	$('#btn_finish_quiz').on('click', function(event) {
		var subjectItemList = [];
		$('.question').each(function(index, element) {
			var questionElement = $(element);
			var errorElement = $('.red', questionElement);
			errorElement.text('');
			$("input[type='radio']:checked").val();
			if($("input[type='radio']", questionElement).length > 0) {
				if($("input[type='radio']:checked", questionElement).length == 0) {
					// not select one
					errorElement.text('请选择一个选项');
					event.stopPropagation();
					return false;
				} else {
					var userAnswer = {
							'quizId' : getUrlParameter('quizId'),
							'subjectId' : $("input[type='radio']", questionElement)[0].getAttribute('name'),
							'answer' : $("input[type='radio']:checked", questionElement).val()
						};
					subjectItemList.push(userAnswer);
				}
				
			} else if ($("input[type='checkbox']", questionElement).length > 0) {
				
			} 
		});
		if(subjectItemList.length != $('.question').length) {
			return false;
		}
		
		var btn = $("#btn_finish_quiz");
		btn.button('loading');
		var self = this;
		$.ajax({
			type : 'post',
			url : 'finishQuiz.action',
			data : {
				'quizId' : getUrlParameter('quizId'),
				'subjectItemList' : JSON.stringify(subjectItemList)
			},
			dataType : 'json',
			success : function(json) {
				var finishFlg = json.finishFlg;
				if(finishFlg) {
					feedbackContainer.css('display', '');
					feedbackContent.text(json.feedbackContent);
				}
				
				// btn.button('disabled');
				$("#btn_finish_quiz").attr("disabled", true);
				btn.button('reset');
				btn.hide();
				setTimeout(function() {
					$("#btn_finish_quiz").attr("disabled", true);
				}, 100);
			},
			error : function(json) {
				return false;
			}
		})
	});
});
