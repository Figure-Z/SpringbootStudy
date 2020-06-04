package com.zsq.SpringBootDemo.modules.test.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zsq.SpringBootDemo.modules.test.entity.City;

@Mapper
public interface CityDao {

	List<City> getCitiesByCountryId(int countryId);
}
