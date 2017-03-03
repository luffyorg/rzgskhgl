package cn.yznu.rzgskhgl.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.yznu.rzgskhgl.common.PageBean;
import cn.yznu.rzgskhgl.pojo.Record;
import cn.yznu.rzgskhgl.pojo.User;
import cn.yznu.rzgskhgl.service.ICommonService;
import net.sf.json.JSONObject;

/**
 *  操作记录控制类
 * @author 张伟
 *
 */
@RequestMapping("/admin/record")
@Controller
public class RecordController extends BaseController {
	private static Logger log = Logger.getLogger(RecordController.class);
	@Autowired
	private ICommonService commonService;
	
	@SuppressWarnings({ "static-access", "rawtypes" })
	@RequestMapping(value = "list")
	public ModelAndView list(HttpServletRequest request) {
		log.info("进入操作记录界面");
		ModelAndView mav = new ModelAndView();
		PageBean pb = new PageBean();
		String pagesize = request.getParameter("pageSize");
		String page1 = request.getParameter("page");
		if (pagesize == null || pagesize.equals("")) {
			pagesize = "10";
		}
		if (page1 == null || page1.equals("")) {
			page1 = "1";
		}
		int pageSize = Integer.parseInt(pagesize);
		int page = Integer.parseInt(page1);
		int count = commonService.getCount(Record.class);
		int totalPage = pb.countTotalPage(pageSize, count); // 总页数
		int offset = pb.countOffset(pageSize, page); // 当前页开始记录
		int length = pageSize; // 每页记录数
		int currentPage = pb.countCurrentPage(page);
		List<Object> list = commonService.queryForPage("from Record r,User u where r.userid=u.id order by time desc", offset,
				length); // 该分页的记录
		Iterator iterator1 = list.iterator();
		List<User> users = new ArrayList<User>();
		List<Record> records = new ArrayList<Record>();
		while (iterator1.hasNext()) {
			Object[] o = (Object[]) iterator1.next();
			User user = (User) o[1];
			Record record = (Record) o[0];
			users.add(user);
			records.add(record);
		}
		pb.setList(list);
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setTotalPage(totalPage);
		pb.setAllRow(count);
		mav.addObject("pb", pb);
		mav.addObject("users", users);
		mav.addObject("records", records);
		mav.setViewName("record/list");
		return mav;
	}
	@SuppressWarnings({ "static-access", "rawtypes" })
	@RequestMapping(value = "nextPage", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject nextPage(HttpServletRequest request) {
		log.info("开始执行admin/user/nextPage 方法");
		Map<String, Object> map = new HashMap<String, Object>();
		PageBean pb = new PageBean();
		String pagesize = request.getParameter("pageSize");
		String page1 = request.getParameter("page");
		String name = request.getParameter("username");
		String startTime = request.getParameter("startTime");// 创建时间
		String endTime = request.getParameter("endTime");// 创建时间

		if (pagesize == null || pagesize.equals("")) {
			pagesize = "10";
		}
		if (page1 == null || page1.equals("")) {
			page1 = "1";
		}
		int pageSize = Integer.parseInt(pagesize);
		int page = Integer.parseInt(page1);

		int offset = pb.countOffset(pageSize, page); // 当前页开始记录
		int length = pageSize; // 每页记录数
		int currentPage = pb.countCurrentPage(page);
		String hql = "from Record r,User u where r.userid=u.id ";
		String hqlCount = "select count(*) from Record r where 1=1 ";
		User u = commonService.findUniqueByProperty(User.class , "name", name);
		if (u != null && !u.equals("")) {
			hql += "and r.userid = '" + u.getId() + "' ";
			hqlCount += "and r.userid = '" + u.getId() + "' ";
		}
		if (startTime != null && !startTime.equals("") && endTime != null && !endTime.equals("")) {
			hql += "and r.time >= '" + startTime + "' and r.time <='"+endTime+"' ";
			hqlCount += "and time >= '" + startTime + "' and time <='"+endTime+"' ";
		}
		
		hql += "ORDER BY r.time DESC";
		List<Object> list = commonService.queryForPage(hql, offset, length); // 该分页的记录
		Iterator iterator1 = list.iterator();
		List<User> users = new ArrayList<User>();
		List<Record> records = new ArrayList<Record>();
		while (iterator1.hasNext()) {
			Object[] o = (Object[]) iterator1.next();
			User user = (User) o[1];
			Record record = (Record) o[0];
			users.add(user);
			records.add(record);
		}
		int count = commonService.getCountByParam(hqlCount);
		int totalPage = pb.countTotalPage(pageSize, count); // 总页数
		pb.setList(list);
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setTotalPage(totalPage);
		pb.setAllRow(count);
		map.put("pb", pb);
		map.put("users", users);
		map.put("records", records);
		JSONObject jsonObject = JSONObject.fromObject(map);
		return jsonObject;
	}
	@SuppressWarnings({ "static-access", "rawtypes" })
	@RequestMapping("/search")
	@ResponseBody
	public JSONObject search(HttpServletRequest request) {
		log.info("操作记录---搜索记录");
		Map<String, Object> map = new HashMap<String, Object>();
		PageBean pb = new PageBean();
		String pagesize = request.getParameter("pageSize");// 每页显示的数量
		String page1 = request.getParameter("page");// 当前页
		String name = request.getParameter("username");// 业务员账户
		String startTime = request.getParameter("startTime");// 创建时间
		String endTime = request.getParameter("endTime");// 创建时间
		User u = commonService.findUniqueByProperty(User.class , "name", name);
		if (pagesize == null || pagesize.equals("")) {
			pagesize = "10";
		}
		if (page1 == null || page1.equals("")) {
			page1 = "1";
		}
		int pageSize = Integer.parseInt(pagesize);
		int page = Integer.parseInt(page1);

		int offset = pb.countOffset(pageSize, page); // 当前页开始记录
		int length = pageSize; // 每页记录数
		int currentPage = pb.countCurrentPage(page);
		String hql = "from Record r,User u where r.userid=u.id ";
		String hqlCount = "select count(*) from Record r where 1=1 ";
		if (u != null && !u.equals("")) {
			hql += "and r.userid = '" + u.getId() + "' ";
			hqlCount += "and r.userid = '" + u.getId() + "' ";
		}
		if (startTime != null && !startTime.equals("") && endTime != null && !endTime.equals("")) {
			hql += "and r.time >= '" + startTime + "' and r.time <='"+endTime+"' ";
			hqlCount += "and time >= '" + startTime + "' and time <='"+endTime+"' ";
		}
		
		hql += "ORDER BY r.time DESC";
		List<Object> list = commonService.queryForPage(hql, offset, length); // 该分页的记录
		Iterator iterator1 = list.iterator();
		List<User> users = new ArrayList<User>();
		List<Record> records = new ArrayList<Record>();
		while (iterator1.hasNext()) {
			Object[] o = (Object[]) iterator1.next();
			User user = (User) o[1];
			Record record = (Record) o[0];
			users.add(user);
			records.add(record);
		}
		int count = commonService.getCountByParam(hqlCount);
		int totalPage = pb.countTotalPage(pageSize, count); // 总页数
		pb.setList(list);
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setTotalPage(totalPage);
		pb.setAllRow(count);
		map.put("pb", pb);
		map.put("users", users);
		map.put("records", records);
		JSONObject jsonObject = JSONObject.fromObject(map);
		return jsonObject;

	}
}
