<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<link href="${basePath}static/css/public.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript" src="${basePath}static/js/jquery.min.js"></script>
<script type="text/javascript" src="${basePath}static/js/global.js"></script>
<script type="text/javascript" src="${basePath}static/js/jquery.tab.js"></script>
<!--头部导航开始-->
<div id="dcHead">
	<div id="head">
		<div class="logo">
			<!-- <a href="index.html"><img src="images/dclogo.gif" alt="logo"></a> -->
		</div>
		<div class="nav">
			<ul>
				<!-- <li class="M"><a href="JavaScript:void(0);" class="topAdd">新建</a>
					<div class="drop mTopad">
						<a href="#">产品</a><a href="#">单页面</a> <a href="#">管理员</a> <a
							href="link.html"></a>
					</div></li> -->
				<li><a href="${basePath }admin/product/list" >查看产品</a></li>
				<li><a href="${basePath }admin/process/list">产品购买</a></li>
<%-- 				<li><a href="${basePath }admin/process/orderList" >订单</a></li>
 --%>				<li class="M noLeft"> <a href="JavaScript:void(0);">订单</a>
 						<div class="drop mUser">
							<a href="${basePath }admin/process/orderList" class="updateColor">我的订单</a> 
							<shiro:hasRole name="ADMIN">
								<a href="${basePath }admin/process/allOrderList" class="updateColor">全部订单</a> 
							</shiro:hasRole>
						</div>
 
 					</li>
			</ul>
			<ul class="navRight">
				<li class="M noLeft"><a href="JavaScript:void(0);">您好， 
				<shiro:principal /><%-- <img src="${basePath }static/images/icon_select.png" style="position:absolute;
				top:13px;right: -4%;"> --%></a>
				
					<div class="drop mUser">
						<a onclick="updatePwd();" class="updateColor">修改密码</a> 
					</div></li>
				<li class="noRight"><a
					href="${path }/logout">退出系统</a></li>
			</ul>
		</div>
	</div>
</div>
<!--头部导航结束-->
<!--弹窗开始 更新 -->
		<div class="updatePwd">
			<div class="updatePwd1">
				修改密码<img src="${basePath}static/images/closed.png"
					onclick="tcUpdatePwdclose()"
					style="float: right; margin-top: 15px; margin-right: 15px; cursor: pointer;">
			</div>
			<table>
				<tr >
					<td height="35" align="right">登录名称：</td>
					<td><input type="text"   value="<shiro:principal />" id="updateLoginName"
						size="80" class="inpMain" readonly/></td>
				</tr>
				<tr>
					<td height="35" align="right">密 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
					<td><input type="password" name="updatePassword" id="updatePassword" value=""
						size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">确认密码：</td>
					<td><input type="password" name="updatePassword2" id="updatePassword2" value=""
						size="80" class="inpMain" /></td>
				</tr>
				
				<tr>
					<td></td>
					<td><input id="updateBtn" class="btn" type="button" value="提交"
						onclick="updateUserPwd();" /></td>
				</tr>
			</table>
		</div>
		<!--弹窗结束-->

<style type="text/css">
.updateColor {
	color: #05cc88;
	cursor: pointer;
}

/**弹窗样式开始**/
.updatePwd {
	width: 600px;
	/* height: 700px; */
	display: none;
	position: absolute;
	margin: 200px auto;
	z-index: 999;
	background: #fff;
	left: 35%;
	border-radius: 5px;
}

.updatePwd table {
	margin-top: 20px;
	margin-left: 20px;
}

.updatePwd table tr {
	margin-top: 20px;
}

.updatePwd1 {
	width: 100%;
	height: 50px;
	background: #60BBFF;
	border-radius: 5px;
	font-size: 20px;
	color: #fff;
	text-align: center;
	line-height: 50px;
}

.updatePwd1 img {
	width: 25px;
	height: 25px;
}

.mask {
	margin: 0;
	padding: 0;
	border: none;
	width: 100%;
	height: 100%;
	background: #333;
	opacity: 0.6;
	filter: alpha(opacity = 60);
	z-index: 99;
	position: fixed;
	top: 0;
	left: 0;
	display: block;
}
/**弹窗样式结束**/

</style>
<script type="text/javascript">

function updatePwd(){
	$("body").append("<div id='mask'></div>");
    $("#mask").addClass("mask").fadeIn("slow");
    $(".updatePwd").fadeIn("slow");
  }; 
  function tcUpdatePwdclose(){
	  $(".updatePwd").fadeOut("fast");
	    $("#mask").css({ display: 'none' });
  };
  
  function updateUserPwd(){
	  var loginName = $("#updateLoginName").val();
	  var pwd = $("#updatePassword").val();
	  var pwd2 = $("#updatePassword2").val();
	  if(pwd == pwd2){
		  $.get("${basePath }admin/user/updateUserPwd?loginName="+loginName+"&password="+pwd+"", function(data){
			  if(data == "success"){
				  alert("修改成功，请从新登陆！")
				  window.location.href ="${path }/logout";
			  }
		  })
	  }
	  
		  
  }
</script>
