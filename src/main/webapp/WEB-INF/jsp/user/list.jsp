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
<title>用户管理</title>
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

.inp_name {
	width: 120px;
	height: 30px;
	line-height: 30px;
	border: 1px solid #d8d8d8;
	border-radius: 4px;
	font-size: 16px;
	color: #333;
	font-family: '微软雅黑';
	margin-left: 10px;
	padding: 0 14px;
	margin-top: 8px;
}

.label_select {
	font-size: 16px;
	color: #333;
	font-family: "微软雅黑";
	margin-top: -5px;
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
  function tc(){
    $("body").append("<div id='mask'></div>");
    $("#mask").addClass("mask").fadeIn("slow");
    $(".tc").fadeIn("slow");
  }; 
  function tcUpdate(id,name,tel){
	  $("#upId").val(id);
	  $("#upName").val(name);
	  $("#upTel").val(tel);
	  
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
	  $("#upTel").val("");
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
					<a onclick="tc();" class="actionBtn">添加用户</a> 自定义用户 <input
						type="text" class="inp_name" placeholder="登录名或手机号" id="searchName" />
					<input type="button" value="搜索" class="inp_btn" onclick="search()" />
				</h3>
				<div class="navList" id="main">
					<table width="100%" border="0" cellpadding="10" cellspacing="0"
						class="tableBasic">
						<tr>
							<th>序号</th>
							<th>登录名称</th>
							<th>电话</th>
							<th>用户状态</th>
							<th>用户操作</th>
						</tr>
						<tbody>
							<c:forEach items="${users }" var="user" varStatus="i">
								<tr>
									<td align="center">${i.count + (pb.currentPage-1)*10}</td>
									<td align="center">${user.name }</td>
									<td align="center">${user.tel }</td>
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
											<a
												onclick="tcUpdate('${user.id }','${user.name }','${user.tel }');"
												class="updateColor">更新</a> | <a
												href="${basePath }admin/user/listRes/${user.id }"
												class="setReColor">查看资源</a>
										</shiro:hasAnyRoles></td>
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
						<td><input type="text" id="addName" value="" size="80" onkeyup="checkName();"
							class="inpMain" placeholder="2-25位字符串" maxlength="25" /> <span
							class="tips" id="divname">2-25位字符串</span></td>
					</tr>
					<tr>
						<td height="35" align="right">密码：</td>
						<td><input type="password" id="addPassword" value=""
							size="80" onblur="checkPwd()" class="inpMain" onkeyup="checkPwd();"
							placeholder="密码为字母和数字6-20位组成" maxlength="20" /><span class="tips"
							id="divpwd"></span></td>
					</tr>
					<tr>
						<td height="35" align="right">确认密码：</td>
						<td><input type="password" id="addPassword2" value="" 
							size="80" maxlength="20" class="inpMain" onkeyup="checkPwd2()" /><span
							class="tips" id="divpwd2"></span></td>
					</tr>
					<tr>
						<td height="35" align="right">电话：</td>
						<td><input type="text" name="addTel" id="addTel" value=""
							onkeyup="checkTel()" maxlength="11"
							size="80" class="inpMain" /><span class="tips" id="divtel">请输入正确手机号码</span></td>
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
								<label for="captcha_${role.id }"> <input type="checkbox"
									name="checkbox" id="checkbox" value="${role.id }">
									${role.name }
								</label>
							</c:forEach></td>
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
					<tr style="display: none">
						<td height="35" align="right">用户标识：</td>
						<td><input type="text" name="upId" id="upId" value=""
							size="80" class="inpMain" /></td>
					</tr>
					<tr>
						<td height="35" align="right">登录账户：</td>
						<td><input type="text" name="upName" id="upName" value="" onkeyup="checkUpName()" maxlength="25"
							size="80" class="inpMain" readonly="readonly"/><span class="tips"
							id="updivname">2-25位字符串</span></td>
					</tr>
					<tr>
						<td height="35" align="right">密码：</td>
						<td><input type="password" id="upPassword" value=""
							size="80" onkeyup="checkUpPwd()" class="inpMain"
							placeholder="密码为字母和数字6-20位组成" maxlength="20" /><span class="tips"
							id="updivpwd">字母和数字6-20位</span></td>
					</tr>
					<tr>
						<td height="35" align="right">确认密码：</td>
						<td><input type="password" id="upPassword2" value="" onkeyup="checkUpPwd2()"
							size="80" maxlength="20" class="inpMain" /><span
							class="tips" id="updivpwd2"></span></td>
					</tr>
					<tr>
						<td height="35" align="right">电 话：</td>
						<td><input type="text" name="upTel" id="upTel" value="" maxlength="11" onkeyup="checkUpTel()"
							size="80" class="inpMain" /><span class="tips"
							id="updivtel">请输入正确手机号</span></td>
					</tr>
					<tr>
					<tr>
						<td height="35" align="right">角色：</td>
						<td><c:forEach var="role" items="${roles }">
								<label for="captcha_${role.id }"> <input type="checkbox"
									name="upcheckbox" id="upcheckbox${role.id }"
									value="${role.id }"> ${role.name }
								</label>
							</c:forEach></td>
					</tr>
					<tr>
						<td></td>
						<td><input id="updateBtn" class="btn" type="button"
							value="提交" onclick="updateUser();" /></td>
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
	if(checkName() && checkPwd() && checkPwd2() && checkTel()){
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
		var pwd = $("#addPassword").val();
		var pwd2 = $("#addPassword2").val();
		var tel = $("#addTel").val();
		var isEnable = $("#addisEnable").val();
		var sendInfo = {
				"name" : name,
				"pwd" : pwd,
				"tel" :tel,
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
	else{
		
	}
}
function check(){
	var name = $("#addName").val();
	var pwd = $("#addPassword").val();
	var pwd2 = $("#addPassword2").val();
	var tel = $("#addTel").val();
	var isEnable = $("#addisEnable").val();
}

function checkName(){
	var name = $("#addName").val();
	var patrn = /^(?!_+$)[a-z\d_]{2,25}$/;
	if (!patrn.test(name)) 
	{  	
		divname.innerHTML='<font class="tips_false">2-25位长度</font>';
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

function checkPwd()  {  
	var pwd = $("#addPassword").val();
	var patrn=/^(\w){6,20}$/;  
	if (!patrn.test(pwd)) {
		divpwd.innerHTML='<font class="tips_false">字母和数字6-20位组成</font>';
		return false;
	}else{
		divpwd.innerHTML='<font class="tips_true">输入正确</font>';
		return true;
	}
		
} 
function checkPwd2()  {  
	var pwd = $("#addPassword").val();
	var pwd2 = $("#addPassword2").val();
	var patrn=/^(\w){6,20}$/;  
	if (!patrn.test(pwd2) || pwd!=pwd2) {
		divpwd2.innerHTML='<font class="tips_false">密码不同</font>';
		return false;
	}else{
		divpwd2.innerHTML='<font class="tips_true">输入正确</font>';
		return true;
	}
		
} 
function checkTel(){
	var tel = $("#addTel").val();
	var patrn = /^1[34578]\d{9}$/;
	if (!patrn.exec(tel)){
		divtel.innerHTML='<font class="tips_false">请输入正确号码</font>';
		return false;
	}else{
		divtel.innerHTML='<font class="tips_true">输入正确</font>';
		return true;
	}

}

function checkUpPwd()  {  
	var pwd = $("#upPassword").val();
	var patrn=/^(\w){6,20}$/;  
	if (!patrn.exec(pwd)) {
		updivpwd.innerHTML='<font class="tips_false">字母和数字6-20位组成</font>';
		return false;
	}else{
		updivpwd.innerHTML='<font class="tips_true">输入正确</font>';
		return true;
	}
		
} 
function checkUpPwd2()  {  
	var pwd = $("#upPassword").val();
	var pwd2 = $("#upPassword2").val();
	var patrn=/^(\w){6,20}$/;  
	if (!patrn.exec(pwd2) || pwd!=pwd2) {
		updivpwd2.innerHTML='<font class="tips_false">密码不同</font>';
		return false;
	}else{
		updivpwd2.innerHTML='<font class="tips_true">输入正确</font>';
		return true;
	}
		
} 
function checkUpTel(){
	var tel = $("#upTel").val();
	var patrn = /^1[34578]\d{9}$/;
	if (!patrn.exec(tel)){
		updivtel.innerHTML='<font class="tips_false">请输入正确号码</font>';
		return false;
	}else{
		updivtel.innerHTML='<font class="tips_true">输入正确</font>';
		return true;
	}

}
function updateUser(){
	if(checkUpPwd() && checkUpPwd2() && checkUpTel()){
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
		var tel = $("#upTel").val();
		var password = $("#upPassword").val();
		var sendInfo = {
				"id" : id,
				"name" : name,
				"password" : password,
				"tel" :tel,
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
	}else{
		
	}
	
}
function search() {
	var name = $("#searchName").val();
		$.get("search?pageSize="+10+"&page="+1+"&name="+name+"", function(data){
		 //组装表格
		var htmlStr = "<table width='100%'  border='0' cellpadding='10' cellspacing='0' class='tableBasic'>";
		htmlStr += "<tr> <th width='80'>序号</th>"+
			      "<th width='80'>姓名</th>"+
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

</script>
<script type="text/javascript">
$("#userli").addClass("cur");
</script>
</html>