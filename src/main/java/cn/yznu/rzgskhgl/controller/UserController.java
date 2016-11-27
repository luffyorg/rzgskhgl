package cn.yznu.rzgskhgl.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import cn.yznu.rzgskhgl.common.PageBean;
import cn.yznu.rzgskhgl.pojo.Resource;
import cn.yznu.rzgskhgl.pojo.Role;
import cn.yznu.rzgskhgl.pojo.User;
import cn.yznu.rzgskhgl.service.IRoleService;
import cn.yznu.rzgskhgl.service.IUserService;
import cn.yznu.rzgskhgl.shiro.ShiroKit;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author zhangwei
 *
 */

@RequestMapping("/admin/user")
@Controller
public class UserController extends BaseController{
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
	@SuppressWarnings("static-access")
	@RequestMapping(value = "list")
	public ModelAndView list(HttpServletRequest request) {
		log.info("开始执行admin/user/list 方法");
		ModelAndView mav = new ModelAndView();
		PageBean pb = new PageBean();
		String pagesize = request.getParameter("pageSize");
		String page1 = request.getParameter("page");
		if(pagesize ==null || pagesize.equals("")){
			pagesize = "10";
		}
		if(page1 ==null || page1.equals("")){
			page1 = "1";
		}
		int pageSize = Integer.parseInt(pagesize);
		int page = Integer.parseInt(page1);
		int count = userService.getCount(User.class);
		int totalPage = pb.countTotalPage(pageSize, count); // 总页数
		int offset = pb.countOffset(pageSize, page); // 当前页开始记录
		int length = pageSize; // 每页记录数
		int currentPage = pb.countCurrentPage(page);
		List<User> list = userService.queryForPage("from User order by isEnable desc ,createDate desc", offset, length); // 该分页的记录
		pb.setList(list);
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setTotalPage(totalPage);
		pb.setAllRow(count);
		mav.addObject("pb", pb);
		mav.addObject("users", list);
		mav.addObject("roles",roleService.listRole());
		mav.setViewName("user/list");
		return mav;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="save" ,method=RequestMethod.POST)
	@ResponseBody
	public Map saveUser(@RequestBody JSONObject json){
		Map<String,String> map = new HashMap<String,String>();
		String name = json.getString("name");
		String nickName = json.getString("nickName");
		String pwd = json.getString("pwd");
		Long tel = json.getLong("tel");
		String gender = json.getString("gender");
		String address = json.getString("address");
		double totalAssets = json.getDouble("totalAssets");
		double totalLiability = json.getDouble("totalLiability");
		String creditConditions = json.getString("creditConditions");
		String industry = json.getString("industry");
		int estate = json.getInt("estate");
		int movable = json.getInt("movable");
		int solidSurfacing = json.getInt("solidSurfacing");
		int company = json.getInt("company");
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
		u.setNickName(nickName);
		u.setPassword(pwd);
		u.setTel(tel);
		u.setGender(gender);
		u.setAddress(address);
		u.setTotalAssets(totalAssets);
		u.setTotalLiability(totalLiability);
		u.setCreditConditions(creditConditions);
		u.setIndustry(industry);
		u.setEstate(estate);
		u.setCompany(company);
		u.setMovable(movable);
		u.setSolidSurfacing(solidSurfacing);
		u.setIsEnable(isEnable);
		u.setCreateBy(getSessionUser().toString());
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
	
	@SuppressWarnings("static-access")
	@RequestMapping(value="nextPage" ,method=RequestMethod.GET)
	@ResponseBody
	public JSONObject nextPage(HttpServletRequest request) {
		log.info("开始执行admin/user/nextPage 方法");
		Map<String,Object> map = new HashMap<String,Object>();
		PageBean pb = new PageBean();
		String pagesize = request.getParameter("pageSize");
		String page1 = request.getParameter("page");
		if(pagesize ==null || pagesize.equals("")){
			pagesize = "10";
		}
		if(page1 ==null || page1.equals("")){
			page1 = "1";
		}
		int pageSize = Integer.parseInt(pagesize);
		int page = Integer.parseInt(page1);
		int count = userService.getCount(User.class);
		int totalPage = pb.countTotalPage(pageSize, count); // 总页数
		int offset = pb.countOffset(pageSize, page); // 当前页开始记录
		int length = pageSize; // 每页记录数
		int currentPage = pb.countCurrentPage(page);
		List<User> list = userService.queryForPage("from User ORDER BY isEnable DESC,createDate DESC", offset, length); // 该分页的记录
		pb.setList(list);
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setTotalPage(totalPage);
		pb.setAllRow(count);
		map.put("users", list);
		map.put("pb", pb);
		JSONObject json = JSONObject.fromObject( map ); 
		return json;
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
		String nickName = json.getString("nickName");
		Long tel = json.getLong("tel");
		String gender = json.getString("gender");
		String address = json.getString("address");
		double totalAssets = json.getDouble("totalAssets");
		double totalLiability = json.getDouble("totalLiability");
		String creditConditions = json.getString("creditConditions");
		String industry = json.getString("industry");
		int estate = json.getInt("estate");
		int movable = json.getInt("movable");
		int solidSurfacing = json.getInt("solidSurfacing");
		int company = json.getInt("company");
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
		u.setNickName(nickName);
		u.setTel(tel);
		u.setGender(gender);
		u.setAddress(address);
		u.setTotalAssets(totalAssets);
		u.setTotalLiability(totalLiability);
		u.setCreditConditions(creditConditions);
		u.setIndustry(industry);
		u.setEstate(estate);
		u.setCompany(company);
		u.setMovable(movable);
		u.setSolidSurfacing(solidSurfacing);
		u.setUpdateBy(getSessionUser().getId().toString());
		u.setUpdateDate(new Date());
		u.setUpdateName(getSessionUser().getName());
		userService.add(u, ridss);
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
