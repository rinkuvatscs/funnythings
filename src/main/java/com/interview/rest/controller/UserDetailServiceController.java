package com.interview.rest.controller;

import java.sql.SQLException;
import java.util.Map;

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

import com.interview.mysqlDb.UserDetailService;
import com.interview.pojo.UserDetail;

@RestController
@RequestMapping(value = "/api/interviewservice/userdetailservice")
public class UserDetailServiceController {

	@Autowired
	private UserDetailService userDetailService;

	@RequestMapping(value = "/addUserDetail", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<UserDetail> addUserDetails(@RequestBody UserDetail user_Details, @RequestPart MultipartFile file)
			throws SQLException {
		UserDetail userDetail = null;
		userDetail = userDetailService.addUser(user_Details);
		return new ResponseEntity<UserDetail>(userDetail, HttpStatus.OK);
	}

	@RequestMapping(value = "/getUserDetail", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Map<Integer, UserDetail>> getUserDetails() throws SQLException {
		Map<Integer, UserDetail> userDetails = userDetailService.getUserDetails();
		return new ResponseEntity<Map<Integer, UserDetail>>(userDetails, HttpStatus.OK);
	}

	@RequestMapping(value = "/modifyUserDetailByEmail", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<UserDetail> modifyByEmail(@RequestBody UserDetail user_Details)
			throws SQLException {
		UserDetail userDetails = null;
		userDetails = userDetailService.modifyByEmail(user_Details);
		return new ResponseEntity<UserDetail>(userDetails, HttpStatus.OK);
	}

	@RequestMapping(value = "/activateDeactivateUser", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<String> activateDeactivateUser(@RequestParam String email, String status)
			throws SQLException {
		String userDetails = userDetailService.activateDeactivateUser(email, status);
		return new ResponseEntity<String>(userDetails, HttpStatus.OK);
	}
}
