package com.interview.mysqlDb.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.interview.mysqlDb.CountryService;
import com.interview.mysqlDb.CountryServiceDAO;
import com.interview.pojo.Country;

@Component
public class CountryServiceImpl implements CountryService {

	
	@Autowired
	private CountryServiceDAO countryServiceDAO;
	/*
	public String getCountry(int contryCode) {
		String countryName = countryServiceDAO.getCountry(contryCode);
		return countryName;
	}*/

	@Override
	public String getCountry(int contryCode) {
		String countryName = countryServiceDAO.getCountry(contryCode);
		return countryName;
	}

	@Override
	public String addCountry(String country) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getContryCodeByName(String countryName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String modifyCountry(Country oldCountryName, Country newCountryName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteCountryByName(String countryName) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
