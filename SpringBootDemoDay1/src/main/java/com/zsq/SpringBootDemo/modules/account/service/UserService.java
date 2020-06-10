package com.zsq.SpringBootDemo.modules.account.service;

import java.util.List;

import com.zsq.SpringBootDemo.modules.account.entity.User;
import com.zsq.SpringBootDemo.modules.commom.vo.Result;

public interface UserService {

	List<User> selectUser();
	
	Result<User> insertUser(User user);
	
	Result<User> updateUserMessage(User user);
	
	Result<Object> deleteUser(int userId);
}
