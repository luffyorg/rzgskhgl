package cn.yznu.rzgskhgl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import cn.yznu.rzgskhgl.pojo.AccessToken;
import cn.yznu.rzgskhgl.pojo.Order;
import cn.yznu.rzgskhgl.service.IProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-beans.xml", "classpath:spring-hibernate.xml",
		"classpath:spring-mvc.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class TestProduct {

	@Autowired
	private IProductService productService;
	@Test
	public void testMyOrder(){
		String hql = "from AccessToken  order by createDate desc  ";
		List<AccessToken> list = productService.queryForPage(hql, 0, 1);
		for(AccessToken a : list){
			System.out.println(a.getAccessToken());
		}
	}

}
