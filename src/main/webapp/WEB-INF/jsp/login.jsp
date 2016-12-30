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
<script type="text/javascript" src="${basePath }static/js/jquery.min.js"></script>
<script type="text/javascript" src="${basePath }static/js/global.js"></script>
<script type="text/javascript" src="${basePath }static/js/jquery.tab.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<style type="text/css">
.yz {
	background: #f0f0f0;
	border: none;
	border-radius: 20px;
	box-shadow: none;
	padding: 0 0 0 0px;
	height: 40px;
	transition: all 0.3s ease 0s;
	margin-left: -12px;
	width: 150px;
	color: #000;
	text-align: center;
	outline: none
}

.form-horizontal {
	background: #fff;
	padding-bottom: 40px;
	border-radius: 15px;
	text-align: center;
}

.form-horizontal .heading {
	display: block;
	font-size: 35px;
	font-weight: 700;
	padding: 35px 0;
	border-bottom: 1px solid #f0f0f0;
	margin-bottom: 30px;
}

.form-horizontal .form-group {
	padding: 0 40px;
	margin: 0 0 25px 0;
	position: relative;
}

.form-horizontal .form-control {
	background: #f0f0f0;
	border: none;
	border-radius: 20px;
	box-shadow: none;
	padding: 0 20px 0 45px;
	height: 40px;
	transition: all 0.3s ease 0s;
}

.form-horizontal .form-control:focus {
	background: #e0e0e0;
	box-shadow: none;
	outline: 0 none;
}

.form-horizontal .form-group i {
	position: absolute;
	top: 12px;
	left: 60px;
	font-size: 17px;
	color: #c8c8c8;
	transition: all 0.5s ease 0s;
}

.form-horizontal .form-control:focus+i {
	color: #00b4ef;
}

.form-horizontal .fa-question-circle {
	display: inline-block;
	position: absolute;
	top: 12px;
	right: 60px;
	font-size: 20px;
	color: #808080;
	transition: all 0.5s ease 0s;
}

.form-horizontal .fa-question-circle:hover {
	color: #000;
}

.form-horizontal .main-checkbox {
	float: left;
	width: 20px;
	height: 20px;
	background: #11a3fc;
	border-radius: 50%;
	position: relative;
	margin: 5px 0 0 5px;
	border: 1px solid #11a3fc;
}

.form-horizontal .main-checkbox label {
	width: 20px;
	height: 20px;
	position: absolute;
	top: 0;
	left: 0;
	cursor: pointer;
}

.form-horizontal .main-checkbox label:after {
	content: "";
	width: 10px;
	height: 5px;
	position: absolute;
	top: 5px;
	left: 4px;
	border: 3px solid #fff;
	border-top: none;
	border-right: none;
	background: transparent;
	opacity: 0;
	-webkit-transform: rotate(-45deg);
	transform: rotate(-45deg);
}

.form-horizontal .main-checkbox input[type=checkbox] {
	visibility: hidden;
}

.form-horizontal .main-checkbox input[type=checkbox]:checked+label:after
	{
	opacity: 1;
}

.form-horizontal .text {
	float: left;
	margin-left: 7px;
	line-height: 20px;
	padding-top: 5px;
	text-transform: capitalize;
}

.form-horizontal .btn {
	float: right;
	font-size: 14px;
	color: #fff;
	background: #00b4ef;
	border-radius: 30px;
	padding: 10px 25px;
	border: none;
	text-transform: capitalize;
	transition: all 0.5s ease 0s;
}

@media only screen and (max-width: 479px) {
	.form-horizontal .form-group {
		padding: 0 25px;
	}
	.form-horizontal .form-group i {
		left: 45px;
	}
	.form-horizontal .btn {
		padding: 10px 20px;
	}
}

body, html {
	font-size: 100%;
	padding: 0;
	margin: 0;
}

/* Reset */
*, *:after, *:before {
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
}

/* Clearfix hack by Nicolas Gallagher: http://nicolasgallagher.com/micro-clearfix-hack/ */
.clearfix:before, .clearfix:after {
	content: " ";
	display: table;
}

.clearfix:after {
	clear: both;
}

body {
	background: #60bbff;
	color: #D5D6E2;
	font-weight: 500;
	font-size: 1.05em;
	font-family: "Microsoft YaHei", "Segoe UI", "Lucida Grande", Helvetica,
		Arial, sans-serif;
}

a {
	color: rgba(255, 255, 255, 0.6);
	outline: none;
	text-decoration: none;
	-webkit-transition: 0.2s;
	transition: 0.2s;
}

a:hover, a:focus {
	color: #74777b;
	text-decoration: none;
}
</style>
</head>
<body>
	<!--  
      作者：z.y.q.d
      时间：2016-10-21
      描述：后台管理
    -->

<div class="demo" style="padding: 20px 0;">
		<div class="container">
			<div class="row">
				<div class="col-md-offset-3 col-md-6">
					<form class="form-horizontal">
						<span class="heading">用户登录</span>
						<div class="form-group">
							<input type="text" class="form-control" id="name" placeholder="账户">
							<i class="fa fa-user"></i>
						</div>
						<div class="form-group help">
							<input type="password" class="form-control" id="password" placeholder="密　码">
							<i class="fa fa-lock"></i>
							<a href="#" class="fa fa-question-circle"></a>
						</div>
					
						<div>
						    <input type="text" id="captcha" name="captcha" class="yz"
								maxlength="4" placeholder="验证码" /> 
							<label><img id="captchaImage"
								src="${basePath }captcha" onclick="chageCode()"
								title="图片看不清？点击重新得到验证码" style="cursor: pointer;" /></label>
							<label style="font-size:14px;font-weight:400;margin-bottom:40px;color:#00b4ef;"  onclick="chageCode()">看不清，点击刷新</label>
						</div>
						<div class="form-group">
							<!-- <div class="main-checkbox">
								<input type="checkbox" value="None" id="checkbox1" name="check"/>
								<label for="checkbox1"></label>
							</div>
							<span class="text">记住密码</span> -->
							<button type="button" class="btn" id="loginbtn">登录</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	function chageCode() {
		$('#captchaImage').attr('src', 'captcha.do?abc=' + Math.random());//链接后添加Math.random，确保每次产生新的验证码，避免缓存问题。
	}
	$("#loginbtn").click(function() {
		var captcha = $("#captcha").val();
		var loginname = $("#name").val();
		var pwd = $("#password").val();

		var sendInfo = {
			name : loginname,
			password : pwd,
			captcha : captcha
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
					window.location.href = "admin/process/orderList";
				} else if (data.result == "usererror") {
					alert("用户名或密码有误");
				} else if (data.result == "验证码错误") {
					alert("验证码错误");
				} else {

				}
			},
			error : function() {
				alert("网络异常，请稍后再试。");
			}
		});
	});
	
	$("body").keydown(function() {
	    if (event.keyCode == "13") {//keyCode=13是回车键
	        $("#loginbtn").click();
	    }
	});    
</script>
</html>