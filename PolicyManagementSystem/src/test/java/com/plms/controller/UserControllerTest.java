package com.plms.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.plms.repository.UserRepository;

public class UserControllerTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserController userController;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

	}


	@Test
	public void testRegisterUser() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.userController).build();

		mockMvc.perform(post("/registerUser").param("UserInfo_FirstName", "UserInfo_FirstName")
				.param("UserInfo_LastName", "UserInfo_LastName").param("UserInfo_DateOfBirth", "1990-01-30")
				.param("UserInfo_ContactNumber", "UserInfo_ContactNumber").param("UserInfo_Address", "UserInfo_Address")
				.param("UserInfo_Email", "UserInfo_Email").param("UserInfo_Password", "UserInfo_Password"))
				.andExpect(status().isOk()).andExpect(model().size(1)).andExpect(model().attributeExists("userId"))
				.andExpect(forwardedUrl("registerConfirmation"));

	}

	@Test
	public void testLoginForValidUser() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.userController).build();
		when(userRepository.validateUser("UserInfo_UserId", "UserInfo_Password")).thenReturn(true);
		mockMvc.perform(post("/login").param("UserInfo_UserId", "UserInfo_UserId").param("UserInfo_Password",
				"UserInfo_Password")).andExpect(status().is3xxRedirection()).andExpect(flash().attributeCount(2))
				.andExpect(flash().attributeExists("userId")).andExpect(flash().attributeExists("isUserAdmin"))
				.andExpect(redirectedUrl("retrievePolicies"));
	}

	@Test
	public void testLoginForInvalidUser() throws Exception {
		MockMvc mockMvc =
	            MockMvcBuilders.standaloneSetup(this.userController)
	                    .setViewResolvers(new StandaloneMvcTestViewResolver())
	                    .build();
		when(userRepository.validateUser("UserInfo_UserId", "UserInfo_Password")).thenReturn(false);
		mockMvc.perform(post("/login").param("UserInfo_UserId", "UserInfo_UserId").param("UserInfo_Password",
				"UserInfo_Password")).andExpect(status().isOk()).andExpect(model().size(1))
				.andExpect(model().attributeExists("loginError")).andExpect(forwardedUrl("login"));
	}

	@Test
	public void testLogout() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.userController).build();
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("UserInfo_UserId", "UserInfo_UserId");
		sessionattr.put("UserInfo_Password", "UserInfo_Password");
		mockMvc.perform(MockMvcRequestBuilders.get("/logout").sessionAttrs(sessionattr)).andExpect(status().is2xxSuccessful())
				.andExpect(forwardedUrl("login"));
	}

}
