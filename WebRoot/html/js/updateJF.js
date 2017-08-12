
$(function() {
	var userid =QueryString("userid");//用户名
	var eid =QueryString("eid");
	//记录补全是自动带出用户名和银行卡号
	if(isNotEmpty(userid)){
		$.ajax({url : 'manager_queryTidAndBankcard',
			data : {
				'eid':eid
			},
			type : 'POST',
			async : false,
			success : function(ret) {
				var obj = eval(ret);
				$('#userid').val(obj[0].userid);
				$('#username').val(obj[0].username);
				$('#bankcard').val(obj[0].bankcard);
				$('#name').val(obj[0].name);
				$('#bankname').val(obj[0].bankname);
				$('#amount').val(obj[0].declaration_price);
				$('#declaration_num').val(obj[0].declaration_num);
				//$('#userid').val(obj[0].userid);
				//$('#userid').val(obj[0].userid);
			}
		});
	}
	//默认登陆人员为操作人员
	$.ajax({url : 'manager_querysession',
		type : 'POST',
		async : false,
		success : function(ret) {
			$('#operateorid').val(ret);
		}


	});
});
function chaxun() {
	var username=$('#username').val();
	var bankcard=$('#bankcard').val();
	var bankTransaction=$('#bankTransaction').val();
	var rows = [];
	$.ajax({url : 'manager_queryRecord',
		data : {
			'username':username, //用户名
			'username':username, //交易金额
			'username':username, //用户名
			'bankcard':bankcard,
			'bankTransaction':bankTransaction
		},
		type : 'POST',
		async : false,
		success : function(ret) {
			var obj = eval(ret);

				for ( var i = 0; i <= obj.length - 1; i++) {
					var caozuo ;
					var to; //生成币
					var sc ;//积分
					//交易状态
					if(obj[i].userid=="1"){
						 caozuo="<a href='javascript:void(0)' onclick='confrimStatus(\""
						+ obj[i].userid+ "\")' >确认付款</a>";
					}else{
						 caozuo="已付款";
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
						userid : obj[i].uid, //用户名
						username : obj[i].username, //用户名
						amount : obj[i].amount, //交易金额
						status : obj[i].status,//交易状态
						bankcard : obj[i].bankcard,//银行卡号
						bankTransaction:obj[i].banktransaction,//流水号
						date : obj[i].date,//转账日期
						successnum : obj[i].successnum,//积分
						scoreStatus : sc ,//积分状态
						token : obj[i].token,//生成币
						tokenStatus :to,
						'caozuo' : caozuo
					});
				}
				$('#dg').datagrid('loadData', rows);




		}


	});
}
 function  save(){
	 var userid=$('#userid').val();//用户id
	 var username=$('#username').val();//用户名
	 var bankcard=$('#bankcard').val();//用行卡号
	 var amount=$('#amount').val();//实际交易金额
	 var bankTransaction=$('#bankTransaction').val();//流水号
	 var date =$('#dd').datebox('getValue'); //获取时间
	 var operateorid=$('#operateorid').val();//操作人
	 var bankname=$('#bankname').val();//操作人

	 if(isEmpty(username)){alert("请输入用户名");return false;}
	 if(isEmpty(bankcard)){alert("请输入银行卡号");return false;}
	 if(isEmpty(bankname)){alert("请输入所属银行");return false;}
	 if(isEmpty(amount)){alert("请输入交易金额");return false;}
	 if(isEmpty(bankTransaction)){alert("请输入流水号");return false;}
	 if(isEmpty(date)){alert("请选择转账日期");return false;}
	 if(isEmpty(operateorid)){alert("请输入操作人员id");return false;}

	 if(confirm("您确认到账？")){
		 $.ajax({url : 'manager_saveOrUpdate',
			 data : {
				 'userid':userid,
				 'amount':amount,
				 'bankTransaction':bankTransaction,
				 'operateorid':operateorid, //操作人员id
				 'bankcard':bankcard,//银行卡号
				 'bankname':bankname,//银行卡号
				 'date':date,
				 'eid' : QueryString("eid"),
				 'type':"3"
			 },
			 type : 'POST',
			 async : false,
			 success : function(ret) {
				 if(ret =="0"){
					 alert("保存成功");
				 }else{
					 alert("保存失败");
				 }
				 window.location="jfList.html";
			 }
		 });
	 }
}
function back(){
	window.location="jfList.html";
}
