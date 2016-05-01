package com.interview.mysql;

import java.util.HashMap;

import com.interview.pojo.Country;

public interface CountryService {
	
	public String getCountryNameByCountryCode(int countryCode);
	
	public String addCountry(Country country);
	
	public int getCountryCodeByCountryName(String countryName);
	
	public Country modifyCountry(String oldCountryName , String newCountryName);
	
	public String deleteCountryByCountryName(String countryName);
	
	public HashMap<Integer,Country> getCountry();
	
	public boolean isCountryExist(String country);

}
