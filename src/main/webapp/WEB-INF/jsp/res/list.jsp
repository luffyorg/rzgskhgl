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
<script type="text/javascript" src="${basePath }jsp/js/res.js"></script>
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
.cursorpointer{
cursor:pointer;
}
.cursorauto{
cursor:Default;
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
				管理中心<b>></b><strong>资源管理</strong>
			</div>
			<div class="mainBox"
				style="height: auto !important; height: 550px; min-height: 550px;">
				<h3>
					<a onclick="tc()" class="actionBtn" style="cursor: pointer;">添加资源</a>资源管理 
				</h3>
				<div class="navList">
					<table width="100%" border="0" cellpadding="10" cellspacing="0"
						class="table table-striped">
						<tr>
							<th >序号</th>
							<th >资源名称</th>
							<th align="left">资源地址</th>
							<th align="left">权限字符串</th>
							<th >操作</th>
						</tr>
						<c:forEach items="${ress }" var="res" varStatus="i">
							<tr>
								<td >${i.count + (pb.currentPage-1)*10}</td>
								<td  id="resname">${res.name}</td>
								<td align="left" id="resurl">${res.url }</td>
								<td align="left" id="respermission">${res.permission }</td>
								<td ><a onclick="updateRes(this,${res.id});" class="updateColor">更新</a> | <a onclick="delRes(this,${res.id});" class="deleteColor">删除</a></td>
							</tr>
						</c:forEach>
					</table>

				</div>
				 <!-- 分页开始 -->
				<div style="float:right;margin-top:15px;" class="splitPage">
					<c:if test="${pb.currentPage==1 }">
						<a  class='cursorauto'>首页</a> 
					</c:if>
					<c:if test="${pb.currentPage!=1 }">
						<a onclick="nextPage(10,1);" class="cursorpointer">首页</a> 
					</c:if>
					<c:if test="${pb.hasPreviousPage==true}">
						<a onclick="nextPage(10,${pb.currentPage-1});"}" class="cursorpointer"> ◄上一页</a>
					</c:if>
					<c:if test="${pb.hasPreviousPage==false}">
						<a  class='cursorauto'> ◄上一页</a>
					</c:if>
					<c:if test="${pb.hasNextPage==true }">
						<a onclick="nextPage(10,${pb.currentPage+1});" class="cursorpointer">下一页► </a> 
					</c:if>
					<c:if test="${pb.hasNextPage==false }">
						<a class='cursorauto'>下一页► </a> 
					</c:if>
					<c:if test="${pb.totalPage==pb.currentPage }">
						<a  class='cursorauto'>末页</a> 
					</c:if>
					<c:if test="${pb.totalPage!=pb.currentPage }">
						<a onclick="nextPage(10,${pb.totalPage});" class="cursorpointer">末页</a> 
					</c:if>
					总${pb.allRow }条，第${pb.currentPage}/${pb.totalPage }页，到第
					<input  id="goInput" value='' style="border:1px solid #d8d8d8;width:40px ;height:17px;line-height:17px;text-align:center;" />页,
					<input type="button" class='cursorpointer'
						value="搜索" onclick="gotoPageByInput(${pb.currentPage},${pb.totalPage});" />
				</div>
				<!-- 分页结束 -->
			</div>

		</div>
		<!--主体内容部分结束-->
		<!--弹窗开始 -->
		<div class="tc">
			<div class="tc1">
				资源添加<img src="${basePath}static/images/closed.png" onclick="tcclose()"
					style="float: right; margin-top: 15px; margin-right: 15px; cursor: pointer;">
			</div>
			<table>
				<tr >
					<td height="35" width="25%" align="right">资源名称：</td>
					<td><input type="text" name="name" id="name" value=""
						size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" width="100px" align="right">资源地址：</td>
					<td><input type="text" name="url" id="url" value="" size="80"
						class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" width="100px" align="right">权限字符：</td>
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
				资源更新<img src="${basePath}static/images/closed.png" onclick="tcupclose()"
					style="float: right; margin-top: 15px; margin-right: 15px; cursor: pointer;">
			</div>
			<table>
				<tr style="display: none">
					<td height="35" width="100px" align="right">资源标识：</td>
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

function nextPage(size,page){
	 $.get("nextPage?pageSize="+size+"&page="+page+"", function(data){
		 //组装表格
		var htmlStr = "<table width='100%'  border='0' cellpadding='10' cellspacing='0' class='table table-striped'>";
		htmlStr += "<tr> <th >序号</th><th >资源名称</th><th align='left'>资源地址</th><th align='left'>权限字符串</th><th >操作</th></tr>";
	    for(var i = 0; i < data.ress.length; i++){
	         var res = data.ress[i];
	         htmlStr += "<tr><td  id='resid'>"+res.id+"</td><td  id='resname'>"+res.name+"</td><td  id='resurl'>"+res.url+"</td><td  id='respermission'>"+res.permission+"</td>";
	         htmlStr += "<td ><a onclick=updateRes(this,"+res.id+"); class='updateColor'>更新</a> | <a onclick=delRes(this,"+res.id+"); class='deleteColor'>删除</a></td></tr>";
	    }
	    //组装分页
	    var htmlPage = "<div style='float:right;margin-top:15px;' class='splitPage'>";
	    var pb=data.pb;
	    if(pb.currentPage==1){
	    	htmlPage += "<a  class='cursorauto'>首页</a> ";
	    }
	    else{
	    	htmlPage += "<a onclick='nextPage(10,1)' class='cursorpointer'>首页</a>";
	    }
	    if(pb.hasPreviousPage==true){
	    	htmlPage += "<a onclick='nextPage(10,"+(pb.currentPage-1)+")' class='cursorpointer'> ◄上一页 </a>";
	    }
	    else{
	    	htmlPage += "<a  class='cursorauto'>◄上一页 </a> ";
	    }
	    if(pb.hasNextPage==true){
	    	htmlPage += "<a onclick='nextPage(10,"+(pb.currentPage+1)+")' class='cursorpointer'> 下一页► </a>";
	    }
	    else{
	    	htmlPage += "<a  class='cursorauto'>下一页► </a> ";
	    }
	    if(pb.totalPage==pb.currentPage){
	    	htmlPage += "<a  class='cursorauto'> 末页</a> ";
	    }else{
	    	htmlPage += "<a onclick='nextPage(10,"+pb.totalPage+")' class='cursorpointer'> 末页</a> ";
	    }
	    
	    htmlStr += "</table>";
	    htmlPage += " 总"+pb.allRow+"条，第"+pb.currentPage+"/"+pb.totalPage+" 页，到第"+
			"<input  id='goInput' value='' style='border:1px solid #d8d8d8;width:40px ;height:17px;line-height:17px;text-align:center;' />页,"+
		"<input type='button' value='搜索' class='cursorpointer' onclick='gotoPageByInput("+pb.currentPage+","+pb.totalPage+");' />"
	    $(".navList").html(htmlStr);
	    $(".splitPage").html(htmlPage);
	   
	}) 
}



function gotoPageByInput(currentPage,totalpage){
	var page = $("#goInput").val();
	if(page<1 || page>totalpage){
		alert("请输入正确页码！");
	}else if(page==currentPage){
		
	}else
		nextPage(10,page);
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
<script type="text/javascript">
$(function(){
	$("#resli").addClass("cur");
})
</script>
</html>
