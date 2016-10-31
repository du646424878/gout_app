function getUrlParam(name) {
    //构造一个含有目标参数的正则表达式对象  
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    //匹配目标参数  
    var r = window.location.search.substr(1).match(reg);
    //返回参数值  
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}

$(document).ready(function(){
		var staplefood={
			name:"每日主食",
			staplefood1:"米饭",
			staplefood2:"面",
			staplefood3:"粥",
			staplefood4:"面包",
			staplefood5:"方便食品",
			staplefood6:"其他"
		}
		var staplefoodamount={
			name:"主食量",
			staplefoodamount0:"0g",
			staplefoodamount1:"1-250g",
			staplefoodamount2:"251-500g",
			staplefoodamount3:"500g以上"
		}
		var taste={
			name:"口味习惯",
			taste1:"不辣",
			taste2:"微辣",
			taste3:"中辣",
			taste4:"极辣"
		}
		var dietarypreference={
			name:"饮食偏好",
			dietarypreference1:"海鲜",
			dietarypreference2:"红肉",
			dietarypreference3:"豆类",
			dietarypreference4:"甜食",
			dietarypreference5:"蔬菜",
			dietarypreference6:"其他",
		}
		var drinktype={
			name:"饮酒种类",
			drinktype1:"啤酒",
			drinktype2:"白酒",
			drinktype3:"红酒",
			drinktype4:"不饮"
		}
		var seafoodpd={
			name:"每日海鲜量",
			seafoodpd0:"0",
			seafoodpd1:"1-250g",
			seafoodpd2:"251-500g",
			seafoodpd3:"500g以上"
		}
		var beefpd={
			name:"每日牛肉量",
			beefpd0:"0",
			beefpd1:"1-250g",
			beefpd2:"251-500g",
			beefpd3:"500g以上"
		}
		var fishpd={
			name:"每日鱼肉量",
			fishpd0:"0",
			fishpd1:"1-250g",
			fishpd2:"251-500g",
			fishpd3:"500g以上"
		}
		var porkpd={
			name:"每日猪肉量",
			porkpd0:"0",
			porkpd1:"1-250g",
			porkpd2:"251-500g",
			porkpd3:"500g以上"
		}
		var poultrypd={
			name:"每日家禽量",
			poultrypd0:"0",
			poultrypd1:"1-250g",
			poultrypd2:"251-500g",
			poultrypd3:"500g以上"
		}
		var visceralpd={
			name:"每日内脏量",
			visceralpd0:"0",
			visceralpd1:"1-250g",
			visceralpd2:"251-500g",
			visceralpd3:"500g以上"
		}
		var vegetablepd={
			name:"每日绿蔬量",
			vegetablepd0:"0",
			vegetablepd1:"1-250g",
			vegetablepd2:"251-500g",
			vegetablepd3:"500g以上"
		}
		var beanpd={
			name:"每日豆制品量",
			beanpd0:"0",
			beanpd1:"1-250g",
			beanpd2:"251-500g",
			beanpd3:"500g以上"
		}
		var eggpd={
			name:"每日鸡蛋量",
			eggpd1:"0个",
			eggpd2:"1个",
			eggpd3:"2个",
			eggpd4:"3个",
			eggpd5:"4个",
			eggpd6:"5个及以上",
		}
		var nutpd={
			name:"每日坚果量",
			nutpd0:"0",
			nutpd1:"1-100g",
			nutpd2:"101-200g",
			nutpd3:"201-300g",
			nutpd4:"301-400g",
			nutpd5:"401-500g",
			nutpd6:"500g以上",
		}
		var fruitpd={
			name:"每日水果量",
			fruitpd1:"0个",
			fruitpd2:"1个",
			fruitpd3:"2个",
			fruitpd4:"3个",
			fruitpd5:"4个",
			fruitpd6:"5个及以上",
		}
		var saltpd={
			name:"每日食盐量",
			saltpd0:"0",
			saltpd1:"0-6g",
			saltpd2:"6-12g",
			saltpd3:"12-18g",
			saltpd4:"18g及以上",
		}
		var beerpd={
			name:"每日啤酒量",
			beerpd0:"0",
			beerpd1:"1-500ml",
			beerpd2:"501-1000ml",
			beerpd3:"1000ml以上",
		}
		var milkpd={
			name:"每日牛奶量",
			milkpd0:"0",
			milkpd1:"0-250ml",
			milkpd2:"250-500ml",
			milkpd3:"500ml以上",
		}
		var liquorpd={
			name:"每日白酒量",
			liquorpd1:"0",
			liquorpd2:"1-50g",
			liquorpd3:"51-100g",
			liquorpd4:"101-150g",
			liquorpd5:"151-200g",
			liquorpd6:"201-250g",
			liquorpd7:"250g以上",
		}
		var wirepd={
			name:"每日红酒量",
			wirepd1:"0",
			wirepd2:"1-50g",
			wirepd3:"51-100g",
			wirepd4:"101-150g",
			wirepd5:"151-200g",
			wirepd6:"201-250g",
			wirepd7:"250g以上",
		}
		var teatype={
			name:"每日饮茶种类",
			teatype1:"无",
			teatype2:"花茶",
			teatype3:"绿茶",
			teatype4:"红茶",
			teatype5:"乌龙茶",
			teatype6:"普洱茶",
			teatype7:"吗啡",
			teatype8:"含糖饮料",
			teatype9:"苏打",
		}
		var teapd={
			name:"每日饮茶量",
			teapd1:"0",
			teapd2:"1杯",
			teapd3:"2杯",
			teapd4:"3杯",
			teapd5:"4杯",
			teapd6:"5杯及以上",
		}
	$.get("/admin/db/weekhabit/select/27", function(data, status) {
        for (key in data) {
        	if(key!="habitid"&key!="createtime"&key!="modifytime"&key!="userid"){
        		var content='<div class="weui_cells_title">'+eval(key+".name")+'</div><div id="'+key+'"class="weui_cells weui_cells_radio"></div>'
        		$("#table").append(content);
        	}
        }
        for(key in data) {
        	if(key!="habitid"&key!="createtime"&key!="modifytime"&key!="userid"){
        		var content1='';
        		for(text in eval(key)){
        			
        			if(text!="name"){
        				content1=content1+'<label class="weui_cell weui_check_label" for="'+text+'">\
					              <div class="weui_cell_bd weui_cell_primary">\
					                  <p>'+eval(key+'.'+text)+'</p>\
					              </div>\
					              <div class="weui_cell_ft">\
					                  <input type="radio" class="weui_check" name="'+key+'" id="'+text+'" value="'+text.charAt(text.length-1,1)+'">\
					                  <span class="weui_icon_checked"></span>\
					              </div>\
					          </label>'
        			}
        		}
        		$("#"+key).append(content1);
        		content1='';
        	}
        }
        $("#button").click(function() {
	        for(key in data){
	        	if(key!="habitid"&key!="createtime"&key!="modifytime"&key!="userid"){
		        	var checkArry = document.getElementsByName(key);
		        	var judge=false;
		            for (var i = 0; i < checkArry.length; i++) { 
		                if(checkArry[i].checked == true){
		                    judge=true;
		                }
		            }
		            if(judge==false){
		            	alert("您有未选择的选项");
		            	var t = $('#' + key+ '1').offset().top;
		                $(window).scrollTop(t);
		            }
		        }
	        }
	    })
    });
})