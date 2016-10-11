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
@Table(name = "order")
public class Order extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 订单号 */
	@Column(name = "order_no", length = 20, nullable = true)
	private String orderNo;

	/** 购买人 */
	@Column(name = "buy_name")
	private String buyName;

	/** 业务员 */
	@Column(name = "sales_man")
	private String salesMan;

	/** 产品名称 */
	@Column(name = "product_name")
	private String productName;
	
	/** 产品价格*/
	@Column(name = "product_price")
	private double productPrice;
	
	/** 订单状态 ：1与客户签订合同---2收齐资料---3递交渠道处----4审核阶段---5下款，6收费，7完成服务 */
	@Column(name = "order_status", length = 20, nullable = true)
	private int orderStatus;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	@Override
	public String toString() {
		return "Order [orderNo=" + orderNo + ", buyName=" + buyName + ", salesMan=" + salesMan + ", productName="
				+ productName + ", productPrice=" + productPrice + ", orderStatus=" + orderStatus + "]";
	}

	
	
	
}
