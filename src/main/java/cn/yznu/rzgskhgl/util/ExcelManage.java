package cn.yznu.rzgskhgl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * 从excel读取数据/往excel中写入 excel有表头，表头每列的内容对应实体类的属性
 * 
 * @author zhangwei
 * 
 */
public class ExcelManage {
	private static final transient Logger log = Logger.getLogger(ExcelManage.class);

	private XSSFWorkbook workbook;

	public ExcelManage(File file) {
		try {
			workbook = new XSSFWorkbook(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ExcelManage(String fileDir) {
		File file = new File(fileDir);
		try {
			workbook = new XSSFWorkbook(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getCellString(XSSFCell cell) {
		if (cell == null)
			return "";

		String cellSring = "";
		switch (cell.getCellType()) {
		case XSSFCell.CELL_TYPE_STRING: // 字符串
			cellSring = cell.getStringCellValue();
			break;
		case XSSFCell.CELL_TYPE_NUMERIC: // 数字
			cellSring = String.valueOf(cell.getNumericCellValue());
			break;
		case XSSFCell.CELL_TYPE_BOOLEAN: // Boolean
			cellSring = String.valueOf(cell.getBooleanCellValue());
			break;
		case XSSFCell.CELL_TYPE_FORMULA: // 公式
			cellSring = String.valueOf(cell.getCellFormula());
			break;
		case XSSFCell.CELL_TYPE_BLANK: // 空值
			cellSring = "";
			break;
		case XSSFCell.CELL_TYPE_ERROR: // 故障
			cellSring = "";
			break;
		default:
			cellSring = "ERROR";
			break;
		}
		return cellSring;
	}

	/**
	 * 读取excel数据
	 * 
	 * @param object
	 *            实体类
	 * @param names
	 *            属性名，对应实体的属性
	 * @param startRow
	 *            开始读取行数
	 * @return
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List readFromExcel(Object object, String[] names, int startRow)
			throws IOException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {
		List result = new ArrayList();
		// 获取该对象的class对象
		Class class_ = object.getClass();
		// 获得该类的所有属性
		Field[] fields = class_.getDeclaredFields();

		// 读取excel数据
		// 获得指定的excel表
		XSSFSheet sheet = workbook.getSheetAt(0);
		if (sheet == null || "".equals(sheet)) {
			throw new IOException("表格为空，不能进行导入!");
		}
		// 获取表格的总行数
		int rowCount = sheet.getLastRowNum() + 1; // 需要加一
		System.out.println("表格总行数:" + rowCount);
		if (rowCount < 1) {
			return result;
		}
		// 获取表头的列数
		int columnCount = sheet.getRow(0).getLastCellNum();
		if (names.length > columnCount) {
			log.info("属性名长度与表的列长度不符，不能进行导入!");
			throw new IOException("属性名长度与表的列长度不符，不能进行导入!");
		}
		// 读取表头信息,确定需要用的方法名---set方法
		// 用于存储方法名
		String[] methodNames = new String[columnCount]; // 表头列数即为需要的set方法个数
		// 用于存储属性类型
		String[] fieldTypes = new String[columnCount];
		// 获得表头行对象
		XSSFRow titleRow = sheet.getRow(0);
		// 遍历
		for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) { // 遍历表头列
			String name = names[columnIndex];
			String Udata = Character.toUpperCase(name.charAt(0)) + name.substring(1, name.length()); // 使其首字母大写
			methodNames[columnIndex] = "set" + Udata;

			for (int i = 0; i < fields.length; i++) { // 遍历属性数组
				if (name.equals(fields[i].getName())) { // 属性与表头相等
					fieldTypes[columnIndex] = fields[i].getType().getName(); // 将属性类型放到数组中
				}
			}

			if (null == fieldTypes[columnIndex] || "".equals(fieldTypes[columnIndex])) {
				log.info("找不到" + name + "属性，不能进行导入!");
				throw new IOException("找不到" + name + "属性，不能进行导入!");
			}
		}
		// 逐行读取数据 从1开始 忽略表头
		for (int rowIndex = startRow; rowIndex < rowCount; rowIndex++) {
			// 获得行对象
			XSSFRow row = sheet.getRow(rowIndex);
			if (row != null) {
				Object obj = null;
				// 实例化该泛型类的对象一个对象
				obj = class_.newInstance();
				// 获得本行中各单元格中的数据
				for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {

					String data = this.getCellString(row.getCell(columnIndex));
					// 获取要调用方法的方法名
					String methodName = methodNames[columnIndex];
					Method method = null;
					System.out.println(data + ">>>>>>>>>>>>");
					// 这部分可自己扩展
					if (fieldTypes[columnIndex].equals("java.lang.String")) {
						method = class_.getDeclaredMethod(methodName, String.class); // 设置要执行的方法--set方法参数为String
						if (data.matches("^[-+]?(([0]+)([.]([0]+))?|([.]([0]+))?)$")) {
							data = "";
						}
						if(data.indexOf(".") > 0){
						     //正则表达
							data = data.replaceAll("0+?$", "");//去掉后面无用的零
							data = data.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
						}
						method.invoke(obj, data); // 执行该方法
					} else if (fieldTypes[columnIndex].equals("int")) {
						method = class_.getDeclaredMethod(methodName, int.class); // 设置要执行的方法--set方法参数为int
						double data_double = Double.parseDouble(data.equals("") ? "0" : data);
						int data_int = (int) data_double;
						method.invoke(obj, data_int); // 执行该方法
					} else if (fieldTypes[columnIndex].equals("double")) {
						method = class_.getDeclaredMethod(methodName, double.class); // 设置要执行的方法--set方法参数为int
						double data_double = Double.parseDouble(data.equals("") ? "0" : data);
						method.invoke(obj, data_double); // 执行该方法
					}

				}
				result.add(obj);
			}
		}
		return result;
	}

}