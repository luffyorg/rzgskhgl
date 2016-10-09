<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div id="content">
	<h3 class="admin_link_bar">
		<jsp:include page="inc.jsp"></jsp:include>
	</h3>
	<table width="800" cellspacing="0" cellPadding="0" id="listTable" border="1">
		<thead>
		<tr>
			<td>用户标识</td>
			<td>用户名称</td>
			<td>用户状态</td>
			<td>用户操作</td>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${users }" var="user">
			<tr>
				<td>${user.id }</td>
				<td><a href="${user.id }" class="list_link">${user.name }</a></td>
				<td>
					<c:if test="${user.isEnable eq 0 }">
						<span class="emp">停用</span>
						<a href="updateStatus/${user.id }" class="list_op">启用</a>
					</c:if>
					<c:if test="${user.isEnable eq 1 }">
						<span>启用</span>
						<a href="updateStatus/${user.id }" class="list_op">停用</a>
					</c:if>
					&nbsp;
				</td>
				<td><shiro:hasRole name="ADMIN">
					<a href="update/${user.id }" class="list_op">更新</a>
					</shiro:hasRole>
				&nbsp;
					<a href="listRes/${user.id }" class="list_op">查询管理资源</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
</body>
</html>