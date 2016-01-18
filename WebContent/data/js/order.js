if(checklogin(baseurl+'order.html?good_id='+paramMap.get("good_id")+"&good_number="+paramMap.get("good_number"))){
	$("#modifyFetchInfo").on("click", function() {$("#username").focus();});
	var reg = /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	function transitionbyux(base_page,the_tp_page){
		base_page.css('-webkit-transform', 'translateX(0)');
		the_tp_page.css('-webkit-transform', 'translateX(100%)');
	};
	function modifyPrice(){
		var price = parseFloat($('#product_price').html().replace("￥",""))*($('#product_number').html()*1) + parseFloat($('#postage').html().replace("￥","")) + parseFloat($('#cashcouponprice').html().replace("￥",""));
		if(price<0){price=0;}
		$('#last_price').html("￥"+((price+"").indexOf(".")<0 ? price+".00" : price));
	};
	$(function(){
		var base_page = $('#base_page').show(),the_tp_page1 = $('#the_tp_page1').show(),the_tp_page2 = $('#the_tp_page2').show(),the_tp_page3 = $('#the_tp_page3').show(),the_tp_page4 = $('#the_tp_page4').show(),the_tp_page5 = $('#the_tp_page5').show();  
		base_page.css('-webkit-transform', 'translateX(0)');
		the_tp_page1.css('-webkit-transform', 'translateX(100%)');
		the_tp_page2.css('-webkit-transform', 'translateX(100%)');
		the_tp_page3.css('-webkit-transform', 'translateX(100%)');
		the_tp_page4.css('-webkit-transform', 'translateX(100%)');
		the_tp_page5.css('-webkit-transform', 'translateX(100%)');
		
		$('.the_tp_page').css('-webkit-transition', 'all .3s ease-in-out');
        $('.the_tp_page').css('overflow-x', 'hidden');
		function transitionbyx(e,base_page,the_tp_page){
			e.preventDefault();
			base_page.css('-webkit-transform', 'translateX(-100%)');
			window.scrollTo(0, 0);
			the_tp_page.css('-webkit-transform', 'translateX(0)');
		};
		$('#cash_coupon').click(function(e){transitionbyx(e,base_page,the_tp_page1);});
		$('#user_address').click(function(e){transitionbyx(e,base_page,the_tp_page2);});
		$('#other_demand').click(function(e){transitionbyx(e,base_page,the_tp_page3);});
		$('#other_demand1').click(function(e){transitionbyx(e,base_page,the_tp_page3);});
		$('.user_address_add_modify').click(function(e){transitionbyx(e,the_tp_page2,the_tp_page4);});
		$('#brandshop').click(function(e){transitionbyx(e,base_page,the_tp_page5);});
		$('.btn_back1').click(function(){transitionbyux(base_page,the_tp_page1);});
		$('#btn_back2').click(function(){transitionbyux(base_page,the_tp_page2);});
		$('#btn_back3').click(function(){transitionbyux(base_page,the_tp_page3);});
		$('#btn_back4').click(function(){transitionbyux(the_tp_page2,the_tp_page4);});
		$('#btn_back5').click(function(){transitionbyux(base_page,the_tp_page5);});
	});
	function changeDeliverType(deliver_type){
		$('.deliver_type').removeClass("selected");
		if(deliver_type=='2' || deliver_type=='3'){
			$('#delivertype').val("2");
			$('.deliver_type_div2').css("display","none");
			$('.deliver_type_div1').css("display","block");
			$('#deliver_type1').addClass("selected");
		}else if(deliver_type=='1'){
			$('#delivertype').val("1");
			$('.deliver_type_div1').css("display","none");
			$('.deliver_type_div2').css("display","block");
			$('#deliver_type2').addClass("selected");
		}
	};
	function sendShopareaselect(shoparea){
		$('#addressstorearealist').css('display','block');
		$('#shopaddressselectshowlist').css('display','block');
		$('#shoparea').val(shoparea);
		$('#shopareaselectshowvalue').html(shoparea);
        $('#shopareaselectshowlist').css("display","none");
		var s = document.getElementById('shoparea'),evt = document.createEvent('HTMLEvents');  
		evt.initEvent('change',true,true);  
		s.dispatchEvent(evt); 
	};
	function sendShopaddressselect(id,shopnamev,shopaddressv){
		$('#shopaddress').val(id);
		$('#shopaddressselectshowvalue').html(shopnamev+" "+shopaddressv);
        $('#shopaddressselectshowlist').css('display','none');
		$('#shopid').val(id);
		$('#shopnamev').html(shopnamev);
		$('#shopaddressv').html(shopaddressv);
		var s = document.getElementById('shopaddress'),evt = document.createEvent('HTMLEvents');  
		evt.initEvent('change',true,true);  
		s.dispatchEvent(evt); 
	};
	var wiuseraddressupdateid="";
	function goModifyAddress(id,contact,mobile,address){
        var event = arguments.callee.caller.arguments[0];
		if (event.stopPropagation){event.stopPropagation();}else if (window.event){window.event.cancelBubble=true;}
		wiuseraddressupdateid=id;
		if(id == null || id == ''){
			$('#contact').val("");
			$('#address').val("");
			$('#arealist').val("");
			$('#arealistselectshowvalue').html("请选择收货地所在区");
			$('#addressmobile').val("");
			$('#addresstitle').html("新建地址");
			$('#btndiv').html("<div class='submit-btn' onclick=\"wiuseraddressadd()\">保存地址</div>");
		}else{
			$('#contact').val(contact);
			$('#address').val(((address.split("-")[1] == null) ? address : address.split("-")[1]));
			$('#arealist').val(address!=null ? address.split("-")[0] : "");
			$('#arealistselectshowvalue').html(address!=null ? address.split("-")[0] : "");
			$('#addressmobile').val(mobile);
			$('#addresstitle').html("编辑地址");
			$('#btndiv').html("<div class='submit-btn-left' onclick=\"wiuseraddressdel()\">删除地址</div>"+"<div class='submit-btn-right' onclick=\"wiuseraddressupdate()\">保存地址</div>");
		}
	};
	function sendaddressinfo(id,contact,mobile,address){
		$('#addressid').val(id);
		$('#contactv').val(contact);
		$('#addressv').val(address);
		$('#mobilev').val(mobile);
		$('#addressform').css('display','block');
		transitionbyux($('#base_page'),$('#the_tp_page2'));
	};
    var defaultSelect="<img src='../data/images/selected.png' alt='默认选中' style='width:16px;height:16px;'>",unSelect="<img src='../data/images/unselected.png' alt='默认选中' style='width:16px;height:16px;'>";
	function modifyState(obj,id){
		var event = arguments.callee.caller.arguments[0];
		if (event.stopPropagation){event.stopPropagation();}else if(window.event){window.event.cancelBubble = true;}
		var mstate = ($(obj).attr("statevalue")=='1'? "2" : "1");
		var updateparpam = baseparams+"&id="+id+"&state="+mstate;
		serviceAjax(updateparpam,TP.initConfig.getUrl('wi_user_address_update'),function(data){
			if(mstate=='1'){
				$('.statevalues').attr("statevalue","2");
				$('.statevalues').html(unSelect);
			}
			$(obj).html($(obj).attr("statevalue")=='1' ? unSelect : defaultSelect);
			$(obj).attr("statevalue",mstate);
		});
	};
	function wiuseraddresslist(){
		serviceAjax(baseparams,TP.initConfig.getUrl('wi_user_address_list'),function(data){
			 var addresslist = data.data.address_list;
			 if(addresslist!=null&&addresslist!=''){
				var useraddresshtml = "";
				for(var i =0; i<addresslist.length;i++){
					useraddresshtml+="<table class=\"user-address-list\" onclick=\"sendaddressinfo('"+addresslist[i].id+"','"+addresslist[i].contact+"','"+addresslist[i].mobile+"','"+addresslist[i].address+"')\">";
					useraddresshtml+="<tr>";
					useraddresshtml+="<td rowspan=\"2\" class=\"statevalues user-default\" statevalue=\""+addresslist[i].state+"\" onclick=\"modifyState(this,'"+addresslist[i].id+"')\">"+(addresslist[i].state=="1" ? defaultSelect : addresslist[i].state=="2" ? unSelect : "")+"</td>";
					useraddresshtml+="<td>"+addresslist[i].contact+"</td>";
					useraddresshtml+="<td rowspan=\"2\" class=\"user-edit user_address_add_modify\" onclick=\"goModifyAddress('"+addresslist[i].id+"','"+addresslist[i].contact+"','"+addresslist[i].mobile+"','"+addresslist[i].address+"')\"><span class='user-edit-pen'></span></td>";
					useraddresshtml+="</tr>";
					useraddresshtml+="<tr>";
					useraddresshtml+="<td>";
					useraddresshtml+="<p>"+addresslist[i].address+"</p>";
					useraddresshtml+="<p>"+addresslist[i].mobile+"</p>";
					useraddresshtml+="</td>";
					useraddresshtml+="</tr>";
					useraddresshtml+="</table>";
				}
				$('#useraddress').html(useraddresshtml);
				$('.user_address_add_modify').click(function(e){
					e.preventDefault();
					$('#the_tp_page2').css('-webkit-transform', 'translateX(-100%)');
					$('#the_tp_page4').css('-webkit-transform', 'translateX(0)');
				});
			 }else{
				$('#useraddress').html("");
			 }
		});
	};
	function sendArealistselect(areasel){
		$('#arealist').val(areasel);
        $('#arealistselectshowvalue').html(areasel);
        $('#arealistselectshowlist').css('display', 'none');
	};
	function wibasearealist(){
		serviceAjax(baseparams,TP.initConfig.getUrl('wi_base_arealist'),function(data){
			var areasel = data.data.area_sel;
			if(areasel!=null&&areasel!=''){
				var arealisthtml = "<option value=\"\">请选择收货地所在区</option>";
				var arealistselectshowlisthtml="<li onclick=\"sendArealistselect('');\">请选择收货地所在区</li>";
				for(var i =0 ;i<areasel.length;i++){
					arealisthtml+="<option value=\""+areasel[i]+"\">"+areasel[i]+"</option>";
					arealistselectshowlisthtml+="<li onclick=\"sendArealistselect('"+areasel[i]+"');\">";
					arealistselectshowlisthtml+=areasel[i];
					arealistselectshowlisthtml+="</li>";
				}
				$('#arealistselectshowlist').html(arealistselectshowlisthtml);	
				$('#arealistselectshowvalue').bind('click',function(){
					if($('#arealistselectshowlist').css('display')=='none'){
						$('#arealistselectshowlist').css('display','block');
					}else{
						$('#arealistselectshowlist').css('display','none');
					}
				});
				$('#arealist').html(arealisthtml);
			}
		});
	}
	function sendcashcouponprice(price,ticket_id){
		var couponparams = baseparams+"&coupon_id="+ticket_id+"&"+
						"good_id="+paramMap.get("good_id")+"&"+
						"good_number="+paramMap.get("good_number");
		serviceAjax(couponparams,TP.initConfig.getUrl('wi_order_coupon_check'),function(data){
			$('#cashcouponprice').html("￥-"+price);
			$('#ticket_id').val(ticket_id);
			modifyPrice();
			transitionbyux($('#base_page'),$('#the_tp_page1'));
		});
	};
	function sendCashcouponhtml(cashcoupon){
		var statemap = new Map();
		statemap.put("1","未发放");
		statemap.put("2","");
		statemap.put("3","已使用");
		statemap.put("4","已过期");
		statemap.put("5","已取消");
		var cashcouponhtml="";
		for(var i=0 ;i <cashcoupon.length;i++){
			cashcouponhtml+="<table class=\"user-address-list\" onclick=\"sendcashcouponprice('"+cashcoupon[i].price+"','"+cashcoupon[i].id+"')\">";
			cashcouponhtml+="<tr>";
			cashcouponhtml+="<td colspan=\"2\">"+cashcoupon[i].price+"元代金券   "+statemap.get(cashcoupon[i].state)+"</td>";
			cashcouponhtml+="</tr>";
			if(cashcoupon[i].coupon_name!=null&&cashcoupon[i].coupon_name!=''){
				cashcouponhtml+="<tr>";
				cashcouponhtml+="<td colspan=\"2\">";
				cashcouponhtml+=cashcoupon[i].coupon_name;
				cashcouponhtml+="</td>";
				cashcouponhtml+="</tr>";
			}
			cashcouponhtml+="<tr>";
			cashcouponhtml+="<td>";
			cashcouponhtml+=cashcoupon[i].serial_number;
			cashcouponhtml+="</td>";
			cashcouponhtml+="<td>";
			cashcouponhtml+="有效期至:"+cashcoupon[i].end_time.substring(0,10);
			cashcouponhtml+="</td>";
			cashcouponhtml+="</tr>";
			cashcouponhtml+="</table>";
		}
		$('#cashcoupon').html(cashcouponhtml+$('#cashcoupon').html());	
	};
	function wiusercoupon(){
		serviceAjax(baseparams,TP.initConfig.getUrl('wi_user_coupon'),function(data){
			var cashcoupon = data.data.user_coupon;
			if(cashcoupon!=null&&cashcoupon!=''){
				sendCashcouponhtml(cashcoupon);
			}
		});
	}
	var orderparams = baseparams+"&good_id="+paramMap.get("good_id")+"&good_number"+paramMap.get("good_number");
	serviceAjax(orderparams,TP.initConfig.getUrl('wi_order_create_pre'),function(data){
		var deliver_type = data.data.deliver_type;
		changeDeliverType(deliver_type);
		if(deliver_type=="3"){
			$('#deliver_type1').bind('click',function(){changeDeliverType("2");});
			$('#deliver_type2').bind('click',function(){changeDeliverType("1");});
		}
		$('#username').val(data.data.username);
		$('#mobile').val(data.data.mobile);
		var addressv = data.data.address;
		if(addressv!=null&&addressv!=''){
			$('#addressid').val(addressv.id);
			$('#contactv').val(addressv.contact);
			$('#addressv').val(addressv.address);
			$('#mobilev').val(addressv.mobile);
		}else{
			$('#addressform').css("display","none");
		}
		var taketime = data.data.taketime;
		if(taketime!=null&&taketime!=''){
			var taketimehtml = "";
			for(var i = 0 ; i<taketime.length; i++){
				taketimehtml+="<span class=\"order-time "+(i==0?"order-time-radius":"")+"\">";
				taketimehtml+=taketime[i];
				taketimehtml+="</span>";
			}
			$('#taketime').html(taketimehtml);
		}

		var sendtime = data.data.sendtime;
		if(sendtime!=null&&sendtime!=''){
			var sendtimehtml = "";
			for(var i = 0 ; i<sendtime.length; i++){
				if(i==0){
					$('#ordertimev').val(sendtime[i]);
				}
				sendtimehtml+="<span class=\"order-time "+(i==0?"order-time-radius":"")+"\">";
				sendtimehtml+=sendtime[i];
				sendtimehtml+="</span>";
			}
			$('#sendtime').html(sendtimehtml);
			$('.order-time').bind('click',function(){
				$('.order-time').removeClass("order-time-radius");
				$(this).addClass("order-time-radius");
				$('#ordertimev').val($(this).html());
			});
		}
		var startdate = new Date(data.data.send_date_start),enddate = new Date(data.data.send_date_end),diff =(enddate.getTime() - startdate.getTime())/(24 * 60 * 60 * 1000),week =["日","一","二","三","四","五","六"],thedayhtml = "";
		for(var i=0;i<diff;i++){
			startdate.setDate(startdate.getDate()+1);
			if(i==0){
				$('#orderdatev').val(startdate.getFullYear()+"-"+(startdate.getMonth()+1)+"-"+startdate.getDate());
			}
			thedayhtml+="<span  dvalue=\""+startdate.getFullYear()+"-"+(startdate.getMonth()+1)+"-"+startdate.getDate()+"\" class=\"order-date "+(i==0?"order-date-radius":"")+"\">";
			thedayhtml+=""+startdate.getDate()+"日<br>周"+week[startdate.getDay()]+"";
			thedayhtml+="</span>";
				
		}
		$("#contentdate").html(thedayhtml);
		$('.order-date').bind('click',function(){
			$('.order-date').removeClass("order-date-radius");
			$(this).addClass("order-date-radius");
			$('#orderdatev').val($(this).attr("dvalue"));
		});
		$('#deliver_range').html(data.data.deliver_range);
		var product = data.data.product;
		if(product!=null&&product!=''){
			$('#product_name').html(product.product_name);
			$('#good_name').html(product.good_name);
			$('#product_number').html(paramMap.get("good_number"));
			$('#product_price').html("￥"+product.sales_price);
			$('#order_product_price').html("￥"+((product.sales_price*1)*(paramMap.get("good_number")*1)));
		}
		$('#postage').html("￥"+data.data.postage);
		$('#shopform').css("display","none");
		var shops = data.data.shop;
		if(shops!=null&&shops!=''){
			var shopjson = null;
			for(var i=0;i<shops.length;i++){
				var shopstr = "{\""+shops[i].area+"\":[{\"id\":\""+shops[i].id+"\",\"area\":\""+shops[i].area+"\",\"name\":\""+shops[i].name+"\",\"address\":\""+shops[i].address+"\"}]}";
				if(shopjson==null){
					shopjson=eval('(' + shopstr + ')');
				}else{
					if(shopjson[shops[i].area]==null){
						shopjson[shops[i].area]=eval('(' + shopstr + ')')[shops[i].area];
					}else{
						shopjson[shops[i].area][shopjson[shops[i].area].length]=eval('(' + shopstr + ')')[shops[i].area][0];
					}
				}
			}
			if(shopjson!=null){
				var shopareahtml = "<option value=\"\">请选择区</option>",shopareaselectshowlisthtml="<li onclick=\"sendArealistselect('');\">请选择区</li>";
				for(var key in shopjson){
					shopareahtml+="<option value=\""+key+"\">"+key+"</option>";
					shopareaselectshowlisthtml+="<li onclick=\"sendShopareaselect('"+key+"');\">";
					shopareaselectshowlisthtml+=key;
					shopareaselectshowlisthtml+="</li>";
				}
				$('#shopareaselectshowlist').html(shopareaselectshowlisthtml);	
				$('#shopareaselectshowvalue').bind('click',function(){
					if($('#shopareaselectshowlist').css('display')=='none'){
						$('#shopareaselectshowlist').css('display','block');
                        $('#shopaddressselectshowlist').css('display', 'none');
                        $('#shopareaselectshowvalue').removeClass('downarrow');
                        $('#shopareaselectshowvalue').addClass('uparrow');
					}else{
						$('#shopareaselectshowlist').css('display','none');
						   $('#shopareaselectshowvalue').removeClass('uparrow');
                        $('#shopareaselectshowvalue').addClass('downarrow');
					}
				});
				$('#shoparea').html(shopareahtml);
			}
			
			$('#shoparea').bind("change",function(){
				if($(this).val()==''){
					$('#shopaddress').html("");
					$('#shopaddress').unbind();
					$('#shopareaselectshowvalue').html("");
					$('#shopareaselectshowlist').html("");
					return;
				}
				var listaddress = shopjson[$(this).val()];
				var shopaddresshtml = "";
				var shopaddressselectshowlisthtml="";
				for(var i=0;i< listaddress.length ;i++){
					if(i==0){
						$('#shopid').val(listaddress[i].id);
						$('#shopnamev').html(listaddress[i].name);
						$('#shopaddressv').html(listaddress[i].address);
						$('#shopform').css("display","block");
						$('#shopaddressselectshowvalue').html(listaddress[i].name+" "+listaddress[i].address)
					}
					shopaddresshtml+="<option shopnamev=\""+listaddress[i].name+"\" shopaddressv=\""+listaddress[i].address+"\" value=\""+listaddress[i].id+"\">"+listaddress[i].name+"   "+listaddress[i].address+"</option>";
					
					shopaddressselectshowlisthtml+="<li onclick=\"sendShopaddressselect('"+listaddress[i].id+"','"+listaddress[i].name+"','   "+listaddress[i].address+"');\">";
					shopaddressselectshowlisthtml+=listaddress[i].name+" "+listaddress[i].address;
					shopaddressselectshowlisthtml+="</li>";
				}
				$('#shopaddressselectshowlist').html(shopaddressselectshowlisthtml);	
                $('#shopaddressselectshowvalue').unbind();
				$('#shopaddressselectshowvalue').bind('click',function(){
					if($('#shopaddressselectshowlist').css('display')=='none'){
						$('#shopaddressselectshowlist').css('display','block');
                         $('#shopareaselectshowlist').css('display','none');
					}else{
						$('#shopaddressselectshowlist').css('display','none');
					}
				});
				$('#shopaddress').html(shopaddresshtml);
				$('#shopaddress').unbind();
				$('#shopaddress').bind("change",function(){
					$('#shopform').css("display","block");
					transitionbyux($('#base_page'),$('#the_tp_page5'));
				});
			});
		}
		setTimeout(function(){
			new iScroll('base_page');
			new iScroll('the_tp_page1');
			new iScroll('the_tp_page2');
			new iScroll('the_tp_page3');
			new iScroll('the_tp_page4');
			new iScroll('the_tp_page5');
			$(window).trigger('resize');
		}, 200);
		modifyPrice();
		wibasearealist();
		wiuseraddresslist();
		wiusercoupon();
	});
	function wiusercouponadd(){
		var serial_number= $('#serial_number').val();
			if(serial_number!=null&&serial_number!=''){
			var couponparams = baseparams+"&serial_number="+serial_number;
			serviceAjax(couponparams,TP.initConfig.getUrl('wi_user_coupon_add'),function(data){
				var cashcoupon = data.data.coupon;
				if(cashcoupon!=null&&cashcoupon!=''){
					var cashcoupons  = [cashcoupon];
					sendCashcouponhtml(cashcoupons);
				}
			});
		}
		return false;
	};
	function checkAddress(){
		var contact=$('#contact').val(),address=$('#address').val(),mobile=$('#addressmobile').val(),arealist = $('#arealist').val();
		if(arealist==''){
			toastr['info']("请选择收货地所在区!");
			return false;
		}
		if(contact==''){
			toastr['info']("请输入收件人！");
			return false;
		}
		if(address==''){
			toastr['info']("请输入地址！");
			return false;
		}
        if(!reg.test(mobile)) {
			toastr['info']("请输入正确的手机号码！");
			return false;
		}
		
		return true;
	};
	function wiuseraddresscallback(){
		transitionbyux($('#the_tp_page2'),$('#the_tp_page4'));
		wiuseraddresslist();
	};
	function wiuseraddressupdate(){
		if(checkAddress()){
			var updateparpam = baseparams+"&id="+wiuseraddressupdateid+"&"+
										 "contact="+$('#contact').val()+"&"+
										 "address="+$('#arealist').val()+"-"+$('#address').val()+"&"+
										 "mobile="+$('#addressmobile').val();
			serviceAjax(updateparpam,TP.initConfig.getUrl('wi_user_address_update'),function(data){
				 wiuseraddresscallback();
			});
		}
	};
	function wiuseraddressdel(){
		var delparpam = baseparams+"&id="+wiuseraddressupdateid;
		serviceAjax(delparpam,TP.initConfig.getUrl('wi_user_address_del'),function(data){
			 wiuseraddresscallback();
		});
	};
	function wiuseraddressadd(){
		if(checkAddress()){
			var addparpam = baseparams+"&id="+wiuseraddressupdateid+"&"+
										 "contact="+$('#contact').val()+"&"+
										 "address="+$('#arealist').val()+"-"+$('#address').val()+"&"+
										 "mobile="+$('#addressmobile').val()+"&"+
										 "state=2";
			serviceAjax(addparpam,TP.initConfig.getUrl('wi_user_address_add'),function(data){
				 wiuseraddresscallback();
			});
		}
	};
	$('#remarkbtn').bind('click',function(){
		var remark=$('#remark').val();
		if(remark==''){
			 $('#remarkmsg').html("(请输入蜡烛数字，餐盘数量等)");
			 return;
		}
		if(remark.length>150){
			 toastr['info']("提交内容不许超过150字!");
			 return;
		}
		$('#remarkmsg').html($('#remark').val());
		transitionbyux($('#base_page'),$('#the_tp_page3'));
	});
	$('.order-date').bind('click',function(){
	});
	$('.paytype').bind('click',function(){
		$('.paytype').removeClass("pay-unselected");
		$('.paytype').removeClass("pay-selected");
		$('.paytype').addClass("pay-unselected");
		$(this).removeClass("pay-unselected");
		$(this).addClass("pay-selected");
		$('#paytype').val($(this).attr("paytypevalue"));
	});
	var ispay = true;
	function wiordercreate(){
		var delivertype=$('#delivertype').val(),username=$('#username').val(),contactv=$('#contactv').val(),mobile=$('#mobile').val(),mobilev=$('#mobilev').val(),addressv=$('#addressv').val();
		if(delivertype=='2'){
			if(username==""){
				toastr['info']("请输入提货人的姓名！");
				return;
			}
			if(!reg.test(mobile)) {
				toastr['info']("请输入正确的手机号码！");
				return;
			}
			if($('#shopid').val()=='0'){
				toastr['info']("请选择提货门店！");
				return ;
			}
		}
		if(delivertype=='1'){
			if(contactv==''){
				toastr['info']("请输入收件人！");
				return ;
			}
			if(!reg.test(mobilev)) {
				toastr['info']("请输入正确的手机号码！");
				return;
			}
			if(addressv==''){
				toastr['info']("请输入地址！");
				return ;
			}
		}
		if(ispay){
			ispay=false;
			var ordercreateparpam = baseparams+"&remark="+$('#remark').val()+"&"+
											 "deliver_type="+$('#delivertype').val()+"&"+
											 "receiver_name="+(delivertype=='2' ? username :contactv)+"&"+
											 "receiver_mobile="+(delivertype=='2' ? mobile : mobilev)+"&"+
											 "receiver_address="+(delivertype=='2'? "" : addressv)+"&"+
											 "shop_id="+$('#shopid').val()+"&"+
											 "send_date="+$('#orderdatev').val()+" "+$('#ordertimev').val()+"&"+
											 "postage="+$('#postage').html().replace("￥","")+"&"+
											 "good_id="+paramMap.get("good_id")+"&"+
											 "good_number="+paramMap.get("good_number")+"&"+
											 "ticket_id="+$('#ticket_id').val()+"&"+
											 "pay_type="+$('#paytype').val()+"&sweet_point=0";
			serviceAjax(ordercreateparpam,TP.initConfig.getUrl('wi_order_create'),function(data){
				var rdata = data.data;
				if($('#paytype').val()=="2"){
					if($('#last_price').html().replace("￥","")*1==0){
						TP.goUrl('pay_success.html?order_no='+rdata.order_sn+"&pay_result=1",'','-1');
					}else{
						$('#order_no').val(rdata.order_sn);
						$('#goods_name').val($('#product_name').html());
						$('#total_amount').val($('#last_price').html().replace("￥",""));
						$('#return_url').val(TP.initConfig.getUrl('base_url_i1')+"baidu/wapdirect/return_url.php");
						$('#page_url').val(TP.initConfig.getUrl('base_url_m1')+"/view/pay_success.html");
						document.bfPayOrderForm.action = TP.initConfig.getUrl('base_url_i1')+"baidu/wapdirect/pay_wap.php";
						document.bfPayOrderForm.submit();
					}
				}else if($('#paytype').val()=="1"){
					 if($('#last_price').html().replace("￥","")*1==0){
						TP.goUrl('alipay_success.html?out_trade_no='+rdata.order_sn+"&result=1",'','-1');
					}else{
						$('#WIDout_trade_no').val(rdata.order_sn);
						$('#WIDsubject').val($('#product_name').html());
						$('#WIDtotal_fee').val($('#last_price').html().replace("￥",""));
						$('#WIDpage_url').val(TP.initConfig.getUrl('base_url_m1')+"/view/alipay_success.html");
						document.aliPayOrderForm.action = TP.initConfig.getUrl('base_url_i1')+"alipay/alipayapi.php";
						document.aliPayOrderForm.submit();
					}
				}
			});
		}
	};
	$('#submitbtnorder').bind('click',function(){wiordercreate()});
}