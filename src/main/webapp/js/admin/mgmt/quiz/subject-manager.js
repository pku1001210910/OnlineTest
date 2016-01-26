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
	SINGLE_CHOICE: 0,
	MULTIPLE_CHOICE: 1,
	COMPLETION: 2,
	ANSWER: 3
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