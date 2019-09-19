package com.wanghuadi.cms.web.controllers;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wanghuadi.cms.domain.Category;
import com.wanghuadi.cms.service.ChannelCategoryService;

@Controller
@RequestMapping("/my/channelCategory")
public class ChannelCategoryController {

	@Resource
	private ChannelCategoryService service;
	
	/**
	 * 根据栏目id查询 对应的分类列表
	 * @param channelId 	参数  栏目id
	 * @return	返回分类列表信息
	 */
	@RequestMapping("/queryCategoryByChannelId")
	@ResponseBody
	public List<Category> queryCategoryByChannelId(Integer channelId){
		//根据栏目id查询 分类列表
		List<Category> list = service.getCategories(channelId);
		//向ajax  返回json数据
		return list;
	}
	
}
