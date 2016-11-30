package cn.yznu.rzgskhgl.controller;

import java.io.File;
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
import cn.yznu.rzgskhgl.pojo.Product;
import cn.yznu.rzgskhgl.service.IProductService;
import cn.yznu.rzgskhgl.util.ExcelManage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 产品控制类
 * 
 * @author 张伟
 *
 */
@Controller
@RequestMapping("/admin/product")
public class ProductController extends BaseController {
	Logger log = Logger.getLogger(ProductController.class);

	@Autowired
	private IProductService productService;

	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		String hql = "from Product p where 1=1 ";
		hql += "ORDER BY p.isEnable DESC,p.createDate DESC";
		List<Product> list = productService.findHql(Product.class, hql); // 该分页的记录

		mv.addObject("products", list);
		mv.setViewName("product/list");
		return mv;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "addProduct", method = RequestMethod.POST)
	@ResponseBody
	public Map addProduct(@RequestBody JSONObject json) {
		log.info("添加产品");
		Map<String, String> map = new HashMap<String, String>();
		String productName = json.getString("productName");
		String productNo = json.getString("productNo");
		String suitable = json.getString("suitable");
		String productPrice = json.getString("productPrice");
		String description = json.getString("description");
		JSONArray conditions = json.getJSONArray("condition");
		int isEnable = json.getInt("isEnable");
		System.out.println("condition" + conditions);
		List<Integer> condition = new ArrayList<Integer>();
		List<String> conditionss = JSONArray.toList(conditions);
		int estate = 0;
		int movable = 0;
		int company = 0;
		int solidSurfacing = 0;
		for (String rid : conditionss) {
			if(rid.equals("") || rid==null){
				
			}else
				condition.add(Integer.parseInt(rid));
		}
		estate = condition.get(0);
		movable = condition.get(1);
		company = condition.get(2);
		solidSurfacing = condition.get(3);
		Product p = new Product();
		p.setName(productName);
		p.setProductNo(productNo);
		p.setProductPrice(productPrice);
		p.setSuitable(suitable);
		p.setDescription(description);
		p.setCompany(company);
		p.setEstate(estate);
		p.setMovable(movable);
		p.setSolidSurfacing(solidSurfacing);
		p.setIsEnable(isEnable);
		p.setCreateBy(getSessionUser().getId().toString());
		p.setCreateDate(new Date());
		p.setCreateName(getSessionUser().getName());
		productService.save(p);
		map.put("msg", "success");
		return map;
	}

	/**
	 * 更新产品状态
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("updateStatus/{id}")
	@ResponseBody
	public Map updateStatus(@PathVariable int id) {
		log.info("更新产品状态");
		Map<String, Object> map = new HashMap<String, Object>();
		Product product = productService.load(Product.class, id);
		if (product.getIsEnable() == 0) {
			product.setIsEnable(1);
		} else {
			product.setIsEnable(0);
		}
		product.setUpdateDate(new Date());
		product.setUpdateBy(getSessionUser().getId().toString());
		product.setUpdateName(getSessionUser().getName());
		productService.saveOrUpdate(product);
		map.put("isEnable", product.getIsEnable());
		return map;
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public ModelAndView update(@PathVariable int id) {
		ModelAndView mv = new ModelAndView();
		Product product = productService.load(Product.class, id);
		mv.addObject("product", product);
		mv.setViewName("product/update");
		return mv;
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.POST)
	public String update(@PathVariable int id, Product p) {
		Product product = productService.load(Product.class, id);
		product.setName(p.getName());
		product.setProductNo(p.getProductNo());
		product.setEstate(p.getEstate());
		product.setMovable(p.getMovable());
		product.setCompany(p.getCompany());
		product.setSolidSurfacing(p.getSolidSurfacing());
		product.setIsEnable(p.getIsEnable());
		productService.saveOrUpdate(product);
		return "redirect:/admin/product/list";
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "nextPage", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject nextPage(HttpServletRequest request) {
		log.info("开始执行admin/product/nextPage 方法");
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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("myfile") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("上传excel 产品数据");
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
			String[] names = new String[] { "name", "productNo", "productPrice", "description", "estate", "movable",
					"company", "solidSurfacing" };
			Product product = new Product();
			List<Product> list = excelManage.readFromExcel(product, names, 1);
			for(Product c : list){
				c.setIsEnable(1);
				c.setCreateDate(new Date());
				c.setCreateBy(getSessionUser().getId().toString());
				c.setCreateName(getSessionUser().getName());
			}
			System.out.println(list.size() + ">>>>>>>>>>>>>>>size");
			productService.batchSave(list);
		} catch (Exception e) {
			msg = "出错，未能全部导入!";
			return msg;
		}

		return "redirect:list";
	}

	@RequestMapping("exportProduct")
	public void exportProduct(HttpServletRequest request,HttpServletResponse response) {
		log.info("导出数据");
		// 获取需要导出的数据List
		List<Product> products = productService.getAllProduct();
		// 使用方法生成excle模板样式
		HSSFWorkbook workbook = productService.createExcel(products, request);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // 定义文件名格式

		try {
			// 定义excle名称 ISO-8859-1防止名称乱码
			String msg = new String(("产品信息_" + format.format(new Date()) + ".xls").getBytes(), "ISO-8859-1");
			// 以导出时间作为文件名
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment;filename=" + msg);
			workbook.write(response.getOutputStream());
		} catch (IOException e) {
			log.error(e);
		}
	}
	
	/**
	 * 修改产品
	 * @param json
	 * @return
	 */
	@RequestMapping(value="updateUser",method=RequestMethod.POST)
	@ResponseBody
	public Object updateUser(@RequestBody JSONObject json){
		log.info("修改产品");
		Map<String,String> map = new HashMap<String,String>();
		int id = json.getInt("id");
		Product product = productService.load(Product.class, id);
		String name = json.getString("name");
		String productNo = json.getString("productNo");
		String productPrice = json.getString("productPrice");
		String description = json.getString("description");
		int estate = json.getInt("estate");
		int movable = json.getInt("movable");
		int solidSurfacing = json.getInt("solidSurfacing");
		int company = json.getInt("company");
		
		product.setName(name);
		product.setProductNo(productNo);
		product.setProductPrice(productPrice);
		product.setDescription(description);
		product.setEstate(estate);
		product.setCompany(company);
		product.setMovable(movable);
		product.setSolidSurfacing(solidSurfacing);
		product.setUpdateBy(getSessionUser().getId().toString());
		product.setUpdateDate(new Date());
		product.setUpdateName(getSessionUser().getName());
		productService.saveOrUpdate(product);
		map.put("msg", "success");
		return map;
	}
	@SuppressWarnings("static-access")
	@RequestMapping("/search")
	@ResponseBody
	public JSONObject search(HttpServletRequest request) {
		log.info("搜索产品");
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
