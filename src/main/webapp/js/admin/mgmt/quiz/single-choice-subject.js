var onlineTest = onlineTest || {};
onlineTest.management = onlineTest.management || {};

/**
 * @constructor
 */
onlineTest.management.SingleChoiceSubject = function() {
	onlineTest.management.Subject.call(this);
	
	this.defaultSubjectQuestion = '请点击填写测试问题';
	this.defaultItemChoice = '请点击填写选项';
	this.defaultScore = 0;
	this.defaultItemCount = 2;
};
onlineTest.management.SingleChoiceSubject.prototype = new onlineTest.management.Subject();

/**
 * @enum
 */
onlineTest.management.SingleChoiceSubject.EventType = {
	SUBJECT_QUESTION_UPDATE: 'subjectQuestionUpdate',
	ITEM_CHOICE_UPDATE: 'itemChoiceUpdate',
	ITEM_SCORE_UPDATE: 'itemScoreUpdate',
	ITEM_SHIFT_UP: 'itemShiftUp',
	ITEM_SHIFT_DOWN: 'itemShiftDown',
	ITEM_DELETE: 'itemDelete',
	ITEM_ADD: 'itemAdd'
};

(function(SingleChoiceSubject) {
	/**
	 * @override
	 */
	SingleChoiceSubject.prototype.createSubjectDom = function() {
		return $('<div class="subject-question">' + this.defaultSubjectQuestion + '</div>');
	};
	
	/**
	 * @override
	 */
	SingleChoiceSubject.prototype.createItemDom = function(itemCount) {
		var $itemContainer = $('<div class="subject-items"></div>');
		// create two items by default
		var itemCount = itemCount || this.defaultItemCount;
		for (var i = 0; i < itemCount; i++) {
			$itemContainer.append(this.createSingeChoiceDom_());
		}
		$itemContainer.append('<div class="subject-operation"><i class="add-item glyphicon glyphicon-plus-sign quiz-icon-gray"></i></div>');
		return $itemContainer;
	};
	
	/**
	 * @override
	 * @param model
	 */
	SingleChoiceSubject.prototype.applyData = function(model) {
		
	};
	
	/**
	 * @override
	 */
	SingleChoiceSubject.prototype.initialize = function() {
		onlineTest.management.Subject.prototype.initialize.call(this);
		this.bindEvent_();
	};
	
	/**
	 * @override
	 */
	SingleChoiceSubject.prototype.destroy = function() {
		this.unbindEvent_();
		onlineTest.management.Subject.prototype.destroy.call(this);
	};
	
	/**
	 * @param {string} choice
	 * @param {number} score
	 * @param {number} itemId
	 * @private
	 */
	SingleChoiceSubject.prototype.createSingeChoiceDom_ = function() {
		var $singleChoice = $('<div class="radio subject-item"></div>');
		$singleChoice.append('<label><input type="radio" name="subject-items">' + this.defaultItemChoice + '</label>')
			.append('<div class="item-score">该选项分值: <input type="number" value="' + this.defaultScore + '" name="item-score" min="0" max="100" class="item-score-value"/></div>');
		return $singleChoice;
	};
	
	/**
	 * @private
	 */
	SingleChoiceSubject.prototype.createSubjectQuestionEditor_ = function() {
		var self = this;
		var $editor = $('<div class="subject-editor subject-question-editor" contentEditable="true"></div>');
		var $question = this.getDom().find('.subject-question');
		$editor.text($question.text());
		this.getRightPanel().append($editor);
		$editor.focus();
		$editor.one('blur', function() {
			if ($question.text() !== $editor.text()) {
				$question.text($editor.text());
				self.getDom().trigger(SingleChoiceSubject.EventType.SUBJECT_QUESTION_UPDATE, self.getDom().data('subjectId'), $editor.text());
			}
			$editor.remove();
		});
	};
	
	/**
	 * @private
	 */
	SingleChoiceSubject.prototype.createItemChoiceEditor_ = function(event) {
		var self = this;
		var $choice = $(event.target).parent('label');
		var $item = $(event.target).parents('.subject-item');
		var $editor = $('<div class="subject-editor subject-choice-editor" contentEditable="true"></div>');
		$editor.text($choice.text());
		$item.append($editor);
		$editor.focus();
		// choice editor header
		var $editorHeader = $('<div class="choice-editor-header"></div>')
			.append('<i class="shift-item-up glyphicon glyphicon-arrow-up quiz-icon-gray"></i>')
			.append('<i class="shift-item-down glyphicon glyphicon-arrow-down quiz-icon-gray"></i>')
			.append('<i class="remove-item glyphicon glyphicon-trash quiz-icon-gray"></i>');
		$item.append($editorHeader);

		$editorHeader.find('.shift-item-up').one('click', function() {
			self.shiftItemUp_($item);
		});
		$editorHeader.find('.shift-item-down').one('click', function() {
			self.shiftItemDown_($item);
		});
		$editorHeader.find('.remove-item').one('click', function() {
			self.removeItem_($item);
		});
				
		$editor.one('blur', function() {
			if ($choice.text() != $editor.text()) {
				$choice.text($editor.text());
				$choice.prepend('<input type="radio" name="subject-items">');
				self.getDom().trigger(SingleChoiceSubject.EventType.ITEM_CHOICE_UPDATE, $item.data('itemId'), $editor.text());
			}
			$editor.remove();
			$editorHeader.fadeOut('fast', function() {
				$editorHeader.remove();
			})
		});
	};
	
	/**
	 * @private
	 */
	SingleChoiceSubject.prototype.addItem_ = function() {
		var $itemContainer = this.getDom().find('.subject-items');
		var $subjectOp = $itemContainer.find('.subject-operation');
		var $singleChoice = this.createSingeChoiceDom_();
		$singleChoice.insertBefore($subjectOp);
		$singleChoice.find('input[type="radio"]').click();
		
		this.getDom().trigger(SingleChoiceSubject.EventType.ITEM_ADD, this.getDom().data('subjectId'));
	};
	
	/**
	 * @param {HTMLDocument} $item
	 * @private
	 */
	SingleChoiceSubject.prototype.removeItem_ = function($item) {
		this.getDom().trigger(SingleChoiceSubject.EventType.ITEM_DELETE, $item.data('itemId'));
		$item.remove();
	};
	
	/**
	 * @param {HTMLDocument} $item
	 * @private
	 */
	SingleChoiceSubject.prototype.shiftItemUp_ = function($item) {
		var $pre = $item.prev('.subject-item');
		if ($pre.size() === 0) {
			return;
		}
		$item.insertBefore($pre);
		$item.find('input[type="radio"]').click();
		this.getDom().trigger(SingleChoiceSubject.EventType.ITEM_SHIFT_UP, $item.data('itemId'));
	};
	
	/**
	 * @param {HTMLDocument} $item
	 * @private
	 */
	SingleChoiceSubject.prototype.shiftItemDown_ = function($item) {
		var $next = $item.next('.subject-item');
		if ($next.size() === 0) {
			return;
		}
		$item.insertAfter($next);
		$item.find('input[type="radio"]').click();
		this.getDom().trigger(SingleChoiceSubject.EventType.ITEM_SHIFT_DOWN, $item.data('itemId'));
	};
	
	/**
	 * @private
	 * @param event
	 */
	SingleChoiceSubject.prototype.updateItemScore_ = function(event) {
		var $scoreInput = $(event.target);
		var score = parseFloat($scoreInput.val());
		var $item = $scoreInput.parents('.subject-item');
		this.getDom().trigger(SingleChoiceSubject.EventType.ITEM_SCORE_UPDATE, $item.data('itemId'), score);
	};
	
	/**
	 * @private
	 */
	SingleChoiceSubject.prototype.bindEvent_ = function() {
		var self = this;
		this.getDom().on('click', '.subject-question', function(event) {
			self.createSubjectQuestionEditor_();
		});
		this.getDom().on('click', 'input[type="radio"]', function(event) {
			self.createItemChoiceEditor_(event);
		});
		this.getDom().on('click', '.add-item', function() {
			self.addItem_();
		});
		this.getDom().on('change', '.item-score-value', function(event) {
			self.updateItemScore_(event);
		});
	};
	
	SingleChoiceSubject.prototype.unbindEvent_ = function() {
		this.getDom().off('click', '.subject-question');
		this.getDom().off('click', 'label');
		this.getDom().off('click', '.add-item');
		this.getDom().off('change', '.item-score-value');
	};
	
})(onlineTest.management.SingleChoiceSubject);
