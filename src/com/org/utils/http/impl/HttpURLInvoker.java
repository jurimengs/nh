package com.org.utils.http.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.org.log.LogUtil;
import com.org.log.impl.LogUtilMg;
import com.org.util.CT;
import com.org.utils.JSONUtils;
import com.org.utils.http.HttpTool;
/**
 * @author Nano
 * 
 * URL 通讯工具封装
 */
public class HttpURLInvoker implements HttpTool {

	public static Logger log = LoggerFactory.getLogger(HttpURLInvoker.class);

	public  String httpGet(String url,String charset) {
		HttpURLConnection connection = null;
		BufferedReader reader  = null;
	    StringBuffer sb = new StringBuffer();
		try {
			LogUtil.log(CT.LOG_CATEGORY_COMMUNICATION, "1.将进行HttpGet请求", null, LogUtilMg.LOG_INFO, CT.LOG_PATTERN_COMMUNICATION);
			URL getUrl = new URL(url);
			connection = (HttpURLConnection) getUrl.openConnection();
			connection.connect();
			LogUtil.log(CT.LOG_CATEGORY_COMMUNICATION, "2.成功创建连接到["+url+"]连接", null, LogUtilMg.LOG_INFO, CT.LOG_PATTERN_COMMUNICATION);
		    reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),charset));
			String line = "";
			while ((line = reader.readLine()) != null) {
				  sb.append(line);
			}
			LogUtil.log(CT.LOG_CATEGORY_COMMUNICATION, "3.成功获得数据长度["+sb.toString().length()+"],并断开连接", null, LogUtilMg.LOG_INFO, CT.LOG_PATTERN_COMMUNICATION);
		} catch (MalformedURLException e) {
			LogUtil.log(CT.LOG_CATEGORY_ERR, "URL地址解析失败", e, LogUtilMg.LOG_ERROR, CT.LOG_PATTERN_ERR);
		} catch (IOException e) {
			LogUtil.log(CT.LOG_CATEGORY_ERR, "网络数据流操作失败", e, LogUtilMg.LOG_ERROR, CT.LOG_PATTERN_ERR);
		} finally{
			 try {
					if(reader != null) reader.close();
					if(connection != null) connection.disconnect();
				} catch (IOException e) {
					LogUtil.log(CT.LOG_CATEGORY_ERR, "关闭数据流操作失败", e, LogUtilMg.LOG_ERROR, CT.LOG_PATTERN_ERR);
				}
		}
		return sb.toString();
	}
	
	public  String httpPost(String content,String url,String charset){
		 HttpURLConnection connection = null;
		 DataOutputStream out = null;
		 BufferedReader reader = null;
		 StringBuffer sb = new StringBuffer();
		try {
			LogUtil.log(CT.LOG_CATEGORY_COMMUNICATION, "1.将进行HttpPost请求", null, LogUtilMg.LOG_INFO, CT.LOG_PATTERN_COMMUNICATION);
			
	        URL postUrl = new URL(url);
	        connection = (HttpURLConnection) postUrl.openConnection();
	        // 设置是否向connection输出
	        connection.setDoOutput(true);
	        // 设置是否向connection输入
	        connection.setDoInput(true);
	        // Default is GET
	        connection.setRequestMethod("POST");
	        // Post请求不能使用缓存
	        connection.setUseCaches(false);
	        
	        //connection.setFollowRedirects(true);
	        
	        connection.setInstanceFollowRedirects(true);
	        
	        // 配置application/x-www-form-urlencoded
	        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
	        connection.setRequestProperty("Content-Length", String.valueOf(content.getBytes().length));   
	        // 配置必须要在connect之前完成
	        connection.connect();
	   
	        LogUtil.log(CT.LOG_CATEGORY_COMMUNICATION, "2.成功创建连接到["+url+"]连接", null, LogUtilMg.LOG_INFO, CT.LOG_PATTERN_COMMUNICATION);
			
	        out = new DataOutputStream(connection.getOutputStream());
	        //out.writeBytes(content); 
	        out.write(content.getBytes(charset));
	        out.flush();
	       
	        reader = new BufferedReader(new InputStreamReader( connection.getInputStream(),charset));
	        String line;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line);
	        }
	        
	        LogUtil.log(CT.LOG_CATEGORY_COMMUNICATION, "3.成功获得数据长度["+sb.toString().length()+"],并断开连接", null, LogUtilMg.LOG_INFO, CT.LOG_PATTERN_COMMUNICATION);
		} catch (MalformedURLException e) {
			LogUtil.log(CT.LOG_CATEGORY_ERR, "URL地址解析失败", e, LogUtilMg.LOG_ERROR, CT.LOG_PATTERN_ERR);
		} catch (IOException e) {
			LogUtil.log(CT.LOG_CATEGORY_ERR, "网络数据流操作失败", e, LogUtilMg.LOG_ERROR, CT.LOG_PATTERN_ERR);
		} finally{
			 try {
				    if(out != null) out.close();
					if(reader != null) reader.close();
					if(connection != null) connection.disconnect();
				} catch (IOException e) {
					LogUtil.log(CT.LOG_CATEGORY_ERR, "关闭数据流操作失败", e, LogUtilMg.LOG_ERROR, CT.LOG_PATTERN_ERR);
				}
		}
		
		return sb.toString();
	}

	public JSONObject httpPost(JSONObject requestJson, String url,String charset) {
		 HttpURLConnection connection = null;
		 DataOutputStream out = null;
		 BufferedReader reader = null;
		 JSONObject resultJson = null;
		try {
			LogUtil.log(CT.LOG_CATEGORY_COMMUNICATION, "1.将进行HttpPost请求", null, LogUtilMg.LOG_INFO, CT.LOG_PATTERN_COMMUNICATION);
			
			Iterator<?> it = requestJson.keySet().iterator();
	    	StringBuffer content = new StringBuffer();
			while(it.hasNext()){
				Object nextObj = it.next();
				if(nextObj != null) {
					String name = nextObj.toString();
					content.append(name).append(CT.SYMBOL_DYH).append(requestJson.getString(name)).append(CT.SYMBOL_ADF);	
				}
			}
			String data = content.substring(0, content.length()-1);
			
	        URL postUrl = new URL(url);
	        connection = (HttpURLConnection) postUrl.openConnection();
	        // 设置是否向connection输出
	        connection.setDoOutput(true);
	        // 设置是否向connection输入
	        connection.setDoInput(true);
	        // Default is GET
	        connection.setRequestMethod("POST");
	        // Post请求不能使用缓存
	        connection.setUseCaches(false);
	        
	        //connection.setFollowRedirects(true);
	        
	        connection.setInstanceFollowRedirects(true);
	        
	        // 配置application/x-www-form-urlencoded
	        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
	        connection.setRequestProperty("Content-Length", String.valueOf(data.getBytes(charset).length));   
	        // 配置必须要在connect之前完成
	        connection.connect();
	   
	        LogUtil.log(CT.LOG_CATEGORY_COMMUNICATION, "2.成功创建连接到["+url+"]连接", null, LogUtilMg.LOG_INFO, CT.LOG_PATTERN_COMMUNICATION);
			
	        out = new DataOutputStream(connection.getOutputStream());
	        //out.writeBytes(content); 
	        out.write(data.getBytes(charset));
	        out.flush();
	       
	        reader = new BufferedReader(new InputStreamReader( connection.getInputStream(),charset));
	        StringBuffer buffer = new StringBuffer();
	        String line;
	        while ((line = reader.readLine()) != null) {
	        	buffer.append(line);
	        }
	        
	        String httpResult = buffer.toString();
	        if(httpResult.trim().length() > 0){
	        	resultJson = JSONUtils.getJsonFromString(httpResult);
	        }
	        
	        LogUtil.log(CT.LOG_CATEGORY_COMMUNICATION, "3.成功获得数据长度["+buffer.toString().length()+"],并断开连接", null, LogUtilMg.LOG_INFO, CT.LOG_PATTERN_COMMUNICATION);
		} catch (MalformedURLException e) {
			LogUtil.log(CT.LOG_CATEGORY_ERR, "URL地址解析失败", e, LogUtilMg.LOG_ERROR, CT.LOG_PATTERN_ERR);
		} catch (IOException e) {
			LogUtil.log(CT.LOG_CATEGORY_ERR, "网络数据流操作失败", e, LogUtilMg.LOG_ERROR, CT.LOG_PATTERN_ERR);
		} finally{
			 try {
				    if(out != null) out.close();
					if(reader != null) reader.close();
					if(connection != null) connection.disconnect();
				} catch (IOException e) {
					LogUtil.log(CT.LOG_CATEGORY_ERR, "关闭数据流操作失败", e, LogUtilMg.LOG_ERROR, CT.LOG_PATTERN_ERR);
				}
		}
		
		return resultJson;
	}

	/**
	 * 不用实现
	 */
	public String simplePost(JSONObject jsonParam, String remoteUrl,
			String charSet) {
		return null;
	}

	public JSONObject wxHttpsPost(JSONObject paramContent, String url) {
		// TODO Auto-generated method stub
		return null;
	}

	public JSONObject wxHttpsPost(String paramContent, String url) {
		// TODO Auto-generated method stub
		return null;
	}

	public JSONObject wxHttpsGet(JSONObject paramContent, String url) {
		// TODO Auto-generated method stub
		return null;
	}
}
