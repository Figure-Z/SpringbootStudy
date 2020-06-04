package com.zsq.SpringBootDemo.modules.test.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zsq.SpringBootDemo.modules.test.dao.CityDao;
import com.zsq.SpringBootDemo.modules.test.entity.City;
import com.zsq.SpringBootDemo.modules.test.service.CityService;

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityDao CityDao;

	@Override
	public List<City> getCitiesByCountryId(int countryId) {

		return Optional.ofNullable(CityDao.getCitiesByCountryId(countryId)).orElse(Collections.emptyList());
	}

}
