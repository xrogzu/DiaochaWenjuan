package com.cn.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cn.domain.Users;
import com.cn.service.UserServiceInterface;

public class TestService {
	
	@Test
	public void test(){
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{"classpath:spring.xml","classpath:spring-hibernate.xml"});
		UserServiceInterface usi = (UserServiceInterface) ac.getBean("userService");
		Users users = new Users();
		users.setUname("ccc");
		users.setUpassword("123456");
		usi.add(users);
	}
	
	@Test
	public void testShow(){
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{"classpath:spring.xml","classpath:spring-hibernate.xml"});
		UserServiceInterface usi = (UserServiceInterface) ac.getBean("userService");
		List<Users> users = usi.showUsers();
		for(Users user : users){
			System.out.println(user.getUid()+user.getUname()+user.getUpassword());
		}
	}
	
}
