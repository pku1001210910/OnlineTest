package com.fivestars.websites.onlinetest.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fivestars.websites.onlinetest.dao.ArticleDAO;
import com.fivestars.websites.onlinetest.model.Article;
import com.fivestars.websites.onlinetest.service.ArticleService;

@Transactional
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	private ArticleDAO articleDao;

	public Serializable save(Article article) {
		return articleDao.save(article);
	}

	@Override
	public List<Article> findAll() {
		return articleDao.listAll();
	}

	@Override
	public Article loadById(int id) {
		return articleDao.load(id);
	}

	@Override
	public List<Article> loadAllTitles() {
		List<Article> articles = articleDao.listAll();
		articles.forEach(a -> a.setContent(null));
		return articles;
	}

	@Override
	public void delete(int id) {
		articleDao.delete(id);
	}
}