package cn.yznu.rzgskhgl.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.yznu.rzgskhgl.pojo.Customer;
import cn.yznu.rzgskhgl.pojo.Order;
import cn.yznu.rzgskhgl.pojo.WeixinUserInfo;
import cn.yznu.rzgskhgl.service.ITokenService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("weixin")
public class WeChatController {
	private static Logger log = Logger.getLogger(WeChatController.class);

	@Autowired
	private ITokenService tokenService;

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request) {
		log.info("微信端--绑定账户");
		String openid = request.getParameter("openid");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("weixin/login");
		mv.addObject("openid", openid);
		return mv;
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public Map login2(@RequestBody JSONObject jsonObject) {
		log.info("微信端--验证登录账户");
		Map<String, Object> map = new HashMap<String, Object>();
		String name = jsonObject.getString("name");
		Long tel = jsonObject.getLong("tel");
		String openid = jsonObject.getString("openid");
		WeixinUserInfo info = tokenService.findUniqueByProperty(WeixinUserInfo.class, "openId", openid);
		if( info == null ){
			map.put("msg", "请用微信登录");
			return map;
		}else{
			String hql = "from Customer where name=? and tel=? ";
			Object[] values = { name, tel };
			Customer customer = tokenService.getSingleByHQL(hql, values);
			String msg = "";
			if (customer == null) {
				msg = "error";
			} else {
				info.setCustomerId(customer.getId());
				tokenService.saveOrUpdate(info);
				msg = "success";
			}
			
			map.put("msg", msg);
			return map;
		}
		
	}

	@RequestMapping("myOrder")
	public ModelAndView myOrder(HttpServletRequest request) {
		log.info("微信端--绑定账户");
		String openid = request.getParameter("openid");
		if(openid == null || openid.equals("")){
			openid = (String) request.getSession().getAttribute("fromUserName");
		}
		log.info("openid :" + openid);
		WeixinUserInfo info = tokenService.getWxUserInfo(openid);
		ModelAndView mv = new ModelAndView();
		String hql = "from Order where isEnable = 1 and buyNameId=?";
		Object[] param = {info.getCustomerId()};
		List<Order> orders = tokenService.findHql(hql, param);
		
		mv.setViewName("weixin/order");
		mv.addObject("orders", orders);
		return mv;

	}
}
