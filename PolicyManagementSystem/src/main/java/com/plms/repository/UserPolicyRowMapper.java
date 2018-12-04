package com.plms.repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.plms.model.Policy;

public class UserPolicyRowMapper implements RowMapper<Policy> {

	@Override
	public Policy mapRow(ResultSet rs, int rowNumber) throws SQLException {
		// TODO Auto-generated method stub
		Policy policy = new Policy(rs.getString(1), rs.getString(2), rs.getDate(3).toString());
		Date currentDateSql = new Date(new java.util.Date().getTime());
		String isPolicyValid = (rs.getDate(3).compareTo(currentDateSql) < 0 )? "No" : "Yes"; 
		policy.setIsPolicyValid(isPolicyValid);
		return policy;
	}


}
