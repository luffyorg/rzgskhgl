package cn.yznu.rzgskhgl.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import cn.yznu.rzgskhgl.service.IResourceService;
import cn.yznu.rzgskhgl.service.IRoleService;
/**
 * 角色控制类
 * @author 张伟
 *
 */
@RequestMapping("/admin/role")
@Controller
public class RoleController {
	Logger log = Logger.getLogger(RoleController.class);
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IResourceService resourceService;
	
	@RequestMapping("list")
	public ModelAndView list(){
		log.info("开始执行admin/role/list 方法");
		ModelAndView mav = new ModelAndView();
		mav.addObject("roles", roleService.listRole());
		mav.setViewName("role/list");
		return mav;
	}
	
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("role", new Role());
		return "role/add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(Role role,Model model) {
		roleService.save(role);
		return "redirect:/admin/role/list";
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
	
	@RequestMapping("/listRes/{id}")
	public ModelAndView listRes(@PathVariable int id) {
		log.info("开始执行admin/role/listRes/{id} 方法" );
		ModelAndView mv = new ModelAndView();
		List<Resource> hasRes = roleService.listRoleResource(id);
		List<Integer> hrIds = new ArrayList<Integer>();
		for(Resource r:hasRes) {
			hrIds.add(r.getId());
		}
		mv.addObject("rids", hrIds);
		mv.addObject("reses", resourceService.getAllRes());
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
	
}
