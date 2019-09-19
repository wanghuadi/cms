package com.wanghuadi.cms.web.controllers.admin;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wanghuadi.cms.core.Page;
import com.wanghuadi.cms.domain.Article;
import com.wanghuadi.cms.domain.Category;
import com.wanghuadi.cms.domain.Channel;
import com.wanghuadi.cms.domain.User;
import com.wanghuadi.cms.service.ArticleService;
import com.wanghuadi.cms.service.ChannelCategoryService;
import com.wanghuadi.cms.utils.FileUtils;
import com.wanghuadi.cms.web.Constant;

@Controller
@RequestMapping("/admin/blog")
public class AdminArticleController {
	

	@Resource
	private ArticleService articleService;
	
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer page){
		Page _page = new Page(page, 2);
		Article articles = new Article();
		List<Article> articleList = articleService.gets(articles, _page, null);
		model.addAttribute("articleList", articleList);
		
		model.addAttribute("page", _page);
		
		return "admin/article_list";
	}
	
	
	/**
	 * 文章审核
	 * @param id  文章主键
	 * @param status	文章状态
	 * @return	修改状态  成功true  失败false
	 */
	@RequestMapping("/updateByStatus")
	@ResponseBody
	public boolean updateByStatus(Integer id,Integer status){
		Map<String,Object> map = new HashMap<>();
		map.put("id", id);
		map.put("status", status);
		//修改文章状态
		boolean flg = articleService.updateByStatus(map);
		return flg;
		
	}
	
}
