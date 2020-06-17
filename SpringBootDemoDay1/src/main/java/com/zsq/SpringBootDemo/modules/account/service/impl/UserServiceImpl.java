package com.zsq.SpringBootDemo.modules.account.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsq.SpringBootDemo.config.ResourceConfigBean;
import com.zsq.SpringBootDemo.modules.account.dao.UserDao;
import com.zsq.SpringBootDemo.modules.account.dao.UserRoleDao;
import com.zsq.SpringBootDemo.modules.account.entity.Role;
import com.zsq.SpringBootDemo.modules.account.entity.User;
import com.zsq.SpringBootDemo.modules.account.service.UserService;
import com.zsq.SpringBootDemo.modules.commom.vo.Result;
import com.zsq.SpringBootDemo.modules.commom.vo.Result.ResultStatus;
import com.zsq.SpringBootDemo.modules.commom.vo.SearchVo;
import com.zsq.SpringBootDemo.utils.FileUtil;
import com.zsq.SpringBootDemo.utils.MD5Util;

@Service
public class UserServiceImpl implements UserService{

	private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private ResourceConfigBean resourceConfigBean;
	
	@Override
	public User getUserByUserId(int userId) {
		return userDao.getUserByUserId(userId);
	}

	/**
	 * 插入用户数据
	 */
//	@Override
//	@Transactional
//	public Result<User> insertUser(User user) {
//		User userTemp = getUserByUserName(user.getUserName());
//		if(userTemp != null) {
//			return new Result<User>(ResultStatus.FAILD.status,"User name is repeat");
//		}
//		
//		user.setCreateDate(new Date());
//		user.setPassword(MD5Util.getMD5(user.getPassword()));
//		userDao.insertUser(user);
//		//删除该用户的所有角色
//		userRoleDao.deleteRolesByUserId(user.getUserId());
//		List<Role> roles = user.getRoles();
//		if(roles!=null && roles.size()>0) {
//			for (Role role : roles) {
//				userRoleDao.insertUserRole(user.getUserId(), role.getRoleId());
//			}
//		}
//		
//		
//		return new Result<User>(ResultStatus.SUCCESS.status,"Insert Success", user);
//	}
//
//	/**
//	 * 更新用户
//	 */
//	@Override
//	@Transactional
//	public Result<User> updateUserMessage(User user) {
//		//判断要更新的user是否存在
//		User userTemp = getUserByUserName(user.getUserName());
//		if(userTemp != null && userTemp.getUserId() != user.getUserId()) {
//			return new Result<User>(ResultStatus.FAILD.status,"User name is repeat");
//		}
//		userDao.updateUserMessage(user);
//		
//		userRoleDao.deleteRolesByUserId(user.getUserId());
//		//获取到check选择的role并添加到表中
//		List<Role> roles = user.getRoles();
//		if(roles!=null && roles.size()>0) {
//			for (Role role : roles) {
//				userRoleDao.insertUserRole(user.getUserId(), role.getRoleId());
//			}
//		}
//		return new Result<User>(ResultStatus.SUCCESS.status,"Update Success",user);
//	}

	@Override
	@Transactional
	public Result<Object> deleteUser(int userId) {
		userDao.deleteUser(userId);
		userRoleDao.deleteRolesByUserId(userId);//消除角色
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

	/**
	 * 更新和插入的共和
	 */
	@Override
	@Transactional
	public Result<User> editUser(User user) {
		//判断要更新的user是否存在
				User userTemp = getUserByUserName(user.getUserName());
				if(userTemp != null && userTemp.getUserId() != user.getUserId()) {
					return new Result<User>(ResultStatus.FAILD.status,"User name is repeat");
				}
				
				if(user.getUserId()>0) {
					userDao.updateUserMessage(user);
					userRoleDao.deleteRolesByUserId(user.getUserId());
				}else {
					user.setCreateDate(new Date());
					user.setPassword(MD5Util.getMD5(user.getPassword()));
					userDao.insertUser(user);
				}
				//获取到check选择的role并添加到表中
				List<Role> roles = user.getRoles();
				if(roles!=null && roles.size()>0) {
					for (Role role : roles) {
						userRoleDao.insertUserRole(user.getUserId(), role.getRoleId());
					}
				}
				return new Result<User>(ResultStatus.SUCCESS.status,"Edit Success",user);
	}

	/**
	 * 上传图片
	 */
	@Override
	public Result<String> uploadUserImage(MultipartFile userImage) {
		//判断上传是否为空
		if(userImage.isEmpty()) {
			return new Result<>(ResultStatus.FAILD.status,"Upload img is empty");
		}
		//判断上传的是否为图片
		if (!FileUtil.isImage(userImage)) {
			return new Result<>(ResultStatus.FAILD.status, "File is not a image.");
		}
		//获取文件名称
		String originalFilename = userImage.getOriginalFilename();
		//获取相对路径
		String relatedPath = resourceConfigBean.getResourcePath() + originalFilename;
		String destPath = String.format("%s%s", resourceConfigBean.getLocalPathForWindows(), originalFilename);
		try {
			File destFile = new File(destPath);
			userImage.transferTo(destFile);//上传
			
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			LOGGER.debug(e.getMessage());
			return new Result<>(ResultStatus.FAILD.status, "File upload error.");
		}
		
		return new Result<>(ResultStatus.SUCCESS.status, "File upload success.", relatedPath);
	}

	/**
	 * 用于保存头像资源地址
	 */
	@Override
	@Transactional
	public Result<User> updataUserProfile(User user) {
		User userTemp = getUserByUserName(user.getUserName());
		if(userTemp != null && userTemp.getUserId() != user.getUserId()) {
			return new Result<User>(ResultStatus.FAILD.status,"User name is repeat");
		}
		
		userDao.updateUserMessage(user);
		
		return new Result<User>(ResultStatus.SUCCESS.status,"Edit Success",user);
	}
}
