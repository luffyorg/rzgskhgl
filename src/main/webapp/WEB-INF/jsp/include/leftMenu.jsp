<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!--左侧边栏导航开始-->
		<div id="dcLeft">
			<div id="menu">
				<ul class="top">
					<li  id="indexli"><a href="${basePath}admin/index"><i class="home"></i><em>管理首页</em></a></li>
				</ul>
				<shiro:authenticated>
					<ul>
							<li id="resli""><a href="${basePath}admin/resource/list"><i
									class="nav"></i><em>资源管理</em></a></li>
							<li id="roleli"><a href="${basePath}admin/role/list"><i
									class="show"></i><em>角色管理</em></a></li>
							<li id="userli"><a href="${basePath}admin/user/list"><i
									class="page"></i><em>用户管理</em></a></li>
					</ul>
				
				<ul>
						<li id="productli"><a href="${basePath}admin/product/list"><i
								class="productCat"></i><em>产品管理</em></a></li>
				</ul>
				<ul class="bot">
					<li id="datali"><a href="backup.html"><i class="backup"></i><em>数据备份</em></a></li>
					<li id="adminli"><a href="manager.html"><i class="manager"></i><em>网站管理员</em></a></li>
					<li id="opli"><a href="manager_log.html"><i class="managerLog"></i><em>操作记录</em></a></li>
				</ul>
				</shiro:authenticated>
			</div>
		</div>
		<!--左侧边栏导航结束-->