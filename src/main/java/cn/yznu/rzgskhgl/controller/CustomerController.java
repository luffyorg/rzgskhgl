package cn.yznu.rzgskhgl.controller;

import java.io.File;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import cn.yznu.rzgskhgl.common.PageBean;
import cn.yznu.rzgskhgl.pojo.Customer;
import cn.yznu.rzgskhgl.pojo.Product;
import cn.yznu.rzgskhgl.pojo.User;
import cn.yznu.rzgskhgl.service.ICustomerService;
import cn.yznu.rzgskhgl.service.IUserService;
import cn.yznu.rzgskhgl.util.ExcelManage;
import net.sf.json.JSONObject;

/**
 * 客户管理控制类
 * 
 * @author zhangwei
 *
 */

@RequestMapping("/admin/customer")
@Controller
public class CustomerController extends BaseController {
	Logger log = Logger.getLogger(CustomerController.class);

	@Autowired
	private IUserService userService;
	@Autowired
	private ICustomerService customerService;

	/**
	 * 客户列表
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "list")
	public ModelAndView list(HttpServletRequest request) {
		log.info("开始执行admin/customer/list 方法");
		ModelAndView mav = new ModelAndView();
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
		int count = userService.getCount(Customer.class);
		int totalPage = pb.countTotalPage(pageSize, count); // 总页数
		int offset = pb.countOffset(pageSize, page); // 当前页开始记录
		int length = pageSize; // 每页记录数
		int currentPage = pb.countCurrentPage(page);
		List<User> list = customerService.queryForPage("from Customer order by isEnable desc ,createDate desc", offset,
				length); // 该分页的记录
		pb.setList(list);
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setTotalPage(totalPage);
		pb.setAllRow(count);
		mav.addObject("pb", pb);
		mav.addObject("customers", list);
		mav.setViewName("customer/list");
		return mav;
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map saveUser(@RequestBody JSONObject json) {
		log.info("添加客户");
		Map<String, String> map = new HashMap<String, String>();
		String name = json.getString("name");
		Long tel = json.getLong("tel");
		String gender = json.getString("gender");
		String address = json.getString("address");
		double totalAssets = json.getDouble("totalAssets");
		double totalLiability = json.getDouble("totalLiability");
		String creditConditions = json.getString("creditConditions");
		String industry = json.getString("industry");
		int estate = json.getInt("estate");
		int movable = json.getInt("movable");
		int solidSurfacing = json.getInt("solidSurfacing");
		int company = json.getInt("company");
		int isEnable = json.getInt("isEnable");
		Customer u = new Customer();
		u.setName(name);
		u.setTel(tel);
		u.setGender(gender);
		u.setAddress(address);
		u.setTotalAssets(totalAssets);
		u.setTotalLiability(totalLiability);
		u.setCreditConditions(creditConditions);
		u.setIndustry(industry);
		u.setEstate(estate);
		u.setCompany(company);
		u.setMovable(movable);
		u.setSolidSurfacing(solidSurfacing);
		u.setIsEnable(isEnable);
		u.setCreateBy(getSessionUser().getId().toString());
		u.setCreateDate(new Date());
		u.setCreateName(getSessionUser().getName());
		customerService.save(u);
		map.put("msg", "success");
		return map;
	}

	/**
	 * 更新客户状态
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("updateStatus/{id}")
	@ResponseBody
	public Map updateStatus(@PathVariable int id) {
		log.info("更新客户状态");
		Map<String, Object> map = new HashMap<String, Object>();
		Customer u = userService.load(Customer.class, id);
		if (u.getIsEnable() == 0) {
			u.setIsEnable(1);
		} else {
			u.setIsEnable(0);
		}
		u.setUpdateBy(getSessionUser().getId().toString());
		u.setUpdateDate(new Date());
		u.setUpdateName(getSessionUser().getName());
		customerService.saveOrUpdate(u);
		// map.put("user", userService.load(User.class, id));
		map.put("isEnable", u.getIsEnable());
		map.put("msg", "更新成功");
		return map;
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "nextPage", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject nextPage(HttpServletRequest request) {
		log.info("开始执行admin/user/nextPage 方法");
		Map<String, Object> map = new HashMap<String, Object>();
		PageBean pb = new PageBean();
		String pagesize = request.getParameter("pageSize");
		String page1 = request.getParameter("page");
		String estate1 = request.getParameter("estate");
		String movable1 = request.getParameter("movable");
		String solidSurfacing1 = request.getParameter("solidSurfacing");
		String company1 = request.getParameter("company");
		String name = request.getParameter("name");

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
		String hql = "from Customer u where 1=1 ";
		String hqlCount = "select count(*) from Customer u where 1=1 ";
		if (name != null && !name.equals("")) {
			hql += "and CONCAT(u.name,u.tel) LIKE '%" + name + "%'";
			hqlCount += "and CONCAT(u.name,u.tel) LIKE '%" + name + "%'";
		}
		if (estate1 != null && !estate1.equals("") && !estate1.equals("2")) {
			int estate = Integer.parseInt(estate1);
			hql += "and u.estate = " + estate + " ";
			hqlCount += "and u.estate = " + estate + " ";
		}
		if (movable1 != null && !movable1.equals("") && !movable1.equals("2")) {
			int movable = Integer.parseInt(movable1);
			hql += "and u.movable = " + movable + " ";
			hqlCount += "and u.movable = " + movable + " ";
		}
		if (solidSurfacing1 != null && !solidSurfacing1.equals("") && !solidSurfacing1.equals("2")) {
			int solidSurfacing = Integer.parseInt(solidSurfacing1);
			hql += "and u.movable = " + solidSurfacing + " ";
			hqlCount += "and u.movable = " + solidSurfacing + " ";
		}
		if (company1 != null && !company1.equals("") && company1 != null && !company1.equals("2")) {
			int company = Integer.parseInt(company1);
			hql += "and u.company = " + company + " ";
			hqlCount += "and u.company = " + company + " ";
		}
		hql += "ORDER BY u.isEnable DESC,u.createDate DESC";
		List<Product> list = userService.queryForPage(hql, offset, length); // 该分页的记录
		int count = userService.getCountByParam(hqlCount);
		int totalPage = pb.countTotalPage(pageSize, count); // 总页数
		pb.setList(list);
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setTotalPage(totalPage);
		pb.setAllRow(count);
		map.put("customers", list);
		map.put("pb", pb);
		JSONObject jsonObject = JSONObject.fromObject(map);
		return jsonObject;
	}

	/**
	 * 修改用户
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "updateCustomer", method = RequestMethod.POST)
	@ResponseBody
	public Object updateCustomer(@RequestBody JSONObject json) {
		log.info("更新客户信息");
		Map<String, String> map = new HashMap<String, String>();
		int id = json.getInt("id");
		Customer u = customerService.load(Customer.class, id);
		String name = json.getString("name");
		Long tel = json.getLong("tel");
		String gender = json.getString("gender");
		String address = json.getString("address");
		double totalAssets = json.getDouble("totalAssets");
		double totalLiability = json.getDouble("totalLiability");
		String creditConditions = json.getString("creditConditions");
		String industry = json.getString("industry");
		int estate = json.getInt("estate");
		int movable = json.getInt("movable");
		int solidSurfacing = json.getInt("solidSurfacing");
		int company = json.getInt("company");
		u.setName(name);
		u.setTel(tel);
		u.setGender(gender);
		u.setAddress(address);
		u.setTotalAssets(totalAssets);
		u.setTotalLiability(totalLiability);
		u.setCreditConditions(creditConditions);
		u.setIndustry(industry);
		u.setEstate(estate);
		u.setCompany(company);
		u.setMovable(movable);
		u.setSolidSurfacing(solidSurfacing);
		u.setUpdateBy(getSessionUser().getId().toString());
		u.setUpdateDate(new Date());
		u.setUpdateName(getSessionUser().getName());
		customerService.saveOrUpdate(u);
		map.put("msg", "success");
		return map;
	}

	@SuppressWarnings("static-access")
	@RequestMapping("/search")
	@ResponseBody
	public JSONObject search(HttpServletRequest request) {
		log.info("搜索客户");
		Map<String, Object> map = new HashMap<String, Object>();
		PageBean pb = new PageBean();
		String pagesize = request.getParameter("pageSize");
		String page1 = request.getParameter("page");
		String estate1 = request.getParameter("estate");
		String movable1 = request.getParameter("movable");
		String solidSurfacing1 = request.getParameter("solidSurfacing");
		String company1 = request.getParameter("company");
		String name = request.getParameter("name");

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
		String hql = "from Customer u where 1=1 ";
		String hqlCount = "select count(*) from Customer u where 1=1 ";
		if (name != null && !name.equals("")) {
			hql += "and CONCAT(u.name,u.tel) LIKE '%" + name + "%'";
			hqlCount += "and CONCAT(u.name,u.tel) LIKE '%" + name + "%'";
		}
		if (estate1 != null && !estate1.equals("") && !estate1.equals("2")) {
			int estate = Integer.parseInt(estate1);
			hql += "and u.estate = " + estate + " ";
			hqlCount += "and u.estate = " + estate + " ";
		}
		if (movable1 != null && !movable1.equals("") && !movable1.equals("2")) {
			int movable = Integer.parseInt(movable1);
			hql += "and u.movable = " + movable + " ";
			hqlCount += "and u.movable = " + movable + " ";
		}
		if (solidSurfacing1 != null && !solidSurfacing1.equals("") && !solidSurfacing1.equals("2")) {
			int solidSurfacing = Integer.parseInt(solidSurfacing1);
			hql += "and u.movable = " + solidSurfacing + " ";
			hqlCount += "and u.movable = " + solidSurfacing + " ";
		}
		if (company1 != null && !company1.equals("") && company1 != null && !company1.equals("2")) {
			int company = Integer.parseInt(company1);
			hql += "and u.company = " + company + " ";
			hqlCount += "and u.company = " + company + " ";
		}
		hql += "ORDER BY u.isEnable DESC,u.createDate DESC";
		List<Product> list = userService.queryForPage(hql, offset, length); // 该分页的记录
		int count = userService.getCountByParam(hqlCount);
		int totalPage = pb.countTotalPage(pageSize, count); // 总页数
		pb.setList(list);
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setTotalPage(totalPage);
		pb.setAllRow(count);
		map.put("customers", list);
		map.put("pb", pb);
		JSONObject jsonObject = JSONObject.fromObject(map);
		return jsonObject;

	}

	@RequestMapping("exportCustomer")
	public void exportCustomer(HttpServletRequest request, HttpServletResponse response) {
		log.info("导出数据--客户信息");
		String estate1 = request.getParameter("estate");
		String movable1 = request.getParameter("movable");
		String solidSurfacing1 = request.getParameter("solidSurfacing");
		String company1 = request.getParameter("company");
		String name = request.getParameter("name");
		String hql = "from Customer u where 1=1 ";
		if (name != null && !name.equals("")) {
			hql += "and CONCAT(u.name,u.tel) LIKE '%" + name + "%'";
		}
		if (estate1 != null && !estate1.equals("") && !estate1.equals("2")) {
			int estate = Integer.parseInt(estate1);
			hql += "and u.estate = " + estate + " ";
		}
		if (movable1 != null && !movable1.equals("") && !movable1.equals("2")) {
			int movable = Integer.parseInt(movable1);
			hql += "and u.movable = " + movable + " ";
		}
		if (solidSurfacing1 != null && !solidSurfacing1.equals("") && !solidSurfacing1.equals("2")) {
			int solidSurfacing = Integer.parseInt(solidSurfacing1);
			hql += "and u.solidSurfacing = " + solidSurfacing + " ";
		}
		if (company1 != null && !company1.equals("") && company1 != null && !company1.equals("2")) {
			int company = Integer.parseInt(company1);
			hql += "and u.company = " + company + " ";
		}
		hql += "ORDER BY u.isEnable DESC,u.createDate DESC";
		// 获取需要导出的数据List
		List<Customer> customers = customerService.getAllCustomer(hql);
		// 使用方法生成excle模板样式
		HSSFWorkbook workbook = customerService.createExcel(customers, request);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // 定义文件名格式

		try {
			// 定义excle名称 ISO-8859-1防止名称乱码
			String msg = new String(("客户信息_" + format.format(new Date()) + ".xls").getBytes(), "ISO-8859-1");
			// 以导出时间作为文件名
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment;filename=" + msg);
			workbook.write(response.getOutputStream());
		} catch (IOException e) {
			log.error(e);
		}
	}

	/**
	 * 上传客户信息
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("myfile") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("上传excel 客户信息数据");
		// 判断文件是否为空
		if (file == null)
			return null;
		// 获取文件名
		String name = file.getOriginalFilename();
		// 进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
		long size = file.getSize();
		if (name == null || ("").equals(name) && size == 0)
			return null;
		String msg = "";
		File targetFile = new File("d:\\temp", name);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}

		// 保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ExcelManage excelManage = new ExcelManage("d:\\temp\\" + name + "");
			String[] names = new String[] { "name", "tel", "address", "gender", "totalAssets", "totalLiability",
					"creditConditions", "industry", "estate", "movable", "company", "solidSurfacing" };
			Customer customer = new Customer();
			List<Customer> list = excelManage.readFromExcel(customer, names, 1);
			for (Customer c : list) {
				c.setIsEnable(1);
				c.setCreateDate(new Date());
				c.setCreateBy(getSessionUser().getId().toString());
				c.setCreateName(getSessionUser().getName());
			}
			System.out.println(list.size() + ">>>>>>>>>>>>>>>size");
			customerService.batchSave(list);
			msg = "导入成功";
		} catch (Exception e) {
			msg = "出错，未能全部导入!";
		}
		return "redirect:list";
	}

}
