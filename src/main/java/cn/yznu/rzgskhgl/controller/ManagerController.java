package cn.yznu.rzgskhgl.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import cn.yznu.rzgskhgl.pojo.Resource;
import cn.yznu.rzgskhgl.pojo.Role;
import cn.yznu.rzgskhgl.pojo.User;
import cn.yznu.rzgskhgl.service.IRoleService;
import cn.yznu.rzgskhgl.service.IUserService;
import cn.yznu.rzgskhgl.shiro.ShiroKit;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 *  网址管理员控制类
 * @author 张伟
 * @date  2016-12-2
 */
@Controller
@RequestMapping("/admin/manager")
public class ManagerController extends BaseController{
	Logger log = LoggerFactory.getLogger(ManagerController.class);
	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;
	
	@RequestMapping("list")
	public ModelAndView list(){
		ModelAndView mv = new ModelAndView();
		String hql = "select u from User u,UserRole ur,Role r where ur.user.id=u.id and r.id=ur.role.id and r.sn='ADMIN'";
		List<User> users = userService.findHql(User.class , hql);
		mv.setViewName("manager");
		mv.addObject("users", users);
		mv.addObject("roles",roleService.listRole());
		return mv;
	}
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	@RequestMapping(value="save" ,method=RequestMethod.POST)
	@ResponseBody
	public Map saveUser(@RequestBody JSONObject json){
		Map<String,String> map = new HashMap<String,String>();
		String name = json.getString("name");
		String pwd = json.getString("pwd");
		Long tel = json.getLong("tel");
		int isEnable = json.getInt("isEnable");
		JSONArray trids = json.getJSONArray("rids");
		System.out.println("trids" + trids);
		List<Integer> ridss = new ArrayList<Integer>();
		List<String> rids = JSONArray.toList(trids);
		for (String rid : rids) {
			if(rid.equals("") || rid==null){
				
			}else
				ridss.add(Integer.parseInt(rid));
		}
		System.out.println("ridss" + ridss);
		System.out.println("rids" + rids);
		User u = new User();
		u.setName(name);
		u.setPassword(pwd);
		u.setTel(tel);
		u.setIsEnable(isEnable);
		u.setCreateBy(getSessionUser().getId().toString());
		u.setCreateDate(new Date());
		u.setCreateName(getSessionUser().getName());
		userService.add(u, ridss);
		map.put("msg", "success");
		return map;
	}
	
	
	/**
	 * 更新用户状态 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("updateStatus/{id}")
	@ResponseBody
	public Map updateStatus(@PathVariable int id) {
		log.info("更新用户状态");
		Map<String,Object> map = new HashMap<String,Object>();
		User u = userService.load(User.class, id);
		if (u.getIsEnable() == 0) {
			u.setIsEnable(1);
		} else {
			u.setIsEnable(0);
		}
		u.setUpdateBy(getSessionUser().getId().toString());
		u.setUpdateDate(new Date());
		u.setUpdateName(getSessionUser().getName());
		userService.saveOrUpdate(u);
		//map.put("user", userService.load(User.class, id));
		map.put("isEnable", u.getIsEnable());
		map.put("msg", "更新成功");
		return map;
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
	
	@RequestMapping(value = "checkName" , method=RequestMethod.GET)
	@ResponseBody
	public String checkName(HttpServletRequest request){
		log.info("校验登录名是否重复");
		String msg = "";
		String name = request.getParameter("name");
		
		User u = userService.findUniqueByProperty(User.class, "name", name);
		if(u == null){
			msg = "error";
		}else
			msg = "success";
		return msg;
	}
	
	/**
	 * 根据用户id获取用户的所以角色id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="getUserRole" ,method=RequestMethod.GET)
	@ResponseBody
	public JSONObject getUserRole(HttpServletRequest request){
		log.info("获取改用户的所有角色id");
		Map<String,Object> map = new HashMap<String,Object>();
		int id = Integer.parseInt(request.getParameter("id"));
		List<Role> hasRoles = userService.listUserRole(id);
		List<Integer> rids = new ArrayList<Integer>();
		for (Role r : hasRoles) {
			rids.add(r.getId());
		}
		map.put("roles", rids);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	/**
	 * 修改用户
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="updateUser",method=RequestMethod.POST)
	@ResponseBody
	public Object updateUser(@RequestBody JSONObject json){
		Map<String,String> map = new HashMap<String,String>();
		int id = json.getInt("id");
		User u = userService.load(User.class, id);
		String name = json.getString("name");
		String pwd = json.getString("password");
		Long tel = json.getLong("tel");
		JSONArray trids = json.getJSONArray("rids");
		System.out.println("trids" + trids);
		List<Integer> ridss = new ArrayList<Integer>();
		List<String> rids = JSONArray.toList(trids);
		for (String rid : rids) {
			if(rid.equals("") || rid==null){
				
			}else
				ridss.add(Integer.parseInt(rid));
		}
		System.out.println("ridss" + ridss);
		System.out.println("rids" + rids);
		u.setName(name);
		u.setTel(tel);
		u.setPassword(pwd);
		u.setUpdateBy(getSessionUser().getId().toString());
		u.setUpdateDate(new Date());
		u.setUpdateName(getSessionUser().getName());
		userService.update(u, ridss);
		map.put("msg", "success");
		return map;
	}
	@RequestMapping("updateUserPwd")
	@ResponseBody
	public String updateUserPwd(HttpServletRequest request){
		log.info("修改密码");
		String loginName = request.getParameter("loginName"); 	
		String password = request.getParameter("password");
		User user = userService.getUserByName(loginName);
		user.setPassword(password);
		if (ShiroKit.isEmpty(user.getName()) || ShiroKit.isEmpty(user.getPassword())) {
			throw new RuntimeException("用户名或者密码不能为空！");
		}
		user.setPassword(ShiroKit.md5(user.getPassword(), user.getName()));
		userService.saveOrUpdate(user);
		return "success";
	}
}
 