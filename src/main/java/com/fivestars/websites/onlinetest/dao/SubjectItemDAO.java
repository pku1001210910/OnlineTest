package com.fivestars.websites.onlinetest.dao;

import com.fivestars.websites.onlinetest.model.SubjectItem;

public interface SubjectItemDAO extends GenericDAO<SubjectItem, Integer> {

	public Integer getItemBySubjectIdAndOrder(Integer subjectId, Integer order);
}
