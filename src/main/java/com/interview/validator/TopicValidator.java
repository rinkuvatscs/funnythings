package com.interview.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.interview.pojo.Topic;

@Component
public class TopicValidator {

	public String topicValidNew(Topic topic) {
		String response = null;
		if (StringUtils.isEmpty(topic) && StringUtils.isEmpty(topic.getTopic_name())) {
			response = "Topic Name can not be Blank";
		}

		return response;
	}

	public String getTopicsByName(String name) {
		if (name != null && name != "" && !name.isEmpty()) {
			String topic_name = name.toLowerCase().trim();
			return topic_name;
		}
		return null;
	}

}
