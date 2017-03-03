<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>首页</title>
<meta name="Copyright" content="Douco Design." />

</head>
<body>
	<!--
      作者：z.y.q.d
      时间：2016-10-21
      描述：后台管理
    -->
	<!--start-->
	<div id="dcWrap">
		<!--头部导航开始-->
		<%@ include file="include/header.jsp"%>
		<!--头部导航结束-->

		<!--左侧边栏导航开始-->
		<%@ include file="include/leftMenu.jsp"%>
		<!--左侧边栏导航结束-->

		<!--主体内容部分开始-->
		<div id="dcMain">
			<!-- 当前位置 -->
			<div id="urHere">个人中心</div>
			<div id="index" class="mainBox"
				style="padding-top: 18px; height: auto !important; height: 550px; min-height: 550px;">
				<div id="douApi"></div>
				
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="indexBoxTwo">
					<tr>
						<td width="65%" valign="top" class="pr">
							<div class="indexBox">
									<table width="100%" border="0" cellspacing="0" cellpadding="7"
										class="table">
										<caption>最近订单信息</caption>
										<tr>
											<td width="120">订单号：</td>
											<td><strong>12132323232323</strong></td>
											<td width="100">购买人：</td>
											<td><strong>张伟</strong></td>
										</tr>
										<tr>
											<td>产品名称：</td>
											<td><strong>小额担</strong></td>
											<td>订单状态：</td>
											<td><strong>审核</strong></td>
										</tr>
										<tr>
											<td>创建时间：</td>
											<td><strong>2017-1-2
											</strong></td>
											<td>更新时间：</td>
											<td><strong>2017-1-3</strong></td>
										</tr>
									</table>
							</div>
						</td>
						<td valign="top" class="pl">
							<div class="table-responsive">
									<table width="100%" border="0" cellspacing="0" cellpadding="7"
										class="table">
										 <caption align="top">最近三月订单量统计</caption>
										<tr>
											<th width="45%" class="active">年月</th>
											<th width="55%" class="active">数量</th>
										</tr>
										<tr>
											<td >2017-1</td>
											<td >12</td>
										</tr>
										<tr>
											<td >2016-12</td>
											<td >6</td>
										</tr>
										<tr>
											<td >2016-11</td>
											<td >8</td>
										</tr>
									</table>
							</div>
						</td>
					</tr>
				</table>
				

			</div>
		</div>
		<!--主体内容部分结束-->
		<div class="clear"></div>
		<!--底部开始-->
			<%@ include file="include/footer.jsp"%>
		<!--底部结束-->
		<div class="clear"></div>

	</div>
	<!--end-->

</body>
<script type="text/javascript">
$("#indexli").addClass("cur");
</script>
</html>
