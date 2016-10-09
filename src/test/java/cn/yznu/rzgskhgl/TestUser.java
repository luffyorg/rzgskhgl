package cn.yznu.rzgskhgl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.sun.org.glassfish.external.statistics.annotations.Reset;

import cn.yznu.rzgskhgl.dao.IBaseDao;
import cn.yznu.rzgskhgl.pojo.Resource;
import cn.yznu.rzgskhgl.pojo.Role;
import cn.yznu.rzgskhgl.pojo.User;
import cn.yznu.rzgskhgl.service.IUserService;
import cn.yznu.rzgskhgl.shiro.ShiroKit;
import net.sf.json.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-beans.xml", "classpath:spring-hibernate.xml",
"classpath:spring-mvc.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class TestUser {
	@Autowired
	IUserService userService;

	@Autowired
	IBaseDao dao;
	@Test
	public void testBatchSave() {
		List<User> users = new ArrayList<User>();
		User u1 = new User();
		u1.setName("张伟");
		u1.setPassword(ShiroKit.md5("1", "张伟"));
		u1.setCompany(1);
		u1.setAddress("长江师范学院");
		u1.setEstate(1);
		u1.setGender("男");
		u1.setIndustry("外包");
		u1.setMovable(1);
		u1.setIsEnable(1);
		u1.setTel(Long.parseLong("13100000001"));
		u1.setTotalAssets(12333343.5);
		u1.setTotalLiability(123.3);
		u1.setCreateBy("1");
		u1.setCreateDate(new Date());
		u1.setCreateName("snake");
		
		User u2 = new User();
		u2.setName("秦士昱");
		u2.setPassword(ShiroKit.md5("1", "秦士昱"));
		u2.setCompany(1);
		u2.setAddress("长江师范学院");
		u2.setEstate(1);
		u2.setGender("男");
		u2.setIndustry("外包");
		u2.setMovable(1);
		u2.setIsEnable(1);
		u2.setTel(Long.parseLong("13100000002"));
		u2.setTotalAssets(12333343.5);
		u2.setTotalLiability(123.3);
		u2.setCreateBy("1");
		u2.setCreateDate(new Date());
		u2.setCreateName("snake");
		
		User u3 = new User();
		u3.setName("代黎明");
		u3.setPassword(ShiroKit.md5("1", "代黎明"));
		u3.setCompany(1);
		u3.setAddress("长江师范学院");
		u3.setEstate(1);
		u3.setGender("男");
		u3.setIndustry("外包");
		u3.setMovable(1);
		u3.setIsEnable(1);
		u3.setTel(Long.parseLong("13100000003"));
		u3.setTotalAssets(12333343.5);
		u3.setTotalLiability(123.3);
		u3.setCreateBy("1");
		u3.setCreateDate(new Date());
		u3.setCreateName("snake");
		
		User u4 = new User();
		u4.setName("殷绍也");
		u4.setPassword(ShiroKit.md5("1", "殷绍也"));
		u4.setCompany(1);
		u4.setAddress("长江师范学院");
		u4.setEstate(1);
		u4.setGender("男");
		u4.setIndustry("外包");
		u4.setMovable(1);
		u4.setIsEnable(1);
		u4.setTel(Long.parseLong("13100000004"));
		u4.setTotalAssets(12333343.5);
		u4.setTotalLiability(123.3);
		u4.setCreateBy("1");
		u4.setCreateDate(new Date());
		u4.setCreateName("snake");
		users.add(u1);
		users.add(u2);
		users.add(u3);
		users.add(u4);
		userService.batchSave(users);
	}
	
	@Test
	public void testSave(){
		User u4 = new User();
		u4.setName("admin");
		u4.setPassword(ShiroKit.md5("1", "admin"));
		u4.setCompany(1);
		u4.setAddress("长江师范学院");
		u4.setEstate(1);
		u4.setGender("男");
		u4.setIndustry("外包");
		u4.setMovable(1);
		u4.setIsEnable(1);
		u4.setTel(Long.parseLong("13100000000"));
		u4.setTotalAssets(12333343.5);
		u4.setTotalLiability(123.3);
		u4.setCreateBy("1");
		u4.setCreateDate(new Date());
		u4.setCreateName("admin");
		userService.save(u4);
	}
	
	
	@Test
	public void testGetUser(){
		List<User> users = userService.loadAll(User.class);
		for(User u :users){
			System.out.println("====" + u.getName());
		}
	}
	
	@Test
	public void testLogin(){
		JSONObject json = new JSONObject();
		json.put("loginname", "张伟");
		json.put("password", "1");
		
	}
	@Test
	public void testGetUsersByRole(){
		Role role = new Role();
		role.setId(2);
		List<User> users = userService.getUsersByRoleId(role);
		for(User u : users){
			System.out.println(u.getName());
		}
	}
	
	@Test
	public void testAllUser(){
		List<User> users = userService.findHql(User.class,"from User" );
		for(User u : users){
			System.out.println(u.getName());
		}
	}
	
	@Test
	public void testAllRes(){
		List<Resource> lists = userService.listAllResource(1);
		for(Resource r : lists){
			System.out.println(">>>>" + r.getName());
		}
			
	}
	
}
