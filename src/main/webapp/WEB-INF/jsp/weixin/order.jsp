<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>我的订单</title>
</head>
<body>
	<table width="100%" border="0" cellpadding="10" cellspacing="0"
		class="tableBasic">
		<thead>
			<tr>
				<td align="center">序号</td>
				<td align="center">订单号</td>
				<td align="center">购买人</td>
				<td align="center">业务员</td>
				<td align="center">产品名称</td>
				<td align="center">订单状态</td>
				<td align="center">创建时间</td>
				<td align="center">更新时间</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${orders }" var="order" varStatus="status">
				<tr>
					<td align='center'>${status.index+1 }</td>
					<td align='center'>${order.orderNo }</td>
					<td align='center'>${order.buyName }</td>
					<td align='center'>${order.salesMan }</td>
					<td align='center'>${order.productName }</td>
					<td align='center' id="status${order.id }"><c:if
							test="${order.orderStatus  eq 0 }">暂未更新</c:if> <c:if
							test="${order.orderStatus  eq 1 }">签订合同</c:if> <c:if
							test="${order.orderStatus  eq 2 }">收齐资料</c:if> <c:if
							test="${order.orderStatus  eq 3 }">递交渠道处</c:if> <c:if
							test="${order.orderStatus  eq 4 }">审核阶段</c:if> <c:if
							test="${order.orderStatus  eq 5 }">下款</c:if> <c:if
							test="${order.orderStatus  eq 6 }">收费</c:if> <c:if
							test="${order.orderStatus  eq 7 }">完成服务</c:if></td>
					<td align='center'>${order.createDate }</td>
					<td align='center'>${order.updateDate }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>