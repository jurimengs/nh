package com.org.utils.http.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.org.log.LogUtil;
import com.org.log.impl.LogUtilMg;
import com.org.util.CT;
import com.org.utils.JSONUtils;
import com.org.utils.http.HttpTool;

/**
 * @author Nano
 * 
 * HTTPClient 的封装
 */
public class HttpApacheClient implements HttpTool{

	public String httpGet(String url,String charset) {
		String httpResult = "";
		AbstractHttpClient httpclient = null;
		try{
			HttpGet httpRequest = new HttpGet(url);
			
		    // 设置 超时机制 毫秒为单位,重连3机制
            HttpParams params = new BasicHttpParams();  
            HttpConnectionParams.setConnectionTimeout(params, 30 * 1000);  
            HttpConnectionParams.setSoTimeout(params, 45 * 1000);    
            HttpConnectionParams.setTcpNoDelay(params, true);
            HttpClientParams.setRedirecting(params, false);
       
            httpclient = new DefaultHttpClient(params); 
            HttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(3,true);
            httpclient.setHttpRequestRetryHandler(retryHandler);
            
            
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){//请求成功 
				httpResult = EntityUtils.toString(httpResponse.getEntity(),charset);
				
				LogUtil.log(CT.LOG_CATEGORY_COMMUNICATION, "HttpApacheClient  httpGet 请求成功 --> " + httpResult, null, LogUtilMg.LOG_INFO, CT.LOG_PATTERN_COMMUNICATION);	
				
			}else{
				int statusCode = httpResponse.getStatusLine().getStatusCode() ;	
				System.out.println("HttpApacheClient httpGet返回相应码 --> " + statusCode);
			}
		}catch(Exception e){
			LogUtil.log(CT.LOG_CATEGORY_ERR, "httpGet请求失败 ", e, LogUtilMg.LOG_ERROR, CT.LOG_PATTERN_ERR);
		}finally{
			if(httpclient != null)httpclient.getConnectionManager().shutdown();  
		}
		return httpResult;
	}

	public String httpPost(String paramContent, String url,String charset) {
		String httpResult = "";
		AbstractHttpClient httpclient = null;
		try{
			HttpPost httpRequest = new HttpPost(url);
			List<NameValuePair> params = new ArrayList<NameValuePair>(); 
			String[] paramValues = paramContent.split("&");
			for (int i = 0; i < paramValues.length; i++) {
				String[] paramValue = paramValues[i].split("=");
				if(paramValue != null && paramValue.length == 2){
					params.add(new BasicNameValuePair(paramValue[0], paramValue[1]));					
				}else{
					LogUtil.log(CT.LOG_CATEGORY_ERR, "传递进来的参数解析不合法,原始数据 --> "+ paramValues[i], null, LogUtilMg.LOG_INFO, CT.LOG_PATTERN_ERR);
				}
			}
			
			HttpEntity httpentity = new UrlEncodedFormEntity(params, charset);
			httpRequest.setHeader("accept", "*/*");
			httpRequest.setHeader("connection", "Keep-Alive");
			httpRequest.setEntity(httpentity);
			
			//设置超时 毫秒为单位,重连机制
			HttpParams httpParams = new BasicHttpParams();  
	        HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);  
	        HttpConnectionParams.setSoTimeout(httpParams, 45 * 1000);    
	        HttpConnectionParams.setTcpNoDelay(httpParams, true);
	        HttpClientParams.setRedirecting(httpParams, false);
	        
		    httpclient = new DefaultHttpClient(httpParams);
		    HttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(3,true);
            httpclient.setHttpRequestRetryHandler(retryHandler);
		   
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){//HttpStatus.SC_OK表示连接成功
				 httpResult = EntityUtils.toString(httpResponse.getEntity(),charset);
				 LogUtil.log(CT.LOG_CATEGORY_COMMUNICATION, "HttpApacheClient  httpPost 请求成功 --> " + httpResult, null, LogUtilMg.LOG_INFO, CT.LOG_PATTERN_COMMUNICATION);		
			}else{
				 int statusCode = httpResponse.getStatusLine().getStatusCode() ;
				 LogUtil.log(CT.LOG_CATEGORY_COMMUNICATION, "HttpApacheClient httpPost返回相应码 --> " + statusCode, null, LogUtilMg.LOG_INFO, CT.LOG_PATTERN_COMMUNICATION);	
			}
		}catch(Exception e){
			LogUtil.log(CT.LOG_CATEGORY_ERR, "httpPost 请求失败 ", e, LogUtilMg.LOG_ERROR, CT.LOG_PATTERN_ERR);
		}finally{
			if(httpclient != null)httpclient.getConnectionManager().shutdown();  
		}
		return httpResult;
	} 
	
	public JSONObject httpPost(JSONObject requestJson, String url,
			String charset) {
		JSONObject resultJson = null;
		AbstractHttpClient httpclient = null;
		try {
			HttpPost httpRequest = new HttpPost(url);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			Iterator<?> it = requestJson.keySet().iterator();
			while(it.hasNext()){
				Object nextObj = it.next();
				if(nextObj != null) {
					String name = nextObj.toString();
					params.add(new BasicNameValuePair(name,requestJson.getString(name)));
				}
			}
			
			HttpEntity httpentity = new UrlEncodedFormEntity(params, charset);
			httpRequest.setHeader("accept", "*/*");
			httpRequest.setHeader("connection", "Keep-Alive");
			httpRequest.setEntity(httpentity);

			// 设置超时 毫秒为单位,重连机制
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);
			HttpConnectionParams.setSoTimeout(httpParams, 45 * 1000);
			HttpConnectionParams.setTcpNoDelay(httpParams, true);
			HttpClientParams.setRedirecting(httpParams, false);

			httpclient = new DefaultHttpClient(httpParams);
			HttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(
					3, true);
			httpclient.setHttpRequestRetryHandler(retryHandler);

			HttpResponse httpResponse = httpclient.execute(httpRequest);

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {// HttpStatus.SC_OK表示连接成功
				String httpResult = EntityUtils.toString(httpResponse.getEntity(),charset);

				resultJson = JSONUtils.getJsonFromString(httpResult);
//				LogUtil.log(CT.LOG_CATEGORY_COMMUNICATION,"HttpApacheClient  httpPost 请求成功 --> " + resultJson,
//						null, LogUtilMg.LOG_INFO, CT.LOG_PATTERN_COMMUNICATION);
			} else {
				int statusCode = httpResponse.getStatusLine().getStatusCode();
				LogUtil.log(CT.LOG_CATEGORY_COMMUNICATION,
						"HttpApacheClient httpPost返回相应码 --> " + statusCode,
						null, LogUtilMg.LOG_INFO, CT.LOG_PATTERN_COMMUNICATION);
			}
		} catch (Exception e) {
			LogUtil.log(CT.LOG_CATEGORY_ERR, "httpPost 请求失败 ", e,LogUtilMg.LOG_ERROR, CT.LOG_PATTERN_ERR);
		} finally {
			if (httpclient != null){
				httpclient.getConnectionManager().shutdown();
			}
		}
		return resultJson;
	}

	public JSONObject wxHttpsPost(JSONObject requestJson, String url) {
		return httpsRequest(url, "POST", requestJson.toString());
	}
	
	public JSONObject wxHttpsPost(String requestJson, String url) {
		return httpsRequest(url, "POST", requestJson);
	}
	
	public JSONObject wxHttpsGet(JSONObject requestJson, String url) {
		return httpsRequest(url, "GET", requestJson.toString());
	}
	
	public String simplePost(JSONObject jsonParam, String remoteUrl, String charSet){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String key ="";
		for (Iterator<?> iterator = jsonParam.keys(); iterator.hasNext();) {
			key = String.valueOf(iterator.next());
			params.add(new BasicNameValuePair(key,jsonParam.getString(key)));
		}

	    UrlEncodedFormEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(params,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	    HttpPost post = new HttpPost(remoteUrl);
		post.setHeader("accept", "*/*");
	    post.setHeader("connection", "Keep-Alive");
	    post.setEntity(entity);
	    
	    HttpClient client = new DefaultHttpClient();
	    HttpResponse response = null;
	    HttpEntity httpEntity = null;
	    String result = "";
		try {
			response = client.execute(post);
			httpEntity = response.getEntity();
			result = EntityUtils.toString(httpEntity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return result;
	}
	
	public JSONObject httpsRequest(String requestUrl, String httpMethod, String defaultStr){
		JSONObject res = new JSONObject();
		try {
			TrustManager[] tm = {new MyX509TrustManager()};
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new SecureRandom());
			SSLSocketFactory sf = sslContext.getSocketFactory();
			
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(sf);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod(httpMethod);
			
			if(defaultStr != null) {
				OutputStream os = conn.getOutputStream();
				os.write(defaultStr.getBytes("UTF-8"));
				os.close();
			}
			
			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			
			String str = null;
			StringBuffer sb = new StringBuffer();
			while((str = br.readLine()) != null){
				sb.append(str);
			}
			br.close();
			isr.close();
			is.close();
			is = null;
			conn.disconnect();
			
			res = JSONObject.fromObject(sb.toString());
			
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public static void main(String[] args) {

		HttpApacheClient s = new HttpApacheClient();
		//System.out.println(s.httpGet("http://172.28.250.129:3128/", "UTF-8"));
		System.out.println(s.httpGet("http://www.baidu.com", "UTF-8"));
	}
	
	public class MyX509TrustManager implements X509TrustManager {

		// 检查客户端证书
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			
		}
		
		// 检查服务端证书
		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			
		}
		
		//返回受信任的X509数组
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
		
	}
	
}
