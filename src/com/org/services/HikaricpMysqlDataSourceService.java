package com.org.services;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.org.container.CommonContainer;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * hikaricp数据源服务
 * @author Administrator
 *
 */
public class HikaricpMysqlDataSourceService {
	
	public static HikaricpMysqlDataSourceService getInstance(){
		if(hds == null){
			// hds = new HikaricpMysqlDataSourceService();
			hds = new HikaricpMysqlDataSourceService("/WEB-INF/config/oracle_db.properties");
			
		}
		return hds;
	}
	
	public Connection getConnection(){
		try {
			return template.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	private static HikaricpMysqlDataSourceService hds = null;
	private HikaricpMysqlDataSourceService(String fileName) {
		InputStream pin = null;
		Properties pro = new Properties();
		try {
			pin = CommonContainer.getServletContext().getResourceAsStream(fileName);
			pro.load(pin);
		} catch (IOException e) {
			System.out.println("load properties error : " + fileName);
		}
		
		String driverClassName = pro.getProperty("hikaricp.jdbc.driverClassName");
		String jdbcUrl = pro.getProperty("hikaricp.url");
		String user = pro.getProperty("hikaricp.username");
		String password = pro.getProperty("hikaricp.password");
		String minimumIdle = pro.getProperty("hikaricp.minimumIdle");
		String maximumPoolSize = pro.getProperty("hikaricp.maximumPoolSize");
		String connectionTestQuery = pro.getProperty("hikaricp.connectionTestQuery");
		String connectionTimeout = pro.getProperty("hikaricp.connectionTimeout");
		String idleTimeout = pro.getProperty("hikaricp.idleTimeout");
		String maxLifetime = pro.getProperty("hikaricp.maxLifetime");
		
		HikariConfig config = new HikariConfig();

		config.setMaximumPoolSize(100);
		//config.setDataSourceClassName(driverClassName);
		config.setDriverClassName(driverClassName);
		config.setJdbcUrl(jdbcUrl);
		config.addDataSourceProperty("user", user);
		config.addDataSourceProperty("password", password);
		
		config.addDataSourceProperty("useUnicode", "true");
		config.addDataSourceProperty("characterEncoding", "utf8");
		
		config.setConnectionTimeout(Long.valueOf(connectionTimeout));
		config.setIdleTimeout(Long.valueOf(idleTimeout));
		config.setMaxLifetime(Long.valueOf(maxLifetime));
		config.setMaximumPoolSize(Integer.valueOf(maximumPoolSize));
		config.setMinimumIdle(Integer.valueOf(minimumIdle));
		config.setConnectionTestQuery(connectionTestQuery);
		
		HikariDataSource temp = new HikariDataSource(config);
		
		this.template = temp;
	}
	
	private HikaricpMysqlDataSourceService(){
		Properties pro = new Properties();
		try {
			String fileName = "/WEB-INF/config/mysql_db.properties";
			InputStream in = CommonContainer.getServletContext().getResourceAsStream(fileName);
			pro.load(in);
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		String driverClassName = pro.getProperty("driverClassName");
		String serverName = pro.getProperty("serverName");
		String port = pro.getProperty("port");
		String databaseName = pro.getProperty("databaseName");
		String user = pro.getProperty("user");
		String password = pro.getProperty("password");
		String connectionTimeout = pro.getProperty("connectionTimeout");
		String maxLifetime = pro.getProperty("maxLifetime");
		String idleTimeout = pro.getProperty("idleTimeout");
		String maximumPoolSize = pro.getProperty("maximumPoolSize");
		String minimumIdle = pro.getProperty("minimumIdle");
		String connectionTestQuery = pro.getProperty("connectionTestQuery");
		
		HikariConfig config = new HikariConfig();

		config.setMaximumPoolSize(100);
		config.setDataSourceClassName(driverClassName);
		config.addDataSourceProperty("serverName", serverName);
		config.addDataSourceProperty("port", port);
		config.addDataSourceProperty("databaseName", databaseName);
		config.addDataSourceProperty("user", user);
		config.addDataSourceProperty("password", password);
		config.addDataSourceProperty("useUnicode", "true");
		config.addDataSourceProperty("characterEncoding", "utf8");
		
		config.setConnectionTimeout(Long.valueOf(connectionTimeout));
		config.setIdleTimeout(Long.valueOf(idleTimeout));
		config.setMaxLifetime(Long.valueOf(maxLifetime));
		config.setMaximumPoolSize(Integer.valueOf(maximumPoolSize));
		config.setMinimumIdle(Integer.valueOf(minimumIdle));
		config.setConnectionTestQuery(connectionTestQuery);
		
		HikariDataSource temp = new HikariDataSource(config);
		this.template = temp;
	}
	private DataSource template;
	//private Log log = LogFactory.getLog(HikaricpMysqlDataSourceService.class);
}
