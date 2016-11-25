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
<script type="text/javascript" src="${basePath }static/js/jquery.min.js"></script>
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
	margin: -200px auto;
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
	margin: -400px auto;
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
					<input type="text" id="productName" class="inpMain1" placeholder="产品名称或者产品编号"/>
					<input type="button" value="查找" onclick="search()">
				</h3> 
				<div class="navList" id="main">
					<table width="100%" border="0" cellpadding="10" cellspacing="0"
						class="tableBasic">
						<tr>
							<td>序号</td>
							<td>产品名称</td>
							<td>产品编号</td>
							<td>产品价格</td>
							<td colspan="4">
								<table >
									<tr>购买条件</tr>
									<tr>
										<td align="center">房产</td>
										<td align="center">动产</td>
										<td align="center">公司</td>
										<td align="center">实体</td>
									</tr>
								</table>

							</td>

							<td align="center">产品状态</td>
							<td>操作</td>
						</tr>
						<tbody>
							<c:forEach items="${products }" var="product" varStatus="i">
								<tr>
									<td>${i.count + (pb.currentPage-1)*10}</td>
									<td>${product.name }</td>
									<td>${product.productNo }</td>
									<td>${product.productPrice }</td>
									<td align="center"><c:if test="${product.estate eq 0 }">无</c:if> <c:if
											test="${product.estate eq 1 }">有</c:if></td>
									<td align="center"><c:if test="${product.movable eq 0 }">无</c:if> <c:if
											test="${product.movable eq 1 }">有</c:if></td>
									<td align="center"><c:if test="${product.company eq 0 }">无</c:if> <c:if
											test="${product.company eq 1 }">有</c:if></td>
									<td align="center"><c:if test="${product.solidSurfacing eq 0 }">无</c:if>
										<c:if test="${product.solidSurfacing eq 1 }">有</c:if></td>
									<td align="center"><c:if test="${product.isEnable eq 0 }">
										下架
										</c:if> <c:if test="${product.isEnable eq 1 }">
											上架
										</c:if></td>
									<td>
										<a onclick="queryBuyUser(${product.productNo});" class="updateColor">搜索客户</a>  | 
										<c:if test="${product.isEnable eq 0 }">
											<a>购买</a>
										</c:if>
										<c:if test="${product.isEnable eq 1 }">
											<%-- <a href="buy/${product.id }" class="setReColor">购买</a> --%>
											<a onclick="buy(${product.productNo },'${product.name }','${product.productPrice }');" class="setReColor">购买 </a> 
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<!-- 分页开始 -->
				<div style="float:right;margin-top:15px;" class="splitPage" id="splitPage">
					<c:if test="${pb.currentPage==1 }">
						<a  class='cursorauto'>首页</a> 
					</c:if>
					<c:if test="${pb.currentPage!=1 }">
						<a onclick="nextPage(10,1);" class="cursorpointer">首页</a> 
					</c:if>
					<c:if test="${pb.hasPreviousPage==true}">
						<a onclick="nextPage(10,${pb.currentPage-1});" class="cursorpointer"> ◄上一页</a>
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
				<!--弹窗开始 -->
				<div class="tc">
					<div class="tc1">
						购买产品<img src="${basePath}static/images/closed.png" onclick="tcclose()"
							style="float: right; margin-top: 15px; margin-right: 15px; cursor: pointer;">
					</div>
					<table>
						<tr>
							<td height="35" width="25%" align="right">产品编号：</td>
							<td><input type="text" name="productId" id="productId" value=""
								size="80" class="inpMain" readonly="readonly"/></td>
						</tr>
						<tr>
							<td height="35" width="100px" align="right">产品名称：</td>
							<td><input type="text" name="productName" id="productName" value="" size="80"
								class="inpMain" readonly="readonly"/></td>
						</tr>
						<tr>
							<td height="35" width="100px" align="right">产品价格：</td>
							<td><input type="text" name="productPrice" id="productPrice" value="" size="80"
								class="inpMain" readonly="readonly"/></td>
						</tr>
						<tr>
							<td height="35" width="100px" align="right">购买人账户：</td>
							<td><input id="productBuyBy" list="users" name="link" style="border:1px solid #d8d8d8;
									width:98px;height:20px;line-height:17px;"/>
								<datalist id="users" > 
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
									</select>
							</td>
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
function nextPage(size,page){
	 $.get("nextPage?pageSize="+size+"&page="+page+"", function(data){
		 //组装表格
		var htmlStr = "<table width='100%'  border='0' cellpadding='10' cellspacing='0' class='tableBasic'>";
		htmlStr += "<tr> <th width='80'>序号</th>"+
       "<th width='80'>产品名称</th>"+
       "<th width='80'>产品编号</th>"+
       "<th width='80'>产品价格</th>"+
       "<th width='80'>产品简介</th>"+
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
				"<td align='center'>"+product.productPrice+" </td>"+
				"<td align='center'>"+product.description+" </td>"
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
	       	  	htmlStr += "<td align='center'><a onclick='queryBuyUser("+product.productNo +")' class='updateColor'>搜索</a>  | ";
	       		if(product.isEnable==0){
	         		htmlStr += "<a>购买</a></td></td></tr>";
	       		}else{
	       			htmlStr += "<a onclick=buy("+product.productNo+",'"+product.name+"','"+product.productPrice+"'); class='setReColor'>购买</a></td></td></tr>";
	       		}
	       			/* htmlStr += "<a href='buy/"+product.id +"' class='setReColor'>购买</a></td></td></tr>"; */
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
function buy(id,name,price){
	var options="";
	 $.get("queryBuyUser?id="+id+"", function(data){
		 if(data.msg=="success"){
			 for(var i = 0; i < data.users.length; i++){
				 var user = data.users[i];
				 options += "<option value='"+user.name+"'></option>";
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
			      "<th width='80'>登录名称</th>"+
			      "<th width='80'>真实姓名</th>"+
			      "<th width='80'>电话</th>"+
			      "<th width='80'>地址</th>"+
			      "<th width='80'>性别</th>"+
			      "<th width='80'>总资产</th>"+
			      "<th width='80'>总负债</th>"+
			      "<th width='80'>征信情况</th>"+
			      "<th width='80'>行业</th>"
      var pb=data.pb;
	    for(var i = 0; i < data.users.length; i++){
	         var user = data.users[i];
	         htmlStr += "<tr><td align='center'>"+(i+1)+" </td>"+
				"<td align='center'>"+user.name+" </td>"+
				"<td align='center'>"+user.nickName+" </td>"+
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
	    $("#h3").html("<input type='text' id='userName'class='inpMain1' placeholder='登录名称'/>"+
	    		"<input type='button' value='查找' class=''>");
	    
	})
}

function search(){
	var productName=$("#productName").val();
	if(productName=="" || productName==null){
		alert("请输入查询条件");
	}else{
		$.get("searchProduct?productName="+productName+"", function(data){
			 var htmlStr = "<table width='100%'  border='0' cellpadding='10' cellspacing='0' class='tableBasic'>";
			 		htmlStr += "<tr> <th width='80'>序号</th>"+
			        "<th width='80'>产品名称</th>"+
			        "<th width='80'>产品编号</th>"+
			        "<th width='80'>产品价格</th>"+
			        "<th width='80'>产品简介</th>"+
			        "<th width='80'>房产</th>"+
			        "<th width='80'>动产</th>"+
			        "<th width='80'>公司</th>"+
			        "<th width='80'>实体</th>"+
			        "<th width='80'>产品状态</th>"+
			        "<th width='80'>操作</th></tr>";
			 	    for(var i = 0; i < data.products.length; i++){
			 	         var product = data.products[i];
			 	         htmlStr += "<tr><td align='center'>"+(1+i)+" </td>"+
			 				"<td align='center'>"+product.name+" </td>"+
			 				"<td align='center'>"+product.productNo+" </td>"+
			 				"<td align='center'>"+product.productPrice+" </td>"+
			 				"<td align='center'>"+product.description+" </td>"
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
			 	    }
			 	   $("#main").html(htmlStr);
			 	   $(".splitPage").hide();
		})
		 
	}
	
}
</script>
</html>