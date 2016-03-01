package com.cn.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Hang
 * @date 2016年2月29日下午1:38:25
 * @version
 */
@Entity
@Table(name = "powergroup")
public class PowerGroup {
	@Id
	@GeneratedValue
	@Column
	private Long pgid;  //主键id
	
	@Column
	private Power pgpid;  //权限id
	
	@Column
	private Groop pggid;  //组id

	public PowerGroup() {
		super();
	}
	
	public PowerGroup(Long pgid, Power pgpid, Groop pggid) {
		super();
		this.pgid = pgid;
		this.pgpid = pgpid;
		this.pggid = pggid;
	}
	
}
