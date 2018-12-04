package com.plms.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.plms.model.Policy;
import com.plms.repository.PolicyRepository;

@Controller
public class PolicyController {

	@Autowired
	private PolicyRepository policyRepository;

	@RequestMapping("/retrievePolicies")
	public String getAllPolicies(Model model,@SessionAttribute("userId") final String userId,
			@SessionAttribute("isUserAdmin") final boolean isUserAdmin, RedirectAttributes redirectAttributes,HttpServletResponse httpServletResponse,HttpSession httpSession) {
		
		httpServletResponse.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
		List<Policy> policies = policyRepository.getAllPolicies();
		if(!isUserAdmin) {
			httpSession.setAttribute("policies", policies);
			httpSession.setAttribute("userId", userId);
			httpSession.setAttribute("isUserAdmin", isUserAdmin);
		return "redirect:retrievePoliciesForUser";
		}else{
			model.addAttribute("policies", policies);
			model.addAttribute("userId", userId);
			model.addAttribute("isUserAdmin", isUserAdmin);
			return "policy";
		}
	}

	@RequestMapping("/retrievePoliciesForUser")
	public String getAllPoliciesForUser(Model model,@SessionAttribute("userId") final String userId,
			@SessionAttribute("isUserAdmin") final boolean isUserAdmin,
			@SessionAttribute("policies") final List<Policy> policies ,HttpServletResponse httpServletResponse) {
		httpServletResponse.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
		List<Policy> userPolicies  = policyRepository.getAllPoliciesByUserId(userId);
		model.addAttribute("policies", policies);
		model.addAttribute("userId", userId);
		model.addAttribute("isUserAdmin", isUserAdmin);
		model.addAttribute("userPolicies", userPolicies);
		return "policy";
	}

	@RequestMapping("/editPolicy")
	public String editPolicy(Model model, @RequestParam("policyId") Integer policyId,
			@RequestParam("policyName") String policyName, @RequestParam("policyDetails") String policyDetails) {
		Integer count = policyRepository.editPolicy(policyId, policyName, policyDetails);
		System.out.println("count : " + count);
		model.addAttribute("isUserAdmin", true);
		return "policy";
	}

}
