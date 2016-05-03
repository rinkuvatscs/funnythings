package com.interview.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.interview.pojo.State;

public class StateExtractor implements ResultSetExtractor<List<State>> {

	@Override
	public List<State> extractData(ResultSet rs) throws SQLException,
			DataAccessException {

		List<State> stateList = new ArrayList<State>();
		State state = null;
		while (rs.next()) {
			state = new State();
			state.setState_id(rs.getInt("topic_id"));
			state.setState_name(rs.getString("topic_name"));
			state.setCountry_id(rs.getInt("country_id"));
			stateList.add(state);
		}
		return stateList;
	}
}
