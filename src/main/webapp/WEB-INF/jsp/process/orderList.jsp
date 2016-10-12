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
		<table width="800" cellspacing="0" cellPadding="0" id="listTable"
			border="1">
			<thead>
				<tr>
					<td>序号</td>
					<td>订单号</td>
					<td>购买人</td>
					<td>业务员</td>
					<td>产品名称</td>
					<td>产品描述</td>
					<td>产品价格</td>
					<td>订单状态</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orders }" var="order" varStatus="status">
					<tr>
						<td>${status.index+1 }</td>
						<td><a href="${order.id }" class="list_link">${order.order_no }</a></td>
						<td>${order.buyName }</a></td>
						<td>${order.salesMan }</a></td>
						<td>${order.productName }</a></td>
						<td>${order.description }</a></td>
						<td>${order.productPrice }</a></td>
						<td>${order.orderStatus }</a></td>
						<a href="updateOrder/${order.id }" class="list_op">更新状态</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>