package cn.yznu.rzgskhgl.service;

import java.util.List;

import cn.yznu.rzgskhgl.pojo.Resource;
import cn.yznu.rzgskhgl.pojo.User;



/**
 *
 * @author  张伟
 *
 */
public interface IResourceService extends ICommonService{
	
	/**
	 * 登陆用户检查
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User checkUserExits(User user) throws Exception;
	
	/**
	 * 根据姓名得到用户
	 * @param userName
	 * @return
	 */
	public User getUserByUserName(String userName);
	
	/**
	 * 根据用户得到所拥有的资源
	 * @param user
	 * @return
	 */
	public List<Resource> listResourceByUser(User user);
	
	/**
	 * 得到全部的资源
	 * @return
	 */
	public List<Resource> getAllRes();
	
 
}
