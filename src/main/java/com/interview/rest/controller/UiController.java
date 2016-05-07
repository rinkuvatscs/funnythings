package com.interview.rest.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping(value = "/interviewservice/UiController/")
public class UiController {

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
			if (topicList == null) {
				topicList = new ArrayList<Topic>();
			}
			if (countryList == null) {
				countryList = new ArrayList<Country>();
			}
			request.setAttribute("countryList", countryList);

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			topicList = new ArrayList<Topic>();
		}

		return new ModelAndView("userDetailsUi").addObject("topicList", topicList);
	}

	@RequestMapping(value = "/userDetailData", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public ModelAndView getUserData(@RequestParam String fName, @RequestParam String lName, @RequestParam String mNo,
			@RequestParam String email, @RequestParam("tName") String topicId, @RequestParam("cName") String countryId,
			@RequestParam String location, @RequestParam MultipartFile file) {

		String response = null;
		UserDetail userDetail = new UserDetail();
		userDetail.setFirstName(fName);
		userDetail.setLastName(lName);
		userDetail.setMobileNum(mNo);
		userDetail.setEmailAddress(email);
		userDetail.setCountryId(Integer.parseInt(countryId));
		userDetail.setTopicId(Integer.parseInt(topicId));
		userDetail.setLocation(location);
		response = interviewServiceImpl.addInterview(userDetail, file);
		return new ModelAndView("userDetailsUi").addObject("result", response);
	}

	@RequestMapping(value = "/searchFile")
	public ModelAndView searchFile() {
		return new ModelAndView("FileSearch");
	}

	@RequestMapping(value = "/searchResult")
	public ModelAndView searchResult(@RequestParam String fName, @RequestParam String lName, @RequestParam String mNo,
			@RequestParam String email, @RequestParam String location, HttpServletRequest request,
			HttpServletResponse response) {

		List<String> fileLocation = interviewServiceImpl.fileSearch(fName, lName, email, mNo, location);
		request.setAttribute("fileLoc", fileLocation);
		return new ModelAndView("FileSearchResult").addObject("fname", fName);
	}

}
