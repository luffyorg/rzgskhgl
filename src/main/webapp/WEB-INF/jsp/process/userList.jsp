<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div id="buyuser">
		<h3 class="admin_link_bar">
			<jsp:include page="inc.jsp"></jsp:include>
		</h3>
		<h3>${msg }</h3>
		<table width="800" cellspacing="0" cellPadding="0" id="listTable"
			border="1">
			<thead>
				<tr>
					<td>用户标识</td>
					<td>用户名称</td>
					<td>电话</td>
					<td>地址</td>
					<td>性别</td>
					<td>总资产</td>
					<td>总负债</td>
					<td>征信情况</td>
					<td>行业</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${users }" var="user">
					<tr>
						<td>${user.id }</td>
						<td>${user.name }</td>
						<td>${user.tel }</td>
						<td>${user.address }</td>
						<td>${user.gender }</td>
						<td>${user.totalAssets }</td>
						<td>${user.totalLiability }</td>
						<td>${user.creditConditions }</td>
						<td>${user.industry }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>