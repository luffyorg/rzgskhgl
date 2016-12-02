package cn.yznu.rzgskhgl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.sun.org.glassfish.external.statistics.annotations.Reset;

import cn.yznu.rzgskhgl.dao.IBaseDao;
import cn.yznu.rzgskhgl.pojo.Customer;
import cn.yznu.rzgskhgl.pojo.Resource;
import cn.yznu.rzgskhgl.pojo.Role;
import cn.yznu.rzgskhgl.pojo.User;
import cn.yznu.rzgskhgl.service.IUserService;
import cn.yznu.rzgskhgl.shiro.ShiroKit;
import cn.yznu.rzgskhgl.util.DateUtil;
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
		u1.setIsEnable(1);
		u1.setTel(Long.parseLong("13100000001"));
		u1.setCreateBy("1");
		u1.setCreateDate(new Date());
		u1.setCreateName("snake");
		
		User u2 = new User();
		u2.setName("秦士昱");
		u2.setPassword(ShiroKit.md5("1", "秦士昱"));
		u2.setIsEnable(1);
		u2.setTel(Long.parseLong("13100000002"));
		u2.setCreateBy("1");
		u2.setCreateDate(new Date());
		u2.setCreateName("snake");
		
		User u3 = new User();
		u3.setName("代黎明");
		u3.setPassword(ShiroKit.md5("1", "代黎明"));
		u3.setIsEnable(1);
		u3.setTel(Long.parseLong("13100000003"));
		u3.setCreateBy("1");
		u3.setCreateDate(new Date());
		u3.setCreateName("snake");
		
		User u4 = new User();
		u4.setName("殷绍也");
		u4.setPassword(ShiroKit.md5("1", "殷绍也"));
		u4.setIsEnable(1);
		u4.setTel(Long.parseLong("13100000004"));
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
		u4.setPassword(ShiroKit.md5("111111", "admin"));
		u4.setIsEnable(1);
		u4.setTel(Long.parseLong("13100000000"));
		u4.setCreateBy("1");
		u4.setCreateDate(new Date());
		u4.setCreateName("admin");
		userService.save(u4);
	}
	
	@Test
	public void testUpdate(){
		User u = userService.load(User.class,1);
		u.setPassword(ShiroKit.md5("1", u.getName()));
		userService.saveOrUpdate(u);
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
	@Test
	public void testLogin2(){
		User u = new User();
		u.setPassword("111111");
		u.setName("admin");
		userService.save(u);
	}
	
	@Test
	public void testCustomer(){
		String hql = "from User";
		List<User> list = userService.findHql(User.class, hql);
		List<String> m = DateUtil.beforeJune();
		Map<String,List<Integer>> map = new HashMap<String,List<Integer>>();
		for(User c : list){
			List<Integer> strName = new ArrayList<Integer>();
			int i=0;
			for(String str : m){
				
				String hql2 = "select count(*) from Order o where salesMan='"+c.getName()+"' and years='"+str+"'";
				int count = userService.getCountByParam(hql2);
				strName.add(i++, count);
			}
			map.put(c.getName(), strName);
		}
		JSONObject json = JSONObject.fromObject(map);
		System.out.println(">>>>" + json);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
