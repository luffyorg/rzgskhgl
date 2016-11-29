package cn.yznu.rzgskhgl.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import cn.yznu.rzgskhgl.pojo.Customer;
import cn.yznu.rzgskhgl.pojo.Order;
import cn.yznu.rzgskhgl.pojo.Product;
import cn.yznu.rzgskhgl.pojo.SendSms;
import cn.yznu.rzgskhgl.pojo.User;
import cn.yznu.rzgskhgl.service.ICustomerService;
import cn.yznu.rzgskhgl.service.IProcessService;
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
@SuppressWarnings("deprecation")
@Controller
@RequestMapping("/admin/process")
public class ProcessController extends BaseController{
	Logger log = Logger.getLogger(ProcessController.class);

	@Autowired
	private IProductService productService;
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IProcessService processService;
	
	@Autowired
	private ICustomerService customerService;

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
			mv.addObject("users", productService.queryBuyCustomers(product));
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
		List<Customer> customers = productService.queryBuyCustomers(product);
		System.out.println(">>>" + customers);
		if (customers == null || customers.size() == 0) {
			map.put("msg", "没有符合要求的客户");
		} else {
			map.put("msg", "success");
		}
		map.put("customers", customers);
		JSONObject json = JSONObject.fromObject( map ); 
		return json;
	}
	@RequestMapping(value = "queryUserByNameOrCode", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject queryUserByNameOrCode(HttpServletRequest request) {
		log.info("查询条件满足的客户");
		Map<String ,Object> map = new HashMap<String,Object>();
		Product product = productService.findUniqueByProperty(Product.class, "productNo", request.getParameter("customer"));
		if(product==null){
			product = productService.findUniqueByProperty(Product.class, "name", request.getParameter("customer"));
		}
		if(product==null){
			map.put("msg", "error");
			JSONObject json = JSONObject.fromObject( map ); 
			return json;
		}
			
		int bmovable = product.getMovable();
		int bEstate = product.getEstate();
		int bSolidS = product.getSolidSurfacing();
		int bCompany = product.getCompany();
		log.info("产品的购买条件：bmovable=" + bmovable + ",bEstate=" + bEstate + ",bSolidS=" + bSolidS + ",bCompany="
				+ bCompany);
		List<Customer> customers = productService.queryBuyCustomers(product);
		System.out.println(">>>" + customers);
		if (customers == null || customers.size() == 0) {
			map.put("msg", "没有符合要求的客户");
		} else {
			map.put("msg", "success");
		}
		map.put("msg", "success");
		map.put("customers", customers);
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
		Customer customer = new Customer();
		Product product = null;
		List<Customer> customers = null;
		if(!buyName.equals("")){
			customer = userService.findUniqueByProperty(Customer.class, "name", buyName);
		}
		
		if(customer == null || customer.equals("")){
			msg = "购买人不存在！";
		}else{
			product = productService.findUniqueByProperty(Product.class, "productNo", id);
			customers = productService.queryBuyCustomers(product);
			if(!customers.contains(customer)){
				msg = "购买人不符合购买条件！";
			}/*else if(customer.getTotalAssets() < product.getProductPrice()){
				msg = "购买人的资产不足以购买止产品！";
			}else if(!customer.getCreditConditions().equals("合格")){
				msg = "购买人的征信情况不合格！";
			}*/
			else {
				MakeOrderNum mon = new MakeOrderNum();
				order.setOrderNo(mon.makeOrderNum());//生成订单号
				order.setCreateDate(new Date());
				order.setSalesMan(user.getName());
				order.setOrderStatus(orderStatus);
				order.setSalesManId(user.getId());
				order.setBuyNameId(customer.getId());
				order.setBuyName(customer.getName());
				order.setCreateName(user.getName());
				order.setCreateBy(user.getId().toString());
				order.setCreateDate(new Date());
				order.setIsEnable(1);
				order.setDescription(product.getDescription());
				order.setProductName(product.getName());
				order.setProductPrice(product.getProductPrice());
				productService.save(order);
				
				SendMsg_webchinese sendMsg = new SendMsg_webchinese();
				String orderStatus2 = sendMsg.orderStatus(orderStatus);
				SendSms sms = new SendSms();
				sms.setCreateBy(getSessionUser().getId().toString());
				sms.setCreateDate(new Date());
				sms.setCreateName(getSessionUser().getName());
				sms.setStatus(0);
				sms.setSmsText("亲，你已成功在XX融资公司购买产品【"+order.getProductName()+"】，订单号：【"+order.getOrderNo()+"】，"
						+ "订单状态为：【"+orderStatus2+"】。如有问题，请联系tel：1330000000");
				sms.setSmsMob(user.getTel());
				processService.saveOrUpdate(sms);
				/*try {
					String result = sendMsg.SendMsgForUser(sms);
					log.info("发送短信返回结果：" + result);
				} catch (Exception e) {
					e.printStackTrace();
				}*/
				
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
					msg = "查询成功";
					String hql = "from Order where salesMan=? order by  createDate desc";
					orders = userService.findHql(hql, user.getName());
			}
		}
		mv.addObject("msg", msg);
		mv.addObject("orders", orders);
		mv.setViewName("process/orderList");
		return mv;
	}
	@SuppressWarnings("unused")
	@RequestMapping(value = "allOrderList")
	public ModelAndView allOrderList() {
		log.info("查看全部订单列表");
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
					String hql = "from Order  order by  createDate desc";
					orders = userService.findHql(Order.class, hql);
				} else {
					msg = "你没有权限，请联系管理员！";
					orders = null;
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
		Order o = processService.queryOrderbByOrderNo( orderNo);
		o.setUpdateBy(getSessionUser().getId().toString());
		o.setUpdateDate(new Date());
		o.setUpdateName(getSessionUser().getName());
		o.setIsEnable(0);
		userService.saveOrUpdate(o);
		Order order = new Order();
		order.setOrderNo(orderNo);
		order.setOrderStatus(status);
		order.setBuyName(o.getBuyName());
		order.setBuyNameId(o.getBuyNameId());
		order.setProductName(o.getProductName());
		order.setProductNum(o.getProductNum());
		order.setProductPrice(o.getProductPrice());
		order.setDescription(o.getDescription());
		order.setSalesMan(o.getSalesMan());
		order.setSalesManId(o.getSalesManId());
		order.setIsEnable(1);
		order.setCreateBy(getSessionUser().getId().toString());
		order.setCreateDate(new Date());
		order.setCreateName(getSessionUser().getName());
		productService.save(order);
		map.put("msg", "success");
		map.put("id", o.getId());
		map.put("status", status);
		Customer customer = customerService.load(Customer.class, o.getBuyNameId());
		SendMsg_webchinese sendMsg = new SendMsg_webchinese();
		String orderStatus = sendMsg.orderStatus(status);
		SendSms sms = new SendSms();
		sms.setCreateBy(getSessionUser().getId().toString());
		sms.setCreateDate(new Date());
		sms.setCreateName(getSessionUser().getName());
		sms.setStatus(0);
		sms.setSmsText("亲，你在XX融资公司购买的产品，订单号：【"+order.getOrderNo()+"】订单状态已更新为：【"+orderStatus+"】。如有问题，请联系：1330000000");
		sms.setSmsMob(customer.getTel());
		processService.saveOrUpdate(sms);
		/*try {
			String result = sendMsg.SendMsgForUser(sms);
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
	
	@RequestMapping("exportOrder")
	public void exportOrder(HttpServletRequest request,HttpServletResponse response) {
		log.info("导出订单");
		// 获取需要导出的数据List
		String hql = "from Order where isEnable=1 order by  createDate desc";
		List<Order> orders = processService.findHql(Order.class, hql);
		// 使用方法生成excle模板样式
		HSSFWorkbook workbook = processService.createExcel(orders, request);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // 定义文件名格式

		try {
			// 定义excle名称 ISO-8859-1防止名称乱码
			String msg = new String(("订单信息_" + format.format(new Date()) + ".xls").getBytes(), "ISO-8859-1");
			// 以导出时间作为文件名
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment;filename=" + msg);
			workbook.write(response.getOutputStream());
		} catch (IOException e) {
			log.error(e);
		}
	}
	@SuppressWarnings("static-access")
	@RequestMapping("/search")
	@ResponseBody
	public JSONObject search(HttpServletRequest request) {
		log.info("产品购买---搜索产品");
		Map<String,Object> map = new HashMap<String,Object>();
		PageBean pb = new PageBean();
		String pagesize = request.getParameter("pageSize");
		String page1 = request.getParameter("page");
		String estate1 = request.getParameter("estate");
		String movable1 = request.getParameter("movable");
		String solidSurfacing1 = request.getParameter("solidSurfacing");
		String company1 = request.getParameter("company");
		String productNo = request.getParameter("productNo");
		
		if (pagesize == null || pagesize.equals("")) {
			pagesize = "10";
		}
		if (page1 == null || page1.equals("")) {
			page1 = "1";
		}
		int pageSize = Integer.parseInt(pagesize);
		int page = Integer.parseInt(page1);
		
		
		int offset = pb.countOffset(pageSize, page); // 当前页开始记录
		int length = pageSize; // 每页记录数
		int currentPage = pb.countCurrentPage(page);
		String hql = "from Product p where 1=1 ";
		String hqlCount="select count(*) from Product p where 1=1 ";
		if(productNo != null && !productNo.equals("")){
			hql += "and CONCAT(p.name,p.productNo) LIKE '%"+productNo+"%'";
			hqlCount += "and CONCAT(p.name,p.productNo) LIKE '%"+productNo+"%'";
		}
		if(estate1 != null && !estate1.equals("") && !estate1.equals("2")){
			int estate = Integer.parseInt(estate1);
			hql += "and p.estate = "+estate+" ";
			hqlCount += "and p.estate = "+estate+" ";
		}
		if(movable1 != null && !movable1.equals("")&&  !movable1.equals("2")){
			int movable = Integer.parseInt(movable1);
			hql += "and p.movable = "+movable+" ";
			hqlCount += "and p.movable = "+movable+" ";
		}
		if(solidSurfacing1 != null &&  !solidSurfacing1.equals("")&& !solidSurfacing1.equals("2")){
			int solidSurfacing = Integer.parseInt(solidSurfacing1);
			hql += "and p.movable = "+solidSurfacing+" ";
			hqlCount += "and p.movable = "+solidSurfacing+" ";
		}
		if(company1 != null && !company1.equals("")&& company1 != null && !company1.equals("2")){
			int company = Integer.parseInt(company1);
			hql += "and p.company = "+company+" ";
			hqlCount += "and p.company = "+company+" ";
		}
		hql += "ORDER BY p.isEnable DESC,p.createDate DESC";
		List<Product> list = productService.queryForPage(hql, offset,
				length); // 该分页的记录
		int count = productService.getCountByParam(hqlCount);
		int totalPage = pb.countTotalPage(pageSize, count); // 总页数
		pb.setList(list);
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setTotalPage(totalPage);
		pb.setAllRow(count);
		map.put("products", list);
		map.put("pb", pb);
		JSONObject jsonObject = JSONObject.fromObject(map);
		return jsonObject;

	}
	
	
}
