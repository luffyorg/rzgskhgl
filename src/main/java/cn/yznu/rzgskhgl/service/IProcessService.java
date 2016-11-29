package cn.yznu.rzgskhgl.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cn.yznu.rzgskhgl.pojo.Order;

/**
 * @deprecated 业务流程服务类
 * @author zhangwei
 * @date 2016-11-25
 */
public interface IProcessService extends ICommonService {
	/**
	 * 导出报表服务
	 * @param orders
	 * @param request
	 * @return
	 */
	public HSSFWorkbook createExcel(List<Order> orders, HttpServletRequest request);
	
	/**
	 * 根据订单号 和查询订单订单状态为1
	 */
	public Order queryOrderbByOrderNo(String orderNo);
}
