package com.zsq.SpringBootDemo.modules.account.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zsq.SpringBootDemo.modules.account.entity.Role;
import com.zsq.SpringBootDemo.modules.commom.vo.Result;
import com.zsq.SpringBootDemo.modules.commom.vo.SearchVo;

public interface RoleService {

	List<Role> getRoles();
	
	Result<Role> editRole(Role role);
	
	Result<Object> deleteRole(int roleId);
	
	PageInfo<Role> getRoles(SearchVo searchVo);
	
	List<Role> getRolesByUserId(int userId);
	
	List<Role> getRolesByResourceId(int resourceId);
	
	Role getRoleById(int roleId);
	
	
}
