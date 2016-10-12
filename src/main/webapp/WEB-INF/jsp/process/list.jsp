<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.8.0.min.js"></script>
</head>
<body>
	<div id="content">
		<h3 class="admin_link_bar">
			<jsp:include page="inc.jsp"></jsp:include>
		</h3>
		<h3>
			<input type="text" id="">
		</h3>
		<h3>${info }</h3>
		<table width="800" cellspacing="0" cellPadding="0" id="listTable"
			border="1">
			<thead>
				<tr>
					<td>序号</td>
					<td>产品名称</td>
					<td>产品编号</td>
					<td>总资产</td>
					<td>总负债</td>
					<td>征信情况</td>
					<td>行业</td>
					<td colspan="4">
						<table>
							<td align="center">条件</td>
							<tr>
								<td>房产</td>
								<td>动产</td>
								<td>公司</td>
								<td>实体</td>
							</tr>
						</table>

					</td>

					<td>产品状态</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${products }" var="product">
					<tr>
						<td>${product.id }</td>
						<td><a href="${product.id }" class="list_link">${product.name }</a></td>
						<td>${product.productNo }</a></td>
						<td>${product.totalAssets }</a></td>
						<td>${product.totalLiability }</a></td>
						<td>${product.creditConditions }</a></td>
						<td>${product.industry }</a></td>

						<td><c:if test="${product.estate eq 0 }">无</c:if> <c:if
								test="${product.estate eq 1 }">有</c:if></a></td>
						<td><c:if test="${product.movable eq 0 }">无</c:if> <c:if
								test="${product.movable eq 1 }">有</c:if></a></td>
						<td><c:if test="${product.company eq 0 }">无</c:if> <c:if
								test="${product.company eq 1 }">有</c:if></a></td>
						<td><c:if test="${product.solidSurfacing eq 0 }">无</c:if> <c:if
								test="${product.solidSurfacing eq 1 }">有</c:if></a></td>
						<td><c:if test="${product.isEnable eq 0 }">
							下架
							</c:if> <c:if test="${product.isEnable eq 1 }">
								上架
							</c:if></td>
						<td><shiro:hasRole name="ADMIN">
								<a href="queryBuyUser/${product.id }" class="list_op">搜索</a>
							</shiro:hasRole> &nbsp; <a href="buy/${product.id }" class="list_op">购买</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>