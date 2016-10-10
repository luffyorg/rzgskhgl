package cn.yznu.rzgskhgl.pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.yznu.rzgskhgl.common.BaseEntity;

@Entity
@Table(name="user")
public class User extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	
	
	/**
	 * 姓名
	 */
	@Column(name="name",columnDefinition=("varchar(50)  default '' comment '姓名'"))
	private String name;
	/**
	 * 密码
	 */
	@Column(name="password",columnDefinition=("varchar(50)  default '' comment '密码'"))
	private String password;
	/**
	 * 电话
	 */
	@Column(name="tel",columnDefinition=("bigint(20)  default null comment '电话'"))
	private long tel;
	/**
	 * 地址
	 */
	@Column(columnDefinition =( "varchar(255) default '' comment '地址'"))
	private String address;
	/**
	 * 性别
	 */
	@Column(columnDefinition =( "varchar(4) default '' comment '性别'"))
	private String gender;

	/**
	 * 总资产
	 */
	@Column(name = "total_asset",columnDefinition =( "double default null comment '总资产'"))
	private double totalAssets;
	/**
	 * 总负债
	 */
	@Column(name = "total_liability",columnDefinition = ("double default null comment '总负债'"))
	private double totalLiability;
	/**
	 * 征信情况
	 */
	@Column(name = "credit_conditions",columnDefinition=("varchar(50)  default null comment '征信情况'"))
	private String creditConditions;
	/**
	 * 行业
	 */
	@Column(columnDefinition = ("varchar(65) default null comment '行业'"))
	private String industry;
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
	@Column(name = "solid_surfacing",columnDefinition = ("int(2) default null comment '实体铺面 1:有,0：无'"))
	private int solidSurfacing;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getTel() {
		return tel;
	}
	public void setTel(long tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public double getTotalAssets() {
		return totalAssets;
	}
	public void setTotalAssets(double totalAssets) {
		this.totalAssets = totalAssets;
	}
	public double getTotalLiability() {
		return totalLiability;
	}
	public void setTotalLiability(double totalLiability) {
		this.totalLiability = totalLiability;
	}
	public String getCreditConditions() {
		return creditConditions;
	}
	public void setCreditConditions(String creditConditions) {
		this.creditConditions = creditConditions;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
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
	@Override
	public String toString() {
		return "User [name=" + name + ", password=" + password + ", tel=" + tel + ", address=" + address + ", gender="
				+ gender + ", totalAssets=" + totalAssets + ", totalLiability=" + totalLiability + ", creditConditions="
				+ creditConditions + ", industry=" + industry + ", estate=" + estate + ", movable=" + movable
				+ ", company=" + company + ", solidSurfacing=" + solidSurfacing + "]";
	}
	
	
}
