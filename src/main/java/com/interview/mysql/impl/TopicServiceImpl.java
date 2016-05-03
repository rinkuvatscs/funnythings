package com.interview.mysql.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.interview.constants.QueryConstants;
import com.interview.extractor.TopicExtractor;
import com.interview.mysql.TopicService;
import com.interview.pojo.Topic;

@Repository
public class TopicServiceImpl implements TopicService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public String addTopics(Topic topic) throws SQLException {
// NEED TO VERIFY ITS PENDING
		if (!isExist(topic)) {
			String sql = QueryConstants.ADDTOPIC;
			List<String> args = new ArrayList<>();
			if (topic.getTopic_name() != null) {
				args.add(topic.getTopic_name().toLowerCase());
			}
			try {
				int response = jdbcTemplate.update(sql, args.toArray());
				if (response > 0) {
					Topic topics = getTopicByName(topic.getTopic_name());
					return "Successfully Added topic id : "
							+ topics.getTopic_id() + " topic name : "
							+ topics.getTopic_name();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "Successfully Added topic id : " + topic.getTopic_id()
					+ " topic name : " + topic.getTopic_name();
		} else {
			Topic tempTopic = getTopicByName(topic.getTopic_name());
			return "Topic Already Exist topic id : " + tempTopic.getTopic_id()
					+ " topic name : " + tempTopic.getTopic_name();
		}

	}

	@Override
	public List<Topic> getTopic() throws SQLException {
//DONE
		List<Topic> listTopic = null;

		try {
			listTopic = jdbcTemplate.query(QueryConstants.GETTOPIC,
					new TopicExtractor());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (listTopic == null) {
			return new ArrayList<Topic>();
		} else {
			return listTopic;
		}
	}

	@Override
	public Topic getTopicByName(String name) throws SQLException {
//DONE
		Topic topicResponse = null ;
		List<Topic> responseList = null;
		List<String> args = new ArrayList<>();
		args.add(name.toLowerCase());
		try {
			responseList = jdbcTemplate.query(QueryConstants.GETTOPICBYNAME, args.toArray(),
					new TopicExtractor());
			if(responseList!=null && !responseList.isEmpty()){
				topicResponse = responseList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			responseList = null ;
		}
		
		
		if(topicResponse != null ) {
			return topicResponse ;
		}else {
			return new Topic();
		}
	}

	@Override
	public Topic modifyByTopicName(String oldName, String newName)
			throws SQLException {
//NEED TO CHECL
		String sql = QueryConstants.MODIFYBYTOPICNAME;
		List<String> args = new ArrayList<>();
		args.add(newName.toLowerCase());
		args.add(oldName.toLowerCase());
		try {
			int response = jdbcTemplate.update(sql, args.toArray());
			if (response != 0) {
				Topic topic = getTopicByName(newName);
				return topic;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private boolean isExist(Topic topic) {
//NEED TO CHECK
		String sql = QueryConstants.ISEXIST;
		List<String> args = new ArrayList<>();
		args.add(topic.getTopic_name());
		try {
			List<Topic> response = jdbcTemplate.query(sql, args.toArray(),
					new TopicExtractor());
			if (response != null && !response.isEmpty()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
