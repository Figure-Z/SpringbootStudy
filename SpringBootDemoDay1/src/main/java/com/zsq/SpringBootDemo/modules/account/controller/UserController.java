package com.zsq.SpringBootDemo.modules.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.pagehelper.PageInfo;
import com.zsq.SpringBootDemo.modules.account.entity.User;
import com.zsq.SpringBootDemo.modules.account.service.UserService;
import com.zsq.SpringBootDemo.modules.commom.vo.Result;
import com.zsq.SpringBootDemo.modules.commom.vo.SearchVo;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/user/{userId}")
	public User getUserByUserId(@PathVariable int userId){
		return userService.getUserByUserId(userId);
	}
	
	@PostMapping(value = "/user",consumes = "application/json")
	public Result<User> insertUser(@RequestBody User user) {
		return userService.editUser(user);
	}
	
	@PostMapping(value = "/login",consumes = "application/json")
	public Result<User> login(@RequestBody User user){
		return userService.login(user);
	}
	
	@PutMapping(value = "/user",consumes = "application/json")
	public Result<User> updateUserMessage(@RequestBody User user){
		return userService.editUser(user);
	}
	
	@DeleteMapping("/user/{userId}")
	public Result<Object> deleteUser(@PathVariable int userId){
		return userService.deleteUser(userId);
	}
	
	@PostMapping(value = "/users",consumes = "application/json")
	public PageInfo<User> getUsersBySearchVo(@RequestBody SearchVo searchVo){
		return userService.getUsersBySearchVo(searchVo);
	}
	
	@PostMapping(value = "/userImage",consumes = "multipart/form-data")
	public Result<String> uploadUserImage(@RequestParam MultipartFile userImage,RedirectAttributes redirectAttributes){
		return userService.uploadUserImage(userImage);
	}
	
	@PutMapping(value = "/profile" ,consumes ="application/json")
	public Result<User> updataUserProfile(@RequestBody User user){
		return userService.updataUserProfile(user);
	}
}
