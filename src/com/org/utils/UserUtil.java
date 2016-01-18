package com.org.utils;

/**
 * 用户工具类
 * 主要用于处理与用户相关的数据，比如创建一个临时用户
 */
public class UserUtil {
	public static synchronized String randomLoginName(){
		String rn = DateUtil.getDateStringByFormat(DateUtil.yyyyMMddHHmmss);
		return rn;
	}
	
}
