package com.wanghuadi.cms.web.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

//文章相关请求来找我
@Controller
@RequestMapping("/my/blog")
public class ArticleController {
	
	@Resource
	private ChannelCategoryService channelCategoryService;
	
	@Resource
	private ArticleService articleService;

	/*@RequestMapping("/article/{id}")
	public String article(Model model,@PathVariable(value = "id")Integer id){
		Article article = articleService.queryArticleById(id);
		model.addAttribute("blog", article);
		return "blog";
	}*/
	
	/**
	 * 跳转文章页面
	 * @return
	 */
	@RequestMapping("/edit")
	public String editArticle(Model model,Integer id){
		
		if(id != null){
			//查询文章并回显
			Article article = articleService.queryArticleById(id);
			model.addAttribute("blog", article);
		}else{
			//绑定model对象
			model.addAttribute("blog", new Article());
		}
		
		
		//查询栏目列表
		List<Channel> channelList = channelCategoryService.getChannels();
		
		for (Channel channel : channelList) {
			List<Category> categories = channelCategoryService.getCategories(channel.getId());
			channel.setCategoryList(categories);
		}
		//根据id查询所有分类
		
		model.addAttribute("channelList", channelList);
		/*List<Category> cateList = channelCategoryService.getCategories();
		model.addAttribute("cateList", cateList);*/
		
		return "user-space/blog_edit";
	}
	
	@RequestMapping("/save")
	public String saveArticle(String hots,HttpSession session,@Validated @ModelAttribute("blog") Article article,BindingResult result,MultipartFile file) throws IllegalStateException, IOException{
		if(result.hasErrors()){
			return "user-space/blog_edit";
		}
		
		//上传图片
		String upload = FileUtils.upload(file);
		
		//设置图片路径
		article.setPicture(upload);
		
		//当前用户
		User user = (User) session.getAttribute(Constant.LOGIN_USER);
		User author = new User(user.getId());
		article.setAuthor(author);
		
		//创建时间
		article.setCreated(new Date());
		
		if("on".equals(hots)){
			//是否上热门
			article.setHot(true);
		}
		
		//发布文章
		articleService.saveArticle(article);
		
		//文章列表页面
		return "redirect:/my/blog/list";
	}
	
	/**
	 * 查看图片
	 * @param path  图片路径
	 * @param request 请求
	 * @param response 响应
	 */
	@RequestMapping("lookImg")
	public void lookImg(String path,HttpServletRequest request,HttpServletResponse response){
		FileUtils.lookImg(path, request, response);
	}
	
	/**
	 * 前台  我的文章
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping("/list")
	public String list(HttpSession session,Model model,@RequestParam(defaultValue = "1") Integer page){
		
		User user = (User) session.getAttribute(Constant.LOGIN_USER);
		
		Page _page = new Page(page, 10);
		Article articles = new Article();
		
		//根据 用户信息 去 查询     对应文章
		articles.setAuthor(user );
		List<Article> articleList = articleService.gets(articles, _page, null);
		model.addAttribute("blogs", articleList);
		model.addAttribute("page", _page);
		return "user-space/blog_list";
	}
	
}
