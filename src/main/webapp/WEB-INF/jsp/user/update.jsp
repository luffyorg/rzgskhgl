<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
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
<script type="text/javascript"
	src="${basePath}static/js/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		var hasRoles = new Array();
		$(".hasRole").each(function() {
			hasRoles.push($(this).val());
		});
		$(".rids").each(function() {
			if ($.inArray($(this).val(), hasRoles) >= 0) {
				$(this).attr("checked", "checked");
			}
		});
	});
</script>
</head>
<body>
	<div id="content">
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
				<sf:form method="post" modelAttribute="user" id="addForm">
					<table width="800" cellspacing="0" cellPadding="0">
						<thead>
							<tr>
								<td colspan="2">添加用户功能</td>
							</tr>
						</thead>
						<tr>
							<td class="rightTd" width="200px">用户名(必须是英文):</td>
							<td class="leftTd"><sf:input path="name" size="30" /></td>
						</tr>
						<tr>
							<td class="rightTd">用户密码:</td>
							<td><sf:password path="password" size="30" /></td>
						</tr>
						<tr>
							<td class="rightTd" width="200px">电话:</td>
							<td class="leftTd"><sf:input path="tel" size="30" /></td>
						</tr>
						<tr>
							<td class="rightTd" width="200px">地址:</td>
							<td class="leftTd"><sf:input path="address" size="30" /></td>
						</tr>
						<tr>
							<td class="rightTd" width="200px">性别:</td>
							<td class="leftTd"><sf:input path="gender" size="30" /></td>
						</tr>
						<tr>
							<td class="rightTd" width="200px">总资产:</td>
							<td class="leftTd"><sf:input path="totalAssets" size="30" /></td>
						</tr>
						<tr>
							<td class="rightTd" width="200px">总负债:</td>
							<td class="leftTd"><sf:input path="totalLiability" size="30" /></td>
						</tr>
						<tr>
							<td class="rightTd" width="200px">征信情况:</td>
							<td class="leftTd"><sf:input path="creditConditions"
									size="30" /></td>
						</tr>
						<tr>
							<td class="rightTd" width="200px">行业:</td>
							<td class="leftTd"><sf:input path="industry" size="30" /></td>
						</tr>
						<tr>
							<td class="rightTd" width="200px">房产:</td>
							<td class="leftTd"><sf:select path="estate">
									<sf:option value="0">无</sf:option>
									<sf:option value="1">有</sf:option>
								</sf:select></td>
						</tr>
						<tr>
							<td class="rightTd" width="200px">动产:</td>
							<td class="leftTd"><sf:select path="movable">
									<sf:option value="0">无</sf:option>
									<sf:option value="1">有</sf:option>
								</sf:select></td>
						</tr>
						</tr>
						<tr>
							<td class="rightTd" width="200px">公司:</td>
							<td class="leftTd"><sf:select path="company">
									<sf:option value="0">无</sf:option>
									<sf:option value="1">有</sf:option>
								</sf:select></td>
						</tr>
						<tr>
							<td class="rightTd" width="200px">实体铺面:</td>
							<td><sf:select path="solidSurfacing">
									<sf:option value="0">无</sf:option>
									<sf:option value="1">有</sf:option>
								</sf:select></td>
						</tr>
						<tr>
							<td class="rightTd">状态:</td>
							<td><sf:select path="isEnable">
									<sf:option value="0">停用</sf:option>
									<sf:option value="1">启用</sf:option>
								</sf:select></td>
						</tr>
						<tr>
							<td class="rightTd">角色:</td>
							<td><c:forEach var="hr" items="${hasRole }">
									<input type="hidden" class="hasRole" name="hasRole"
										value="${hr}" />
								</c:forEach> <c:forEach var="role" items="${roles }">
					${role.name }<input type="checkbox" class="rids" name="rids"
										value="${role.id }" />
								</c:forEach></td>
						</tr>
						<tr>
							<td colspan="2" class="centerTd"><input type="submit"
								value="修改用户" /> &nbsp;<input type="reset" /></td>
						</tr>
					</table>
				</sf:form>
			</div>
		</div>
		
		<!--底部开始-->
		<%@ include file="../include/footer.jsp"%>
		<!--底部结束-->
		<div class="clear"></div>
	</div>
</body>
</html>