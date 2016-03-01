package com.cn.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 权限表
 * @author Hang
 * @date 2016年2月29日上午10:39:47
 * @version
 */
@Entity
@Table(name = "power")
public class Power {
	//权限ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pid", nullable = false, length = 50, unique = true)
	private int pid;
	
	//权限名
	@Column(name = "pname", nullable = false, length = 1)
	private String pname;
	
	//权限类型
	@Column(name = "ptype", nullable = false, length = 5)
	private int ptype;
	
	//权限备注
	@Column(name = "pnote", nullable = true, length = 255)
	private String pnote;
	
}
