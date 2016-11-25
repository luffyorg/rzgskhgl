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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.yznu.rzgskhgl.common.MakeOrderNum;
import cn.yznu.rzgskhgl.common.PageBean;
import cn.yznu.rzgskhgl.pojo.Order;
import cn.yznu.rzgskhgl.pojo.Product;
import cn.yznu.rzgskhgl.pojo.User;
import cn.yznu.rzgskhgl.service.IProductService;
import cn.yznu.rzgskhgl.service.IUserService;
import cn.yznu.rzgskhgl.util.SendMsg_webchinese;
import net.sf.json.JSONObject;

/**
 * 业务流程处理 控制类
 * 
 * @author 张伟
 * 
 */
@Controller
@RequestMapping("/admin/process")
public class ProcessController extends BaseController{
	Logger log = Logger.getLogger(ProcessController.class);

	@Autowired
	private IProductService productService;
	@Autowired
	private IUserService userService;

	@SuppressWarnings("static-access")
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		log.info("执行process/list 方法");
		ModelAndView mv = new ModelAndView();
		PageBean pb = new PageBean();
		String pagesize = request.getParameter("pageSize");
		String page1 = request.getParameter("page");
		if(pagesize ==null || pagesize.equals("")){
			pagesize = "10";
		}
		if(page1 ==null || page1.equals("")){
			page1 = "1";
		}
		int pageSize = Integer.parseInt(pagesize);
		int page = Integer.parseInt(page1);
		int count = productService.getCount(Product.class);
		int totalPage = pb.countTotalPage(pageSize, count); // 总页数
		int offset = pb.countOffset(pageSize, page); // 当前页开始记录
		int length = pageSize; // 每页记录数
		int currentPage = pb.countCurrentPage(page);
		List<Product> list = productService.queryForPage("from Product ORDER BY isEnable DESC,createDate DESC", offset, length); // 该分页的记录
		
		pb.setList(list);
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setTotalPage(totalPage);
		pb.setAllRow(count);
		mv.addObject("products", list);
		mv.addObject("pb", pb);
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

	@RequestMapping(value = "queryBuyUser", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject queryBuyUser(HttpServletRequest request) {
		log.info("查询条件满足的客户");
		Map<String ,Object> map = new HashMap<String,Object>();
		Product product = productService.findUniqueByProperty(Product.class, "productNo", request.getParameter("id"));
		int bmovable = product.getMovable();
		int bEstate = product.getEstate();
		int bSolidS = product.getSolidSurfacing();
		int bCompany = product.getCompany();
		log.info("产品的购买条件：bmovable=" + bmovable + ",bEstate=" + bEstate + ",bSolidS=" + bSolidS + ",bCompany="
				+ bCompany);
		List<User> users = productService.queryBuyUsers(product);
		System.out.println(">>>" + users);
		if (users == null || users.size() == 0) {
			map.put("msg", "没有符合要求的客户");
		} else {
			map.put("msg", "success");
		}
		map.put("users", users);
		JSONObject json = JSONObject.fromObject( map ); 
		return json;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "buyProduct", method = RequestMethod.POST)
	@ResponseBody
	public Map addOrder(@RequestBody JSONObject json) {
		log.info("购买产品");
		Map<String ,Object> map = new HashMap<String,Object>();
		String msg = "";
		User user = (User) getSessionUser();//获取当前系统的业务员
		Order order = new Order();
		String id = json.getString("id");
		String buyName = json.getString("buyName");
		int orderStatus = json.getInt("orderStatus");
		User u = new User();
		Product product = null;
		List<User> users = null;
		if(!buyName.equals("")){
			u = userService.findUniqueByProperty(User.class, "name", buyName);
		}
		
		if(u == null || u.equals("")){
			msg = "购买人不存在！";
		}else{
			product = productService.findUniqueByProperty(Product.class, "productNo", id);
			users = productService.queryBuyUsers(product);
			if(!users.contains(u)){
				msg = "购买人不符合购买条件！";
			}else if(u.getTotalAssets() < product.getProductPrice()){
				msg = "购买人的资产不足以购买止产品！";
			}else {
				MakeOrderNum mon = new MakeOrderNum();
				order.setOrderNo(mon.makeOrderNum());//生成订单号
				order.setCreateDate(new Date());
				order.setSalesMan(user.getName());
				order.setOrderStatus(orderStatus);
				order.setSalesManId(user.getId());
				order.setBuyNameId(u.getId());
				order.setBuyName(u.getName());
				order.setCreateName(user.getName());
				order.setCreateBy(user.getId().toString());
				order.setCreateDate(new Date());
				order.setIsEnable(1);
				order.setDescription(product.getDescription());
				productService.save(order);
				msg = "success";
			}
		}
		map.put("msg", msg);
		return map;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "orderList")
	public ModelAndView orderList() {
		log.info("查看订单列表");
		ModelAndView mv = new ModelAndView();
		User user = getSessionUser();
		List<Order> orders = null;
		String msg = "";
		if(user==null){
			msg = "请登录";
		}else{
			List<String> roles = userService.listRoleSnByUser(user);
			if (user == null) {
				mv.addObject("orders", new Order());
			} else {
				if(roles.contains("ADMIN")){
					msg = "查询成功";
					String hql = "from Order where isEnable=1 order by  createDate desc";
					orders = userService.findHql(Order.class, hql);
				} else {
					msg = "查询成功";
					String hql = "from Order where isEnable=1 and salesMan=? order by  createDate desc";
					orders = userService.findHql(hql, user.getName());
				}
			}
		}
		mv.addObject("msg", msg);
		mv.addObject("orders", orders);
		mv.setViewName("process/orderList");
		return mv;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "updateOrder",method=RequestMethod.POST)
	public @ResponseBody Map updateOrderStatus(HttpServletRequest request) {
		log.info("更新订单状态");
		Map<String,Object> map = new HashMap<String,Object>();
		String orderNo = request.getParameter("orderNo");
		int status = Integer.parseInt(request.getParameter("status"));
		Order o = productService.findUniqueByProperty(Order.class, "orderNo", orderNo);
		o.setOrderStatus(status);
		o.setUpdateBy(getSessionUser().getId().toString());
		o.setUpdateDate(new Date());
		o.setUpdateName(getSessionUser().getName());
		userService.save(o);
		map.put("msg", "success");
		map.put("id", o.getId());
		map.put("status", o.getOrderStatus());
		/*SendMsg_webchinese sendMsg = new SendMsg_webchinese();
		try {
			String result = sendMsg.SendMsgForUser("15826215565", o.getOrderStatus());
			log.info("发送短信返回结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return map;
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping(value="nextPage" ,method=RequestMethod.GET)
	@ResponseBody
	public JSONObject nextPage(HttpServletRequest request) {
		log.info("开始执行admin/process/nextPage 方法");
		Map<String,Object> map = new HashMap<String,Object>();
		PageBean pb = new PageBean();
		String pagesize = request.getParameter("pageSize");
		String page1 = request.getParameter("page");
		if(pagesize ==null || pagesize.equals("")){
			pagesize = "10";
		}
		if(page1 ==null || page1.equals("")){
			page1 = "1";
		}
		int pageSize = Integer.parseInt(pagesize);
		int page = Integer.parseInt(page1);
		int count = productService.getCount(Product.class);
		int totalPage = pb.countTotalPage(pageSize, count); // 总页数
		int offset = pb.countOffset(pageSize, page); // 当前页开始记录
		int length = pageSize; // 每页记录数
		int currentPage = pb.countCurrentPage(page);
		List<Product> list = productService.queryForPage("from Product ORDER BY isEnable DESC,createDate DESC", offset, length); // 该分页的记录
		pb.setList(list);
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setTotalPage(totalPage);
		pb.setAllRow(count);
		map.put("products", list);
		map.put("pb", pb);
		JSONObject json = JSONObject.fromObject( map ); 
		return json;

	}
	@RequestMapping(value="searchProduct" ,method=RequestMethod.GET)
	@ResponseBody
	public JSONObject searchProduct(HttpServletRequest request) {
		log.info("开始执行admin/process/searchProduct 方法");
		Map<String,Object> map = new HashMap<String,Object>();
		String productName=request.getParameter("productName");
		List<Product> lists = productService.findHql(Product.class,
				"from Product p where p.name='"+productName+"' OR p.productNo='"+productName+"'");
		map.put("products", lists);
		JSONObject json = JSONObject.fromObject( map ); 
		return json;
		
	}
}
