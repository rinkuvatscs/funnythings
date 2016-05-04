package com.interview.mysql.impl;

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
	public UserDetail addUser(UserDetail userDetail) {
		UserDetail responseUserDetail = null;

		if (isUserExist(userDetail))
			return userDetail;

		List<String> args = new ArrayList<>();
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
			int response = jdbcTemplate.update(QueryConstants.ADDUSERDETAILS,
					args.toArray());
			if (response != 0) {
				responseUserDetail = getUserByEmail(userDetail
						.getEmailAddress());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseUserDetail = new UserDetail();
		}

		return responseUserDetail;
	}

	@Override
	public Map<Integer, UserDetail> getUserDetails() {
		Map<Integer, UserDetail> userDetailMap = new HashMap<Integer, UserDetail>();
		try {
			List<UserDetail> response = jdbcTemplate.query(
					QueryConstants.GETUSERDETAILS, new UserDetailExtractor());
			if (!StringUtils.isEmpty(response)) {
				for (UserDetail userDetail : response) {
					userDetailMap.put(userDetail.getUserId(), userDetail);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			userDetailMap.clear();
		}
		return userDetailMap;
	}

	@Override
	public UserDetail getUserByEmail(String email) {
		UserDetail userDetail = null;
		StringBuffer str = new StringBuffer();
		List<String> args = new ArrayList<>();
		if (!StringUtils.isEmpty(email)) {
			str.append("where email = ? ");
			args.add(email);
		}
		try {
			List<UserDetail> response = jdbcTemplate.query(
					QueryConstants.GETUSERBYEMAIL + str, args.toArray(),
					new UserDetailExtractor());
			if (!StringUtils.isEmpty(response)
					&& !StringUtils.isEmpty(response.get(0))) {
				userDetail = response.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			userDetail = new UserDetail();
		}
		return userDetail;
	}

	@Override
	public UserDetail modifyByEmail(UserDetail userDetail) {
		boolean isSecondArg = false;
		UserDetail responseUserDetail = null;
		StringBuffer str = new StringBuffer(" UPDATE USER_DETAILS SET ");
		List<String> args = new ArrayList<>();
		if (!StringUtils.isEmpty(userDetail)) {
			if (!StringUtils.isEmpty(userDetail.getEmailAddress())) {

				if (!StringUtils.isEmpty(userDetail.getFirstName())) {
					str.append("FIRST_NAME = ? ");
					args.add(userDetail.getFirstName());
					isSecondArg = true;
				}

				if (!StringUtils.isEmpty(userDetail.getLastName())) {
					if (isSecondArg) {
						str.append(", LAST_NAME = ? ");
					} else {
						str.append(" SET LAST_NAME = ? ");
						isSecondArg = true;
					}

					args.add(userDetail.getLastName());
				}
				if (!StringUtils.isEmpty(userDetail.getMobileNum())) {
					if (isSecondArg) {
						str.append(", MOBILE_NUMBER = ? ");
					} else {
						str.append(" SET MOBILE_NUMBER = ? ");
						isSecondArg = true;
					}
					args.add(String.valueOf(userDetail.getMobileNum()));
				}
				if (!StringUtils.isEmpty(userDetail.getStatus())) {
					if (isSecondArg) {
						str.append(", STATUS = ? ");
					} else {
						str.append(" SET STATUS = ? ");
					}
					args.add(userDetail.getStatus());
				}
				str.append("WHERE EMAIL = ? ");
				args.add(userDetail.getEmailAddress());

				try {
					int response = jdbcTemplate.update(str.toString(),
							args.toArray());
					if (response > 0) {
						responseUserDetail = getUserByEmail(userDetail
								.getEmailAddress());
					}
				} catch (Exception e) {
					e.printStackTrace();
					responseUserDetail = new UserDetail();
				}
			}
		}
		return responseUserDetail;
	}

	@Override
	public String activateDeactivateUserByEmail(String email, String status) {

		List<String> args = new ArrayList<>();
		if (!StringUtils.isEmpty(email)) {
			args.add(email);
			if (StringUtils.isEmpty(status) || status.equalsIgnoreCase("D")) {
				try {
					int response = jdbcTemplate.update(
							QueryConstants.DEACTIVATEUSER, args.toArray());
					if (response > 0) {
						return "User Deactivated successfully ";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					int response = jdbcTemplate.update(
							QueryConstants.ACTIVATEUSER, args.toArray());
					if (response > 0) {
						return "User Activated  successfully";
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
