<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
    
    <!-- 分页开始 -->
	<div style="float:right;margin-top:15px;">
		<c:if test="${pb.currentPage==1 }">
			<a href="#">首页</a> 
		</c:if>
		<c:if test="${pb.currentPage!=1 }">
			<a href="${basePath }admin/role/list?pageSize=10&page=1">首页</a> 
		</c:if>
		<c:if test="${pb.hasPreviousPage==true}">
			<a href="${basePath }admin/role/list?pageSize=10&page=${pb.currentPage-1}"> ◄上一页</a>
		</c:if>
		<c:if test="${pb.hasPreviousPage==false}">
			<a href="#"> ◄上一页</a>
		</c:if>
		<c:if test="${pb.hasNextPage==true }">
			<a href="${basePath }admin/role/list?pageSize=10&page=${pb.currentPage+1}">下一页► </a> 
		</c:if>
		<c:if test="${pb.hasNextPage==false }">
			<a href="#">下一页► </a> 
		</c:if>
		<c:if test="${pb.totalPage==pb.currentPage }">
			<a href="#">末页</a> 
		</c:if>
		<c:if test="${pb.totalPage!=pb.currentPage }">
			<a href="${basePath }admin/role/list?pageSize=10&page=${pb.totalPage}">末页</a> 
		</c:if>
		总${pb.allRow }条，第${pb.currentPage}/${pb.totalPage }页，到第
		<input size=2 id="goInput" value='' />页,
		<input type="button"
			value="搜索" class="goButton" onclick="gotoPageByInput(${pb.totalPage });" />
	</div>
	<!-- 分页结束 -->