package com.zsq.SpringBootDemo.modules.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zsq.SpringBootDemo.modules.account.entity.User;
import com.zsq.SpringBootDemo.modules.commom.vo.SearchVo;

@Mapper
public interface UserDao {

	//查询所有用户对象，连表查询
	@Select("select * from user where user_id = #{userId}")
	@Results(id = "userResult",value = {
			@Result(column = "user_id",property = "userId"),
			@Result(column = "user_id",property = "roles",
			javaType = List.class,
			many = @Many(select = "com.zsq.SpringBootDemo.modules.account.dao."
					+"RoleDao.getRolesByUserId"))
	})
	User getUserByUserId(int userId);
	
	@Select("select * from user where user_name = #{userName}")
	User getUserByUserName(String userName);
	
	@Select("<script>" + 
			"select * from user "
			+ "<where> "
			+ "<if test='keyWord != \"\" and keyWord != null'>"
			+ " and (user_name like '%${keyWord}%') "
			+ "</if>"
			+ "</where>"
			+ "<choose>"
			+ "<when test='orderBy != \"\" and orderBy != null'>"
			+ " order by ${orderBy} ${sort}"
			+ "</when>"
			+ "<otherwise>"
			+ " order by user_id desc"
			+ "</otherwise>"
			+ "</choose>"
			+ "</script>")
	List<User> getUsersBySearchVo(SearchVo searchVo);
	
	
	//插入用户
	@Insert("insert into user (user_name, password, create_date) "
			+ "values (#{userName}, #{password}, #{createDate})")
	@Options(useGeneratedKeys = true, keyColumn = "user_id", keyProperty = "userId")
	void insertUser(User user);
	
	//更新数据
	@Update("update user set user_name = #{userName},user_img = #{userImg} where user_id = #{userId}")
	void updateUserMessage(User user);
	
	//删除用户
	@Delete("delete from user where user_id = #{userId}")
	void deleteUser(int userId);
	
}
