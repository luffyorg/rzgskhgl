<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("path", path);
	request.setAttribute("basePath", basePath);
%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.8.0.min.js"></script>
</head>
<style type="text/css">
.updateColor {
	color: #05cc88;
	cursor: pointer;
}

.deleteColor {
	color: #ee6450;
	cursor: pointer;
}

.setReColor {
	color: #ea8010;
	cursor: pointer;
}
</style>
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
			<div id="urHere">产品购买</div>
			<div class="mainBox"
				style="height: auto !important; height: 550px; min-height: 550px;">
				<h3>产品</h3>
				<div class="navList">
					<table width="100%" border="0" cellpadding="10" cellspacing="0"
						class="tableBasic">
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
								<td>创建时间</td>
								<td>更新时间</td>
								<td>操作</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${orders }" var="order" varStatus="status">
								<tr>
									<td>${status.index+1 }</td>
									<td>${order.orderNo }</td>
									<td>${order.buyName }</td>
									<td>${order.salesMan }</td>
									<td>${order.productName }</td>
									<td>${order.description }</td>
									<td>${order.productPrice }</td>

									<td><c:if test="${order.orderStatus  eq 0 }">暂未更新</c:if> <c:if
											test="${order.orderStatus  eq 1 }">与客户签订合同</c:if> <c:if
											test="${order.orderStatus  eq 2 }">收齐资料</c:if> <c:if
											test="${order.orderStatus  eq 3 }">递交渠道处</c:if> <c:if
											test="${order.orderStatus  eq 4 }">审核阶段</c:if> <c:if
											test="${order.orderStatus  eq 5 }">下款</c:if> <c:if
											test="${order.orderStatus  eq 6 }">收费</c:if> <c:if
											test="${order.orderStatus  eq 7 }">完成服务</c:if></td>
											<td>${order.createDate }</td>
											<td>${order.updateDate }</td>
									<td><a  class="updateColor"
										onclick="updateOrder(${order.id });">更新状态</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
$(function() {
	  $("#updateOrder").hide();
});

</script>
<script type="text/javascript">
function updateOrder(id){
	alert(id);
	 $("#content").hide();
	$.post("updateOrder/"+id+"", function(data) {
		alert("===" + data);
		 $("#updateOrder").show();
		 $("#updateOrder").html(data);
		});
}

</script>
<jsp:include page="updateOrder.jsp"></jsp:include>

</html>