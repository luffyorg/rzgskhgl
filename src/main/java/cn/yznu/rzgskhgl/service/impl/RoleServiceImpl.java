package cn.yznu.rzgskhgl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.yznu.rzgskhgl.pojo.Resource;
import cn.yznu.rzgskhgl.pojo.Role;
import cn.yznu.rzgskhgl.pojo.RoleResource;
import cn.yznu.rzgskhgl.pojo.User;
import cn.yznu.rzgskhgl.pojo.UserRole;
import cn.yznu.rzgskhgl.service.ICommonService;
import cn.yznu.rzgskhgl.service.IRoleService;

@Service("roleService")
public class RoleServiceImpl extends CommonServiceimpl implements IRoleService {
	@Autowired
	@Qualifier("commonService")
	private ICommonService common;

	public List<Role> listRole() {
		String hql = "from Role";
		return common.findHql(Role.class, hql);
	}

	public UserRole loadUserRole(int uid, int roleId) {
		String hql = "SELECT ur.* from user_role ur WHERE ur.user_id=? and ur.role_id=?";
		Object[] obj = { uid, roleId };
		return common.getSingleBySQL(UserRole.class, hql, obj);
	}

	public void addUserRole(int uid, int roleId) {
		UserRole ur = null;
		ur = loadUserRole(uid, roleId);
		User user = new User();
		user.setId(uid);
		Role role = new Role();
		role.setId(roleId);
		if (ur == null) {
			ur = new UserRole();
			ur.setUser(user);
			ur.setRole(role);
			ur.setIsEnable(1);
			common.save(ur);
		}
	}

	public void deleteUserRole(int uid, int roleId) {
		UserRole ur = null;
		ur = loadUserRole(uid, roleId);
		if (ur != null) {
			common.delete(ur);
		}
	}

	public List<Resource> listRoleResource(int roleId) {
		String hql = "select res from Role role,Resource res,RoleResource rr where "
				+ "role.id=rr.role.id and res.id=rr.resource.id and role.id=?";

		return common.getListByHQL(hql, roleId);
	}

	@Override
	public void deleteUserRoles(int uid) {
		String hql = "delete UserRole ur where ur.user.id=?";
		common.executeHql(hql, uid);

	}

	@Override
	public void addRoleResource(int roleId, int resId) {
		RoleResource rr = null;
		Resource res = null;
		Role role = null;
		rr = loadResourceRole(roleId, resId);
		if (rr == null) {
			rr = new RoleResource();
			res = new Resource();
			role = new Role();
			res.setId(resId);
			role.setId(roleId);
			rr.setResource(res);
			rr.setRole(role);
			common.save(rr);
		}

	}

	@Override
	public void deleteRoleResource(int roleId, int resId) {
		RoleResource rr = null;
		rr = loadResourceRole(roleId, resId);
		if (rr != null) {
			common.delete(rr);
		}
	}

	@Override
	public RoleResource loadResourceRole(int roleId, int resId) {
		String hql = "select rr from RoleResource rr where rr.role.id=? and rr.resource.id=?";
		Object[] values = { roleId, resId };
		return common.getSingleByHQL(hql, values);
	}

}
