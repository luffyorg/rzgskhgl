package cn.yznu.rzgskhgl.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.yznu.rzgskhgl.pojo.Customer;
import cn.yznu.rzgskhgl.pojo.Order;
import cn.yznu.rzgskhgl.pojo.Product;
import cn.yznu.rzgskhgl.pojo.Record;
import cn.yznu.rzgskhgl.pojo.Resource;
import cn.yznu.rzgskhgl.pojo.Role;
import cn.yznu.rzgskhgl.pojo.RoleResource;
import cn.yznu.rzgskhgl.pojo.SendSms;
import cn.yznu.rzgskhgl.pojo.User;
import cn.yznu.rzgskhgl.pojo.UserRole;
import cn.yznu.rzgskhgl.service.IUserService;
import cn.yznu.rzgskhgl.util.BackupsDB;

/**
 * 
    * @ClassName: BackupController  
    * @Description: 数据库备份与恢复
    * @author zhangwei  
    * @date 2016年11月25日  
    *
 */
@Controller
@RequestMapping("admin/backup")
public class BackupController extends BaseController{
	
	Logger log = Logger.getLogger(BackupController.class);
	@Autowired
	private IUserService userService;
	
	@RequestMapping("index")
	public ModelAndView backup(){
		log.info("跳转到备份页面");
		ModelAndView mv = new ModelAndView();
		mv.addObject("user", userService.getCount(User.class));
		mv.addObject("userRole", userService.getCount(UserRole.class));
		mv.addObject("role", userService.getCount(Role.class));
		mv.addObject("roleResource", userService.getCount(RoleResource.class));
		mv.addObject("resource", userService.getCount(Resource.class));
		mv.addObject("product", userService.getCount(Product.class));
		mv.addObject("sendSms", userService.getCount(SendSms.class));
		mv.addObject("customer", userService.getCount(Customer.class));
		mv.addObject("order", userService.getCount(Order.class));
		mv.addObject("record", userService.getCount(Record.class));
		mv.setViewName("backup");
		return mv;
	}
	
	@RequestMapping("all")
	@ResponseBody
	public String backupAll(HttpServletRequest request,HttpServletResponse response) {
		log.info("备份产品数据");
		String tableName = "";
		String user = request.getParameter("user");
		if(user != null && !user.equals("")){
			tableName += "user ";
		}
		String userRole = request.getParameter("userRole");
		if(userRole != null && !userRole.equals("")){
			tableName += "user_role ";
		}
		String role = request.getParameter("role");
		if(role != null && !role.equals("")){
			tableName += "role ";
		}
		String roleResource = request.getParameter("roleResource");
		if(roleResource != null && !roleResource.equals("")){
			tableName += "role_resource ";
		}
		String resource = request.getParameter("resource");
		if(resource != null && !resource.equals("")){
			tableName += "resource ";
		}
		String product = request.getParameter("product");
		if(product != null && !product.equals("")){
			tableName += "product ";
		}
		String sendSms = request.getParameter("sendSms");
		if(sendSms != null && !sendSms.equals("")){
			tableName += "send_sms ";
		}
		String customer = request.getParameter("customer");
		if(customer != null && !customer.equals("")){
			tableName += "customer ";
		}
		String order = request.getParameter("order");
		if(order != null && !order.equals("")){
			tableName += "order ";
		}
		String record = request.getParameter("record");
		if(record != null && !record.equals("")){
			tableName += "record ";
		}
		
		SimpleDateFormat sd=new SimpleDateFormat("yyyyMMddHHmmss");  
        String root = "root";  
        String rootPass = "root";  
        String dbName = "rzgskhgl";  
        String backupsPath = "D:\\";  
        String backupsSqlFileName = "erpDB_"+sd.format(new Date())+".sql";  
        String path =  BackupsDB.dbBackUp(root, rootPass, dbName, backupsPath, backupsSqlFileName,tableName);  
        return path;
	}


}
