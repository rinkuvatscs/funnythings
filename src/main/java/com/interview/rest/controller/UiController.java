package com.interview.rest.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
import com.interview.util.ExceptionCodes;
import com.interview.util.MysqlOperations;
import com.mysql.jdbc.StringUtils;

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
			@RequestParam String email, @RequestParam String location) {

		List<UserDetail> fileLocation = interviewServiceImpl.fileSearch(fName, lName, email, mNo, location);
		return new ModelAndView("FileSearchResult").addObject("fileLoc", fileLocation);
	}

	@RequestMapping(value = "/mobileSearchResult")
	public ModelAndView mobileSearchResult(@RequestParam String fName, @RequestParam String lName,
			@RequestParam String mNo, @RequestParam String email, @RequestParam String location) {

		List<UserDetail> fileLocation = interviewServiceImpl.fileSearch(fName, lName, email, mNo, location);
		File file = new File(fileLocation.get(0).getFileLocation());
		System.out.println(file);
		FileInputStream fin = null;

		try {
			fin = new FileInputStream(file);
			byte fileContent[] = new byte[(int) file.length()];
			fin.read(fileContent);
			String s = new String(fileContent);
			System.out.println("File content: " + s);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ModelAndView("FileSearchResult").addObject("fileLoc", fileLocation);
	}

	private List<Country> getCountryList() {

		List<Country> countryList = null;
		countryList = countryServiceImpl.getCountry();

		if (countryList == null) {
			countryList = new ArrayList<Country>();
		}
		return countryList;
	}

	@RequestMapping(value = "/viewCountry")
	public ModelAndView addCountry() {

		return new ModelAndView("countryServiceOperation").addObject("countryName", getCountryList());
	}

	@RequestMapping(value = "/CountryAction")
	public ModelAndView CountryAction(@RequestParam String countryName, @RequestParam("radiobtn") String operation) {

		Country country = null;
		String cntry = null;
		if (operation.equals("Add")) {
			if (!StringUtils.isNullOrEmpty(countryName)) {
				country = new Country();
				country.setCountryName(countryName);
				cntry = countryServiceImpl.addCountry(country);
			}
		} else if (operation.equals("Delete")) {
			cntry = countryServiceImpl.activateDeactivateCountryByCountryName(MysqlOperations.DEACTIVATE, countryName);
		} else {
			cntry = "Please provide operation name.";
		}

		if (StringUtils.isNullOrEmpty(cntry)) {
			return new ModelAndView("countryServiceOperation").addObject("countryName",
					"Country not added, please try again.");
		}
		return new ModelAndView("countryServiceOperation").addObject("msg", cntry).addObject("countryName",
				getCountryList());
	}

	@ExceptionHandler(Exception.class)
	@RequestMapping(value = "/error")
	public ModelAndView error(@RequestParam Exception exception) {

		ModelAndView mav = new ModelAndView();
		if (exception instanceof SQLException) {
			mav.addObject("exception", ExceptionCodes.INTERVIEWSERVICE_SQL);
		} else if (exception instanceof NullPointerException) {
			mav.addObject("exception", ExceptionCodes.INTERVIEWSERVICE_NULL);
		} else if (exception instanceof MissingServletRequestParameterException) {
			mav.addObject("exception", ExceptionCodes.INTERVIEWSERVICE_MISSING_PARAMETER);
		} else if (exception instanceof FileNotFoundException) {
			mav.addObject("exception", ExceptionCodes.INTERVIEWSERVICE_FILEMISSING);
		}
		mav.setViewName("errorPage");
		return mav;
	}
}
