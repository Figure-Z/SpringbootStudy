package com.zsq.SpringBootDemo.modules.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zsq.SpringBootDemo.modules.account.entity.Resource;
/**
 * 能访问的访问资源
 * @author ZengShiQi
 *
 */
@Mapper
public interface ResourceDao {

	@Select("select * from resource")
	List<Resource> selectResources();
	
	@Insert("insert into resource (resource_uri,resource_name,permission) values (#{resourceUri},#{resourceName},#{permission})")
	void insertResource(Resource resource);
	
	@Update("update resource set resource_uri=#{resourceUri},resource_name=#{resourceName},permission=#{permission} where resource_id=#{resourceId}")
	void updateResource(Resource resource);
	
	@Delete("delete from resource where resource_id=#{resourceId}")
	void deleteResource(Resource resource);
}
