<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("path", path);
	request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<meta name="Copyright" content="Douco Design." />
<link href="${basePath }static/css/public.css" rel="stylesheet"
	type="text/css">
<link href="${basePath }static/css/login.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript"
	src="${basePath }static/js/jquery.min.js"></script>
<script type="text/javascript" src="${basePath }static/js/global.js"></script>
<script type="text/javascript"
	src="${basePath }static/js/jquery.tab.js"></script>
</head>
<body>
	<!--
      作者：z.y.q.d
      时间：2016-10-21
      描述：后台管理
    -->

	<!--start-->
	<div id="dcWrap">
		<!--头部导航开始-->
		<div id="dcHead">
			<div id="head">
				<div class="logo">
					<a href="index.html"> <%-- <img src="${basePath }images/dclogo.gif" alt="logo"></a>  --%>
				</div>
				<div class="nav">
					<ul>
						<li><a href="#" target="_blank">帮助</a></li>
					</ul>
					<ul class="navRight">
						<li class="M noLeft"><a href="JavaScript:void(0);">您好，请登录</a>
						</li>
						<li class="noRight"><a href="#">离开</a></li>
					</ul>
				</div>
			</div>
		</div>
		<!--头部导航结束-->

		<!--登录框开始-->
		<div class="loginbox">
			<div class="box">
				<div class="rob1" style="margin-bottom: 40px;">后台登录</div>
				<div class="rob">
					<div
						style="width: 319px; height: 36px; background: url('${basePath }static/images/account.png'); margin: 0 auto;">
						<input type="text" id="name" placeholder="账号" />
					</div>
					<!-- <a href="javascript:void(0)" title="提示" class="warning" id="warn">*</a> -->
				</div>
				<div class="rob">
					<span class="inputBox">
						<div
							style="width: 319px; height: 36px; background: url('${basePath }static/images/password.png'); margin: 0 auto;">
							<input type="password" id="password" placeholder="密码" />
						</div>
					</span>
					<!-- <a href="javascript:void(0)" title="提示" class="warning" id="warn2">*</a> -->
				</div>
				<div class="rob" style="height: 100px;">
					<div class="lbtn">
						<a id="loginbtn">登录</a>
					</div>
				</div>
			</div>
		</div>
		<!--登录框结束-->

		<div class="clear"></div>
		<!--底部开始-->
		<div id="dcFooter">
			<div id="footer">
				<div class="line"></div>
				<ul>版权所有 © 2017 毕业设计，并保留所有权利。
				</ul>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
$("#loginbtn").click(function() {
	var loginname = $("#name").val();
	var pwd = $("#password").val();

	var sendInfo = {
		name : loginname,
		password : pwd
	};

	$.ajax({
		type : "POST",
		url : "login",
		dataType : "json",
		contentType : 'application/json',
		data : JSON.stringify(sendInfo),
		success : function(data) {
			if (data.result == "success") {
				//alert("登录成功");
				window.location.href = "admin/index";
			} else if (data.result == "usererror") {
				alert("用户名或密码有误");
			} else {
				alert("身份验证失败");
			}
		},
		error : function() {
			alert("没有返回值");
		}
	});
});
</script>
</html>