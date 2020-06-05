package com.zsq.SpringBootDemo.modules.test.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zsq.SpringBootDemo.modules.commom.vo.SearchVo;
import com.zsq.SpringBootDemo.modules.test.entity.City;

public interface CityService {

	List<City> getCitiesByCountryId(int countryId);

	City getCityByName(String cityName, String localCityName);
	
	//定义分页方法，参数为当前页，每页展示数，查询所用的参数
	PageInfo<City> getCitiesByPage(int currentPage,int pageSize,int countryId);
	
	PageInfo<City> getCitiesBySearchVo(SearchVo searchVo);
}
