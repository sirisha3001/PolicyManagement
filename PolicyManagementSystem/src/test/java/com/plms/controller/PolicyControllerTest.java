/**
 * 
 */
package com.plms.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.plms.model.Policy;
import com.plms.repository.PolicyRepository;

/**
 * @author sirishakarra
 *
 */
public class PolicyControllerTest {

	@Mock
	private PolicyRepository policyRepository;

	@InjectMocks
	private PolicyController policyController;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

	}


	@Test
	public void testEditPolicy() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.policyController).build();
		when(policyRepository.editPolicy(Integer.parseInt("1"), "policyName", "policyDetails")).thenReturn(1);
		mockMvc.perform(post("/editPolicy").param("policyId", "1").param("policyName", "policyName")
				.param("policyDetails", "policyDetails")).andExpect(status().isOk()).andExpect(model().size(1))
				.andExpect(model().attributeExists("isUserAdmin")).andExpect(forwardedUrl("policy"));

	}

	@Test
	public void testRetrieveAllPolicyForAdminUser() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.policyController).build();
		List<Policy> policies = new ArrayList<Policy>();
		when(policyRepository.getAllPolicies()).thenReturn(policies);
		mockMvc.perform(post("/retrievePolicies").flashAttr("userId", "userId").flashAttr("isUserAdmin", true))
				.andExpect(status().isOk()).andExpect(model().size(3)).andExpect(model().attributeExists("isUserAdmin"))
				.andExpect(model().attributeExists("userId"))
				.andExpect(model().attributeExists("policies"))
				.andExpect(forwardedUrl("policy"));

	}
	
	@Test
	public void testRetrieveAllPolicyForNonAdminUser() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.policyController).build();
		List<Policy> policies = new ArrayList<Policy>();
		when(policyRepository.getAllPolicies()).thenReturn(policies);
		mockMvc.perform(post("/retrievePolicies").flashAttr("userId", "userId").flashAttr("isUserAdmin", false))
				.andExpect(status().is3xxRedirection()).andExpect(flash().attributeCount(3)).andExpect(flash().attributeExists("isUserAdmin"))
				.andExpect(flash().attributeExists("userId"))
				.andExpect(flash().attributeExists("policies"))
				.andExpect(redirectedUrl("retrievePoliciesForUser"));

	}
	
	@Test
	public void testRetrievePolicyForNonAdminUser() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.policyController).build();
		List<Policy> policies = new ArrayList<Policy>();
		when(policyRepository.getAllPoliciesByUserId("userId")).thenReturn(policies);
		mockMvc.perform(post("/retrievePoliciesForUser").flashAttr("userId", "userId").flashAttr("isUserAdmin", false).flashAttr("policies", new ArrayList<Policy>()))
				.andExpect(status().isOk()).andExpect(model().size(4)).andExpect(model().attributeExists("isUserAdmin"))
				.andExpect(model().attributeExists("userId"))
				.andExpect(model().attributeExists("policies"))
				.andExpect(model().attributeExists("userPolicies"))
				.andExpect(forwardedUrl("policy"));

	}

}
