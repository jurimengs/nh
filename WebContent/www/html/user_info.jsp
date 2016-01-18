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
<script type="text/javascript" charset="utf-8" src="/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/js/browserinfo.js"></script>
<script type="text/javascript" charset="utf-8" src="/js/ipone-music-compatibility.js"></script>

<script type="text/javascript">
</script>

</head>

<body>
用户：${usermeg }

<br />
<br />
<br />

中奖：${rewardarrary }

<div id="ado"></div>

<button onclick="startPlay();">开始</button>
<button onclick="stopPlay();">停止</button>

<br />
<br />
<br />
<span id="tt"></span>
</body>
<script type="text/javascript">
$(document).ready(function(){   
    if(/i(Phone|P(o|a)d)/.test(navigator.userAgent)){
        $(document).one('touchstart', function (e) {
        g_audio.touchstart = true;
        g_audio.play();
        g_audio.pause();
        return false;
        });
    }
});
 

var es = new EventSource('/message.do');
es.onmessage = function(e) {
    //alert(e.data);
    var jsonData = eval("("+e.data+")");
    $("#tt").append(e.data);
};

es.addEventListener('myevent', function(e) {
	//alert(e.data);
});
function startPlay(){
	try{
		//alert(g_audio);
		g_audio.elems["id"] = "aaa";
	    if(browser.browserName == "safari") {
		    g_audio.push({song_id:"aaa",song_fileUrl:"/files/music/1.mp3"});
	    } else {
		    g_audio.push({song_id:"aaa",song_fileUrl:"/files/music/1.wav"});
	    }
	} catch(e) {
		alert(e);
	}

}

function stopPlay(){
	try{
	    g_audio.pause();
	} catch(e) {
		alert(e);
	}
}
</script>
</html>
