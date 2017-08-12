<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		
		<meta charset="utf-8" />
		<title></title>
		
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="css/bootstrap.min.css" rel="stylesheet" />
		<link href="css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="css/font-awesome.min.css" />
		<!--[if IE 7]><link rel="stylesheet" href="css/font-awesome-ie7.min.css" /><![endif]-->
		<!--[if lt IE 9]><link rel="stylesheet" href="css/ace-ie.min.css" /><![endif]-->
		
		
		<link rel="stylesheet" href="css/ace.min.css" />
		<link rel="stylesheet" href="css/ace-responsive.min.css" />
		<link rel="stylesheet" href="css/ace-skins.min.css" />
		
		<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
		<!--提示框-->
		<script type="text/javascript" src="js/jquery.tips.js"></script>
		
<script type="text/javascript">
	
	
	//保存
	function save(){
		
		if($("#YSYNAME").val()==""){
			$("#YSYNAME").tips({
				side:3,
	            msg:'输入系统名称',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#YSYNAME").focus();
			return false;
		}

		if($("#COUNTPAGE").val()==""){
			$("#COUNTPAGE").tips({
				side:3,
	            msg:'输入每页条数',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#COUNTPAGE").focus();
			return false;
		}
		
		if($("#SMTP").val()==""){
			$("#SMTP").tips({
				side:1,
	            msg:'输入SMTP',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#SMTP").focus();
			return false;
		}
		
		if($("#PORT").val()==""){
			$("#PORT").tips({
				side:1,
	            msg:'输入端口',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#PORT").focus();
			return false;
		}
		
		if($("#EMAIL").val()==""){
			
			$("#EMAIL").tips({
				side:3,
	            msg:'输入邮箱',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#EMAIL").focus();
			return false;
		}else if(!ismail($("#EMAIL").val())){
			$("#EMAIL").tips({
				side:3,
	            msg:'邮箱格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#EMAIL").focus();
			return false;
		}
	
		if($("#PAW").val()==""){
			$("#PAW").tips({
				side:1,
	            msg:'输入密码',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#PAW").focus();
			return false;
		}
		
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
		
	}
	
	function ismail(mail){
		return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
		}
</script>
	</head>
<body>
	<form action="head/saveSys.do" name="Form" id="Form" method="post">
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:15%">系统名称:</td>
				<td><input type="text" name="YSYNAME" id="YSYNAME" value="${pd.YSYNAME }" placeholder="这里输入系统名称" style="width:90%" title="系统名称"/></td>
			
				<td style="width:15%">每页条数:</td>
				<td><input type="number" name="COUNTPAGE" id="COUNTPAGE" value="${pd.COUNTPAGE }" placeholder="这里输入每页条数" style="width:90%" title="每页条数"/></td>
			</tr>
		</table>
		
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="text-align: center;" colspan="100">
					邮件服务器配置
				</td>
			</tr>
			<tr>
				<td style="width:15%">SMTP:</td>
				<td><input type="text" name="SMTP" id="SMTP" value="${pd.SMTP }" placeholder="例如:smtp.qq.com" style="width:90%" title="SMTP"/></td>
			
				<td style="width:15%">端口:</td>
				<td><input type="number" name="PORT" id="PORT" value="${pd.PORT }" placeholder="一般为：25" style="width:90%" title="端口"/></td>
			</tr>
			<tr>
				<td style="width:15%">邮箱:</td>
				<td><input type="email" name="EMAIL" id="EMAIL" value="${pd.EMAIL }" placeholder="请输入邮件服务器邮箱" style="width:90%" title="邮箱"/></td>
			
				<td style="width:15%">密码:</td>
				<td><input type="password" name="PAW" id="PAW" value="${pd.PAW }" placeholder="请输入邮箱密码" style="width:90%" title="密码"/></td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="100">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
		
	</form>
	
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/ace-elements.min.js"></script>
		<script src="js/ace.min.js"></script>
		
		<script type="text/javascript">
		
		</script>
	
</body>
</html>