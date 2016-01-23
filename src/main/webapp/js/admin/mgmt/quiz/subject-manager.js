var onlineTest = onlineTest || {};
onlineTest.management = onlineTest.management || {};

/**
 * @constructor
 */
onlineTest.management.SubjectManager = function() {
};

/**
 * @enum
 */
onlineTest.management.SubjectManager.SubjectType = {
	SINGLE_CHOICE: 'singleChoice',
	MULTIPLE_CHOICE: 'multipeChoice',
	COMPLETION: 'completion',
	ANSWER: 'answer'
};

/**
 * @param {onlineTest.management.SubjectManager.SubjectType} type
 * @return {onlineTest.management.Subject}
 */
onlineTest.management.SubjectManager.prototype.getSubjectComponent = function(type) {
	if (type === onlineTest.management.SubjectManager.SubjectType.SINGLE_CHOICE) {
		return new onlineTest.management.SingleChoiceSubject();
	}
}