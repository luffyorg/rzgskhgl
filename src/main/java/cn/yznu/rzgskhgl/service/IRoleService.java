package cn.yznu.rzgskhgl.service;

import java.util.List;

import cn.yznu.rzgskhgl.pojo.Resource;
import cn.yznu.rzgskhgl.pojo.Role;
import cn.yznu.rzgskhgl.pojo.RoleResource;
import cn.yznu.rzgskhgl.pojo.UserRole;

public interface IRoleService extends ICommonService {

	public List<Role> listRole();

	public UserRole loadUserRole(int uid, int roleId);

	public void addUserRole(int uid, int roleId);

	public void deleteUserRole(int uid, int roleId);

	/**
	 * 根据角色id获取可以访问的所有资源
	 * 
	 * @param roleId
	 * @return
	 */
	public List<Resource> listRoleResource(int roleId);
	
	public void deleteUserRoles(int uid);
	
	public void addRoleResource(int roleId, int resId);
	
	public void deleteRoleResource(int roleId, int resId);
	
	public RoleResource loadResourceRole(int roleId, int resId);

}
