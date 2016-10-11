package cn.yznu.rzgskhgl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import cn.yznu.rzgskhgl.pojo.Product;
import cn.yznu.rzgskhgl.pojo.User;
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
	public void testList() {
		List<Product> pros = productService.getAllProduct();
		for (Product p : pros) {
			System.out.println(">>>" + p.getName());
		}
	}

	@Test
	public void testSave() {
		Product p = new Product();
		p.setName("产品1");
		p.setProductNo("1");
		productService.save(p);
		p = new Product();
		p.setName("产品2");
		p.setProductNo("2");
		productService.save(p);
		p = new Product();
		p.setName("产品3");
		p.setProductNo("3");
		productService.save(p);
		p = new Product();
		p.setName("产品4");
		p.setProductNo("4");
		productService.save(p);

		p = new Product();
		p.setName("产品5");
		p.setProductNo("5");
		productService.save(p);
	}
	
	@Test
	public void testUser(){
		Product p = productService.load(Product.class, 3);
		List<User> users = productService.queryBuyUsers(p);
		for(User u : users){
			System.out.println(">>>>"+u.getName());
		}
	}
}
