package cn.yznu.rzgskhgl.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.yznu.rzgskhgl.util.CaptchaUtil;


@Controller
public class WelcomeController {
	Logger log = Logger.getLogger(WelcomeController.class);
	
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
		log.info("进入主页面");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		return mv;
	}
	
	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
    @ResponseBody
    public void captcha(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        CaptchaUtil.outputCaptcha(request, response);
    }
}
