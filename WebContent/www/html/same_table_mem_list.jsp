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
<style type="text/css">
.wx_priview {
	max-width:320px;
}
</style>
<script type="text/javascript" charset="utf-8" src="/www/js/jquery-1.11.1.min.js"></script>

<script type="text/javascript">
</script>

</head>

<body>
用户：${usermeg }

<br />
<br />
<br />

同桌其他用户：${otherMembers }

</body>
</html>
