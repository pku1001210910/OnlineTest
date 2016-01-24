package com.fivestars.websites.onlinetest.dao;

import java.util.List;

import com.fivestars.websites.onlinetest.model.Article;

public interface ArticleDAO extends GenericDAO<Article, Integer> {
	public List<Article> loadByPage(int pageNo, int pageSize);
}
