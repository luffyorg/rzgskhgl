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
	${user.name }
	<input type="hidden" id="roleId" value="${role.id }"/>
	<table width="800" cellspacing="0" cellPadding="0" id="listTable" border="1">
		<thead>
		<tr>
			<td>资源名称</td>
			<td>资源url</td>
			<td>资源权限字符串</td>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${rids }" var="rid">
			<input type="hidden" class="hasRes" value="${rid }"/>
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
</body>
</html>