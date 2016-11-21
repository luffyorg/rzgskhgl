package cn.yznu.rzgskhgl.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import cn.yznu.rzgskhgl.pojo.User;
/**
 * 
 * @author snake团队
 *
 */
public class BaseController {
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
}
