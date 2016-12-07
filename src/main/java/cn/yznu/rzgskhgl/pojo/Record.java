package cn.yznu.rzgskhgl.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="record")
public class Record  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
    /**
     * 用户ID
     */
	@Column(name="userid",columnDefinition=("varchar(50)  default '' comment '真实姓名'"))
	private Integer userid;
	
	/**
     * 操作
     */
	@Column(name="record",columnDefinition=("varchar(5000)  default '' comment '操作'"))
	private String record;

	/**
	 * 操作IP
	 */
	@Column(name="ipv4",columnDefinition=("varchar(32)  default '' comment '操作'"))
	private String ipv4;
	
	/**
	 * 操作时间
	 * @return
	 */
	@Column(name="time",columnDefinition=("varchar(50)  default '' comment '操作'"))
	private String time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public String getIpv4() {
		return ipv4;
	}

	public void setIpv4(String ipv4) {
		this.ipv4 = ipv4;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	
}
