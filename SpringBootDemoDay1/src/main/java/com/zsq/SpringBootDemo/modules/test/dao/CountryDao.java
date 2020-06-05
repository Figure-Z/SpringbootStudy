package com.zsq.SpringBootDemo.modules.test.dao;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.zsq.SpringBootDemo.modules.test.entity.Country;
/**
 * 组合查询 
 * @author ZengShiQi
 *
 */

@Mapper
public interface CountryDao {

	/**
	 *   根据countryId查询country对象，该对象里包含着另一个表中的数据
	 * 
	 * @Result 封装结果集 column:需要根据的参数, property：映射到的地方 javaType：返回的类型 many = @Many() :
	 *         用来把column的值拿到另一个查询语句中查询
	 * @param countryId
	 * @return
	 */
	@Select("select * from m_country where country_id = #{countryId}")
	@Results(id = "countryResult" ,value = { 
			@Result(column = "country_id", property = "countryId"),   //先将得到的参数映射给本身，在映射给别的
			@Result(column = "country_id", property = "cities", javaType = List.class, many = @Many(select = "com.zsq.SpringBootDemo.modules.test.dao.CityDao.getCitiesByCountryId2")) })
	Country getCountryByCountryId(int countryId);
	
	
	/**
	 * 根据countryName查询country对象
	 * @param countryName
	 * @return
	 */
	@Select("select * from m_country where country_name = #{countryName}")
	@ResultMap("countryResult")  //通过@Results的id属性来进行引用结果集
	Country getCountryByCountryName(String countryName);
	
	
	
	
	
	
	
}
