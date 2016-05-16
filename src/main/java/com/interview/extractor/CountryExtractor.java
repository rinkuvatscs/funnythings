package com.interview.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.interview.pojo.Country;

public class CountryExtractor implements ResultSetExtractor<Object> {

	@Override
	public Country extractData(ResultSet rs) throws SQLException, DataAccessException {
		Country country = null;
		if (rs.next()) {
			country = new Country();
			country.setCountryCode(rs.getInt("countryCode"));
			country.setCountryName(rs.getString("countryName"));
			country.setStatus(rs.getString("status"));
		}
		return country;
	}

}
