package com.interview.mysql.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.interview.constants.QueryConstants;
import com.interview.extractor.TopicExtractor;
import com.interview.mysql.TopicService;
import com.interview.pojo.Topic;

@Repository
public class TopicServiceImpl implements TopicService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	public static final String TOPIC_SAVING_ERROR = "Sorry , Topic is not saved ";
	public static final String TOPIC_ADDED_ERROR = "Sorry , Topic is not added ";

	@Override
	public String addTopics(Topic topic) throws SQLException {
		String response = null;
		if (!isExist(topic)) {
			List<String> args = new ArrayList<>();
			args.add(topic.getTopicName().toLowerCase());
			try {
				int result = jdbcTemplate.update(QueryConstants.ADDTOPIC,
						args.toArray());
				if (result > 0) {
					Topic topics = getTopicByName(topic.getTopicName());
					response = "Successfully Added topic id : "
							+ topics.getTopicId() + " topic name : "
							+ topics.getTopicName();
				} else {
					response = TOPIC_SAVING_ERROR;
				}
			} catch (Exception e) {
				e.printStackTrace();
				response = TOPIC_ADDED_ERROR;
			}
		} else {
			Topic tempTopic = getTopicByName(topic.getTopicName());
			response = "Topic Already Exist topic id : "
					+ tempTopic.getTopicId() + " Topic name : "
					+ tempTopic.getTopicName();
		}
		return response;
	}

	@Override
	public List<Topic> getTopic() throws SQLException {
		List<Topic> listTopic = null;

		try {
			listTopic = jdbcTemplate.query(QueryConstants.GETTOPIC,
					new TopicExtractor());
		} catch (Exception e) {
			e.printStackTrace();
			listTopic = new ArrayList<Topic>();
		}
		if (listTopic == null) {
			listTopic = new ArrayList<Topic>();
		} else {
			return listTopic;
		}
		return listTopic;
	}

	@Override
	public Topic getTopicByName(String name) throws SQLException {
		Topic topicResponse = null;
		List<Topic> responseList = null;
		List<String> args = new ArrayList<>();
		args.add(name.toLowerCase());
		try {
			responseList = jdbcTemplate.query(QueryConstants.GETTOPICBYNAME,
					args.toArray(), new TopicExtractor());
			if (responseList != null && !responseList.isEmpty()) {
				topicResponse = responseList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			responseList = null;
		}

		if (topicResponse != null) {
			return topicResponse;
		} else {
			return new Topic();
		}
	}

	@Override
	public Topic modifyByTopicName(String oldName, String newName)
			throws SQLException {
		Topic topic = null;
		List<String> args = new ArrayList<>();
		args.add(newName.toLowerCase());
		args.add(oldName.toLowerCase());
		try {
			int response = jdbcTemplate.update(
					QueryConstants.MODIFYBYTOPICNAME, args.toArray());
			if (response != 0) {
				topic = getTopicByName(newName);
			} else {
				topic = new Topic();
			}
		} catch (Exception e) {
			e.printStackTrace();
			topic = new Topic();
		}

		return topic;
	}

	private boolean isExist(Topic topic) {
		boolean response = false;
		List<String> args = new ArrayList<>();
		args.add(topic.getTopicName());
		try {
			List<Topic> topicList = jdbcTemplate.query(QueryConstants.ISEXIST,
					args.toArray(), new TopicExtractor());
			if (!StringUtils.isEmpty(response)) {
				topic.setTopicId(topicList.get(0).getTopicId());
				response = true;
			} else {
				response = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = false;
		}

		return response;
	}

}
