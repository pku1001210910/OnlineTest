package com.fivestars.websites.onlinetest.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.fivestars.websites.onlinetest.dao.ArticleDAO;
import com.fivestars.websites.onlinetest.model.Article;

@Repository("articleDao")
public class ArticleDAOImpl extends GenericDAOImpl<Article, Integer> implements ArticleDAO {

	@Override
	public List<Article> loadByPage(int pageNo, int pageSize) {
		Criteria criteria = createCriteria();
		return pagedQuery(criteria, pageNo, pageSize);
	}
}
