package com.bawei.cms.service;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.bawei.cms.BaseTestCase;
import com.wanghuadi.cms.domain.Slide;
import com.wanghuadi.cms.service.SlideService;

/**
 * 说明:
 * 
 * @author howsun ->[howsun.zhang@gmail.com]
 * @version 1.0
 *
 * 2019年3月28日 下午4:49:38
 */
public class SlideServiceTest extends BaseTestCase {

	@Resource
	SlideService slideService;
	
	//@Test
	public void testSave() {
		Slide slide = new Slide();
		slide.setTitle("官方小程序开发体验课");
		slide.setUrl("https://ke.qq.com/course/297792");
		slide.setPicture("http://s9.sinaimg.cn/middle/66e796c3g755eaef7edc8&690");
		slideService.save(slide);
	}

	@Test
	public void testUpdateByKey() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteById() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectById() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectTop() {
		List<Slide> slides = slideService.getTops(5);
		for (Slide slide : slides) {
			System.out.println(slide.getId() + "\t" + slide.getTitle());
		}
	}

	@Test
	public void testSelectAll() {
		fail("Not yet implemented");
	}

}
