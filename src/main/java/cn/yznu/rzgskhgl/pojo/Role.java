package cn.yznu.rzgskhgl.pojo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.yznu.rzgskhgl.common.BaseEntity;

@Entity
@Table(name="role")
public class Role extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 角色说明
	 */
	private String sn;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	
}
