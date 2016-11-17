<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
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
</head>
<body>
	<div id="content">
		<!--
      作者：z.y.q.d
      时间：2016-10-21
      描述：后台管理
    -->

		<div id="dcWrap">
			<!--头部导航开始-->
			<%@ include file="../include/header.jsp"%>
			<!--头部导航结束-->

			<!--左侧边栏导航开始-->
			<%@ include file="../include/leftMenu.jsp"%>
			<!--左侧边栏导航结束-->

			<!--主体内容部分开始-->
			<div id="dcMain">
				<!-- 当前位置 -->
				<div id="urHere">
					管理中心<b>></b>用户管理<b>></b><strong>用户拥有资源</strong>
				</div>
				<div class="mainBox"
					style="height: auto !important; height: 550px; min-height: 550px;">
					<div class="navList">
						<h3>
					<a onclick="location.href='javascript:history.go(-1);'" class="actionBtn">返回上一级</a>${user.name }
				</h3><input type="hidden" id="roleId" value="${role.id }" />
						<table width="800" cellspacing="0" cellPadding="0" id="listTable"
							border="1">
							<thead>
								<tr>
									<td>资源名称</td>
									<td>资源url</td>
									<td>资源权限字符串</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${rids }" var="rid">
									<input type="hidden" class="hasRes" value="${rid }" />
								</c:forEach>
								<c:forEach items="${reses }" var="res">
									<tr>
										<td>${res.name }</td>
										<td>${res.url}</td>
										<td>${res.permission }&nbsp;</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<!--底部开始-->
			<%@ include file="../include/footer.jsp"%>
			<!--底部结束-->
			<div class="clear"></div>
		</div>
</body>
</html>