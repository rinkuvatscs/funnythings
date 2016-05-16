package com.interview.mysql.impl;

import java.util.ArrayList;
import java.util.List;

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
	public static final String INSERT_COUNTRY = " INSERT INTO Country (countryCode,countryName) VALUES (0, ?) ";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public String getCountryNameByCountryCode(int cuontryCode) {
		List<Integer> intList = new ArrayList<Integer>();
		intList.add(cuontryCode);
		Country country = (Country) jdbcTemplate.query(SELECT_COUNTRY, intList.toArray(), new CountryExtractor());
		if (StringUtils.isEmpty(country)) {
			country = new Country();
		}
		return country.getCountryName();
	}

	@Override
	public String addCountry(Country country) {
		String response = null;
		List<String> intList = new ArrayList<String>();
		if (!StringUtils.isEmpty(country) && !StringUtils.isEmpty(country.getCountryName())) {
			intList.add(country.getCountryName());
			if (!isCountryExist(country)) {
				int result = jdbcTemplate.update(INSERT_COUNTRY, intList.toArray());
				if (result > 0) {
					response = "Country " + country.getCountryName() + " " + "is Added";
				} else {
					response = "Sorry , Can not add " + country;
				}
			} else {
				response = "Country " + country.getCountryName() + " " + "is already exists";
			}
		} else {
			response = "Please Check your Country Name, either it is empty or unknown country";
		}

		return response;
	}

	@Override
	public Country getCountryCodeByCountryName(String countryName) {
		String sql = "select * from country where countryName = ?";
		List<String> intList = new ArrayList<String>();
		intList.add(countryName);
		Country country = (Country) jdbcTemplate.query(sql, intList.toArray(), new CountryExtractor());
		/*
		 * If Country Object not found then what will be Country Value ?
		 */
		return country;
	}

	@Override
	public Country modifyCountry(String oldCountryName, String newCountryName) {
		String updateQuery = "update country set countryName = ? where countryName=?";
		List<String> intList = new ArrayList<String>();
		intList.add(newCountryName);
		intList.add(oldCountryName);
		jdbcTemplate.update(updateQuery, intList);

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
	public String activateDeactivateCountryByCountryName(MysqlOperations mysqlOperations, String countryName) {
		String query = null;
		/* String query ="DELETE FROM country WHERE countryName = ?"; */
		/*
		 * At THe Time of deletion we will not delete Country we will disable it
		 */
		if (mysqlOperations.toString().equalsIgnoreCase(MysqlOperations.ACTIVATE.toString())) {
			query = "UPDATE country set status = 'A' WHERE countryName = ?";
		} else if (mysqlOperations.toString().equalsIgnoreCase(MysqlOperations.DEACTIVATE.toString())) {
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
		List<Country> countryList = jdbcTemplate.query(query, new CountryListExtrator());
		return countryList;
	}

	private boolean isCountryExist(Country country) {

		String query = "SELECT * FROM country WHERE countryName = ? ";
		List<String> intList = new ArrayList<String>();
		intList.add(country.getCountryName());
		Country tempCountry = null;
		boolean status = false;
		try {
			tempCountry = (Country) jdbcTemplate.query(query, intList.toArray(), new CountryExtractor());
			System.out.println(tempCountry);
			if (!StringUtils.isEmpty(tempCountry)) {
				status = true;
			} else {
				status = false;
			}
		} catch (Exception ex) {
			status = false;
		}
		return status;
	}

}
