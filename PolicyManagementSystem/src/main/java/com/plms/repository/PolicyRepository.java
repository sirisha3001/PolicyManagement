package com.plms.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.plms.model.Policy;

@Repository
public class PolicyRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Policy> getAllPolicies() {
		List<Policy> policies = jdbcTemplate.query("select policy_id,policy_name,policy_details from Policy",
				new RowMapper<Policy>() {
					@Override
					public Policy mapRow(ResultSet rs, int rowNumber) throws SQLException {
						Policy policy = new Policy(rs.getInt(1), rs.getString(2), rs.getString(3));
						return policy;
					}

				});
		return policies;
	}

	public List<Policy> getAllPoliciesByUserId(String userId) {
		List<Policy> userPolicies = jdbcTemplate.query(
				"select p.policy_name,up.amount_paid,up.policy_end_date from User_Policy up,  Policy p where up.User_id =?  and up.policy_id= p.policy_id  ",
				new UserPolicyRowMapper(), userId);
		return userPolicies;
	}

	public int editPolicy(Integer policyId, String policyName, String policyDetails) {
		System.out.println(policyId);
		System.out.println(policyName);
		System.out.println(policyDetails);
		return jdbcTemplate.update("update Policy set policy_name =? , policy_details = ? where policy_id =?", policyName,
				policyDetails, policyId);

	}

}
