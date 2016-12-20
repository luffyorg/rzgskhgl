<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>产品详情</title>
<script type="text/javascript" src="${basePath }static/js/jquery.min.js"></script>
<style type="text/css">
/*
* 产品详情页面css
*/
body {
	margin: 0;
	padding: 0;
	background-color: #fff;
}

.ProDetail_title {
	width: 92%;
	float: left;
	margin-left: 4%;
	margin-right: 4%;
	height: 120px;
	line-height: 120px;
	font-size: 52px;
	font-family: '微软雅黑';
	color: #666;
	margin-top: 40px;
}

.ProDetail {
	float: left;
	width: 92%;
	margin-left: 4%;
	margin-right: 4%;
	height: auto;
	margin-top: 0;
}

.productLabel, .objectLabel, .conditionLabel, .progressLabel,
	.priceLabel, .warnLabel {
	width: 100%;
	height: 100px;
	line-height: 100px;
	color: #5A74B9;
	font-size: 48px;
	font-family: '微软雅黑';
	font-weight: bold;
	margin-top: 40px;
}

.productContent, .objectContent, .conditionContent, .progressContent,
	.priceContent, .warnContent {
	width: 100%;
	height: auto;
	line-height: 80px;
	color: #353535;
	font-size: 30px;
	font-family: '微软雅黑';
}
</style>
</head>
<body>
	<div class="ProDetail_title">${product.name }</div>
	<div class="ProDetail">
		<div class="productLabel">产品简介</div>
		<div class="productContent">&nbsp;&nbsp;&nbsp;&nbsp;${product.description }</div>
		<div class="objectLabel">适用对象</div>
		<div class="objectContent">&nbsp;&nbsp;&nbsp;&nbsp;${product.suitable }</div>
		<div class="conditionLabel">办理条件</div>
		<div class="conditionContent">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<c:if test="${product.estate==1 }">房产 </c:if>
			<c:if test="${product.movable==1 }">动产 </c:if> 
			<c:if test="${product.company==1 }">公司 </c:if> 
			<c:if test="${product.solidSurfacing==1 }">实体铺面 </c:if> 
		</div>
		<div class="progressLabel">业务流程</div>
		<div class="progressContent">&nbsp;&nbsp;&nbsp;&nbsp;签订合同-收齐资料-递交渠道处-审核阶段-下款-收费-完成服务</div>
		<div class="priceLabel">产品定价</div>
		<div class="priceContent">&nbsp;&nbsp;&nbsp;&nbsp;${product.productPrice }</div>
		<!-- <div class="warnLabel">注意事项</div>
            <div class="warnContent">&nbsp;&nbsp;&nbsp;&nbsp;可是地方过分的话放大镜看了很久的更好看了几个来回</div> -->
	</div>
</body>

</html>