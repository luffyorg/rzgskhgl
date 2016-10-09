package cn.yznu.rzgskhgl.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.yznu.rzgskhgl.pojo.Resource;
import cn.yznu.rzgskhgl.service.IResourceService;

@RequestMapping("/admin/resource")
@Controller
public class ResourceController {
	Logger log = Logger.getLogger(UserController.class);
	@Autowired
	private IResourceService resourceService;

	@RequestMapping("list")
	public ModelAndView list() {
		log.info("开始执行admin/resource/list 方法");
		ModelAndView mav = new ModelAndView();
		mav.addObject("ress", resourceService.getAllRes());
		mav.setViewName("res/list");
		return mav;

	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("res", new Resource());
		return "res/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(Resource res, Model model) {
		resourceService.save(res);
		return "redirect:/admin/resource/list";
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable int id, Model model) {
		model.addAttribute("res", resourceService.get(Resource.class, id));
		return "res/add";
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	public String update(@PathVariable int id, Resource res, Model model) {
		Resource tr = resourceService.get(Resource.class, id);
		tr.setName(res.getName());
		tr.setPermission(res.getPermission());
		tr.setUrl(res.getUrl());
		resourceService.saveOrUpdate(tr);
		return "redirect:/admin/resource/list";
	}

}
