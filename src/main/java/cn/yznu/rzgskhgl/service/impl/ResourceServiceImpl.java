package cn.yznu.rzgskhgl.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.yznu.rzgskhgl.pojo.Resource;
import cn.yznu.rzgskhgl.pojo.User;
import cn.yznu.rzgskhgl.service.ICommonService;
import cn.yznu.rzgskhgl.service.IResourceService;

@Service("resourceService")
public class ResourceServiceImpl extends CommonServiceimpl implements IResourceService {
	@Autowired
	@Qualifier("commonService")
	private ICommonService common;
	public User checkUserExits(User user) throws Exception {
		//return this.commonDao.getUserByUseridAndUserNameExits(user);
		System.out.println("********");
		return null;
	}

	@Override
	public List<Resource> listResourceByUser(User user) {
		String sql = "SELECT res.* from user u, role r,resource res, "
				+ "role_resource rr, user_role ur "
				+ "where u.id =ur.user_id and r.id=ur.role_id and r.id=rr.role_id"
				+ " and rr.resource_id=res.id and u.id=?";
		return dao.getListBySQL(Resource.class, sql, user.getId());

	}

	@Override
	public User getUserByUserName(String userName) {
		User user = new User();
		user.setName("admin");
		user.setPassword("1");
		return user;
	}
	
	@Override
	public List<Resource> getAllRes(){
		String hql = "from Resource";
		return dao.findHql(Resource.class, hql);
	}
	
}
