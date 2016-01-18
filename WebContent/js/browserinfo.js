
(function(window) {
	// 获取浏览器
	// 由于控件升级不能覆盖, 所以所有的方案改为, 默认重启浏览器
	var ua = navigator.userAgent.toLowerCase();
	var rMsie = /(msie\s|trident.*rv:)([\w.]+)/;
	var rFirefox = /(firefox)\/([\w.]+)/;
	var rOpera = /(opera).+version\/([\w.]+)/;
	//var rChrome = /(chrome)\/([\w.]+)/;
	var rSafari = /version\/([\w.]+).*(safari)/;
	
	var match = rMsie.exec(ua);   
	if (match != null){
		this.browserName = "IE";
	}
	
	match = rFirefox.exec(ua);
	if (match != null){
		this.browserName = match[1]; 
	}
	
	if (window.MessageEvent && !document.getBoxObjectFor){
		var chromeMatch = ua.match(/chrome\/([\d.]+)/);
		if(chromeMatch && chromeMatch.length > 1){
			var baiduMatch = ua.match(/bidubrowser\/([\d.]+)/);
			if(!!baiduMatch){
				this.browserName = "BaiDu"; 
			} else {
				this.browserName = "Chrome"; 
			}
		}
	}
	
	match = rOpera.exec(ua);   
	if (match != null){
		this.browserName = match[1]; 
	}
	
	match = rSafari.exec(ua);   
	if (match != null){
		this.browserName = match[2]; 
	}
	
    window.browser = this;
})(window);