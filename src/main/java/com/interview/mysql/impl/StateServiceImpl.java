package com.interview.mysql.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.interview.extractor.StateExtractor;
import com.interview.mysql.StateService;
import com.interview.pojo.State;
import com.interview.util.MysqlOperations;

@Repository
public class StateServiceImpl implements StateService {

	public static final String ADD_STATE = "insert into state (state_id,country_id,state_name,status) values (LAST_INSERT_ID(),?,?,?)";
	public static final String STATE_EXIST = "select * from state where country_id = ? AND state_name = ? ";
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public String addState(int countryId, String stateName) {
		String response = null;
		
		State state = new State();
		state.setState_name(stateName);
		
		List<String> intList = new ArrayList<String>();
		intList.add(String.valueOf(countryId));
		intList.add(stateName);
		intList.add("A");
		if(!StringUtils.isEmpty(stateName)){
			if(!isStateExist(state,countryId)){
				int result = jdbcTemplate.update(ADD_STATE, intList.toArray());
				
				if (result > 0) {
					response = state + " " + "state Is Added";
				} else {
					response = "Sorry , Can not add " + state;
				}
				
			}
		
		else{
			if (state.getStatus().equalsIgnoreCase("D")) {
				activateDeactivateStateByStateName(
						MysqlOperations.ACTIVATE, state.getState_name(),countryId);
				response = state + " " + "state Is Activated";
			}
			
			response = state + " " + "state Is already exists";
		}
		}else {
			response = "Please Check your state Name";
		}
		
		return response;
	}

	

	@Override
	public int getStateCodeByStateNameAndCountryId(int countryId,
			String stateName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getStateNmaeByStateIdAndCountryId(int countryId, int stateId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String modifyStateName(int countryId, String oldStateName,
			String newStateName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteState(int countryId, String stateName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isStateExist(State state , int countryId) {

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

	@Override
	public String activateDeactivateStateByStateName(MysqlOperations mysqlOperations, String stateName ,int countryId) {
		String query = null;
		/* String query ="DELETE FROM state WHERE state_name = ?"; */
		/*
		 * At THe Time of deletion we will not delete state we will disable it
		 */
		if (mysqlOperations.toString().equalsIgnoreCase(
				MysqlOperations.ACTIVATE.toString())) {
			query = "UPDATE state set status = 'A' WHERE country_id = ? AND state_name = ?";
		} else if (mysqlOperations.toString().equalsIgnoreCase(
				MysqlOperations.DEACTIVATE.toString())) {
			query = "UPDATE state set status = 'D' WHERE country_id = ? AND state_name = ?";
		}
		List<String> args = new ArrayList<>();
		args.add(String.valueOf(countryId));
		args.add(stateName);
		jdbcTemplate.update(query, args);
		return "state is modified";
	}



	@Override
	public List<State> getStateListByCountryId(int countryId) {
		List<String> intList = new ArrayList<String>();
		intList.add(String.valueOf(countryId));
		String query = "select * from state where status = 'A' AND country_id = ? ";
		List<State> stateList = jdbcTemplate.query(query,intList.toArray(),
				new StateExtractor());
		return stateList;
	}
	
	

}
