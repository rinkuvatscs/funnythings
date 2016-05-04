package com.interview.rest.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.interview.mysql.CountryService;
import com.interview.mysql.TopicService;
import com.interview.pojo.Country;
import com.interview.pojo.Topic;

@RestController
@RequestMapping(value = "/interviewservice/UiController/")
public class UiController {

	@Autowired
	private TopicService mysqlDbService;
	
	@Autowired
	private CountryService countryServiceImpl;

	@RequestMapping(value = "/userDetail")
	public ModelAndView userDetailUi(HttpServletRequest request, HttpServletResponse response) {

		List<Topic> topicList = null;
		List<Country> countryList = null ;
		try {
			topicList = mysqlDbService.getTopic();

			request.setAttribute("msgList", topicList);

			countryList = countryServiceImpl.getCountry() ;
			
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
}
