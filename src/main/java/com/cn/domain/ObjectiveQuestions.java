package com.cn.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 客观题表
 * @author Hang
 * @date 2016年2月29日上午10:30:47
 * @version
 */
public class ObjectiveQuestions {
	//客观题ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "oid", nullable = false, length = 50, unique = true)
	private int oid;
	//客观题内容
	@Column(name = "ocontent", nullable = false, length = 255)
	private String ocontent;
	//客观题类型
	@Column(name = "otype", nullable = false, length = 5)
	private int otype;
	//客观题备注
	@Column(name = "onote", nullable = true, length = 255)
	private String onote;
	//选项A
	@Column(name = "oanswera", nullable = true, length = 5)
	private int oanswera;
	//选项B
	@Column(name = "oanswerb", nullable = true, length = 5)
	private int oanswerb;
	//选项C
	@Column(name = "oanswerc", nullable = true, length = 5)
	private int oanswerc;
	//选项D
	@Column(name = "oanswerd", nullable = true, length = 5)
	private int oanswerd;
	//选项E
	@Column(name = "oanswere", nullable = true, length = 5)
	private int oanswere;
	//选项F
	@Column(name = "oanswerf", nullable = true, length = 5)
	private int oanswerf;
	//对应的问卷
	@Column(name = "oquertionnaire", nullable = false, length = 50)
	private String oquertionnaire;
}
