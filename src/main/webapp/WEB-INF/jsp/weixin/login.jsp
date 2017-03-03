<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
		request.setAttribute("path", path);
		request.setAttribute("basePath", basePath);
	%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="telephone=no" name="format-detection" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="viewport"
	content="width=320, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="apple-touch-icon-precomposed"
	href="images/apple-touch-icon.png" />
<title>绑定账户</title>
<link href="../${bashPath }static/css/reset.css" rel="stylesheet" type="text/css" />
<link href="../${bashPath }static/css/head.css" rel="stylesheet" type="text/css" />
<link href="../${bashPath }static/css/foot.css" rel="stylesheet" type="text/css" />
<link rel='stylesheet' type='text/css' href='../${bashPath }static/css/user.css' />
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="${basePath}static/js/jquery.min.js"></script>
</head>
<body>
	<div class="header" id="header">
		<a class="back" href="javascript:history.back();"></a> <span
			class="headline">绑定账户</span> <a class="search"></a>
	</div>

	<section class="login">
		<div class="form">
			<form method="post" action="" onsubmit="return false;">
				<input type="hidden" name="PHPSESSID"
					value="u02aadldcl5mdv9v836vatlgb5" />
				<ul>
					<li class="sign clearfix"><input id='btn_moblogin'
						type="button" class="btn" value="手机动态密码登录>" /></li>
					<li class="user-info name"><input class='tipInput'
						placeholder='用户名' type="text" placeholder="" value="" name="user_name"
						id='user_name'></li>
					<li class="user-info name"><input class='tipInput'
						placeholder='手机号' type="text" placeholder="" value="" name="user_tel"
						id='user_tel'></li>
					<li style="display: none"><input class='tipInput'
					placeholder='openid' type="text" placeholder="" value="${openid }" name="openid"
					id='openid'></li>
					<li class="enter"><input id='btn_enter' type="button"
						class="btn" value=""  onclick="login()"/></li>
				</ul>
				<input type="hidden" name="act" value="login" />
				<div id="alipay" style="display: none"></div>
			</form>
		</div>
	</section>

<style type="text/css">
ul, li {
	list-style: none;
	margin: 0;
	padding: 0
}

.nones {
	display: none;
}

.jxplus {
	position: fixed;
	bottom: 35px;
	left: 0;
	padding-left: 10px;
	z-index: 99;
	-webkit-user-select: none;
	-webkit-tap-highlight-color: rgba(0, 0, 0, 0);
	-webkit-transform: translate3d(0, 0, 0);
}

.jxplus .circle {
	position: absolute;
	left: 30px;
	bottom: 5px;
	width: 195px;
	height: auto; /*overflow:hidden;*/
	-webkit-transition: all linear .15s
}

.jxplus .circle.shows {
	opacity: 1;
	-webkit-transform: translate(20px, 0px) scale(1);
	pointer-events: auto;
}

.jxplus .circle.hides {
	opacity: 0;
	-webkit-transform: translate(-90px, 0px) scaleX(0.1);
	pointer-events: none
}

.jxbtn.on {
	background: url(images/homedaohang/jx2.png) no-repeat;
	width: 42px;
	height: 42px;
}

.jxbtn.off {
	background: url(images/homedaohang/jx1.png) no-repeat;
	width: 42px;
	height: 42px;
}

.jxicons {
	width: 260px;
	height: 36px;
	position: absolute;
	left: -16px;
	bottom: -2px;
	background: url(images/homedaohang/bg.png) no-repeat;
}

.jxicons ul {
	padding-left: 14px;
}

.jxicons li {
	margin: 8px 14px 0 14px;
	width: 20px;
	height: 20px;
	float: left
}

.jxicons li._category, li.cart, li.myjx {
	margin-top: 10px;
} /**/
.jxicons li a, .jxicons li a:hover {
	width: 100%;
	height: 100%;
	display: block;
}

.jxicons li a {
	background: url(images/homedaohang/icons1.png) no-repeat
}

.jxicons li a:hover {
	background: url(images/homedaohang/icons2.png) no-repeat
}

li._home a:hover, li._home a {
	background-position: -14px -8px;
}

li._category a:hover, li._category a {
	background-position: -60px -10px;
}

li.find a:hover, li.find a {
	background-position: -105px -7px;
}

li.cart a:hover, li.cart a {
	background-position: -151px -8px;
}

li.myjx a:hover, li.myjx a {
	background-position: -198px -8px;
}

#J_Shade {
	width: 100%;
	height: 100%;
	position: fixed;
	top: 0;
	left: 0;
	background: #000;
	opacity: .2;
	-webkit-transform: translate3d(0, 0, 0);
	z-index: 97
}
</style>


</body>
<script type="text/javascript">
function login(){
	var name = $("#user_name").val();
	var tel = $("#user_tel").val();
	var openid = $("#openid").val();
	var sendInfo = {
		name : name,
		tel : tel,
		openid : openid
	};

	$.ajax({
		type : "POST",
		url : "login",
		dataType : "json",
		contentType : 'application/json',
		data : JSON.stringify(sendInfo),
		success : function(data) {
			if (data.msg == "success") {
				alert("绑定成功");
				window.location.href = "myOrder?openid="+openid+"";
			}else if(data.msg == "error"){
				alert("姓名或者手机号不正确");
			}else if(data.msg == "bind"){
				alert("你以绑定账户，无需重复绑定！")
			}else{
				alert("请从微信端登录") 
			}
		},
		error : function() {
			alert("网络异常，请稍后再试。");
		}
	});
}

</script>
</html>