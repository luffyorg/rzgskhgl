<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>角色管理</title>
<meta name="Copyright" content="Douco Design." />
</head>
<style type="text/css">
.updateColor{
	color:#05cc88;
	cursor:pointer;
}
.deleteColor{
color:#ee6450;
cursor:pointer;
}
.setReColor{
color:#ea8010;
cursor:pointer;
}


/**弹窗样式开始**/
.tc {
	width: 600px;
	height: 300px;
	display: none;
	position: absolute;
	margin: -600px auto;
	z-index: 999;
	background: #fff;
	left: 35%;
	border-radius: 5px;
}

.tc table {
	margin-top: 20px;
	margin-left: 20px;
}

.tc table tr {
	margin-top: 20px;
}
.tcUpdate {
	width: 600px;
	height: 300px;
	display: none;
	position: absolute;
	margin: -600px auto;
	z-index: 999;
	background: #fff;
	left: 35%;
	border-radius: 5px;
}

.tcUpdate table {
	margin-top: 20px;
	margin-left: 20px;
}

.tcUpdate table tr {
	margin-top: 20px;
}

.tc1 {
	width: 100%;
	height: 50px;
	background: #60BBFF;
	border-radius: 5px;
	font-size: 20px;
	color: #fff;
	text-align: center;
	line-height: 50px;
}

.tc1 img {
	width: 25px;
	height: 25px;
}

.mask {
	margin: 0;
	padding: 0;
	border: none;
	width: 100%;
	height: 100%;
	background: #333;
	opacity: 0.6;
	filter: alpha(opacity = 60);
	z-index: 99;
	position: fixed;
	top: 0;
	left: 0;
	display: block;
}
/**弹窗样式结束**/
</style>

<script type="text/javascript">
	/**弹窗效果开始**/
	function tc() {
		$("body").append("<div id='mask'></div>");
		$("#mask").addClass("mask").fadeIn("slow");
		$(".tc").fadeIn("slow");
	};
	function tcclose() {
		$(".tc").fadeOut("fast");
		$("#mask").css({
			display : 'none'
		});
	};
	function tcupclose() {
		$(".tcUpdate").fadeOut("fast");
		$("#mask").css({
			display : 'none'
		});
	};
	/**弹窗效果结束**/
	
</script>

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
			<div id="urHere">
				管理中心<b>></b><strong>用户管理</strong>
			</div>
			<div class="mainBox"
				style="height: auto !important; height: 550px; min-height: 550px;">
				<h3>
					<a href="add" class="actionBtn">添加用户</a>自定义用户
				</h3>
				<div class="navList">
					<table width="100%" border="0" cellpadding="10" cellspacing="0"
						class="tableBasic">
						<tr>
							<th>序号</th>
							<th>用户名称</th>
							<th>电话</th>
							<th>地址</th>
							<th>性别</th>
							<th>总资产</th>
							<th>总负债</th>
							<th>征信情况</th>
							<th>行业</th>
							<th>房产</th>
							<th>动产</th>
							<th>公司</th>
							<th>实体铺面</th>
							<th>用户状态</th>
							<th>用户操作</th>
						</tr>
						</thead>
						<tbody>
							<c:forEach items="${users }" var="user">
								<tr>
									<td align="center">${i.count + (pb.currentPage-1)*10}</td>
									<td align="center"><a href="${user.id }" class="list_link">${user.name }</a></td>
									<td align="center">${user.tel }</a></td>
									<td align="center">${user.address }</a></td>
									<td align="center">${user.gender }</a></td>
									<td align="center">${user.totalAssets }</a></td>
									<td align="center">${user.totalLiability }</a></td>
									<td align="center">${user.creditConditions }</a></td>
									<td align="center">${user.industry }</a></td>
									<td align="center"><c:if test="${user.estate eq 0 }">无</c:if> <c:if
											test="${user.estate eq 1 }">有</c:if></a></td>
									<td align="center"><c:if test="${user.movable eq 0 }">无</c:if> <c:if
											test="${user.movable eq 1 }">有</c:if></a></td>
									<td align="center"><c:if test="${user.company eq 0 }">无</c:if> <c:if
											test="${user.company eq 1 }">有</c:if></a></td>
									<td align="center"><c:if test="${user.solidSurfacing eq 0 }">无</c:if> <c:if
											test="${user.solidSurfacing eq 1 }">有</c:if></a></td>
									<td align="center"><c:if test="${user.isEnable eq 0 }">
											<span class="emp">停用</span>
											<a href="updateStatus/${user.id }" class="updateColor"> | 启用</a> 
										</c:if> <c:if test="${user.isEnable eq 1 }">
											<span>启用</span>
											<a href="updateStatus/${user.id }" class="updateColor"> | 停用</a>
										</c:if> &nbsp;</td>
									<td align="center"><shiro:hasRole name="ADMIN">
											<a href="${basePath }admin/user/update/${user.id }"
												class="updateColor">更新</a> | <a
												href="${basePath }admin/user/listRes/${user.id }" class="setReColor">查看资源</a>
										</shiro:hasRole></td>
								</tr>
							</c:forEach>
					</table>
				</div>
				<%@ include file="../include/pageSplit.jsp"%>
			</div>
		</div>
		<!--主体内容部分结束-->

		<!--弹窗开始 添加-->
		<div class="tc">
			<div class="tc1">
				资源添加<img src="${basePath}static/images/closed.png" onclick="tcclose()"
					style="float: right; margin-top: 15px; margin-right: 15px; cursor: pointer;">
			</div>
			<table>
				<tr>
					<td height="35" align="right">资源名称：</td>
					<td><input type="text" name="name" id="name" value=""
						size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">资源地址：</td>
					<td><input type="text" name="url" id="url" value="" size="80"
						class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">权限字符：</td>
					<td><input type="text" name="permission" id="permission"
						value="" size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td></td>
					<td><input id="saveBtn" class="btn" type="button" value="提交"
						onclick="saveRes();" /></td>
				</tr>
			</table>
		</div>
		<!--弹窗结束-->
		<!--弹窗开始 更新 -->
		<div class="tcUpdate">
			<div class="tc1">
				用户更新<img src="${basePath}static/images/closed.png" onclick="tcupclose()"
					style="float: right; margin-top: 15px; margin-right: 15px; cursor: pointer;">
			</div>
			<table>
				<tr>
					<td height="35" align="right">用户标识：</td>
					<td><input type="text" name="upId" id="upId" value=""
						size="80" class="inpMain" readonly="readonly"/></td>
				</tr>
				<tr>
					<td height="35" align="right">用户名称：</td>
					<td><input type="text" name="upName" id="upName" value=""
						size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">昵      称：</td>
					<td><input type="text" name="upNickName" id="upNickName" value=""
						size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">密      码：</td>
					<td><input type="text" name="upPassword" id="upPassword" value=""
						size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">电       话：</td>
					<td><input type="text" name="upTel" id="upurl" value="" size="80"
						class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">地      址：</td>
					<td><input type="text" name="upAddress" id="upAddress"
						value="" size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">性      别：</td>
					<td><input type="text" name="upGender" id="upGender"
						value="" size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">资      产：</td>
					<td><input type="text" name="upTotalAssets" id="upTotalAssets"
						value="" size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">负      债：</td>
					<td><input type="text" name="upTotalLiability" id="upTotalLiability"
						value="" size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">征信情况：</td>
					<td><input type="text" name="upCreditConditions" id="upCreditConditions"
						value="" size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">行      业：</td>
					<td><input type="text" name="upDustry" id="upDustry"
						value="" size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">房      产：</td>
					<td><input type="text" name="uppermission" id="uppermission"
						value="" size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">动      产：</td>
					<td><input type="text" name="uppermission" id="uppermission"
						value="" size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">公      司：</td>
					<td><input type="text" name="uppermission" id="uppermission"
						value="" size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">实体铺面：</td>
					<td><input type="text" name="uppermission" id="uppermission"
						value="" size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td></td>
					<td><input id="updateBtn" class="btn" type="button" value="提交"
						onclick="update();" /></td>
				</tr>
			</table>
		</div>
		<!--弹窗结束-->


		<!--底部开始-->
		<%@ include file="../include/footer.jsp"%>
		<!--底部结束-->
		<div class="clear"></div>

	</div>
	<!--end-->
</body>
<script type="text/javascript">
$("#productli").removeClass("cur");
$("#resli").removeClass("cur");
$("#roleli").removeClass("cur");
$("#userli").addClass("cur");
$("#indexli").removeClass("cur");
$("#opli").removeClass("cur");
$("#datali").removeClass("cur");
$("#adminli").removeClass("cur");</script>
</html>