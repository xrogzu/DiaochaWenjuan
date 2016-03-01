package com.cn.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 主观题表
 * @author Hang
 * @date 2016年2月29日上午10:37:16
 * @version
 */
public class SubjectiveQuestions {
	//主观题表ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sid", nullable = false, length = 50, unique = true)
	private int sid;
	//主观题表内容
	@Column(name = "scontent", nullable = false, length = 100)
	private String scontent;
	//主观题表类型
	@Column(name = "stype", nullable = false, length = 255)
	private int stype;
	//主观题表备注
	@Column(name = "snote", nullable = true, length = 255)
	private String snote;
	//主观题表所对应问卷
	@Column(name = "squestionnaire", nullable = false, length = 50)
	private String squestionnaire;
	
}
