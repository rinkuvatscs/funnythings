package com.interview.constants;

public class Query {

	public static final String addTopic = " INSERT INTO TOPICS VALUES (LAST_INSERT_ID(), ?) ";
	public static final String getTopic = " SELECT * FROM TOPICS ";
	public static final String getTopicByName = " SELECT * FROM TOPICS WHERE topic_name = ? ";
	public static final String modifyByTopicName = " UPDATE TOPICS SET topic_name = ? WHERE topic_name = ? ";
	public static final String isExist = " SELECT * FROM TOPICS WHERE  LOWER(topic_name) = ? ";
}
