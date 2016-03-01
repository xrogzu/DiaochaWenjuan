package com.cn.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 用户组表
 * @author Hang
 * @date 2016年2月29日上午10:49:29
 * @version
 */
@Entity
@Table(name = "groop")
public class Groop {
	//组ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gid", nullable = false, length = 50, unique = true)
	private int gid;
	//组名
	@Column(name = "gname", nullable = false, length = 10)
	private String gname;
	//组类型
	@Column(name = "gtype", nullable = false, length = 5)
	private int gtype;
	//组备注
	@Column(name = "gnote", nullable = true, length = 255)
	private String gnote;
	//组权限
	@ManyToMany()
	private String gpower;
	//
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "uid")
	private String guser;
	
}
