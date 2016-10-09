<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript">
	$(function() {
		var hasReses = new Array();
		$(".hasRes").each(function() {
			hasReses.push($(this).val());
		});
		$(".setPermission").each(function() {
			if ($.inArray($(this).val(), hasReses) >= 0) {
				$(this).attr("checked", "checked");
			}
		});
		$(".setPermission").click(function() {
			var c = 0;
			if (this.checked) {
				c = 1;
			}
			var resId = $(this).val();
			var roleId = $("#roleId").val();
			$.post("<%=request.getContextPath()%>/admin/role/setRes", {
				c : c,
				resId : resId,
				roleId : roleId
			}, function(data) {
			});
		});
	});
</script>
</head>
<body>
	<div id="content">
		<h3 class="admin_link_bar">
			<jsp:include page="inc.jsp"></jsp:include>
		</h3>
		${role.name }---${role.sn} <input type="hidden" id="roleId"
			value="${role.id }" />
		<table width="800" cellspacing="0" cellPadding="0" id="listTable"
			border="1">
			<thead>
				<tr>
					<td>资源名称</td>
					<td>资源url</td>
					<td>资源权限字符串</td>
					<td>操作</td>
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
						<td><input type="checkbox" name="setPermission"
							class="setPermission" value="${res.id }" /> &nbsp;</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>