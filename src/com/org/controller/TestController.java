package com.org.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import com.org.common.CommonConstant;
import com.org.container.CommonContainer;
import com.org.servlet.CommonController;
import com.org.servlet.SmpHttpServlet;

@Controller
public class TestController extends SmpHttpServlet implements CommonController {
	private static final long serialVersionUID = 2156792239072761671L;

	public TestController() {

	}

	public void post(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if(request.getParameter("val") != null) {
			CommonContainer.saveData(request.getParameter("key"), request.getParameter("val"));
		}
	}

}
