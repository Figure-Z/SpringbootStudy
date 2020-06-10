package com.zsq.SpringBootDemo.modules.account.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<Role> selctRoles(){
		return roleDao.selctRoles();
	}

	@Override
	public Result<Role> insertRole(Role role) {
		roleDao.insertRole(role);
		return new Result<Role>(ResultStatus.SUCCESS.status,"Insert Success",role);
	}

	@Override
	public Result<Role> updateRole(Role role) {
		roleDao.updateRole(role);
		return new Result<Role>(ResultStatus.SUCCESS.status,"Update Success",role);
	}

	@Override
	public Result<Object> deleteRole(int roleId) {
		roleDao.deleteRole(roleId);
		return new Result<Object>(ResultStatus.SUCCESS.status,"Delete Success");
	}
}
