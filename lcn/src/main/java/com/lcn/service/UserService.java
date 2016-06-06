package com.lcn.service;

import java.util.List;

import com.lcn.entity.User;

public interface UserService {
	
	public Long insertUser(User user);
	
	public User selectByPrimaryKey(Long userId);
	
	public void updateUser(User user);
	
	public List<User> search(User user);
}
