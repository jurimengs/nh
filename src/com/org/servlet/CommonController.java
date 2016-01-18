package com.org.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public interface CommonController {
	public void post(HttpServletRequest request, HttpServletResponse response)
			throws Exception;
}
