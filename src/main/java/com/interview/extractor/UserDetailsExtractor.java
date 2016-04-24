package com.interview.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.interview.pojo.UserDetails;

public class UserDetailsExtractor implements ResultSetExtractor<List<UserDetails>> {

	@Override
	public List<UserDetails> extractData(ResultSet rs) throws SQLException, DataAccessException {

		List<UserDetails> userDetailsList = new ArrayList<UserDetails>();
		UserDetails user_Details = null;
		while (rs.next()) {
			user_Details = new UserDetails();
			user_Details.setUser_id(rs.getInt("user_id"));
			user_Details.setFirst_name(rs.getString("first_name"));
			user_Details.setLast_name(rs.getString("last_name"));
			user_Details.setEmail(rs.getString("email"));
			user_Details.setMobile_number(rs.getLong("mobile_number"));
			user_Details.setStatus(rs.getString("status"));
			userDetailsList.add(user_Details);
		}
		return userDetailsList;
	}

}
