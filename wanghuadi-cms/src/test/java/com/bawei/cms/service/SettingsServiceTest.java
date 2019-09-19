package com.bawei.cms.service;

import static org.junit.Assert.fail;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bawei.cms.BaseTestCase;
import com.wanghuadi.cms.domain.Settings;
import com.wanghuadi.cms.service.SettingsService;
import com.wanghuadi.cms.web.controllers.PassportController;

/**
 * 说明:
 * 
 * @author howsun ->[howsun.zhang@gmail.com]
 * @version 1.0
 *
 * 2019年3月28日 上午11:12:21
 */
public class SettingsServiceTest extends BaseTestCase{

	public static Logger log = LoggerFactory.getLogger(PassportController.class);
	
	@Resource
	SettingsService settingsService;
	
	@Test
	public void testSave() {
		fail("Not yet implemented");
	}

	//@Test
	public void testUpdate() {
		Settings settings = new Settings();
		settings.setSiteDomain("www.howsun.com");
		settings.setAdminUsername("administrator");
		settingsService.update(settings);
	}

	@Test
	public void testGet() {
		Settings settings = settingsService.get();
		if(settings != null){
			System.out.println(settings.getSiteDomain());
			System.out.println(settings.getSiteName());
			System.out.println(settings.getAdminUsername());
			System.out.println(settings.getAdminPassword());
		}
		
		log.error(settings.toString());
	}

}
