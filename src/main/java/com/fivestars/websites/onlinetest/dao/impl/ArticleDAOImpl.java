package com.fivestars.websites.onlinetest.dao.impl;

import org.springframework.stereotype.Repository;

import com.fivestars.websites.onlinetest.dao.ArticleDAO;
import com.fivestars.websites.onlinetest.model.Article;

@Repository("articleDao")
public class ArticleDAOImpl extends GenericDAOImpl<Article, Integer> implements ArticleDAO {

}
