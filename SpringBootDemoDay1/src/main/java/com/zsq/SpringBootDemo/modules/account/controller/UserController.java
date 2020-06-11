package com.zsq.SpringBootDemo.modules.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zsq.SpringBootDemo.modules.account.entity.User;
import com.zsq.SpringBootDemo.modules.account.service.UserService;
import com.zsq.SpringBootDemo.modules.commom.vo.Result;

@RestController
@RequestMapping("/account")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/user")
	public List<User> selectUser(){
		return userService.selectUser();
	}
	
	@PostMapping(value = "/user",consumes = "application/json")
	public Result<User> insertUser(@RequestBody User user) {
		return userService.insertUser(user);
	}
	
	@PostMapping(value = "/login",consumes = "application/json")
	public Result<User> login(@RequestBody User user){
		return userService.login(user);
	}
	
	@PutMapping(value = "/user",consumes = "application/json")
	public Result<User> updateUserMessage(@RequestBody User user){
		return userService.updateUserMessage(user);
	}
	
	@DeleteMapping("/user")
	public Result<Object> deleteUser(@RequestBody int userId){
		return userService.deleteUser(userId);
	}
}
