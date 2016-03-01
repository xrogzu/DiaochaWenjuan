package com.cn.utils;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component("randomString")
public class RandomString {

	public static void main(String[] args) {
		new RandomString().getRandomString();
	}
	
	int length = 16;
	
	public String getRandomString() { //length表示生成字符串的长度  
		
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";    
	    
	    Random random = new Random();   
	    
	    StringBuffer sb = new StringBuffer();  
	    
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     

	    return sb.toString();     
	 }
}
