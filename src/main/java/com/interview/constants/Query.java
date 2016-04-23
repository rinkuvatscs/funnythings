package com.interview.constants;

public class Query {

	public static final String addTopic = " INSERT INTO TOPICS VALUES (LAST_INSERT_ID(), ?) ";
	public static final String getTopic = " SELECT * FROM TOPICS ";
	public static final String getTopicByName = " SELECT * FROM TOPICS WHERE topic_name = ? ";
	public static final String modifyByTopicName = " UPDATE TOPICS SET topic_name = ? WHERE topic_name = ? ";
	public static final String isExist = " SELECT * FROM TOPICS WHERE  LOWER(topic_name) = ? ";

	public static final String addUserDetails = " INSERT INTO USER_DETAILS (first_name,last_name,email,mobile_number) VALUES (?, ?, ?, ?)";
	public static final String getUserDetails = " SELECT * FROM USER_DETAILS WHERE status = 'A' ";
	public static final String getUserByEmail = " SELECT * FROM USER_DETAILS WHERE  ";
	public static final String deactivateUser = " UPDATE USER_DETAILS SET STATUS = 'D' WHERE EMAIL = ? ";
	public static final String activateUser = " UPDATE USER_DETAILS SET STATUS = 'A' WHERE EMAIL = ? ";
}
