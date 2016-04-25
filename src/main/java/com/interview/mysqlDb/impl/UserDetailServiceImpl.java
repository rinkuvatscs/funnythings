package com.interview.mysqlDb.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.interview.constants.QueryConstants;
import com.interview.extractor.UserDetailExtractor;
import com.interview.mysqlDb.UserDetailService;
import com.interview.pojo.UserDetail;

@Component
public class UserDetailServiceImpl implements UserDetailService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public UserDetail addUser(UserDetail userDetail) throws SQLException {

		UserDetail doExist = isUserExist(userDetail);
		if (doExist != null && doExist.getUserId() > 0) {
			return doExist;
		}
		String sql = QueryConstants.ADDUSERDETAILS;
		List<String> args = new ArrayList<>();
		args.add(userDetail.getFirstName());
		args.add(userDetail.getLastName());
		args.add(userDetail.getEmailAddress());
		if (StringUtils.isEmpty(userDetail.getMobileNum())) {
			args.add(String.valueOf(userDetail.getMobileNum()));
		} else {
			args.add("0");
		}

		try {
			int response = jdbcTemplate.update(sql, args.toArray());
			if (response != 0) {
				UserDetail details = getUserByEmail(userDetail.getEmailAddress());
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
			List<UserDetail> response = jdbcTemplate.query(sql, new UserDetailExtractor());
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
			List<UserDetail> response = jdbcTemplate.query(sql + str, args.toArray(), new UserDetailExtractor());
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
					int response = jdbcTemplate.update(str.toString(), args.toArray());
					if (response > 0) {
						UserDetail user = getUserByEmail(userDetail.getEmailAddress());
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
	public String activateDeactivateUser(String email, String status) throws SQLException {

		String sqlDeactive = QueryConstants.DEACTIVATEUSER;
		String sqlActive = QueryConstants.ACTIVATEUSER;
		List<String> args = new ArrayList<>();
		if (!StringUtils.isEmpty(email)) {
			args.add(email);
			if (StringUtils.isEmpty(status) || status.equalsIgnoreCase("D")) {
				try {
					int response = jdbcTemplate.update(sqlDeactive, args.toArray());
					if (response > 0) {
						return "User successfully Deactivat...";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					int response = jdbcTemplate.update(sqlActive, args.toArray());
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

	private UserDetail isUserExist(UserDetail userDetail) {

		String sql = QueryConstants.GETUSERBYEMAIL;
		StringBuffer str = new StringBuffer();
		List<String> args = new ArrayList<>();
		if (!StringUtils.isEmpty(userDetail.getEmailAddress())) {
			str.append(" email = ? ");
			args.add(userDetail.getEmailAddress());
			if (StringUtils.isEmpty(userDetail.getMobileNum())) {
				str.append(" OR mobile_number = ? ");
				args.add(String.valueOf(userDetail.getMobileNum()));
			}
		}
		try {
			List<UserDetail> response = jdbcTemplate.query(sql + str, args.toArray(), new UserDetailExtractor());
			if (response != null && !response.isEmpty()) {
				UserDetail details = response.get(0);
				return details;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
