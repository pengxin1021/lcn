package com.lcn.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lcn.BaseTest;
import com.lcn.entity.User;

public class UserMapperTest extends BaseTest{

	@Autowired
	UserDao userDao;
	
	@Test
	public void testUser(){
		User user = new User();
		user.setUserName("Test");
		userDao.insertSelective(user);
	}

}
