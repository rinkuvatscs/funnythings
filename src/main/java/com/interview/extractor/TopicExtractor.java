package com.interview.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.interview.pojo.Topic;

public class TopicExtractor implements ResultSetExtractor<List<Topic>> {

	@Override
	public List<Topic> extractData(ResultSet rs) throws SQLException, DataAccessException {

		List<Topic> topicList = new ArrayList<Topic>();
		Topic topics = null;
		while(rs.next()){
			topics = new Topic();
			topics.setTopic_id(rs.getInt("topic_id"));
			topics.setTopic_name(rs.getString("topic_name"));
			topicList.add(topics);
		}
		return topicList;
	}

}
