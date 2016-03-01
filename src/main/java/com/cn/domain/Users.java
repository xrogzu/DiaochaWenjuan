package com.cn.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * 用户表
 * @author Hang
 * @date 2016年2月29日上午9:39:41
 * @version
 */
@Entity
@Table(name = "users")
public class Users {
	//用户ID,主键自增,对应数据库主键uid、非空、长度50、唯一
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "uid", nullable = false, length = 50, unique = true)
	private int uid;
	
	//用户名
	@Column(name = "uname", nullable = false, length = 255)
	private String uname;
	
	//用户密码
	@Column(name = "upassword", nullable = false, length = 255)
	private String upassword;
	
	//用户类型(普通用户、VIP、管理员)
	@Column(name = "utype", nullable = false, length = 5)
	private int utype;
	
	//用户年龄
	@Column(name = "uage", nullable = true, length = 10)
	private int uage;
	
	//用户性别
	@Column(name = "usex", nullable = true, length = 5)
	private int usex;
	
	//用户地址
	@Column(name = "uadd", nullable = true, length = 255)
	private String uadd;
	
	//用户E-mail
	@Column(name = "uemail", nullable = true, length = 50)
	private String uemail;
	
	//用户昵称
	@Column(name = "ulogin", nullable = true, length = 50)
	private String ulogin;
	
	//用户上一次登录时间,注解不会自动创建时间,而是在取出是以Y-M-D H-M-S的形式
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ulastlogintime", nullable = false, length = 50)
	private Date ulastlogintime;
	
	//用户上一次使用所在地点
	@Column(name = "ulastloginadd", nullable = false, length = 50)
	private String ulastloginadd;
	
	//用户注册时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "uregistrationtime", nullable = false, length = 50)
	private Date uregistrationtime;
	
	//用户积分
	@Column(name = "upoint", nullable = false, length = 50)
	private int upoint;
	
	//用户头像
	@Column(name = "uphoto", nullable = true, length = 50)
	private String uphoto;
	
	//用户所在组
	@JoinColumn(name = "groop", referencedColumnName = "gid")
	@ManyToOne
	private Groop gid;
	
	//用户备注
	@Column(name = "unote", nullable = true, length = 50)
	private String unote;
	
	//验证码
	//private String code;
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getUpassword() {
		return upassword;
	}
	public void setUpassword(String upassword) {
		this.upassword = upassword;
	}
	public int getUtype() {
		return utype;
	}
	public void setUtype(int utype) {
		this.utype = utype;
	}
	public int getUage() {
		return uage;
	}
	public void setUage(int uage) {
		this.uage = uage;
	}
	public int getUsex() {
		return usex;
	}
	public void setUsex(int usex) {
		this.usex = usex;
	}
	public String getUadd() {
		return uadd;
	}
	public void setUadd(String uadd) {
		this.uadd = uadd;
	}
	public String getUemail() {
		return uemail;
	}
	public void setUemail(String uemail) {
		this.uemail = uemail;
	}
	public String getUlogin() {
		return ulogin;
	}
	public void setUlogin(String ulogin) {
		this.ulogin = ulogin;
	}
	public Date getUlastlogintime() {
		return ulastlogintime;
	}
	public void setUlastlogintime(Date ulastlogintime) {
		this.ulastlogintime = ulastlogintime;
	}
	public String getUlastloginadd() {
		return ulastloginadd;
	}
	public void setUlastloginadd(String ulastloginadd) {
		this.ulastloginadd = ulastloginadd;
	}
	public Date getUregistrationtime() {
		return uregistrationtime;
	}
	public void setUregistrationtime(Date uregistrationtime) {
		this.uregistrationtime = uregistrationtime;
	}
	public int getUpoint() {
		return upoint;
	}
	public void setUpoint(int upoint) {
		this.upoint = upoint;
	}
	public String getUphoto() {
		return uphoto;
	}
	public void setUphoto(String uphoto) {
		this.uphoto = uphoto;
	}
	public Groop getGid() {
		return gid;
	}
	public void setGid(Groop gid) {
		this.gid = gid;
	}
	public String getUnote() {
		return unote;
	}
	public void setUnote(String unote) {
		this.unote = unote;
	}
	
}
