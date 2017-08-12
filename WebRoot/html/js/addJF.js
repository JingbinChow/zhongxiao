$(function() {
	//默认登陆人员为操作人员
	$.ajax({url : 'manager_querysession',
		type : 'POST',
		async : false,
		success : function(ret) {
			$('#operateorid').val(ret);
		}
	});
});
function  findJb(){
	var name =$('#name').val();
	var bankcard =$('#bankcard').val();

	if(isNotEmpty(name)&&isNotEmpty(bankcard)){

			$.ajax({url : 'manager_queryFor',
				data : {
					'name':name,
					'bankcard':bankcard
				},
				type : 'POST',
				async : false,
				success : function(ret) {
					var obj = eval(ret);
					if(obj.length>0){
						$('#username').val(obj[0].username);
						$('#bankcard').val(obj[0].bankcard);
						$('#name').val(obj[0].name);
						$('#bankname').val(obj[0].bankname);
						$('#userid').val(obj[0].userid);
					}else{
							alert("该人员未在系统内注册");
					}


				}
			});
	}
}

function  save(){

	var userid=$('#userid').val();//用户id                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           ').val();
	var username=$('#username').val();//用户名
	var bankcard=$('#bankcard').val();//用行卡号
	var amount=$('#amount').val();//交易金额
	var bankTransaction=$('#bankTransaction').val();//流水号
	var date =$('#dd').datebox('getValue'); //获取时间
	var operateorid=$('#operateorid').val();//操作人
	var bankname=$('#bankname').val();//银行

	if(isEmpty(username)){alert("请输入用户名");return false;}
	if(isEmpty(bankcard)){alert("请输入银行卡号");return false;}
	if(isEmpty(amount)){alert("请输入交易金额");return false;}
	if(isEmpty(bankTransaction)){alert("请输入流水号");return false;}
	if(isEmpty(date)){alert("请选择转账日期");return false;}
	if(isEmpty(operateorid)){alert("请输入操作人员id");return false;}
	if(isEmpty(bankname)){alert("请输所属银行");return false;}
	if(isEmpty(userid)){alert("该人员不存在无法录入");return false;}
	if(confirm("您确认到账？")){
		$.ajax({url : 'manager_saveOrUpdate',
			data : {
				'userid':userid,
				'amount':amount,
				'bankTransaction':bankTransaction,
				'operateorid':operateorid, //操作人员id
				'bankcard':bankcard,//银行卡号
				'date':date,
				'bankname':bankname,
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