package cn.yznu.rzgskhgl.service;

import java.util.List;

import cn.yznu.rzgskhgl.pojo.Resource;
import cn.yznu.rzgskhgl.pojo.Role;
import cn.yznu.rzgskhgl.pojo.RoleResource;
import cn.yznu.rzgskhgl.pojo.UserRole;

public interface IRoleService extends ICommonService {
	/**
	 * 得到全部角色
	 * @return
	 */
	public List<Role> listRole();
	/**
	 * 根据角色id和用户id 查询对应的用户角色
	 * @param uid
	 * @param roleId
	 * @return
	 */
	public UserRole loadUserRole(int uid, int roleId);
	/**
	 * 根据用户id 和 角色id 添加用户角色
	 * @param uid
	 * @param roleId
	 */
	public void addUserRole(int uid, int roleId);
	/**
	 * 删除用户角色 根据用户id和角色id
	 * @param uid
	 * @param roleId
	 */
	public void deleteUserRole(int uid, int roleId);

	/**
	 * 根据角色id获取可以访问的所有资源
	 * 
	 * @param roleId
	 * @return
	 */
	public List<Resource> listRoleResource(int roleId);
	
	/**
	 * 根据用户id 删除对应的角色
	 * @param uid
	 */
	public void deleteUserRoles(int uid);
	
	/**
	 * 根据角色id和资源id 添加角色资源
	 * @param roleId
	 * @param resId
	 */
	public void addRoleResource(int roleId, int resId);
	
	/**
	 * 根据角色id 和资源id 删除对应的角色资源
	 * @param roleId
	 * @param resId
	 */
	public void deleteRoleResource(int roleId, int resId);
	
	/**
	 * 根据角色id 和资源id 查询对应的角色资源
	 * @param roleId
	 * @param resId
	 * @return
	 */
	public RoleResource loadResourceRole(int roleId, int resId);
	
	/**
	 * 根据角色id删除对应的角色资源集合
	 * @param roleId
	 * @return
	 */
	public int deleteRoleResourceByRole(int roleId);
	/**
	 * 根据角色id 删除用户角色关联
	 * @param roleId
	 * @return
	 */
	public int deleteRoleUserByRole(int roleId);
	
}
