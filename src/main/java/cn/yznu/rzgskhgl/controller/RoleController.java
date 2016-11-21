package cn.yznu.rzgskhgl.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import cn.yznu.rzgskhgl.common.PageBean;
import cn.yznu.rzgskhgl.pojo.Resource;
import cn.yznu.rzgskhgl.pojo.Role;
import cn.yznu.rzgskhgl.service.IResourceService;
import cn.yznu.rzgskhgl.service.IRoleService;
/**
 * 角色控制类
 * @author 张伟
 *
 */
@SuppressWarnings("rawtypes")
@RequestMapping("/admin/role")
@Controller
public class RoleController extends BaseController{
	Logger log = Logger.getLogger(RoleController.class);
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IResourceService resourceService;
	
	@SuppressWarnings("static-access")
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request){
		log.info("开始执行admin/role/list 方法");
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
		int count = roleService.getCount(Role.class);
		int totalPage = pb.countTotalPage(pageSize, count); // 总页数
		int offset = pb.countOffset(pageSize, page); // 当前页开始记录
		int length = pageSize; // 每页记录数
		int currentPage = pb.countCurrentPage(page);
		List<Role> list = roleService.queryForPage("from Role ", offset, length); // 该分页的记录
		pb.setList(list);
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setTotalPage(totalPage);
		pb.setAllRow(count);
		mav.addObject("pb", pb);
		mav.addObject("roles", list);
		mav.setViewName("role/list");
		return mav;
	}
	
	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Map save(@RequestBody JSONObject json) {
		log.info("执行方法>>>save");
		Map<String,String> map = new HashMap<String,String>();
		String msg = "";
		String roleName = json.getString("roleName");
		String roleCode = json.getString("roleCode");
		String hql = "from Role where name=? or sn=? and isEnable=0";
		Object[] values = {roleName,roleCode};
		Role role = roleService.getSingleByHQL(hql, values);
		if(role != null){
			role.setIsEnable(1);
			role.setUpdateBy(getSessionUser().getId().toString());
			role.setCreateName(getSessionUser().getName());
			role.setCreateDate(new Date());
		}
		else{
			role = new Role();
			role.setName(roleName);
			role.setSn(roleCode);
			role.setCreateBy(getSessionUser().getId().toString());
			role.setCreateName(getSessionUser().getName());
			role.setCreateDate(new Date());
			role.setIsEnable(1);
		}
		roleService.saveOrUpdate(role);
		msg = "success";
		map.put("success", msg);
		return map;
	}
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Map updateRole(@RequestBody JSONObject json) {
		log.info("执行方法>>>update");
		
		Map<String,String> map = new HashMap<String,String>();
		String msg = "";
		Integer id = json.getInteger("id");
		Role role = roleService.getEntity(Role.class, id);
		String name = json.getString("roleName");
		String sn = json.getString("roleCode");
		role.setName(name);
		role.setSn(sn);
		role.setUpdateBy(getSessionUser().getId().toString());
		role.setUpdateName(getSessionUser().getName());
		role.setUpdateDate(new Date());
		role.setIsEnable(1);
		roleService.saveOrUpdate(role);
		msg = "success";
		map.put("success", msg);
		return map;
	}
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(Model model,@PathVariable int id) {
		model.addAttribute("role", roleService.load(Role.class,id));
		return "role/update";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	public String add(@PathVariable int id,Role role,Model model) {
		Role tr = roleService.load(Role.class,id);
		tr.setName(role.getName());
		tr.setSn(role.getSn());
		roleService.saveOrUpdate(tr);
		return "redirect:/admin/role/list";
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping("/listRes/{id}")
	public ModelAndView listRes(@PathVariable int id,HttpServletRequest request) {
		log.info("开始执行admin/role/listRes/{id} 方法" );
		ModelAndView mv = new ModelAndView();
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
		int count = roleService.getCount(Resource.class);
		int totalPage = pb.countTotalPage(pageSize, count); // 总页数
		int offset = pb.countOffset(pageSize, page); // 当前页开始记录
		int length = pageSize; // 每页记录数
		int currentPage = pb.countCurrentPage(page);
		List<Role> list = roleService.queryForPage("from Role where isEnable = 1", offset, length); // 该分页的记录
		pb.setList(list);
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setTotalPage(totalPage);
		pb.setAllRow(count);
		
		
		
		List<Resource> hasRes = roleService.listRoleResource(id);
		List<Integer> hrIds = new ArrayList<Integer>();
		for(Resource r:hasRes) {
			hrIds.add(r.getId());
		}
		mv.addObject("pb", pb);
		mv.addObject("rids", hrIds);
		//mv.addObject("reses", resourceService.getAllRes());
		mv.addObject("reses", resourceService.queryForPage("from Resource where isEnable = 1", offset, length));
		mv.addObject("role", roleService.load(Role.class,id));
		mv.setViewName("role/res");
		return mv;
	}
	
	@RequestMapping(value="/setRes",method=RequestMethod.POST)
	public void setRes(int resId,int roleId,int c,HttpServletResponse resp) throws IOException {
		log.info("开始执行admin/role/setRes/ 方法" );
		if(c==0) {
			roleService.deleteRoleResource(roleId, resId);
		} else {
			roleService.addRoleResource(roleId, resId);
		}
		resp.getWriter().println("ok");
	} 
	
	
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public Map del(@RequestBody JSONObject json) {
		log.info("执行方法>>>delete");
		Map<String,String> map = new HashMap<String,String>();
		String msg = "";
		int id =json.getInteger("id");
		//获取角色
		Role role = roleService.load(Role.class, id);
		//删除角色对应的资源
		int i = roleService.deleteRoleResourceByRole(id);
		//删除角色对应的用户
		int j = roleService.deleteRoleUserByRole(id);
		if(i > 0 && j > 0){
			log.info("角色资源关系删除成功--用户角色关系删除成功");
			msg = "success";
		}else{
			log.info("角色资源关系删除失败--用户角色关系删除失败");
			msg = "error";
		}
		roleService.delete(role);
		map.put("msg", msg);
		return map;
	}
	/**
	 * 禁用角色，将isEnable 设为0
	 * @param json
	 * @return
	 */
	@RequestMapping("updateStatus/{id}")
	@ResponseBody
	public Map updateStatus(@PathVariable int id) {
		log.info("更改角色状态");
		String msg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		Role role = roleService.load(Role.class, id);
		if (role.getIsEnable() == 0) {
			role.setIsEnable(1);
		} else {
			role.setIsEnable(0);
		}
		role.setUpdateBy(getSessionUser().getId().toString());
		role.setUpdateDate(new Date());
		role.setUpdateName(getSessionUser().getName());
		roleService.saveOrUpdate(role);
		if(role.getIsEnable()==1){
			msg = "启用成功";
		}else{
			msg = "禁用成功";
		}
		
		map.put("isEnable", role.getIsEnable());
		
		map.put("msg", msg);
		return map;
	}
	@RequestMapping(value = "checkName" , method=RequestMethod.GET)
	@ResponseBody
	public String checkName(HttpServletRequest request){
		log.info("校验角色名称是否重复");
		String msg = "";
		String name = request.getParameter("name");
		
		Role r = roleService.findUniqueByProperty(Role.class, "name", name);
		if(r == null){
			msg = "error";
		}else
			msg = "success";
		return msg;
	}	
	
}
