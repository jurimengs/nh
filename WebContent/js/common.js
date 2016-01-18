
(function(window){
	
	Array.prototype.contains = function(item){
		for (var i=0; i < this.length; i++) {
			if (this[i] === item){
	            return true;
			}
	    }
	    return false;
	};
	
	var session = {};

	var $_d = function(i, fromSession){
		if ( window == this ){
			return new $d(i);
		}
		this._$_d = null;
		if(typeof i == "object"){
			this._$_d = i;
			this.id = i.id;
		} else if(typeof i == "string"){
			if(!!! fromSession){
				// 如果fromSession为空, 则默认方案为不从缓存取
				fromSession = false;
			}
			this._$_d = $$(i, fromSession);
			this.id = i;
		} else {
			// for array
			this._$_d = i;
		}
		this.copyAttr();
		//判断设备类型
		this.initDeviceType();
		
	},
	$$ = function(id, fromSession){
		if(fromSession){
			if(!! session[id]){
				//alert("已缓存");
				return session[id];
			} else {
				var o = document.getElementById(id);
				if(!!! o) {
					return null;
				}
				session[id] = o;
				return session[id];
			}
		}
		return document.getElementById(id);
	};

	$_d.prototype.copyAttr = function(o) {
		if(!!! this._$_d) {
			return false;
		}
		if(!!! o) {
			// if param object is null , copy owner object's attributes
			o = this._$_d;
		}
		var i = 0;
		for(var k in o) {
			i += 1;
			this[k] = o[k];
		}
		return this;
	};
	
	$_d.prototype.initDeviceType = function(){
		var sUserAgent= navigator.userAgent.toLowerCase();
		var bIsIpad= sUserAgent.match(/ipad/i) == "ipad";
		var bIsIphoneOs= sUserAgent.match(/iphone os/i) == "iphone os";
		var bIsMidp= sUserAgent.match(/midp/i) == "midp";
		var bIsUc7= sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
		var bIsUc= sUserAgent.match(/ucweb/i) == "ucweb";
		var bIsAndroid= sUserAgent.match(/android/i) == "android";
		var bIsCE= sUserAgent.match(/windows ce/i) == "windows ce";
		var bIsWinPhone= sUserAgent.match(/windows mobile/i) == "windows mobile";

		if (bIsIpad) {
			this.currentDevice = "ipad";
		} else if (bIsIphoneOs) {
			this.currentDevice = "iphone";
		} else if (bIsMidp) {
			this.currentDevice = "midp";
		} else if (bIsUc7) {
			this.currentDevice = "uc7";
		} else if (bIsUc) {
			this.currentDevice = "uc";
		} else if (bIsAndroid) {
			this.currentDevice = "android";
		} else if (bIsCE) {
			this.currentDevice = "ce";
		} else if (bIsWinPhone) {
			this.currentDevice = "windows mobile";
		} else {
			this.currentDevice = "pc";
		}
	};

	$_d.prototype.copyAttrTo = function(from, to) {
		if(!!! from) {
			return false;
		}
		if(!!! to) {
			return false;
		}
		for(var k in from) {
			to.style[k] = from[k];
		}
		return to;
	};
	
	$_d.prototype.remove = function() {
		if(!!! this._$_d){
			return;
		}
		this._$_d.parentNode.removeChild(this._$_d);
		return this;
	};
	
	$_d.prototype.offset = function() {
		// 经过属性复制后,已经有一切属性
		this.top = this.offsetTop;
		this.left = this.offsetLeft;
		return this;
	};
	
	$_d.prototype.submit = function() {
		// 经过属性复制后,已经有一切属性
		$$(this.id).submit();
	};
	
	$_d.prototype.append = function(obj) {
		// 经过属性复制后,已经有一切属性
		$$(this.id).appendChild(obj);
	};
	
	/**
	 * 评论对话框
	 * @id 目标感言ID
	 */
	$_d.prototype.dialogComments = function(id) {
		var o = $$(this.id);
		if(!! o){
			try {
				document.body.removeChild(o);
			} catch (e) {
			}
		}
		var h = 
			'<div class="message" id="maskDiv">' +
				'<div class="modal" id="modalDiv">' +
					'<div class="pop_up_box">' +
						'<div class="modal-header" id="maskHeadDiv">' +
							'<button id="maskCloseBtn" class="close" onclick="document.getElementById(\'maskDiv\').style.display=\'none\'; ">&times;</button>' +
							'<div style="text-align:center;font-size:22px" id="maskTitleDiv">&nbsp;评论</div>' +
						'</div>' +
						'<div class="modal-body grey" id="maskContentDiv">&nbsp;'+
							'<form action="" id="commentsForm" method="post">&nbsp;'+
							'<input type="hidden" id="deviceType" name="deviceType" />'+
							'<input type="hidden" id="testimonialsId" name="testimonialsId" />'+
							'请输入内容:'+
							'<br />'+
							'<textarea class="maskContent" id="content" name="commentContent" placeholder="内容"></textarea>'+
							'<br />'+
							'<a href="javascript:void(0);" id="commentsBtn" class="btn orange" onclick="">发表</a>'+
							'</form>'+
						'</div>' +
					'</div>' +
				'</div>' +
			'</div>';
		o = getObjFromHtml(h);
		document.body.appendChild(o);
		document.getElementById("testimonialsId").value=id;
		// 根据设备类型，判断左右间距、对话框宽度等
		args = {"width":"720px", "marginLeft":"270px"};
		
		if(this.currentDevice != "pc"){
			args = {"width":"100%", "marginLeft":"1px", "top":"1px"};
			// 为手机处理
			var topTemp = $("#modalDiv").css("top");
			$("#testimonialsContent").focus(function(){
				$d("modalDiv").css("top", "-50px");
			});
			$("#testimonialsContent").focusout(function(){
				$d("modalDiv").css("top", topTemp);
			});
		}
		
		
		$d("modalDiv").adjustCenter(args);
		o.style.display = "block";
		return "commentsBtn";
	};
	
	/**
	 * 感言对话框
	 */
	$_d.prototype.dialogTestimonials = function(currentChannelId) {
		// 经过属性复制后,已经有一切属性
		var o = $$(this.id);
		if(!! o){
			try {
				document.body.removeChild(o);
			} catch (e) {
			}
		}
		var h = 
			'<div class="message" id="maskDiv">' +
				'<div class="modal" id="modalDiv">' +
					'<div class="pop_up_box">' +
					'<div class="modal-header" id="maskHeadDiv">' +
						'<button id="maskCloseBtn" class="close" onclick="document.getElementById(\'maskDiv\').style.display=\'none\'; ">&times;</button>' +
						'<div class="pop_title" style="text-align:center;font-size:22px" id="maskTitleDiv">&nbsp;人生感言</div>' +
					'</div>' +
					'<div class="modal-body" id="maskContentDiv">&nbsp;'+
						'<form action="" id="commentsForm" enctype="multipart/form-data" method="post">&nbsp;'+
							'<input type="hidden" id="deviceType" name="deviceType" placeholder="主题" />'+
							'主题:'+
							'<br />'+
							'<input class="input-small commentsTitle" id="testimonialsTitle" name="testimonialsTitle" placeholder="主题" />'+
							'<br />'+
							'感言类型:'+
							'<br />'+
							'<select id="channelId" name="channelId">'+
								'<option value="0">生活频道</option>'+
								'<option value="1">情感频道</option>'+
								'<option value="2">工作频道</option>'+
								'<option value="3">其他频道</option>'+
							'</select>'+
							'<br />'+
							'要不要来张图片:' +
							'<input type="file" id="picFile" name="picFile" placeholder="可心来张图片？" />'+
							'<br />'+
							'您的感言:'+
							'<br />'+
							'<textarea class="maskContent" id="testimonialsContent" name="testimonialsContent" placeholder="内容"></textarea>'+
							'<br />'+
							'<a href="javascript:void(0);" id="submitBtn" class="btn orange" onclick="">发表</a>'+
						'</form>'+
					'</div>' +
					'</div>' +
				'</div>' +
			'</div>';
		o = getObjFromHtml(h);
		document.body.appendChild(o);
		if(!! currentChannelId && currentChannelId != "null"){
			if(currentChannelId == "6") {
				currentChannelId = "0";
			}
			$("#channelId").val(currentChannelId);
		} else {
			$("#channelId").val(0);
		}
		
		var args = {"width":"720px", "marginLeft":"270px"};
		if(this.currentDevice != "pc"){
			args = {"width":"100%", "marginLeft":"1px", "top":"1px"};
			// 为手机处理
			var topTemp = $("#modalDiv").css("top");
			$("#testimonialsContent").focus(function(){
				$d("modalDiv").css("top", "-50px");
			});
			$("#testimonialsContent").focusout(function(){
				$d("modalDiv").css("top", topTemp);
			});
		}
		
		$d("modalDiv").adjustCenter(args);
		o.style.display = "block";
		return "submitBtn";
	};
	
	/**
	 * 普通对话框，内容需要调用处填充
	 */
	$_d.prototype.commonDialog = function(args) {
		// 经过属性复制后,已经有一切属性
		var o = $$(this.id);
		if(!! o){
			try {
				document.body.removeChild(o);
			} catch (e) {
			}
		}
		var h = 
			'<div class="message" id="maskDiv">' +
				'<div class="modal" id="modalDiv">' +
					'<div class="pop_up_box">' +
					'<div class="modal-header" id="maskHeadDiv">' +
						'<button id="maskCloseBtn" class="close" onclick="document.getElementById(\'maskDiv\').style.display=\'none\'; ">&times;</button>' +
						'<div class="pop_title" style="text-align:center;font-size:22px" id="maskTitleDiv">&nbsp;纪念板</div>' +
					'</div>' +
					'<div class="modal-body auto-scroll" id="maskContentDiv">&nbsp;'+
					'</div>' +
					'</div>' +
				'</div>' +
			'</div>';
		o = getObjFromHtml(h);
		document.body.appendChild(o);
		
		var args = {"width":"100%", "marginLeft":"1px", "top":"1px", "marginTop":"1px", "height":"100%"};
		/*if(this.currentDevice != "pc"){
			args = {"width":"100%", "marginLeft":"1px", "top":"1px", "marginTop":"1px"};
		}*/
		
		$d("modalDiv").adjustCenter(args);
		o.style.display = "block";
		// dialog完成后，将maskContentDiv 的id返回, 便于页面向其添加内容
		return "maskContentDiv";
	};
	
	$_d.prototype.debugjsonArgs = function(args){
		/*var str = "";
		for(var k in args){
			str += k +" : "+args[k] + "\n";
		}
		alert(str);*/
	};
	
	$_d.prototype.adjustCenter = function(args) {
		var width = document.body.scrollWidth;
		// 获取屏幕高度
		//var height = screen.height ;
		//alert(height);
		this.debugjsonArgs(args);
		
		var fixed = 20;
		var realWidth = width - fixed * 2;
		
		args = args || {};
		if(!!! args["width"]){
			args["width"] = realWidth+"px";
		}
		//this._$_d.style.height = realHeight+"px";
		if(!!! args["marginLeft"]){
			args["marginLeft"] = fixed + "px";
		}
		if(!!! args["marginTop"]){
			args["marginTop"] = fixed + "px";
		}
		if(!!! args["top"]){
			args["top"] = 150 + "px";
		}
		this.copyAttrTo(args, this._$_d);
		
		return this;
	};
	
	$_d.prototype.css = function(sName, sValue) {
		// 经过属性复制后,已经有一切属性
		this.style[sName] = this._$_d.style[sName] = sValue;
		return this;
	};
	
	$_d.prototype.hide = function() {
		// 经过属性复制后,已经有一切属性
		this.style.display = this._$_d.style.display = "none";
		return this;
	};
	
	$_d.prototype.show = function() {
		// 经过属性复制后,已经有一切属性
		this.style.display = this._$_d.style.display = "block";
		return this;
	};
	
	$_d.prototype.val = function() {
		var thisVal = "";
		if(this.value != null && this.value != undefined){
			thisVal = this.value;
		} else if(this.text != null && this.text != undefined){
			thisVal = this.text;
		} else {
			thisVal = this.innerHTML;
		}
		return thisVal;
	};
	
	/**
	 * 创建一个信息显示区域
	 * @param areaId 生成的区域id, 注意不同的信息间不可有相同的ID.否则效果不出来
	 * @param msg 区域中显示的内容
	 * @param objId 区域添加到该id后面 nextSibling
	 */
	
	$_d.prototype.createMsgAreaBehind = function(areaId, msg, objId) {
		var areaDiv = $$(areaId);
		if(!!! areaDiv){
			// 如果不存在的情况下
			areaDiv = document.createElement("span");
			areaDiv.style.dispay = "none;";
			areaDiv.setAttribute("id", areaId);
			areaDiv.setAttribute("class", "help-inline");
			areaDiv.className = "help-inline";
			areaDiv.innerHTML=msg;
			$$(objId).parentNode.appendChild(areaDiv);
		}
		return areaDiv;
	};
	

	/**
	 * @param obj 位置参考对象
	 * @param locaId 待定位对象
	 * @param xFix x座标位置修正
	 * @param yFix y座标位置修正
	 */
	$_d.prototype.setPosByCoorDinate = function(obj, locatObj, xFix, yFix) {
		// 计算层的位置
		var x = eval(obj.offsetTop) + eval(obj.width) + eval(xFix);
		var y = eval(obj.offsetLeft) - eval(yFix);
		locatObj.style.left = x + "px";
		locatObj.style.top = y + "px";
	};
	
	/**
	 * @param id
	 * @param tips;
	 * @param showTips defalult true;
	 * @returns {Boolean}
	 */
	$_d.prototype.checkValueSame = function(id, tips, showTips) {
		if(!!! id){
			return ;
		}
		var idobj = $$(id);
		if(!!! idobj){
			return;
		}
		
		if(!!! showTips){
			showTips = true;
		}
		
		if(!!! tips){
			// default tip
			tips = "not same";
		}
		
		var evalStr = "'" + this.val()+ "' === '" + idobj.value + "'";
		var isSame = Boolean(eval(evalStr));

		var thisId = this.id;
		var msgAreaId_A = thisId + "Area";
		var msgAreaId_B = id + "Area";

		var a = $$(msgAreaId_A);
		var b = $$(msgAreaId_B);
		if(!! a){
			a.parentNode.removeChild(a);
		}
		if(!! b){
			b.parentNode.removeChild(b);
		}
		if(!isSame){
			this.createMsgAreaBehind(msgAreaId_A, tips, thisId);
			this.createMsgAreaBehind(msgAreaId_B, tips, id);
		}
		return isSame;
	};

	$_d.prototype.notNull = function(id, msg) {
		var a = this._$_d;
		if(!! id){
			a = $$(id);
		}
		if(!!! msg){
			msg = "not null";
		}
		var tipId = "notNull_"+a.id;
		var b = $$(tipId);
		if(!! b) {
			b.parentNode.removeChild(b);
		}
		if(!! a.value){
			return true;
		} else {
			var _notNull = this.createMsgAreaBehind(tipId, msg, a.id);
			this.setPosByCoorDinate(a, _notNull, 25, -4);
			return false;
		}
	};
	
	window.$d = $_d;
})(window);

var notNullArr = function (ids){
	var i;
	var len = ids.length;
	// 默认true, 表示非空
	var a = true;
	for(i=0; i<len; i+=1){
		if(!$d(ids[i]).notNull()){
			a = false;
		}
	}
	return a;
};

function createButton(){
	var btnDiv = $d('div');
//	var btn_pwdSure = document.createElement("button");
	var btn_pwdSure = document.createElement("input");
	$(btn_pwdSure).attr("type", "button");
	$(btn_pwdSure).attr("id", "pwdSure");
	$(btn_pwdSure).attr("class", "btn btn-primary");
	$(btn_pwdSure).css("width", "95px");
	$(btn_pwdSure).val("密码确认");
	
	//var btn_pwdCancel = document.createElement("button");
	var btn_pwdCancel = document.createElement("input");
	$(btn_pwdCancel).attr("type", "button");
	$(btn_pwdCancel).attr("id", "pwdCancel");
	$(btn_pwdCancel).attr("class", "btn");
	$(btn_pwdCancel).css("width", "95px");
	$(btn_pwdCancel).val("取消");

	$(btnDiv).append(btn_pwdSure);
	$(btnDiv).append(btn_pwdCancel);
	return btnDiv;
}

function getObjFromHtml(htmlStr){
	var tempDiv = document.createElement("div"); 
	tempDiv.innerHTML = htmlStr;
	obj = tempDiv.childNodes[0];
	tempDiv = null;
	return obj;
}

function cancelBubble(){
	var e = getEvent();
	if(window.event){
		//e.returnValue=false;//阻止自身行为
		e.cancelBubble=true;//阻止冒泡
	}else if(e.preventDefault){
		//e.preventDefault();//阻止自身行为
		e.stopPropagation();//阻止冒泡
	}
}

function getEvent(){
    if(window.event) {
    	return window.event;
    }
    var func = getEvent.caller;
    while(func != null){
        var arg0 = func.arguments[0];
        if(arg0){
            if((arg0.constructor==Event || arg0.constructor ==MouseEvent
               || arg0.constructor==KeyboardEvent)
               ||(typeof(arg0)=="object" && arg0.preventDefault
               && arg0.stopPropagation)){
                return arg0;
            }
        }
        func=func.caller;
    }
    return null;
}

function getYYYYMMDD () {
	// 当前日期
	var cdate = new Date();
	var Y = cdate.getFullYear()+"";
	var M = cdate.getMonth() + 1; // getMonth的值是0-11的
	if(M <= 9) {
		M = "0"+M;
	}
	var D = cdate.getDate();
	if(D <= 9) {
		D = "0"+D;
	}
	
	return Y+M+D;
};

function getCurrentTimeMillis () {
	// 当前日期
	return new Date().getTime();
};



var pageComponent = {
	operateFlag : null,
	checkedClassFlag : "checkbox_div",
	totalMoneyShowIn : "totalPrice",
	totalMoney: 0, // 单位元, 类型字符串
	init : function(){
		this.totalMoney = 0;
		this.checkedClassFlag = "checkbox_div";
		this.totalMoneyShowIn = "totalPrice";
		this.operateFlag = 0;
		// 选择框全部隐藏
		var inp_squre_sel = $(".inp_squre_sel");
		inp_squre_sel.hide();
		inp_squre_sel.removeClass(this.checkedClassFlag);
		inp_squre_sel.css("background-image", "url(/www/images/selected-grey.png)");
	},
	multiChoose : function (operateFlag){
		this.init();
		this.showHide('menuDiv');
		this.operateFlag = operateFlag;
		var all_inp_squre_sel = $(".inp_squre_sel");
		
		if("delete" == operateFlag) {
			$("#footerLi").show();
			$("#recieveMoney").hide();
			$("#upToShelf").hide();
			$("#downFromShelf").hide();
			$("#deleteBtn").show();
		} else if("recieveMoney" == operateFlag) {
			$("#footerLi").show();
			$("#recieveMoney").show();
			$("#upToShelf").hide();
			$("#deleteBtn").hide();
			$("#downFromShelf").hide();
		} else if("upToShelf" == operateFlag) {
			$("#footerLi").show();
			$("#recieveMoney").hide();
			$("#upToShelf").show();
			$("#deleteBtn").hide();
			$("#downFromShelf").hide();
		} else if("downFromShelf" == operateFlag) {
			$("#footerLi").show();
			$("#recieveMoney").hide();
			$("#upToShelf").hide();
			$("#deleteBtn").hide();
			$("#downFromShelf").show();
		} else if("edit" == operateFlag) {
			$("#footerLi").hide();
			all_inp_squre_sel.css("background-image","url(/www/images/edit.png)");
		}
		all_inp_squre_sel.show();
	},
	/**
	 * 如果没有任何操作的情况下, 跳转到
	 * @param url
	 */
	goodsDivClick : function (url, aimObj){
		if(this.operateFlag == "delete" || this.operateFlag == "recieveMoney" || this.operateFlag == "upToShelf" || this.operateFlag == "downFromShelf") {
			var inp_squre_sel = $(aimObj).find(".inp_squre_sel");
			//alert(inp_squre_sel.attr("class"));
			if(inp_squre_sel.attr("class").indexOf(this.checkedClassFlag) > 0) {
				// 去除
				//如果操作是收款
				if(this.operateFlag == "recieveMoney") {
					var toAddPrice = $(aimObj).find(".goodsPrice").text();
					this.minusMoney(toAddPrice);
					this.setValueTo(this.totalMoneyShowIn);
				}
				inp_squre_sel.removeClass(this.checkedClassFlag);
				inp_squre_sel.css("background-image", "url(/www/images/selected-grey.png)");
			} else {
				// 添加
				//如果操作是收款
				if(this.operateFlag == "recieveMoney") {
					var toAddPrice = $(aimObj).find(".goodsPrice").text();
					this.addMoney(toAddPrice);
					this.setValueTo(this.totalMoneyShowIn);
				}
				inp_squre_sel.addClass(this.checkedClassFlag);
				inp_squre_sel.css("background-image", "url(/www/images/selected.png)");
			}
			return ;
		} else if(!!! this.operateFlag){
			this.showHide('menuDiv');
		} else if(this.operateFlag == "edit"){
			window.location.href = url;
		}
	},
	addMoney : function(money){
		// money 是字符串， 要转换成数字。为计算精确， 先转换成分计算
		var f = this.yuanZhuanFen(money);
		var t = this.yuanZhuanFen(this.totalMoney);
		var temp = this.fenZhuanYuan(f+t);
		this.totalMoney = temp;
		return temp;
	},
	minusMoney : function(money){
		// money 是字符串， 要转换成数字。为计算精确， 先转换成分计算
		var f = this.yuanZhuanFen(money);
		var t = this.yuanZhuanFen(this.totalMoney);
		var temp = this.fenZhuanYuan(t-f);
		this.totalMoney = temp;
		return temp;
	},
	setValueTo : function(aimId){
		document.getElementById(aimId).value = this.totalMoney;
	},
	/**
	 * 
	 * @param fen
	 * @returns {String}
	 */
	fenZhuanYuan:function(fen){
		var balance = "";
		if(typeof fen == 'number'){
			var yuan = ""+fen/100;
			var d = yuan.indexOf('.', 0);
			if((d == -1)){
				balance = yuan+".00";
			}else if(yuan.substring(d+1).length == 1){
				balance = yuan+"0";
			}else{
				balance = yuan;
			}
		}
		return balance;
	},
	/**
	 * 
	 * @param money
	 * @returns int
	 */
	yuanZhuanFen:function(money){
		var bal = money + "";
		var d = bal.indexOf('.', 0);
		if(d > 0){
			var bef = bal.substring(0,d);
			var aft = bal.substring(d+1,bal.length);
			if(aft.length <2){
				aft = aft + '0';
			}else if(aft.length > 2){
				aft = aft.substring(0,2);
			}else{
				
			}
			bal = bef+aft;
		}else{
			bal = bal+'00';
		}
		return parseInt(bal,10);
	},
	showHide: function (aimId){
		var aimObj = $("#"+ aimId);
		//alert(aimObj.css("display"));
		if(aimObj.css("display") != "none") {
			$("#"+ aimId).hide();
		} else {
			$("#"+ aimId).show();
		}
	},
	checkAll: function (aim){
		$(aim).click();
	},
	back: function (){
		cancelBubble();
		window.history.back();
	}
};

