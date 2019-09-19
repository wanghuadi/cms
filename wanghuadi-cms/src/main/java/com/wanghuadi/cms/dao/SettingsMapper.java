/**
 * 
 */
package com.wanghuadi.cms.dao;

import com.wanghuadi.cms.domain.Settings;

/**
 * 说明:
 * 
 * @author howsun ->[howsun.zhang@gmail.com]
 * @version 1.0
 *
 * 2019年3月27日 下午2:56:07
 */
public interface SettingsMapper {

	public void insert(Settings settings);
	
	public void update(Settings settings);

	public Settings select();
	
}
