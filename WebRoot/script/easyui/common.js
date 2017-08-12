/**
 * 页面加载优先运行_init()方法
 */

var fileLoadServerUrl = "http://192.168.1.34/";

$(function() {
	try {
		_init();
	} catch (e) {
	}
});

/**
 * 获取cookie
 * 
 * @return
 */
function getCookie(key) {
	return $.cookie(key);
}

/**
 * 设置Cookie
 * 
 * @return
 */
function setCookie(key,value) {
	$.cookie(key,value);
}

/**
 * 字符串为空
 * 
 * @param str
 * @return
 */
function isEmpty(str) {
	if (str == null || str == "" || (typeof str == undefined)) {
		return true;
	}
	return false;
}

/**
 * 字符串非空
 * 
 * @param str
 * @return
 */
function isNotEmpty(str) {
	return !isEmpty(str);
}

/**
 * 发起异步ajax
 * 
 * @param url
 * @param data
 * @param fn
 * @return
 */
function jax(url, data, fn) {
	$.ajax( {
		url : url,
		type : "post",
		data : data,
		success : fn
	});
}

function jax(url, data, fn,err) {
	$.ajax( {
		url : url,
		type : "post",
		data : data,
		success : fn,
		error:err
	});
}

/**
 * 发起同步ajax
 * 
 * @param url
 * @param data
 * @return
 */
function ajax(url, data,fn) {
	var ret = null;
	$.ajax( {
		url : url,
		type : "post",
		data : data,
		async : false,
		success : fn
	});
	return ret;
}

function test() {
	var settime = null;
	function redraw(){
		$('#wrap').layout('resize');
		var ttHeight = $('#tt').height();
		var ttWidth = $('#tt').width();
		var tabHeight = ttHeight - 31;
		var tabWidth = ttWidth - 2;
		var tabIfDivHeight = tabHeight - 20;
		if(tabHeight < 0){
			tabHeight = 0;
		}
		if(tabWidth < 0){
			tabWidth = 0;
		}
		if(tabIfDivHeight < 0){
			tabIfDivHeight = 0;
		}
		$('.tabs-panels').css("height",tabHeight);
		$('.tabs-panels').css("width",tabWidth);
		$('.tabs-wrap').css("width",tabWidth);
		$('.tabs-panels>div').css("width",tabWidth);
		$('.tabs-panels>div>div').css("width",tabWidth);
		$('.tabs-panels>div>div').css("height",tabHeight);
		$('.tabs-panels>div>div>div').css("height",tabIfDivHeight);
	}

	$(function(){		
		
			if(settime != null)
				clearTimeout(settime);
			settime=setTimeout("redraw()",100);
		  			
		var p1 = $('body').layout('panel','west').panel({
			onCollapse:function(){
				if(settime != null)
					clearTimeout(settime);
				settime=setTimeout("redraw()",100);
			},
			onExpand:function(){
				if(settime != null)
					clearTimeout(settime);
				settime=setTimeout("redraw()",100);
			},
			onResize:function(width,height){
				if(settime != null)
					clearTimeout(settime);
				settime=setTimeout("redraw()",100);
			}
		});				
		var p2 = $('body').layout('panel','east').panel({
			onCollapse:function(){
				if(settime != null)
					clearTimeout(settime);
				settime=setTimeout("redraw()",100);
			},
			onExpand:function(){
				if(settime != null)
					clearTimeout(settime);
				settime=setTimeout("redraw()",100);
			},
			onResize:function(width,height){
				if(settime != null)
					clearTimeout(settime);
				settime=setTimeout("redraw()",100);
			}
		});	
	});

}

/**
 * 模块动态添加tabs
 * 
 * @param index
 * @param title
 */
/*
 * function addPanel(index,title){ if($('#tt').tabs('exists',title)){
 * $('#tt').tabs('select',title); }else{
 * $('#tt').tabs('add',{title:title,id:title,content: '<div style="padding:0px;
 * margin-top: 0px;height:97%" fit="true"><iframe id="if1"
 * style="border-style:none;" name="if1" width="100%" height="99%"
 * src="'+index+'"></iframe></div>',closable: true}); } }
 */

function addPanelhome(index,title){//home页增加tab
    if (!parent.$('#tt').tabs('exists', title)) {
        parent.$('#tt').tabs('add', {
            title: title,
            content: '<div style="padding:10px; margin-top: 0px;height:97%" fit="true"><iframe id="if1" style="border-style:none;" name="if1" width="99%" height="99%" src="'+index+'"  frameborder="no" border="no"></iframe></div>',
            closable: true
        });
    } else {
        parent.$('#tt').tabs('select', title);
        parent.$('#mm-tabupdate').click();
    }
	
}

function addPanel(index,title){//列表增加tab
	
    if (!$('#tt').tabs('exists', title)) {
        $('#tt').tabs('add', {
            title: title,
            content: '<div style="padding:10px; margin-top: 0px;height:97%" fit="true"><iframe id="if1" style="border-style:none;" name="if1" width="99%" height="99%" src="'+index+'"   frameborder="no" border="no"></iframe></div>',
            closable: true
        });
    } else {
        $('#tt').tabs('select', title);
        $('#mm-tabupdate').click();
    }
	
    tabClose();	
	tabCloseEven();
	$(window).resize();
}
function addPanelh(index,title,ist,icon){//列表增加首页
    if (!$('#tt').tabs('exists', title)) {
        $('#tt').tabs('add', {
            title: title,
            content: '<div style="padding:10px; margin-top: 0px;height:97%" fit="true"><iframe id="if1" style="border-style:none;" name="if1" width="99%" height="99%" src="'+index+'"   frameborder="no" border="no"></iframe></div>',
            closable: ist,
            iconCls:icon
        });
    } else {
        $('#tt').tabs('select', title);
        $('#mm-tabupdate').click();
    }
	
    tabClose();	
	tabCloseEven();
}

function tabClose() {
    /* 双击关闭TAB选项卡 */
    $(".tabs-inner").dblclick(function () {
        var subtitle = $(this).children(".tabs-closable").text();
        $('#tt').tabs('close', subtitle);
    })
    /* 为选项卡绑定右键 */
    $(".tabs-inner").bind('contextmenu', function (e) {
        $('#mm').menu('show', {
            left: e.pageX,
            top: e.pageY
        });

        var subtitle = $(this).children(".tabs-closable").text();

        $('#mm').data("currtab", subtitle);
        $('#tt').tabs('select', subtitle);
        return false;
    });
}
// 绑定右键菜单事件
function tabCloseEven() {
    // 刷新
   /* $('#mm-tabupdate').click(function () {
        var currTab = $('#tt').tabs('getSelected');
        var url = $(currTab.panel('options').content).attr('src');
        $('#tt').tabs('update', {
            tab: currTab,
            options: {
                content: createFrame(url)
            }
        });
    });*/
    // 关闭当前
    $('#mm-tabclose').click(function () {
        var currtab_title = $('#mm').data("currtab");
        $('#tt').tabs('close', currtab_title);
    });
    // 全部关闭
    $('#mm-tabcloseall').click(function () {
        $('.tabs-inner span').each(function (i, n) {
            var t = $(n).text();
            $('#tt').tabs('close', t);
        });
    });
    // 关闭除当前之外的TAB
    $('#mm-tabcloseother').click(function () {
        $('#mm-tabcloseright').click();
        $('#mm-tabcloseleft').click();
    });
    // 关闭当前右侧的TAB
    $('#mm-tabcloseright').click(function () {
        var nextall = $('.tabs-selected').nextAll();
        if (nextall.length == 0) {            
           // alert('后边没有啦~~');
            return false;
        }
        nextall.each(function (i, n) {
            var t = $('a:eq(0) span', $(n)).text();
            $('#tt').tabs('close', t);
        });
        return false;
    });
    // 关闭当前左侧的TAB
    $('#mm-tabcloseleft').click(function () {
        var prevall = $('.tabs-selected').prevAll();
        if (prevall.length == 0) {
            // alert('到头了，前边没有啦~~');
            return false;
        }
        prevall.each(function (i, n) {
            var t = $('a:eq(0) span', $(n)).text();
            $('#tt').tabs('close', t);
        });
        return false;
    });

    // 退出
    $("#mm-exit").click(function () {
        $('#mm').menu('hide');
    });
}

// 弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
    $.messager.alert(title, msgString, msgType);
}

// 菜单变色
  $(document).ready(function(){
    $("p").addClass("border");
    $("p").click(function(){
	   $("p").addClass("border");
       $(this).removeClass("border");
     });
  });
  
//截取地址样参数val为参数名		
	function QueryString(val) {
		var uri = window.location.search;
		var re = new RegExp("" + val + "=([^&?]*)", "ig");
		return ((uri.match(re)) ? (uri.match(re)[0].substr(val.length + 1)) : null);
	}

//前台主页获取数据
function findPIndexDatas(){

}

//获取url参数	
function getParameter(name) {  
    var url = document.location.href;  
    var start = url.indexOf("?")+1;  
    if (start==0) {  
        return "";  
    }  
    var value = "";  
    var queryString = url.substring(start);  
    var paraNames = queryString.split("&");  
    for (var i=0; i<paraNames.length; i++) {  
        if (name==getParameterName(paraNames[i])) {  
            value = getParameterValue(paraNames[i]);
        }  
    }  
    return value;  
}

//从字符串获取参数	
function getParameterForStr(str,name) {  
    if (str==0) {  
        return "";  
    }  
    var value = "";  
    var paraNames = str.split("&");  
    for (var i=0; i<paraNames.length; i++) {  
        if (name==getParameterName(paraNames[i])) {  
            value = getParameterValue(paraNames[i]);
        }  
    }  
    return value;
}

function getParameterName(str) {  
    var start = str.indexOf("=");  
    if (start==-1) {  
        return str;  
    }  
    return str.substring(0,start);  
}  
  
function getParameterValue(str) {  
    var start = str.indexOf("=");  
    if (start==-1) {  
        return "";  
    }  
    return str.substring(start+1);  
}

//去除左右空格
function trim(s){  
	return trimRight(trimLeft(s));  
}

//去掉左边的空白  
function trimLeft(s){  
	if(s == null) {
		return "";  
	}  
	var whitespace = new String(" \t\n\r");  
	var str = new String(s);  
	if (whitespace.indexOf(str.charAt(0)) != -1) {  
		var j=0, i = str.length;  
		while (j < i && whitespace.indexOf(str.charAt(j)) != -1){  
			j++;  
		}  
		str = str.substring(j, i);  
	}  
	return str;  
}  
//去掉右边的空白
function trimRight(s){  
	if(s == null) return "";  
	var whitespace = new String(" \t\n\r");  
	var str = new String(s);  
	if (whitespace.indexOf(str.charAt(str.length-1)) != -1){  
		var i = str.length - 1;  
		while (i >= 0 && whitespace.indexOf(str.charAt(i)) != -1){  
			i--;  
		}  
		str = str.substring(0, i+1);  
	}  
	return str;  
}
//时间格式化  调用方法：  new Date().format('yyyy-MM-dd');
Date.prototype.format =function(format)
{
var o = {
"M+" : this.getMonth()+1, //month
"d+" : this.getDate(), //day
"h+" : this.getHours(), //hour
"m+" : this.getMinutes(), //minute
"s+" : this.getSeconds(), //second
"q+" : Math.floor((this.getMonth()+3)/3), //quarter
"S" : this.getMilliseconds() //millisecond
}
if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
(this.getFullYear()+"").substr(4- RegExp.$1.length));
for(var k in o)if(new RegExp("("+ k +")").test(format))
format = format.replace(RegExp.$1,
RegExp.$1.length==1? o[k] :
("00"+ o[k]).substr((""+ o[k]).length));
return format;
}
//防止损失精度的相加
function accAdd(arg1,arg2){ 	
    var r1,r2,m; 
    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0} 
    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0} 
    m=Math.pow(10,Math.max(r1,r2));
    return (arg1*m+arg2*m)/m;
} 
//easyui 合并单元格
function mergeCellsByField(tableID, colList) {	
    var ColArray = colList.split(",");
    var tTable = $("#" + tableID);
    var TableRowCnts = tTable.datagrid("getRows").length;
    var tmpA;
    var tmpB;
    var PerTxt = "";
    var CurTxt = "";
    for (var j = ColArray.length - 1; j >= 0; j--) {
        PerTxt = "";
        tmpA = 1;
        tmpB = 0;
        for (var i = 1; i <= TableRowCnts; i++) {
            if (i == TableRowCnts) {
                CurTxt = "";
            }
            else {
              //  CurTxt = tTable.datagrid("getRows")[i][ColArray[j]];
                CurTxt = $("#label" + i).text();
            }
            if (PerTxt == CurTxt) {
                tmpA += 1;
            }
            else {
                tmpB += tmpA;
                tTable.datagrid("mergeCells", {
                    index: i - tmpA,
                    field: ColArray[j],//合并字段
                    rowspan: tmpA,
                    colspan: null
                });

                tmpA = 1;
            }
            PerTxt = CurTxt;
        }
    }
}
/*
 * 判断保存按钮 div的id必须为btn
 */
function init() {
	var progid = QueryString("progid");
	var sub = QueryString("sub");
	var etime = QueryString("etime");
	var flag = false;
	$.ajax({
		url : 'usersUnit_findUserByName',
		type : "post",
		data : {
			'ssoid' : $.cookie("ssoid")
		},
		async : false,
		success : function(json) {
			if (json != "[null]" && json != "[]" && json != "") {
				var jo = eval(json)[0];
				var role = jo.type;// 用户类型
				if (sub == "1" || role != "申报人") {
					$("#btn").css("display", "none");
					flag = true;
				}
				if (role == "申报人") {
					if (etime == "0" || etime == "" || etime == "null") {
						ajax('progBz_findEdi', {
							'progid' : progid
						}, function(json) {
							if (json.indexOf("[null]") > -1) {

							} else {
								var jo = eval(json)[0];
								var editisnot = jo.editisnot;
								if (editisnot === "1") {
									$('#btn').css('display', 'block');
								}
							}
						});
					} else {
						$('#btn').css('display', 'none');
						flag = true;
					}

				}
			}
		}
	});
	return flag;
}