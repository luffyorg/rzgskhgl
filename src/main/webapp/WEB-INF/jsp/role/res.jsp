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
<script type="text/javascript" src="${basePath}static/js/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
			$.post("${basePath}admin/role/setRes", {
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
				管理中心<b>></b>角色管理<b>></b><strong>角色资源管理</strong>
			</div>
			<div class="mainBox"
				style="height: auto !important; height: 550px; min-height: 550px;">
				<div class="navList">
					${role.name }---${role.sn} <input type="hidden" id="roleId"
						value="${role.id }" />
					<table width="100%" border="0" cellpadding="10" cellspacing="0"
						class="tableBasic">
						<tr>
							<td>资源名称</td>
							<td>资源url</td>
							<td>资源权限字符串</td>
							<td>操作</td>
						</tr>
						<c:forEach items="${rids }" var="rid">
							<input type="hidden" class="hasRes" value="${rid }" />
						</c:forEach>
						<c:forEach items="${reses }" var="res">
							<tr>
								<td>${res.name }</td>
								<td>${res.url}</td>
								<td>${res.permission }</td>
								<td><input type="checkbox" name="setPermission"
									class="setPermission" value="${res.id }" /> &nbsp;</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				 <!-- 分页开始 -->
				<div style="float:right;margin-top:15px;">
					<c:if test="${pb.currentPage==1 }">
						<a href="#">首页</a> 
					</c:if>
					<c:if test="${pb.currentPage!=1 }">
						<a href="${basePath }admin/role/listRes/${role.id}?pageSize=10&page=1">首页</a> 
					</c:if>
					<c:if test="${pb.hasPreviousPage==true}">
						<a href="${basePath }admin/role/listRes/${role.id}?pageSize=10&page=${pb.currentPage-1}"> ◄上一页</a>
					</c:if>
					<c:if test="${pb.hasPreviousPage==false}">
						<a href="#"> ◄上一页</a>
					</c:if>
					<c:if test="${pb.hasNextPage==true }">
						<a href="${basePath }admin/role/listRes/${role.id}?pageSize=10&page=${pb.currentPage+1}">下一页► </a> 
					</c:if>
					<c:if test="${pb.hasNextPage==false }">
						<a href="#">下一页► </a> 
					</c:if>
					<c:if test="${pb.totalPage==pb.currentPage }">
						<a href="#">末页</a> 
					</c:if>
					<c:if test="${pb.totalPage!=pb.currentPage }">
						<a href="${basePath }admin/role/listRes/${role.id}?pageSize=10&page=${pb.totalPage}">末页</a> 
					</c:if>
					总${pb.allRow }条，第${pb.currentPage}/${pb.totalPage }页，到第
					<input size=2 id="goInput" value='' />页,
					<input type="button"
						value="搜索" class="goButton" onclick="gotoPageByInput(${pb.totalPage });" />
				</div>
				<!-- 分页结束 -->
			</div>
		</div>
	</div>
</body>
</html>