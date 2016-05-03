package com.interview.mysql;

import java.util.Map;

import com.interview.pojo.UserDetail;

public interface UserDetailService {

	public UserDetail addUser(UserDetail user_Details);

	public Map<Integer, UserDetail> getUserDetails();

	public UserDetail getUserByEmail(String name);

	public UserDetail modifyByEmail(UserDetail user_Details);

	public String activateDeactivateUserByEmail(String email, String status);
}
