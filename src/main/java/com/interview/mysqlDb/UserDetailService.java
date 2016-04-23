package com.interview.mysqlDb;

import java.sql.SQLException;
import java.util.Map;

import com.interview.pojo.User_Details;

public interface UserDetailService {

	public User_Details addUser(User_Details user_Details) throws SQLException;

	public Map<Integer, User_Details> getUserDetails() throws SQLException;

	public User_Details getUserByEmail(String name) throws SQLException;

	public User_Details modifyByEmail(User_Details user_Details) throws SQLException;

	public String activateDeactivateUser(String email, String status) throws SQLException;
}
