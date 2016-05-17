package com.interview.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.interview.mysql.StateService;
import com.interview.pojo.State;
import com.interview.util.MysqlOperations;

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
	
	@RequestMapping(value = "/getStateListByCountryId/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<State>> getStateListByCountryId(@PathVariable("id") int countryid){
		List<State> stateList = stateService.getStateListByCountryId(countryid);
		return new ResponseEntity<List<State>>(stateList, HttpStatus.OK);
	
	}
	


	@RequestMapping(value = "/deleteStateByStateName/{stateName}/byCountryId/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> deleteStateByStateName(@PathVariable("id") int countryid, @PathVariable("stateName") String stateName){
		String stateStatus = stateService.activateDeactivateStateByStateNameAndCountry_Id(MysqlOperations.DEACTIVATE,stateName,countryid);
		return new ResponseEntity<String>(stateStatus, HttpStatus.OK);
	
	}
	
	
	@RequestMapping(value = "/getStateCodeByStateNameAndCountryId/{countryId}/stateName/{stateName}", method = RequestMethod.GET)
	public ResponseEntity<Integer> getStateCodeByStateNameAndCountryId(@PathVariable("countryId") int countryid, @PathVariable("stateName") String stateName){
		int statecode = stateService.getStateCodeByStateNameAndCountryId(countryid, stateName);
		return new ResponseEntity<Integer>(statecode, HttpStatus.OK);
	
	}
	
	@RequestMapping(value = "/getStateNmaeByStateIdAndCountryId/{countryId}/stateId/{stateId}", method = RequestMethod.GET)
	public ResponseEntity<String> getStateNmaeByStateIdAndCountryId(@PathVariable("countryId") int countryid, @PathVariable("stateId") int stateId){
		String stateStatus = stateService.getStateNmaeByStateIdAndCountryId(countryid, stateId);
		return new ResponseEntity<String>(stateStatus, HttpStatus.OK);
	
	}
	
	@RequestMapping(value = "/modifyStateNamebyCountryId/{id}/oldStateName/{oldStateName}/newStateName/{newStateName}", method = RequestMethod.GET)
	public ResponseEntity<String> modifyStateName(@PathVariable("id") int countryid, @PathVariable("oldStateName") String oldStateName, @PathVariable("newStateName") String newStateName){
		String stateStatus = stateService.modifyStateName(countryid, oldStateName, newStateName);
		return new ResponseEntity<String>(stateStatus, HttpStatus.OK);
	
	}
	
}
