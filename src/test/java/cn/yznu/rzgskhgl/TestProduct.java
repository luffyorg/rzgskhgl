package cn.yznu.rzgskhgl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import cn.yznu.rzgskhgl.common.PageBean;
import cn.yznu.rzgskhgl.pojo.Order;
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
		p.setName("产品6");
		p.setProductNo("6");
		productService.save(p);
		p = new Product();
		p.setName("产品7");
		p.setProductNo("7");
		productService.save(p);
		p = new Product();
		p.setName("产品8");
		p.setProductNo("8");
		productService.save(p);
		p = new Product();
		p.setName("产品9");
		p.setProductNo("9");
		productService.save(p);

		p = new Product();
		p.setName("产品10");
		p.setProductNo("10");
		productService.save(p);
	}

	@Test
	public void testUser() {
		Product p = productService.load(Product.class, 3);
		List<User> users = productService.queryBuyUsers(p);
		for (User u : users) {
			System.out.println(">>>>" + u.getName());
		}
	}

	@Test
	public void testOrder() {
		Order o = new Order();
		productService.save(o);
	}

	@Test
	public void testPage() {
		int count = productService.getCount(Product.class);
		int pageSize = 4;
		int page = 2;
		int totalPage = PageBean.countTotalPage(pageSize, count); // 总页数
		int offset = PageBean.countOffset(pageSize, page); // 当前页开始记录
		int length = pageSize; // 每页记录数
		int currentPage = PageBean.countCurrentPage(page);
		List<Product> list = productService.queryForPage("from Product", offset, length); // 该分页的记录
		for (Product p : list) {
			System.out.println(p.getName());
		}

	}
}
