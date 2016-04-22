package com.interview.mysqlDb;

import com.interview.pojo.Country;

public interface CountryServiceDAO {
	
	public String getCountry(int contryCode);
	
	public String addCountry(String country);
	
	public int getContrycodeByName(String countryName);
	
	public Country modifyCountry(Country oldCountryName, Country newCountryName);
	
	public String deleteCountryByName(String countryName);
	
	

}
