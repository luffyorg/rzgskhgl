package cn.yznu.rzgskhgl.service;

import java.util.List;

import cn.yznu.rzgskhgl.pojo.Product;
import cn.yznu.rzgskhgl.pojo.User;

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
	 * 根据产品查询出符合条件的用户
	 * @param product
	 * @return
	 */
	public List<User> queryBuyUsers(Product product);
	
	
	
}
