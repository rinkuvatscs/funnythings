package com.interview.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.interview.pojo.UserDetail;

public class UserDetailExtractor implements ResultSetExtractor<List<UserDetail>> {

	@Override
	public List<UserDetail> extractData(ResultSet rs) throws SQLException, DataAccessException {

		List<UserDetail> userDetailsList = new ArrayList<UserDetail>();
		UserDetail userDetail = null;
		while (rs.next()) {
			userDetail = new UserDetail();
			userDetail.setUserId(rs.getInt("user_id"));
			userDetail.setFirstName(rs.getString("first_name"));
			userDetail.setLastName(rs.getString("last_name"));
			userDetail.setEmailAddress(rs.getString("email"));
			userDetail.setMobileNum(rs.getString("mobile_number"));
			userDetail.setStatus(rs.getString("status"));
			userDetailsList.add(userDetail);
		}
		return userDetailsList;
	}

}
