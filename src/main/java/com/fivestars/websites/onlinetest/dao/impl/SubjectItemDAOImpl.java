package com.fivestars.websites.onlinetest.dao.impl;

import org.springframework.stereotype.Repository;

import com.fivestars.websites.onlinetest.dao.SubjectItemDAO;
import com.fivestars.websites.onlinetest.model.SubjectItem;

@Repository("SubjectItemDao")
public class SubjectItemDAOImpl extends GenericDAOImpl<SubjectItem, Integer> implements SubjectItemDAO {

}
