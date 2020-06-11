package com.zsq.SpringBootDemo.modules.account.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zsq.SpringBootDemo.modules.account.dao.UserDao;
import com.zsq.SpringBootDemo.modules.account.entity.User;
import com.zsq.SpringBootDemo.modules.account.service.UserService;
import com.zsq.SpringBootDemo.modules.commom.vo.Result;
import com.zsq.SpringBootDemo.modules.commom.vo.Result.ResultStatus;
import com.zsq.SpringBootDemo.utils.MD5Util;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public List<User> selectUser(){
		return  userDao.selectUser();
	}

	@Override
	@Transactional
	public Result<User> insertUser(User user) {
		User userTemp = getUserByUserName(user.getUserName());
		if(userTemp != null) {
			return new Result<User>(ResultStatus.FAILD.status,"User name is repeat");
		}
		
		user.setCreateDate(new Date());
		user.setPassword(MD5Util.getMD5(user.getPassword()));
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

	@Override
	public User getUserByUserName(String userName) {
		return userDao.getUserByUserName(userName);
	}

}
