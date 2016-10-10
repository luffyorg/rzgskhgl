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
			<td>资源表示</td>
			<td>名称</td>
			<td>URL</td>
			<td>权限字符串</td>
			<td>操作</td>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${ress }" var="res">
			<tr>
				<td>${res.id }</td>
				<td>${res.name}</td>
				<td>${res.url }</td>
				<td>${res.permission }&nbsp;</td>
				<td>
					<a href="update/${res.id }" class="list_op">更新</a>
				&nbsp;
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
</body>
</html>