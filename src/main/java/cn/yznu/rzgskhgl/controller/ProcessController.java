package cn.yznu.rzgskhgl.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import cn.yznu.rzgskhgl.pojo.Order;
import cn.yznu.rzgskhgl.pojo.Product;
import cn.yznu.rzgskhgl.pojo.User;
import cn.yznu.rzgskhgl.service.IProductService;
import cn.yznu.rzgskhgl.service.IUserService;
import cn.yznu.rzgskhgl.util.Snippet;

/**
 * 业务流程处理 控制类
 * 
 * @author 张伟
 * 
 */
@Controller
@RequestMapping("/process")
public class ProcessController {
	Logger log = Logger.getLogger(ProcessController.class);

	@Autowired
	private IProductService productService;
	@Autowired
	private IUserService userService;

	@RequestMapping("/list")
	public ModelAndView list() {
		log.info("执行process/list 方法");
		ModelAndView mv = new ModelAndView();
		mv.addObject("products", productService.getAllProduct());
		mv.setViewName("process/list");
		return mv;
	}

	@RequestMapping(value = "buy/{id}", method = RequestMethod.GET)
	public ModelAndView buyProduct(@PathVariable int id, HttpServletRequest request) {
		log.info("开始执行process/buy 方法");
		ModelAndView mv = new ModelAndView();
		Order order = null;
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			mv.addObject("msg", "请登录！！！");

		} else {
			Product product = productService.load(Product.class, id);
			order = new Order();
			order.setProductName(product.getName());
			order.setProductPrice(product.getProductPrice());
			order.setOrderStatus(0);
			order.setDescription(product.getDescription());
			mv.addObject("users", productService.queryBuyUsers(product));
			mv.addObject("order", order);
		}
		mv.setViewName("process/order");
		return mv;
	}

	@RequestMapping(value = "queryBuyUser/{id}", method = RequestMethod.GET)
	public ModelAndView queryBuyUser(@PathVariable int id) {
		log.info("查询条件满足的客户");
		ModelAndView mv = new ModelAndView();
		Product product = productService.load(Product.class, id);
		int bmovable = product.getMovable();
		int bEstate = product.getEstate();
		int bSolidS = product.getSolidSurfacing();
		int bCompany = product.getCompany();
		log.info("产品的购买条件：bmovable=" + bmovable + ",bEstate=" + bEstate + ",bSolidS=" + bSolidS + ",bCompany="
				+ bCompany);
		List<User> users = productService.queryBuyUsers(product);
		System.out.println(">>>" + users);
		if (users == null || users.size() == 0) {
			mv.addObject("msg", "没有符合要求的客户");
		} else {
			mv.addObject("msg", "搜索完成");
		}
		mv.addObject("users", users);
		mv.setViewName("process/userList");
		return mv;
	}

	@RequestMapping(value = "buy/add", method = RequestMethod.POST)
	public String addOrder(Order order) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute("user");
		Snippet snippet = new Snippet();
		String str = snippet.getRandomString();
		// String hql = "from Order where isEnable = 1 and orderNo = "+str+"";
		// List<Order> list = productService.findHql(Order.class, hql);
		order.setOrderNo(str);
		order.setCreateDate(new Date());
		order.setSalesMan(user.getName());

		productService.save(order);
		return "redirect:/process/orderList";
	}

	@RequestMapping(value = "orderList")
	public ModelAndView orderList() {
		ModelAndView mv = new ModelAndView();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute("user");
		List<Order> orders = null;
		String msg = "";
		if (user == null) {
			mv.addObject("orders", new Order());
		} else {
			String sn = userService.RoleSnByUser(user);
			if (sn.equals("ADMIN")) {
				msg = "查询成功";
				String hql = "from Order where isEnable=1 order by  createDate desc";
				orders = userService.findHql(Order.class, hql);
			} else if (sn.equals("EMP")) {
				msg = "查询成功";
				String hql = "from Order where isEnable=1 and salesMan=? order by  createDate desc";
				orders = userService.findHql(hql, user.getName());
			} else {
				msg = "你没有权限查看";
			}

		}
		mv.addObject("msg", msg);
		mv.addObject("orders", orders);
		mv.setViewName("process/orderList");
		return mv;
	}

	@RequestMapping(value = "updateOrder/{id}")
	public @ResponseBody Order updateOrderStatus(@PathVariable int id) {
		log.info("更新订单状态");
		ModelMap mm = new ModelMap();
		Gson gson = new Gson();
		Order o = productService.load(Order.class, id);
		mm.addAttribute("order", o);
		return o;
	}

}
