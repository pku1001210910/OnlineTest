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
 * @param {number} subjectId
 */
onlineTest.management.Subject.prototype.createDom = function(parent, subjectNumber) {
	var $container = $('<div class="subject-container clearfix"></div>');
	var $leftPanel = this.createSubjectLeftPanel_(subjectNumber);
	var $rightPanel = this.createSubjectRightPanel_();
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
 * @return {HTMLDocument}
 */
onlineTest.management.Subject.prototype.createSubjectRightPanel_ = function() {
	var $rightDom = $('<div class="subject-right col-md-11"></div>');
	var $subjectDom = this.createSubjectDom();
	if ($subjectDom) {
		$rightDom.append($subjectDom);	
	}
	var $itemDom = this.createItemDom();
	if ($itemDom) {
		$rightDom.append($itemDom);	
	}
	return $rightDom;
};

/**
 * @private
 */
onlineTest.management.Subject.prototype.bindGenericEvent_ = function() {
	var self = this;
	var EventType = onlineTest.management.Subject.EventType;
	var subjectId = self.$dom_.data('subjectId');
	this.$dom_.on('click', '.shift-subject-up', function(event) {
		self.$dom_.trigger(EventType.SUBJECT_SHIFT_UP, subjectId);
	});
	this.$dom_.on('click', '.shift-subject-down', function(event) {
		self.$dom_.trigger(EventType.SUBJECT_SHIFT_DOWN, subjectId);
	});
	this.$dom_.on('click', '.remove-subject', function(event) {
		self.destroy();
		self.$dom_.trigger(EventType.SUBJECT_DELETE, subjectId);
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
 * @return {?HTMLDocument} null means this subject does not need items section
 */
onlineTest.management.Subject.prototype.createItemDom = function() {};

/**
 * @param {Object} model
 */
onlineTest.management.Subject.prototype.applyData = function(model) {};

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

