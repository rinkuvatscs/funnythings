package com.interview.mysql.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.interview.constants.QueryConstants;
import com.interview.extractor.UserDetailExtractor;
import com.interview.mysql.UserDetailService;
import com.interview.pojo.UserDetail;

@Repository
public class UserDetailServiceImpl implements UserDetailService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public UserDetail addUser(UserDetail userDetail) throws SQLException {

		if (isUserExist(userDetail))
			return userDetail;
		String sql = QueryConstants.ADDUSERDETAILS;
		List<String> args = new ArrayList<>();
		// args.add(String.valueOf(userDetail.getUserId()));
		args.add(userDetail.getFirstName());
		args.add(userDetail.getLastName());
		args.add(userDetail.getEmailAddress());
		if (!StringUtils.isEmpty(userDetail.getMobileNum())) {
			args.add(String.valueOf(userDetail.getMobileNum()));
		} else {
			args.add("0");
		}
		args.add(userDetail.getStatus());
		args.add(userDetail.getLocation());
		args.add(userDetail.getTopic());
		if (userDetail.getFile() != null) {
			args.add(userDetail.getFile().getContentType());
		} else {
			args.add(null);
		}
		try {
			int response = jdbcTemplate.update(sql, args.toArray());
			if (response != 0) {
				UserDetail details = getUserByEmail(userDetail
						.getEmailAddress());
				return details;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Map<Integer, UserDetail> getUserDetails() throws SQLException {

		String sql = QueryConstants.GETUSERDETAILS;
		try {
			List<UserDetail> response = jdbcTemplate.query(sql,
					new UserDetailExtractor());
			if (response != null && !response.isEmpty()) {
				Map<Integer, UserDetail> map = new HashMap<>();
				for (int i = 0; i < response.size(); i++) {
					map.put(response.get(i).getUserId(), response.get(i));
				}
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public UserDetail getUserByEmail(String name) throws SQLException {

		String sql = QueryConstants.GETUSERBYEMAIL;
		StringBuffer str = new StringBuffer();
		List<String> args = new ArrayList<>();
		if (!StringUtils.isEmpty(name)) {
			str.append(" email = ? ");
			args.add(name);
		}
		try {
			List<UserDetail> response = jdbcTemplate.query(sql + str,
					args.toArray(), new UserDetailExtractor());
			if (response != null && !response.isEmpty()) {
				return response.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public UserDetail modifyByEmail(UserDetail userDetail) throws SQLException {

		StringBuffer str = new StringBuffer(" UPDATE USER_DETAILS ");
		List<String> args = new ArrayList<>();
		if (!StringUtils.isEmpty(userDetail)) {
			if (!StringUtils.isEmpty(userDetail.getEmailAddress())) {
				if (!StringUtils.isEmpty(userDetail.getFirstName())) {
					str.append("SET FIRST_NAME = ? ");
					args.add(userDetail.getFirstName());
				}
				if (!StringUtils.isEmpty(userDetail.getLastName())) {
					str.append(", LAST_NAME = ? ");
					args.add(userDetail.getLastName());
				}
				if (!StringUtils.isEmpty(userDetail.getMobileNum())) {
					str.append(", MOBILE_NUMBER = ? ");
					args.add(String.valueOf(userDetail.getMobileNum()));
				}
				if (!StringUtils.isEmpty(userDetail.getStatus())) {
					str.append(", SET STATUS = ? ");
					args.add(userDetail.getStatus());
				}
				str.append("WHERE EMAIL = ? ");
				args.add(userDetail.getEmailAddress());

				try {
					int response = jdbcTemplate.update(str.toString(),
							args.toArray());
					if (response > 0) {
						UserDetail user = getUserByEmail(userDetail
								.getEmailAddress());
						return user;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public String activateDeactivateUser(String email, String status)
			throws SQLException {

		String sqlDeactive = QueryConstants.DEACTIVATEUSER;
		String sqlActive = QueryConstants.ACTIVATEUSER;
		List<String> args = new ArrayList<>();
		if (!StringUtils.isEmpty(email)) {
			args.add(email);
			if (StringUtils.isEmpty(status) || status.equalsIgnoreCase("D")) {
				try {
					int response = jdbcTemplate.update(sqlDeactive,
							args.toArray());
					if (response > 0) {
						return "User successfully Deactivat...";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					int response = jdbcTemplate.update(sqlActive,
							args.toArray());
					if (response > 0) {
						return "User successfully Active Again...";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private Boolean isUserExist(UserDetail userDetail) {
		boolean isUserExist = false;
		boolean isEmailAdded = false;
		StringBuffer str = new StringBuffer();
		List<String> args = new ArrayList<>();
		if (!StringUtils.isEmpty(userDetail)) {

			if (!StringUtils.isEmpty(userDetail.getEmailAddress())) {
				str.append("where email = ? ");
				args.add(userDetail.getEmailAddress());
				isEmailAdded = true;
			}
			if (!StringUtils.isEmpty(userDetail.getMobileNum())) {
				if (isEmailAdded) {
					str.append(" OR mobile_number = ? ");
				} else {
					str.append(" where mobile_number = ? ");
				}
				args.add(String.valueOf(userDetail.getMobileNum()));
			}
		}
		try {
			List<UserDetail> response = jdbcTemplate.query(
					QueryConstants.GETUSERBYEMAIL + str, args.toArray(),
					new UserDetailExtractor());
			if (!StringUtils.isEmpty(response)) {
				userDetail.setEmailAddress(response.get(0).getEmailAddress());
				userDetail.setFirstName(response.get(0).getFirstName());
				userDetail.setLastName(response.get(0).getLastName());
				userDetail.setLocation(response.get(0).getLocation());
				userDetail.setMobileNum(response.get(0).getMobileNum());
				userDetail.setTopic(response.get(0).getTopic());
				userDetail.setUserId(response.get(0).getUserId());
				isUserExist = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			isUserExist = false;
		} finally {
			str = null;
			args = null;
		}
		return isUserExist;
	}

}
