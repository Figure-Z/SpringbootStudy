package com.zsq.SpringBootDemo.modules.account.Service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zsq.SpringBootDemo.modules.account.Service.UserService;
import com.zsq.SpringBootDemo.modules.account.dao.UserDao;
import com.zsq.SpringBootDemo.modules.account.entity.User;
import com.zsq.SpringBootDemo.modules.commom.vo.Result;
import com.zsq.SpringBootDemo.modules.commom.vo.Result.ResultStatus;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public List<User> selectUser(){
		return  userDao.selectUser();
	}

	@Override
	public Result<User> insertUser(User user) {
		user.setCreateDate(new Date());
		userDao.insertUser(user);
		return new Result<User>(ResultStatus.SUCCESS.status,"Insert Success", user);
	}

	@Override
	public Result<User> updateUserMessage(User user) {
		userDao.updateUserMessage(user);
		return new Result<User>(ResultStatus.SUCCESS.status,"Update Success",user);
	}

	@Override
	public Result<Object> deleteUser(int userId) {
		userDao.deleteUser(userId);
		return new Result<Object>(ResultStatus.SUCCESS.status,"Delete Success");
	}
}
