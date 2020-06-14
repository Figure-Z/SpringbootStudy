package com.zsq.SpringBootDemo.modules.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zsq.SpringBootDemo.modules.account.entity.Resource;
import com.zsq.SpringBootDemo.modules.account.service.ResourceService;
import com.zsq.SpringBootDemo.modules.commom.vo.Result;

@RestController
@RequestMapping("/account")
public class ResourceController {

	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping("/resources")
	public List<Resource> selectResources(){
		return resourceService.selectResources();
	}
	
	@PostMapping(value = "/resource", consumes = "application/json")
	public Result<Resource> insertResource(@RequestBody Resource resource){
		return resourceService.insertResource(resource);
	}
	
	@PutMapping(value = "/resource",consumes = "application/json")
	public Result<Resource> updateResource(@RequestBody Resource resource){
		return resourceService.updateResource(resource);
	}
	
	@DeleteMapping(value = "/resource",consumes = "application/json")
	public Result<Object> deleteResource(Resource resource){
		return resourceService.deleteResource(resource);
	}
}
