package com.plms.repository;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class UserRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	


	public void saveUser(String userId, String firstName, String lastName, Date dateOfBirth, String address,
			String contactNumber, String emailAddress, String password) {
		jdbcTemplate.update(
				"insert into User(user_id,first_name,last_name,date_of_birth,address,contact_number,email_address,password) values (?,?,?,?,?,?,?,?)",
				userId, firstName, lastName, dateOfBirth, address, contactNumber, emailAddress, password);

	}
	
	

	public boolean validateUser(String userId, String password) {
		Long userCount = (Long) jdbcTemplate
				.queryForMap("select count(*) as count from User where user_id = ? and password = ?",
						new Object[] { userId, password })
				.get("count");
		if (userCount.intValue() == 0) {
			return false;

		} else {
			return true;
		}

	}

	public boolean isUserAdmin(String userId) {
		String isAdmin = (String) jdbcTemplate
				.queryForMap("select is_admin from User where user_id = ? ", new Object[] { userId }).get("is_admin");
		if ("Y".equals(isAdmin)) {
			return true;

		} else {
			return false;
		}

	}

}
