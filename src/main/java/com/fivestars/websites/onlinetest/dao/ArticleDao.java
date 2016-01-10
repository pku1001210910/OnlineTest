package com.fivestars.websites.onlinetest.dao;

import java.io.Serializable;
import java.util.List;

import com.fivestars.websites.onlinetest.model.Article;

public interface ArticleDao {
	/**
	 * 
	 * save article to DB
     * @param user
     * @return
     */
    public Serializable save(Article article); 
    
    /**
     * load all
     * @return
     */
    public List<Article> findAll();

	public Article loadById(int id);
	
}
