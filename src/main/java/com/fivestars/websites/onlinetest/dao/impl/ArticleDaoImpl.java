package com.fivestars.websites.onlinetest.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fivestars.websites.onlinetest.dao.ArticleDao;
import com.fivestars.websites.onlinetest.model.Article;

@Repository("articleDao")
public class ArticleDaoImpl implements ArticleDao{
	
    @Autowired
    private SessionFactory sessionFactory;
    
	/**
	 * 
	 * save Article to DB
     * @param user
     * @return
     */
    public Serializable save(Article article) {
    	return sessionFactory.getCurrentSession().save(article);
    }

	@Override
	@SuppressWarnings("unchecked")
	public List<Article> findAll() {
		return sessionFactory.getCurrentSession().createCriteria(Article.class).list();
	}

	@Override
	public Article loadById(int id) {
		return (Article) sessionFactory.getCurrentSession().get(Article.class, id);
	}
}
