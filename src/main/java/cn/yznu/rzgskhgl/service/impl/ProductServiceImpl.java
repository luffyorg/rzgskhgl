package cn.yznu.rzgskhgl.service.impl;

import java.util.List;

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

}
