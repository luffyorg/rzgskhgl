package cn.yznu.rzgskhgl.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cn.yznu.rzgskhgl.pojo.Customer;

/**
 * 客户服务接口
 * 
 * @author 张伟
 *
 */
public interface ICustomerService extends ICommonService {




	/**
	 * 导出用户数据到excel
	 * @param customers
	 * @param request
	 * @return
	 */
	public HSSFWorkbook createExcel(List<Customer> customers, HttpServletRequest request);

}
