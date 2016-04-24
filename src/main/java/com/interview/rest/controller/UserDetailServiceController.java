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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.interview.mysqlDb.UserDetailService;
import com.interview.pojo.UserDetails;

@RestController
public class UserDetailServiceController {

	@Autowired
	private UserDetailService userDetailService;

	@RequestMapping(value = "/addUserDetail", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<UserDetails> addUserDetails(@RequestBody UserDetails user_Details)
			throws SQLException {
		UserDetails userDetails = null;
		userDetails = userDetailService.addUser(user_Details);
		return new ResponseEntity<UserDetails>(userDetails, HttpStatus.OK);
	}

	@RequestMapping(value = "/getUserDetail", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Map<Integer, UserDetails>> getUserDetails() throws SQLException {
		Map<Integer, UserDetails> userDetails = userDetailService.getUserDetails();
		return new ResponseEntity<Map<Integer, UserDetails>>(userDetails, HttpStatus.OK);
	}

	@RequestMapping(value = "/modifyUserDetailByEmail", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<UserDetails> modifyByEmail(@RequestBody UserDetails user_Details)
			throws SQLException {
		UserDetails userDetails = null;
		userDetails = userDetailService.modifyByEmail(user_Details);
		return new ResponseEntity<UserDetails>(userDetails, HttpStatus.OK);
	}

	@RequestMapping(value = "/activateDeactivateUser", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<String> activateDeactivateUser(@RequestParam String email, String status)
			throws SQLException {
		String userDetails = userDetailService.activateDeactivateUser(email, status);
		return new ResponseEntity<String>(userDetails, HttpStatus.OK);
	}
}
