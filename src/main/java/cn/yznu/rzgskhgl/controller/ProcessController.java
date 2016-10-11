package cn.yznu.rzgskhgl.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.yznu.rzgskhgl.pojo.Product;
import cn.yznu.rzgskhgl.pojo.User;
import cn.yznu.rzgskhgl.service.IProductService;

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
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			mv.addObject("msg", "请登录！！！");

		} else {
			Product product = productService.load(Product.class, id);
			int bmovable = product.getMovable();
			int bEstate = product.getEstate();
			int bSolidS = product.getSolidSurfacing();
			int bCompany = product.getCompany();
			log.info("产品的购买条件：bmovable=" + bmovable + ",bEstate=" + bEstate + ",bSolidS=" + bSolidS + ",bCompany="
					+ bCompany);
			int movable = user.getMovable();
			int estate = user.getEstate();
			int solids = user.getSolidSurfacing();
			int company = user.getCompany();
			log.info("登录用户的资产情况：" + user.getMovable() + "," + user.getEstate() + "," + user.getSolidSurfacing() + ","
					+ user.getCompany());
			if (bEstate > estate) {
				mv.addObject("msg", "对不起,你没有房产,不符合购买条件！！！");
			} else if (bmovable > movable) {
				mv.addObject("msg", "对不起,你没有动产,不符合购买条件！！！");
			} else if (bSolidS > solids) {
				mv.addObject("msg", "对不起,你没有实体铺面,不符合购买条件！！！");
			} else if (bCompany > company) {
				mv.addObject("msg", "对不起,你没有公司,不符合购买条件！！！");
			} else {
				mv.addObject("msg", "购买成功");
			}
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

}
