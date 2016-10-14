package cn.yznu.rzgskhgl.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import cn.yznu.rzgskhgl.common.PageBean;
import cn.yznu.rzgskhgl.pojo.Product;
import cn.yznu.rzgskhgl.service.IProductService;

/**
 * 产品控制类
 * @author 张伟
 *
 */
@Controller
@RequestMapping("/admin/product")
public class ProductController {
	Logger log = Logger.getLogger(UserController.class);
	
	@Autowired
	private IProductService productService;
	
	
	
	@SuppressWarnings("static-access")
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request){
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
		List<Product> list = productService.queryForPage("from Product", offset, length); // 该分页的记录
		
		pb.setList(list);
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setTotalPage(totalPage);
		pb.setAllRow(count);
		mv.addObject("products", list);
		mv.addObject("pb", pb);
		mv.setViewName("product/list");
		return mv;
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	public ModelAndView add(){
		ModelAndView mv = new ModelAndView();
		mv.addObject("product", new Product());
		mv.setViewName("product/add");
		return mv;
	}
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String add(Product product) {
		productService.save(product);
		return "redirect:/admin/product/list";
	}
	
	@RequestMapping("updateStatus/{id}")
	public String updateStatus(@PathVariable int id) {
		Product product = productService.load(Product.class,id);
		if(product.getIsEnable()==0) {
			product.setIsEnable(1);
		} else {
			product.setIsEnable(0);
		}
		productService.saveOrUpdate(product);
		return "redirect:/admin/product/list";
	}
	
	@RequestMapping(value="update/{id}",method=RequestMethod.GET)
	public ModelAndView update(@PathVariable int id) {
		ModelAndView mv = new ModelAndView();
		Product product = productService.load(Product.class,id);
		mv.addObject("product", product);
		mv.setViewName("product/update");
		return mv;
	}
	@RequestMapping(value="update/{id}",method=RequestMethod.POST)
	public String update(@PathVariable int id,Product p) {
		Product product = productService.load(Product.class,id);
		product.setName(p.getName());
		product.setProductNo(p.getProductNo());
		product.setEstate(p.getEstate());
		product.setIndustry(p.getIndustry());
		product.setMovable(p.getMovable());
		product.setCompany(p.getCompany());
		product.setSolidSurfacing(p.getSolidSurfacing());
		product.setTotalAssets(p.getTotalAssets());
		product.setTotalLiability(p.getTotalLiability());
		product.setIsEnable(p.getIsEnable());
		productService.saveOrUpdate(product);
		return "redirect:/admin/product/list";
	}
	
}
