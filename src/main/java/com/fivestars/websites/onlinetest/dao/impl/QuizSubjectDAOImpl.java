package com.fivestars.websites.onlinetest.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.fivestars.websites.onlinetest.dao.QuizSubjectDAO;
import com.fivestars.websites.onlinetest.model.Quiz;
import com.fivestars.websites.onlinetest.model.QuizSubject;

@Repository("quizSubjectDao")
public class QuizSubjectDAOImpl extends GenericDAOImpl<QuizSubject, Integer> implements QuizSubjectDAO {

	@Override
	public Integer getSubjectByQuizIdAndOrder(Integer quizId, Integer order) {
		Criteria criteria = createCriteria();
		Quiz quiz = new Quiz();
		quiz.setQuizId(quizId);
		Criterion quizEq = Restrictions.eq("quiz", quiz);
		Criterion orderEq = Restrictions.eq("subjectOrder", order);
		criteria.add(quizEq).add(orderEq);
		QuizSubject subject = (QuizSubject) criteria.uniqueResult();
		return subject != null ? subject.getSubjectId() : null;
	}

}
