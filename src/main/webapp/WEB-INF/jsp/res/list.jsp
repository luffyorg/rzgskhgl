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
<title>资源管理</title>
<meta name="Copyright" content="Douco Design." />
<script type="text/javascript" src="${basePath }static/js/res.js"></script>
</head>
<style type="text/css">
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
				管理中心<b>></b><strong>资源管理</strong>
			</div>
			<div class="mainBox"
				style="height: auto !important; height: 550px; min-height: 550px;">
				<h3>
					<a onclick="tc()" class="actionBtn" style="cursor: pointer;">添加资源</a>资源管理
				</h3>
				<div class="navList">
					<table width="100%" border="0" cellpadding="10" cellspacing="0"
						class="tableBasic">
						<tr>
							<th align="center">资源名称</th>
							<th align="left">资源地址</th>
							<th align="left">权限字符串</th>
							<th align="center">操作</th>
						</tr>
						<c:forEach items="${ress }" var="res" varStatus="i">
							<tr>
								<td align="center" id="resname">${res.name}</td>
								<td align="left" id="resurl">${res.url }</td>
								<td align="left" id="respermission">${res.permission }</td>
								<td align="center"><input type="button" value="更新" onclick="updateRes(this,${res.id});"> | <input type="button" onclick="delRes(this,${res.id});" value="删除"></td>
							</tr>
						</c:forEach>
					</table>

				</div>
				<div>
					<c:if test="${pb.currentPage==1 }">
						<a href="#">首页</a> 
					</c:if>
					<c:if test="${pb.currentPage!=1 }">
						<a href="${basePath }admin/resource/list?pageSize=10&page=1">首页</a> 
					</c:if>
					<c:if test="${pb.hasPreviousPage==true}">
						<a href="${basePath }admin/resource/list?pageSize=10&page=${pb.currentPage-1}"> ◄上一页</a>
					</c:if>
					<c:if test="${pb.hasPreviousPage==false}">
						<a href="#"> ◄上一页</a>
					</c:if>
					<c:if test="${pb.hasNextPage==true }">
						<a href="${basePath }admin/resource/list?pageSize=10&page=${pb.currentPage+1}">下一页► </a> 
					</c:if>
					<c:if test="${pb.hasNextPage==false }">
						<a href="#">下一页► </a> 
					</c:if>
					<c:if test="${pb.totalPage==pb.currentPage }">
						<a href="#">末页</a> 
					</c:if>
					<c:if test="${pb.totalPage!=pb.currentPage }">
						<a href="${basePath }admin/resource/list?pageSize=10&page=${pb.totalPage}">末页</a> 
					</c:if>
					总${pb.allRow }条，第${pb.currentPage}/${pb.totalPage }页，到第
					<input size=2 id="goInput" value='' />页,<input type="button"
						value="搜索" class="goButton" onclick="gotoPageByInput(${pb.totalPage });" />
				</div>
			</div>

		</div>
		<!--主体内容部分结束-->
		<!--弹窗开始 -->
		<div class="tc">
			<div class="tc1">
				资源添加<img src="${basePath}images/close.png" onclick="tcclose()"
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
		<!--弹窗开始 -->
		<div class="tcUpdate">
			<div class="tc1">
				资源更新<img src="${basePath}images/close.png" onclick="tcupclose()"
					style="float: right; margin-top: 15px; margin-right: 15px; cursor: pointer;">
			</div>
			<table>
				<tr>
					<td height="35" align="right">资源标识：</td>
					<td><input type="text" name="upid" id="upid" value=""
						size="80" class="inpMain" readonly="readonly"/></td>
				</tr>
				<tr>
					<td height="35" align="right">资源名称：</td>
					<td><input type="text" name="upname" id="upname" value=""
						size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">资源地址：</td>
					<td><input type="text" name="upurl" id="upurl" value="" size="80"
						class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">权限字符：</td>
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
function gotoPageByInput(totalpage){
	var page = $("#goInput").val();
	if(page<1 || page>totalpage){
		alert("请输入正确页码！");
	}else
		window.location.href = "${basePath }admin/resource/list?pageSize=10&page="+page+"";
}
function delRes(obj,id){
	 if(confirm('确定要删除这条记录吗?')==true) 
	  { 
		 var tr=obj.parentNode.parentNode; 
			var tbody=tr.parentNode; 
			var sendInfo = {
					"id" : id
				};
			$.ajax({ 
				type : "post", 
				url : "del", 
				dataType : "json",
				contentType : 'application/json',
				data : JSON.stringify(sendInfo),
				success : function(data) { 
					if(data.msg="success"){
						tbody.removeChild(tr); 
					}
				}
			})
	  } 
	  return false; 
}
function updateRes(obj,id){
	 var name=$(obj).parents("tr").find("#resname").text()
	 var url=$(obj).parents("tr").find("#resurl").text()
	 var permission=$(obj).parents("tr").find("#respermission").text()
	 $("#upid").val(id);
	 $("#upname").val(name);
	 $("#upurl").val(url);
	 $("#uppermission").val(permission);
	 $("body").append("<div id='mask'></div>");
	 $("#mask").addClass("mask").fadeIn("slow");
	 $(".tcUpdate").fadeIn("slow");
}

function update(){
	var id = $("#upid").val();
	var name =  $("#upname").val();
	var url =	$("#upurl").val();
	var permission = $("#uppermission").val();
	var sendInfo = {
		"id" : id,
		"name" : name,
		"url" : url,
		"permission" : permission
	};
	
	$.ajax({
		type : "POST",
		url : "update",
		dataType : "json",
		contentType : 'application/json',
		data : JSON.stringify(sendInfo),
		success : function(data) {
			if (data.success == "success") {
				alert("更新成功！")
				window.location.href = "list";
			}  else {
				alert("更新失败！");
			}
		},
		error : function() {
			alert("网络异常，请稍后再试！");
		}
	});
}
</script>
</html>
