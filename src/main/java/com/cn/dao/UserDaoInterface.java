package com.cn.dao;

import java.io.Serializable;
import java.util.List;

import com.cn.domain.Users;

/**
 * @author Hang
 * @date 2016年2月23日下午3:40:42
 * @version
 */

public interface UserDaoInterface {
	
	public Serializable add(Users users);
	
	public List<Users> show();
	
}
