package cn.yznu.rzgskhgl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import cn.yznu.rzgskhgl.pojo.Resource;
import cn.yznu.rzgskhgl.pojo.Role;
import cn.yznu.rzgskhgl.pojo.RoleResource;
import cn.yznu.rzgskhgl.pojo.User;
import cn.yznu.rzgskhgl.pojo.UserRole;
import cn.yznu.rzgskhgl.service.IRoleService;
import cn.yznu.rzgskhgl.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-beans.xml", "classpath:spring-hibernate.xml",
		"classpath:spring-mvc.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class TestRole {

	@Autowired
	IRoleService roleService;
	@Autowired
	IUserService userService;
	@Test
	public void testSaveRole(){
		List<Role> roles = new ArrayList<Role>();
		Role r1 = new Role();
		r1.setName("管理员");
		r1.setSn("ADMIN");
		r1.setIsEnable(1);
		Role r2 = new Role();
		r2.setName("会员");
		r2.setSn("VIP");
		r2.setIsEnable(1);
		Role r3 = new Role();
		r3.setName("普通用户");
		r3.setSn("EMP");
		r3.setIsEnable(1);
		roles.add(r1);
		roles.add(r2);
		roles.add(r3);
		
		roleService.batchSave(roles);
	}
	
	@Test
	public void teseQuery(){
		User u = new User();
		u.setId(5);
		List<String> sn = userService.listRoleSnByUser(u);
			System.out.println(sn);
	}
	
	@Test
	public void testloadUserRole(){
		UserRole ur = roleService.loadUserRole(5, 1);
		System.out.println(">>>" + ur);
	}
	@Test
	public void testsaveUserRole(){
		roleService.addUserRole(5, 2);
	}
	
	@Test
	public void testLoadAllResR(){
		List<Resource> rr =roleService.listRoleResource(2);
		for(Resource r: rr){
			System.out.println(r.getName()+ ">>>" + r.getUrl());
		}
	}
	
	@Test
	public void testSave(){
		String hql = "from Role where name=? or sn=? and isEnable=0";
		Object[] values = {"1","test1"};
		Role role = roleService.getSingleByHQL(hql, values);
		System.out.println(role);
	}
	
}
