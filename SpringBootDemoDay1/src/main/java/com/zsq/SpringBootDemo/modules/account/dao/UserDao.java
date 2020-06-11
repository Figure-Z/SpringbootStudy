package com.zsq.SpringBootDemo.modules.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zsq.SpringBootDemo.modules.account.entity.User;

@Mapper
public interface UserDao {

	//查询所有用户对象
	@Select("select * from user")
	List<User> selectUser();
	
	@Select("select * from user where user_name = #{userName}")
	User getUserByUserName(String userName);
	
	//插入用户
	@Insert("insert into user (user_name, password, create_date) "
			+ "values (#{userName}, #{password}, #{createDate})")
	@Options(useGeneratedKeys = true, keyColumn = "user_id", keyProperty = "userId")
	void insertUser(User user);
	
	//更新数据
	@Update("update user set password = #{password} where user_id = #{userId}")
	void updateUserMessage(User user);
	
	//删除用户
	@Delete("delete from user where user_id = #{userId}")
	void deleteUser(int userId);
	
}
