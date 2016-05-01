package com.interview.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interview.mysql.StateService;

@RestController
@RequestMapping(value = "/api/interviewservice/stateservice/")
public class StateServiceController {

	@Autowired
	public StateService stateService;
	
	@RequestMapping("/")
	public String test() {
		return "state-Service-Contorller";
	}
}
