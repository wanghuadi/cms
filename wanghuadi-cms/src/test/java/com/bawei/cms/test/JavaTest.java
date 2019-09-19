/**
 * 
 */
package com.bawei.cms.test;

import java.util.Date;

/**
 * 说明:
 * 
 * @author howsun ->[howsun.zhang@gmail.com]
 * @version 1.0
 *
 * 2019年4月10日 上午9:55:43
 */
public class JavaTest {

	/**
	 * 功能说明：<br>
	 * @param args
	 * void
	 */
	public static void main(String[] args) {
		int i = 1;
		change(i);
		System.out.println(i);
		
		Date date = new Date();
		change(date);
		System.out.println(date);
		
		Date date1 = new Date();
		change1(date1);
		System.out.println(date1);
	}
	
	public static void change(int j){
		j = 2;
	}

	public static void change(Date date){
		date.setTime(date.getTime() - 86400000L);
	}
	
	public static void change1(Date date){
		date = new Date(System.currentTimeMillis() - 86400000L * 2);
	}
}
