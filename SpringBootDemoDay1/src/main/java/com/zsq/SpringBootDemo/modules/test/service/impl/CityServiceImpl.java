package com.zsq.SpringBootDemo.modules.test.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsq.SpringBootDemo.modules.commom.vo.SearchVo;
import com.zsq.SpringBootDemo.modules.test.dao.CityDao;
import com.zsq.SpringBootDemo.modules.test.entity.City;
import com.zsq.SpringBootDemo.modules.test.service.CityService;

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityDao CityDao;

	@Override
	public List<City> getCitiesByCountryId(int countryId) {
		// 修改了方法，利用注解完成了
		return Optional.ofNullable(CityDao.getCitiesByCountryId2(countryId)).orElse(Collections.emptyList());
	}

	@Override
	public City getCityByName(String cityName, String localCityName) {
		return CityDao.getCityByName(cityName, localCityName);
	}

	@Override
	public PageInfo<City> getCitiesByPage(int currentPage, int pageSize, int countryId) {
		PageHelper.startPage(currentPage, pageSize);  //设置分页属性
		//将查询结果封装到PageInfo中
		return new PageInfo<City>(Optional.ofNullable(CityDao.getCitiesByCountryId2(countryId)).orElse(Collections.emptyList()));
	}

	/**
	 *通过searchVo查询分页数据
	 */
	@Override
	public PageInfo<City> getCitiesBySearchVo(SearchVo searchVo) {
		searchVo.initSearchVo(); //如果没有值则给与默认值
		PageHelper.startPage(searchVo.getCurrentPage(), searchVo.getPageSize());
		return new PageInfo<City>(Optional.ofNullable(CityDao.getCitiesBySearchVo(searchVo)).orElse(Collections.emptyList()));
	}

}
