package com.eventangled.myapp.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eventangled.myapp.dao.UserDao;
import com.eventangled.myapp.exceptions.UserException;
import com.eventangled.myapp.pojo.User;
import com.eventangled.myapp.pojo.UserProfile;
import com.eventangled.myapp.validators.UserProfileValidator;


@Controller
@RequestMapping("/userprofile/*")
public class UserProfileController  {
	
	@Autowired
	@Qualifier("userDao")
	UserDao userDao;
	
	@Autowired
	@Qualifier("userProfileValidator")
	UserProfileValidator validator;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	//redirecting user to update/save profile page
	@RequestMapping(value = "/userprofile/updateUserProfile", method = RequestMethod.GET)
	protected ModelAndView updateProfile(HttpServletRequest request) throws Exception {
		System.out.print("update profile");
		try{
			User user =(User)request.getSession().getAttribute("user");
			UserProfile profilePresent = userDao.getUserProfile(user);
			
			if (profilePresent==null){
				return new ModelAndView("myaccount", "userprofile", new UserProfile());
			}
			else{
				return new ModelAndView("myaccount", "userprofile", profilePresent);
			}
		
	} catch (UserException e) {
		System.out.println("Exception: " + e.getMessage());
		return new ModelAndView("error", "errorMessage", "error while login");
	}

		

	}
	
	//saving  updating user profile to database
	@RequestMapping(value = "/userprofile/updateUserProfile", method = RequestMethod.POST)
	protected ModelAndView updateUserProfileDetails(HttpServletRequest request,  @ModelAttribute("userprofile") UserProfile userProfile, BindingResult result) throws Exception {

		validator.validate(userProfile, result);

	if (result.hasErrors()) {
		return new ModelAndView("myaccount", "userProfile", userProfile);
}

		try {

			System.out.print("Save User Profile");
			
			User user =(User)request.getSession().getAttribute("user");

			UserProfile profile = userDao.saveUserProfile(userProfile,user);
						
			return new ModelAndView("myaccount", "userProfile", profile);

		} catch (UserException e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}
	}
		
}
