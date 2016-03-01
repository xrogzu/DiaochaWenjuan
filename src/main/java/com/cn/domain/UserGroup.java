package com.cn.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class UserGroup {
	
	@Id
	@GeneratedValue
	@Column
	private Long ugid;  //主键ID
	@Column
	private Users uguid;  //用户ID
	@Column
	private Groop uggid;  //组ID
	
	public UserGroup() {
		super();
	}
	
	public UserGroup(Long ugid, Users uguid, Groop uggid) {
		super();
		this.ugid = ugid;
		this.uguid = uguid;
		this.uggid = uggid;
	}
	
}
