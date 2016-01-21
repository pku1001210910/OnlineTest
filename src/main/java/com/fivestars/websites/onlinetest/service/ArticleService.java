package com.fivestars.websites.onlinetest.service;

import java.io.Serializable;
import java.util.List;

import com.fivestars.websites.onlinetest.model.Article;

public interface ArticleService {
	/**
	 * 
	 * save article
     * @param user
     * @return
     */
    public Serializable save(Article article); 
    
    public void saveOrUpdate(Article article); 
    
    /**
     * load all
     * @return
     */
    public List<Article> findAll();

	public Article loadById(int id);
	
	public List<Article> loadAllTitles();
	
	public void delete(int id);
}