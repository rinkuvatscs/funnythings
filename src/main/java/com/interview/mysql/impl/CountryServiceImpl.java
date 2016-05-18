package com.interview.mysql.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.interview.constants.QueryConstants;
import com.interview.extractor.CountryExtractor;
import com.interview.extractor.CountryListExtrator;
import com.interview.mysql.CountryService;
import com.interview.pojo.Country;
import com.interview.util.MysqlOperations;

@Repository
public class CountryServiceImpl implements CountryService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public String getCountryNameByCountryCode(int cuontryCode) {
		List<Integer> intList = new ArrayList<Integer>();
		intList.add(cuontryCode);
		Country country = (Country) jdbcTemplate.query(QueryConstants.SELECT_COUNTRY, intList.toArray(),
				new CountryExtractor());
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
				int result = jdbcTemplate.update(QueryConstants.INSERT_COUNTRY, intList.toArray());
				if (result > 0) {
					response = "Country " + country.getCountryName() + " " + "is Added";
				} else {
					response = "Sorry , Can not add " + country;
				}
			} else {
				if (!country.getStatus().equals("A")) {
					activateDeactivateCountryByCountryName(MysqlOperations.ACTIVATE, country.getCountryName());
					response = "Country " + country.getCountryName() + " "
							+ "is already exists therefore making active";
				} else {
					response = "Country " + country.getCountryName() + " " + "is already exists and Active";
				}
			}
		} else {
			response = "Please Check your Country Name, either it is empty or unknown country";
		}

		return response;
	}

	@Override
	public Country getCountryCodeByCountryName(String countryName) {

		List<String> intList = new ArrayList<String>();
		intList.add(countryName);
		Country country = (Country) jdbcTemplate.query(QueryConstants.GET_COUNTRY_CODE_BY_COUNTRYNAME,
				intList.toArray(), new CountryExtractor());
		/*
		 * If Country Object not found then what will be Country Value ?
		 */
		return country;
	}

	@Override
	public Country modifyCountry(String oldCountryName, String newCountryName) {

		List<String> intList = new ArrayList<String>();
		intList.add(newCountryName);
		intList.add(oldCountryName);
		jdbcTemplate.update(QueryConstants.MODIFYCOUNTRY, intList);

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
			query = QueryConstants.UPDATE_TO_ACTIVATE;
		} else if (mysqlOperations.toString().equalsIgnoreCase(MysqlOperations.DEACTIVATE.toString())) {
			query = QueryConstants.UPDATE_TO_DEACTIVATE;
		}
		List<String> intList = new ArrayList<String>();
		intList.add(countryName);
		jdbcTemplate.update(query, intList.toArray());
		return countryName +" has been deleted ";
	}

	@Override
	public List<Country> getCountry() {
		String query = "select * from country where status = 'A'";
		List<Country> countryList = jdbcTemplate.query(query, new CountryListExtrator());
		return countryList;
	}

	@Override
	public boolean isCountryExist(Country country) {

		List<String> intList = new ArrayList<String>();
		intList.add(country.getCountryName());
		Country tempCountry = null;
		boolean status = false;
		try {
			tempCountry = (Country) jdbcTemplate.query(QueryConstants.ISCOUNTRYEXIST, intList.toArray(),
					new CountryExtractor());
			if (!StringUtils.isEmpty(tempCountry)) {
				country.setStatus(tempCountry.getStatus());
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
