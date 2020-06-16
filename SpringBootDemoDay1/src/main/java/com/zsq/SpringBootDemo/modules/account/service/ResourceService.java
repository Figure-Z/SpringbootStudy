package com.zsq.SpringBootDemo.modules.account.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zsq.SpringBootDemo.modules.account.entity.Resource;
import com.zsq.SpringBootDemo.modules.commom.vo.Result;
import com.zsq.SpringBootDemo.modules.commom.vo.SearchVo;

public interface ResourceService {

	Result<Resource> editResource(Resource resource);
	
	Result<Resource> deleteResource(int resourceId);
	
	PageInfo<Resource> getResources(SearchVo searchVo);
	
	List<Resource> getResourcesByRoleId(int roleId);
	
	Resource getResourceById(int resourceId);
}
