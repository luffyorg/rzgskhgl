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
<script type="text/javascript" src="${basePath }static/js/jquery.min.js"></script>
<script type="text/javascript">
</script>
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
<script type="text/javascript">
$(function(){
	$("#productli").addClass("cur");
})
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
				管理中心<b>></b><strong>产品管理</strong>
			</div>
			<div class="mainBox"
				style="height: auto !important; height: 550px; min-height: 550px;">
				<h3>产品管理</h3>
				<script type="text/javascript">
     
     $(function(){ $(".idTabs").idTabs(); });
     
    </script>
				<div class="idTabs">
					<ul class="tab">
						<li><a href="#productList">产品列表</a></li>
						<li><a href="#addProduct">添加产品</a></li>
					</ul>
					<div class="items">
						<!--产品列表开始-->
						<div id="productList">
							<table width="100%" border="0" cellpadding="8" cellspacing="0"
								class="tableBasic">
								<tr>
									<th width="80">序号</th>
									<th width="80">产品名称</th>
									<th width="80">产品编号</th>
									<th width="80">产品价格</th>
									<th width="80">产品介绍</th>
									<th width="80">房产</th>
									<th width="80">动产</th>
									<th width="80">公司</th>
									<th width="80">实体</th>
									<th width="80">产品状态</th>
									<th width="80">操作</th>
								</tr>
								<!--数据循环开始-->
								<c:forEach items="${products }" var="product" varStatus="i">
									<tr>
										<td align="center">${i.count + (pb.currentPage-1)*10}</td>
										<td align="center">${product.name }</td>
										<td align="center">${product.productNo }</td>
										<td align="center">${product.productPrice }</td>
										<td align="center">${product.description }</td>
										<td align="center"><c:if test="${product.estate eq 0 }">无</c:if>
											<c:if test="${product.estate eq 1 }">有</c:if></td>
										<td align="center"><c:if test="${product.movable eq 0 }">无</c:if>
											<c:if test="${product.movable eq 1 }">有</c:if></td>
										<td align="center"><c:if test="${product.company eq 0 }">无</c:if>
											<c:if test="${product.company eq 1 }">有</c:if></td>
										<td align="center"><c:if
												test="${product.solidSurfacing eq 0 }">无</c:if> <c:if
												test="${product.solidSurfacing eq 1 }">有</c:if></td>
										<td align="center" id="updateStatus${product.id }"><c:if
												test="${product.isEnable eq 0 }">
												<span class="stop" id="stop${product.id }">下架 | </span>
												<a
													onclick="updateStatus(${product.id },${product.isEnable});"
													class="updateColor" id="start${product.id }"> 上架</a>
											</c:if> <c:if test="${product.isEnable eq 1 }">
												<span class="start" id="start${product.id }">上架 | </span>
												<a
													onclick="updateStatus(${product.id },${product.isEnable});"
													class="updateColor" id="stop${product.id }"> 下架</a>
											</c:if></td>
										<td align="center"><a href="update/${product.id }"
											class="updateColor">更新</a></td>
									</tr>
								</c:forEach>
								<!--数据循环结束-->
							</table>
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
						<!--产品列表结束-->
						
						<!--添加产品开始-->
						<div id="addProduct">
							<table width="100%" border="0" cellpadding="8" cellspacing="0"
								class="tableBasic">
								<tr>
									<td align="right" width="5">产品名称：</td>
									<td width="180"><input type="text" name="productName" id="productName"
										value="" size="80" class="inpMain" /></td>
								</tr>
								<tr>
									<td align="right">产品编号：</td>
									<td><input type="text" name="productNo" id="productNo" value=""
										size="80" class="inpMain" /></td>
								</tr>
								<tr>
									<td align="right">产品价格：</td>
									<td><input type="text" name="productPrice" id="productPrice" value=""
										size="80" class="inpMain" /></td>
								</tr>
								<tr>
									<td align="right">产品介绍：</td>
									<td><input type="text" name="description" id="description" value=""
										size="80" class="inpMain" /></td>
								</tr>
								<tr>
									<td align="right">购买条件：</td>
									<td>
										<label for="captcha_1"> 
											<input type="checkbox" name="checkbox" id="checkbox1" value="1">房产
										</label>
										<label for="captcha_2"> 
											<input type="checkbox" name="checkbox" id="checkbox2" value="1">动产
										</label>
										<label for="captcha_3"> 
											<input type="checkbox" name="checkbox" id="checkbox3" value="1">公司
										</label>
										<label for="captcha_4"> 
											<input type="checkbox" name="checkbox" id="checkbox4" value="1">实体
										</label>
									</td>
								</tr>
								<tr>
									<td align="right">状态：</td>
									<td><select name="">
											<option value="1">上架</option>
											<option value="0" selected>下架</option>
									</select></td>
								</tr>
								<tr>
									<td ></td>
									<td>
										<input name="button" class="btn" type="button" value="提交"  onclick="addProduct()"/>
									</td>
								</tr>
							</table>
						</div>
						<!--添加产品结束-->
					</div>
				</div>
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
function updateStatus(id,isEnable){
	$.get("updateStatus/"+id+"", function(result){
		if(result.isEnable==1){
			$("#updateStatus"+id+"").html("<span class=start id=start"+id+">上架 | </span> <a onclick=updateStatus("+id+",0); class=updateColor id="+id+">下架</a>")
		}else {
			$("#updateStatus"+id+"").html("<span class=start id=start"+id+">下架 | </span> <a onclick=updateStatus("+id+",1); class=updateColor id="+id+">上架</a>")
		}
				
	  });
}

function nextPage(size,page){
	 $.get("nextPage?pageSize="+size+"&page="+page+"", function(data){
		 //组装表格
		var htmlStr = "<table width='100%'  border='0' cellpadding='10' cellspacing='0' class='tableBasic'>";
		htmlStr += "<tr> <th width='80'>序号</th>"+
        "<th width='80'>产品名称</th>"+
        "<th width='80'>产品编号</th>"+
        "<th width='80'>总资产</th>"+
        "<th width='80'>总负债</th>"+
        "<th width='80'>征信情况</th>"+
        "<th width='80'>行业</th>"+
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
				"<td align='center'>"+product.totalAssets+" </td>"+
				"<td align='center'>"+product.totalLiability+" </td>"+
				"<td align='center'>"+product.creditConditions+" </td>"+
				"<td align='center'>"+product.industry+"</td>";
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
					htmlStr += "<td align='center' id='updateStatus"+product.id+"'>"+
					"<span class='stop' id='stop"+product.id +"'>下架 | </span>"+
					"<a onclick='updateStatus("+product.id +","+product.isEnable+")'"+
					"class='updateColor' id='start"+product.id+"'> 上架</a></td>";
				}else{
					htmlStr += "<td align='center' id='updateStatus"+product.id+"'>"+
					"<span class='stop' id='stop${product.id }'>上架 | </span>"+
					"<a onclick='updateStatus("+product.id +","+product.isEnable+")'"+
					"class='updateColor' id='start"+product.id+"'> 下架</a></td>";
				}
	         htmlStr += "<td align='center'><a onclick=updateRes(this,"+product.id+"); class='updateColor'>更新</a></td></tr>";
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
function addProduct(){
	
	var name = $("#productName").val();
	var productNo = $("#productNo").val();
	var productPrice = $("#productPrice").val();
	var description = $("#description").val();
}
</script>
</html>