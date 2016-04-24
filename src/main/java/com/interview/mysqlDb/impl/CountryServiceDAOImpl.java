package com.interview.mysqlDb.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.interview.extractor.CountryExtractor;
import com.interview.mysqlDb.CountryServiceDAO;
import com.interview.pojo.Country;


@Component
public class CountryServiceDAOImpl implements CountryServiceDAO {

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

	public String getCountry(int contryCode) {
		String sql = "select * from country where id = ?";
		List<Integer> intList = new ArrayList<Integer>();
		intList.add(contryCode);
		Country country = (Country) jdbcTemplate.query(sql,intList.toArray(), new CountryExtractor());
		String countryName  = country.getCountryName();
		return countryName;
	}

	@Override
	public String addCountry(String country) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getContrycodeByName(String countryName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Country modifyCountry(Country oldCountryName, Country newCountryName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteCountryByName(String countryName) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
