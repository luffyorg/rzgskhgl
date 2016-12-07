package cn.yznu.rzgskhgl.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.yznu.rzgskhgl.pojo.User;
/**
 * 
 * @author snake团队
 *
 */
public class BaseController {
	
	Logger log = Logger.getLogger(BaseController.class);
	/**
	 * 获取session
	 * @return
	 */
	public Session getSession(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		return session;
	}
	
	/**
	 * 获取缓存中的用户
	 * @return
	 */
	public User getSessionUser(){
		User user = (User) getSession().getAttribute("user");
		return user;
	}
	
	/**
	 * 获取时间
	 * @return
	 */
	public String getTime(){
		Date d=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(d);
	}
	
	//获得客户端真实IP地址的方法一
	@RequestMapping("getRemortIP")
	public String getRemortIP(HttpServletRequest request) {  
	    if (request.getHeader("x-forwarded-for") == null) {  
	        return request.getRemoteAddr();  
	    }  
	    log.info(">>>>"+request.getHeader("x-forwarded-for"));
	    return request.getHeader("x-forwarded-for");  
	}  
	//获得客户端真实IP地址的方法二：
	@RequestMapping("getIpAddr")
	public String getIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("x-forwarded-for");  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    log.info(">>>"+ ip);
	    return ip;  
	}

}
