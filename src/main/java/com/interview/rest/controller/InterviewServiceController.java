package com.interview.rest.controller;

import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.interview.mysql.InterviewService;
import com.interview.pojo.UserDetail;

@RestController
@RequestMapping(value = "/interviewservice/interviewservice/")
public class InterviewServiceController {

	@Autowired
	InterviewService interviewServiceImpl;

	@RequestMapping("/")
	public String welcomeEmployee() {
		return "Interview-Service-Controller";
	}

	@ApiOperation(value = "addSabha", nickname = "addSabha")
	@RequestMapping(value = "/addSabha", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> handleUploadFile(
			@RequestBody UserDetail userDetail, @RequestPart MultipartFile file) {
		return new ResponseEntity<String>(interviewServiceImpl.addInterview(
				userDetail, file), HttpStatus.OK);
	}

	@ApiOperation(value = "getSabhaByEmailAddress", nickname = "getSabhaByEmailAddress")
	@RequestMapping(value = "/getSabhaByEmailAddress", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<UserDetail>> getSabhaByEmailAddress(
			@RequestParam String emailAddress) {
		List<UserDetail> userDetails = null;
		// String emailAddress = "rinkuvatscs@gmail.com";
		return new ResponseEntity<List<UserDetail>>(userDetails, HttpStatus.OK);

	}

}
