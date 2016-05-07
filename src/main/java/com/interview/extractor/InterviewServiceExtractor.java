package com.interview.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.interview.pojo.InterviewDetail;

public class InterviewServiceExtractor implements ResultSetExtractor<List<InterviewDetail>> {

	@Override
	public List<InterviewDetail> extractData(ResultSet rs) throws SQLException, DataAccessException {

		List<InterviewDetail> interviewDetails = new ArrayList<InterviewDetail>();
		InterviewDetail interviewDetail = null;
		while (rs.next()) {
			interviewDetail = new InterviewDetail();
			interviewDetail.setUserId(rs.getInt("userid"));
			interviewDetail.setInterviewId(rs.getInt("interview_id"));
			interviewDetail.setFileLocation(rs.getString("file_location"));
			interviewDetail.setLocation(rs.getString("location"));
			interviewDetail.setStateId(rs.getString("state_id"));
			interviewDetail.setStatus(rs.getString("status"));
			interviewDetail.setTopicId(rs.getString("topic_id"));
			interviewDetail.setCountryId(rs.getString("country_id"));
			interviewDetails.add(interviewDetail);
		}
		return interviewDetails;
	}

}
