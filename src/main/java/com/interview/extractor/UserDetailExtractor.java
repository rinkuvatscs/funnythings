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
			userDetail.setUserId(rs.getInt("userid"));
			userDetail.setFirstName(rs.getString("firstname"));
			userDetail.setLastName(rs.getString("lastname"));
			userDetail.setEmailAddress(rs.getString("email"));
			userDetail.setMobileNum(rs.getString("mobile"));
			userDetail.setStatus(rs.getString("status"));
			userDetail.setLocation(rs.getString("location"));
			userDetail.setTopicId(rs.getInt("topic_id"));
			userDetail.setFileLocation(rs.getString("file_location"));

			userDetailsList.add(userDetail);
		}
		return userDetailsList;
	}

}
