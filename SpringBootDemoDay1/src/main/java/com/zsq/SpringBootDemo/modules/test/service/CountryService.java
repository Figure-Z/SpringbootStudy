package com.zsq.SpringBootDemo.modules.test.service;

import com.zsq.SpringBootDemo.modules.test.entity.Country;

public interface CountryService {

	Country getCountryByCountryId(int countryId);
	
	Country getCountryByCountryName(String countryName);
}
