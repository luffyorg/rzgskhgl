package cn.yznu.rzgskhgl.controller;


import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import cn.yznu.rzgskhgl.pojo.User;
import cn.yznu.rzgskhgl.service.IUserService;
import net.sf.json.JSONObject;

/****
 * 用户登录Controller
 * 
 * @author 张伟
 * 
 */
@Controller
public class LoginController {
	Logger log = Logger.getLogger(LoginController.class);
	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/toLogin")
	public ModelAndView toLogin() {
		log.info("跳转到登录界面..");
		ModelAndView mv = new ModelAndView();// this.getModelAndView();
		mv.setViewName("login");
		return mv;
	}
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public ModelAndView login() {
		log.info("跳转到登录界面.");
		ModelAndView mv = new ModelAndView();// this.getModelAndView();
		mv.setViewName("login");
		return mv;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Object login(@RequestBody JSONObject jsonObj) {
		log.info("开始执行登录操作====");
		String name = jsonObj.getString("name");
		String pwd = jsonObj.getString("password");
		String errInfo = "";
		log.info("获取到的用户名：" + name + ",密码：" + pwd);
		User user = userService.getUserByNameAndPassword(name, pwd);
		if (user != null) {
			// shiro加入身份验证
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(name, pwd);
			try {
				subject.login(token);
				errInfo = "success"; // 验证成功
			} catch (AuthenticationException e) {
				errInfo = "身份验证失败！";
			}

		} else {
			errInfo = "usererror"; // 用户名或密码有误
		}
		Map map = new HashMap<String,String>();
		map.put("result", errInfo);
		return map;
	}

}
