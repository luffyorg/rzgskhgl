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
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${basePath }static/js/jquery.min.js"></script>
<script type="text/javascript" src="${basePath }static/js/echarts.js"></script>
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

.cursorpointer {
	cursor: pointer;
}

.cursorauto {
	cursor: Default;
}
/**弹窗样式开始**/
.tc {
	width: 600px;
	height: 300px;
	display: none;
	position: absolute;
	margin: -300px auto;
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
.inp_name {
	width: 120px;
	height: 30px;
	line-height: 30px;
	border: 1px solid #d8d8d8;
	border-radius: 4px;
	font-size: 13px;
	color: #333;
	font-family: '微软雅黑';
	margin-left: 10px;
	padding: 0 14px;
	margin-top: 8px;
}

.inp_btn {
	font-size: 16px;
	color: #fff;
	font-family: "微软雅黑";
	height: 30px;
	width: 60px;
	line-height: 30px;
	text-align: center;
	border: none;
	border-radius: 4px;
	background-color: #5096fa;
	margin-top: 8px;
	margin-left: 15px;
	cursor: pointer;
}

.inp_btn:hover {
	background-color: #3c87f0;
}
</style>

<script type="text/javascript">
	/**弹窗效果开始**/
	function tc(orderNo, buyName, productName, status) {
		$("#orderNo").val(orderNo);
		$("#productName").val(productName);
		$("#buyName").val(buyName);
		var selectTag = document.getElementById("orderStatus");
		var options = selectTag.getElementsByTagName("option");
		for (var i = 0; i < options.length; i++) {
			var value = options[i].value;
			if (value == status) {
				options[i].setAttribute("selected", "true");
			}
		}

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
			<div id="urHere">订单列表</div>
			<div class="mainBox"
				style="height: auto !important; height: 550px; min-height: 550px;">
				<h3>
					<a onclick="excelOrder();" class="actionBtn"
						style="cursor: pointer;">导出excel</a>订单 <input type="text"
						class="inp_name" placeholder="订单号" id="searchOrderNo" /> <input
						type="button" value="搜索" class="inp_btn" id="search"
						onclick="search();" />
				</h3>
				<div id="msg"></div>
				<div class="navList" id="navList">
					<table width="100%" border="0" cellpadding="10" cellspacing="0"
						class="tableBasic">
						<thead>
							<tr>
								<td>序号</td>
								<td>订单号</td>
								<td>购买人</td>
								<td>业务员</td>
								<td>产品名称</td>
								<td>订单状态</td>
								<td>创建时间</td>
								<td>更新时间</td>
								<td>操作</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${orders }" var="order" varStatus="status">
								<tr>
									<td>${status.index+1 }</td>
									<td>${order.orderNo }</td>
									<td>${order.buyName }</td>
									<td>${order.salesMan }</td>
									<td>${order.productName }</td>
									<td id="status${order.id }"><c:if
											test="${order.orderStatus  eq 0 }">暂未更新</c:if> <c:if
											test="${order.orderStatus  eq 1 }">签订合同</c:if> <c:if
											test="${order.orderStatus  eq 2 }">收齐资料</c:if> <c:if
											test="${order.orderStatus  eq 3 }">递交渠道处</c:if> <c:if
											test="${order.orderStatus  eq 4 }">审核阶段</c:if> <c:if
											test="${order.orderStatus  eq 5 }">下款</c:if> <c:if
											test="${order.orderStatus  eq 6 }">收费</c:if> <c:if
											test="${order.orderStatus  eq 7 }">完成服务</c:if></td>
									<td>${order.createDate }</td>
									<td>${order.updateDate }</td>
									<td><c:if test="${order.isEnable  eq 1 }">
											<a class="updateColor"
												onclick="tc('${order.orderNo }','${order.buyName }','${order.productName }','${order.orderStatus }');">更新状态</a>
										</c:if> <c:if test="${order.isEnable  eq 0 }">
											<a>已失效</a>
										</c:if></td>
								</tr>
							</c:forEach>
						</tbody>
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
			<!--主体内容部分结束-->
			<!--弹窗开始 -->
			<div class="tc">
				<div class="tc1">
					更新状态<img src="${basePath}static/images/closed.png"
						onclick="tcclose()"
						style="float: right; margin-top: 15px; margin-right: 15px; cursor: pointer;">
				</div>
				<table>
					<tr>
						<td height="35" width="25%" align="right">订单号：</td>
						<td><input type="text" name="orderNo" id="orderNo" value=""
							size="80" class="inpMain" readonly="readonly" /></td>
					</tr>
					<tr>
						<td height="35" width="25%" align="right">产品名称：</td>
						<td><input type="text" name="productName" id="productName"
							value="" size="80" class="inpMain" readonly="readonly" /></td>
					</tr>
					<tr>
						<td height="35" width="100px" align="right">购买人：</td>
						<td><input type="text" name="buyName" id="buyName" value=""
							size="80" class="inpMain" readonly="readonly" /></td>
					</tr>
					<tr>
						<td height="35" width="100px" align="right">订单状态：</td>
						<td><select id="orderStatus">
								<option value="1">签订合同</option>
								<option value="2">收齐资料</option>
								<option value="3">递交渠道处</option>
								<option value="4">审核阶段</option>
								<option value="5">下款</option>
								<option value="6">收费</option>
								<option value="7">完成服务</option>
						</select></td>
					</tr>
					<tr>
						<td></td>
						<td><input id="saveBtn" class="btn" type="button" value="提交"
							onclick="updateOrder();" /></td>
					</tr>
				</table>
			</div>
			<!--弹窗结束-->
			<div id="s1" class="scatter" name="dten" style="display: block;">
				<div style="margin: 20px 0; height: 325px; width: 946px;" id="main"></div>

			</div>
		</div>

	</div>

	</div>
	<!--底部开始-->
	<%@ include file="../include/footer.jsp"%>
	<!--底部结束-->
	<div class="clear"></div>
</body>
<script type="text/javascript">
	
</script>
<script type="text/javascript">
	function updateOrder() {
		var orderNo = $("#orderNo").val();
		var status = $("#orderStatus").val();
		$.post("updateOrder?orderNo=" + orderNo + "&status=" + status + "",
				function(data) {
					if (data.msg == "success") {
						if (data.status == 1) {
							$("#status" + data.id + "").html("签订合同");
						} else if (data.status == 2) {
							$("#status" + data.id + "").html("收齐资料");
						} else if (data.status == 3) {
							$("#status" + data.id + "").html("递交渠道处");
						} else if (data.status == 4) {
							$("#status" + data.id + "").html("审核阶段");
						} else if (data.status == 5) {
							$("#status" + data.id + "").html("下款");
						} else if (data.status == 6) {
							$("#status" + data.id + "").html("收费");
						} else {
							$("#status" + data.id + "").html("完成服务");
						}

						tcclose();
						window.location.href = "orderList";
					}
				});
	}
	function excelOrder() {
		$.ajax({
			type : "POST",
			url : "exportOrder",
			success : function(data) {
				window.open('exportOrder');
			}

		});
	}
	 function search(){
			var orderNo = $("#searchOrderNo").val();
			 $.get("search?pageSize="+10+"&page="+1+"&orderNo="+orderNo+"", function(data){
				 //组装表格
				var htmlStr = "<table width='100%'  border='0' cellpadding='10' cellspacing='0' class='tableBasic'>";
				htmlStr += "<tr> <th width='80'>序号</th>"+
		        "<th width='80'>订单号</th>"+
		        "<th width='80'>购买人</th>"+
		        "<th width='80'>业务员</th>"+
		        "<th width='80'>产品名称</th>"+
		        "<th width='80'>订单状态</th>"+
		        "<th width='80'>创建时间</th>"+
		        "<th width='80'>更新时间</th>"+
		        "<th width='80'>操作</th></tr>";
		        var pb=data.pb;
			    for(var i = 0; i < data.pb.list.length; i++){
			         var order = data.pb.list[i];
			         htmlStr += "<tr><td align='center'>"+((pb.currentPage-1)*10+1+i)+" </td>"+
						"<td align='center'>"+order.orderNo+" </td>"+
						"<td align='center'>"+order.buyName+" </td>"+
						"<td align='center'>"+order.salesMan+" </td>"+
						"<td align='center'>"+order.productName+" </td>"+
						"<td align='center'>"+order.orderStatus+" </td>"+
						"<td align='center'>"+order.createDate+" </td>"+
						"<td align='center'>"+order.updateDate+" </td>"
			         htmlStr += "<td align='center'><a onclick=updateRes(this,"+order.id+"); class='updateColor'>更新</a></td></tr>";
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
			   
			    $("#navList").html(htmlStr);
			    $("#splitPage").html(htmlPage);
			   
			}) 
	 }
	//页面跳转
	 function nextPage(size,page){
	 	var name = $("#searchName").val();
	 	 $.get("nextPage?pageSize="+size+"&page="+page+"&name="+name+"", function(data){
	 		 //组装表格
	 		var htmlStr = "<table width='100%'  border='0' cellpadding='10' cellspacing='0' class='tableBasic'>";
	 		htmlStr += "<tr> <th width='80'>序号</th>"+
	 			      "<th width='80'>登录名称</th>"+
	 			      "<th width='80'>电话</th>"+
	 			      "<th width='80'>用户状态</th>"+
	 			      "<th width='80'>操作</th></tr>";
	       var pb=data.pb;
	 	    for(var i = 0; i < data.users.length; i++){
	 	         var user = data.users[i];
	 	         htmlStr += "<tr><td align='center'>"+((pb.currentPage-1)*10+1+i)+" </td>"+
	 				"<td align='center'>"+user.name+" </td>"+
	 				"<td align='center'>"+user.tel+" </td>";
	 				
	 				if(user.isEnable==0){
	 					htmlStr += "<td align='center' id='updateStatus"+user.id +"'>"+
	 								"<span class='stop' id='stop"+user.id +"'>停用 | </span>"+
	 								"<a onclick=updateStatus("+user.id +","+user.isEnable+"); "+
	 								"class='updateColor' id='start"+user.id +"''> 启用</a></td>";
	 				}else{
	 					htmlStr += "<td align='center' id='updateStatus"+user.id +"'>"+
	 							"<span class='stop' id='stop"+user.id +"'>启用 | </span>"+
	 							"<a onclick=updateStatus("+user.id +","+user.isEnable+"); "+
	 							"class='updateColor' id='start"+user.id +"''> 停用</a></td>";
	 				}
	 	       	  	htmlStr += "<td align='center'><shiro:hasAnyRoles name='ADMIN,EMP'>"+
	 							"<a onclick=tcUpdate('"+user.id+"','"+user.name+"'); class='updateColor'>更新</a> | "+
	 							"<a href=listRes/"+user.id+" class='setReColor'>查看资源</a></shiro:hasAnyRoles></td></tr>";
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
	 	   
	 	    $("#main").html(htmlStr);
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
</script>


<script type="text/javascript">
</script>
</html>