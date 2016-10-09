<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
			<td>角色标识</td>
			<td>角色名称</td>
			<td>角色sn</td>
			<td>操作</td>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${roles }" var="role">
			<tr>
				<td>${role.id }</td>
				<td>${role.name}</td>
				<td>${role.sn }&nbsp;</td>
				<td>
					<a href="update/${role.id }" class="list_op">更新</a>
				&nbsp;
					<a href="listRes/${role.id }" class="list_op">设置资源</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
</body>
</html>