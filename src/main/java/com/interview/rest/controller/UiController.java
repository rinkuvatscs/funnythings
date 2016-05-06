package com.interview.rest.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.interview.mysql.CountryService;
import com.interview.mysql.InterviewService;
import com.interview.mysql.TopicService;
import com.interview.pojo.Country;
import com.interview.pojo.Topic;
import com.interview.pojo.UserDetail;
import com.interview.util.FileProcessingUtil;

@RestController
@RequestMapping(value = "/interviewservice/UiController/")
public class UiController {

	private static final String NOINPUT = "All parameters are required.";

	public String fileLocation = "d://interviewService//";
	@Autowired
	private TopicService mysqlDbService;

	@Autowired
	private CountryService countryServiceImpl;

	@Autowired
	InterviewService interviewServiceImpl;

	@RequestMapping(value = "/userDetail")
	public ModelAndView userDetailUi(HttpServletRequest request, HttpServletResponse response) {

		List<Topic> topicList = null;
		List<Country> countryList = null;
		try {
			topicList = mysqlDbService.getTopic();

			request.setAttribute("msgList", topicList);

			countryList = countryServiceImpl.getCountry();

			if (topicList == null)
				topicList = new ArrayList<Topic>();

			if (countryList == null)
				countryList = new ArrayList<Country>();
			request.setAttribute("countryList", countryList);

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			topicList = new ArrayList<Topic>();
		}

		return new ModelAndView("userDetailsUi").addObject("topicList", topicList);
	}

	@RequestMapping(value = "/userDetailData", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public ModelAndView getUserData(@RequestParam String fName, @RequestParam String lName, @RequestParam String mNo,
			@RequestParam String email, @RequestParam String location, @RequestParam("tName") String topicId,
			@RequestParam("cName") String countryId, @RequestParam MultipartFile file) {

		String response = null;
		FileProcessingUtil.fileSaved(file, fileLocation + file.getOriginalFilename());
		UserDetail userDetail = new UserDetail();

		userDetail.setFirstName(fName);
		userDetail.setLastName(lName);
		userDetail.setMobileNum(mNo);
		userDetail.setEmailAddress(email);
		userDetail.setCountryId(Integer.parseInt(countryId));
		userDetail.setTopicId(Integer.parseInt(topicId));
		userDetail.setLocation(location);

		response = interviewServiceImpl.addInterview(userDetail, file);
		FileProcessingUtil.fileSaved(file, fileLocation);
		return new ModelAndView("userDetailsUi").addObject("result", response);
	}
}
