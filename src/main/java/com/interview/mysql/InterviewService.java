package com.interview.mysql;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.interview.pojo.UserDetail;

public interface InterviewService {

	public String addInterview(UserDetail userDetail , MultipartFile file) ;
	
	public String deleteInterview(UserDetail userDetail) ;
	
	public String modifyInterviewDetails(UserDetail userDetail) ;
	
	public List<UserDetail> getInterviewDetailByUserId(int userId) ;
	
	public Map<Integer, List<UserDetail>> getInterviewDetails();
	
	
	
}
