<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    
<meta name="apple-mobile-web-app-capable" content="yes" />    
<meta name="format-detection" content="telephone=no" />  
<title>微信api测试</title>
<%@ include file="/www/include/common.jsp"%>
</head>
<body>
userList: <br />
<c:forEach var="openid" items="${openidList }">
<div>${fn:replace(openid, '"', '')}</div>
<div>
	<a href="/webapp/wxUser/getUserBaseInfo.do?openid=${openid }">查询用户基本信息</a>
	<a href="/webapp/wxUser/getUserGroup.do?openid=${openid }">查询用户组信息</a>
</div>
</c:forEach>
</body>
</html>
