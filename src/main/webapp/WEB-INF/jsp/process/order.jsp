<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>产品购买</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.8.0.min.js"></script>
</head>
<body>
	<div id="content">
		<h3 class="admin_link_bar">
			<jsp:include page="inc.jsp"></jsp:include>
		</h3>
		<sf:form action="add" method="post" modelAttribute="order"
			id="addForm">
			<table width="800" cellspacing="0" cellPadding="0">
				<tr>
					<td class="rightTd" width="200px">产品名称:</td>
					<td class="leftTd"><sf:input path="productName" size="30" /></td>
				</tr>

				<tr>
					<td class="rightTd">产品价格:</td>
					<td><sf:input path="productPrice" size="30" /></td>
				</tr>
				<tr>
					<td class="rightTd">产品描述:</td>
					<td><sf:input path="description" size="30" /></td>
				</tr>
				<tr>
					<td class="rightTd">购买人:</td>
					<td>
							<sf:input type="text" list="user_list" name="link" path="buyName"/>
							<datalist id="user_list"> <c:forEach items="${users }"
								var="user">
								<option value="${user.name }">${user.name }</option>
							</c:forEach> </datalist>
						</td>

				</tr>

				<tr>
					<td class="rightTd">订单状态:</td>
					<td class="leftTd"><sf:select path="orderStatus">
							<sf:option value="0">请选择</sf:option>
							<sf:option value="1">签订合同</sf:option>
							<sf:option value="2">收齐资料</sf:option>
							<sf:option value="3">递交渠道处</sf:option>
							<sf:option value="4">审核阶段</sf:option>
							<sf:option value="5">下款</sf:option>
							<sf:option value="6">收费</sf:option>
							<sf:option value="7">完成服务</sf:option>
						</sf:select></td>
				</tr>
				<tr>
					<td></td>
					<td><button type="button" onclick="save();">提交</button></td>
				</tr>
			</table>
		</sf:form>
	</div>
</body>
<script type="text/javascript">
	function save() {
		var username = $("#buyName").val();
		if (username == "") {
			alert("请选择客户");
		} else {
			$("#addForm").submit();
		}

	}
</script>
</html>