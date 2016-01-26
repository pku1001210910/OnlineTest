package com.fivestars.websites.onlinetest.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.fivestars.websites.onlinetest.dao.SubjectItemDAO;
import com.fivestars.websites.onlinetest.model.QuizSubject;
import com.fivestars.websites.onlinetest.model.SubjectItem;

@Repository("SubjectItemDao")
public class SubjectItemDAOImpl extends GenericDAOImpl<SubjectItem, Integer> implements SubjectItemDAO {

	@Override
	public Integer getItemBySubjectIdAndOrder(Integer subjectId, Integer order) {
		Criteria criteria = createCriteria();
		QuizSubject subject = new QuizSubject();
		subject.setSubjectId(subjectId);
		Criterion subjectEq = Restrictions.eq("quizSubject", subject);
		Criterion orderEq = Restrictions.eq("itemOrder", order);
		criteria.add(subjectEq).add(orderEq);
		SubjectItem item = (SubjectItem) criteria.uniqueResult();
		return item != null ? item.getItemId() : null;
	}

}
