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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${basePath }static/js/jquery.min.js"></script>
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
									<td><c:if test="${product.solidSurfacing eq 0 }">无</c:if>
										<c:if test="${product.solidSurfacing eq 1 }">有</c:if></a></td>
									<td><c:if test="${product.isEnable eq 0 }">
							下架
							</c:if> <c:if test="${product.isEnable eq 1 }">
								上架
							</c:if></td>
									<td><shiro:hasRole name="ADMIN">
											<a href="queryBuyUser/${product.id }" class="updateColor">搜索</a>
										</shiro:hasRole> &nbsp; <a href="buy/${product.id }" class="setReColor">购买</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>