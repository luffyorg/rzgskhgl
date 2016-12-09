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
<title>操作记录</title>
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

</style>

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
				管理中心<b>></b><strong>操作记录</strong>
			</div>
			<div class="mainBox"
				style="height: auto !important; height: 550px; min-height: 550px;">
				<h3>
					操作记录 
				</h3>
				<div><span id="msg"></span></div>
				<div class="navList">
 					<table width="100%" border="0" cellpadding="10" cellspacing="0"
						class="tableBasic">
						<tr >
							<th  align="center">序号</th>
							<th  align="center">用户</th>
							<th align="center">操作</th>
							<th align="center">IP</th>
							<th align="center">操作时间</th>
						</tr>
						
						<c:forEach items="${pb.list }" var="list" varStatus="i">
							<tr class="success">
								<td align="center">${i.count + (pb.currentPage-1)*10}</td>
								<td align="center" >${list[1].name}</td>
								<td align="center" >${list[0].record }</td>
								
								<td align="center" >${list[0].ipv4 }</td>
								<td align="center" >${list[0].time }</td>
							</tr>
						</c:forEach>
						
					</table>
				</div>
				<!-- 分页开始 -->
				<div style="float: right; margin-top: 15px;" class="splitPage"
					id="splitPage">
					<c:if test="${pb.currentPage==1 }">
						<a class='cursorauto'>首页</a>
					</c:if>
					<c:if test="${pb.currentPage!=1 }">
						<a onclick="nextPage(10,1);" class="cursorpointer">首页</a>
					</c:if>
					<c:if test="${pb.hasPreviousPage==true}">
						<a onclick="nextPage(10,${pb.currentPage-1});"
							class="cursorpointer"> ◄上一页</a>
					</c:if>
					<c:if test="${pb.hasPreviousPage==false}">
						<a class='cursorauto'> ◄上一页</a>
					</c:if>
					<c:if test="${pb.hasNextPage==true }">
						<a onclick="nextPage(10,${pb.currentPage+1});"
							class="cursorpointer">下一页► </a>
					</c:if>
					<c:if test="${pb.hasNextPage==false }">
						<a class='cursorauto'>下一页► </a>
					</c:if>
					<c:if test="${pb.totalPage==pb.currentPage }">
						<a class='cursorauto'>末页</a>
					</c:if>
					<c:if test="${pb.totalPage!=pb.currentPage }">
						<a onclick="nextPage(10,${pb.totalPage});" class="cursorpointer">末页</a>
					</c:if>
					总${pb.allRow }条，第${pb.currentPage}/${pb.totalPage }页，到第 <input
						id="goInput" value=''
						style="border: 1px solid #d8d8d8; width: 40px; height: 17px; line-height: 17px; text-align: center;" />页,
					<input type="button" class='cursorpointer' value="搜索"
						onclick="gotoPageByInput(${pb.currentPage},${pb.totalPage});" />
				</div>
				<!-- 分页结束 -->
			</div>
			
		</div>
		<!--主体内容部分结束-->
		
		<!--底部开始-->
		<%@ include file="../include/footer.jsp"%>
		<!--底部结束-->
		<div class="clear"></div>

	</div>
	<!--end-->
</body>

<script type="text/javascript">
//页面跳转
function nextPage(size,page){
	var beginTime = $("#beginTime").val();
	var endTime = $("#endTime").val();
	var userid = $("#userid").val();
	 //$.get("nextPage?pageSize="+size+"&page="+page+"&beginTime="+beginTime+"&endTime="+endTime+"&userid="+userid+"", function(data){
	 $.get("nextPage?pageSize="+size+"&page="+page+"", function(data){
		 //组装表格
		var htmlStr = "<table width='100%'  border='0' cellpadding='10' cellspacing='0' class='tableBasic'>";
		htmlStr += "<tr> <th width='80'>序号</th>"+
			      "<th width='80'>用户</th>"+
			      "<th width='80'>操作</th>"+
			      "<th width='80'>操作IP</th>"+
			      "<th width='80'>操作时间</th>";
      var pb=data.pb;
	    for(var i = 0; i < data.pb.list.length; i++){
	         var record = data.records[i];
	         var user = data.users[i];
	         htmlStr += "<tr><td align='center'>"+((pb.currentPage-1)*10+1+i)+" </td>"+
				"<td align='center'>"+user.name+" </td>"+
				"<td align='center'>"+record.record+" </td>"+
				"<td align='center'>"+record.ipv4+" </td>"+
				"<td align='center'>"+record.time+" </td></tr>";
	    }
	    //组装分页
	    var htmlPage = "<div style='float:right;margin-top:15px;' class='splitPage'>";
	   
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
	    htmlPage += " 总"+pb.allRow+"条，第"+pb.currentPage+"/"+pb.totalPage+" 页，到第"+
	   				"<input  id='goInput' value='' style='border:1px solid #d8d8d8;width:40px ;height:17px;line-height:17px;text-align:center;' />页,"+
					"<input type='button' value='搜索' class='cursorpointer' onclick='gotoPageByInput("+pb.currentPage+","+pb.totalPage+");' />"
	    htmlStr += "</table>";
	   
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
$("#opli").addClass("cur");
</script>
</html>
