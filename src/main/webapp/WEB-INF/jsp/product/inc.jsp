<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <jsp:include page="/jsp/common.jsp"></jsp:include>
<span>
<a href="<%=request.getContextPath() %>/admin/product/add" class="admin_link">添加产品</a>
<a href="<%=request.getContextPath() %>/admin/product/list" class="admin_link">产品列表</a>
<a href="<%=request.getContextPath() %>/process/list" class="admin_link">购买产品</a>
</span>