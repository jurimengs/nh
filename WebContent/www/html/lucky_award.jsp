<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />

<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="format-detection" content="telephone=no" />
<title>幸运奖</title>
<%@ include file="/www/include/common.jsp"%>

<style>
.run_class{
	display: none;
	top: 100px;
	width:100px;
	height:100px;
	background:red;
	position:relative;
	float: left;
	animation: active_class 5s linear 2s infinite alternate;
	/* Firefox: */
	-moz-animation: active_class 2s linear 1s;
	/* Safari and Chrome: */
	-webkit-animation: active_class 5s linear 2s infinite alternate;
	/* Opera: */
	-o-animation: active_class 5s linear 2s infinite alternate;
}
</style>

<style id="">

@keyframes active_class{
}

@-moz-keyframes active_class /* Firefox */{
	0%   { }
	100% {};
}

@-webkit-keyframes active_class /* Safari and Chrome */{
}

@-o-keyframes active_class /* Opera */{
}

</style>
<script type="text/javascript" charset="utf-8" src="/www/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/www/js/browserinfo.js"></script>

</head>
<body>
<!-- <div class="run_class"></div> -->
<!-- <div class="run_class" style="left: 100px;"></div> -->

<c:forEach items="${userlist }" var="user" varStatus="temp">
	<div id="div_${temp.index }" class="run_class">${user }</div>
</c:forEach>
</body>

<script type="text/javascript">
var temp = "${userlist }";
var userlist = temp.split(",");

var i = 0;
var intervalId = setInterval("show()", 2000);
function show(){
	if(userlist.length <= i) {
		clearInterval(intervalId);
	}
	
	$("#div_" + i).fadeIn();
	i++;
}


</script>
</html>
