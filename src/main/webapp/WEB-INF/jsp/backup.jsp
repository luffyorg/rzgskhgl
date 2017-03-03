<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
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
<title>首页</title>
<meta name="Copyright" content="Douco Design." />

</head>
<body>
	<!--
      作者：z.y.q.d
      时间：2016-10-21
      描述：后台管理
    -->
	<!--start-->
	<div id="dcWrap">
		<!--头部导航开始-->
		<%@ include file="include/header.jsp"%>
		<!--头部导航结束-->

		<!--左侧边栏导航开始-->
		<%@ include file="include/leftMenu.jsp"%>
		<!--左侧边栏导航结束-->


<!--主体内容部分开始-->
 <div id="dcMain">
   <!-- 当前位置 -->
  <div id="urHere">管理中心<b>></b><strong>数据备份</strong> </div>   
  <div class="mainBox" style="height:auto!important;height:550px;min-height:550px;">
    <h3><!-- <a href="backup.html?rec=restore" class="actionBtn">恢复备份</a> -->数据备份</h3>
        <table width="100%" border="0" cellpadding="8" cellspacing="0" class="table table-striped">
     <form name="myform" method="post" action="backup.php?rec=backup">
      <tr>
       <th>
        <input name='chkall' type='checkbox' id='chkall' onclick='selectcheckbox(this.form)' value='check' checked>
       </th>
       <th >数据表名</th>
       <th>类型</th>
       <th>记录数</th>
       <th>数据</th>
       <th>索引</th>
       <th>碎片</th>
      </tr>
            <tr>
       <td><input type=checkbox name=tables[] value=dou_admin checked></td>
       <td >user</td>
       <td>MyISAM</td>
       <td>${user }</td>
       <td>84</td>
       <td>2048</td>
       <td>0</td>
      </tr>
            <tr>
       <td><input type=checkbox name=tables[] value=dou_admin_log checked></td>
       <td >user_role</td>
       <td>MyISAM</td>
       <td>${userRole }</td>
       <td>660</td>
       <td>4096</td>
       <td>0</td>
      </tr>
        <tr>
       <td><input type=checkbox name=tables[] value=dou_admin_log checked></td>
       <td >role</td>
       <td>MyISAM</td>
       <td>${role }</td>
       <td>660</td>
       <td>4096</td>
       <td>0</td>
      </tr>
            <tr>
       <td><input type=checkbox name=tables[] value=dou_article checked></td>
       <td >role_resource</td>
       <td>MyISAM</td>
       <td>${roleResource }</td>
       <td>45KB</td>
       <td>2048</td>
       <td>0</td>
      </tr>
            <tr>
       <td><input type=checkbox name=tables[] value=dou_article_category checked></td>
       <td >resource</td>
       <td>MyISAM</td>
       <td>${resource }</td>
       <td>140</td>
       <td>2048</td>
       <td>0</td>
      </tr>
            <tr>
       <td><input type=checkbox name=tables[] value=dou_config checked></td>
       <td >product</td>
       <td>MyISAM</td>
       <td>${product }</td>
       <td>2284</td>
       <td>1024</td>
       <td>0</td>
      </tr>
          <tr>
       <td><input type=checkbox name=tables[] value=dou_config checked></td>
       <td >customer</td>
       <td>MyISAM</td>
       <td>${customer }</td>
       <td>2284</td>
       <td>1024</td>
       <td>0</td>
      </tr>
            <tr>
       <td><input type=checkbox name=tables[] value=dou_nav checked></td>
       <td >product_order</td>
       <td>MyISAM</td>
       <td>${order }</td>
       <td>1284</td>
       <td>2048</td>
       <td>0</td>
      </tr>
            <tr>
       <td><input type=checkbox name=tables[] value=dou_page checked></td>
       <td >send_sms</td>
       <td>MyISAM</td>
       <td>${sendSms }</td>
       <td>2148</td>
       <td>2048</td>
       <td>0</td>
      </tr>
           
            <tr>
       <td><input type=checkbox name=tables[] value=dou_page checked></td>
       <td >record</td>
       <td>MyISAM</td>
       <td>${record }</td>
       <td>2148</td>
       <td>2048</td>
       <td>0</td>
      </tr>
           
      <tr>
       <td height="26" colspan="7">
        <input type="hidden" name="token" value="82ace968" />
        <input type="hidden" name="totalsize" value="118">
        <input type="button" name="submit" class="btn" value="确定备份"  onClick="backUp()">
       </td>
      </tr>
     </form>
    </table>
           </div>
 </div>
<!--主体内容部分结束-->

<div class="clear"></div>
<!--底部开始-->
<div id="dcFooter">
 <div id="footer">
  <div class="line"></div>
  <ul>
   版权所有 © 2017 毕业设计，并保留所有权利。
  </ul>
 </div>
</div>
<!--底部结束-->
<div class="clear"></div> 

</div>
<!--end-->
</body>
<script type="text/javascript">
function backUp(){
	$.get("all", function(data){
		alert("数据已备份到：" + data)
	});
}


</script>
<script type="text/javascript">
$("#datali").addClass("cur");
</script>
</html>
