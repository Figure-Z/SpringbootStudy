package com.zsq.SpringBootDemo.modules.account.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zsq.SpringBootDemo.modules.account.dao.RoleDao;
import com.zsq.SpringBootDemo.modules.account.entity.Role;
import com.zsq.SpringBootDemo.modules.account.service.RoleService;
import com.zsq.SpringBootDemo.modules.commom.vo.Result;
import com.zsq.SpringBootDemo.modules.commom.vo.Result.ResultStatus;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleDao roleDao;
	
	@Override
	public List<Role> getRoles(){
		return Optional.ofNullable(roleDao.getRoles()).orElse(Collections.emptyList());
	}

	@Override
	@Transactional
	public Result<Role> insertRole(Role role) {
		roleDao.insertRole(role);
		return new Result<Role>(ResultStatus.SUCCESS.status,"Insert Success",role);
	}

	@Override
	@Transactional
	public Result<Role> updateRole(Role role) {
		roleDao.updateRole(role);
		return new Result<Role>(ResultStatus.SUCCESS.status,"Update Success",role);
	}

	@Override
	@Transactional
	public Result<Object> deleteRole(int roleId) {
		roleDao.deleteRole(roleId);
		return new Result<Object>(ResultStatus.SUCCESS.status,"Delete Success");
	}
}
