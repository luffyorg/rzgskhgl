package cn.yznu.rzgskhgl.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.yznu.rzgskhgl.common.BaseEntity;
/**
 * @Deprecated 微信 accessToken 实体类
 * @author zhangwei
 *
 */
@Entity
@Table(name = "wx_access_token")
public class AccessToken extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 8991285156225074128L;
	// 接口访问凭证
	private String accessToken;
	// 凭证有效期，单位：秒
	private int expiresIn;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	
}