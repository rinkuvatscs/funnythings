package com.interview.mysqlDb;

import java.sql.SQLException;
import java.util.Map;

import com.interview.pojo.UserDetail;

public interface UserDetailService {

	public UserDetail addUser(UserDetail user_Details) throws SQLException;

	public Map<Integer, UserDetail> getUserDetails() throws SQLException;

	public UserDetail getUserByEmail(String name) throws SQLException;

	public UserDetail modifyByEmail(UserDetail user_Details) throws SQLException;

	public String activateDeactivateUser(String email, String status) throws SQLException;
}
