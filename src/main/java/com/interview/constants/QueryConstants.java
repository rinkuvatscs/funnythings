package com.interview.constants;

public class QueryConstants {

	public static final String ADDTOPIC = " INSERT INTO TOPICS VALUES (LAST_INSERT_ID(), ?) ";
	public static final String GETTOPIC = " SELECT * FROM TOPICS where status='A' ";
	public static final String GETTOPICBYNAME = " SELECT * FROM TOPICS WHERE topic_name = ? and status='A'";
	public static final String MODIFYBYTOPICNAME = " UPDATE TOPICS SET topic_name = ? WHERE topic_name = ? ";
	public static final String ISEXIST = " SELECT * FROM TOPICS WHERE  LOWER(topic_name) = ? ";

	public static final String ADDUSERDETAILS = " INSERT INTO USER_DETAILS (firstname,lastname,email,mobile,topicid) VALUES (?, ?, ?, ?, ?)";
	public static final String GETUSERDETAILS = " SELECT * FROM USER_DETAILS WHERE status = 'A' ";
	public static final String GETUSERBYEMAIL = " SELECT * FROM USER_DETAILS where status='A' ";
	public static final String DEACTIVATEUSER = " UPDATE USER_DETAILS SET STATUS = 'D' WHERE EMAIL = ? ";
	public static final String ACTIVATEUSER = " UPDATE USER_DETAILS SET STATUS = 'A' WHERE EMAIL = ? ";
	public static final String INTERVIEW_DETAIL_ADD = " INSERT INTO INTERVIEW_DETAIL (user_id,file_location,state_id,country_id,topic_id,status) VALUES (?, ?, ?, ?, ?, ?)";

}
