package com.interview.mysql;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.interview.pojo.UserDetail;

public interface InterviewService {

	public String addInterview(UserDetail userDetail, MultipartFile file);

	public String modifyInterviewDetails(UserDetail userDetail);

	public List<UserDetail> getInterviewDetailByUserId(int userId);

	public Map<Integer, List<UserDetail>> getInterviewDetails();

	public List<UserDetail> fileSearch(String fname, String lname, String email, String mobile, String location);

	public String deleteInterviewDetail(String email, String mobile);

}
