package cn.yznu.rzgskhgl.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.yznu.rzgskhgl.pojo.Product;
import cn.yznu.rzgskhgl.service.IProductService;

/**
 * 业务流程处理 控制类
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
	public ModelAndView list(){
		log.info("执行process/list 方法");
		ModelAndView mv = new ModelAndView();
		mv.addObject("products", productService.getAllProduct());
		mv.setViewName("process/list");
		return mv;
	}
	
	@RequestMapping(value="buy/{id}",method=RequestMethod.GET)
	@ResponseBody
	public String buyProduct(@PathVariable int id){
		log.info("开始执行process/buy 方法");
		Product product = productService.load(Product.class, id);
		product.getIndustry();
		return "";
	}
	
}
