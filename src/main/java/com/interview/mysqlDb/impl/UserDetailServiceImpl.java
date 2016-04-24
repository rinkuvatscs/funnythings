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
import com.interview.extractor.UserDetailsExtractor;
import com.interview.mysqlDb.UserDetailService;
import com.interview.pojo.UserDetails;

@Component
public class UserDetailServiceImpl implements UserDetailService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public UserDetails addUser(UserDetails user_Details) throws SQLException {

		UserDetails doExist = isUserExist(user_Details);
		if (doExist != null && doExist.getUser_id() > 0) {
			return doExist;
		}
		String sql = QueryConstants.addUserDetails;
		List<String> args = new ArrayList<>();
		args.add(user_Details.getFirst_name());
		args.add(user_Details.getLast_name());
		args.add(user_Details.getEmail());
		if (user_Details.getMobile_number() > 0) {
			args.add(String.valueOf(user_Details.getMobile_number()));
		} else {
			args.add("0");
		}

		try {
			int response = jdbcTemplate.update(sql, args.toArray());
			if (response != 0) {
				UserDetails details = getUserByEmail(user_Details.getEmail());
				return details;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Map<Integer, UserDetails> getUserDetails() throws SQLException {

		String sql = QueryConstants.getUserDetails;
		try {
			List<UserDetails> response = jdbcTemplate.query(sql, new UserDetailsExtractor());
			if (response != null && !response.isEmpty()) {
				Map<Integer, UserDetails> map = new HashMap<>();
				for (int i = 0; i < response.size(); i++) {
					map.put(response.get(i).getUser_id(), response.get(i));
				}
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public UserDetails getUserByEmail(String name) throws SQLException {

		String sql = QueryConstants.getUserByEmail;
		StringBuffer str = new StringBuffer();
		List<String> args = new ArrayList<>();
		if (!StringUtils.isEmpty(name)) {
			str.append(" email = ? ");
			args.add(name);
		}
		try {
			List<UserDetails> response = jdbcTemplate.query(sql + str, args.toArray(), new UserDetailsExtractor());
			if (response != null && !response.isEmpty()) {
				return response.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public UserDetails modifyByEmail(UserDetails user_Details) throws SQLException {

		StringBuffer str = new StringBuffer(" UPDATE USER_DETAILS ");
		List<String> args = new ArrayList<>();
		if (!StringUtils.isEmpty(user_Details)) {
			if (!StringUtils.isEmpty(user_Details.getEmail())) {
				if (!StringUtils.isEmpty(user_Details.getFirst_name())) {
					str.append("SET FIRST_NAME = ? ");
					args.add(user_Details.getFirst_name());
				}
				if (!StringUtils.isEmpty(user_Details.getLast_name())) {
					str.append(", LAST_NAME = ? ");
					args.add(user_Details.getLast_name());
				}
				if (user_Details.getMobile_number() > 0) {
					str.append(", MOBILE_NUMBER = ? ");
					args.add(String.valueOf(user_Details.getMobile_number()));
				}
				if (!StringUtils.isEmpty(user_Details.getStatus())) {
					str.append(", SET STATUS = ? ");
					args.add(user_Details.getStatus());
				}
				str.append("WHERE EMAIL = ? ");
				args.add(user_Details.getEmail());

				try {
					int response = jdbcTemplate.update(str.toString(), args.toArray());
					if (response > 0) {
						UserDetails user = getUserByEmail(user_Details.getEmail());
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

		String sqlDeactive = QueryConstants.deactivateUser;
		String sqlActive = QueryConstants.activateUser;
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

	private UserDetails isUserExist(UserDetails user_Details) {

		String sql = QueryConstants.getUserByEmail;
		StringBuffer str = new StringBuffer();
		List<String> args = new ArrayList<>();
		if (!StringUtils.isEmpty(user_Details.getEmail())) {
			str.append(" email = ? ");
			args.add(user_Details.getEmail());
			if (user_Details.getMobile_number() > 0) {
				str.append(" OR mobile_number = ? ");
				args.add(String.valueOf(user_Details.getMobile_number()));
			}
		}
		try {
			List<UserDetails> response = jdbcTemplate.query(sql + str, args.toArray(), new UserDetailsExtractor());
			if (response != null && !response.isEmpty()) {
				UserDetails details = response.get(0);
				return details;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
