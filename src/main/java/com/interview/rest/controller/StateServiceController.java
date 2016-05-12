package com.interview.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.interview.mysql.StateService;

@RestController
@RequestMapping(value = "/interviewservice/stateservice/")
public class StateServiceController {

	@Autowired
	public StateService stateService;
	
	@RequestMapping("/")
	public String test() {
		return "state-Service-Contorller";
	}
	
	@RequestMapping(value = "/addState/{stateName}/byCountryId/{id}", method = RequestMethod.POST)
	public ResponseEntity<String> addStateByCountryId(@PathVariable("id") int countryid, @PathVariable("stateName") String stateName){
		String status = stateService.addState(countryid, stateName);
		
		return new ResponseEntity<String>(status, HttpStatus.OK);

		
	}
	
}
