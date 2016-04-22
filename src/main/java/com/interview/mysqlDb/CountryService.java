package com.interview.mysqlDb;

import com.interview.pojo.Country;

public interface CountryService {
	
	public String getCountry(int contryCode);
	
	public String addCountry(String country);
	
	public int getContryCodeByName(String countryName);
	
	public String modifyCountry(Country oldCountryName, Country newCountryName);
	
	public String deleteCountryByName(String countryName);
	
}
