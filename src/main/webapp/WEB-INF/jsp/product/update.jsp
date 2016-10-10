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
	<sf:form method="post" modelAttribute="product" id="addForm">
	<table width="800" cellspacing="0" cellPadding="0">
		<thead><tr><td colspan="2">更新产品功能</td></tr></thead>
		<tr>
			<td class="rightTd" width="200px">产品名称:</td>
			<td class="leftTd"><sf:input path="name" size="30"/></td>
		</tr>
		<tr>
			<td class="rightTd">产品编号:</td><td><sf:input path="productNo" size="30"/></td>
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
			<td colspan="2" class="centerTd"><input type="submit" value="添加产品"/><input type="reset"/></td>
		</tr>
	</table>
	</sf:form>
</div>
</body>
</html>