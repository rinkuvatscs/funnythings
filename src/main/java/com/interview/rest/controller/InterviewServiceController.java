package com.interview.rest.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.interview.pojo.UserDetail;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
public class InterviewServiceController {

	HashMap<String, List<UserDetail>> cache = new HashMap<String, List<UserDetail>>();

	@RequestMapping("/welcome")
	public String welcomeEmployee() {
		return "Welcome To Employee Order Management System ";
	}

	@ApiOperation(value = "getGreeting", nickname = "getGreeting")
	@RequestMapping(value = "/addSabha", method = RequestMethod.POST)
	public @ResponseBody String handleUploadFile(@RequestParam String firstName,
			@RequestParam String lastName, @RequestParam String emailAddress,
			@RequestParam String location, @RequestParam String mobileNum,
			@RequestParam String topic, @RequestParam MultipartFile file) {

		UserDetail userDetail = new UserDetail();
		userDetail.setFirstName(firstName);
		userDetail.setLastName(lastName);
		userDetail.setEmailAddress(emailAddress);
		userDetail.setLocation(location);
		userDetail.setMobileNum(mobileNum);
		userDetail.setTopic(topic);

		if (null != file && !file.isEmpty()) {
			FileOutputStream fileOutputStream = null;
			String uploadedFileLocation = "D:\\Interview\\";
			String fileLocation = uploadedFileLocation
					+ file.getOriginalFilename();
			try {

				byte[] bytes = file.getBytes();

				fileOutputStream = new FileOutputStream(fileLocation);
				fileOutputStream.write(bytes);

				if (fileOutputStream != null) {
					fileOutputStream.flush();
				}

			} catch (Exception e) {
				return "You failed to upload => " + e.getMessage();
			} finally {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			// ADDING VALUES INTO DATABASE
			if (cache.containsKey(emailAddress)) {
				cache.get(emailAddress).add(userDetail);
			} else {
				List<UserDetail> userDetails = new ArrayList<UserDetail>();
				userDetails.add(userDetail);
				cache.put(emailAddress, userDetails);
			}
			return "You successfully uploaded  !";
		} else {
			return "You failed to upload because the file was empty.";
		}
	}
	
	@ApiOperation(value = "getSabhaByEmailAddress", nickname = "getSabhaByEmailAddress")
	@RequestMapping(value = "/getSabhaByEmailAddress", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<UserDetail>> getSabhaByEmailAddress(
			@RequestParam String emailAddress) {
		List<UserDetail> userDetails = null;
	//	String emailAddress = "rinkuvatscs@gmail.com";
		userDetails = cache.get(emailAddress);
		return new ResponseEntity<List<UserDetail>>(userDetails,HttpStatus.OK);

	}

}
