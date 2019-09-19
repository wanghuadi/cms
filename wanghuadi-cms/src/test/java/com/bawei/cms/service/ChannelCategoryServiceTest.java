package com.bawei.cms.service;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.bawei.cms.BaseTestCase;
import com.wanghuadi.cms.domain.Category;
import com.wanghuadi.cms.domain.Channel;
import com.wanghuadi.cms.service.ChannelCategoryService;

/**
 * 说明:
 * 
 * @author howsun ->[howsun.zhang@gmail.com]
 * @version 1.0
 *
 * 2019年3月18日 上午10:46:34
 */
public class ChannelCategoryServiceTest extends BaseTestCase {

	@Resource
	ChannelCategoryService channelCategoryService;
	
	@Test
	public void testSaveChannel() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveChannel() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetChannel() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetChannels() {
		List<Channel> channels = channelCategoryService.getChannels();
		for (Channel channel : channels) {
			System.out.println(channel.getId() + "\t" + channel.getName());
		}
	}

	@Test
	public void testSaveCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCategory() {
		Category category = channelCategoryService.getCategory(1);
		System.out.println(category.getId() + "\t" + category.getName());
	}

	@Test
	public void testGetCategoriesInt() {
		List<Category> categories = channelCategoryService.getCategories(1);
		for (Category category : categories) {
			System.out.println(category.getId() + "\t" + category.getName());
		}
	}

	@Test
	public void testGetCategories() {
		List<Category> categories = channelCategoryService.getCategories();
		for (Category category : categories) {
			System.out.println(category.getId() + "\t" + category.getName());
		}
	}

}
