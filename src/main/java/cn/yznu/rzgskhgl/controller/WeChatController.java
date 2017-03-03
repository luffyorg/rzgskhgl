package cn.yznu.rzgskhgl.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import cn.yznu.rzgskhgl.pojo.Product;
import cn.yznu.rzgskhgl.pojo.WeixinUserInfo;
import cn.yznu.rzgskhgl.service.ITokenService;
import cn.yznu.rzgskhgl.util.CommonUtil;
import cn.yznu.rzgskhgl.util.DateJsonValueProcessor;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

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
		String code = request.getParameter("code");
		if (openid == null || openid.equals("")) {
			if (null != code && !"".equals(code)) {
				log.info("==============[OAuthServlet]获取网页授权code不为空，code=" + code);
				// 根据code换取openId
				openid = CommonUtil.getOpenid(code);
				log.info("获取到openid为：" + openid);
			} else {
				log.info("==============获取网页授权code失败！");
			}
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("weixin/login");
		mv.addObject("openid", openid);
		return mv;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public Map login2(@RequestBody JSONObject jsonObject) {
		log.info("微信端--验证登录账户");
		Map<String, Object> map = new HashMap<String, Object>();
		String name = jsonObject.getString("name");
		Long tel = jsonObject.getLong("tel");
		String openid = jsonObject.getString("openid");
		WeixinUserInfo info = tokenService.findUniqueByProperty(WeixinUserInfo.class, "openId", openid);
		if (info == null) {
			map.put("msg", "请用微信登录");
			return map;
		} else if (info.getCustomerId() != 0) {
			map.put("msg", "bind");
			return map;
		} else {
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
	public ModelAndView myOrder(HttpServletRequest request, HttpServletResponse response) {
		log.info("微信端--绑定账户");
		ModelAndView mv = new ModelAndView();
		// 参数
		String code = request.getParameter("code");
		String openid = request.getParameter("openid");
		if (openid == null || openid.equals("")) {
			if (null != code && !"".equals(code)) {
				log.info("==============[OAuthServlet]获取网页授权code不为空，code=" + code);
				// 根据code换取openId
				openid = CommonUtil.getOpenid(code);
				log.info("获取到openid为：" + openid);
			} else {
				log.info("==============获取网页授权code失败！");
			}
		}
		//String openid = "o8TDSwhdSAZMEXulPcR8ExOYbOCk";
		log.info("openid :" + openid);
		HttpSession session = request.getSession();
		session.setAttribute("openid", openid);
		log.info(">>>" + session.getAttribute("openid"));
		WeixinUserInfo info = tokenService.getWxUserInfo(openid);
		Customer customer = tokenService.get(Customer.class, info.getCustomerId());
		if (customer == null) {
			mv.setViewName("weixin/login");
			return mv;
		} else {
			String hql = "from Order where isEnable = 1 and buyNameId=? order by createDate desc";
			Object[] param = { info.getCustomerId() };
			List<Order> orders = tokenService.findHql(hql, param);

			mv.setViewName("weixin/order");
			mv.addObject("orders", orders);
			return mv;
		}
	}

	@RequestMapping("product")
	public ModelAndView proudct(HttpServletRequest request, HttpServletResponse response) {
		log.info("微信端--产品介绍");
		// 参数
		String code = request.getParameter("code");
		String openid = request.getParameter("openid");
		if (openid == null || openid.equals("")) {
			if (null != code && !"".equals(code)) {
				log.info("==============[OAuthServlet]获取网页授权code不为空，code=" + code);
				// 根据code换取openId
				openid = CommonUtil.getOpenid(code);
				log.info("获取到openid为：" + openid);
			} else {
				log.info("==============获取网页授权code失败！");
			}
		}
		log.info("openid :" + openid);
		// WeixinUserInfo info = tokenService.getWxUserInfo(openid);
		ModelAndView mv = new ModelAndView();
		String hql = "from Product where isEnable = 1";
		List<Product> products = tokenService.findHql(Product.class, hql);

		mv.setViewName("weixin/product");
		mv.addObject("products", products);
		return mv;
	}

	@RequestMapping("search")
	public ModelAndView search(HttpServletRequest request) {
		log.info("微信端页面 -产品切换");
		// 参数
		String code = request.getParameter("code");
		int id = Integer.parseInt(request.getParameter("id"));
		String openid = "";
		if (null != code && !"".equals(code)) {
			log.info("==============[OAuthServlet]获取网页授权code不为空，code=" + code);
			// 根据code换取openId
			openid = CommonUtil.getOpenid(code);
			log.info("获取到openid为：" + openid);
		} else {
			log.info("==============获取网页授权code失败！");
		}
		log.info("openid :" + openid);
		HttpSession session = request.getSession();
		session.setAttribute("openid", openid);
		ModelAndView mv = new ModelAndView();
		Product product = tokenService.get(Product.class, id);
		mv.addObject("product", product);
		mv.setViewName("weixin/productDetail");
		return mv;
	}

	@RequestMapping("searchOrder")
	@ResponseBody
	public JSONObject searchOrder(HttpServletRequest request) {
		log.info("微信端页面 -搜索指定产品");
		Map<String, Object> map = new HashMap<String, Object>();
		// 参数
		String orderNo = request.getParameter("orderNo");
		int orderStatus = Integer.parseInt(request.getParameter("orderStatus"));
		HttpSession session = request.getSession();
		String openid = (String) session.getAttribute("openid");
		//String openid = "o8TDSwhdSAZMEXulPcR8ExOYbOCk";
		WeixinUserInfo info = tokenService.getWxUserInfo(openid);
		Customer customer = tokenService.get(Customer.class, info.getCustomerId());
		JSONObject json = new JSONObject();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		if (customer == null) {
			return json.fromObject("");
		} else {
			String hql = "from Order where isEnable = 1 and buyNameId=" + info.getCustomerId() + " ";
			if (orderNo != null && !orderNo.equals("")) {
				hql += "and orderNo = " + orderNo + " ";
			}
			if (orderStatus != 0) {
				hql += "and orderStatus = " + orderStatus + " ";
			}
			hql += "order by createDate desc";
			List<Order> orders = tokenService.findHql(Order.class,hql);
			map.put("orders2", orders);
			json = json.fromObject(map,jsonConfig);
			log.info("json =" + json);
			
			return json;
		}
	}

}
