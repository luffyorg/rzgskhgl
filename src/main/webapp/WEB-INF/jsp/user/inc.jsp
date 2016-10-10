<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <jsp:include page="/jsp/common.jsp"></jsp:include>
<span>
<a href="<%=request.getContextPath() %>/admin/user/add" class="admin_link">添加用户</a>
<a href="<%=request.getContextPath() %>/admin/user/list" class="admin_link">用户列表</a>
</span>