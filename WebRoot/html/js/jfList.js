//进入页面默认执行
$(function() {
	$('#username').val("");
	$('#status').val("");

	chaxun();
});
//模糊查询
function chaxun() {
	var username=$('#username').val();
	var status=$('#status').val();
	var obj={};
	obj['member_name']=username;
	obj['status']=status;
	//var str1 = '{ "member_id": "1", "status": "2" }';
	var rows = [];
	$.ajax({
		//url : "doctor/admin_queryDoctors.do",
		url: "../../doctor/admin_queryDoctors.do",
		dataType  : "json",
		data:JSON.stringify(obj),
		//data : {
		//	"member_id":username, //用户名
		//	"status":status //认证状态
		//},
		contentType: 'application/json',
		type : 'POST',

		async : false,
		success : function(ret) {
						var obj = eval(ret);

						for ( var i = 0; i <= obj.length - 1; i++) {
							//alert(i);
							var renzhengcaozuo ;
							//var status ;
							var shanchucaozuo;
							//var to; //生成币
							//var sc ;//积分
							//交易状态
							//alert(obj[i].status);
							if(obj[i].status=="1"){
								renzhengcaozuo="<a href='javascript:void(0)' onclick='confrimMember(\"" + obj[i].member_id + "\")' >认证</a>";

							}
							if(obj[i].status=="0"){
								renzhengcaozuo="未认证";

							}

							if(obj[i].status=="2"){
								renzhengcaozuo="已认证";

							}
							shanchucaozuo="<a href='javascript:void(0)' onclick='deleteMember(\""
							+ obj[i].member_id+ "\")' >删除</a>";
							//积分状态

							//alert(obj[i].member_name);
							//alert(renzhengcaozuo);
					rows.push({

						member_id:obj[i].member_id,
						member_name : obj[i].member_name, //用户名
						member_truename :obj[i].member_truename,
						member_mobile:obj[i].member_mobile,
						hospital_name:obj[i].hospital_name,
						area:obj[i].area,
						renzhengcaozuo:renzhengcaozuo,
						shanchucaozuo:shanchucaozuo

					});
							//alert(i+"结束")
				}
			    $('#dg').datagrid({loadFilter : pagerFilter}).datagrid('loadData', rows);
				//themes




		}


	});
}
//信息补全
 function  deleteMember(member_id){
	 //window.location="updateJf.html?eid="+eid+"&userid="+userid+"";
		alert(member_id)
	 $.ajax({
		 url: "../../doctor/admin_deleteDoctor.do?memberid="+member_id,
		 headers : {
			 'Content-Type' : 'application/json;charset=utf-8'
		 },
		 success: function (root) {


			 if(root =="0"){
				 alert("删除成功");
				 $('#username').val("");
				 $('#status').val("");
				 chaxun();
			 }
			 if(root =='1'){
				 alert("删除失败");
				 $('#username').val("");
				 $('#status').val("");
				 chaxun();
			 }


		 },
		 error: function () {
			 alert("与服务器连接失败");
		 }
	 });
}
function  confrimMember(member_id){
	//window.location="updateJf.html?eid="+eid+"&userid="+userid+"";
	//alert(member_id)
	$.ajax({
		url: "../../doctor/admin_renzheng.do?memberid="+member_id,
		headers : {
			'Content-Type' : 'application/json;charset=utf-8'
		},
		success: function (root) {


			if(root =="0"){
				alert("认证成功");
				$('#username').val("");
				$('#status').val("");
				chaxun();
			}
			if(root =='1'){
				alert("认证失败");
				$('#username').val("");
				$('#status').val("");
				chaxun();
			}


		},
		error: function () {
			alert("与服务器连接失败");
		}
	});
}

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
