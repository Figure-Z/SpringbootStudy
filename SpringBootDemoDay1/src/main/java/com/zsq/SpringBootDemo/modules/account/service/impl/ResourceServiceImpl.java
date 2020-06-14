package com.zsq.SpringBootDemo.modules.account.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zsq.SpringBootDemo.modules.account.dao.ResourceDao;
import com.zsq.SpringBootDemo.modules.account.entity.Resource;
import com.zsq.SpringBootDemo.modules.account.service.ResourceService;
import com.zsq.SpringBootDemo.modules.commom.vo.Result;
import com.zsq.SpringBootDemo.modules.commom.vo.Result.ResultStatus;

@Service
public class ResourceServiceImpl implements ResourceService{

	@Autowired
	private ResourceDao resourceDao;
	
	@Override
	public List<Resource> selectResources() {
		return resourceDao.selectResources();
	}

	@Override
	public Result<Resource> insertResource(Resource resource) {
		resourceDao.insertResource(resource);
		return new Result<Resource>(ResultStatus.SUCCESS.status,"Insert Success",resource);
	}

	@Override
	public Result<Resource> updateResource(Resource resource) {
		resourceDao.updateResource(resource);
		return new Result<Resource>(ResultStatus.SUCCESS.status,"Update Success",resource);
	}

	@Override
	public Result<Object> deleteResource(Resource resource) {
		resourceDao.deleteResource(resource);
	 return new Result<Object>(ResultStatus.SUCCESS.status,"Delete Success");
	}

	
}
