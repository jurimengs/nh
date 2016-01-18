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

<script type="text/javascript" charset="utf-8" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

<script type="text/javascript">
//window.location.href="/view/login.jsp";
//alert('${appId}' + ": " + '${signature }' + '   ${timestamp }' + '  ${nonceStr }' + '');
wx.config({
    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    appId: '${appId}', // 必填，公众号的唯一标识
    timestamp: '${timestamp }', // 必填，生成签名的时间戳
    nonceStr: '${nonceStr }', // 必填，生成签名的随机串
    signature: '${signature }',// 必填，签名，见附录1
    jsApiList: [
		'onMenuShareTimeline',
		'onMenuShareAppMessage',
		'onMenuShareQQ',
		'onMenuShareWeibo',
		'onMenuShareQZone',
		'startRecord',
		'stopRecord',
		'onVoiceRecordEnd',
		'playVoice',
		'pauseVoice',
		'stopVoice',
		'onVoicePlayEnd',
		'uploadVoice',
		'downloadVoice',
		'chooseImage',
		'previewImage',
		'uploadImage',
		'downloadImage',
		'translateVoice',
		'getNetworkType',
		'openLocation',
		'getLocation',
		'hideOptionMenu',
		'showOptionMenu',
		'hideMenuItems',
		'showMenuItems',
		'hideAllNonBaseMenuItem',
		'showAllNonBaseMenuItem',
		'closeWindow',
		'scanQRCode',
		'chooseWXPay',
		'openProductSpecificView',
		'addCard',
		'chooseCard',
		'openCard'
	] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
});

wx.error(function(res){
	var s = "";
	for (var k in res) {
		s += k + ":" + res[k];
	}
	alert("error:\n"+s);
});

wx.ready(function(){
	//alert("loading success");
	$("#codebarscannBtn").click();
});

function codebarScann(callback){
	wx.scanQRCode({
	    needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
	    scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
	    success: callback
	});
}

function codebarScannCallback(res){
	var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
	alert(result);
	window.location.href = result;
}
</script>

</head>

<body>
<%-- <div>token: ${cacheToken }</div><br />

<div>signature: ${signature }</div><br />
<div>timestamp: ${timestamp } </div><br />
<div>nonceStr: ${nonceStr }</div><br />

<input onclick="getGroupidList();" type="button" value="getGroupidList"/>
<input onclick="getUserList();" type="button" value="getUserList"/>
<input onclick="deleteBottomMenu();" type="button" value="deleteBottomMenu"/>
<input onclick="initBottomMenu();" type="button" value="initBottomMenu"/>
<input onclick="getCacheToken();" type="button" value="getCacheToken"/>
<input onclick="takePic();" type="button" value="微信图片接口"/>
<div id="wxPic">
</div>
<span id="spanmsg"></span>
 --%>

<input id="codebarscannBtn" style="display: none;" onclick="codebarScann(codebarScannCallback);" type="button" />

</body>
</html>
