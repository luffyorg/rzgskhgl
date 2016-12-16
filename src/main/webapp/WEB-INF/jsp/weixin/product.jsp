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
<title>产品介绍</title>
<script type="text/javascript" src="${basePath }static/js/jquery.min.js"></script>
<style type="text/css">
body {
	padding: 0;
	margin: 0;
	background-color: #efeff4;
}

.Product {
	width: 100%;
	height: auto;
	float: left;
}

.ProductC_title, .ProductE_title {
	width: 92%;
	float: left;
	margin-left: 4%;
	margin-right: 4%;
	height: 120px;
	line-height: 120px;
	font-size: 48px;
	font-family: '微软雅黑';
	color: #333;
}

.ProductE_title {
	color: #999;
	line-height: 20px;
}

.ProductContent {
	float: left;
	margin-top: 40px;
	width: 92%;
	margin-left: 4%;
}

.ProductContentUl {
	float: left;
	margin-top: 40px;
	width: 90%;
}

ul, ul li {
	list-style: none;
}

.ProductList {
	width: 100%;
	height: 140px;
	margin-top: 10px;
	background-color: #CBD3E9;
	float: left;
	margin-left: 0;
	color: #fff;
	font-size: 46px;
	font-family: '微软雅黑';
	font-weight: bold;
	text-align: left;
	padding-left: 28px;
	line-height: 140px;
}

.active {
	background-color: #5A74B9;
}
</style>
</head>
<body>
	<div class="Product">
		<div class="ProductC_title">产品介绍</div>
		<div class="ProductE_title">Products</div>
		<div class="ProductContent">
			<ul class="ProductContentUl">
				<!--数据循环开始-->
				<c:forEach items="${products }" var="product" varStatus="i">
					<a
						href="search?id=${product.id }">
						<li class="ProductList " id="${product.id }">${product.name }</li>
					</a>
				</c:forEach>
			</ul>
		</div>
	</div>
</body>
</html>