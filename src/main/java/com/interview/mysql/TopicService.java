package com.interview.mysql;

import java.sql.SQLException;
import java.util.List;

import com.interview.pojo.Topic;

public interface TopicService {

	public String addTopics(String topicName) throws SQLException;

	public List<Topic> getTopic() throws SQLException;

	public Topic getTopicByName(String name) throws SQLException;

	public Topic modifyByTopicName(String oldName, String newName) throws SQLException;
}
