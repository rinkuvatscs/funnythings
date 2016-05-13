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
import com.interview.extractor.InterviewServiceExtractor;
import com.interview.extractor.UserDetailExtractor;
import com.interview.mysql.InterviewService;
import com.interview.mysql.UserDetailService;
import com.interview.pojo.InterviewDetail;
import com.interview.pojo.UserDetail;
import com.interview.util.FileProcessingUtil;

@Component
public class InterviewServiceImpl implements InterviewService {

	public String FILELOCATION = "d://interviewService//";
	public static final String FILE_SAVING_ERROR = "Errror in file saving for ";
	public static final String FILE_IS_BLANK = "Please add audio or video file ";
	public static final String USER_ADDING_ERROR = "Error in User Detail Adding ";
	public static final String INTERVIEW_SAVED = "Interview Saved succesfully ";
	public static final String INTERVIEW_NOT_SAVED = "Sorry, Interview not Saved succesfully for ";
	public static final String INTERVIEW_NOT_SAVE_ERROR = "Sorry , Error in Interview Saving for ";
	public static final String INTERVIEW_FILE_LOCATION_ERROR = "Sorry , Error in file Location ";
	public static final String INTERVIEW_FIELDS_IS_BLANK = "Please provide input";

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
				boolean isFileSaved = FileProcessingUtil.fileSaved(file, FILELOCATION);
				if (isFileSaved) {
					// ADD DETAILS IN INTERVIEW_DETAIL TABLE
					List<String> args = new ArrayList<>();
					userDetail.setStatus("A");
					userDetail.setFileLocation(FILELOCATION + file.getOriginalFilename());

					args.add(String.valueOf(userDetail.getUserId()));
					args.add(userDetail.getFileLocation());
					args.add(String.valueOf(userDetail.getStateId()));
					args.add(String.valueOf(userDetail.getCountryId()));
					args.add(userDetail.getLocation());
					args.add(String.valueOf(userDetail.getTopicId()));
					args.add(userDetail.getStatus());
					try {
						int response = jdbcTemplate.update(QueryConstants.INTERVIEW_DETAIL_ADD, args.toArray());
						if (response != 0) {
							message = INTERVIEW_SAVED;
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
	public List<UserDetail> fileSearch(String fname, String lname, String email, String mobile, String location) {

		boolean isEmail = false;
		boolean isMobile = false;
		boolean isLocation = false;
		boolean isFname = false;
		List<String> args = new ArrayList<>();
		StringBuffer str = new StringBuffer();
		List<UserDetail> response = new ArrayList<>();

		if (!StringUtils.isEmpty(email)) {
			str.append(" WHERE email = ? ");
			args.add(email);
			isEmail = true;
		}
		if (isEmail) {
			if (!StringUtils.isEmpty(mobile)) {
				str.append(" AND mobile = ? ");
				args.add(mobile);
				isMobile = true;
			}
		} else {
			if (!StringUtils.isEmpty(mobile)) {
				str.append(" where mobile = ? ");
				args.add(mobile);
				isMobile = true;
			}
		}
		if (isMobile) {
			if (!StringUtils.isEmpty(location)) {
				str.append(" AND location = ? ");
				args.add(location);
				isLocation = true;
			} else {
				// Location is Empty.
			}
		} else {
			if (!StringUtils.isEmpty(location)) {
				str.append(" where location = ? ");
				args.add(location);
				isLocation = true;
			}
		}

		if (isLocation) {
			if (!StringUtils.isEmpty(fname)) {
				str.append(" OR firstname = ? ");
				args.add(fname);
				isFname = true;
			}
		} else {
			if (!StringUtils.isEmpty(fname)) {
				str.append(" WHERE firstname = ? ");
				args.add(fname);
				isFname = true;
			}
		}
		if (isFname) {
			if (!StringUtils.isEmpty(lname)) {
				str.append(" OR lastname = ? ");
				args.add(lname);
			}
		} else {
			if (!StringUtils.isEmpty(lname)) {
				str.append(" WHERE lastname = ? ");
				args.add(lname);
			}
		}
		List<UserDetail> interviewDetailList = jdbcTemplate.query(QueryConstants.INTERVIEW_DETAIL_SEARCH_FILE + str,
				args.toArray(), new UserDetailExtractor());
		if (!StringUtils.isEmpty(interviewDetailList) && interviewDetailList.size() > 0) {
			for (int i = 0; i < interviewDetailList.size(); i++) {
				response.add(interviewDetailList.get(i));
			}
		} else {
			// response = INTERVIEW_FILE_LOCATION_ERROR;
			// response.add(INTERVIEW_FILE_LOCATION_ERROR);
		}
		/*
		 * } else { response.add(); }
		 */

		return response;
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

	@Override
	public String deleteInterviewDetail(String email, String mobile) {

		StringBuffer str = new StringBuffer(" SELECT * FROM USER_DETAILS ");
		List<String> args = new ArrayList<>();
		List<String> args1 = new ArrayList<>();
		List<String> args2 = new ArrayList<>();
		List<String> args3 = new ArrayList<>();
		String response = null;
		if (!StringUtils.isEmpty(email) || !StringUtils.isEmpty(mobile)) {
			if (!StringUtils.isEmpty(email)) {
				str.append(" WHERE email = ? ");
				args.add(email);
				if (!StringUtils.isEmpty(mobile)) {
					str.append(" AND mobile = ? ");
					args.add(mobile);
				}
			} else {
				if (!StringUtils.isEmpty(mobile)) {
					str.append(" WHERE mobile = ? ");
					args.add(email);
				} else {
					response = INTERVIEW_FIELDS_IS_BLANK;
				}
			}
		} else {
			response = INTERVIEW_FIELDS_IS_BLANK;
		}

		if (!args.isEmpty()) {

			List<UserDetail> userDetailList = jdbcTemplate.query(str.toString(), args.toArray(),
					new UserDetailExtractor());
			if (userDetailList.size() > 0 && userDetailList.get(0) != null) {
				int userId = userDetailList.get(0).getUserId();
				int topicId = userDetailList.get(0).getTopicId();
				String name = userDetailList.get(0).getFirstName();

				args1.add(String.valueOf(userId));
				args1.add(String.valueOf(topicId));

				List<InterviewDetail> interviewDetailList = jdbcTemplate.query(QueryConstants.INTERVIEW_DETAIL_SELECT,
						args1.toArray(), new InterviewServiceExtractor());
				if (interviewDetailList.size() > 0 && interviewDetailList.get(0) != null) {
					args2.add(interviewDetailList.get(0).getCountryId());
					args2.add(interviewDetailList.get(0).getFileLocation());
					args2.add(String.valueOf(interviewDetailList.get(0).getInterviewId()));
					args2.add(interviewDetailList.get(0).getLocation());
					args2.add(interviewDetailList.get(0).getStateId());
					args2.add(interviewDetailList.get(0).getStatus());
					args2.add(interviewDetailList.get(0).getTopicId());
					args2.add(String.valueOf(interviewDetailList.get(0).getUserId()));

					int responseGet = jdbcTemplate.update(QueryConstants.INTERVIEW_DETAIL_DELETE, args2.toArray());
					if (responseGet > 0) {
						args3.add(String.valueOf(interviewDetailList.get(0).getUserId()));
						args3.add(interviewDetailList.get(0).getTopicId());
						int deleteResponse = jdbcTemplate.update(QueryConstants.INTERVIEW_DELETE, args3.toArray());
						if (deleteResponse > 0) {
							response = name + " your details has been deleted Successfully. ";
						}
					}
				}
			}
		}

		return response;
	}
}
