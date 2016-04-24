package com.interview.mysqlDb;

import java.sql.SQLException;
import java.util.Map;

import com.interview.pojo.UserDetails;

public interface UserDetailService {

	public UserDetails addUser(UserDetails user_Details) throws SQLException;

	public Map<Integer, UserDetails> getUserDetails() throws SQLException;

	public UserDetails getUserByEmail(String name) throws SQLException;

	public UserDetails modifyByEmail(UserDetails user_Details) throws SQLException;

	public String activateDeactivateUser(String email, String status) throws SQLException;
}
