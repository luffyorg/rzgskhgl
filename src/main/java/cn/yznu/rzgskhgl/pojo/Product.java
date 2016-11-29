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
	@Column(columnDefinition = ("varchar(50) default null comment '产品名称'"))
	private String name;
	/**
	 * 产品编号
	 */
	@Column(name = "product_no", columnDefinition = ("varchar(50) default null comment '产品编号'"))
	private String productNo;
	/**
	 * 适用对象
	 */
	private String suitable;
	
	
	/** 产品介绍 */
	private String description;

	/** 产品定价*/
	@Column(name="product_price")
	private	String productPrice;
	/**
	 * 房产(1:有,0:无)
	 */
	@Column(columnDefinition = ("int(2) default null comment '房产 1:有,0：无'"))
	private int estate;
	/**
	 * 动产(1:有,0:无)
	 */
	@Column(columnDefinition = ("int(2) default null comment '动产 1:有,0：无'"))
	private int movable;
	/**
	 * 公司˾(1:有,0:无)
	 */
	@Column(columnDefinition = ("int(2) default null comment '公司 1:有,0：无'"))
	private int company;
	/**
	 * 实体铺面(1:有,0:无)
	 */
	@Column(name = "solid_surfacing", columnDefinition = ("int(2) default null comment '实体铺面 1:有,0：无'"))
	private int solidSurfacing;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public int getEstate() {
		return estate;
	}

	public void setEstate(int estate) {
		this.estate = estate;
	}

	public int getMovable() {
		return movable;
	}

	public void setMovable(int movable) {
		this.movable = movable;
	}

	public int getCompany() {
		return company;
	}

	public void setCompany(int company) {
		this.company = company;
	}

	public int getSolidSurfacing() {
		return solidSurfacing;
	}

	public void setSolidSurfacing(int solidSurfacing) {
		this.solidSurfacing = solidSurfacing;
	}

	public Product() {
		super();
	}

	public String getSuitable() {
		return suitable;
	}

	public void setSuitable(String suitable) {
		this.suitable = suitable;
	}

}
