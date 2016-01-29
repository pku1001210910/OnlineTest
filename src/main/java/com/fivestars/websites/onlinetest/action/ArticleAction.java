package com.fivestars.websites.onlinetest.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fivestars.websites.onlinetest.model.Article;
import com.fivestars.websites.onlinetest.service.ArticleService;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("user")
@Namespace("/article")
public class ArticleAction {
	private Integer id;
	private String title;
	private String content;
	private Integer pageNo;
	private final Integer pageSize = 10;

	@Autowired
	private ArticleService articleService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Action(value = "all", results = { @Result(name = "success", location = "/WEB-INF/views/article/articlelist.jsp") })
	public String articleList() throws JsonProcessingException {
		List<Article> articles = new ArrayList<>();
		if (pageNo == null) {
			for (Article each : articleService.loadAllTitles()) {
				articles.add(new Article(each));
			}
			pageNo = 1;
		} else {
			for (Article each : articleService.loadTitlesByPage(pageNo, pageSize)) {
				articles.add(new Article(each));
			}
		}

		Map request = (Map) ServletActionContext.getContext().get("request");
		ObjectMapper objectMapper = new ObjectMapper();
		request.put("articles", objectMapper.writeValueAsString(articles));
		request.put("total", articleService.count() / pageSize + 1);
		request.put("pageNo", pageNo);
		return ActionSupport.SUCCESS;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Action(value = "view", results = { @Result(name = "success", location = "/WEB-INF/views/article/article.jsp") })
	public String articleView() throws JsonProcessingException {
		id = (id == null) ? 77 : id;
		Map request = (Map) ServletActionContext.getContext().get("request");
		Article article = new Article(articleService.loadById(id));
		
		// current article
		ObjectMapper objectMapper = new ObjectMapper();
		request.put("article", objectMapper.writeValueAsString(article));
		
		// pager
		int index = 0;
		List<Article> articles = articleService.loadAllTitles();
		for(Article each : articles) {
			if(each.getArticleId().equals(id)) {
				break;
			}
			index++;
		}
		if(index > 0) {
			int prevId = articles.get(index -1).getArticleId();
			request.put("prev", prevId);
		}
		if(index < articles.size() - 1) {
			int nextId = articles.get(index + 1).getArticleId();
			request.put("next", nextId);
		}
		
		// recommends
		int total = articles.size();
		List<Article> recommends = new ArrayList<>();
		for(Article each : articles.subList(0,  total > 10 ? 10 : total)) {
			recommends.add(new Article(each));
		}
		request.put("recommends", objectMapper.writeValueAsString(recommends));
		
		return ActionSupport.SUCCESS;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}
}