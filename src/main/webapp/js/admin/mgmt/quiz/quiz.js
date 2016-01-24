var onlineTest = onlineTest || {};
onlineTest.management = onlineTest.management || {};

/**
 * @constructor
 * @param status 'create' or 'update'
 */
onlineTest.management.Quiz = function(status) {
	/**
	 * @type {string}
	 */
	this.status_ = status || onlineTest.management.Quiz.Status.CREATE;
	
	/**
	 * @type {number}
	 */
	this.step_ = 1;
	
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
	 * @param {onlineTest.management.Quiz.Status} status
	 * @param {number} step
	 * @private
	 */
	Quiz.prototype.resetHeaderStatus_ = function(status, step) {
		if (status === onlineTest.management.Quiz.Status.CREATE) {
			$('#quiz-dialog-label').val('添加文章');
			if (step === 1) {
				$('#quiz-save-label').css('visibility', 'hidden');
				$('#quiz-status-group').css('visibility', 'hidden');
			}
		}
	};
	
	/**
	 * @param {onlineTest.management.Quiz.Status} status
	 * @param {number} step
	 * @private
	 */
	Quiz.prototype.resetFooterStatus_ = function(status, step) {
		
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
		this.io_.getAllQuizCategories(function(data) {
			$.each(data['allCategories'], function(i, entry) {
				var category = {};
				category.categoryId = entry['categoryId'];
				category.categoryName = entry['categoryName'];
				if (i === 0) {
					// first category will be the default one
					$('#quiz-category-names').data('categoryId', category.categoryId);
					$('#quiz-category-names').find('.default-category').text(category.categoryName);
				}
				self.createQuizCategoryOptionDom_(category, $('#quiz-category-options'));
			});
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
	 * @param {string} type
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
		
		// TODO add subject in server side
	};
	
	/**
	 * @private
	 * @param {onlineTest.management.Subject} subjectComponent
	 */
	Quiz.prototype.bindSubjectComponentEvent_ = function(subjectComponent) {
		var SubjectEventType = onlineTest.management.Subject.EventType;
		var ItemEventType = onlineTest.management.SingleChoiceSubject.EventType;
		// TODO operation in server side
		var $dom = subjectComponent.getDom();
		$dom.bind(SubjectEventType.SUBJECT_SHIFT_UP, function(event, subjectId) {
			console.log('shift subject up');
		});
		$dom.bind(SubjectEventType.SUBJECT_SHIFT_DOWN, function(event, subjectId) {
			console.log('shift subject down');
		});
		$dom.bind(SubjectEventType.SUBJECT_DELETE, function(event, subjectId) {
			console.log('delete subject');
		});
		$dom.bind(ItemEventType.SUBJECT_QUESTION_UPDATE, function(event, subjectId, question) {
			console.log('update subject question');
		});
		$dom.bind(ItemEventType.ITEM_CHOICE_UPDATE, function(event, itemId, choice) {
			console.log('update item choice');
		});
		$dom.bind(ItemEventType.ITEM_SCORE_UPDATE, function(event, itemId, score) {
			console.log('update item score');
		});
		$dom.bind(ItemEventType.ITEM_SHIFT_UP, function(event, itemId) {
			console.log('shift item up');
		});
		$dom.bind(ItemEventType.ITEM_SHIFT_DOWN, function(event, itemId) {
			console.log('shift item down');
		});
		$dom.bind(ItemEventType.ITEM_DELETE, function(event, itemId) {
			console.log('delete item');
		});
		$dom.bind(ItemEventType.ITEM_ADD, function(event, subjectId) {
			console.log('add item');
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
			if (self.status === onlineTest.management.Quiz.Status.CREATE) {
				// TODO create quiz in server side
			} else {
				
			}
			if (self.step_ === 1) {
				$('#add-quiz-step-1').css('display', 'none');
				$('#add-quiz-step-2').css('display', 'block');
				$('#add-quiz-step-3').css('display', 'none');
				self.step_ = 2;
			} else if (self.step_ === 2) {
				$('#add-quiz-step-1').css('display', 'none');
				$('#add-quiz-step-2').css('display', 'none');
				$('#add-quiz-step-3').css('display', 'block');
				self.step_ = 3;
			} else {
				$('#add-quiz-step-1').css('display', 'none');
				$('#add-quiz-step-2').css('display', 'none');
				$('#add-quiz-step-3').css('display', 'none');
			}
		});
		
		// bind category change event, delegate event
		$('#quiz-category-options').on('click', 'a', function() {
			var categoryName = $(this).text();
			var categoryId = $(this).data('categoryId');
			$('#quiz-category-names').data('categoryId', categoryId);
			$('#quiz-category-names').find('.default-category').text(categoryName);
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
		// unbind subject type collapse/expand event
		$('#collapse-type').unbind('click');
		// unbind subject type click event
		$('.subject-type').unbind('click');
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
	};
	
})(onlineTest.management.Quiz);

onlineTest.management.Quiz.IO = function() {
};

(function(IO) {
	/**
	 * @param {Function} callback
	 */
	IO.prototype.getAllQuizCategories = function(callback) {
		$.getJSON('./getAllQuizCategories.action', null, callback);
	};
})(onlineTest.management.Quiz.IO);