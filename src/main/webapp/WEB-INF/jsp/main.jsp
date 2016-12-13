<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("path", path);
	request.setAttribute("basePath", basePath);
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户主页</title>
<meta name="Copyright" content="Douco Design." />
<link href="${basePath }static/css/public.css" rel="stylesheet" type="text/css">
<link href="${basePath }static/css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${basePath }static/js/jquery.min.js"></script>
<script type="text/javascript" src="${basePath }static/js/global.js"></script>
</head>
<body>
<!--
      作者：z.y.q.d
      时间：2016-10-21
      描述：用户主页
    -->
<!--start-->
  <!--导航start-->
  <div class="navbox">
    <div class="nav">
      <div class="nav_left">
        <a href="/">
          <img src="${basePath }static/images/logo1.png" alt="snake">
        </a>
      </div>
      <div class="nav_right">
        <ul>
          <li>主页</li>
          <li>关于我们</li>
          <li>新闻简讯</li>
          <li>产品介绍</li>
          <li>合作伙伴</li>
        </ul>
      </div>
    </div>
  </div>

  <!--banner start-->
  <div class="banner">
    
  </div>

  <!--main start-->
  <div class="bigbox">
  <div class="main_box">
    <div class="main_bl">
      <h1>产品介绍</h1>
      <span>Products</span>
      <ul>
      	<c:forEach items="${products }" var="product">
      	<li><a>${product.name }</a></li>
      	</c:forEach>
        <!-- <li><a style="background-color:#5a75b8;">税银通</a></li>
        <li><a>融资担保</a></li>
        <li><a>见贷即保</a></li>
        <li><a>接力担</a></li>
        <li><a>科技文创企业小额信用担保</a></li> -->
      </ul>
    </div>
    <div class="main_br">
      <h3>
                ${product.name }</h3>
                                <h4>
                    产品简介</h4>
                <p> ${product.description } </p>
                                                <h4>
                    适用对象</h4>
                <p>${product.suitable } </p>
                                                <h4>
                    办理条件</h4>
                <p>须有：
                <c:if test="${product.estate==1 }">房产</c:if>
                <c:if test="${product.movable==1 }">动产</c:if>
                <c:if test="${product.company==1 }">公司</c:if>
                <c:if test="${product.solidSurfacing==1 }">实体铺面</c:if> </p>
                                                <h4>
                    业务流程</h4>
                <p>签订合同---收齐资料---递交渠道处---审核阶段---下款---收费---完成服务</p>
                                                <h4>
                    产品定价</h4>
                <p>
                    ${product.productPrice }</p>
                                                <h4>
                    注意事项</h4>
                <div>  <p>暂无</p> <br /> 

    </div>
  </div>
  </div>
<!--end-->

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
</html>