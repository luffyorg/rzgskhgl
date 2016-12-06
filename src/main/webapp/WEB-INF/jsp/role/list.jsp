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
<title>角色管理</title>
<meta name="Copyright" content="Douco Design." />
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

.tips {
	color: rgba(0, 0, 0, 0.5);
	padding-left: 10px;
}

.tips_true, .tips_false {
	padding-left: 10px;
}

.tips_true {
	color: green;
}

.tips_false {
	color: red;
}
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
</head>
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
				管理中心<b>></b><strong>角色管理</strong>
			</div>
			<div class="mainBox"
				style="height: auto !important; height: 550px; min-height: 550px;">
				<h3>
					<a onclick="tc()" class="actionBtn" style="cursor: pointer;">添加角色</a>自定义角色 
				</h3>
				<div><span id="msg"></span></div>
				<div class="navList">
 					<table width="100%" border="0" cellpadding="10" cellspacing="0"
						class="tableBasic">
						<tr >
							<th  align="center">序号</th>
							<th  align="center">角色名称</th>
							<th align="center">角色代码</th>
							
							<th align="center">创建人</th>
							<th align="center">创建时间</th>
							<th  align="center">更新人</th>
							<th  align="center">更新时间</th>
							<th  align="center">角色状态</th>
							<th  align="center">操作</th>
						</tr>
						<c:forEach items="${roles }" var="role" varStatus="i">
							<tr class="success">
								<td align="center">${i.count + (pb.currentPage-1)*10}</td>
								<td align="center" id="name">${role.name}</td>
								<td align="center" id="sn">${role.sn }</td>
								
								<td align="center" id="createName">${role.createName }</td>
								<td align="center" id="createDate">${role.createDate }</td>
								<td align="center" id="updateName">${role.updateName }</td>
								<td align="center" id="updateDate">${role.updateDate }</td>
								<%-- <td align="center"><c:if test="${role.isEnable eq 0 }">
											<span class="emp">停用  | </span>
											<a href="updateStatus/${role.id }"  class="updateColor">启用</a> 
										</c:if> <c:if test="${role.isEnable eq 1 }">
											<span>启用 | </span>
											<a href="updateStatus/${role.id }" class="updateColor">停用</a>
										</c:if> &nbsp;</td> --%>
								<td align="center" id="updateStatus${role.id }"><c:if
									test="${role.isEnable eq 0 }">
									<span class="stop" id="stop${role.id }">停用 | </span>
									<a onclick="updateStatus(${role.id },${role.isEnable});"
										class="updateColor" id="start${role.id }"> 启用</a>
								</c:if> <c:if test="${role.isEnable eq 1 }">
									<span class="start" id="start${role.id }">启用 | </span>
									<a onclick="updateStatus(${role.id },${role.isEnable});"
										class="updateColor" id="stop${role.id }"> 停用</a>
								</c:if></td>
								<td align="center"><a  onclick="updateRole(this,${role.id});" class="updateColor">更新</a> | 
								<a onclick="delRole(this,${role.id});" class="deleteColor">删除</a> |  
								<a href="listRes/${role.id}" class="setReColor" >设置资源</a>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
		<!--主体内容部分结束-->
		<!--弹窗开始 -->
		<div class="tc">
			<div class="tc1">
				角色添加<img src="${basePath}static/images/closed.png" onclick="tcclose()"
					style="float: right; margin-top: 15px; margin-right: 15px; cursor: pointer;">
			</div>
			<table>
				<tr>
					<td height="35" align="right">角色名称：</td>
					<td><input type="text" name="roleName" id="roleName" value=""
						size="80" class="inpMain" placeholder="内容为2~7个字符"  maxlength="10"  onkeyup="checkName()"/><span
						class="tips" id="divname">内容为2~7个字符</span></td>
				</tr>
				<tr>
					<td height="35" align="right">角色代码：</td>
					<td><input type="text" name="roleSn" id="roleSn" value="" size="80"
						class="inpMain" placeholder="如:ADMIN" maxlength="10" onkeyup="checkCode()" /><span
						class="tips" id="divcode">如:ADMIN</span></td>
				</tr>
				<tr>
					<td></td>
					<td><input id="saveBtn" class="btn" type="button" value="提交"
						onclick="save();" /></td>
				</tr>
			</table>
		</div>
		<!--弹窗结束-->
		
<!--弹窗开始 -->
		<div class="tcUpdate">
			<div class="tc1">
				角色更新<img src="${basePath}static/images/closed.png" onclick="tcupclose()"
					style="float: right; margin-top: 15px; margin-right: 15px; cursor: pointer;">
			</div>
			<table>
				<tr style="display: none">
					<td height="35" align="right">角色标识：</td>
					<td><input type="text" name="updateId" id="updateId" value=""
						size="80" class="inpMain" readonly="readonly"/></td>
				</tr>
				<tr>
					<td height="35" align="right">角色名称：</td>
					<td><input type="text" name="updateRoleName" id="updateRoleName" value=""
						size="80" class="inpMain" maxlength="7" onkeyup="checkNameUp()" /><span
						class="tips" id="divname1">内容为2~7个字符</span></td>
				</tr>
				<tr>
					<td height="35" align="right">角色代码：</td>
					<td><input type="text" name="updateSn" id="updateSn" value="" size="80"
						class="inpMain" maxlength="10" onkeyup="checkCodeUp()"/><span
						class="tips" id="divcode1">如：ADMIN</span></td>
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
function updateStatus(id,isEnable){
	$.get("updateStatus/"+id+"", function(result){
		$("#msg").html("");
		if(result.isEnable==1){
			$("#updateStatus"+id+"").html("<span class=start id=start"+id+">启用 | </span> <a onclick=updateStatus("+id+",0); class=updateColor id="+id+">停用</a>")
		}else {
			$("#updateStatus"+id+"").html("<span class=start id=start"+id+">停用 | </span> <a onclick=updateStatus("+id+",1); class=updateColor id="+id+">启用</a>")
		}
		$("#msg").html(result.msg);		
	  });
}


function save(){
	if(checkCode() && checkName()){
		var roleName = $("#roleName").val();
		var roleSn = $("#roleSn").val();
		var sendInfo = {
				"roleCode" : roleSn,
				"roleName" : roleName
			};

			$.ajax({
				type : "POST",
				url : "save",
				dataType : "json",
				contentType : 'application/json',
				data : JSON.stringify(sendInfo),
				success : function(data) {
					if (data.success == "success") {
						alert("保存成功！")
						window.location.href = "list";
					}  else {
						alert("保存失败！");
					}
				},
				error : function() {
					alert("网络异常，请稍后再试！");
				}
			});
	}else{
		alert("请核对输入是否正确")
	}
	
}
function checkName(){
	var name = $("#roleName").val();
	var patrn = /^[\u4e00-\u9fa5]{2,7}$/;
	if (!patrn.exec(name)) {  	
			divname.innerHTML='<font class="tips_false">内容为2-7个汉字</font>';
			return false;
		}else{  
			$.get("checkName?name="+name+"", function(msg){
				if(msg=="success"){
					divname.innerHTML='<font class="tips_false">角色已存在</font>';
				}else{
					divname.innerHTML='<font class="tips_true">正确</font>';
					$("#divname").addClass('tips_true');
				}
			});
			if($("#divname").hasClass("tips_true")){
				return true;
			}else
				return false;
		}  
}
function checkCode(){
	var sn = $("#roleSn").val();
	$("#roleSn").val(sn.toUpperCase());
	
	var patrn = /^[a-zA-Z]+$/;
	if (!patrn.exec(sn)) 
		{  	
			divcode.innerHTML='<font class="tips_false">格式：ADMIN</font>';
			return false;
		}else{  
			divcode.innerHTML='<font class="tips_true">正确</font>';
			return true;
		}  
}
function checkNameUp(){
	var name = $("#updateRoleName").val();
	var patrn = /^[\u4e00-\u9fa5]{2,7}$/;
	if (!patrn.exec(name)) {  	
			divname1.innerHTML='<font class="tips_false">内容为2-7个汉字</font>';
			return false;
		}else{  
			divname1.innerHTML='<font class="tips_true">正确</font>';
			return true;
		}  
}
function checkCodeUp(){
	var sn = $("#updateSn").val();
	$("#updateSn").val(sn.toUpperCase());
	
	var patrn = /^[a-zA-Z]+$/;
	if (!patrn.exec(sn)) 
		{  	
			divcode1.innerHTML='<font class="tips_false">格式：ADMIN</font>';
			return false;
		}else{  
			divcode1.innerHTML='<font class="tips_true">正确</font>';
			return true;
		}  
}
function delRole(obj,id){
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
					else 
						alert("删除失败！");
				}
			})
	  } 
	  return false; 
}
function updateRole(obj,id){
	 var name=$(obj).parents("tr").find("#name").text()
	 var sn=$(obj).parents("tr").find("#sn").text()
	 $("#updateRoleName").val(name);
	 $("#updateSn").val(sn);
	 $("#updateId").val(id);
	 $("body").append("<div id='mask'></div>");
	 $("#mask").addClass("mask").fadeIn("slow");
	 $(".tcUpdate").fadeIn("slow");
}

function update(){
	if(checkCodeUp() && checkNameUp()){
		var id = $("#updateId").val();
		var name =   $("#updateRoleName").val();
		var sn =	$("#updateSn").val();
		var sendInfo = {
			"id" : id,
			"roleName" : name,
			"roleCode" : sn
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
	}else{
		alert("请核对输入是否正确");
	}
}
</script>
<script type="text/javascript">
$("#roleli").addClass("cur");
</script>
</html>
