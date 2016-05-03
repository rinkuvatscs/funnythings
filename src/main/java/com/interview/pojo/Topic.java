package com.interview.pojo;

public class Topic {

	private int topicId;

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	private String topicName;

	public Topic() {
		super();
	}

	public Topic(int topic_id, String topic_name) {
		super();
		this.topicId = topic_id;
		this.topicName = topic_name;
	}

}
