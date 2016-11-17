package cn.yznu.rzgskhgl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import cn.yznu.rzgskhgl.pojo.Resource;
import cn.yznu.rzgskhgl.service.ICommonService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-beans.xml", "classpath:spring-hibernate.xml",
"classpath:spring-mvc.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class TestResource {

	@Autowired
	private ICommonService commonService;
	
	
	@Test
	public void testsave() {
		
		Resource res = new Resource();
		res.setName("系统管理");
		res.setUrl("/admin/**");
		commonService.save(res);
		
		res = new Resource();
		res.setName("用户管理");
		res.setUrl("/admin/user/*");
		commonService.save(res);
		
		res = new Resource();
		res.setName("用户添加");
		res.setUrl("/admin/user/save");
		commonService.save(res);
		
		res = new Resource();
		res.setName("用户更新");
		res.setUrl("/admin/user/update");
		commonService.save(res);
		
		res = new Resource();
		res.setName("用户删除");
		res.setUrl("/admin/user/delete");
		commonService.save(res);
		
		res = new Resource();
		res.setName("角色管理");
		res.setUrl("/admin/role/*");
		commonService.save(res);
		
		res = new Resource();
		res.setName("角色添加");
		res.setUrl("/admin/role/save");
		commonService.save(res);
		
		res = new Resource();
		res.setName("角色修改");
		res.setUrl("/admin/role/update");
		commonService.save(res);
		res = new Resource();
		
		res.setName("资源管理");
		res.setUrl("/admin/resource/*");
		commonService.save(res);
		res = new Resource();
		res.setName("资源修改");
		res.setUrl("/admin/resource/update");
		commonService.save(res);
		res = new Resource();
		res.setName("资源添加");
		res.setUrl("/admin/resource/save");
		commonService.save(res);
		res = new Resource();
		res.setName("资源删除");
		res.setUrl("/admin/resource/delete");
		commonService.save(res);
	}
	public void delete(){
		Resource r = commonService.load(Resource.class, 19);
		System.out.println(r);
		commonService.delete(r);
	}
	
}
