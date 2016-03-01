package com.cn.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cn.dao.UserDaoInterface;
import com.cn.domain.Users;

@Repository(value = "userDao")
public class UserDaoImpl implements UserDaoInterface {

	private SessionFactory sessionFactory;
	
	@Override
	public Serializable add(Users users) {
		// TODO Auto-generated method stub
		return this.sessionFactory.getCurrentSession().save(users);
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Users> show() {
		// TODO Auto-generated method stub
		String sql = "from Users";
		Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
		List<Users> list = query.list();
		return list;
	}
}
