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
	 * 登录名
	 */
	@Column(name="name",columnDefinition=("varchar(50)  default '' comment '登录名'"))
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
	 * 邮箱
	 */
	@Column(name="email",columnDefinition=("varchar(50)  default '' comment '邮箱'"))
	private String email;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
