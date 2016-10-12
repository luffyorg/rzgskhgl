package cn.yznu.rzgskhgl.service;

import java.util.List;

import cn.yznu.rzgskhgl.controller.ProductController;
import cn.yznu.rzgskhgl.pojo.Product;
import cn.yznu.rzgskhgl.pojo.Resource;
import cn.yznu.rzgskhgl.pojo.Role;
import cn.yznu.rzgskhgl.pojo.User;


/**
 * 用户服务接口
 * @author 张伟
 *
 */
public interface IUserService extends ICommonService{
	/**
	 * 根据姓名查询对象
	 * @param name
	 * @return
	 */
	public User getUserByName(String name);
	
	/**
	 * 增加用户
	 * @param user
	 */
	public void add(User user);
	
	/**
	 * 得到全部的用户
	 * @return 用户集合
	 */
	public List<User> getAllUsers();
	/**
	 * 根据用户名和密码查询用户
	 * @param userName 用户名
	 * @param password 密码
	 * @return 用户
	 */
	public User getUserByNameAndPassword(String userName,String password);
	/**
	 * 根据角色id查询所对应有多少个用户
	 * @param role 角色对象
	 * @return 用户集合
	 */
	public List<User> getUsersByRoleId(Role role);
	/**
	 * 根据用户查询所对应的sn
	 * @param user 用户对象
	 * @return sn
	 */
	public List<String> listRoleSnByUser(User user);
	/**
	 * 根据用户查询所对应的sn
	 * @param user 用户对象
	 * @return sn
	 */
	public String RoleSnByUser(User user);
	
	/**
	 * 得到一个用户的所有资源权限
	 * @param uid 用户id
	 * @return
	 */
	public List<Resource> listAllResource(int uid);
	
	public void add(User user, List<Integer> rids);
	
	public List<Role> listUserRole(Integer uid);
	
	public void update(User user,List<Integer> rids);
	
	
}
