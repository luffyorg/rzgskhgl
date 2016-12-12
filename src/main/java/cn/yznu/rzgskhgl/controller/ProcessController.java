package cn.yznu.rzgskhgl.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import cn.yznu.rzgskhgl.pojo.Record;
import cn.yznu.rzgskhgl.pojo.SendSms;
import cn.yznu.rzgskhgl.pojo.User;
import cn.yznu.rzgskhgl.service.ICustomerService;
import cn.yznu.rzgskhgl.service.IProcessService;
import cn.yznu.rzgskhgl.service.IProductService;
import cn.yznu.rzgskhgl.service.IRecordService;
import cn.yznu.rzgskhgl.service.IUserService;
import cn.yznu.rzgskhgl.util.DateJsonValueProcessor;
import cn.yznu.rzgskhgl.util.DateUtil;
import cn.yznu.rzgskhgl.util.SendMsg_webchinese;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 业务流程处理 控制类
 * 
 * @author 张伟
 * 
 */
@SuppressWarnings("deprecation")
@Controller
@RequestMapping("/admin/process")
public class ProcessController extends BaseController {
	Logger log = Logger.getLogger(ProcessController.class);

	@Autowired
	private IProductService productService;
	@Autowired
	private IUserService userService;

	@Autowired
	private IProcessService processService;

	@Autowired
	private ICustomerService customerService;

	@Autowired
	private IRecordService iRecordService;

	@SuppressWarnings("static-access")
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		log.info("执行process/list 方法");
		ModelAndView mv = new ModelAndView();
		PageBean pb = new PageBean();
		String pagesize = request.getParameter("pageSize");
		String page1 = request.getParameter("page");
		if (pagesize == null || pagesize.equals("")) {
			pagesize = "10";
		}
		if (page1 == null || page1.equals("")) {
			page1 = "1";
		}
		int pageSize = Integer.parseInt(pagesize);
		int page = Integer.parseInt(page1);
		int count = productService.getCount(Product.class);
		int totalPage = pb.countTotalPage(pageSize, count); // 总页数
		int offset = pb.countOffset(pageSize, page); // 当前页开始记录
		int length = pageSize; // 每页记录数
		int currentPage = pb.countCurrentPage(page);
		List<Product> list = productService.queryForPage("from Product ORDER BY isEnable DESC,createDate DESC", offset,
				length); // 该分页的记录

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
		Map<String, Object> map = new HashMap<String, Object>();
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
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}

	@RequestMapping(value = "queryUserByNameOrCode", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject queryUserByNameOrCode(HttpServletRequest request) {
		log.info("查询条件满足的客户");
		Map<String, Object> map = new HashMap<String, Object>();
		Product product = productService.findUniqueByProperty(Product.class, "productNo",
				request.getParameter("customer"));
		if (product == null) {
			product = productService.findUniqueByProperty(Product.class, "name", request.getParameter("customer"));
		}
		if (product == null) {
			map.put("msg", "error");
			JSONObject json = JSONObject.fromObject(map);
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
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "buyProduct", method = RequestMethod.POST)
	@ResponseBody
	public Map addOrder(@RequestBody JSONObject json, HttpServletRequest request) {
		log.info("购买产品");
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		User user = (User) getSessionUser();// 获取当前系统的业务员
		Order order = new Order();
		String id = json.getString("id");
		String buyName = json.getString("buyName");
		int orderStatus = json.getInt("orderStatus");
		Customer customer = new Customer();
		Product product = null;
		List<Customer> customers = null;
		if (!buyName.equals("")) {
			customer = userService.findUniqueByProperty(Customer.class, "name", buyName);
		}

		if (customer == null || customer.equals("")) {
			msg = "购买人不存在！";
		} else {
			product = productService.findUniqueByProperty(Product.class, "productNo", id);
			customers = productService.queryBuyCustomers(product);
			if (!customers.contains(customer)) {
				msg = "购买人不符合购买条件！";
			} /*
				 * else if(customer.getTotalAssets() <
				 * product.getProductPrice()){ msg = "购买人的资产不足以购买止产品！"; }else
				 * if(!customer.getCreditConditions().equals("合格")){ msg =
				 * "购买人的征信情况不合格！"; }
				 */
			else {
				MakeOrderNum mon = new MakeOrderNum();
				order.setOrderNo(mon.makeOrderNum());// 生成订单号
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
				order.setYears(DateUtil.currentYears());
				productService.save(order);

				Record record = new Record();
				record.setUserid(getSessionUser().getId());
				record.setIpv4(getIpAddr(request));
				record.setRecord(
						"用户ID:" + customer.getId() + ",购买产品:" + product.getName() + ",订单号:" + order.getOrderNo());
				record.setTime(getTime());
				iRecordService.add(record);

				SendMsg_webchinese sendMsg = new SendMsg_webchinese();
				String orderStatus2 = sendMsg.orderStatus(orderStatus);
				SendSms sms = new SendSms();
				sms.setCreateBy(getSessionUser().getId().toString());
				sms.setCreateDate(new Date());
				sms.setCreateName(getSessionUser().getName());
				sms.setStatus(0);
				sms.setSmsText("亲，你已成功在XX融资公司购买产品【" + order.getProductName() + "】，订单号：【" + order.getOrderNo() + "】，"
						+ "订单状态为：【" + orderStatus2 + "】。如有问题，请联系tel：1330000000");
				sms.setSmsMob(user.getTel());
				processService.saveOrUpdate(sms);
				/*
				 * try { String result = sendMsg.SendMsgForUser(sms);
				 * log.info("发送短信返回结果：" + result); } catch (Exception e) {
				 * e.printStackTrace(); }
				 */

				msg = "success";
			}
		}
		map.put("msg", msg);
		return map;
	}

	@SuppressWarnings({ "unused", "static-access" })
	@RequestMapping(value = "orderList")
	public ModelAndView orderList(HttpServletRequest request) {
		log.info("查看订单列表");
		ModelAndView mv = new ModelAndView();
		PageBean pb = new PageBean();
		String pagesize = request.getParameter("pageSize");
		String page1 = request.getParameter("page");
		if (pagesize == null || pagesize.equals("")) {
			pagesize = "10";
		}
		if (page1 == null || page1.equals("")) {
			page1 = "1";
		}
		int pageSize = Integer.parseInt(pagesize);
		int page = Integer.parseInt(page1);

		User user = getSessionUser();
		List<Order> orders = null;
		String msg = "";
		String hqlCount = "select count(*) from Order where isEnable=1 and salesMan='" + user.getName() + "' ";
		int count = processService.getCountByParam(hqlCount);
		int totalPage = pb.countTotalPage(pageSize, count); // 总页数
		int offset = pb.countOffset(pageSize, page); // 当前页开始记录
		int length = pageSize; // 每页记录数
		int currentPage = pb.countCurrentPage(page);
		if (user == null) {
			msg = "请登录";
		} else {
			List<String> roles = userService.listRoleSnByUser(user);
			if (user == null) {
				mv.addObject("orders", new Order());
			} else {
				msg = "查询成功";
				String hql = "from Order where isEnable=1 and salesMan='" + user.getName()
						+ "' order by  createDate desc";
				orders = processService.queryForPage(hql, offset, length); // 该分页的记录
			}
		}

		pb.setList(orders);
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setTotalPage(totalPage);
		pb.setAllRow(count);
		mv.addObject("pb", pb);
		mv.addObject("msg", msg);
		mv.addObject("orders", orders);
		mv.setViewName("process/orderList");
		return mv;
	}

	@SuppressWarnings({ "unused", "static-access" })
	@RequestMapping(value = "allOrderList")
	public ModelAndView allOrderList(HttpServletRequest request) {
		log.info("查看全部订单列表");
		ModelAndView mv = new ModelAndView();
		PageBean pb = new PageBean();
		String pagesize = request.getParameter("pageSize");
		String page1 = request.getParameter("page");
		if (pagesize == null || pagesize.equals("")) {
			pagesize = "10";
		}
		if (page1 == null || page1.equals("")) {
			page1 = "1";
		}
		int pageSize = Integer.parseInt(pagesize);
		int page = Integer.parseInt(page1);

		User user = getSessionUser();
		List<Order> orders = null;
		String msg = "";
		String hqlCount = "select count(*) from Order where isEnable=1  ";
		int count = processService.getCountByParam(hqlCount);
		int totalPage = pb.countTotalPage(pageSize, count); // 总页数
		int offset = pb.countOffset(pageSize, page); // 当前页开始记录
		int length = pageSize; // 每页记录数
		int currentPage = pb.countCurrentPage(page);
		if (user == null) {
			msg = "请登录";
		} else {
			List<String> roles = userService.listRoleSnByUser(user);
			if (user == null) {
				mv.addObject("orders", new Order());
			} else {
				msg = "查询成功";
				String hql = "from Order where isEnable=1  order by  createDate desc";
				orders = processService.queryForPage(hql, offset, length); // 该分页的记录
			}
		}
		// 获取全部业务员
		String hqlCustomer = "from Customer";
		List<Customer> customers = userService.findHql(Customer.class, hqlCustomer);
		pb.setList(orders);
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setTotalPage(totalPage);
		pb.setAllRow(count);
		mv.addObject("pb", pb);
		mv.addObject("msg", msg);
		mv.addObject("orders", orders);
		mv.addObject("customers", customers);
		mv.setViewName("process/orderAllList");
		return mv;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "updateOrder", method = RequestMethod.POST)
	public @ResponseBody Map updateOrderStatus(HttpServletRequest request) {
		log.info("更新订单状态");
		Map<String, Object> map = new HashMap<String, Object>();
		String orderNo = request.getParameter("orderNo");
		int status = Integer.parseInt(request.getParameter("status"));
		Order o = processService.queryOrderbByOrderNo(orderNo);
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
		order.setCreateBy(o.getCreateBy());
		order.setCreateDate(o.getCreateDate());
		order.setCreateName(o.getCreateName());
		order.setUpdateBy(getSessionUser().getId().toString());
		order.setUpdateDate(new Date());
		order.setUpdateName(getSessionUser().getName());
		productService.save(order);
		map.put("msg", "success");
		map.put("id", o.getId());
		map.put("status", status);
		Customer customer = customerService.load(Customer.class, o.getBuyNameId());
		SendMsg_webchinese sendMsg = new SendMsg_webchinese();
		String orderStatus = sendMsg.orderStatus(status);

		Record record = new Record();
		record.setUserid(getSessionUser().getId());
		record.setIpv4(getIpAddr(request));
		record.setRecord("用户ID:" + o.getBuyNameId() + ",购买的产品:" + o.getProductName() + ",订单号:" + order.getOrderNo()
				+ ",状态更新为" + orderStatus);
		record.setTime(getTime());
		iRecordService.add(record);

		SendSms sms = new SendSms();
		sms.setCreateBy(getSessionUser().getId().toString());
		sms.setCreateDate(new Date());
		sms.setCreateName(getSessionUser().getName());
		sms.setStatus(0);
		sms.setSmsText(
				"亲，你在XX融资公司购买的产品，订单号：【" + order.getOrderNo() + "】订单状态已更新为：【" + orderStatus + "】。如有问题，请联系：1330000000");
		sms.setSmsMob(customer.getTel());
		processService.saveOrUpdate(sms);
		/*
		 * try { String result = sendMsg.SendMsgForUser(sms);
		 * log.info("发送短信返回结果：" + result); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
		return map;
	}

	/**
	 * 产品购买 下一页
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "nextPage", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject nextPage(HttpServletRequest request) {
		log.info("产品购买--开始执行admin/process/nextPage 方法");
		Map<String, Object> map = new HashMap<String, Object>();
		PageBean pb = new PageBean();
		String pagesize = request.getParameter("pageSize");
		String page1 = request.getParameter("page");
		if (pagesize == null || pagesize.equals("")) {
			pagesize = "10";
		}
		if (page1 == null || page1.equals("")) {
			page1 = "1";
		}
		int pageSize = Integer.parseInt(pagesize);
		int page = Integer.parseInt(page1);
		int count = productService.getCount(Product.class);
		int totalPage = pb.countTotalPage(pageSize, count); // 总页数
		int offset = pb.countOffset(pageSize, page); // 当前页开始记录
		int length = pageSize; // 每页记录数
		int currentPage = pb.countCurrentPage(page);
		List<Product> list = productService.queryForPage("from Product ORDER BY isEnable DESC,createDate DESC", offset,
				length); // 该分页的记录
		pb.setList(list);
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setTotalPage(totalPage);
		pb.setAllRow(count);
		map.put("products", list);
		map.put("pb", pb);
		JSONObject json = JSONObject.fromObject(map);
		return json;

	}

	@RequestMapping(value = "searchProduct", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject searchProduct(HttpServletRequest request) {
		log.info("开始执行admin/process/searchProduct 方法");
		Map<String, Object> map = new HashMap<String, Object>();
		String productName = request.getParameter("productName");
		List<Product> lists = productService.findHql(Product.class,
				"from Product p where p.name='" + productName + "' OR p.productNo='" + productName + "'");
		map.put("products", lists);
		JSONObject json = JSONObject.fromObject(map);
		return json;

	}

	@RequestMapping("exportOrder")
	public void exportOrder(HttpServletRequest request, HttpServletResponse response) {
		log.info("导出订单");
		String orderNo = request.getParameter("orderNo");// 订单号
		String createDate = request.getParameter("createDate");// 创建时间
		String orderStatus = request.getParameter("orderStatus");// 订单状态

		// 获取需要导出的数据List
		String hql = "from Order o where 1=1 and isEnable =1 and salesMan='" + getSessionUser().getName() + "' ";
		if (orderNo != null && !orderNo.equals("")) {
			hql += "and o.orderNo = '" + orderNo + "' ";
		}
		if (createDate != null && !createDate.equals("")) {
			hql += "and o.createDate < '" + createDate + "' ";
		}
		if (orderStatus != null && !orderStatus.equals("")) {
			hql += "and o.orderStatus = " + orderStatus + " ";
		}
		hql += "ORDER BY o.createDate DESC";
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
			String method = request.getMethod();
			if (!"GET".equals(method)) {
				Record record = new Record();
				record.setUserid(getSessionUser().getId());
				record.setIpv4(getIpAddr(request));
				record.setRecord("导出订单信息");
				record.setTime(getTime());
				iRecordService.add(record);
			}

		} catch (IOException e) {
			log.error(e);
		}
	}

	/**
	 * 导出全部订单
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("exportAllOrder")
	public void exportAllOrder(HttpServletRequest request, HttpServletResponse response) {
		log.info("导出全部订单");
		String orderNo = request.getParameter("orderNo");// 订单号
		String name = request.getParameter("name");// 业务员账户
		String createDate = request.getParameter("createDate");// 创建时间
		String orderStatus = request.getParameter("orderStatus");// 订单状态

		// 获取需要导出的数据List
		String hql = "from Order o where 1=1 and isEnable =1 ";
		if (orderNo != null && !orderNo.equals("")) {
			hql += "and o.orderNo = '" + orderNo + "' ";
		}
		if (createDate != null && !createDate.equals("")) {
			hql += "and o.createDate < '" + createDate + "' ";
		}
		if (name != null && !name.equals("")) {
			hql += "and o.salesMan = '" + name + "' ";
		}
		if (orderStatus != null && !orderStatus.equals("")) {
			hql += "and o.orderStatus = " + orderStatus + " ";
		}
		hql += "ORDER BY o.createDate DESC";
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
			String method = request.getMethod();
			if (!"GET".equals(method)) {
				Record record = new Record();
				record.setUserid(getSessionUser().getId());
				record.setIpv4(getIpAddr(request));
				record.setRecord("导出全部订单信息");
				record.setTime(getTime());
				iRecordService.add(record);
			}
		} catch (IOException e) {
			log.error(e);
		}
	}

	@SuppressWarnings("static-access")
	@RequestMapping("/search")
	@ResponseBody
	public JSONObject search(HttpServletRequest request) {
		log.info("订单列表---搜索订单");
		Map<String, Object> map = new HashMap<String, Object>();
		PageBean pb = new PageBean();
		String pagesize = request.getParameter("pageSize");// 每页显示的数量
		String page1 = request.getParameter("page");// 当前页
		String orderNo = request.getParameter("orderNo");// 订单号
		String name = request.getParameter("name");// 业务员账户
		String createDate = request.getParameter("createDate");// 创建时间
		String orderStatus = request.getParameter("orderStatus");// 订单状态

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
		String hql = "from Order o where 1=1 and isEnable =1 and salesMan='" + getSessionUser().getName() + "' ";
		String hqlCount = "select count(*) from Order o where 1=1 and isEnable =1 and salesMan='"
				+ getSessionUser().getName() + "' ";
		if (orderNo != null && !orderNo.equals("")) {
			hql += "and o.orderNo = '" + orderNo + "' ";
			hqlCount += "and o.orderNo = '" + orderNo + "' ";
		}
		if (createDate != null && !createDate.equals("")) {
			hql += "and o.createDate < '" + createDate + "' ";
			hqlCount += "and o.createDate < '" + createDate + "' ";
		}
		if (name != null && !name.equals("")) {
			hql += "and o.salesMan = '" + name + "' ";
			hqlCount += "and o.salesMan = '" + name + "' ";
		}
		if (orderStatus != null && !orderStatus.equals("")) {
			hql += "and o.orderStatus = " + orderStatus + " ";
			hqlCount += "and o.orderStatus = " + orderStatus + " ";
		}
		hql += "ORDER BY o.createDate DESC";
		List<Order> list = processService.queryForPage(hql, offset, length); // 该分页的记录
		int count = processService.getCountByParam(hqlCount);
		int totalPage = pb.countTotalPage(pageSize, count); // 总页数
		// 转为json格式是对 日期对象做处理。
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		pb.setList(list);
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setTotalPage(totalPage);
		pb.setAllRow(count);
		map.put("orders", list);
		map.put("pb", pb);
		JSONObject jsonObject = JSONObject.fromObject(map, jsonConfig);
		return jsonObject;

	}

	@SuppressWarnings("static-access")
	@RequestMapping("/searchAll")
	@ResponseBody
	public JSONObject searchAll(HttpServletRequest request) {
		log.info("订单列表---搜索订单");
		Map<String, Object> map = new HashMap<String, Object>();
		PageBean pb = new PageBean();
		String pagesize = request.getParameter("pageSize");// 每页显示的数量
		String page1 = request.getParameter("page");// 当前页
		String orderNo = request.getParameter("orderNo");// 订单号
		String name = request.getParameter("name");// 业务员账户
		String createDate = request.getParameter("createDate");// 创建时间
		String orderStatus = request.getParameter("orderStatus");// 订单状态

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
		String hql = "from Order o where 1=1 and isEnable =1 ";
		String hqlCount = "select count(*) from Order o where 1=1 and isEnable =1 ";
		if (orderNo != null && !orderNo.equals("")) {
			hql += "and o.orderNo = '" + orderNo + "' ";
			hqlCount += "and o.orderNo = '" + orderNo + "' ";
		}
		if (createDate != null && !createDate.equals("")) {
			hql += "and o.createDate < '" + createDate + "' ";
			hqlCount += "and o.createDate < '" + createDate + "' ";
		}
		if (name != null && !name.equals("")) {
			hql += "and o.salesMan = '" + name + "' ";
			hqlCount += "and o.salesMan = '" + name + "' ";
		}
		if (orderStatus != null && !orderStatus.equals("")) {
			hql += "and o.orderStatus = " + orderStatus + " ";
			hqlCount += "and o.orderStatus = " + orderStatus + " ";
		}
		hql += "ORDER BY o.isEnable DESC,o.createDate DESC";
		List<Order> list = processService.queryForPage(hql, offset, length); // 该分页的记录
		int count = processService.getCountByParam(hqlCount);
		int totalPage = pb.countTotalPage(pageSize, count); // 总页数
		// 转为json格式是对 日期对象做处理。
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		pb.setList(list);
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setTotalPage(totalPage);
		pb.setAllRow(count);
		map.put("orders", list);
		map.put("pb", pb);
		JSONObject jsonObject = JSONObject.fromObject(map, jsonConfig);
		return jsonObject;

	}

	/**
	 * 订单列表下一页
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "nextPageOrder", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject nextPageOrder(HttpServletRequest request) {
		log.info("订单列表--开始执行admin/process/nextPageOder 方法");
		Map<String, Object> map = new HashMap<String, Object>();
		PageBean pb = new PageBean();
		String pagesize = request.getParameter("pageSize");// 每页显示的数量
		String page1 = request.getParameter("page");// 当前页
		String orderNo = request.getParameter("orderNo");// 订单号
		String name = request.getParameter("name");// 业务员账户
		String createDate = request.getParameter("createDate");// 创建时间
		String orderStatus = request.getParameter("orderStatus");// 订单状态

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
		String hql = "from Order o where 1=1 and isEnable =1 and salesMan='" + getSessionUser().getName() + "' ";
		String hqlCount = "select count(*) from Order o where 1=1 and isEnable =1 and salesMan='"
				+ getSessionUser().getName() + "' ";
		if (orderNo != null && !orderNo.equals("")) {
			hql += "and o.orderNo = " + orderNo + " ";
			hqlCount += "and o.orderNo = " + orderNo + " ";
		}
		if (createDate != null && !createDate.equals("")) {
			hql += "and o.createDate < '" + createDate + "' ";
			hqlCount += "and o.createDate < '" + createDate + "' ";
		}
		if (name != null && !name.equals("")) {
			hql += "and o.salesMan = '" + name + "' ";
			hqlCount += "and o.salesMan = '" + name + "' ";
		}
		if (orderStatus != null && !orderStatus.equals("")) {
			hql += "and o.orderStatus = " + orderStatus + " ";
			hqlCount += "and o.orderStatus = " + orderStatus + " ";
		}
		hql += "ORDER BY o.isEnable DESC,o.createDate DESC";
		List<Order> list = processService.queryForPage(hql, offset, length); // 该分页的记录
		int count = processService.getCountByParam(hqlCount);
		int totalPage = pb.countTotalPage(pageSize, count); // 总页数
		// 转为json格式是对 日期对象做处理。
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		pb.setList(list);
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setTotalPage(totalPage);
		pb.setAllRow(count);
		map.put("pb", pb);
		JSONObject jsonObject = JSONObject.fromObject(map, jsonConfig);
		return jsonObject;

	}

	/**
	 * 全部订单列表下一页
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "nextPageAllOrder", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject nextPageAllOrder(HttpServletRequest request) {
		log.info("全部订单列表--开始执行admin/process/nextPageAllOrder 方法");
		Map<String, Object> map = new HashMap<String, Object>();
		PageBean pb = new PageBean();
		String pagesize = request.getParameter("pageSize");// 每页显示的数量
		String page1 = request.getParameter("page");// 当前页
		String orderNo = request.getParameter("orderNo");// 订单号
		String name = request.getParameter("name");// 业务员账户
		String createDate = request.getParameter("createDate");// 创建时间
		String orderStatus = request.getParameter("orderStatus");// 订单状态

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
		String hql = "from Order o where 1=1 and isEnable =1 ";
		String hqlCount = "select count(*) from Order o where 1=1 and isEnable =1  ";
		if (orderNo != null && !orderNo.equals("")) {
			hql += "and o.orderNo = " + orderNo + " ";
			hqlCount += "and o.orderNo = " + orderNo + " ";
		}
		if (createDate != null && !createDate.equals("")) {
			hql += "and o.createDate < '" + createDate + "' ";
			hqlCount += "and o.createDate < '" + createDate + "' ";
		}
		if (name != null && !name.equals("")) {
			hql += "and o.salesMan = '" + name + "' ";
			hqlCount += "and o.salesMan = '" + name + "' ";
		}
		if (orderStatus != null && !orderStatus.equals("")) {
			hql += "and o.orderStatus = " + orderStatus + " ";
			hqlCount += "and o.orderStatus = " + orderStatus + " ";
		}
		hql += "ORDER BY o.isEnable DESC,o.createDate DESC";
		List<Order> list = processService.queryForPage(hql, offset, length); // 该分页的记录
		int count = processService.getCountByParam(hqlCount);
		int totalPage = pb.countTotalPage(pageSize, count); // 总页数
		// 转为json格式是对 日期对象做处理。
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		pb.setList(list);
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setTotalPage(totalPage);
		pb.setAllRow(count);
		map.put("pb", pb);
		JSONObject jsonObject = JSONObject.fromObject(map, jsonConfig);
		return jsonObject;

	}

	/**
	 * 图表分析
	 */
	@RequestMapping(value = "chart", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject chart() {
		log.info("图表分析");
		String hql = "from User";
		List<User> list = userService.findHql(User.class, hql);
		List<String> m = DateUtil.beforeJune();
		Map<String, Object> map = new HashMap<String, Object>();
		for (User c : list) {
			List<Integer> strName = new ArrayList<Integer>();
			int i = 0;
			for (String str : m) {

				String hql2 = "select count(*) from Order o where salesMan='" + c.getName() + "' and years='" + str
						+ "'";
				int count2 = userService.getCountByParam(hql2);
				strName.add(i++, count2);
			}
			map.put(c.getName(), strName);
		}
		map.put("users", list);
		map.put("years", m);
		JSONObject json = JSONObject.fromObject(map);
		log.info(json);
		return json;
	}
}
