package com.interview.mysql;

import java.util.HashMap;

import com.interview.pojo.Country;
import com.interview.util.MysqlOperations;

public interface CountryService {
	
	public String getCountryNameByCountryCode(int countryCode);
	
	public String addCountry(Country country);
	
	public Country getCountryCodeByCountryName(String countryName);
	
	public Country modifyCountry(String oldCountryName , String newCountryName);
	
	public String activateDeactivateCountryByCountryName(MysqlOperations mysqlOperations , String countryName);
	
	public HashMap<Integer,Country> getCountry();
	
	public boolean isCountryExist(Country country);

}
