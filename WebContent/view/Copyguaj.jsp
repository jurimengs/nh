<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="yes" />    
	<meta name="format-detection" content="telephone=no" />  
	
    <title>Lottery Demo</title>
    <style type="text/css">
        body{
            height:1000px;
        }
        #lotteryContainer {
            position:relative;
            width: 300px;
            height:100px;
        }
        #drawPercent {
            color:#F60;
        }
    </style>
</head>
<body>
    <button id="freshBtn">刷新彩票</button>
    <label>已刮开 <span id="drawPercent">0%</span> 区域。</label>
    <div id="lotteryContainer"></div>
    <script src="/js/Lottery.js"></script>
    <script>
        window.onload = function () {
            var lottery = new Lottery('lotteryContainer', '#CCC', 'color', 300, 100, null, drawPercent);
            //lottery.init('http://www.baidu.com/img/bdlogo.gif', 'image');
            lottery.init("aaa", 'text');

            document.getElementById('freshBtn').onclick = function() {
                drawPercentNode.innerHTML = '0%';
                lottery.init("test", 'text');
            };


        };

        function drawPercent(percent) {
        	var drawPercentNode = document.getElementById('drawPercent');
            drawPercentNode.innerHTML = percent + '%';
        }
        
        function getRandomStr(len) {
            var text = "";
            var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            for( var i=0; i < len; i++ )
                text += possible.charAt(Math.floor(Math.random() * possible.length));
            return text;
        }
    </script>
</body>
</html>
