<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资源管理</title>
<meta name="Copyright" content="Douco Design." />
<link href="<%=basePath%>css/public.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/global.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.tab.js"></script>
</head>
<body>
	<!--
      作者：z.y.q.d
      时间：2016-10-21
      描述：后台管理
    -->

	<div id="dcWrap">
		<!--头部导航开始-->
		<div id="dcHead">
			<div id="head">
				<div class="logo">
					<a href="index.html"><img src="images/dclogo.gif" alt="logo"></a>
				</div>
				<div class="nav">
					<ul>
						<li class="M"><a href="JavaScript:void(0);" class="topAdd">新建</a>
							<div class="drop mTopad">
								<a href="#">产品</a><a href="#">单页面</a> <a href="#">管理员</a> <a
									href="link.html"></a>
							</div></li>
						<li><a href="#" target="_blank">查看产品</a></li>
						<li><a href="#">清除缓存</a></li>
						<li><a href="#" target="_blank">帮助</a></li>
					</ul>
					<ul class="navRight">
						<li class="M noLeft"><a href="JavaScript:void(0);">您好， <shiro:principal /></a>
							<div class="drop mUser">
								<a href="#">编辑我的个人资料</a> <a href="#">设置账户</a>
							</div></li>
						<li class="noRight"><a href="<%=request.getContextPath() %>/logout">退出系统</a></li>
					</ul>
				</div>
			</div>
		</div>
		<!--头部导航结束-->

		<!--左侧边栏导航开始-->
		<div id="dcLeft">
			<div id="menu">
				<ul class="top">
					<li class="cur"><a href="index.html"><i class="home"></i><em>管理首页</em></a></li>
				</ul>
				<shiro:authenticated>
					<ul>
						<li><a href="system.html"><i class="system"></i><em>系统设置</em></a></li>
						<shiro:hasPermission name="/admin/res/list">
							<li><a href="<%=basePath%>admin/resource/list"><i
									class="nav"></i><em>资源管理</em></a></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="/admin/role/list">
							<li><a href="<%=basePath%>admin/role/list"><i
									class="show"></i><em>角色管理</em></a></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="/admin/user/list">
							<li><a href="<%=basePath%>admin/user/list"><i
									class="page"></i><em>用户管理</em></a></li>
						</shiro:hasPermission>
					</ul>
				
				<ul>
					<shiro:hasPermission name="/admin/product/list">
						<li><a href="<%=basePath%>admin/product/list"><i
								class="productCat"></i><em>产品管理</em></a></li>
					</shiro:hasPermission>
				</ul>
				<ul class="bot">
					<li><a href="backup.html"><i class="backup"></i><em>数据备份</em></a></li>
					<li><a href="manager.html"><i class="manager"></i><em>网站管理员</em></a></li>
					<li><a href="manager_log.html"><i class="managerLog"></i><em>操作记录</em></a></li>
				</ul>
				</shiro:authenticated>
			</div>
		</div>
		<!--左侧边栏导航结束-->

		<!--主体内容部分开始-->
		<div id="dcMain">
			<!-- 当前位置 -->
			<div id="urHere">
				管理中心<b>></b><strong>资源管理</strong>
			</div>
			<div class="mainBox"
				style="height: auto !important; height: 550px; min-height: 550px;">
				<h3>
					<a href="addnav.html" class="actionBtn">添加资源</a>自定义资源
				</h3>
				<div class="navList">
					<table width="100%" border="0" cellpadding="10" cellspacing="0"
						class="tableBasic">
						<tr>
							<th align="center" > 序号</th>
							<th width="113" align="center">资源名称</th>
							<th align="left">资源地址</th>
							<th width="80" align="center">权限字符串</th>
							<th width="120" align="center">操作</th>
						</tr>
						<c:forEach items="${ress }" var="res" varStatus="i">
							<tr>
								<td align="center">${i.count}</td>
								<td align="center">${res.name}</td>
								<td align="center">${res.url }</td>
								<td align="center">${res.permission }</td>
								<td align="center">
									<a href="<%=basePath%>admin/resource/update/${res.id }" class="list_op">更新</a> |
									<a href="<%=basePath%>admin/resource/del?id=${res.id }">删除</a>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
		<!--主体内容部分结束-->

		<div class="clear"></div>
		<!--底部开始-->
		<div id="dcFooter">
			<div id="footer">
				<div class="line"></div>
				<ul>版权所有 © 2017 毕业设计，并保留所有权利。
				</ul>
			</div>
		</div>
		<!--底部结束-->
		<div class="clear"></div>

	</div>
	<!--end-->
</body>
</html>
