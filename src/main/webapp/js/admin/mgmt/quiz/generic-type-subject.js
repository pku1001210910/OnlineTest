var onlineTest = onlineTest || {};
onlineTest.management = onlineTest.management || {};

/**
 * Generic super class of all types of subjects
 * @constructor
 */
onlineTest.management.Subject = function() {
	/**
	 * @type {HTMLDocument}
	 */
	this.$dom_ = null;
	
	/**
	 * @type {HTMLDocument}
	 */
	this.$leftPanel_ = null;
	
	/**
	 * @type {HTMLDocument}
	 */
	this.$rightPanel_ = null;
};

/**
 * @enum
 */
onlineTest.management.Subject.EventType = {
	SUBJECT_SHIFT_UP: 'subjectShiftUp',
	SUBJECT_SHIFT_DOWN: 'subjectShiftDown',
	SUBJECT_DELETE: 'subjectDelete'
};

/**
 * @param {HTMLDocument} parent
 * @param {number} subjectNumber
 * @param {number} itemCount
 */
onlineTest.management.Subject.prototype.createDom = function(parent, subjectNumber, itemCount) {
	var $container = $('<div class="subject-container clearfix"></div>');
	var $leftPanel = this.createSubjectLeftPanel_(subjectNumber);
	var $rightPanel = this.createSubjectRightPanel_(itemCount);
	$leftPanel.appendTo($container);
	$rightPanel.appendTo($container);
	$container.appendTo(parent);
	this.$dom_ = $container;
	this.$leftPanel_ = $leftPanel;
	this.$rightPanel_ = $rightPanel;
};

/**
 * @return {HTMLDocument}
 */
onlineTest.management.Subject.prototype.getDom = function() {
	return this.$dom_;
};

/**
 * @return {HTMLDocument}
 */
onlineTest.management.Subject.prototype.getLeftPanel = function() {
	return this.$leftPanel_;
};

/**
 * @return {HTMLDocument}
 */
onlineTest.management.Subject.prototype.getRightPanel = function() {
	return this.$rightPanel_;
};


/**
 * @private
 * @param {number} subjectNumber
 * @return {HTMLDocument}
 */
onlineTest.management.Subject.prototype.createSubjectLeftPanel_ = function(subjectNumber) {
	var $leftDom = $('<div class="subject-left col-md-1"></div>');
	$leftDom.append('<div class="subject-number">第' + subjectNumber + '题</div>');
	$leftDom.append('<div><i class="shift-subject-up glyphicon glyphicon-arrow-up quiz-icon-gray"></i></div>');
	$leftDom.append('<div><i class="shift-subject-down glyphicon glyphicon-arrow-down quiz-icon-gray"></i></div>');
	$leftDom.append('<div><i class="remove-subject glyphicon glyphicon-trash quiz-icon-gray"></i></div>');
	return $leftDom;
};

/**
 * @private
 * @param {number} itemCount
 * @return {HTMLDocument}
 */
onlineTest.management.Subject.prototype.createSubjectRightPanel_ = function(itemCount) {
	var $rightDom = $('<div class="subject-right col-md-11"></div>');
	var $subjectDom = this.createSubjectDom();
	if ($subjectDom) {
		$rightDom.append($subjectDom);	
	}
	var $itemDom = this.createItemDom(itemCount);
	if ($itemDom) {
		$rightDom.append($itemDom);	
	}
	return $rightDom;
};

/**
 * @private
 */
onlineTest.management.Subject.prototype.rearrangeSubjectNumber_ = function() {
	var $allSubjects = $('#subject-item-body-panel').find('.subject-number');
	var i = 1;
	$allSubjects.each(function() {
		$(this).text("第" + i + "题");
		i++;
	});
};

/**
 * @private
 */
onlineTest.management.Subject.prototype.shiftSubjectUp_ = function() {
	var EventType = onlineTest.management.Subject.EventType;
	var subjectId = this.$dom_.data('subjectId');
	var $pre = this.$dom_.prev('.subject-container');
	if ($pre.size() === 0) {
		return;
	}
	this.$dom_.insertBefore($pre);
	this.rearrangeSubjectNumber_();
	this.$dom_.trigger(EventType.SUBJECT_SHIFT_UP, subjectId);
};

/**
 * @private
 */
onlineTest.management.Subject.prototype.shiftSubjectDown_ = function() {
	var EventType = onlineTest.management.Subject.EventType;
	var subjectId = this.$dom_.data('subjectId');
	var $next = this.$dom_.next('.subject-container');
	if ($next.size() === 0) {
		return;
	}
	this.$dom_.insertAfter($next);
	this.rearrangeSubjectNumber_();
	this.$dom_.trigger(EventType.SUBJECT_SHIFT_DOWN, subjectId);
};

/**
 * @private
 */
onlineTest.management.Subject.prototype.deleteSubject_ = function() {
	var self = this;
	var EventType = onlineTest.management.Subject.EventType;
	var subjectId = this.$dom_.data('subjectId');
	$('#delete-subject-confirm').dialog({
		resizable: false,
		height: 160,
		modal: true,
		buttons: {
			"确定": function() {
				$(this).dialog( "close" );
				self.destroy();
				self.$dom_.trigger(EventType.SUBJECT_DELETE, subjectId);
				self.$dom_.remove();
				self.rearrangeSubjectNumber_();
			},
			"取消": function() {
				$(this).dialog( "close" );
			}
		}
	});
};

/**
 * @private
 */
onlineTest.management.Subject.prototype.bindGenericEvent_ = function() {
	var self = this;
	this.$dom_.on('click', '.shift-subject-up', function(event) {
		self.shiftSubjectUp_();
	});
	this.$dom_.on('click', '.shift-subject-down', function(event) {
		self.shiftSubjectDown_();
	});
	this.$dom_.on('click', '.remove-subject', function(event) {
		self.deleteSubject_();
	});
};

/**
 * @private
 */
onlineTest.management.Subject.prototype.unbindGenericEvent_ = function() {
	this.$dom_.off('click', '.shift-subject-up');
	this.$dom_.off('click', '.shift-subject-down');
	this.$dom_.off('click', '.remove-subject');
};


/**
 * @return {?HTMLDocument} null means this subject does not need subject section
 */
onlineTest.management.Subject.prototype.createSubjectDom = function() {};

/**
 * @param {number} itemCount
 * @return {?HTMLDocument} null means this subject does not need items section
 */
onlineTest.management.Subject.prototype.createItemDom = function(itemCount) {};

/**
 * @param {Object} data
 */
onlineTest.management.Subject.prototype.applyData = function(data) {};

/**
 * @return {Object}
 */
onlineTest.management.Subject.prototype.getData = function() {};

/**
 * do necessary initialization after dom is created
 */
onlineTest.management.Subject.prototype.initialize = function() {
	this.bindGenericEvent_();
};

/**
 * do necessary finalization before competent is destroyed;
 */
onlineTest.management.Subject.prototype.destroy = function() {
	this.unbindGenericEvent_();
};

