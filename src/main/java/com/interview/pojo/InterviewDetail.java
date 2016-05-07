package com.interview.pojo;

public class InterviewDetail {

	private int interviewId;
	private int userId;
	private String fileLocation;
	private String stateId;
	private String countryId;
	private String topicId;
	private String location;
	private String status;

	public InterviewDetail() {
		super();
	}

	public InterviewDetail(int interviewId, int userId, String fileLocation, String stateId, String countryId,
			String topicId, String location, String status) {
		super();
		this.interviewId = interviewId;
		this.userId = userId;
		this.fileLocation = fileLocation;
		this.stateId = stateId;
		this.countryId = countryId;
		this.topicId = topicId;
		this.location = location;
		this.status = status;
	}

	public int getInterviewId() {
		return interviewId;
	}

	public void setInterviewId(int interviewId) {
		this.interviewId = interviewId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
