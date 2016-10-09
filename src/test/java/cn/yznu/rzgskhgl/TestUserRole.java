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

import cn.yznu.rzgskhgl.pojo.Role;
import cn.yznu.rzgskhgl.pojo.User;
import cn.yznu.rzgskhgl.pojo.UserRole;
import cn.yznu.rzgskhgl.service.IUserService;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-beans.xml", "classpath:spring-hibernate.xml",
"classpath:spring-mvc.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class TestUserRole {
	
	@Autowired
	private IUserService userService;
	
	
	@Test
	public void testSaveUserRole(){
		UserRole ur1 = new UserRole();
		UserRole ur2 = new UserRole();
		UserRole ur3 = new UserRole();
		User u1 = userService.get(User.class, 1);
		User u2 = userService.get(User.class, 2);
		User u3 = userService.get(User.class, 3);
		User u4 = userService.get(User.class, 4);
		Role r1 = userService.get(Role.class, 1);
		Role r2= userService.get(Role.class, 2);
		Role r3 = userService.get(Role.class, 3);
		ur1.setRole(r1);
		ur1.setUser(u1);
		ur1.setIsEnable(1);
		ur1.setRole(r1);
		ur1.setUser(u2);
		ur1.setIsEnable(1);
		ur1.setRole(r1);
		ur1.setUser(u3);
		ur1.setIsEnable(1);
		ur1.setRole(r1);
		ur1.setUser(u4);
		ur1.setIsEnable(1);
		List<UserRole> urs = new ArrayList<UserRole>();
		urs.add(ur1);
		urs.add(ur2);
		urs.add(ur3);
		userService.batchSave(urs);
		
	}

}
