package com.interview.rest.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.mysql.InterviewService;
import com.interview.pojo.UserDetail;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/interviewservice/interviewservice/")
public class InterviewServiceController {

	@Autowired
	InterviewService interviewServiceImpl;

	@RequestMapping("/")
	public String welcomeEmployee() {
		return "Interview-Service-Controller";
	}

	@ApiOperation(value = "addInterview", nickname = "addSabha")
	@RequestMapping(value = "/addInterview", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> handleUploadFile(@RequestPart String userDetailStr,
			@RequestPart("file") MultipartFile file) throws JsonParseException, JsonMappingException, IOException {
		UserDetail userDetail = new ObjectMapper().readValue(userDetailStr, UserDetail.class);

		return new ResponseEntity<String>(interviewServiceImpl.addInterview(userDetail, file), HttpStatus.OK);
	}

	@ApiOperation(value = "getSabhaByEmailAddress", nickname = "getSabhaByEmailAddress")
	@RequestMapping(value = "/getSabhaByEmailAddress", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<UserDetail>> getSabhaByEmailAddress(@RequestParam String emailAddress) {
		List<UserDetail> userDetails = null;
		// String emailAddress = "rinkuvatscs@gmail.com";
		return new ResponseEntity<List<UserDetail>>(userDetails, HttpStatus.OK);

	}

	@ApiOperation(value = "deleteByEmailOrMobile", nickname = "deleteByEmailOrMobile")
	@RequestMapping(value = "/deleteByEmailOrMobile", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> deleteByEmailOrMobile(@RequestParam String email,
			@RequestParam String mobile) {

		return new ResponseEntity<String>(interviewServiceImpl.deleteInterviewDetail(email, mobile), HttpStatus.OK);

	}

}
