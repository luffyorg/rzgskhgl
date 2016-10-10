<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
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
	<sf:form method="post" modelAttribute="user" id="addForm">
	<table width="800" cellspacing="0" cellPadding="0">
		<thead><tr><td colspan="2">添加用户功能</td></tr></thead>
		<tr>
			<td class="rightTd" width="200px">用户名:</td>
			<td class="leftTd"><sf:input path="name" size="30"/></td>
		</tr>
		<tr>
			<td class="rightTd">用户密码:</td><td><sf:password path="password" size="30"/></td>
		</tr>
		<tr>
			<td class="rightTd" width="200px">电话:</td>
			<td class="leftTd"><sf:input path="tel" size="30"/></td>
		</tr>
		<tr>
			<td class="rightTd" width="200px">地址:</td>
			<td class="leftTd"><sf:input path="address" size="30"/></td>
		</tr>
		<tr>
			<td class="rightTd" width="200px">性别:</td>
			<td class="leftTd"><sf:input path="gender" size="30"/></td>
		</tr>
		<tr>
			<td class="rightTd" width="200px">总资产:</td>
			<td class="leftTd"><sf:input path="totalAssets" size="30"/></td>
		</tr>
		<tr>
			<td class="rightTd" width="200px">总负债:</td>
			<td class="leftTd"><sf:input path="totalLiability" size="30"/></td>
		</tr>
		<tr>
			<td class="rightTd" width="200px">征信情况:</td>
			<td class="leftTd"><sf:input path="creditConditions" size="30"/></td>
		</tr>
		<tr>
			<td class="rightTd" width="200px">行业:</td>
			<td class="leftTd"><sf:input path="industry" size="30"/></td>
		</tr>
		<tr>
			<td class="rightTd" width="200px">房产:</td>
			<td class="leftTd"><sf:input path="estate" size="30"/></td>
		</tr>
		<tr>
			<td class="rightTd" width="200px">动产:</td>
			<td class="leftTd"><sf:input path="movable" size="30"/></td>
		</tr>
		</tr>
		<tr>
			<td class="rightTd" width="200px">公司:</td>
			<td class="leftTd"><sf:input path="company" size="30"/></td>
		</tr>
		</tr>
		<tr>
			<td class="rightTd" width="200px">实体铺面:</td>
			<td class="leftTd"><sf:input path="solidSurfacing" size="30"/></td>
		</tr>
		<tr>
			<td class="rightTd">状态:</td>
			<td>
				<sf:select path="isEnable">
					<sf:option value="0">停用</sf:option>
					<sf:option value="1">启用</sf:option>
				</sf:select>
			</td>
		</tr>
		<tr>
			<td class="rightTd">角色:</td>
			<td>
				<c:forEach var="role" items="${roles }">
					${role.name }<input type="checkbox" name="rids" value="${role.id }"/>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<td colspan="2" class="centerTd"><input type="submit" value="添加用户"/><input type="reset"/></td>
		</tr>
	</table>
	</sf:form>
</div>
</body>
</html>