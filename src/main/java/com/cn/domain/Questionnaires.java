package com.cn.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 问卷表
 * @author Hang
 * @date 2016年2月29日上午10:23:37
 * @version
 */
public class Questionnaires {
	//问卷ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "qid", nullable = false, length = 50, unique = true)
	private int qid;
	//问卷标题
	@Column(name = "qtitle", nullable = false, length = 100)
	private String qtitle;
	//问卷内容
	@Column(name = "qcontent", nullable = false, length = 255)
	private String qcontent;
	//问卷创建时间
	@Column(name = "qcreatetime", nullable = false, length = 50)
	private Date qcreatetime;
	//问卷类型
	@Column(name = "qtype", nullable = false, length = 5)
	private int qtype;
	//问卷属性
	@Column(name = "qnature", nullable = false, length = 5)
	private int qnature;
	//问卷权限
	@Column(name = "qpower", nullable = false, length = 5)
	private int qpower;
	//问卷最后使用时间
	@Column(name = "qlasttime", nullable = false, length = 50)
	private Date qlasttime;
	
}
