package com.interview.mysql.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.interview.constants.QueryConstants;
import com.interview.mysql.InterviewService;
import com.interview.mysql.UserDetailService;
import com.interview.pojo.UserDetail;
import com.interview.util.FileProcessingUtil;

@Component
public class InterviewServiceImpl implements InterviewService {

	public String fileLocation = "d://interviewService//";
	public static final String FILE_SAVING_ERROR = "Errror in file saving for ";
	public static final String FILE_IS_BLANK = "Please add audio or video file ";
	public static final String USER_ADDING_ERROR = "Error in User Detail Adding ";
	public static final String INTERVIEW_SAVED = "Interview Saved succesfully ";
	public static final String INTERVIEW_NOT_SAVED = "Sorry, Interview not Saved succesfully for ";
	public static final String INTERVIEW_NOT_SAVE_ERROR = "Sorry , Error in Interview Saving for ";
	@Autowired
	UserDetailService userDetailServiceImpl;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public String addInterview(UserDetail userDetail, MultipartFile file) {
		String message = null;

		// Check USER DETAIL
		UserDetail addUserDetail = userDetailServiceImpl.addUser(userDetail);

		if (!StringUtils.isEmpty(addUserDetail) && addUserDetail.getUserId() != 0) {

			if (!StringUtils.isEmpty(file)) {
				boolean isFileSaved = FileProcessingUtil.fileSaved(file, fileLocation);
				if (isFileSaved) {
					// ADD DETAILS IN INTERVIEW_DETAIL TABLE
					List<String> args = new ArrayList<>();
					userDetail.setStatus("A");
					userDetail.setFileLocation(fileLocation);

					args.add(String.valueOf(userDetail.getUserId()));
					args.add(userDetail.getFileLocation());
					args.add(String.valueOf(userDetail.getStateId()));
					args.add(String.valueOf(userDetail.getCountryId()));
//					args.add(userDetail.getLocation());
					args.add(String.valueOf(userDetail.getTopicId()));
					args.add(userDetail.getStatus());
					try {
						int response = jdbcTemplate.update(QueryConstants.INTERVIEW_DETAIL_ADD, args.toArray());
						if (response != 0) {
							message = INTERVIEW_SAVED ;
						} else {
							message = INTERVIEW_NOT_SAVED + userDetail.getUserId();
						}
					} catch (Exception e) {
						e.printStackTrace();
						message = INTERVIEW_NOT_SAVE_ERROR + userDetail.getUserId();
					}

				} else {
					// ERROR IN FILE SAVING
					message = FILE_SAVING_ERROR;
				}
			} else {
				// FILE IS BLANK
				message = FILE_IS_BLANK;
			}
		} else {
			// ERROR IN USE ADDING
			message = USER_ADDING_ERROR;
		}

		return message;
	}

	@Override
	public String deleteInterview(UserDetail userDetail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String modifyInterviewDetails(UserDetail userDetail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDetail> getInterviewDetailByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, List<UserDetail>> getInterviewDetails() {
		// TODO Auto-generated method stub
		return null;
	}

}
