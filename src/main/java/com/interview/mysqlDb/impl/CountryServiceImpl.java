package com.interview.mysqlDb.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.interview.extractor.CountryExtractor;
import com.interview.extractor.CountryListExtrator;
import com.interview.mysqlDb.CountryService;
import com.interview.pojo.Country;


@Repository
public class CountryServiceImpl implements CountryService {

	/*country --country_id,country_name 

	country_states 
	country_id,state_id,state_name 


	create table country(country_id number, country_name varchar2(300)) 

	insert into country values(2,'PAKISTAN') 

	SELECT * FROM COUNTRY 

	CREATE TABLE COUNTRY_STATES(COUNTRY_ID NUMBER,STATE_ID NUMBER,STATE_NAME VARCHAR2(30)) 

	INSERT INTO COUNTRY_STATES VALUES(2,2,'YY') 

	SELECT B.COUNTRY_ID,B.COUNTRY_NAME ,A.STATE_NAME FROM COUNTRY_STATES A,COUNTRY B 
	WHERE A.COUNTRY_ID=B.COUNTRY_ID 
	AND B.COUNTRY_NAME='INDIA' */

	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public String getCountryNameByCountryCode(int cuontryCode) {
		String sql = "select * from country where id = ?";
		List<Integer> intList = new ArrayList<Integer>();
		intList.add(cuontryCode);
		Country country = (Country) jdbcTemplate.query(sql,intList.toArray(), new CountryExtractor());
		String countryName  = country.getCountryName();
		return countryName;
	}

	@Override
	public String addCountry(Country country) {
	
		List<String> intList = new ArrayList<String>();
		String query ="insert into country (countryName) values ?";
		if(StringUtils.isEmpty(country) && StringUtils.isEmpty(country.getCountryName())){
		intList.add(country.getCountryName());
		if(isCountryExist(country.getCountryName())){
			jdbcTemplate.update(query, intList);
			return country+" "+"Country Is Added";
		}
		else{
			return country+" "+"Country Is already exists";
		}
		}else {
			return "Please Check your Country Name";
		}
		
	}

	@Override
	public int getCountryCodeByCountryName(String countryName) {
		String sql = "select * from country where countryName = ?";
		List<String> intList = new ArrayList<String>();
		intList.add(countryName);
		Country country = (Country) jdbcTemplate.query(sql,intList.toArray(), new CountryExtractor());
		int countryCode = country.getCountryCode();
		return countryCode;
	}

	@Override
	public Country modifyCountry(String oldCountryName, String newCountryName) {
		String updateQuery = "update country set countryName = ? where countryName=?";
		Map<String,String> modifymap = new HashMap<String, String>();
		modifymap.put(oldCountryName, newCountryName);
		jdbcTemplate.update(updateQuery, modifymap);
		
		Country country = null;
		String query = "select * from country where countryName = ?";
		List<String> intList = new ArrayList<String>();
		intList.add(newCountryName);
		country = (Country) jdbcTemplate.query(query,intList.toArray(), new CountryExtractor());
		
		return country;
	}

	@Override
	public String deleteCountryByCountryName(String countryName) {
		String query ="DELETE FROM country WHERE countryName = ?";
		List<String> intList = new ArrayList<String>();
		intList.add(countryName);
		jdbcTemplate.update(query, intList);
		return "Country is Deleted";
	}

	@Override
	public HashMap<Integer, Country> getCountry() {
		String query ="select * from country";
		HashMap<Integer, Country> hashMap = jdbcTemplate.query(query, new CountryListExtrator());
		return hashMap;
	}

	@Override
	public boolean isCountryExist(String countryName) {
		
		String query = "select * from country where countryName = ?";
		List<String> intList = new ArrayList<String>();
		intList.add(countryName);
		Country country =  (Country) jdbcTemplate.query(query,intList.toArray(), new CountryExtractor());
		
		countryName = country.getCountryName();
		
		if(countryName == null){
			return true;
		}
		else{
			return false;
		}
		
		
		
	}

	


	
}
