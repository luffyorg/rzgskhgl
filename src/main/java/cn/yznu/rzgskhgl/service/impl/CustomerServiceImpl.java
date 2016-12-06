package cn.yznu.rzgskhgl.service.impl;

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

import cn.yznu.rzgskhgl.pojo.Customer;
import cn.yznu.rzgskhgl.service.ICommonService;
import cn.yznu.rzgskhgl.service.ICustomerService;
import cn.yznu.rzgskhgl.service.IRoleService;

@Service("customerService")
public class CustomerServiceImpl extends CommonServiceimpl implements ICustomerService {
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	@Autowired
	@Qualifier("roleService")
	private IRoleService roleService;

	@Override
	public HSSFWorkbook createExcel(List<Customer> customers, HttpServletRequest request) {

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
		cell.setCellValue("姓名");
		cell.setCellStyle(style);

		cell = row.createCell(2);
		cell.setCellValue("电话");
		cell.setCellStyle(style);

		cell = row.createCell(3);
		cell.setCellValue("地址");
		cell.setCellStyle(style);

		cell = row.createCell(4);
		cell.setCellValue("性别");
		cell.setCellStyle(style);

		cell = row.createCell(5);
		cell.setCellValue("总资产");
		cell.setCellStyle(style);

		cell = row.createCell(6);
		cell.setCellValue("总负债");
		cell.setCellStyle(style);

		cell = row.createCell(7);
		cell.setCellValue("征信情况");
		cell.setCellStyle(style);

		cell = row.createCell(8);
		cell.setCellValue("行业");
		cell.setCellStyle(style);

		cell = row.createCell(9);
		cell.setCellValue("房产");
		cell.setCellStyle(style);

		cell = row.createCell(10);
		cell.setCellValue("动产");
		cell.setCellStyle(style);

		cell = row.createCell(11);
		cell.setCellValue("动产");
		cell.setCellStyle(style);

		cell = row.createCell(12);
		cell.setCellValue("公司");
		cell.setCellStyle(style);

		cell = row.createCell(12);
		cell.setCellValue("实体");
		cell.setCellStyle(style);

		cell = row.createCell(13);
		cell.setCellValue("客户状态");
		cell.setCellStyle(style);

		for (int i = 0; i < customers.size(); i++) {
			row = sheet.createRow(i + 1);
			Customer user = customers.get(i);
			// 创建单元格，并设置值
			// 编号列居左
			HSSFCell c1 = row.createCell(0);
			c1.setCellStyle(style2);
			c1.setCellValue(i + 1);
			HSSFCell c2 = row.createCell(1);
			c2.setCellStyle(style1);
			c2.setCellValue(user.getName());

			HSSFCell c3 = row.createCell(2);
			c3.setCellStyle(style1);
			c3.setCellValue(user.getTel());

			HSSFCell c4 = row.createCell(3);
			c4.setCellStyle(style1);
			c4.setCellValue(user.getAddress());

			HSSFCell c5 = row.createCell(4);
			c5.setCellStyle(style1);
			c5.setCellValue(user.getGender());

			HSSFCell c6 = row.createCell(5);
			c6.setCellStyle(style1);
			c6.setCellValue(user.getTotalAssets());

			HSSFCell c7 = row.createCell(6);
			c7.setCellStyle(style1);
			c7.setCellValue(user.getTotalLiability());

			HSSFCell c8 = row.createCell(7);
			c8.setCellStyle(style1);
			c8.setCellValue(user.getCreditConditions());

			HSSFCell c9 = row.createCell(8);
			c9.setCellStyle(style1);
			c9.setCellValue(user.getIndustry());

			String estate = "";
			if (user.getEstate() == 1) {
				estate = "有";
			} else
				estate = "无";
			HSSFCell c10 = row.createCell(9);
			c10.setCellStyle(style1);
			c10.setCellValue(estate);

			String movable = "";
			if (user.getMovable() == 1) {
				movable = "有";
			} else
				movable = "无";
			HSSFCell c11 = row.createCell(10);
			c11.setCellStyle(style1);
			c11.setCellValue(movable);

			String company = "";
			if (user.getCompany() == 1) {
				company = "有";
			} else
				company = "无";
			HSSFCell c12 = row.createCell(11);
			c12.setCellStyle(style1);
			c12.setCellValue(company);

			String solidSurfacing = "";
			if (user.getSolidSurfacing() == 1) {
				solidSurfacing = "有";
			} else
				solidSurfacing = "无";
			HSSFCell c13 = row.createCell(12);
			c13.setCellStyle(style1);
			c13.setCellValue(solidSurfacing);

			String isEnable = "";
			if (user.getIsEnable() == 1) {
				isEnable = "启用";
			} else
				isEnable = "停用";
			HSSFCell c14 = row.createCell(13);// 状态
			c14.setCellStyle(style1);
			c14.setCellValue(isEnable);

		}

		return workbook;
	}

	@Override
	public List<Customer> getAllCustomer(String hql) {
		return commonService.findHql(Customer.class, hql);
	}

}
