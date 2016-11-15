package cn.yznu.rzgskhgl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sun.istack.internal.logging.Logger;

@Controller
public class WelcomeController {
	Logger logger = Logger.getLogger(WelcomeController.class);
	
	@RequestMapping("welcome")
	public ModelAndView success() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("success");
		return mav;
	}
	
	@RequestMapping("/")
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login");
		return mv;
	}
	
	@RequestMapping("admin/index")
	public ModelAndView main(){
		logger.info("进入主页面");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		return mv;
	}
}
