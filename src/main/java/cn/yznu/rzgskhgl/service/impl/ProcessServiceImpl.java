package cn.yznu.rzgskhgl.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.yznu.rzgskhgl.pojo.Order;
import cn.yznu.rzgskhgl.service.ICommonService;
import cn.yznu.rzgskhgl.service.IProcessService;

/**
 * 
 * @author zhangwei
 *
 */
@SuppressWarnings("deprecation")
@Service("processService")
public class ProcessServiceImpl extends CommonServiceimpl implements IProcessService {

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;

	@Override
	public HSSFWorkbook createExcel(List<Order> orders, HttpServletRequest request) {

		// 创建一个webbook，对应一个excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 在webbook中添加一个sheet,对应excel文件中的sheet
		HSSFSheet sheet = workbook.createSheet("产品信息表");
		// 设置列宽
		sheet.setColumnWidth(0, 25 * 100);
		sheet.setColumnWidth(1, 35 * 100);
		sheet.setColumnWidth(2, 35 * 100);
		sheet.setColumnWidth(3, 40 * 100);
		sheet.setColumnWidth(4, 45 * 100);
		sheet.setColumnWidth(5, 45 * 100);
		sheet.setColumnWidth(6, 50 * 100);
		sheet.setColumnWidth(7, 80 * 100);
		sheet.setColumnWidth(8, 35 * 100);
		// 在sheet中添加表头第0行
		HSSFRow row = sheet.createRow(0);
		// 创建单元格，并设置表头，设置表头居中
		HSSFCellStyle style = workbook.createCellStyle();
		// 创建一个居中格式
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 带边框
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		// 字体增粗
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 字体大小
		font.setFontHeightInPoints((short) 12);
		// 把字体应用到当前的样式
		style.setFont(font);

		// 单独设置整列居中或居左
		HSSFCellStyle style1 = workbook.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);

		HSSFCellStyle style3 = workbook.createCellStyle();
		style3.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		HSSFFont hssfFont = workbook.createFont();
		hssfFont.setColor(HSSFFont.COLOR_RED);
		hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style3.setFont(hssfFont);

		HSSFCellStyle style4 = workbook.createCellStyle();
		style4.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		HSSFFont hssfFont1 = workbook.createFont();
		hssfFont1.setColor(HSSFFont.COLOR_NORMAL);
		hssfFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style4.setFont(hssfFont1);

		HSSFCell cell = row.createCell(0);
		cell.setCellValue("序号");
		cell.setCellStyle(style);

		cell = row.createCell(1);
		cell.setCellValue("订单号");
		cell.setCellStyle(style);

		cell = row.createCell(2);
		cell.setCellValue("购买人");
		cell.setCellStyle(style);

		cell = row.createCell(3);
		cell.setCellValue("业务员");
		cell.setCellStyle(style);

		cell = row.createCell(4);
		cell.setCellValue("产品名称");
		cell.setCellStyle(style);

		cell = row.createCell(5);
		cell.setCellValue("产品说明");
		cell.setCellStyle(style);

		cell = row.createCell(6);
		cell.setCellValue("产品定价");
		cell.setCellStyle(style);

		cell = row.createCell(7);
		cell.setCellValue("订单状态");
		cell.setCellStyle(style);

		cell = row.createCell(8);
		cell.setCellValue("创建时间");
		cell.setCellStyle(style);

		cell.setCellStyle(style);
		for (int i = 0; i < orders.size(); i++) {
			row = sheet.createRow(i + 1);
			Order order = orders.get(i);
			// 创建单元格，并设置值
			// 编号列居左
			HSSFCell c1 = row.createCell(0);
			c1.setCellStyle(style2);
			c1.setCellValue(i + 1);
			HSSFCell c2 = row.createCell(1);
			c2.setCellStyle(style1);
			c2.setCellValue(order.getOrderNo());

			HSSFCell c3 = row.createCell(2);
			c3.setCellStyle(style1);
			c3.setCellValue(order.getBuyName());

			HSSFCell c4 = row.createCell(3);
			c4.setCellStyle(style1);
			c4.setCellValue(order.getCreateName());

			HSSFCell c5 = row.createCell(4);
			c5.setCellStyle(style1);
			c5.setCellValue(order.getProductName());

			HSSFCell c6 = row.createCell(5);
			c6.setCellStyle(style1);
			c6.setCellValue(order.getDescription());

			HSSFCell c7 = row.createCell(6);
			c7.setCellStyle(style1);
			c7.setCellValue(order.getProductPrice());
			// 客户签订合同---2收齐资料---3递交渠道处----4审核阶段---5下款，6收费，7完成服务
			String status = "";
			if (order.getOrderStatus() == 1) {
				status = "签订合同";
			} else if (order.getOrderStatus() == 2) {
				status = "收齐资料";
			} else if (order.getOrderStatus() == 3) {
				status = "递交渠道处";
			} else if (order.getOrderStatus() == 4) {
				status = "审核阶段";
			} else if (order.getOrderStatus() == 5) {
				status = "下款";
			} else if (order.getOrderStatus() == 6) {
				status = "收费";
			} else if (order.getOrderStatus() == 7) {
				status = "完成服务";
			}else
				status ="无";

			HSSFCell c8 = row.createCell(7);
			c8.setCellStyle(style1);
			c8.setCellValue(status);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 定义文件名格式
			HSSFCell c9 = row.createCell(8);// 创建时间
			c9.setCellStyle(style1);
			if (order.getCreateDate() == null || order.getCreateDate().equals("")) {
				c9.setCellValue("无");
			} else
				c9.setCellValue(sdf.format(order.getCreateDate()));

		}

		return workbook;
	}

	@Override
	public Order queryOrderbByOrderNo(String orderNo) {
		String hql = "from Order where orderNo='"+orderNo+"' and isEnable=1";
		Order order = commonService.getSingleByHQL(hql);
		return order;
	}

}
