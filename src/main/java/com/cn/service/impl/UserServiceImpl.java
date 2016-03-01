package com.cn.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.dao.UserDaoInterface;
import com.cn.domain.Users;
import com.cn.service.UserServiceInterface;
@Service(value="userService")
public class UserServiceImpl implements UserServiceInterface {

	private UserDaoInterface userDaoInterface;
	
	@Override
	public void test() {
		// TODO Auto-generated method stub
		System.out.print("测试成功");
	}
	
	@Override
	public Serializable add(Users users) {
		// TODO Auto-generated method stub
		return userDaoInterface.add(users);
	}
	
	public UserDaoInterface getUserDaoInterface() {
		return userDaoInterface;
	}
	
	@Autowired
	public void setUserDaoInterface(UserDaoInterface userDaoInterface) {
		this.userDaoInterface = userDaoInterface;
	}

	@Override
	public List<Users> showUsers() {
		// TODO Auto-generated method stub
		List<Users> users = userDaoInterface.show();
		return users;
	}

}
