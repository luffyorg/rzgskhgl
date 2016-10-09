package cn.yznu.rzgskhgl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController {
	
	
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
}
