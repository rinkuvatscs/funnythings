package com.interview.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.interview.pojo.Country;

public class CountryListExtrator implements ResultSetExtractor<HashMap<Integer, Country>> {

	@Override
	public HashMap<Integer, Country> extractData(ResultSet rs) throws SQLException, DataAccessException {
		HashMap<Integer, Country> hashMap = new HashMap<Integer, Country>();
		Country country = null;
		while (rs.next()) {
			country = new Country();
			country.setCountryCode(rs.getInt("id"));
			country.setCountryName(rs.getString("countryName"));
			hashMap.put(country.getCountryCode(), country);
		}
		return hashMap;
	}

	

}
