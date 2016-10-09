package cn.yznu.rzgskhgl.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.yznu.rzgskhgl.common.BaseEntity;

/**
 * 产品信息表
 * 
 * @author zhangw
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "product")
public class Product extends BaseEntity implements Serializable {
	/**
	 * 产品名称
	 */
	private String name;
	/**
	 * 产品编号
	 */
	@Column(name = "product_no")
	private String productNo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public Product(String name, String productNo) {
		super();
		this.name = name;
		this.productNo = productNo;
	}


	@Override
	public String toString() {
		return "Product [name=" + name + ", productNo=" + productNo + "]";
	}

}
