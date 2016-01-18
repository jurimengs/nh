package com.org.remote;

/**
 * 接入远程请求
 * @author Administrator
 *
 */
public interface RemoteService {

	/**
	 * @param request json 结构数据体
	 * 
	 * @return response json 结构数据体
	 */
	public String access(String request);
	
}