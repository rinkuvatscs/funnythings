package com.interview.mysql.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.interview.mysql.StateService;
@Repository
public class StateServiceImpl implements StateService {

	
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

}
