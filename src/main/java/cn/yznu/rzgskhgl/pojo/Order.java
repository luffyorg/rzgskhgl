package cn.yznu.rzgskhgl.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.yznu.rzgskhgl.common.BaseEntity;

/**
 * 产品订单表
 * 
 * @author 张伟
 *
 */
@Entity
@Table(name = "product_order")
public class Order extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 订单号 */
	@Column(name = "order_no", length = 20)
	private String orderNo;

	/** 购买人 */
	@Column(name = "buy_name")
	private String buyName;
	/** 购买人id */
	@Column(name = "buy_name_id")
	private int buyNameId;

	/** 业务员 */
	@Column(name = "sales_man")
	private String salesMan;
	/** 业务员id */
	@Column(name = "sales_man_id")
	private int salesManId;

	/** 产品名称 */
	@Column(name = "product_name")
	private String productName;
	
	/** 产品描述*/
	private String description;
	
	/** 产品价格*/
	@Column(name = "product_price",columnDefinition =( "double default null comment '产品价格'"))
	private double productPrice;
	
	/** 产品数量*/
	private int productNum;
	
	/** 订单状态 ：1与客户签订合同---2收齐资料---3递交渠道处----4审核阶段---5下款，6收费，7完成服务 */
	@Column(name = "order_status" ,columnDefinition =( "int(11) default null comment '1与客户签订合同---2收齐资料---3递交渠道处----4审核阶段---5下款，6收费，7完成服务'"))
	private int orderStatus;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBuyName() {
		return buyName;
	}

	public void setBuyName(String buyName) {
		this.buyName = buyName;
	}

	public String getSalesMan() {
		return salesMan;
	}

	public void setSalesMan(String salesMan) {
		this.salesMan = salesMan;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public int getBuyNameId() {
		return buyNameId;
	}

	public void setBuyNameId(int buyNameId) {
		this.buyNameId = buyNameId;
	}

	public int getSalesManId() {
		return salesManId;
	}

	public void setSalesManId(int salesManId) {
		this.salesManId = salesManId;
	}

	public int getProductNum() {
		return productNum;
	}

	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}

	
	
	
}
