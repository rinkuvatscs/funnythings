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

@Repository
public class StateServiceImpl implements StateService {

	public static final String ADD_STATE = "insert into state(state_id,country_id,state_name) values(LAST_INSERT_ID(),?,?)";
	public static final String STATE_EXIST = "select * from state where state_name = ? ";
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public String addState(int countryId, String stateName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStateListByCountryId(int countryId) {
		// TODO Auto-generated method stub
		return null;
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
	public boolean isStateExist(State state) {

		boolean response = false;
		List<String> args = new ArrayList<>();
		args.add(state.getState_name().toLowerCase());
		try {
			List<State> stateList = jdbcTemplate.query(STATE_EXIST,
					args.toArray(), new StateExtractor());
			if (!StringUtils.isEmpty(stateList)) {
				state.setState_id(stateList.get(0).getState_id());
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
