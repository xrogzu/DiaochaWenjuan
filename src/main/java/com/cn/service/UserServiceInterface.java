package com.cn.service;

import java.io.Serializable;
import java.util.List;

import com.cn.domain.Users;

public interface UserServiceInterface {
	//测试
	public void test();

	//
	public Serializable add(Users users);
	
	public List<Users> showUsers();
}
