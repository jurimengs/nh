<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.net.Socket"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Map.Entry"%>

<%
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html");
    String url = request.getParameter("url");
    String params = request.getParameter("params");
   	String rjson = ""; 
    try{
    	//sbf.append("\"state\":\"0\"");
    	HashMap<String,String> hm = new HashMap<String,String>();
    	String busiInfo = "\"busiInfo\":{"+params+"}";
    	System.out.println(busiInfo+"---"+url);
		hm.put("data", "{\"version\":\"ver1.0.0\",\"charset\":\"UTF-8\",\"accessChannelNo\":\"00000003\",\"accessType\":\"0002\",\"commType\":\"0001\",\"deviceType\":\"\",\"deviceInfo\":{\"devID\":\"999999999999\",\"duid\":\"\"},\"attachDeviceType\":\"\",\"attachDeviceInfo\":{},\"clientSecurityInfo\":{\"memid\":\"\",\"sessionid\":\"\",\"token\":\"\",\"uuid\":\"\",\"sid\":\"\"},\"apiName\":\"\",\"busiType\":\""+url+"\","+busiInfo+",\"signType\":\"SAS\",\"sign\":\"\"}");
		System.out.println(hm.get("data")+"----");
		rjson=doPost(dourl,hm,requestEncoding);
    }catch (Exception e){
    	rjson="{\"respCode\":\"-1\"}";
        e.printStackTrace();
    }
    System.out.println(rjson+"----------");
    String callback = request.getParameter("callback");
    out.write(callback+"("+rjson+")"); 
%>
<%!
private static int connectTimeOut = 5000;
private static int readTimeOut = 10000;
private static String requestEncoding = "utf-8";
private static String dourl = "http://172.28.249.11:7880/mobile/controller.srv"; 
public static String doPost(String reqUrl, Map parameters, String recvEncoding) {
	HttpURLConnection url_con = null;
	String responseContent = null;
	try {
	    StringBuffer params = new StringBuffer();
	    for (Iterator iter = parameters.entrySet().iterator(); iter.hasNext();) {
		Entry element = (Entry) iter.next();
		params.append(element.getKey().toString());
		params.append("=");
		params.append(URLEncoder.encode(element.getValue().toString(), requestEncoding));
		params.append("&");
	    }

	    if (params.length() > 0) {
		params = params.deleteCharAt(params.length() - 1);
	    }

	    URL url = new URL(reqUrl);
	    url_con = (HttpURLConnection) url.openConnection();
	    url_con.setRequestMethod("POST");
	    System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(connectTimeOut));
	    System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(readTimeOut));

	    url_con.setDoOutput(true);
	    byte[] b = params.toString().getBytes();
	    url_con.getOutputStream().write(b, 0, b.length);
	    url_con.getOutputStream().flush();
	    url_con.getOutputStream().close();

	    InputStream in = url_con.getInputStream();
	    BufferedReader rd = new BufferedReader(new InputStreamReader(in, recvEncoding));
	    String tempLine = rd.readLine();
	    StringBuffer tempStr = new StringBuffer();
	    String crlf = System.getProperty("line.separator");
	    while (tempLine != null) {
		tempStr.append(tempLine);
		tempStr.append(crlf);
		tempLine = rd.readLine();
	    }
	    responseContent = tempStr.toString();
	    rd.close();
	    in.close();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
	    if (url_con != null) {
		url_con.disconnect();
	    }
	}
	return responseContent;
}
%>