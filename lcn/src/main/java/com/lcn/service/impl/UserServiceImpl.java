package com.lcn.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lcn.dao.UserDao;
import com.lcn.entity.User;
import com.lcn.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Resource
	UserDao userDao;

	public Long insertUser(User user) {
		userDao.insertSelective(user);
		return (Long) user.getPrimaryKey();
	}

	@Override
	public void updateUser(User user) {
		userDao.updateByPrimaryKeySelective(user);
	}

	@Override
	public List<User> search(User user) {
		return userDao.search(user);
	}

	@Override
	public User selectByPrimaryKey(Long userId) {
		return userDao.selectByPrimaryKey(userId);
	}
	
}
