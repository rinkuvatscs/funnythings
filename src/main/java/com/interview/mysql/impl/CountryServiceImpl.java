package com.interview.mysql.impl;

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
import com.interview.mysql.CountryService;
import com.interview.pojo.Country;
import com.interview.util.MysqlOperations;

@Repository
public class CountryServiceImpl implements CountryService {

	public static final String SELECT_COUNTRY = "select * from country where id = ?";
	public static final String INSERT_COUNTRY = "INSERT INTO Country VALUES (LAST_INSERT_ID(), ?)";
	/*
	 * country --country_id,country_name
	 * 
	 * country_states country_id,state_id,state_name
	 * 
	 * 
	 * create table country(country_id number, country_name varchar2(300))
	 * 
	 * insert into country values(2,'PAKISTAN')
	 * 
	 * SELECT * FROM COUNTRY
	 * 
	 * CREATE TABLE COUNTRY_STATES(COUNTRY_ID NUMBER,STATE_ID NUMBER,STATE_NAME
	 * VARCHAR2(30))
	 * 
	 * INSERT INTO COUNTRY_STATES VALUES(2,2,'YY')
	 * 
	 * SELECT B.COUNTRY_ID,B.COUNTRY_NAME ,A.STATE_NAME FROM COUNTRY_STATES
	 * A,COUNTRY B WHERE A.COUNTRY_ID=B.COUNTRY_ID AND B.COUNTRY_NAME='INDIA'
	 */

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public String getCountryNameByCountryCode(int cuontryCode) {
		List<Integer> intList = new ArrayList<Integer>();
		intList.add(cuontryCode);
		Country country = (Country) jdbcTemplate.query(SELECT_COUNTRY,
				intList.toArray(), new CountryExtractor());
		if (StringUtils.isEmpty(country)) {
			country = new Country();
		}
		return country.getCountryName();
	}

	@Override
	public String addCountry(Country country) {
		String response = null;
		List<String> intList = new ArrayList<String>();
		if (!StringUtils.isEmpty(country)
				&& !StringUtils.isEmpty(country.getCountryName())) {
			intList.add(country.getCountryName());
			if (!isCountryExist(country)) {
				int result = jdbcTemplate.update(INSERT_COUNTRY, intList);
				// GORU TODO Need to explain why using
				// jdbcTemplate.update(query, intList)
				/*
				 * GORU in place of jdbcTemplate.update(query, intList) we
				 * should use jdbcTemplate.update(query,
				 * country.getCountryName());
				 */
				if (result > 0) {
					response = country + " " + "Country Is Added";
				} else {
					response = "Sorry , Can not add " + country;
				}
			} else {

				if (country.getStatus().equalsIgnoreCase("D")) {
					activateDeactivateCountryByCountryName(
							MysqlOperations.ACTIVATE, country.getCountryName());
					response = country + " " + "Country Is Activated";
				}

				response = country + " " + "Country Is already exists";
			}
		} else {
			response = "Please Check your Country Name";
		}

		return response;
	}

	@Override
	public Country getCountryCodeByCountryName(String countryName) {
		String sql = "select * from country where countryName = ?";
		List<String> intList = new ArrayList<String>();
		intList.add(countryName);
		Country country = (Country) jdbcTemplate.query(sql, intList.toArray(),
				new CountryExtractor());
		/*
		 * If Country Object not found then what will be Country Value ?
		 */
		return country;
	}

	@Override
	public Country modifyCountry(String oldCountryName, String newCountryName) {
		String updateQuery = "update country set countryName = ? where countryName=?";
		Map<String, String> modifymap = new HashMap<String, String>();
		modifymap.put(oldCountryName, newCountryName);
		jdbcTemplate.update(updateQuery, modifymap);

		/*
		 * In between start and end line code is use less when we have
		 * getCountryCodeByCountryName(String COuntryCode) method in this class
		 * then why we are increasing duplicate code
		 */

		// START LINE
		/*
		 * Country country = null; String query =
		 * "select * from country where countryName = ?"; List<String> intList =
		 * new ArrayList<String>(); intList.add(newCountryName); country =
		 * (Country) jdbcTemplate.query(query,intList.toArray(), new
		 * CountryExtractor());
		 */
		return getCountryCodeByCountryName(newCountryName);
		// END LINE

	}

	@Override
	public String activateDeactivateCountryByCountryName(
			MysqlOperations mysqlOperations, String countryName) {
		String query = null;
		/* String query ="DELETE FROM country WHERE countryName = ?"; */
		/*
		 * At THe Time of deletion we will not delete Country we will disable it
		 */
		if (mysqlOperations.toString().equalsIgnoreCase(
				MysqlOperations.ACTIVATE.toString())) {
			query = "UPDATE country set status = 'A' WHERE countryName = ?";
		} else if (mysqlOperations.toString().equalsIgnoreCase(
				MysqlOperations.DEACTIVATE.toString())) {
			query = "UPDATE country set status = 'D' WHERE countryName = ?";
		}
		List<String> intList = new ArrayList<String>();
		intList.add(countryName);
		jdbcTemplate.update(query, intList);
		return "Country is modified";
	}

	@Override
	public List<Country> getCountry() {
		String query = "select * from country where status = 'A'";
		List<Country> countryList = jdbcTemplate.query(query,
				new CountryListExtrator());
		return countryList;
	}

	@Override
	public boolean isCountryExist(Country country) {

		String query = "select * from country where country_name = ? ";
		List<String> intList = new ArrayList<String>();
		intList.add(country.getCountryName());
		Country tempCountry = (Country) jdbcTemplate.query(query,
				intList.toArray(), new CountryExtractor());
		if (!StringUtils.isEmpty(tempCountry)
				&& !StringUtils.isEmpty(tempCountry.getStatus())) {
			country.setCountryCode(tempCountry.getCountryCode());
			country.setStatus(tempCountry.getStatus());
		} else {
			country.setCountryCode(0);
		}

		if (country.getCountryCode() != 0) {
			return true;
		} else {
			return false;
		}

	}

}
