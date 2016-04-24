package com.interview.mysqlDb.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.interview.pojo.Country;

public class CountryListExtrator implements ResultSetExtractor<HashMap<Integer, String>> {

	@Override
	public HashMap<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
		HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
		Country country = null;
		while (rs.next()) {
			country = new Country();
			country.setCountryCode(rs.getInt("id"));
			country.setCountryName(rs.getString("countryName"));
			hashMap.put(country.getCountryCode(), country.getCountryName());
		}
		return hashMap;
	}

	

}
