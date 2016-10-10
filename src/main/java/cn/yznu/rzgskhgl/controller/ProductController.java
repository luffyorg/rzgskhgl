package cn.yznu.rzgskhgl.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
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
	
	
	
	@RequestMapping("/list")
	public ModelAndView list(){
		ModelAndView mv = new ModelAndView();
		mv.addObject("products", productService.getAllProduct());
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
