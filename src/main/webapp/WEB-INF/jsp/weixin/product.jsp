<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>产品介绍</title>
</head>
<body>
	<table width="100%" border="0" cellpadding="10" cellspacing="0"
		class="tableBasic">
		<tr>
			<th width="80">序号</th>
			<th width="80">产品名称</th>
			<th width="80">产品编号</th>
			<th width="80">产品定价</th>
			<th width="80">房产</th>
			<th width="80">动产</th>
			<th width="80">公司</th>
			<th width="80">实体</th>
			<th width="80">产品状态</th>
			<th width="80">操作</th>
		</tr>
		<!--数据循环开始-->
		<c:forEach items="${products }" var="product" varStatus="i">
			<tr>
				<td align="center">${i.count}</td>
				<td align="center">${product.name }</td>
				<td align="center">${product.productNo }</td>
				<td>${product.productPrice }</td>
				<td align="center"><c:if test="${product.estate eq 0 }">无</c:if>
					<c:if test="${product.estate eq 1 }">有</c:if></td>
				<td align="center"><c:if test="${product.movable eq 0 }">无</c:if>
					<c:if test="${product.movable eq 1 }">有</c:if></td>
				<td align="center"><c:if test="${product.company eq 0 }">无</c:if>
					<c:if test="${product.company eq 1 }">有</c:if></td>
				<td align="center"><c:if test="${product.solidSurfacing eq 0 }">无</c:if>
					<c:if test="${product.solidSurfacing eq 1 }">有</c:if></td>
				<td align="center" id="updateStatus${product.id }"><c:if
						test="${product.isEnable eq 0 }">
						<span class="stop" id="stop${product.id }">下架 | </span>
						<a onclick="updateStatus(${product.id },${product.isEnable});"
							class="updateColor" id="start${product.id }"> 上架</a>
					</c:if> <c:if test="${product.isEnable eq 1 }">
						<span class="start" id="start${product.id }">上架 | </span>
						<a onclick="updateStatus(${product.id },${product.isEnable});"
							class="updateColor" id="stop${product.id }"> 下架</a>
					</c:if></td>
				<td align="center"><a
					onclick="tcUpdate(${product.id },'${product.name }','${product.productNo }',
										'${product.productPrice }','${product.description }','${product.estate }','${product.movable }','${product.company }',
										'${product.solidSurfacing }','${product.suitable }')"
					class="updateColor">更新</a> | <a
					onclick="deleteProduct(this,${product.id})" class="deleteColor">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>