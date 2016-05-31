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
import com.interview.validator.LogDuration;

@Repository
public class StateServiceImpl implements StateService {

	public static final String ADD_STATE = "insert into state (state_id,country_id,state_name,status) values (LAST_INSERT_ID(),?,?,?)";
	public static final String STATE_EXIST = "select * from state where country_id = ? AND state_name = ? ";
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	@LogDuration("Add State API")
	public String addState(int countryId, String stateName) {
		String response = null;

		State state = new State();
		state.setState_name(stateName);

		List<String> intList = new ArrayList<String>();
		intList.add(String.valueOf(countryId));
		intList.add(stateName);
		intList.add("A");

		int result =  0;//jdbcTemplate.update(ADD_STATE, intList.toArray());

		if (result > 0) {
			response = state + " " + "state Is Added";
		} else {
			response = "Sorry , Can not add " + state;
		}

		return response;
	}

	@Override
	public int getStateCodeByStateNameAndCountryId(int countryId, String stateName) {
		List<State> stateList = null;
		String query = "select * from state where country_id= ? AND state_name= ?";
		List<String> intList = new ArrayList<String>();
		intList.add(String.valueOf(countryId));
		intList.add(stateName);
		stateList = jdbcTemplate.query(query, intList.toArray(), new StateExtractor());
		State state = stateList.get(0);
		int stateid = state.getState_id();
		return stateid;
	}

	@Override
	public String getStateNmaeByStateIdAndCountryId(int countryId, int stateId) {
		List<State> stateList = null;
		String query = "select * from state where country_id= ? AND state_id = ?";
		List<Integer> intList = new ArrayList<Integer>();
		intList.add(countryId);
		intList.add(stateId);
		stateList = jdbcTemplate.query(query, intList.toArray(), new StateExtractor());
		State state = stateList.get(0);
		String statename = state.getState_name();
		return statename;
	}

	@Override
	@LogDuration("Modify State API")
	public String modifyStateName(int countryId, String oldStateName, String newStateName) {
		String responce = null;

		String updateQuery = "update state set state_name = ? where state_name=? AND country_id=?";
		List<String> intList = new ArrayList<String>();
		intList.add(newStateName);
		intList.add(oldStateName);
		intList.add(String.valueOf(countryId));

		int result = jdbcTemplate.update(updateQuery, intList.toArray());

		if (result > 0) {
			int stateId = getStateCodeByStateNameAndCountryId(countryId, newStateName);
			responce = getStateNmaeByStateIdAndCountryId(countryId, stateId) + " " + "state Is modified";
		} else {
			responce = "Sorry , Can not modified" + oldStateName;
		}

		return responce;
	}

	@Override
	public String activateDeactivateStateByStateNameAndCountry_Id(MysqlOperations mysqlOperations, String stateName,
			int countryId) {
		String query = null;
		/* String query ="DELETE FROM state WHERE state_name = ?"; */
		/*
		 * At THe Time of deletion we will not delete state we will disable it
		 */
		if (mysqlOperations.toString().equalsIgnoreCase(MysqlOperations.ACTIVATE.toString())) {
			query = "UPDATE state set status = 'A' WHERE country_id = ? AND state_name = ?";
		} else if (mysqlOperations.toString().equalsIgnoreCase(MysqlOperations.DEACTIVATE.toString())) {
			query = "UPDATE state set status = 'D' WHERE country_id = ? AND state_name = ?";
		}
		List<String> args = new ArrayList<>();
		args.add(String.valueOf(countryId));
		args.add(stateName);
		jdbcTemplate.update(query, args.toArray());
		return "state is modified";
	}

	@Override
	public List<State> getStateListByCountryId(int countryId) {
		List<String> intList = new ArrayList<String>();
		intList.add(String.valueOf(countryId));
		String query = "select * from state where status = 'A' AND country_id = ? ";
		List<State> stateList = jdbcTemplate.query(query, intList.toArray(), new StateExtractor());
		return stateList;
	}

}
