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
<title>角色管理</title>
<meta name="Copyright" content="Douco Design." />
</head>
<body>
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
				管理中心<b>></b><strong>会员管理</strong>
			</div>
			<div class="mainBox"
				style="height: auto !important; height: 550px; min-height: 550px;">
				<h3>
					<a href="addnav.html" class="actionBtn">添加会员</a>自定义会员
				</h3>
				<div class="navList">
					<table width="100%" border="0" cellpadding="10" cellspacing="0"
						class="tableBasic">
						<tr>
							<th>用户标识</th>
							<th>用户名称</th>
							<th>电话</th>
							<th>地址</th>
							<th>性别</th>
							<th>总资产</th>
							<th>总负债</th>
							<th>征信情况</th>
							<th>行业</th>
							<th>房产</th>
							<th>动产</th>
							<th>公司</th>
							<th>实体铺面</th>
							<th>用户状态</th>
							<th>用户操作</th>
						</tr>
						</thead>
						<tbody>
							<c:forEach items="${users }" var="user">
								<tr>
									<td align="center">${user.id }</td>
									<td align="center"><a href="${user.id }" class="list_link">${user.name }</a></td>
									<td align="center">${user.tel }</a></td>
									<td align="center">${user.address }</a></td>
									<td align="center">${user.gender }</a></td>
									<td align="center">${user.totalAssets }</a></td>
									<td align="center">${user.totalLiability }</a></td>
									<td align="center">${user.creditConditions }</a></td>
									<td align="center">${user.industry }</a></td>
									<td align="center"><c:if test="${user.estate eq 0 }">无</c:if> <c:if
											test="${user.estate eq 1 }">有</c:if></a></td>
									<td align="center"><c:if test="${user.movable eq 0 }">无</c:if> <c:if
											test="${user.movable eq 1 }">有</c:if></a></td>
									<td align="center"><c:if test="${user.company eq 0 }">无</c:if> <c:if
											test="${user.company eq 1 }">有</c:if></a></td>
									<td align="center"><c:if test="${user.solidSurfacing eq 0 }">无</c:if> <c:if
											test="${user.solidSurfacing eq 1 }">有</c:if></a></td>
									<td align="center"><c:if test="${user.isEnable eq 0 }">
											<span class="emp">停用</span>
											<a href="updateStatus/${user.id }" class="list_op"> | 启用</a> 
										</c:if> <c:if test="${user.isEnable eq 1 }">
											<span>启用</span>
											<a href="updateStatus/${user.id }" class="list_op"> | 停用</a>
										</c:if> &nbsp;</td>
									<td align="center"><shiro:hasRole name="ADMIN">
											<a href="${basePath }admin/user/update/${user.id }"
												class="list_op">更新</a> | <a
												href="${basePath }admin/user/listRes/${user.id }">查看管理资源</a>
										</shiro:hasRole></td>
								</tr>
							</c:forEach>
					</table>
				</div>
				<%@ include file="../include/pageSplit.jsp"%>
			</div>
		</div>
		<!--主体内容部分结束-->

		<!--底部开始-->
		<%@ include file="../include/footer.jsp"%>
		<!--底部结束-->
		<div class="clear"></div>

	</div>
	<!--end-->
</body>
</html>