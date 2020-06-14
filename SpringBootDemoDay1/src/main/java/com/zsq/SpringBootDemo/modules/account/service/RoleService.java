package com.zsq.SpringBootDemo.modules.account.service;

import java.util.List;

import com.zsq.SpringBootDemo.modules.account.entity.Role;
import com.zsq.SpringBootDemo.modules.commom.vo.Result;

public interface RoleService {

	List<Role> getRoles();
	
	Result<Role> insertRole(Role role);
	
	Result<Role> updateRole(Role role);
	
	Result<Object> deleteRole(int roleId);
}
