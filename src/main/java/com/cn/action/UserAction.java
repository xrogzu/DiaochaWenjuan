package com.cn.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.service.UserServiceInterface;
/**
 * 
 * @author Hang
 * @date 2016年2月24日下午2:43:51
 * @version
 */

@ParentPackage(value = "basePackage")  //指定package的名字
@Namespace(value = "/")
@Action(value = "userAction", results = {
		@Result(name = "success", location = "/index.jsp")
})
public class UserAction {
	
	private String name;
	private String password;
	
	private UserServiceInterface userServiceInterface;
	
	public String login(){
		System.out.println("进入登录方法");
		System.out.println("进入登录方法");
		return "success";
	}
	
	public void test(){
		System.out.println("Struts2成功");
		userServiceInterface.test();
		/*ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
		UserServiceImpl userServiceImpl = (UserServiceImpl) ac.getBean("userService");
		userServiceImpl.test();*/
	}
	
	public UserServiceInterface getUserServiceInterface() {
		return userServiceInterface;
	}

	@Autowired
	public void setUserServiceInterface(UserServiceInterface userServiceInterface) {
		this.userServiceInterface = userServiceInterface;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
