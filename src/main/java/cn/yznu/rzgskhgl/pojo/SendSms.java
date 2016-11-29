package cn.yznu.rzgskhgl.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.yznu.rzgskhgl.common.BaseEntity;

/**
 * 发送短信记录实体类
 * @author zhangwei
 * @date 2016-11-28
 */
@Entity
@Table(name = "send_sms")
public class SendSms extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	@Column(name="sms_mob",columnDefinition=("bigint(20)  default null comment '短信接收手机'"))
	private long smsMob;//接收手机
	
	@Column(name="sms_text",columnDefinition=("varchar(255)  default '' comment '发送内容'"))
	private String smsText;//发送内容 
	
	@Column(columnDefinition = ("int(2) default null comment '状态  0：未发送，1：已发送'"))
	private int status ;//状态  0：未发送，1：已发送

	public long getSmsMob() {
		return smsMob;
	}

	public void setSmsMob(long smsMob) {
		this.smsMob = smsMob;
	}

	public String getSmsText() {
		return smsText;
	}

	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
