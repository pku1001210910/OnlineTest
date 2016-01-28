var onlineTest = onlineTest || {};
onlineTest.management = onlineTest.management || {};

/**
 * @constructor
 * @param status 'create' or 'update'
 */
onlineTest.management.Quiz = function(status, quizId) {
	/**
	 * @type {string}
	 */
	this.status_ = status || onlineTest.management.Quiz.Status.CREATE;
	
	/**
	 * @type {number}
	 */
	this.step_ = 1;
	
	/**
	 * @type {number}
	 */
	this.quizId_ = quizId;
	
	/**
	 * @type {onlineTest.management.Quiz.IO}
	 */
	this.io_ = new onlineTest.management.Quiz.IO();
	
	/**
	 * @type {onlineTest.management.SubjectManager}
	 */
	this.subjectManager_ = new onlineTest.management.SubjectManager();
	
	this.initComponent_();
};

/**
 * @enum
 */
onlineTest.management.Quiz.Status = {
	CREATE: 'create',
	UPDATE: 'update'
};

(function(Quiz) {
	
	Quiz.prototype.initComponent_ = function() {
		this.resetHeaderStatus_(this.status_, this.step_);
		this.resetFooterStatus_(this.status_, this.step_);
		this.show_();
		this.bindEvent_();
	};
	
	Quiz.prototype.destroy = function() {
		this.restoreUI_();
		this.unbindEvent_();
	};
	
	/**
	 * Setup the component with data
	 * @param {Object} quizData
	 */
	Quiz.prototype.setup = function(quizData) {
		debugger;
		var self = this;
		$('#quiz-dialog').data('quizId', quizData['quizId']);
		$('#quiz-title').val(quizData['title']);
		$('#quiz-description').val(quizData['description']);
		$('#quiz-category-names').data('categoryId', quizData['category']);
		$('.default-category').text(quizData['categoryName']);
		$('#need-charge-toggle').prop('checked', quizData['needCharge'] === 1);
		if ($('#need-charge-toggle').prop('checked')) {
			$('#quiz-price-group').css('display', 'block');
		} else {
			$('#quiz-price-group').css('display', 'none');
		}
		
		$('#quiz-price').val(quizData['price']);
		$('#quiz-status').text(quizData['status'] === 1 ? '已发布' : '未发布');
		
		$.each(quizData['quizSubjects'], function(i, subjectData) {
			self.addSubjectWithData(subjectData);
		});
		
		if (quizData['feedbacks'] && quizData['feedbacks'].length > 0) {
			$('.feeedback-container').empty();
			$.each(quizData['feedbacks'], function(i, feedbackData) {
				var $feedback = self.createFeedbackDom_();
				$('.feeedback-container').append($feedback);
				$feedback.data('feedbackId', feedbackData['feedbackId']);
				$feedback.find('.feedback-content').val(feedbackData['content']);
				$feedback.find('.score-from').val(feedbackData['scoreFrom']);
				$feedback.find('.score-to').val(feedbackData['scoreTo']);
			});
		}
		// TODO
	};
	
	/**
	 * @param {onlineTest.management.Quiz.Status} status
	 * @param {number} step
	 * @private
	 */
	Quiz.prototype.resetHeaderStatus_ = function(status, step) {
		if (status === onlineTest.management.Quiz.Status.CREATE) {
			$('#quiz-dialog-label').text('添加测试');
			$('#quiz-save-label').css('visibility', 'hidden');
			$('#quiz-status-group').css('visibility', 'hidden');
		} else {
			$('#quiz-dialog-label').text('修改测试');
			$('#quiz-save-label').css('visibility', 'visible');
			$('#quiz-status-group').css('visibility', 'visible');
		}
	};
	
	/**
	 * @param {onlineTest.management.Quiz.Status} status
	 * @param {number} step
	 * @private
	 */
	Quiz.prototype.resetFooterStatus_ = function(status, step) {
		if (status === onlineTest.management.Quiz.Status.CREATE) {
			$('#next-btn').text('创建');
		} else {
			if (step === 3) {
				$('#next-btn').text('发布测试');
			} else {
				$('#next-btn').text('下一步');
			}
		}
		
		if (step === 1) {
			$('#prev-btn').css('display', 'none');
		} else {
			$('#prev-btn').css('display', 'inline');
		}
	};
	
	/**
	 * @private
	 */
	Quiz.prototype.show_ = function() {
		var self = this;
		// show create quiz dialog
		$('#quiz-dialog').modal({
			backdrop: 'static',
			keyboard: false
		});
		
		// initialize quiz category selection
		var quizId = this.quizId_;
		this.io_.getAllQuizCategories(quizId, function(data) {
			var allCategories = data['allCategory'];
			$.each(data['allCategory'], function(i, entry) {
				var category = {};
				category.categoryId = entry['categoryId'];
				category.categoryName = entry['categoryName'];
				self.createQuizCategoryOptionDom_(category, $('#quiz-category-options'));
			});
			
			var currentCategory = data['currentCategory'];
			$('#quiz-category-names').data('categoryId', currentCategory.categoryId);
			$('#quiz-category-names').find('.default-category').text(currentCategory.categoryName);
		}, this);
	};
	
	/**
	 * @param {Object} category
	 * @param {HTMLDocument} parent
	 * @return {HTMLDocument}
	 */
	Quiz.prototype.createQuizCategoryOptionDom_ = function(category, parent) {
		var categoryId = category.categoryId;
		var categoryName = category.categoryName;
		var $option = $('<li></li>');
		var $child = $('<a href="#"></a>').data('categoryId', categoryId).text(categoryName);
		$child.appendTo($option);
		$option.appendTo(parent);
	};
	
	/**
	 * @private
	 * @return {HTMLDocument}
	 */
	Quiz.prototype.createFeedbackDom_ = function() {
		var $feedback = $('<div class="feedback-item"></div>').append('<h5></h5>')
			.append('<textarea class="form-control feedback-content" rows="5"></textarea>')
			.append('<div class="feedback-score-scope">')
			.append('最小分值： <input class="score-from" type="number" name="item-score-min" min="0" max="100" value="0"/>')
			.append('&nbsp;&nbsp;&nbsp;&nbsp;最大分值： <input class="score-to" type="number" name="item-score-min" min="0" max="100" value="0"/>')
			.append('</div><div>')
			.append('<i class="add-feedback glyphicon glyphicon-plus-sign quiz-icon-gray"></i>')
			.append('<i class="remove-feedback glyphicon glyphicon-trash quiz-icon-gray"></i>')
			.append('</div>');
		return $feedback;
	};
	
	/**
	 * @private
	 */
	Quiz.prototype.rearrangeFeedbackTitle_ = function() {
		var $allFeedbacks = $('#quiz-feedback-container').find('h5');
		var i = 1;
		$allFeedbacks.each(function() {
			$(this).text("第" + i + "条反馈结果");
			i++;
		});
	};
	
	/**
	 * @param {number} type
	 * @param {Object} subjectData
	 */
	Quiz.prototype.addSubjectWithData = function(subjectData) {
		var subjectComponent = this.subjectManager_.getSubjectComponent(subjectData['type']);
		if (!subjectComponent) {
			return;
		}
		var count = $('#subject-item-body-panel').find('.subject-container').size();
		var $subjectParent = $('#subject-item-body-panel');
		subjectComponent.createDom($subjectParent, count + 1, subjectData['subjectItems'].length);
		subjectComponent.initialize();
		this.bindSubjectComponentEvent_(subjectComponent);
		subjectComponent.applyData(subjectData);
	};
	
	/**
	 * @param {number} type
	 */
	Quiz.prototype.addSubject = function(type) {
		var subjectComponent = this.subjectManager_.getSubjectComponent(type);
		var count = $('#subject-item-body-panel').find('.subject-container').size();
		var $subjectParent = $('#subject-item-body-panel');
		subjectComponent.createDom($subjectParent, count + 1);
		subjectComponent.initialize();
		this.bindSubjectComponentEvent_(subjectComponent);
		
		// scroll to the new subject
		var $scrollContainer = $('.subject-item-panel');
		var $newSubject = subjectComponent.getDom();
		$scrollContainer.scrollTop(
			$newSubject.offset().top - $scrollContainer.offset().top + $scrollContainer.scrollTop()
		);
		// add subject in server side
		var quizId = $('#quiz-dialog').data('quizId');
		if (!!quizId) {
			var subject = subjectComponent.getData();
			subject.quizId = quizId;
			this.io_.addSubject(subject.quizId, subject.type, subject.question, subject.items, function(result) {
				subjectComponent.applyData(result);
			});
		}
	};
	
	/**
	 * @private
	 * @param {onlineTest.management.Subject} subjectComponent
	 */
	Quiz.prototype.bindSubjectComponentEvent_ = function(subjectComponent) {
		var self = this;
		var quizId = $('#quiz-dialog').data('quizId');
		if (!quizId) {
			return;
		}
		var SubjectEventType = onlineTest.management.Subject.EventType;
		var ItemEventType = onlineTest.management.SingleChoiceSubject.EventType;
		// operation in server side
		var $dom = subjectComponent.getDom();
		$dom.bind(SubjectEventType.SUBJECT_SHIFT_UP, function(event, subjectId) {
			self.io_.shiftSubjectUp(quizId, subjectId);
		});
		$dom.bind(SubjectEventType.SUBJECT_SHIFT_DOWN, function(event, subjectId) {
			self.io_.shiftSubjectDown(quizId, subjectId);
		});
		$dom.bind(SubjectEventType.SUBJECT_DELETE, function(event, subjectId) {
			self.io_.deleteSubject(quizId, subjectId);
		});
		$dom.bind(ItemEventType.SUBJECT_QUESTION_UPDATE, function(event, subjectId, question) {
			self.io_.updateSubjectQuestion(subjectId, question);
		});
		$dom.bind(ItemEventType.ITEM_CHOICE_UPDATE, function(event, itemId, choice) {
			self.io_.updateItemChoice(itemId, choice);
		});
		$dom.bind(ItemEventType.ITEM_SCORE_UPDATE, function(event, itemId, score) {
			self.io_.updateItemScore(itemId, score);
		});
		$dom.bind(ItemEventType.ITEM_SHIFT_UP, function(event, subjectId, itemId) {
			self.io_.shiftItemUp(subjectId, itemId);
		});
		$dom.bind(ItemEventType.ITEM_SHIFT_DOWN, function(event, subjectId, itemId) {
			self.io_.shiftItemDown(subjectId, itemId);
		});
		$dom.bind(ItemEventType.ITEM_DELETE, function(event, subjectId, itemId) {
			self.io_.deleteItem(subjectId, itemId);
		});
		$dom.bind(ItemEventType.ITEM_ADD, function(event, subjectId, choice, score, $item) {
			self.io_.addItem(subjectId, choice, score, function(itemId){
				$item.data('itemId', itemId);
			});
		});
	};
	
	/**
	 * @private
	 */
	Quiz.prototype.bindEvent_ = function() {
		var self = this;
		// bind close dialog event
		$('#close-btn').click(function() {
			self.destroy();
		});
		
		$('#next-btn').click(function() {
			if (self.status_ === onlineTest.management.Quiz.Status.CREATE) {
				// create quiz for first time
				var title = $('#quiz-title').val();
				var description = $('#quiz-description').val();
				var categoryId = $('#quiz-category-names').data('categoryId');
				var needCharge = $('#need-charge-toggle').prop('checked') ? 1 : 0;
				var price = 0;
				if ($('#quiz-price').val() !== "" && !isNaN($('#quiz-price').val())) {
					price = parseFloat($('#quiz-price').val());
				}					
				self.io_.createQuiz(title, description, categoryId, needCharge, price, function(quizId) {
					// set quizId to element
					$('#quiz-dialog').data('quizId', quizId);
					self.status_ = onlineTest.management.Quiz.Status.UPDATE;
					// jump to step2 in callback when create
					$('#add-quiz-step-1').css('display', 'none');
					$('#add-quiz-step-2').css('display', 'block');
					$('#add-quiz-step-3').css('display', 'none');
					$('#quiz-caption').text($('#quiz-title').val());
					self.step_ = 2;
					self.resetHeaderStatus_(self.status_, self.step_);
					self.resetFooterStatus_(self.status_, self.step_);
				});
			} else {
				if (self.step_ === 1) {
					$('#add-quiz-step-1').css('display', 'none');
					$('#add-quiz-step-2').css('display', 'block');
					$('#add-quiz-step-3').css('display', 'none');
					$('#quiz-caption').text($('#quiz-title').val());
					self.step_ = 2;
				} else if (self.step_ === 2) {
					$('#add-quiz-step-1').css('display', 'none');
					$('#add-quiz-step-2').css('display', 'none');
					$('#add-quiz-step-3').css('display', 'block');
					self.step_ = 3;
				} else {
					var quizId = $('#quiz-dialog').data('quizId');
					if (!!quizId) {
						self.io_.publishQuiz(quizId);
					}
					$('#close-btn').click();
				}
				self.resetHeaderStatus_(self.status_, self.step_);
				self.resetFooterStatus_(self.status_, self.step_);
			};
		});
		
		$('#prev-btn').click(function() {
			if (self.step_ === 1) {
				return;
			} else if (self.step_ === 2) {
				$('#add-quiz-step-1').css('display', 'block');
				$('#add-quiz-step-2').css('display', 'none');
				$('#add-quiz-step-3').css('display', 'none');
				self.step_ = 1;
			} else {
				$('#add-quiz-step-1').css('display', 'none');
				$('#add-quiz-step-2').css('display', 'block');
				$('#add-quiz-step-3').css('display', 'none');
				self.step_ = 2;
			}
			self.resetHeaderStatus_(self.status_, self.step_);
			self.resetFooterStatus_(self.status_, self.step_);
		});
		
		// bind category change event, delegate event
		$('#quiz-category-options').on('click', 'a', function() {
			var categoryName = $(this).text();
			var categoryId = $(this).data('categoryId');
			$('#quiz-category-names').data('categoryId', categoryId);
			$('#quiz-category-names').find('.default-category').text(categoryName);
			
			// update in server side
			var quizId = $('#quiz-dialog').data('quizId');
			if (!!quizId) {
				self.io_.updateQuizCategory(quizId, categoryId);
			}
		});
		
		// bind needCharge change event
		$('#need-charge-toggle').click(function() {
			// need charge
			if ($(this).prop('checked')) {
				$('#quiz-price-group').css('display', 'block');
			} else {
				$('#quiz-price-group').css('display', 'none');
			}
		});
		
		// bind subject type collapse/expand event
		$('#collapse-type').click(function() {
			if ($(this).hasClass('glyphicon-minus')) {
				$('#subject-type-body-panel').slideUp();
			} else {
				$('#subject-type-body-panel').slideDown();
			}
			$(this).toggleClass('glyphicon-minus glyphicon-plus');
		});
		
		// bind subject type click event
		$('.subject-type').click(function() {
			var subjectType = $(this).data('type');
			self.addSubject(subjectType);
		});
		
		// bind feedback event
		$('#quiz-feedback-container').on('change', 'input, textarea', function(event) {
			var $feedback = $(event.target).parents('.feedback-item');
			var content = $feedback.find('.feedback-content').val();
			var scoreFrom = 0;
			if ($feedback.find('.score-from').val() !== "" && !isNaN($feedback.find('.score-from').val())) {
				scoreFrom = parseFloat($feedback.find('.score-from').val());
			}
			var scoreTo = 0;
			if ($feedback.find('.score-to').val() !== "" && !isNaN($feedback.find('.score-to').val())) {
				scoreTo = parseFloat($feedback.find('.score-to').val());
			}
			
			var quizId = $('#quiz-dialog').data('quizId');
			if (!quizId) {
				return;
			}
			var feedbackId = $feedback.data('feedbackId');
			if (!feedbackId) {
				// add feedback
				self.io_.addFeedback(quizId, content, scoreFrom, scoreTo, function(feedbackId) {
					$feedback.data('feedbackId', feedbackId);
				});
			} else {
				self.io_.updateFeedback(feedbackId, content, scoreFrom, scoreTo);
			}
		});
		
		$('#quiz-feedback-container').on('click', '.add-feedback', function(event) {
			var $prev = $(event.target).parents('.feedback-item');
			var $feedback = self.createFeedbackDom_();
			$feedback.insertAfter($prev);
			$feedback.find('textarea').focus();
			self.rearrangeFeedbackTitle_();
			
			// scroll to the new subject
			var $scrollContainer = $('#quiz-feedback-container');
			$scrollContainer.scrollTop(
				$feedback.offset().top - $scrollContainer.offset().top + $scrollContainer.scrollTop()
			);
			
			var quizId = $('#quiz-dialog').data('quizId');
			if (!quizId) {
				return;
			}
			var content = $feedback.find('.feedback-content').val();
			var scoreFrom = 0;
			if ($feedback.find('.score-from').val() !== "" && !isNaN($feedback.find('.score-from').val())) {
				scoreFrom = parseFloat($feedback.find('.score-from').val());
			}
			var scoreTo = 0;
			if ($feedback.find('.score-to').val() !== "" && !isNaN($feedback.find('.score-to').val())) {
				scoreTo = parseFloat($feedback.find('.score-to').val());
			}
			self.io_.addFeedback(quizId, content, scoreFrom, scoreTo, function(feedbackId) {
				$feedback.data('feedbackId', feedbackId);
			});
		});
		
		$('#quiz-feedback-container').on('click', '.remove-feedback', function(event) {
			var $feedback = $(event.target).parents('.feedback-item');
			var feedbackId = $feedback.data('feedbackId');
			if (!!feedbackId) {
				self.io_.deleteFeedback(feedbackId);
			}
			
			$feedback.remove();
			self.rearrangeFeedbackTitle_();
		});
		
		$('#quiz-title, #quiz-description, #need-charge-toggle, #quiz-price').change(function(event) {
			if (self.status_ === onlineTest.management.Quiz.Status.UPDATE) {
				var quizId = $('#quiz-dialog').data('quizId');
				if (!!quizId) {
					var title = $('#quiz-title').val();
					var description = $('#quiz-description').val();
					var needCharge = $('#need-charge-toggle').prop('checked') ? 1 : 0;
					var price = 0;
					if ($('#quiz-price').val() !== "" && !isNaN($('#quiz-price').val())) {
						price = parseFloat($('#quiz-price').val());
					}
					self.io_.updateQuizMeta(quizId, title, description, needCharge, price);
				}
			}
		});
	};
	
	/**
	 * @private
	 */
	Quiz.prototype.unbindEvent_ = function() {
		// unbind category change event
		$('#quiz-category-options').unbind('click');
		// unbind needCharge change event
		$('#need-charge-toggle').unbind('click');
		// unbind close button event
		$('#close-btn').unbind('click');
		// unbind next button event
		$('#next-btn').unbind('click');
		// unbind prev button click
		$('#prev-btn').unbind('click');
		// unbind subject type collapse/expand event
		$('#collapse-type').unbind('click');
		// unbind subject type click event
		$('.subject-type').unbind('click');
		// unbind feedback blur event
		$('#quiz-feedback-container').off('blur', 'input, textarea');
		// unbind feedback add event
		$('#quiz-feedback-container').off('click', '.add-feedback');
		// unbind feedback delete event
		$('#quiz-feedback-container').off('click', '.remove-feedback');
		// unbind update quiz event
		$('#quiz-title, #quiz-description, #need-charge-toggle, #quiz-price').unbind('change');
	};
	
	/**
	 * @private
	 */
	Quiz.prototype.restoreUI_ = function() {
		// clear category
		$('#quiz-category-options').empty();
		// reset navi dialog
		$('#add-quiz-step-1').css('display', 'block');
		$('#add-quiz-step-2').css('display', 'none');
		$('#add-quiz-step-3').css('display', 'none');
		// reset need charge checkbox
		$('#need-charge-toggle').prop('checked', false);
		$('#quiz-price-group').css('display', 'none');
		// reset subject type collaps/expand status
		$('#collapse-type').removeClass('glyphicon-plus');
		$('#collapse-type').addClass('glyphicon-minus');
		$('#subject-type-body-panel').css('display', 'block');
		// empty subject container
		$('#subject-item-body-panel').empty();
		// empty quiz meta info
		$('#quiz-title').val('');
		$('#quiz-description').val('');
		$('#need-charge-toggle').prop('checked', false);
		$('#quiz-price').val('');
		$('#quiz-price-group').css('display', 'none');
	};
	
})(onlineTest.management.Quiz);

onlineTest.management.Quiz.IO = function() {
};

(function(IO) {
	/**
	 * @param {Function} callback
	 */
	IO.prototype.getAllQuizCategories = function(quizId, callback) {
		var self = this;
		var quizMeta = {
			'quizId': quizId
		};
		$.ajax({
			url: './getAllQuizCategories.action',
			type: 'post',
			data: quizMeta,
			success:function (data) {
				var result = JSON.parse(data['category']);
				if ($.isFunction(callback)) {
					callback(result);
				}
			},
			error: function(xhr) {
				self.onError_();
			}
		});
	};
	
	/**
	 * @param {string} title
	 * @param {string} description
	 * @param {number} category
	 * @param {number} needCharge
	 * @param {number} price
	 * @param {Function} callback
	 */
	IO.prototype.createQuiz = function(title, description, category, needCharge, price, callback) {
		var self = this;
		var quizMeta = {
			'title': title,
			'description': description,
			'category': category,
			'needCharge': needCharge,
			'price': price
		};
		this.beforeRequest_();
		$.ajax({
			url: './createQuiz.action',
			type: 'post',
			data: quizMeta,
			dataType: 'json',
			success:function (data) {
				var result = JSON.parse(data['result']);
				if ($.isFunction(callback)) {
					callback(result['quizId']);
				}
				self.onSuccess_();
			},
			error: function(xhr) {
				self.onError_();
			}
		});
	};
	
	/**
	 * @param {number} quizId
	 */
	IO.prototype.publishQuiz = function(quizId) {
		var self = this;
		var quizMeta = {
			'quizId': quizId
		};
		this.beforeRequest_();
		$.ajax({
			url: './publishQuiz.action',
			type: 'post',
			data: quizMeta,
			success:function () {
				self.onSuccess_();
			},
			error: function(xhr) {
				self.onError_();
			}
		});
	};
	
	/**
	 * Only the attributes in parameter can be updated using this method
	 * @param {number} quizId
	 * @param {string} title
	 * @param {string} description
	 * @param {number} needCharge
	 * @param {number} price
	 * @param {Function} callback
	 */
	IO.prototype.updateQuizMeta = function(quizId, title, description, needCharge, price, callback) {
		var self = this;
		var quizMeta = {
			'quizId': quizId,
			'title': title,
			'description': description,
			'needCharge': needCharge,
			'price': price
		};
		this.beforeRequest_();
		$.ajax({
			url: './updateQuizMeta.action',
			type: 'post',
			data: quizMeta,
			success:function () {
				if ($.isFunction(callback)) {
					callback();
				}
				self.onSuccess_();
			},
			error: function(xhr) {
				self.onError_();
			}
		});
	};
	
	/**
	 * @param {number} quizId
	 * @param {number} category
	 * @param {Function} callback
	 */
	IO.prototype.updateQuizCategory = function(quizId, category, callback) {
		var self = this;
		var quizCategory = {
			'quizId': quizId,
			'category': category
		};
		this.beforeRequest_();
		$.ajax({
			url: './updateQuizCategory.action',
			type: 'post',
			data: quizCategory,
			success:function () {
				if ($.isFunction(callback)) {
					callback();
				}
				self.onSuccess_();
			},
			error: function(xhr) {
				self.onError_();
			}
		});
	};
	
	/**
	 * @param {number} quizId
	 * @param {number} type
	 * @param {string} question
	 * @param {Array.<{choice: string, score: number}>} items
	 * @param {Function} callback
	 */
	IO.prototype.addSubject = function(quizId, type, question, items, callback) {
		var self = this;
		var subject = {
			'quizId': quizId,
			'type': type,
			'question': question,
			'items': JSON.stringify(items)
		};
		this.beforeRequest_();
		$.ajax({
			url: './addSubject.action',
			type: 'post',
			data: subject,
			success:function (data) {
				var result = JSON.parse(data['result']);
				if ($.isFunction(callback)) {
					callback(result);
				}
				self.onSuccess_();
			},
			error: function(xhr) {
				self.onError_();
			}
		});
	};
	
	/**
	 * @param {number} quizId
	 * @param {number} subjectId
	 * @param {Function} callback
	 */
	IO.prototype.deleteSubject = function(quizId, subjectId, callback) {
		var self = this;
		var subject = {
			'quizId': quizId,
			'subjectId': subjectId
		};
		this.beforeRequest_();
		$.ajax({
			url: './deleteSubject.action',
			type: 'post',
			data: subject,
			success:function () {
				if ($.isFunction(callback)) {
					callback();
				}
				self.onSuccess_();
			},
			error: function(xhr) {
				self.onError_();
			}
		});
	};
	
	/**
	 * @param {number} quizId
	 * @param {number} subjectId
	 * @param {Function} callback
	 */
	IO.prototype.shiftSubjectUp = function(quizId, subjectId, callback) {
		var self = this;
		var subject = {
			'quizId': quizId,
			'subjectId': subjectId,
		};
		this.beforeRequest_();
		$.ajax({
			url: './shiftSubjectUp.action',
			type: 'post',
			data: subject,
			success:function () {
				if ($.isFunction(callback)) {
					callback();
				}
				self.onSuccess_();
			},
			error: function(xhr) {
				self.onError_();
			}
		});
	};
	
	/**
	 * @param {number} quizId
	 * @param {number} subjectId
	 * @param {Function} callback
	 */
	IO.prototype.shiftSubjectDown = function(quizId, subjectId, callback) {
		var self = this;
		var subject = {
			'quizId': quizId,
			'subjectId': subjectId,
		};
		this.beforeRequest_();
		$.ajax({
			url: './shiftSubjectDown.action',
			type: 'post',
			data: subject,
			success:function () {
				if ($.isFunction(callback)) {
					callback();
				}
				self.onSuccess_();
			},
			error: function(xhr) {
				self.onError_();
			}
		});
	};
	
	/**
	 * @param {number} subjectId
	 * @param {string} question
	 * @param {Function} callback
	 */
	IO.prototype.updateSubjectQuestion = function(subjectId, question, callback) {
		var self = this;
		var subject = {
			'subjectId': subjectId,
			'question': question
		};
		this.beforeRequest_();
		$.ajax({
			url: './updateSubjectQuestion.action',
			type: 'post',
			data: subject,
			success:function () {
				if ($.isFunction(callback)) {
					callback();
				}
				self.onSuccess_();
			},
			error: function(xhr) {
				self.onError_();
			}
		});
	};
	
	/**
	 * @param {number} subjectId
	 * @param {string} choice
	 * @param {number} score
	 * @param {Function} callback
	 */
	IO.prototype.addItem = function(subjectId, choice, score, callback) {
		var self = this;
		var item = {
			'subjectId': subjectId,
			'choice': choice,
			'score': score,
		};
		this.beforeRequest_();
		$.ajax({
			url: './addItem.action',
			type: 'post',
			data: item,
			success:function (data) {
				var result = JSON.parse(data['result']);
				if ($.isFunction(callback)) {
					callback(result['itemId']);
				}
				self.onSuccess_();
			},
			error: function(xhr) {
				self.onError_();
			}
		});
	};
	
	/**
	 * @param {number} itemId
	 * @param {string} choice
	 * @param {Function} callback
	 */
	IO.prototype.updateItemChoice = function(itemId, choice, callback) {
		var self = this;
		var item = {
			'itemId': itemId,
			'choice': choice
		};
		this.beforeRequest_();
		$.ajax({
			url: './updateItemChoice.action',
			type: 'post',
			data: item,
			success:function () {
				if ($.isFunction(callback)) {
					callback();
				}
				self.onSuccess_();
			},
			error: function(xhr) {
				self.onError_();
			}
		});
	};
	
	/**
	 * @param {number} itemId
	 * @param {number} score
	 * @param {Function} callback
	 */
	IO.prototype.updateItemScore = function(itemId, score, callback) {
		var self = this;
		var item = {
			'itemId': itemId,
			'score': score
		};
		this.beforeRequest_();
		$.ajax({
			url: './updateItemScore.action',
			type: 'post',
			data: item,
			success:function () {
				if ($.isFunction(callback)) {
					callback();
				}
				self.onSuccess_();
			},
			error: function(xhr) {
				self.onError_();
			}
		});
	};
	
	/**
	 * @param {number} subjectId
	 * @param {number} itemId
	 * @param {Function} callback
	 */
	IO.prototype.deleteItem = function(subjectId, itemId, callback) {
		var self = this;
		var item = {
			'subjectId': subjectId,
			'itemId': itemId
		};
		this.beforeRequest_();
		$.ajax({
			url: './deleteItem.action',
			type: 'post',
			data: item,
			success:function () {
				if ($.isFunction(callback)) {
					callback();
				}
				self.onSuccess_();
			},
			error: function(xhr) {
				self.onError_();
			}
		});
	};
	
	/**
	 * @param {number} subjectId
	 * @param {number} itemId
	 * @param {Function} callback
	 */
	IO.prototype.shiftItemUp = function(subjectId, itemId, callback) {
		var self = this;
		var item = {
			'subjectId': subjectId,
			'itemId': itemId
		};
		this.beforeRequest_();
		$.ajax({
			url: './shiftItemUp.action',
			type: 'post',
			data: item,
			success:function () {
				if ($.isFunction(callback)) {
					callback();
				}
				self.onSuccess_();
			},
			error: function(xhr) {
				self.onError_();
			}
		});
	};
	
	/**
	 * @param {number} subjectId
	 * @param {number} itemId
	 * @param {Function} callback
	 */
	IO.prototype.shiftItemDown = function(subjectId, itemId, callback) {
		var self = this;
		var item = {
			'subjectId': subjectId,
			'itemId': itemId
		};
		this.beforeRequest_();
		$.ajax({
			url: './shiftItemDown.action',
			type: 'post',
			data: item,
			success:function () {
				if ($.isFunction(callback)) {
					callback();
				}
				self.onSuccess_();
			},
			error: function(xhr) {
				self.onError_();
			}
		});
	};
	
	/**
	 * @param {number} quizId
	 * @param {string} content
	 * @param {number} scoreFrom
	 * @param {number} scoreTo
	 * @param {Function} callback
	 */
	IO.prototype.addFeedback = function(quizId, content, scoreFrom, scoreTo, callback) {
		var self = this;
		var feedback = {
			'quizId': quizId,
			'content': content,
			'scoreFrom': scoreFrom,
			'scoreTo': scoreTo
		};
		this.beforeRequest_();
		$.ajax({
			url: './addFeedback.action',
			type: 'post',
			data: feedback,
			success:function (data) {
				var result = JSON.parse(data['result']);
				if ($.isFunction(callback)) {
					callback(result['feedbackId']);
				}
				self.onSuccess_();
			},
			error: function(xhr) {
				self.onError_();
			}
		});
	};
	
	/**
	 * @param {number} fedbackId
	 * @param {string} content
	 * @param {number} scoreFrom
	 * @param {number} scoreTo
	 * @param {Function} callback
	 */
	IO.prototype.updateFeedback = function(feedbackId, content, scoreFrom, scoreTo, callback) {
		var self = this;
		var feedback = {
			'feedbackId': feedbackId,
			'content': content,
			'scoreFrom': scoreFrom,
			'scoreTo': scoreTo
		};
		this.beforeRequest_();
		$.ajax({
			url: './updateFeedback.action',
			type: 'post',
			data: feedback,
			success:function () {
				if ($.isFunction(callback)) {
					callback();
				}
				self.onSuccess_();
			},
			error: function(xhr) {
				self.onError_();
			}
		});
	};
	
	/**
	 * @param {number} feedbackId
	 * @param {Function} callback
	 */
	IO.prototype.deleteFeedback = function(feedbackId, callback) {
		var self = this;
		var feedback = {
			'feedbackId': feedbackId
		};
		this.beforeRequest_();
		$.ajax({
			url: './deleteFeedback.action',
			type: 'post',
			data: feedback,
			success:function () {
				if ($.isFunction(callback)) {
					callback();
				}
				self.onSuccess_();
			},
			error: function(xhr) {
				self.onError_();
			}
		});
	};
	
	/**
	 * @private
	 */
	IO.prototype.beforeRequest_ = function() {
		$('#quiz-dialog').showLoading();
	};
	
	/**
	 * @private
	 */
	IO.prototype.onSuccess_ = function() {
		this.updateSaveDateTime_();
		$('#quiz-dialog').hideLoading();
	};
	
	/**
	 * @private
	 */
	IO.prototype.onError_ = function() {
		$('#quiz-dialog').hideLoading();
		$('#request-error').dialog({
			resizable: false,
			height: 160,
			modal: true,
			buttons: {
				"确定": function() {
					$(this).dialog( "close" );
				}
			}
		});
	};
	
	/**
	 * @private
	 */
	IO.prototype.updateSaveDateTime_ = function() {
		if ($('#quiz-save-label')) {
			var date = new Date();
			$('#quiz-save-label').text('系统已为您自动保存所有的更改 ' + date.toLocaleString());
		}
	};
})(onlineTest.management.Quiz.IO);