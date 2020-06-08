package com.zsq.SpringBootDemo.modules.test.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsq.SpringBootDemo.modules.commom.vo.Result;
import com.zsq.SpringBootDemo.modules.commom.vo.Result.ResultStatus;
import com.zsq.SpringBootDemo.modules.commom.vo.SearchVo;
import com.zsq.SpringBootDemo.modules.test.dao.CityDao;
import com.zsq.SpringBootDemo.modules.test.entity.City;
import com.zsq.SpringBootDemo.modules.test.service.CityService;
import com.zsq.SpringBootDemo.utils.RedisUtils;

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityDao cityDao;

	@Autowired   //在RedisUtils中将其注册为了组建，并注入了
	private RedisUtils redisUtils;
	
	@Override
	public List<City> getCitiesByCountryId(int countryId) {
		// 修改了方法，利用注解完成了
		return Optional.ofNullable(cityDao.getCitiesByCountryId2(countryId)).orElse(Collections.emptyList());
	}

	@Override
	public City getCityByName(String cityName, String localCityName) {
		return cityDao.getCityByName(cityName, localCityName);
	}

	@Override
	public PageInfo<City> getCitiesByPage(int currentPage, int pageSize, int countryId) {
		PageHelper.startPage(currentPage, pageSize);  //设置分页属性
		//将查询结果封装到PageInfo中
		return new PageInfo<City>(Optional.ofNullable(cityDao.getCitiesByCountryId2(countryId)).orElse(Collections.emptyList()));
	}

	/**
	 *通过searchVo查询分页数据
	 */
	@Override
	public PageInfo<City> getCitiesBySearchVo(SearchVo searchVo) {
		searchVo.initSearchVo(); //如果没有值则给与默认值
		PageHelper.startPage(searchVo.getCurrentPage(), searchVo.getPageSize());
		return new PageInfo<City>(Optional.ofNullable(cityDao.getCitiesBySearchVo(searchVo)).orElse(Collections.emptyList()));
	}

	/**
	 * 插入数据
	 */
	@Override
	public Result<City> insertCity(City city){
		city.setDateCreated(new Date());
		cityDao.insertCity(city);
		return new Result<City>(ResultStatus.SUCCESS.status,"Insert Success", city);
	}

	/**
	 * @Transactional 注解来配置事务，出现异常自动回滚
	 * noRollbackFor:遇到某种异常不进行回滚
	 * propagation: 配置传播方式
	 */
	@Override
	@Transactional
	public Result<City> updateCity(City city) {
		cityDao.updateCity(city);
		//int a = 5/0;
		return new Result<City>(ResultStatus.SUCCESS.status,"Update Success", city);
	}

	@Override
	public Result<Object> deleteCity(int cityId) {
		cityDao.deleteCity(cityId);
		return new Result<Object>(ResultStatus.SUCCESS.status,"Delete Success");
	}

	/**
	 * 将数据从数据库中查出放入redis在取出来
	 */
	@Override
	public Object migrateCitiesByCountryId(int countryId) {
		List<City> cities = getCitiesByCountryId(countryId);
		redisUtils.set("cities", cities);
		return redisUtils.get("cities");
	}

}
