
package com.plms.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.plms.repository.UserRepository;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;
	

	@RequestMapping(value = "/registerUser")
	public String registerUser(Model model, @RequestParam("UserInfo_FirstName") String firstName,
			@RequestParam("UserInfo_LastName") String lastName,
			@RequestParam("UserInfo_DateOfBirth") String dateOfBirth, @RequestParam("UserInfo_Address") String address,
			@RequestParam("UserInfo_ContactNumber") String contactNumber,
			@RequestParam("UserInfo_Email") String emailAddress, @RequestParam("UserInfo_Password") String password) {
		String userId = firstName;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date sqlDate = null;
		Calendar cal = Calendar.getInstance();
		System.out.println(dateOfBirth);
		try {
			java.util.Date utilDate = format.parse(dateOfBirth);
			cal.setTime(utilDate);
			String month = Integer.toString(cal.get(Calendar.MONTH));
			String date = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
			userId = userId.concat(date).concat(month);
			sqlDate = new java.sql.Date(utilDate.getTime());
			System.out.println(userId);
			System.out.println(sqlDate);
			model.addAttribute("userId", userId);
		} catch (ParseException e) {
			e.printStackTrace();
			model.addAttribute("errorMap", e);

		}
		userRepository.saveUser(userId, firstName, lastName, sqlDate, address, contactNumber, emailAddress, password);
		return "registerConfirmation";

	}
	
	/**@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model, @RequestParam("UserInfo_UserId") String userId,
			@RequestParam("UserInfo_Password") String password, HttpSession httpSession,
			HttpServletResponse httpServletResponse, RedirectAttributes redirectAttributes,final HttpServletRequest request) { **/
		

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model, @RequestParam("UserInfo_UserId") String userId,
			@RequestParam("UserInfo_Password") String password, HttpSession httpSession,
			HttpServletResponse httpServletResponse, RedirectAttributes redirectAttributes,final HttpServletRequest request) {
		
		
		httpServletResponse.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
		httpServletResponse.setHeader("Pragma", "no-cache");
		httpServletResponse.setDateHeader("Expires", 0); 
		boolean isValidUser = userRepository.validateUser(userId, password); 
		if (isValidUser) {
			boolean isUserAdmin = userRepository.isUserAdmin(userId);
			httpSession.setAttribute("loggedInUser", userId);
			httpSession.setAttribute("isUserAdmin", isUserAdmin);
			httpSession.setAttribute("userId", userId);
			return "redirect:retrievePolicies";
	} else {
			model.addAttribute("loginError", "Invalid credentials");
			return "login";
		} 
		
		
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession httpSession) {
		httpSession.invalidate();

		return "login";

	}
	
	
	

}
