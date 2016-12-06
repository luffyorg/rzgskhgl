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
<title>购买产品</title>
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
	margin: -10% auto;
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
	margin: 10% auto;
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
.actionBtn1 {
	float: right;
	display: inline-block;
	background-color: #0072C6;
	padding: 0px 15px;
	height: 27px;
	line-height: 27px;
	color: #FFFFFF;
	font-size: 13px;
	font-weight: bold;
}

.inpMain1 {
	border: 1px solid #DBDBDB;
	background-color: #FFF;
	padding: 0px 5px;
	color: #999;
	font-size: 12x;
	line-height: 20px;
	width: 180px;
	-webkit-appearance: none;
}

.inp_name, .inp_customer {
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

.inp_customer {
	margin-left: 10%;
	width: 150px;
}

.label_select {
	font-size: 16px;
	color: #333;
	font-family: "微软雅黑";
	margin-top: -5px;
}

.inp_btn, .inp_btn2 {
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

.inp_btn2 {
	width: 80px;
}

.inp_btn:hover {
	background-color: #3c87f0;
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
				<h3 id="h3">
					<!-- <input type="text" id="productName" class="inpMain1" placeholder="产品名称或者产品编号"/>
					<input type="button" value="查找" onclick="search()"> -->
					<input type="text" class="inp_name" placeholder="产品名称或产品编号"
						id="searchName" /> <label class="label_select">房产 <select
						style="width: 60px; height: 30px;" id="searchEstate">
							<option value="1">有</option>
							<option value="0">无</option>
							<option value="2" selected>--</option>
					</select>
					</label> <label class="label_select">动产 <select
						style="width: 60px; height: 30px;" id="searchMovable">
							<option value="1">有</option>
							<option value="0">无</option>
							<option value="2" selected>--</option>
					</select>
					</label> <label class="label_select">公司 <select
						style="width: 60px; height: 30px;" id="searchCompany">
							<option value="1">有</option>
							<option value="0">无</option>
							<option value="2" selected>--</option>
					</select>
					</label> <label class="label_select">实体铺面 <select
						style="width: 60px; height: 30px;" id="searchSolidSurfacing">
							<option value="1">有</option>
							<option value="0">无</option>
							<option value="2" selected>--</option>
					</select>
					</label> <input type="button" value="搜索" class="inp_btn" onclick="search()" />
					<input class="inp_customer" type="text" list="customer"
						placeholder="产品名称或产品编号" id="searchCustomer"/>
					<datalist id="customer">
					<c:forEach items="${products }" var="product" varStatus="i">
						<option value="${product.name }"></option>
					</c:forEach>
					<c:forEach items="${products }" var="product" varStatus="i">
						<option value="${product.productNo }"></option>
					</c:forEach>
					<!-- <option value="1"></option>
					<option value="2"></option>
					<option value="3"></option>
					<option value="4"></option>
					<option value="33"></option>
					<option value="42"></option>
					<option value="34"></option>
					<option value="41"></option>
					<option value="31"></option>
					<option value="14"></option> -->

					</datalist>
					<input type="button" class="inp_btn2" value="搜索客户" onclick="queryUserByNameOrCode()"/>
				</h3>
				<div class="navList" id="main">
					<table width="100%" border="0" cellpadding="10" cellspacing="0"
						class="tableBasic">
						<tr>
							<td>序号</td>
							<td>产品名称</td>
							<td>产品编号</td>
							<td>产品定价</td>
							<td align="center">房产</td>
							<td align="center">动产</td>
							<td align="center">公司</td>
							<td align="center">实体</td>

							<td align="center">产品状态</td>
							<td>操作</td>
						</tr>
						<tbody>
							<c:forEach items="${products }" var="product" varStatus="i">
								<tr>
									<td>${i.count}</td>
									<td>${product.name }</td>
									<td>${product.productNo }</td>
									<td>${product.productPrice }</td>
									<td align="center"><c:if test="${product.estate eq 0 }">无</c:if>
										<c:if test="${product.estate eq 1 }">有</c:if></td>
									<td align="center"><c:if test="${product.movable eq 0 }">无</c:if>
										<c:if test="${product.movable eq 1 }">有</c:if></td>
									<td align="center"><c:if test="${product.company eq 0 }">无</c:if>
										<c:if test="${product.company eq 1 }">有</c:if></td>
									<td align="center"><c:if
											test="${product.solidSurfacing eq 0 }">无</c:if> <c:if
											test="${product.solidSurfacing eq 1 }">有</c:if></td>
									<td align="center"><c:if test="${product.isEnable eq 0 }">
										下架
										</c:if> <c:if test="${product.isEnable eq 1 }">
											上架
										</c:if></td>
									<td><a onclick="queryBuyUser(${product.productNo});"
										class="updateColor">搜索客户</a> | <c:if
											test="${product.isEnable eq 0 }">
											<a>购买</a>
										</c:if> <c:if test="${product.isEnable eq 1 }">
											<a
												onclick="buy(${product.productNo },'${product.name }','${product.productPrice }');"
												class="setReColor">购买 </a>
										</c:if></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<!--弹窗开始 -->
				<div class="tc">
					<div class="tc1">
						购买产品<img src="${basePath}static/images/closed.png"
							onclick="tcclose()"
							style="float: right; margin-top: 15px; margin-right: 15px; cursor: pointer;">
					</div>
					<table>
						<tr>
							<td height="35" width="25%" align="right">产品编号：</td>
							<td><input type="text" name="productId" id="productId"
								value="" size="80" class="inpMain" readonly="readonly" /></td>
						</tr>
						<tr>
							<td height="35" width="100px" align="right">产品名称：</td>
							<td><input type="text" name="productName" id="productName"
								value="" size="80" class="inpMain" readonly="readonly" /></td>
						</tr>
						<tr>
							<td height="35" width="100px" align="right">产品价格：</td>
							<td><input type="text" name="productPrice" id="productPrice"
								value="" size="80" class="inpMain" readonly="readonly" /></td>
						</tr>
						<tr>
							<td height="35" width="100px" align="right">购买人账户：</td>
							<td><input id="productBuyBy" list="users" name="link"
								style="border: 1px solid #d8d8d8; width: 98px; height: 20px; line-height: 17px;" />
								<datalist id="users">
								<option value="没有可购买人"></option>
								</datalist></td>
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
								onclick="buyProduct();" /></td>
						</tr>
					</table>
				</div>
				<!--弹窗结束-->
				<div id="user"></div>
				<!--底部开始-->

			</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
		<!--底部结束-->
		<div class="clear"></div>
	</div>
</body>
<script type="text/javascript">

function gotoPageByInput(currentPage,totalpage){
	var page = $("#goInput").val();
	if(page<1 || page>totalpage){
		alert("请输入正确页码！");
	}else if(page==currentPage){
		
	}else
		nextPage(10,page);
}
function buy(id,name,price){
	var options="";
	 $.get("queryBuyUser?id="+id+"", function(data){
		 if(data.msg=="success"){
			 for(var i = 0; i < data.customers.length; i++){
				 var customer = data.customers[i];
				 options += "<option value='"+customer.name+"'></option>";
			 }
			 $("#users").empty();
			 $("#users").append(options);
		 }
	 });
	 $("#productId").val(id);
	 $("#productName").val(name);
	 $("#productPrice").val(price);
	 $("body").append("<div id='mask'></div>");
	 $("#mask").addClass("mask").fadeIn("slow");
	 $(".tc").fadeIn("slow");
}

function buyProduct(){
	 var id = $("#productId").val();
	 var name = $("#productName").val();
	 var price = $("#productPrice").val();
	 var buyBy = $("#productBuyBy").val();
	 var status = $("#orderStatus").val();
	 if(buyBy==""){
		 alert("输入购买人");
	 }else{
		 sendInfo = {
				 "id" : id,
				 "name" : name,
				 "price" : price,
				 "buyName" : buyBy,
				 "orderStatus" : status
		 }
		 $.ajax({
				type : "POST",
				url : "buyProduct",
				dataType : "json",
				contentType : 'application/json',
				data : JSON.stringify(sendInfo),
				success : function(data) {
					if (data.msg == "success") {
						alert("购买成功");
						tcclose();
					} else {
						alert(data.msg);
					}
				},
				error : function() {
					alert("没有返回值");
				}
			});
	 }
}

function queryBuyUser(id){
	$.get("queryBuyUser?id="+id+"", function(data){
		var htmlStr = "<table width='100%'  border='0' cellpadding='10' cellspacing='0' class='tableBasic'>";
		htmlStr += "<tr> <th width='80'>序号</th>"+
			      "<th width='80'>姓名</th>"+
			      "<th width='80'>电话</th>"+
			      "<th width='80'>地址</th>"+
			      "<th width='80'>性别</th>"+
			      "<th width='80'>总资产</th>"+
			      "<th width='80'>总负债</th>"+
			      "<th width='80'>征信情况</th>"+
			      "<th width='80'>行业</th>"
      var pb=data.pb;
	    for(var i = 0; i < data.customers.length; i++){
	         var user = data.customers[i];
	         htmlStr += "<tr><td align='center'>"+(i+1)+" </td>"+
				"<td align='center'>"+user.name+" </td>"+
				"<td align='center'>"+user.tel+" </td>"+
				"<td align='center'>"+user.address+" </td>"+
				"<td align='center'>"+user.gender+" </td>"+
				"<td align='center'>"+user.totalAssets+" </td>"+
				"<td align='center'>"+user.totalLiability+" </td>"+
				"<td align='center'>"+user.creditConditions+" </td>"+
				"<td align='center'>"+user.industry+" </td>";
	    }
	    htmlStr += "</table>";
	    $("#main").hide();
	    $(".splitPage").hide();
	    
	    $("#user").html(htmlStr);
	    $("#h3").html("<input type='text' id='userName'class='inp_name' placeholder='登录名称'/>"+
	    		"<input type='button' value='查找' class='inp_btn'>");
	    
	})
}
function queryUserByNameOrCode(){
	var customer = $("#searchCustomer").val()
	$.get("queryUserByNameOrCode?customer="+customer+"", function(data){
		if(data.msg == "error"){
			alert("没有此产品");
		}else{
			var htmlStr = "<table width='100%'  border='0' cellpadding='10' cellspacing='0' class='tableBasic'>";
			htmlStr += "<tr> <th width='80'>序号</th>"+
				      "<th width='80'>姓名</th>"+
				      "<th width='80'>电话</th>"+
				      "<th width='80'>地址</th>"+
				      "<th width='80'>性别</th>"+
				      "<th width='80'>总资产</th>"+
				      "<th width='80'>总负债</th>"+
				      "<th width='80'>征信情况</th>"+
				      "<th width='80'>行业</th>"
	      var pb=data.pb;
		    for(var i = 0; i < data.customers.length; i++){
		         var user = data.customers[i];
		         htmlStr += "<tr><td align='center'>"+(i+1)+" </td>"+
					"<td align='center'>"+user.name+" </td>"+
					"<td align='center'>"+user.tel+" </td>"+
					"<td align='center'>"+user.address+" </td>"+
					"<td align='center'>"+user.gender+" </td>"+
					"<td align='center'>"+user.totalAssets+" </td>"+
					"<td align='center'>"+user.totalLiability+" </td>"+
					"<td align='center'>"+user.creditConditions+" </td>"+
					"<td align='center'>"+user.industry+" </td>";
		    }
		    htmlStr += "</table>";
		    $("#main").hide();
		    
		    $("#user").html(htmlStr);
		    $("#h3").html("<input type='text' id='userName'class='inp_name' placeholder='登录名称'/>"+
		    		"<input type='button' value='查找' class='inp_btn'>");
		}
		
	    
	})
}

function search(){
	var estate = $("#searchEstate").val();
	var movable = $("#searchMovable").val();
	var company = $("#searchCompany").val();
	var solidSurfacing = $("#searchSolidSurfacing").val();
	var productNo = $("#searchName").val();
		$.get("search?pageSize="+10+"&page="+1+"&estate="+estate+"&movable="+movable+"&company="+company+"&solidSurfacing="+solidSurfacing+"&productNo="+productNo+"", function(data){
			 //组装表格
			var htmlStr = "<table width='100%'  border='0' cellpadding='10' cellspacing='0' class='tableBasic'>";
			htmlStr += "<tr> <th width='80'>序号</th>"+
	       "<th width='80'>产品名称</th>"+
	       "<th width='80'>产品编号</th>"+
	       "<th width='80'>产品价格</th>"+
	       "<th width='80'>房产</th>"+
	       "<th width='80'>动产</th>"+
	       "<th width='80'>公司</th>"+
	       "<th width='80'>实体</th>"+
	       "<th width='80'>产品状态</th>"+
	       "<th width='80'>操作</th></tr>";
	       var pb=data.pb;
		    for(var i = 0; i < data.products.length; i++){
		         var product = data.products[i];
		         htmlStr += "<tr><td align='center'>"+((pb.currentPage-1)*10+1+i)+" </td>"+
					"<td align='center'>"+product.name+" </td>"+
					"<td align='center'>"+product.productNo+" </td>"+
					"<td align='center'>"+product.productPrice+" </td>";
					if(product.estate==0){
						htmlStr += "<td align='center'>无";
					}else
						htmlStr += "<td align='center'>有";
					
					if(product.movable==0){
						htmlStr += "<td align='center'>无";
					}else
						htmlStr += "<td align='center'>有";
					
					if(product.company==0){
						htmlStr += "<td align='center'>无";
					}else
						htmlStr += "<td align='center'>有";
					
					if(product.solidSurfacing==0){
						htmlStr += "<td align='center'>无";
					}else
						htmlStr += "<td align='center'>有";
					if(product.isEnable==0){
						htmlStr += "<td align='center'>下架";
					}else{
						htmlStr += "<td align='center'>上架</td>";
					}
		       	  	htmlStr += "<td align='center'><a onclick='queryBuyUser("+product.productNo +")' class='updateColor'>搜索客户</a>  | ";
		       		if(product.isEnable==0){
		         		htmlStr += "<a>购买</a></td></td></tr>";
		       		}else{
		       			htmlStr += "<a onclick=buy("+product.productNo+",'"+product.name+"','"+product.productPrice+"'); class='setReColor'>购买</a></td></td></tr>";
		       		}
		       			/* htmlStr += "<a href='buy/"+product.id +"' class='setReColor'>购买</a></td></td></tr>"; */
		    }
		    htmlStr += "</table>";
		   
		    $("#main").html(htmlStr);
		   
		}) 
}
</script>
</html>