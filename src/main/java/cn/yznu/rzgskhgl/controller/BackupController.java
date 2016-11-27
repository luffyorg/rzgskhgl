package cn.yznu.rzgskhgl.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.yznu.rzgskhgl.pojo.Product;
import cn.yznu.rzgskhgl.pojo.User;
import cn.yznu.rzgskhgl.service.IProductService;
import cn.yznu.rzgskhgl.service.IUserService;

/**
 * 
    * @ClassName: BackupController  
    * @Description: TODO
    * @author Administrator  
    * @date 2016年11月25日  
    *
 */
@Controller
@RequestMapping("admin/backup")
public class BackupController extends BaseController{
	
	Logger log = Logger.getLogger(BackupController.class);
	@Autowired
	private IProductService productService;
	@Autowired
	private IUserService userService;
	
	@RequestMapping("index")
	public ModelAndView backup(){
		log.info("跳转到备份页面");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("backup");
		return mv;
	}
	
	@RequestMapping("product")
	public void backupProduct(HttpServletRequest request,HttpServletResponse response) {
		log.info("备份产品数据");
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
	
	@RequestMapping("user")
	public void backupUser(HttpServletRequest request,HttpServletResponse response) {
		log.info("备份用户数据");
		// 获取需要导出的数据List
		List<User> users = userService.getAllUsers();
		// 使用方法生成excle模板样式
		HSSFWorkbook workbook = userService.createExcel(users, request);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // 定义文件名格式
		
		try {
			// 定义excle名称 ISO-8859-1防止名称乱码
			String msg = new String(("用户信息_" + format.format(new Date()) + ".xls").getBytes(), "ISO-8859-1");
			// 以导出时间作为文件名
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment;filename=" + msg);
			workbook.write(response.getOutputStream());
		} catch (IOException e) {
			log.error(e);
		}
	}
	

}
