package com.interview.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.interview.pojo.Country;

public class CountryListExtrator implements ResultSetExtractor<List<Country>> {

	@Override
	public List<Country> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		List<Country> countryList = new ArrayList<Country>();
		Country country = null;
		while (rs.next()) {
			country = new Country();
			country.setCountryCode(rs.getInt("id"));
			country.setCountryName(rs.getString("countryName"));
			countryList.add(country);
		}
		return countryList;
	}

}
