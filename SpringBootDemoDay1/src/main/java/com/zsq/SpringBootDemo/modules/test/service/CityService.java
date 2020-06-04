package com.zsq.SpringBootDemo.modules.test.service;

import java.util.List;

import com.zsq.SpringBootDemo.modules.test.entity.City;

public interface CityService {

	List<City> getCitiesByCountryId(int countryId);
}
