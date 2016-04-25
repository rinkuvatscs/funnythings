package com.interview.rest.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interview.mysqlDb.TopicService;
import com.interview.pojo.Topic;
import com.interview.validator.TopicValidator;

@RestController
public class TopicServiceController {

	@Autowired
	private TopicValidator topicValidator;
	@Autowired
	private TopicService mysqlDbService;

	@RequestMapping(value = "/addTopic", method = RequestMethod.PUT)
	public ResponseEntity<String> addTopics(@RequestBody Topic topic) throws SQLException {
		String message = null;
		message = topicValidator.topicValidNew(topic);
		if (message == null) {
			String response = mysqlDbService.addTopics(topic);
			return new ResponseEntity<String>(response,HttpStatus.OK);
		}
		return null;
	}

	@RequestMapping(value = "/getTopic", method = RequestMethod.GET)
	public ResponseEntity<List<Topic>> getTopics() throws SQLException {
		List<Topic> listTopic = mysqlDbService.getTopic();
		return new ResponseEntity<List<Topic>>(listTopic,HttpStatus.OK);
	}

	@RequestMapping(value = "/getTopicByTopicName", method = RequestMethod.PUT)
	public ResponseEntity<Topic> getTopicByName(@RequestParam String topicName) throws SQLException {
		String topic_name = topicValidator.getTopicsByName(topicName);
		if (topic_name != null) {
			Topic topics = mysqlDbService.getTopicByName(topic_name);
			return new ResponseEntity<Topic>(topics,HttpStatus.OK);
		}
		return null;
	}

	@RequestMapping(value = "/modifyTopicByTopicName", method = RequestMethod.PUT)
	public ResponseEntity<Topic> modifyByTopicName(@RequestParam String oldTopicName, @RequestParam String newTopicName) throws SQLException {
		String oldName = topicValidator.getTopicsByName(oldTopicName);
		String newName = topicValidator.getTopicsByName(newTopicName);
		if (oldName != null && newName != null) {
			Topic topics = mysqlDbService.modifyByTopicName(oldName, newName);
			return new ResponseEntity<Topic>(topics,HttpStatus.OK);
		}
		return null;
	}
}
