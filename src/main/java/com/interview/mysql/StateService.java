package com.interview.mysql;

public interface StateService {

	public String addState(int countryId , String stateName);
	
	public String getStateListByCountryId(int countryId);
	
	public int getStateCodeByStateNameAndCountryId(int countryId , String stateName);
	
	public String getStateNmaeByStateIdAndCountryId(int countryId , int stateId);

	public String modifyStateName(int countryId , String oldStateName, String newStateName);
	
	public String deleteState(int countryId , String stateName);
}
