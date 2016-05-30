package com.interview.validator;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.interview.extractor.StateExtractor;
import com.interview.mysql.impl.StateServiceImpl;
import com.interview.pojo.State;
import com.interview.util.MysqlOperations;
import com.interview.validateException.StateServiceValidationException;

@Component
@Aspect
public class StateServiceValidation {

	@Autowired
	private StateServiceImpl stateServiceImpl;
	
	public static final String STATE_EXIST = "select * from state where country_id = ? AND state_name = ? ";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/*@Pointcut("com.interview.mysql.StateService.addState() && args(countryId,stateName,..)")
	public void addState(int countryId, String stateName) {
		
	}*/
	
	//@Before(" execution (* com.interview.mysql.StateService.addState(..))")
	
	//@Before(" execution (* com.interview.mysql.impl.StateServiceImpl.addState(..))")

	//@Before("addState(countryId,stateName)")
	
	
	@Pointcut("execution(* com.interview.rest.controller.StateServiceController.addStateByCountryId(..)) && args(countryId,stateName,..))") 
	private void addStateByCountryId(int countryId, String stateName) {
	}
	
	@Before("addStateByCountryId(countryId,stateName)")
	public void addStateValidation(int countryId, String stateName) throws StateServiceValidationException{
		System.out.println("AOP Called");
		String response = null;

		State state = new State();
		state.setState_name(stateName);
		
		if(!StringUtils.isEmpty(stateName)){
			
			if(isStateExist(state,countryId)){
				response = state + " " + "state Is already exists";
				throw new StateServiceValidationException(response,HttpStatus.CONFLICT);
			}
		
		else{
			
			if (state.getStatus().equalsIgnoreCase("D")) {
				stateServiceImpl.activateDeactivateStateByStateNameAndCountry_Id(
						MysqlOperations.ACTIVATE, state.getState_name(),countryId);
				response = state + " " + "state Is Activated";
				
				throw new StateServiceValidationException(response,HttpStatus.CONFLICT);
			}
			
			
		}
		}else {
			response = "Please Check your state Name";
			throw new StateServiceValidationException(response,HttpStatus.CONFLICT);
		}
	}
	
	
	private boolean isStateExist(State state , int countryId) {

		boolean response = false;
		List<String> args = new ArrayList<>();
		args.add(String.valueOf(countryId));
		args.add(state.getState_name().toLowerCase());
		try {
			List<State> stateList = jdbcTemplate.query(STATE_EXIST,
					args.toArray(), new StateExtractor());
			if (!StringUtils.isEmpty(stateList) && stateList.size() < 0) {
				state.setState_id(stateList.get(0).getState_id());
				state.setStatus(stateList.get(0).getStatus());
				response = true;
			} else {
				response = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = false;
		}

		return response;
	}

	
}
