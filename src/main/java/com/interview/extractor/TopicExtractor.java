package com.interview.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.interview.pojo.Topics;

public class TopicExtractor implements ResultSetExtractor<List<Topics>> {

	@Override
	public List<Topics> extractData(ResultSet rs) throws SQLException, DataAccessException {

		List<Topics> topicList = new ArrayList<Topics>();
		Topics topics = null;
		while(rs.next()){
			topics = new Topics();
			topics.setTopic_id(rs.getInt("topic_id"));
			topics.setTopic_name(rs.getString("topic_name"));
			topicList.add(topics);
		}
		return topicList;
	}

}
