package com.zsq.SpringBootDemo.modules.test.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.zsq.SpringBootDemo.modules.commom.vo.SearchVo;
import com.zsq.SpringBootDemo.modules.test.entity.City;

@Mapper
public interface CityDao {

	List<City> getCitiesByCountryId(int countryId);

	
	@Select("select * from m_city where country_id = #{countryId}")
	List<City> getCitiesByCountryId2(int countryId);

	
	// sql语句的名字要对应参数，或对参数用@param("sql参数名")对应
	@Select("select * from m_city where city_name=#{cityName} and local_city_name=#{localCityName}")
	City getCityByName(String cityName, String localCityName);

	/**
	 * 多条件查询，查询分页数据
	 * @param searchVo
	 * @return
	 */
	@Select("<script>" + 
			"select * from m_city "
			+ "<where> "
			+ "<if test='keyWord != \"\" and keyWord != null'>"
			+ " and (city_name like '%${keyWord}%' or local_city_name like '%${keyWord}%') "
			+ "</if>"
			+ "</where>"
			+ "<choose>"
			+ "<when test='orderBy != \"\" and orderBy != null'>"
			+ " order by ${orderBy} ${sort}"
			+ "</when>"
			+ "<otherwise>"
			+ " order by city_id desc"
			+ "</otherwise>"
			+ "</choose>"
			+ "</script>")
	List<City> getCitiesBySearchVo(SearchVo searchVo);
	
}
