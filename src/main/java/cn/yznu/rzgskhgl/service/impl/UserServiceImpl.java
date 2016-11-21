package cn.yznu.rzgskhgl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.yznu.rzgskhgl.pojo.Resource;
import cn.yznu.rzgskhgl.pojo.Role;
import cn.yznu.rzgskhgl.pojo.User;
import cn.yznu.rzgskhgl.service.ICommonService;
import cn.yznu.rzgskhgl.service.IRoleService;
import cn.yznu.rzgskhgl.service.IUserService;
import cn.yznu.rzgskhgl.shiro.ShiroKit;

@Service("userService")
public class UserServiceImpl extends CommonServiceimpl implements IUserService {
	@Autowired
	@Qualifier("commonService")
	private ICommonService common;
	@Autowired
	@Qualifier("roleService")
	private IRoleService roleService;

	@Override
	public User getUserByNameAndPassword(String userName, String password) {

		String pw = ShiroKit.md5(password, userName);
		String hql = "from User where name=? and password=?";
		Object[] param = { userName, pw };
		User u = common.getSingleByHQL(hql, param);
		return u;
	}

	@Override
	public void add(User user) {
		if (ShiroKit.isEmpty(user.getName()) || ShiroKit.isEmpty(user.getPassword())) {
			throw new RuntimeException("用户名或者密码不能为空！");
		}
		user.setPassword(ShiroKit.md5(user.getPassword(), user.getName()));
		common.save(user);
	}

	@Override
	public List<User> getUsersByRoleId(Role role) {
		String sql = "SELECT u.* FROM user u,role r,user_role ur "
				+ "where u.id=ur.user_id and r.id=ur.role_id and role_id=?";
		return common.getListBySQL(User.class, sql, role.getId());
	}

	@Override
	public List<String> listRoleSnByUser(User user) {
		String sql = "select r.sn from UserRole ur,Role r,User u "
				+ "where u.id=ur.user.id and r.id=ur.role.id and u.id=?";
		return common.getListByHQL(sql, user.getId());
	}
	@Override
	public String RoleSnByUser(User user) {
		String sql = "select r.sn from UserRole ur,Role r,User u "
				+ "where u.id=ur.user.id and r.id=ur.role.id and u.id=?";
		return common.getSingleByHQL(sql, user.getId());
	}

	@Override
	public List<User> getAllUsers() {
		String hql = "from User";
		return common.findHql(User.class, hql);
	}

	@Override
	public User getUserByName(String name) {
		String hql = "from User where name=?";
		return common.getSingleByHQL(hql, name);
	}

	@Override
	public List<Resource> listAllResource(int uid) {
		String hql = "select res from User u,Resource res,UserRole ur,RoleResource rr where "
				+ "u.id=ur.user.id and ur.role.id=rr.role.id  and rr.resource.id=res.id and u.id=?";
		return common.getListByHQL(hql, uid);
	}

	@Override
	public void add(User user, List<Integer> rids) {
		this.add(user);
		for (int rid : rids) {
			roleService.addUserRole(user.getId(), rid);
		}

	}

	@Override
	public List<Role> listUserRole(Integer uid) {
		String hql = "select r from UserRole ur,Role r,User u where u.id=ur.user.id and r.id=ur.role.id and u.id=?";
		return common.getListByHQL(hql, uid);
	}
	@Override
	public void update(User user,List<Integer> rids) {
		roleService.deleteUserRoles(user.getId());
		for(int rid:rids) {
			roleService.addUserRole(user.getId(), rid);
		}
		common.saveOrUpdate(user);
	}
}
