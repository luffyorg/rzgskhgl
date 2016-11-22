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
/**弹窗样式开始**/
.tc {
	width: 600px;
	/* height: 700px; */
	display: none;
	position: absolute;
	margin: -660px auto;
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
	/* height: 700px; */
	display: none;
	position: absolute;
	margin: -660px auto;
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
  function tc(){
    $("body").append("<div id='mask'></div>");
    $("#mask").addClass("mask").fadeIn("slow");
    $(".tc").fadeIn("slow");
  }; 
  function tcUpdate(id,name,nickName,tel,address,gender,totalAssets,totalLiability,creditConditdions,industry,estate,movable,company,solidSurfacing){
	  $("#upId").val(id);
	  $("#upName").val(name);
	  $("#upNickName").val(nickName);
	  $("#upTel").val(tel);
	  $("#upAddress").val(address);
	  $("#upTotalAssets").val(totalAssets);
	  $("#upTotalLiability").val(totalLiability);
	  $("#upindustry").val(industry);
	  
	  var selectTag = document.getElementById("upestate");
	  var options = selectTag.getElementsByTagName("option");
	  for(var i=0;i<options.length;i++){
	    var value = options[i].value;
	    if(value==estate){
	      options[i].setAttribute("selected","true");
	    }
	  }
	  var selectTag1 = document.getElementById("upmovable");
	  var options = selectTag1.getElementsByTagName("option");
	  for(var i=0;i<options.length;i++){
	    var value = options[i].value;
	    if(value==movable){
	      options[i].setAttribute("selected","true");
	    }
	  }
	  var selectTag2 = document.getElementById("upcompany");
	  var options = selectTag2.getElementsByTagName("option");
	  for(var i=0;i<options.length;i++){
	    var value = options[i].value;
	    if(value==company){
	      options[i].setAttribute("selected","true");
	    }
	  }
	  var selectTag3 = document.getElementById("upsolidSurfacing");
	  var options = selectTag3.getElementsByTagName("option");
	  for(var i=0;i<options.length;i++){
	    var value = options[i].value;
	    if(value==solidSurfacing){
	      options[i].setAttribute("selected","true");
	    }
	  }
	  var selectTag4 = document.getElementById("upcreditConditions");
	  var options = selectTag4.getElementsByTagName("option");
	  for(var i=0;i<options.length;i++){
	    var value = options[i].value;
	    if(value==creditConditdions){
	      options[i].setAttribute("selected","true");
	    }
	  }
	  var selectTag5 = document.getElementById("upSex");
	  var options = selectTag5.getElementsByTagName("option");
	  for(var i=0;i<options.length;i++){
	    var value = options[i].value;
	    if(value==gender){
	      options[i].setAttribute("selected","true");
	    }
	  }
	  $.get("getUserRole?id="+id+"", function(data){
			for(var j=0; j<data.roles.length;j++){
				$("#upcheckbox"+data.roles[j]+"").prop('checked',true);
			}
	  })
	    
    $("body").append("<div id='mask'></div>");
    $("#mask").addClass("mask").fadeIn("slow");
    $(".tcUpdate").fadeIn("slow");
  }; 
 
  function tcclose(){
    $(".tc").fadeOut("fast");
    $("#mask").css({ display: 'none' });
  };
  function tcupclose() {
	  $("#upId").val("");
	  $("#upName").val("");
	  $("#upNickName").val("");
	  $("#upTel").val("");
	  $("#upAddress").val("");
	  $("#upSex").html("<option value='男'>男</option><option value='女' selected>女</option>");
	  $("#upTotalAssets").val("");
	  $("#upTotalLiability").val("");
	  $("#upindustry").val("");
	  $("#upestate").html("<option value='1'>有</option><option value='0' selected>无</option>");
	  $("#upmovable").html("<option value='1'>有</option><option value='0' selected>无</option>");
	  $("#upcompany").html("<option value='1'>有</option><option value='0' selected>无</option>");
	  $("#upsolidSurfacing").html("<option value='1'>有</option><option value='0' selected>无</option>");
	  $("#upcreditConditions").html("<option value='合格' selected>合格</option><option value='不合格' >不合格</option>");
	  $("input[name='upcheckbox']").prop('checked',false);
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
					<a onclick="tc();" class="actionBtn">添加用户</a>自定义用户
				</h3>
				<div class="navList" id="main">
					<table width="100%" border="0" cellpadding="10" cellspacing="0"
						class="tableBasic">
						<tr>
							<th>序号</th>
							<th>登录名称</th>
							<th>真实姓名</th>
							<th>电话</th>
							<th>地址</th>
							<th>性别</th>
							<th>总资产(万元)</th>
							<th>总负债(万元)</th>
							<th>征信情况</th>
							<th>行业</th>
							<th>房产</th>
							<th>动产</th>
							<th>公司</th>
							<th>实体铺面</th>
							<th>用户状态</th>
							<th>用户操作</th>
						</tr>
						<tbody>
							<c:forEach items="${users }" var="user" varStatus="i">
								<tr>
									<td align="center">${i.count + (pb.currentPage-1)*10}</td>
									<td align="center">${user.name }</td>
									<td align="center">${user.nickName }</td>
									<td align="center">${user.tel }</td>
									<td align="center">${user.address }</td>
									<td align="center">${user.gender }</td>
									<td align="center">${user.totalAssets }</td>
									<td align="center">${user.totalLiability }</td>
									<td align="center">${user.creditConditions }</td>
									<td align="center">${user.industry }</td>
									<td align="center"><c:if test="${user.estate eq 0 }">无</c:if>
										<c:if test="${user.estate eq 1 }">有</c:if></td>
									<td align="center"><c:if test="${user.movable eq 0 }">无</c:if>
										<c:if test="${user.movable eq 1 }">有</c:if></td>
									<td align="center"><c:if test="${user.company eq 0 }">无</c:if>
										<c:if test="${user.company eq 1 }">有</c:if></td>
									<td align="center"><c:if
											test="${user.solidSurfacing eq 0 }">无</c:if> <c:if
											test="${user.solidSurfacing eq 1 }">有</c:if></td>
									<td align="center" id="updateStatus${user.id }"><c:if
											test="${user.isEnable eq 0 }">
											<span class="stop" id="stop${user.id }">停用 | </span>
											<a onclick="updateStatus(${user.id },${user.isEnable});"
												class="updateColor" id="start${user.id }"> 启用</a>
										</c:if> <c:if test="${user.isEnable eq 1 }">
											<span class="start" id="start${user.id }">启用 | </span>
											<a onclick="updateStatus(${user.id },${user.isEnable});"
												class="updateColor" id="stop${user.id }"> 停用</a>
										</c:if></td>
									<td align="center"><shiro:hasAnyRoles name="ADMIN,EMP">
											<a onclick="tcUpdate('${user.id }','${user.name }','${user.nickName }','${user.tel }','${user.address }',
											'${user.gender }','${user.totalAssets }','${user.totalLiability }','${user.creditConditions }','${user.industry }',
											'${user.estate }','${user.movable }','${user.company }','${user.solidSurfacing }');" class="updateColor">更新</a> | <a
												href="${basePath }admin/user/listRes/${user.id }"
												class="setReColor">查看资源</a>
										</shiro:hasAnyRoles></td>
								</tr>
							</c:forEach>
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
		</div>
		<!--主体内容部分结束-->


		<!--弹窗开始-->
		<div class="tc">
			<div class="tc1">
				添加用户<img src="${basePath}static/images/closed.png"
					onclick="tcclose()"
					style="float: right; margin-top: 15px; margin-right: 15px; cursor: pointer;">
			</div>
			<table>
				<tr>
					<td height="35" align="right">登录账户：</td>
					<td><input type="text" id="addName" onblur="isname()"
						value="" size="80" class="inpMain" placeholder="内容为4~12个字符" maxlength="12"/> <span
						class="tips" id="divname">内容为4~12个字符</span></td>
				</tr>
				<tr>
					<td height="35" align="right">真实姓名：</td>
					<td><input type="text" id="addNickName" onblur="isname2()"
						value="" size="80" class="inpMain" placeholder="内容为2~7个汉字" maxlength="7"/> <span
						class="tips" id="divname2">内容为2~7个汉字</span></td>
				</tr>
				<tr>
					<td height="35" align="right">密码：</td>
					<td><input type="password" id="addPassword" value="" size="80" onblur="checkPwd()"
						class="inpMain" placeholder="密码为字母和数字6-20位组成"  maxlength="20"/><span class="tips"
						id="divpwd"></span></td>
				</tr>
				<tr>
					<td height="35" align="right">确认密码：</td>
					<td><input type="password" id="addPassword2" value="" size="80" maxlength="20"
						class="inpMain"  onblur="checkPwd2()"/><span class="tips" id="divpwd2" ></span></td>
				</tr>
				<tr>
					<td height="35" align="right">电话：</td>
					<td><input type="text" name="addTel" id="addTel" value="" onblur="checkTel()" onkeydown="onlyNum();" maxlength="11"
						size="80" class="inpMain" /><span class="tips" id="divtel">请输入正确手机号码</span></td>
				</tr>
				<tr>
					<td height="35" align="right">地址：</td>
					<td><input type="text" name="addAddress" id="addAddress"
						value="" size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">性别：</td>
					<td><select name="addSex" id="addSex">
							<option value="男">男</option>
							<option value="女" selected>女</option>
					</select></td>
				</tr>
				<tr>
					<td height="35" align="right">总资产：</td>
					<td><input type="text" name="addtotalAssets"
						id="addtotalAssets" value="" size="80" class="inpMain" onkeyup="checkTotalAssets()" onkeydown="onlyNum2();"/>
						<span class="tips" id="divtotalAssets"></span></td>
				</tr>
				<tr>
					<td height="35" align="right">总负债：</td>
					<td><input type="text" name="addtotalLiability"
						id="addtotalLiability" value="" size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">征信情况：</td>
					<td>
						<select id="addcreditConditions">
							<option value="合格" selected="selected">合格</option>
							<option value="不合格">不合格</option>
						</select>
					</td>
				</tr>
				<tr>
					<td height="35" align="right">行业：</td>
					<td><input type="text" name="addindustry" id="addindustry"
						value="" size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">房产：</td>
					<td><select name="addestate" id="addestate">
							<option value="1">有</option>
							<option value="0" selected>无</option>
					</select></td>
				</tr>
				<tr>
					<td height="35" align="right">动产：</td>
					<td><select name="addmovable" id="addmovable">
							<option value="1">有</option>
							<option value="0" selected>无</option>
					</select></td>
				</tr>
				<tr>
					<td height="35" align="right">公司：</td>
					<td><select name="addcompany" id="addcompany">
							<option value="1">有</option>
							<option value="0" selected>无</option>
					</select></td>
				</tr>
				<tr>
					<td height="35" align="right">实体铺面：</td>
					<td><select name="addsolidSurfacing" id="addsolidSurfacing">
							<option value="1">有</option>
							<option value="0" selected>无</option>
					</select></td>
				</tr>
				<tr>
					<td height="35" align="right">状态：</td>
					<td><select name="addisEnable" id="addisEnable">
							<option value="1">启用</option>
							<option value="0" selected>停用</option>
					</select></td>
				</tr>
				<tr>
					<td height="35" align="right">角色：</td>
					<td><c:forEach var="role" items="${roles }">
						<label for="captcha_${role.id }"> 
							<input type="checkbox" name="checkbox" id="checkbox" value="${role.id }"> ${role.name }
						</label>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td></td>
					<td><input name="button" class="btn" type="button"
						onclick="addUser();" value="确认提交" /></td>
				</tr>
			</table>
		</div>
		<!--弹窗结束-->

		<!--弹窗开始 更新 -->
		<div class="tcUpdate">
			<div class="tc1">
				用户更新<img src="${basePath}static/images/closed.png"
					onclick="tcupclose()"
					style="float: right; margin-top: 15px; margin-right: 15px; cursor: pointer;">
			</div>
			<table>
				<tr style="display:none">
					<td height="35" align="right">用户标识：</td>
					<td><input type="text" name="upId" id="upId" value=""
						size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">登录账户：</td>
					<td><input type="text" name="upName" id="upName" value=""
						size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">昵 称：</td>
					<td><input type="text" name="upNickName" id="upNickName"
						value="" size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">电 话：</td>
					<td><input type="text" name="upTel" id="upTel" value=""
						size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">地 址：</td>
					<td><input type="text" name="upAddress" id="upAddress"
						value="" size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">性 别：</td>
					<td><select name="upSex" id="upSex">
							<option value="男">男</option>
							<option value="女" selected>女</option>
					</select></td>
				</tr>
				<tr>
					<td height="35" align="right">资 产：</td>
					<td><input type="text" name="upTotalAssets" id="upTotalAssets"
						value="" size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">负 债：</td>
					<td><input type="text" name="upTotalLiability"
						id="upTotalLiability" value="" size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">征信情况：</td>
					<td>
						<select id="upcreditConditions">
							<option value="合格" selected="selected">合格</option>
							<option value="不合格">不合格</option>
						</select>
					</td>
				</tr>
				<tr>
					<td height="35" align="right">行业：</td>
					<td><input type="text" name="upindustry" id="upindustry"
						value="" size="80" class="inpMain" /></td>
				</tr>
				<tr>
					<td height="35" align="right">房产：</td>
					<td><select name="upestate" id="upestate">
							<option value="1">有</option>
							<option value="0" selected>无</option>
					</select></td>
				</tr>
				<tr>
					<td height="35" align="right">动产：</td>
					<td><select name="upmovable" id="upmovable">
							<option value="1">有</option>
							<option value="0" selected>无</option>
					</select></td>
				</tr>
				<tr>
					<td height="35" align="right">公司：</td>
					<td><select name="upcompany" id="upcompany">
							<option value="1">有</option>
							<option value="0" selected>无</option>
					</select></td>
				</tr>
				<tr>
					<td height="35" align="right">实体铺面：</td>
					<td><select name="upsolidSurfacing" id="upsolidSurfacing">
							<option value="1">有</option>
							<option value="0" selected>无</option>
					</select></td>
				</tr>
				<tr>
					<td height="35" align="right">角色：</td>
					<td><c:forEach var="role" items="${roles }">
						<label for="captcha_${role.id }"> 
							<input type="checkbox" name="upcheckbox" id="upcheckbox${role.id }" value="${role.id }"> ${role.name }
						</label>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td></td>
					<td><input id="updateBtn" class="btn" type="button" value="提交"
						onclick="updateUser();" /></td>
				</tr>
			</table>
		</div>
		<!--弹窗结束-->


		<!--底部开始-->
		<%@ include file="../include/footer.jsp"%>
		<!--底部结束-->
		<div class="clear"></div>
		</div>
	</div>
	<!--end-->
</body>
<script type="text/javascript">
function nextPage(size,page){
	 $.get("nextPage?pageSize="+size+"&page="+page+"", function(data){
		 //组装表格
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
			      "<th width='80'>行业</th>"+
			      "<th width='80'>房产</th>"+
			      "<th width='80'>动产</th>"+
			      "<th width='80'>公司</th>"+
			      "<th width='80'>实体</th>"+
			      "<th width='80'>产品状态</th>"+
			      "<th width='80'>操作</th></tr>";
      var pb=data.pb;
	    for(var i = 0; i < data.users.length; i++){
	         var user = data.users[i];
	         htmlStr += "<tr><td align='center'>"+((pb.currentPage-1)*10+1+i)+" </td>"+
				"<td align='center'>"+user.name+" </td>"+
				"<td align='center'>"+user.nickName+" </td>"+
				"<td align='center'>"+user.tel+" </td>"+
				"<td align='center'>"+user.address+" </td>"+
				"<td align='center'>"+user.gender+" </td>"+
				"<td align='center'>"+user.totalAssets+" </td>"+
				"<td align='center'>"+user.totalLiability+" </td>"+
				"<td align='center'>"+user.creditConditions+" </td>"+
				"<td align='center'>"+user.industry+" </td>"
				if(user.estate==0){
					htmlStr += "<td align='center'>无";
				}else
					htmlStr += "<td align='center'>有";
				
				if(user.movable==0){
					htmlStr += "<td align='center'>无";
				}else
					htmlStr += "<td align='center'>有";
				
				if(user.company==0){
					htmlStr += "<td align='center'>无";
				}else
					htmlStr += "<td align='center'>有";
				
				if(user.solidSurfacing==0){
					htmlStr += "<td align='center'>无";
				}else
					htmlStr += "<td align='center'>有";
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
							"<a onclick=tcUpdate('"+user.id+"','"+user.name+"','"+user.nickName+"','"+user.tel+"','"+user.address+"',"+
											"'"+user.gender+"','"+user.totalAssets+"','"+user.totalLiability+"','"+user.creditConditions+"','"+user.industry+"',"+
											"'"+user.estate+"','"+user.movable+"','"+user.company+"','"+user.solidSurfacing+"'); class='updateColor'>更新</a> | "+
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
function updateStatus(id,isEnable){
	$.get("updateStatus/"+id+"", function(result){
		if(result.isEnable==1){
			$("#updateStatus"+id+"").html("<span class=start id=start"+id+">启用 | </span> <a onclick=updateStatus("+id+",0); class=updateColor id="+id+">停用</a>")
		}else {
			$("#updateStatus"+id+"").html("<span class=start id=start"+id+">停用 | </span> <a onclick=updateStatus("+id+",1); class=updateColor id="+id+">启用</a>")
		}
				
	  });
}

function addUser(){
	var obj=document.getElementsByName('checkbox'); 
	var rids=new Array()
	var j=0;
	for(var i=0; i<obj.length; i++){ 
		if(obj[i].checked) {
			rids[j]=obj[i].value;
			j++;
		}else{
			
		}
	} 
	var name = $("#addName").val();
	var nickName = $("#addNickName").val();
	var pwd = $("#addPassword").val();
	var pwd2 = $("#addPassword2").val();
	var tel = $("#addTel").val();
	var address = $("#addAddress").val();
	var sex = $("#addSex").val();
	var totalAssets = $("#addtotalAssets").val();
	var totalLiability = $("#addtotalLiability").val();
	var creditConditions = $("#addcreditConditions").val();
	var industry = $("#addindustry").val();
	var estate = $("#addestate").val();
	var movable = $("#addmovable").val();
	var company = $("#addcompany").val();
	var solidSurfacing = $("#addsolidSurfacing").val();
	var isEnable = $("#addisEnable").val();
	var sendInfo = {
			"name" : name,
			"nickName" : nickName,
			"pwd" : pwd,
			"tel" :tel,
			"address" : address,
			"gender" : sex,
			"totalAssets" : totalAssets,
			"totalLiability" :totalLiability,
			"creditConditions" : creditConditions,
			"industry" : industry,
			"estate" : estate,
			"movable" : movable,
			"company" : company,
			"solidSurfacing" : solidSurfacing,
			"isEnable" : isEnable,
			"rids" : rids
		};
		$.ajax({
			type : "POST",
			url : "save",
			dataType : "json",
			contentType : 'application/json',
			data : JSON.stringify(sendInfo),
			success : function(data) {
				if (data.msg == "success") {
					alert("添加成功");
					window.location.href = "list";
				}else {
					alert("添加失败");
				}
			},
			error : function() {
				alert("网络异常，请稍后再试！");
			}
		});
}
function check(){
	var name = $("#addName").val();
	var pwd = $("#addPassword").val();
	var pwd2 = $("#addPassword2").val();
	var tel = $("#addTel").val();
	var address = $("#addAddress").val();
	var gender = $("#addGender").val();
	var sex = $("#addSex").val();
	var totalAssets = $("#addtotalAssets").val();
	var totalLiability = $("#addtotalLiability").val();
	var creditConditions = $("#addcreditConditions").val();
	var industry = $("#addindustry").val();
	var estate = $("#addestate").val();
	var movable = $("#addmovable").val();
	var company = $("#addcompany").val();
	var solidSurfacing = $("#addsolidSurfacing").val();
	var isEnable = $("#addisEnable").val();
}

function isname(){
	var name = $("#addName").val();
	var patrn=/^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){3,19}$/;   
	if (!patrn.exec(name)) 
		{  	
			divname.innerHTML='<font class="tips_false">长度是4~20个字符</font>';
		}else{  
			$.get("checkName?name="+name+"", function(msg){
				if(msg=="success"){
					divname.innerHTML='<font class="tips_false">登录名已存在</font>';
				}
				else
					divname.innerHTML='<font class="tips_true">输入正确</font>';
			});
		   
		}  
}
function isname2(){
	var nickName = $("#addNickName").val();
	var patrn = /^[\u4e00-\u9fa5]{2,7}$/;
	if (!patrn.exec(nickName)) 
	{  	
		divname2.innerHTML='<font class="tips_false">内容为2~7个汉字</font>';
	}else{  
		divname2.innerHTML='<font class="tips_true">输入正确</font>';
	}  
}

function checkPwd()  {  
	var pwd = $("#addPassword").val();
	var patrn=/^(\w){6,20}$/;  
	if (!patrn.exec(pwd)) {
		divpwd.innerHTML='<font class="tips_false">字母和数字6-20位组成</font>';
	}else
		divpwd.innerHTML='<font class="tips_true">输入正确</font>';
} 
function checkPwd2()  {  
	var pwd = $("#addPassword").val();
	var pwd2 = $("#addPassword2").val();
	var patrn=/^(\w){6,20}$/;  
	if (!patrn.exec(pwd2) || pwd!=pwd2) {
		divpwd2.innerHTML='<font class="tips_false">错误</font>';
	}else
		divpwd2.innerHTML='<font class="tips_true">输入正确</font>';
} 
function checkTel(){
	var tel = $("#addTel").val();
	var patrn = /^1[34578]\d{9}$/;
	if (!patrn.exec(tel)){
		divtel.innerHTML='<font class="tips_false">请输入正确号码</font>';
	}else
		divtel.innerHTML='<font class="tips_true">输入正确</font>';
}
function onlyNum(){
    if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
    if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))
    event.returnValue=false;  //执行至该语句时，阻止输入；可类比阻止冒泡原理或者禁止右键功能；
}
function onlyNum2(){
    if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
    if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))
    event.returnValue=false;  //执行至该语句时，阻止输入；可类比阻止冒泡原理或者禁止右键功能；
}
function checkTotalAssets(){
	var totalAssets = $("#addtotalAssets").val();
	var patrn = /^[0-9]+$|^[0-9]+\.[0-9]{1,2}$/;
	if(!patrn.exec(totalAssets)){
		divtotalAssets.innerHTML='<font class="tips_false">输入类型错误</font>';
	}
	else
		divtotalAssets.innerHTML='<font class="tips_true">输入正确</font>';
}

function updateUser(){
	var obj=document.getElementsByName('upcheckbox'); 
	var rids=new Array()
	var j=0;
	for(var i=0; i<obj.length; i++){ 
		if(obj[i].checked) {
			rids[j]=obj[i].value;
			j++;
		}else{
			
		}
	} 
	var id = $("#upId").val();
	var name = $("#upName").val();
	var nickName = $("#upNickName").val();
	var tel = $("#upTel").val();
	var address = $("#upAddress").val();
	var sex = $("#upSex").val();
	var totalAssets = $("#upTotalAssets").val();
	var totalLiability = $("#upTotalLiability").val();
	var creditConditions = $("#upcreditConditions").val();
	var industry = $("#upindustry").val();
	var estate = $("#upestate").val();
	var movable = $("#upmovable").val();
	var company = $("#upcompany").val();
	var solidSurfacing = $("#upsolidSurfacing").val();
	var sendInfo = {
			"id" : id,
			"name" : name,
			"nickName" : nickName,
			"tel" :tel,
			"address" : address,
			"gender" : sex,
			"totalAssets" : totalAssets,
			"totalLiability" :totalLiability,
			"creditConditions" : creditConditions,
			"industry" : industry,
			"estate" : estate,
			"movable" : movable,
			"company" : company,
			"solidSurfacing" : solidSurfacing,
			"rids" : rids
		};
		$.ajax({
			type : "POST",
			url : "updateUser",
			dataType : "json",
			contentType : 'application/json',
			data : JSON.stringify(sendInfo),
			success : function(data) {
				if (data.msg == "success") {
					alert("修改成功");
					window.location.href = "list";
				}else {
					alert("修改失败");
				}
			},
			error : function() {
				alert("网络异常，请稍后再试！");
			}
		});
}


</script>
<script type="text/javascript">
$("#userli").addClass("cur");
</script>
</html>