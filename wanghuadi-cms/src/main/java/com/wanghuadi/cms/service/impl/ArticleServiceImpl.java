/**
 * 
 */
package com.wanghuadi.cms.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wanghuadi.cms.core.Page;
import com.wanghuadi.cms.dao.ArticleMapper;
import com.wanghuadi.cms.domain.Article;
import com.wanghuadi.cms.service.ArticleService;

/**
 * 说明:
 * 
 * @author howsun ->[howsun.zhang@gmail.com]
 * @version 1.0
 *
 * 2019年4月21日 下午9:06:07
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

	@Resource
	ArticleMapper articleMapper;

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<Article> gets(Article conditions, Page page, LinkedHashMap<String, Boolean> orders) {
		List<Article> articles = articleMapper.selects(conditions, orders, page);
		if(page != null && page.getPageCount() == 0){
			int totalCount = articleMapper.count(conditions);
			page.setTotalCount(totalCount);
		}
		return articles;
	}

	@Override
	public void saveArticle(Article article) {
		articleMapper.saveArticle(article);
	}

	@Override
	public boolean updateByStatus(Map<String, Object> map) {
		
		Integer num = articleMapper.updateByStatus(map);
		if(num >0){
			return true;
		}
		
		return false;
	}

	@Override
	public Article queryArticleById(Integer id) {
		return articleMapper.queryArticleById(id);
	}
	
	
}
