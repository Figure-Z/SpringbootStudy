package com.zsq.SpringBootDemo.modules.account.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zsq.SpringBootDemo.modules.account.entity.User;
import com.zsq.SpringBootDemo.modules.commom.vo.Result;
import com.zsq.SpringBootDemo.modules.commom.vo.SearchVo;

public interface UserService {

	List<User> selectUser();
	
	User getUserByUserName(String userName);
	
	PageInfo<User> getUsersBySearchVo(SearchVo searchVo);
	
	Result<User> login(User user);
	
	Result<User> insertUser(User user);
	
	Result<User> updateUserMessage(User user);
	
	Result<Object> deleteUser(int userId);
	
}
