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
				<li class="M"><a href="JavaScript:void(0);" class="topAdd">新建</a>
					<div class="drop mTopad">
						<a href="#">产品</a><a href="#">单页面</a> <a href="#">管理员</a> <a
							href="link.html"></a>
					</div></li>
				<li><a href="${basePath }admin/product/list" >查看产品</a></li>
				<li><a href="${basePath }process/list">产品购买</a></li>
				<li><a href="${basePath }process/orderList" >订单</a></li>
			</ul>
			<ul class="navRight">
				<li class="M noLeft"><a href="JavaScript:void(0);">您好， <shiro:principal /></a>
					<div class="drop mUser">
						<a href="#">编辑我的个人资料</a> <a href="#">设置账户</a>
					</div></li>
				<li class="noRight"><a
					href="${path }/logout">退出系统</a></li>
			</ul>
		</div>
	</div>
</div>
<!--头部导航结束-->
