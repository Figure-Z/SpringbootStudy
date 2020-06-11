package com.zsq.SpringBootDemo.modules.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zsq.SpringBootDemo.modules.account.entity.Role;
import com.zsq.SpringBootDemo.modules.account.service.RoleService;
import com.zsq.SpringBootDemo.modules.commom.vo.Result;

@RestController
@RequestMapping("/account")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/role")
	public List<Role> selectRoles(){
		return roleService.selctRoles();
	}
	
	@PostMapping(value = "/role",consumes = "application/json")
	public Result<Role> insertRole(@RequestBody Role role){
		return roleService.insertRole(role);
	}
	
	@PutMapping(value = "/role",consumes = "application/json")
	Result<Role> updateRole(@RequestBody Role role){
		return roleService.updateRole(role);
	}
	
	@DeleteMapping("/role")
	Result<Object> deleteRole(@RequestBody int roleId){
		return roleService.deleteRole(roleId);
	}
}
