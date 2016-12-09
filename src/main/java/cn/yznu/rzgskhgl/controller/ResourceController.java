package cn.yznu.rzgskhgl.controller;

import java.util.Date;
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





import cn.yznu.rzgskhgl.common.PageBean;
import cn.yznu.rzgskhgl.pojo.Record;
import cn.yznu.rzgskhgl.pojo.Resource;
import cn.yznu.rzgskhgl.service.IRecordService;
import cn.yznu.rzgskhgl.service.IResourceService;
import net.sf.json.JSONObject;
@SuppressWarnings("rawtypes")
@RequestMapping("/admin/resource")
@Controller
public class ResourceController extends BaseController {
	Logger log = Logger.getLogger(ResourceController.class);
	@Autowired
	private IResourceService resourceService;
	@Autowired
	private IRecordService iRecordService;

	@SuppressWarnings("static-access")
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request) {
		log.info("开始执行admin/resource/list 方法");
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
		int count = resourceService.getCount(Resource.class);
		int totalPage = pb.countTotalPage(pageSize, count); // 总页数
		int offset = pb.countOffset(pageSize, page); // 当前页开始记录
		int length = pageSize; // 每页记录数
		int currentPage = pb.countCurrentPage(page);
		List<Resource> list = resourceService.queryForPage("from Resource", offset, length); // 该分页的记录
		pb.setList(list);
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setTotalPage(totalPage);
		pb.setAllRow(count);
		mav.addObject("ress", list);
		mav.addObject("pb", pb);
		mav.setViewName("res/list");
		return mav;

	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Map save(@RequestBody JSONObject json,HttpServletRequest request) {
		log.info("执行方法>>>save");
		Map<String,String> map = new HashMap<String,String>();
		String msg = "";
		String name = json.getString("name");
		String url = json.getString("url");
		String permission = json.getString("permission");
		Resource res = new Resource();
		res.setName(name);
		res.setUrl(url);
		res.setPermission(permission);
		res.setCreateName(getSessionUser().getName());
		res.setCreateBy(getSessionUser().getId().toString());
		res.setCreateDate(new Date());
		res.setIsEnable(1);
		resourceService.save(res);
		msg = "success";
		
		Record record = new Record();
		record.setUserid(getSessionUser().getId());
		record.setIpv4(getIpAddr(request));
		record.setRecord("添加资源:"+name);
		record.setTime(getTime());
		iRecordService.add(record);
		
		map.put("success", msg);
		return map;
	}
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Map updateRes(@RequestBody JSONObject json,HttpServletRequest request) {
		log.info("执行方法>>>update");
		
		Map<String,String> map = new HashMap<String,String>();
		String msg = "";
		Integer id = json.getInt("id");
		Resource res = resourceService.getEntity(Resource.class, id);
		String name = json.getString("name");
		String url = json.getString("url");
		String permission = json.getString("permission");
		res.setName(name);
		res.setUrl(url);
		res.setPermission(permission);
		res.setUpdateBy(getSessionUser().getId().toString());
		res.setUpdateName(getSessionUser().getName());
		res.setUpdateDate(new Date());
		resourceService.saveOrUpdate(res);
		msg = "success";
		
		Record record = new Record();
		record.setUserid(getSessionUser().getId());
		record.setIpv4(getIpAddr(request));
		record.setRecord("更新资源信息ID:"+res.getId());
		record.setTime(getTime());
		iRecordService.add(record);
		
		map.put("success", msg);
		return map;
	}

	
	
	
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public Map del(@RequestBody JSONObject json,HttpServletRequest request) {
		log.info("执行方法>>>delete");
		Map<String,String> map = new HashMap<String,String>();
		int id =json.getInt("id");
		resourceService.deleteEntityById(Resource.class, id);
		
		Record record = new Record();
		record.setUserid(getSessionUser().getId());
		record.setIpv4(getIpAddr(request));
		record.setRecord("删除资源ID:"+id);
		record.setTime(getTime());
		iRecordService.add(record);
		
		map.put("msg", "success");
		return map;
	}

	
	@SuppressWarnings("static-access")
	@RequestMapping(value="nextPage" ,method=RequestMethod.GET)
	@ResponseBody
	public JSONObject nextPage(HttpServletRequest request) {
		log.info("开始执行admin/resource/nextPage 方法");
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
		int count = resourceService.getCount(Resource.class);
		int totalPage = pb.countTotalPage(pageSize, count); // 总页数
		int offset = pb.countOffset(pageSize, page); // 当前页开始记录
		int length = pageSize; // 每页记录数
		int currentPage = pb.countCurrentPage(page);
		List<Resource> list = resourceService.queryForPage("from Resource", offset, length); // 该分页的记录
		pb.setList(list);
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setTotalPage(totalPage);
		pb.setAllRow(count);
		map.put("ress", list);
		map.put("pb", pb);
		JSONObject json = JSONObject.fromObject( map ); 
		System.out.println("json===" + json);
		return json;

	}
	
	
}
