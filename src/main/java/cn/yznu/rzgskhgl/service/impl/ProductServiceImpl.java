package cn.yznu.rzgskhgl.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import cn.yznu.rzgskhgl.pojo.Product;
import cn.yznu.rzgskhgl.pojo.User;
import cn.yznu.rzgskhgl.service.ICommonService;
import cn.yznu.rzgskhgl.service.IProductService;

@Service("productService")
public class ProductServiceImpl extends CommonServiceimpl implements IProductService {

	@Autowired
	@Qualifier("commonService")
	private ICommonService common;

	@Override
	public List<Product> getAllProduct() {
		String hql = "from Product";
		return common.findHql(Product.class, hql);
	}

	@Override
	public List<User> queryBuyUsers(Product product) {

		String hql = "from User where isEnable=1 and estate>=? and movable>=? and  company>=? and solidSurfacing>=?";
		Object[] param = { product.getEstate(), product.getMovable(), product.getCompany(),
				product.getSolidSurfacing() };
		return common.findHql(hql, param);
	}

	@SuppressWarnings("null")
	@Override
	public String batchSaveOrUpdate(List<Product> products) {
		List<Product> list = null;
		for(Product p : products){
			p.setIsEnable(1);
			p.setCreateDate(new Date());
			list.add(p);
		}
		batchSave(list);
		
		return "success";
	}
	
	@Override
	public HSSFWorkbook createExcel(List<Product> products,  
	        HttpServletRequest request) {  
	  
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
	        cell.setCellValue("产品名称");  
	        cell.setCellStyle(style);  
	  
	        cell = row.createCell(2);  
	        cell.setCellValue("产品编号");  
	        cell.setCellStyle(style);  
	  
	        cell = row.createCell(3);  
	        cell.setCellValue("产品价格");  
	        cell.setCellStyle(style);  
	  
	        cell = row.createCell(4);  
	        cell.setCellValue("产品说明");  
	        cell.setCellStyle(style);  
	  
	        cell = row.createCell(5);  
	        cell.setCellValue("房产");  
	        cell.setCellStyle(style);  
	  
	        cell = row.createCell(6);  
	        cell.setCellValue("动产");  
	        cell.setCellStyle(style);  
	        
	        cell = row.createCell(7); 
	        cell.setCellValue("公司");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell(8);  
	        cell.setCellValue("实体"); 
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell(9);  
	        cell.setCellValue("状态"); 
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell(10);  
	        cell.setCellValue("创建时间"); 
	        cell.setCellStyle(style); 
	        
	        cell.setCellStyle(style);  
	        for (int i = 0; i < products.size(); i++) {  
	            row = sheet.createRow(i + 1);  
	            Product product = products.get(i);  
	            // 创建单元格，并设置值  
	            // 编号列居左  
	            HSSFCell c1 = row.createCell(0);  
	            c1.setCellStyle(style2);  
	            c1.setCellValue(i+1);  
	            HSSFCell c2 = row.createCell(1);  
	            c2.setCellStyle(style1);  
	            c2.setCellValue(product.getName());//产品名称 
	  
	           
	            HSSFCell c3 = row.createCell(2);//产品编号 
	            c3.setCellStyle(style1);  
	            c3.setCellValue(product.getProductNo());  
	              
	            HSSFCell c4 = row.createCell(3);//产品价格 
	            c4.setCellStyle(style1);  
	            c4.setCellValue(product.getProductPrice());  
	           
	            HSSFCell c5 = row.createCell(4);//产品说明
	            c5.setCellStyle(style1);  
	            c5.setCellValue(product.getDescription());  
	            
	            String estate = "";
	            if(product.getEstate()==1){
	            	estate = "有";
	            }else
	            	estate = "无";
	            HSSFCell c6 = row.createCell(5);//房产  
	            c6.setCellStyle(style1);  
	            c6.setCellValue(estate);  
	            
	            String movable = "";
	            if(product.getMovable()==1){
	            	movable = "有";
	            }else
	            	movable = "无";
	            HSSFCell c7 = row.createCell(6);//动产 
	            c7.setCellStyle(style1);  
	            c7.setCellValue(movable);  
	            
	            String company = "";
	            if(product.getCompany()==1){
	            	company = "有";
	            }else
	            	company = "无";
	            HSSFCell c8 = row.createCell(7);//公司
	            c8.setCellStyle(style1);  
	            c8.setCellValue(company);  
	            
	            String solidSurfacing = "";
	            if(product.getSolidSurfacing()==1){
	            	solidSurfacing = "有";
	            }else
	            	solidSurfacing = "无";
	            HSSFCell c9 = row.createCell(8);//实体
	            c9.setCellStyle(style1);  
	            c9.setCellValue(solidSurfacing);  
	            
	            String isEnable = "";
	            if(product.getIsEnable()==1){
	            	isEnable = "上架";
	            }else
	            	isEnable = "下架";
	            HSSFCell c10 = row.createCell(9);//状态
	            c10.setCellStyle(style1);  
	            c10.setCellValue(isEnable);  
	            
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 定义文件名格式
	            HSSFCell c11 = row.createCell(10);//创建时间
	            c11.setCellStyle(style1);  
	            if(product.getCreateDate()==null || product.getCreateDate().equals("")){
	            	c11.setCellValue("无");  
	            }else
	            	c11.setCellValue(sdf.format(product.getCreateDate()));  
	  
	          /*  //使用默认格式  
	            row.createCell(1).setCellValue(product.getName());  
	            row.createCell(2).setCellValue(product.getProductNo());  
	            row.createCell(3).setCellValue(product.getProductPrice());  
	            row.createCell(4).setCellValue(product.getDescription());  
	            row.createCell(5).setCellValue(product.getEstate());  
	            row.createCell(6).setCellValue(product.getMovable());  
	            row.createCell(7).setCellValue(product.getCompany());  
	            row.createCell(8).setCellValue(product.getSolidSurfacing());  
	            row.createCell(9).setCellValue(product.getIsEnable());  
	            row.createCell(10).setCellValue(product.getCreateDate());  */
	        }  
	       
		return workbook;  
	} 

}
