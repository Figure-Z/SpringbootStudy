package com.zsq.SpringBootDemo.modules.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zsq.SpringBootDemo.modules.account.entity.Role;
import com.zsq.SpringBootDemo.modules.commom.vo.Result;

@Mapper
public interface RoleDao {

	@Select("select * from role")
	List<Role> getRoles();
	
	@Insert("insert into role (role_name) values (#{roleName})")
	@Options(useGeneratedKeys = true,keyColumn = "role_id",keyProperty = "roleId")
	Result<Role> insertRole(Role role);
	
	@Update("update role set role_name = #{roleName} where role_id = #{roleId}")
	Result<Role> updateRole(Role role);
	
	@Delete("delete from role where role_id = #{roleId}")
	Result<Object> deleteRole(int roleId);
}
