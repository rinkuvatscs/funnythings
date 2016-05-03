package com.interview.rest.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interview.mysql.TopicService;
import com.interview.pojo.Topic;

@RestController
@RequestMapping(value = "/interviewservice/topicservice/")
public class TopicServiceController {

	public static final String INVALID_TOPIC = "Topic Name can not be Blank";
	public static final String TOPIC_SAVING_ERROR = "Sorry , Topic is not added ";

	@Autowired
	private TopicService mysqlDbService;

	@RequestMapping(value = "/addTopic", method = RequestMethod.POST)
	public ResponseEntity<String> addTopics(@RequestBody Topic topic) {
		String message = null;
		try {
			if (StringUtils.isEmpty(topic)
					&& StringUtils.isEmpty(topic.getTopicName())) {
				message = mysqlDbService.addTopics(topic);
			} else {
				message = INVALID_TOPIC;
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			message = TOPIC_SAVING_ERROR;
		}
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	@RequestMapping(value = "/getTopic", method = RequestMethod.GET)
	public ResponseEntity<List<Topic>> getTopics() {
		List<Topic> topicList = null;
		try {
			topicList = mysqlDbService.getTopic();

			if (topicList == null)
				topicList = new ArrayList<Topic>();

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			topicList = new ArrayList<Topic>();
		}
		return new ResponseEntity<List<Topic>>(topicList, HttpStatus.OK);
	}

	@RequestMapping(value = "/getTopicByTopicName", method = RequestMethod.PUT)
	public ResponseEntity<Topic> getTopicByName(@RequestParam String topicName) {
		Topic topic = null;
		try {
			if (!StringUtils.isEmpty(topicName)) {
				topic = mysqlDbService.getTopicByName(topicName.toLowerCase());
			} else {
				topic = new Topic();
			}

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			topic = new Topic();
		}
		return new ResponseEntity<Topic>(topic, HttpStatus.OK);
	}

	@RequestMapping(value = "/modifyTopicByTopicName", method = RequestMethod.PUT)
	public ResponseEntity<Topic> modifyByTopicName(
			@RequestParam String oldTopicName, @RequestParam String newTopicName) {
		Topic topic = null;
		try {
			if (StringUtils.isEmpty(oldTopicName)
					&& StringUtils.isEmpty(newTopicName)) {
				topic = mysqlDbService.modifyByTopicName(oldTopicName,
						newTopicName);
			} else {
				topic = new Topic();
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			topic = new Topic();
		}
		return new ResponseEntity<Topic>(topic, HttpStatus.OK);
	}
}
