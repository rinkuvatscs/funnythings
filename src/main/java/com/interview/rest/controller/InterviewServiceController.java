package com.interview.rest.controller;

import io.swagger.annotations.ApiOperation;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import com.interview.pojo.UserDetail;
import com.interview.util.FileProcessingUtil;

@RestController
@RequestMapping(value = "/api/interviewservice/interviewservice/")
public class InterviewServiceController {

	HashMap<String, List<UserDetail>> cache = new HashMap<String, List<UserDetail>>();

	@RequestMapping("/welcome")
	public String welcomeEmployee() {
		return "Welcome To Employee Order Management System ";
	}

	@ApiOperation(value = "addSabha", nickname = "addSabha")
	@RequestMapping(value = "/addSabha", method = RequestMethod.POST)
	public @ResponseBody String handleUploadFile(
			@RequestBody UserDetail userDetail, @RequestPart MultipartFile file) {
		String message = null;

		if (null != file && !file.isEmpty()) {
			if (FileProcessingUtil.fileSaved(file)) {
				message = "Added File ";
			} else {
				message = "Not Added File ";
			}

		} else {
			message = "You failed to upload because the file was empty.";
		}
		return message;
	}

	@ApiOperation(value = "getSabhaByEmailAddress", nickname = "getSabhaByEmailAddress")
	@RequestMapping(value = "/getSabhaByEmailAddress", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<UserDetail>> getSabhaByEmailAddress(
			@RequestParam String emailAddress) {
		List<UserDetail> userDetails = null;
		// String emailAddress = "rinkuvatscs@gmail.com";
		userDetails = cache.get(emailAddress);
		return new ResponseEntity<List<UserDetail>>(userDetails, HttpStatus.OK);

	}

}
