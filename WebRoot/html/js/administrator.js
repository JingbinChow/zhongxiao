//进入页面默认执行
$(function() {
	$('#username').val("");
	$('#bankcard').val("");
	$('#bankTransaction').val("");
	chaxun();
});
//模糊查询
function chaxun() {
	var username=$('#username').val();
	var bankcard=$('#bankcard').val();
	var bankTransaction=$('#bankTransaction').val();
	var transactionType=$('#transactionType').val();
	var top=$('#top1').val();
	var low=$('#low').val();

	var datelow =$('#datelow').datebox('getValue'); //获取时间范围
	var datetop =$('#datetop').datebox('getValue'); //获取时间范围
	var rows = [];
	$.ajax({url : 'manager_queryRecordForAdmin',
		data : {
			'username':username, //用户名
			'transactionType':transactionType, //用户名
			'bankcard':bankcard,
			'top':top,
			'low':low,
			'datelow':datelow,
			'datetop':datetop
		},
		type : 'POST',
		async : false,
		success : function(ret) {
			var obj = eval(ret);

				for ( var i = 0; i <= obj.length - 1; i++) {
					var caozuo ;//type
					var type ;//交易状态
					var to; //生成币
					var sc ;//积分
					//交易状态
					if(obj[i].status=="1"){
						 caozuo="<a href='javascript:void(0)' onclick='confrimStatus(\""
						+ obj[i].eid+ "\",\"" + obj[i].userid + "\")' >确认付款</a>";
						 type ="未付款";
					}else{
						 caozuo="已付款";
						 type ="已付款";
					}

					//生成币状态
					if(obj[i].tokenStatus=="0"){
						to="未分配";
					}else if (obj[i].tokenStatus=="1") {
						to="已分配";
					}else{
						to="";
					}
					//积分状态
					if(obj[i].scoreStatus=="1"){
						sc="交易成功";
					}else{
						sc="";
					}

					rows.push({
						username : obj[i].username, //用户名
						amount : obj[i].amount, //交易金额
						status : type,//交易状态
						bankcard : obj[i].bankcard,//银行卡号
						bankTransaction:obj[i].banktransaction,//流水号
						date : obj[i].date,//转账日期
						successnum : obj[i].successnum,//积分
						scoreStatus : sc ,//积分状态
						token : obj[i].token,//生成币
						tokenStatus :to,
						declaration_price :obj[i].declaration_price,
						declaration_num :obj[i].declaration_num,
						declaration_date :obj[i].declaration_date,
						real_num :obj[i].real_num
						//'caozuo' : caozuo
					});
				}
			    $('#dg').datagrid({loadFilter : pagerFilter}).datagrid('loadData', rows);
				//themes




		}


	});




	var str="";
    //其他信息
	$.ajax({url : 'manager_queryRecordJb',

		data : {},
		type : 'POST',
		async : false,
		success : function(ret) {
			var obj = eval(ret);
			//alert();
			var str1=obj[0].maichubao.split(".");
			var str2=obj[0].jinrimaichubao.split(".");
			str="会员数:"+obj[0].quanburenyuan+"人      "+"今日新增会员数:"+obj[0].xinzengrenyuan+"人         " +"剩余积分包:" +obj[0].shengyu+"           卖出积分包:"+str1[0]+"          今日卖出积分包"+str2[0]
			$('#message').val(str);

		}
	});
}
//信息补全
 function  confrimStatus(eid,userid){
	 window.location="updateJf.html?eid="+eid+"&userid="+userid+"";
}
addJf
//修改
function add(){
	window.location="addJf.html?type="+0+"";
}


function pagerFilter(data) {
	if (typeof data.length == 'number' && typeof data.splice == 'function') {
		data = {total : data.length,rows : data};
	}
	var pageDeploy = $(this);
	var opts = pageDeploy.datagrid('options');
	var pager = pageDeploy.datagrid('getPager');
	pager.pagination({
		onSelectPage : function(pageNum, pageSize) {
			opts.pageNumber = pageNum;
			opts.pageSize = pageSize;
			pager.pagination('refresh', {
				pageNumber : pageNum,
				pageSize : pageSize
			});
			pageDeploy.datagrid('loadData', data);
		}
	});
	if (!data.originalRows) {data.originalRows = (data.rows);}
	var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
	var end = start + parseInt(opts.pageSize);
	data.rows = (data.originalRows.slice(start, end));
	return data;
}
