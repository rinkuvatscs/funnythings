package com.interview.mysqlDb;

import java.util.HashMap;

import com.interview.pojo.Country;

public interface CountryServiceDAO {
	
	public String getCountryName(int contryCode);
	
	public String addCountry(String country);
	
	public int getContrycodeByName(String countryName);
	
	public Country modifyCountry(String oldCountryName, String newCountryName);
	
	public String deleteCountryByName(String countryName);
	
	public HashMap<Integer,String> getCountry();
	

}
