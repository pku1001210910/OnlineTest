var onlineTest = onlineTest || {};
onlineTest.management = onlineTest.management || {};

/**
 * constructor
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
		// show create quiz dialog
		$('#quiz-dialog').modal({
			backdrop: 'static',
			keyboard: false
		});
		
		// initialize quiz category selection
		this.io_.getAllQuizCategories(function(data) {
			$.each(data['allCategories'], function(i, entry) {
				var categoryId = entry['categoryId'];
				var categoryName = entry['categoryName'];
				if (i === 0) {
					// first category will be the default one
					$('#quiz-category-names').data('categoryId', categoryId);
					$('#quiz-category-names').find('.default-category').text(categoryName);
				}
				var $categoryOption = $('#quiz-category-options');
				

			});
		}, this);
	};
	
	/**
	 * @private
	 */
	Quiz.prototype.bindEvent_ = function() {
		
	}
	
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