package com.interview.mysqlDb.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.interview.extractor.TopicExtractor;
import com.interview.mysqlDb.TopicService;
import com.interview.pojo.Topics;

@Component
public class TopicServiceImpl implements TopicService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public String addTopics(Topics topic) throws SQLException {

		if (!isExist(topic)) {
			String sql = " INSERT INTO TOPICS VALUES (LAST_INSERT_ID(), ?) ";
			List<String> args = new ArrayList<>();
			if (topic.getTopic_name() != null) {
				args.add(topic.getTopic_name());
			}
			try {
				int response = jdbcTemplate.update(sql, args.toArray());
				if (response > 0) {
					Topics topics = getTopicByName(topic.getTopic_name());
					return "Successfully Added topic id : " + topics.getTopic_id() + " topic name : "
							+ topics.getTopic_name();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "Successfully Added topic id : " + topic.getTopic_id() + " topic name : " + topic.getTopic_name();
		} else {
			Topics tempTopic = getTopicByName(topic.getTopic_name());
			return "Topic Already Exist topic id : " + tempTopic.getTopic_id() + " topic name : "
					+ tempTopic.getTopic_name();
		}

	}

	@Override
	public List<Topics> getTopic() throws SQLException {

		List<Topics> listTopic = new ArrayList<Topics>();
		String sql = " SELECT * FROM TOPICS ";

		try {
			List<Topics> response = jdbcTemplate.query(sql, new TopicExtractor());
			if (response.get(0) != null) {
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listTopic;
	}

	@Override
	public Topics getTopicByName(String name) throws SQLException {

		String sql = " SELECT * FROM TOPICS WHERE topic_name = ? ";
		List<String> args = new ArrayList<>();
		args.add(name);
		try {
			List<Topics> response = jdbcTemplate.query(sql, args.toArray(), new TopicExtractor());
			if (response.get(0) != null) {
				return response.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Topics modifyByTopicName(String oldName, String newName) throws SQLException {

		String sql = " UPDATE TOPICS SET topic_name = ? WHERE topic_name = ? ";
		List<String> args = new ArrayList<>();
		args.add(newName);
		args.add(oldName);
		try {
			int response = jdbcTemplate.update(sql, args.toArray());
			if (response != 0) {
				Topics topic = getTopicByName(newName);
				return topic;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private boolean isExist(Topics topic) {

		String sql = " SELECT * FROM TOPICS WHERE  LOWER(topic_name) = ? ";
		List<String> args = new ArrayList<>();
		args.add(topic.getTopic_name());
		try {
			List<Topics> response = jdbcTemplate.query(sql, args.toArray(), new TopicExtractor());
			if (response != null && !response.isEmpty()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
