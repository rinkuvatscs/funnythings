package com.interview.validator;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
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
import com.interview.mysql.StateService;
import com.interview.mysql.impl.StateServiceImpl;
import com.interview.pojo.State;
import com.interview.util.MysqlOperations;
import com.interview.validateException.StateServiceValidationException;

@Component
@Aspect
public class StateServiceValidation {

	@Autowired
	StateService stateServiceImpl;

	public static final String STATE_EXIST = "select * from state where country_id = ? AND state_name = ? ";

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Pointcut("execution(* com.interview.mysql.impl.StateServiceImpl.addState(..)) && args(countryId,stateName,..))")
	private void addStateByCountryId(int countryId, String stateName) {
	}

	@Pointcut("execution(* com.interview.mysql.impl.StateServiceImpl.modifyStateName(..)) && args(countryId,oldStateName,..))")
	private void modifyStateName(int countryId, String oldStateName) {
	}
	
	@Before("addStateByCountryId(countryId,stateName)")
	public void addStateValidation(int countryId, String stateName) throws StateServiceValidationException {
		System.out.println("AOP Called");
		String response = null;

		State state = new State();
		state.setState_name(stateName);

		if (!StringUtils.isEmpty(stateName)) {

			if (isStateExist(state, countryId)) {
				response = state + " " + "state Is already exists";
				throw new StateServiceValidationException(response, HttpStatus.CONFLICT);
			}

			else {

				if (state.getStatus().equalsIgnoreCase("D")) {
					stateServiceImpl.activateDeactivateStateByStateNameAndCountry_Id(MysqlOperations.ACTIVATE,
							state.getState_name(), countryId);
					response = state + " " + "state Is Activated";

					throw new StateServiceValidationException(response, HttpStatus.CONFLICT);
				}

			}
		} else {
			response = "Please Check your state Name";
			throw new StateServiceValidationException(response, HttpStatus.CONFLICT);
		}
	}

	@Around("execution(@com.interview.validator.LogDuration * *(..)) && @annotation(logDurationAnnotation)")
	public Object logDuration(ProceedingJoinPoint joinPoint, LogDuration logDurationAnnotation) throws Throwable {

		// capture the start time
		long startTime = System.currentTimeMillis();

		// execute the method and get the result
		Object result = joinPoint.proceed();

		// capture the end time
		long endTime = System.currentTimeMillis();

		// calculate the duration and print results
		long duration = endTime - startTime;

		System.out.println(logDurationAnnotation.value() + ": " + duration + "ms"); 
																					// use
																					// a
																					// logger

		// return the result to the caller
		return result;
	}

	private boolean isStateExist(State state, int countryId) {

		boolean response = false;
		List<String> args = new ArrayList<>();
		args.add(String.valueOf(countryId));
		args.add(state.getState_name().toLowerCase());
		try {
			List<State> stateList = jdbcTemplate.query(STATE_EXIST, args.toArray(), new StateExtractor());
			if (!StringUtils.isEmpty(stateList) && stateList.size() > 0) {
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

	@Before("modifyStateName(countryId,oldStateName)")
	public void modifyStateNameValidation(int countryId, String oldStateName) throws StateServiceValidationException {
		String response = null;

		State state = new State();
		state.setState_name(oldStateName);
		if (isStateExist(state, countryId)) {
			response = "State is exists";
			System.out.println(response);
		} else {
			response = "State is not exists" + " " + oldStateName;
			throw new StateServiceValidationException(response, HttpStatus.CONFLICT);
		}

	}

}
