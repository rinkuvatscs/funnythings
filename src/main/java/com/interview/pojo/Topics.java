package com.interview.pojo;

public class Topics {

	private int topic_id;
	private String topic_name;

	public Topics() {
		super();
	}

	public Topics(int topic_id, String topic_name) {
		super();
		this.topic_id = topic_id;
		this.topic_name = topic_name;
	}

	public int getTopic_id() {
		return topic_id;
	}

	public void setTopic_id(int topic_id) {
		this.topic_id = topic_id;
	}

	public String getTopic_name() {
		return topic_name;
	}

	public void setTopic_name(String topic_name) {
		this.topic_name = topic_name;
	}

}
