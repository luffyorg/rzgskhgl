package cn.yznu.rzgskhgl.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import cn.yznu.rzgskhgl.pojo.Resource;
import cn.yznu.rzgskhgl.pojo.Role;
import cn.yznu.rzgskhgl.pojo.User;
import cn.yznu.rzgskhgl.service.IRoleService;
import cn.yznu.rzgskhgl.service.IUserService;
import cn.yznu.rzgskhgl.shiro.ShiroKit;

@RequestMapping("/admin/user")
@Controller
public class UserController {
	Logger log = Logger.getLogger(UserController.class);

	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;

	/**
	 * 用户列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list() {
		log.info("开始执行admin/user/list 方法");
		ModelAndView mav = new ModelAndView();
		mav.addObject("users", userService.getAllUsers());
		mav.setViewName("user/list");
		return mav;
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView add(Model model) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("user", new User());
		mv.addObject("roles", roleService.listRole());
		mv.setViewName("user/add");
		return mv;
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(User user, HttpServletRequest req, Model model) {
		String[] trids = req.getParameterValues("rids");
		List<Integer> rids = new ArrayList<Integer>();
		for (String rid : trids) {
			rids.add(Integer.parseInt(rid));
		}
		userService.add(user, rids);
		return "redirect:/admin/user/list";
	}

	@RequestMapping("updateStatus/{id}")
	public String updateStatus(@PathVariable int id) {
		User u = userService.load(User.class, id);
		if (u.getIsEnable() == 0) {
			u.setIsEnable(1);
		} else {
			u.setIsEnable(0);
		}
		userService.saveOrUpdate(u);
		return "redirect:/admin/user/list";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable int id, Model model) {
		User user = userService.load(User.class, id);
		model.addAttribute("user", user);
		model.addAttribute("roles", roleService.listRole());
		List<Role> hasRoles = userService.listUserRole(id);
		List<Integer> rids = new ArrayList<Integer>();
		for (Role r : hasRoles) {
			rids.add(r.getId());
		}
		model.addAttribute("hasRole", rids);
		return "user/update";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.POST)
	public String update(@PathVariable int id, HttpServletRequest req, User user, Model model) {
		User u = userService.load(User.class, id);
		u.setName(user.getName());
		u.setIsEnable(user.getIsEnable());
		u.setAddress(user.getAddress());
		u.setCompany(user.getCompany());
		u.setEstate(user.getEstate());
		u.setGender(user.getGender());
		u.setIndustry(user.getIndustry());
		u.setMovable(user.getMovable());
		u.setSolidSurfacing(user.getSolidSurfacing());
		u.setPassword(ShiroKit.md5(user.getPassword(), user.getName()));
		u.setTotalLiability(user.getTotalLiability());
		u.setTotalAssets(user.getTotalAssets());
		u.setTel(user.getTel());
		u.setUpdateDate(new Date());
		String[] trids = req.getParameterValues("rids");
		List<Integer> rids = new ArrayList<Integer>();
		for (String rid : trids) {
			rids.add(Integer.parseInt(rid));
		}
		userService.update(u, rids);
		return "redirect:/admin/user/list";
	}

	/**
	 * 得到全部资源
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/listRes/{id}")
	public ModelAndView listRes(@PathVariable int id) {
		log.info("开始执行admin/user/listRes/{id} 方法");
		List<Resource> hasRes = userService.listAllResource(id);
		ModelAndView mav = new ModelAndView();
		mav.addObject("reses", hasRes);
		mav.addObject("user", userService.get(User.class, id));
		mav.setViewName("user/res");
		return mav;
	}

}
