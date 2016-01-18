package com.org.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;

import com.org.common.CommonConstant;
import com.org.container.CommonContainer;
import com.org.container.UserManager;
import com.org.utils.PropertiesUtil;
import com.org.utils.WxUtil;

public class ContextLoaderListener implements ServletContextListener{

	private static Log log = LogFactory.getLog(ContextLoaderListener.class);
	
	private ContextLoader contextLoader;
	
	public void contextDestroyed(ServletContextEvent arg0) {
		//
		if (this.contextLoader != null) {
			this.contextLoader.closeWebApplicationContext(arg0.getServletContext());
			WxUtil.cancelAutoRun();
			//KestrelSub.getInstance().stop();
		}
	}
	
	protected ContextLoader createContextLoader() {
		return new ContextLoader();
	}

	public ContextLoader getContextLoader() {
		return contextLoader;
	}

	public void contextInitialized(ServletContextEvent arg0) {
		log.info("Integrate Smp With Spring Container Begin....");
		
		ServletContext servletContext =	arg0.getServletContext();
	    PropertiesUtil.initProperties(servletContext);
	    
	    CommonContainer.saveContext(servletContext);
		CommonContainer.saveData(CommonConstant.FLAG_CURRENT_AWARDS, "5");	// 当前奖项默认为5等奖
		
		/* 6.init spring context */
		this.contextLoader = createContextLoader();
		//WebApplicationContext context = this.contextLoader.initWebApplicationContext(servletContext);
		this.contextLoader.initWebApplicationContext(servletContext);
		// 初始化所有的用户信息
		UserManager.initUserInfo();
		
		WxUtil.autoRun();
		log.info("Integrate Smp With Spring Container End....");
	}
	
	

}
