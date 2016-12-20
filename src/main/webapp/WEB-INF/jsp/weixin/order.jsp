<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>我的订单</title>
<script type="text/javascript" src="${basePath}static/js/jquery.min.js"></script>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	background-color: #fff;
}

.publicSearch {
	width: 100%;
	height: 100px;
	float: left;
	background-color: #EFEFF4;
}

.publicSearchId, .publicSearchStatus {
	width: 42.5%;
	height: 80px;
	border-radius: 10px;
	float: left;
	margin-left: 5%;
	margin-right: 5%;
	background-color: #fff;
	margin-top: 10px
}

.publicSearchId {
	float: left;
	margin-left: 5%;
	margin-right: 0;
}

.icon_search {
	float: left;
	margin-top: 30px;
	margin-left: 16px;
	width: 20px;
	height: 20px;
}

.SearchId_inp, .SearchStatus_inp {
	width: 80%;
	float: left;
	margin-left: 0px;
	margin-top: 3px;
	height: 70px;
	border: none;
	padding-left: 14px;
	font-size: 32px;
	color: #353535;
	font-family: '微软雅黑';
}

input::-webkit-input-placeholder {
	font-size: 32px;
	color: #ccc;
	font-family: '微软雅黑';
}

input::-moz-placeholder {
	font-size: 32px;
	color: #ccc;
	font-family: '微软雅黑';
}

.publicBtn {
	float: left;
	width: 92%;
	margin-left: 4%;
	margin-right: 4%;
	margin-top: 10px;
	height: 100px;
}

.btnSelect {
	width: 100%;
	height: 100px;
	background-color: #EDB400;
	color: #ffffff;
	font-size: 42px;
	font-family: '微软雅黑';
	border: none;
	border-radius: 16px;
}

.publicDetail {
	float: left;
	width: 92%;
	margin-left: 4%;
	margin-right: 4%;
	margin-top: 0px;
	height: auto;
}

.DetailId_control, .DetailName_control, .DetailPrice_control,
	.DetailDescription_control, .DetailStatus_control {
	float: left;
	height: auto;
	width: 100%;
	margin-top: 20px;
}

.DetailCreateTime_control, .DetailUpdateTime_control {
	float: left;
	height: auto;
	width: 50%;
	margin-top: 20px;
}

.DetailId_control {
	background-color: #efeff4;
	border-radius: 6px;
}

.DetailDescription_control {
	height: auto;
}

.DetailId_label, .DetailName_label, .DetailPrice_label,
	.DetailDescription_label, .DetailStatus_label, .DetailCreateTime_label,
	.DetailUpdateTime_label {
	float: left;
	line-height: 70px;
	color: #353535;
	margin-left: 0;
	font-size: 34px;
	font-family: '微软雅黑';
	width: auto;
	text-align: center;
}

.DetailDescription_label {
	line-height: 50px;
}

.DetailId, .DetailName, .DetailPrice, .DetailDescription, .DetailStatus,
	.DetailCreateTime, .DetailUpdateTime {
	width: 80%;
	float: left;
	line-height: 70px;
	color: #353535;
	margin-left: 0;
	font-size: 34px;
	font-family: '微软雅黑';
	text-align: left;
}

.DetailDescription {
	height: auto;
	line-height: 50px;
}

.top {
	position: fixed;
	top: 0;
	height: auto;
	width: 100%;
	z-index: 99;
	background-color: #efeff4;
}

.publicDetail_control {
	margin-top: 220px;
}
</style>
</head>
<body>
	<div class="top">
		<div class="publicSearch">
			<div class="publicSearchId">
				<img src="${basePath }static/images/icon_search@2x.png" height="14" width="14"
					class="icon_search" /> <input class="SearchId_inp"
					placeholder="订单号" id="orderNo" />
			</div>
			<div class="publicSearchStatus">
				<img src="${basePath }static/images/icon_search@2x.png" height="14" width="14"
					class="icon_search" />
				<!-- <input list="status"  /> -->
				<select class="SearchStatus_inp" id="orderStatus" placeholder="订单状态">
					<option value="0">全部</option>
					<option value="1">签订合同</option>
					<option value="2">收齐资料</option>
					<option value="3">递交渠道处</option>
					<option value="4">审核阶段</option>
					<option value="5">下款</option>
					<option value="6">收费</option>
					<option value="7">完成服务</option>
				</select>
			</div>
		</div>
		<div class="publicBtn">
			<button class="btnSelect" onclick="searchOrder();">筛选</button>
		</div>
	</div>
	
		<div class="publicDetail_control">
			<div class="publicDetail">
			<c:forEach items="${orders }" var="order" varStatus="status">
				<div class="DetailId_control">
					<div class="DetailId_label">订&nbsp;单&nbsp;号&nbsp;:</div>
					<div class="DetailId">${order.orderNo }</div>
				</div>
				<div class="DetailName_control">
					<div class="DetailName_label">产品名称:</div>
					<div class="DetailName">${order.productName }</div>
				</div>
				<%-- <div class="DetailPrice_control">
				<div class="DetailPrice_label">产品定价:</div>
				<div class="DetailPrice">${order.productPrice }</div>
			</div> --%>

				<div class="DetailStatus_control">
					<div class="DetailStatus_label">订单状态:</div>
					<div class="DetailStatus">
						<c:if test="${order.orderStatus  eq 0 }">暂未更新</c:if>
						<c:if test="${order.orderStatus  eq 1 }">签订合同</c:if>
						<c:if test="${order.orderStatus  eq 2 }">收齐资料</c:if>
						<c:if test="${order.orderStatus  eq 3 }">递交渠道处</c:if>
						<c:if test="${order.orderStatus  eq 4 }">审核阶段</c:if>
						<c:if test="${order.orderStatus  eq 5 }">下款</c:if>
						<c:if test="${order.orderStatus  eq 6 }">收费</c:if>
						<c:if test="${order.orderStatus  eq 7 }">完成服务</c:if>
					</div>
				</div>
				<div class="DetailCreateTime_control">
					<div class="DetailCreateTime_label">创建时间:</div>
					<div class="DetailCreateTime">${order.createDate}</div>
				</div>
				<div class="DetailUpdateTime_control">
					<div class="DetailUpdateTime_label">更新时间:</div>
					<div class="DetailUpdateTime">${order.updateDate}</div>
				</div>
				</c:forEach>
			</div>
		</div>
	
</body>
<script type="text/javascript">
	function searchOrder() {
		var orderNo = $("#orderNo").val();
		var orderStatus = $("#orderStatus").val();
		$
				.get(
						"searchOrder?orderNo=" + orderNo + "&orderStatus="
								+ orderStatus + "",
						function(data) {
							var htmlStr = "";
							for (var i = 0; i < data.orders2.length; i++) {
								var order = data.orders2[i];
								htmlStr += "<div class='DetailId_control'> <div class='DetailId_label'>订&nbsp;单&nbsp;号&nbsp;:</div> "
										+ "<div class='DetailId'>"
										+ order.orderNo
										+ "</div> </div> <div class='DetailName_control'>"
										+ "<div class='DetailName_label'>产品名称:</div>"
										+ "<div class='DetailName'>"
										+ order.productName
										+ "</div> </div><div class='DetailStatus_control'>"
										+ "<div class='DetailStatus_label'>订单状态:</div>"
										+ "<div class='DetailStatus'>";
										if(order.orderStatus == 1){
											htmlStr += "签订合同";
										}else if (order.orderStatus == 2){
											htmlStr += "收齐资料";
										}else if (order.orderStatus == 3){
											htmlStr += "递交渠道处";
										}else if (order.orderStatus == 4){
											htmlStr += "审核阶段";
										}else if (order.orderStatus == 5){
											htmlStr += "下款";
										}else if (order.orderStatus == 6){
											htmlStr += "收费";
										}else if (order.orderStatus == 7){
											htmlStr += "完成服务";
										}else{
											htmlStr += "暂未更新";
										}
										htmlStr +="</div>"
										+ "</div>"
										+ "<div class='DetailCreateTime_control'>"
										+ "<div class='DetailCreateTime_label'>创建时间:</div>"
										+ "<div class='DetailCreateTime'>"
										+ order.createDate
										+ "</div>"
										+ "</div>"
										+ "<div class='DetailUpdateTime_control'>"
										+ "<div class='DetailUpdateTime_label'>更新时间:</div>"
										+ "<div class='DetailUpdateTime'>"
										+ order.updateDate
										+ "</div>"
										+ "</div>";
							}
							$(".publicDetail").html(htmlStr);
						});
	}
</script>
</html>