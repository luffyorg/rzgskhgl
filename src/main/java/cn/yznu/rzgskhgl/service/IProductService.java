package cn.yznu.rzgskhgl.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cn.yznu.rzgskhgl.pojo.Customer;
import cn.yznu.rzgskhgl.pojo.Product;

/**
 * 产品服务接口
 * @author 张伟
 *
 */
public interface IProductService extends ICommonService{

	/**
	 * 得到全部的产品
	 * @return
	 */
	public List<Product> getAllProduct();
	
	
	/**
	 * 根据产品查询出符合条件的客户
	 * @param product
	 * @return
	 */
	public List<Customer> queryBuyCustomers(Product product);
	
	/**
	 * excel导入数据的时候 批了更新数据和保存数据
	 * @param products
	 * @return
	 */
	public String batchSaveOrUpdate(List<Product> products);
	public HSSFWorkbook createExcel(List<Product> products,  
	        HttpServletRequest request);
	
	/**
	 * 搜索产品
	 * @param product
	 * @return
	 */
	public List<Product> searchProduct(Product product);
	
	
}
