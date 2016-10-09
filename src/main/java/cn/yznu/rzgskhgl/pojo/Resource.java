package cn.yznu.rzgskhgl.pojo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.yznu.rzgskhgl.common.BaseEntity;

@Entity
@Table(name="resource")
public class Resource extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String permission;
	private String url;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
