package com.interview.mysql;

import java.util.List;

import com.interview.pojo.State;
import com.interview.util.MysqlOperations;

public interface StateService {

	public String addState(int countryId , String stateName);
	
	public List<State> getStateListByCountryId(int countryId);
	
	public int getStateCodeByStateNameAndCountryId(int countryId , String stateName);
	
	public String getStateNmaeByStateIdAndCountryId(int countryId , int stateId);

	public String modifyStateName(int countryId , String oldStateName, String newStateName);
	
	
	
	public boolean isStateExist(State state , int countryId ) ;
	
	public String activateDeactivateStateByStateNameAndCountry_Id(MysqlOperations mysqlOperations , String stateName , int countryId);
}
