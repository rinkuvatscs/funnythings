package com.interview.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetail {

	public UserDetail(int userId, String firstName, String lastName, String mobileNum, String emailAddress,
			/*String location,*/ int topicId, String fileLocation, String status) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNum = mobileNum;
		this.emailAddress = emailAddress;
//		this.location = location;
		this.topicId = topicId;
		this.fileLocation = fileLocation;
		this.status = status;
	}

	public UserDetail() {

	}

	@JsonIgnore
	private int userId;
	private String firstName;
	private String lastName;
	private String mobileNum;
	private String emailAddress;
//	private String location;
	private int topicId;

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	private String fileLocation;
	private int countryId;
	private int stateId;

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	private String status;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/*public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}*/

	@Override
	public String toString() {
		return "UserDetail [firstName=" + firstName + ", lastName=" + lastName + ", mobileNum=" + mobileNum
				+ ", emailAddress=" + emailAddress +  ", topicId=" + topicId + "]";
	}

}
