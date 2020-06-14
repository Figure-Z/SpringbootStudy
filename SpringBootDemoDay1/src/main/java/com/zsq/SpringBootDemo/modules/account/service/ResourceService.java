package com.zsq.SpringBootDemo.modules.account.service;

import java.util.List;

import com.zsq.SpringBootDemo.modules.account.entity.Resource;
import com.zsq.SpringBootDemo.modules.commom.vo.Result;

public interface ResourceService {

	List<Resource> selectResources();
	
	Result<Resource> insertResource(Resource resource);
	
	Result<Resource> updateResource(Resource resource);
	
	Result<Object> deleteResource(Resource resource);
}
