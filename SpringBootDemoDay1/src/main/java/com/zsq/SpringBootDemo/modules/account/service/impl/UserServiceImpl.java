package com.zsq.SpringBootDemo.modules.account.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsq.SpringBootDemo.modules.account.dao.UserDao;
import com.zsq.SpringBootDemo.modules.account.dao.UserRoleDao;
import com.zsq.SpringBootDemo.modules.account.entity.Role;
import com.zsq.SpringBootDemo.modules.account.entity.User;
import com.zsq.SpringBootDemo.modules.account.service.UserService;
import com.zsq.SpringBootDemo.modules.commom.vo.Result;
import com.zsq.SpringBootDemo.modules.commom.vo.Result.ResultStatus;
import com.zsq.SpringBootDemo.modules.commom.vo.SearchVo;
import com.zsq.SpringBootDemo.utils.MD5Util;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	@Autowired
	private UserRoleDao userRoleDao;
	
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
		//删除该用户的所有角色
		userRoleDao.deleteRolesByUserId(user.getUserId());
		List<Role> roles = user.getRoles();
		if(roles!=null && roles.size()>0) {
			for (Role role : roles) {
				userRoleDao.insertUserRole(user.getUserId(), role.getRoleId());
			}
		}
		
		
		return new Result<User>(ResultStatus.SUCCESS.status,"Insert Success", user);
	}

	@Override
	@Transactional
	public Result<User> updateUserMessage(User user) {
		userDao.updateUserMessage(user);
		return new Result<User>(ResultStatus.SUCCESS.status,"Update Success",user);
	}

	@Override
	@Transactional
	public Result<Object> deleteUser(int userId) {
		userDao.deleteUser(userId);
		return new Result<Object>(ResultStatus.SUCCESS.status,"Delete Success");
	}

	@Override
	public User getUserByUserName(String userName) {
		return userDao.getUserByUserName(userName);
	}

	@Override
	public Result<User> login(User user) {
		User userTemp = userDao.getUserByUserName(user.getUserName());
		if(userTemp == null || userTemp.getPassword().equals(MD5Util.getMD5(user.getPassword()))){
			return new Result<User>(ResultStatus.FAILD.status,"username or passwoed wrone");
		}
		return new Result<User>(ResultStatus.SUCCESS.status,"login Success",user);
	}

	@Override
	public PageInfo<User> getUsersBySearchVo(SearchVo searchVo) {
		searchVo.initSearchVo();
		PageHelper.startPage(searchVo.getCurrentPage(), searchVo.getPageSize());
		return new PageInfo<User>(
				Optional.ofNullable(userDao.getUsersBySearchVo(searchVo))
				.orElse(Collections.emptyList()));
	}

}
