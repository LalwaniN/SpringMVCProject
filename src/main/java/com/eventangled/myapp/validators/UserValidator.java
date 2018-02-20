package com.eventangled.myapp.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.eventangled.myapp.dao.UserDao;
import com.eventangled.myapp.pojo.User;


public class UserValidator implements Validator {
	
	//@Autowired
	//UserDao userDao;
	public boolean supports(Class aClass) {
		return aClass.equals(User.class);
	}

	public void validate(Object obj, Errors errors) {
		User user = (User) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.invalid.password", "Password Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.invalid.email","Email Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "error.invalid.firstName","FirstName Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "error.invalid.lastName","LastName Required");
		
		if (errors.hasErrors()) {
            return;//Skip the rest of the validation rules
        }
		
		UserDao userDao = new UserDao();
		// check if user exists
		User u=userDao.checkIfUserPresent(user.getEmail());
		
		if (u!=null){
			errors.rejectValue("email","error.invalid.email" ,"User with this email already registered");
		}
		
		if(!user.getFirstName().matches("[a-zA-z]+([ '-][a-zA-Z]+)*")){
			errors.rejectValue("firstName","error.invalid.firstName" ,"Enter a valid first name");
		}
		
		if(!user.getFirstName().matches("[a-zA-z]+([ '-][a-zA-Z]+)*")){
			errors.rejectValue("lastName","error.invalid.lastName" ,"Enter a valid last name");
		}
		String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{4,15})";
		if(!user.getPassword().matches(PASSWORD_PATTERN)){
			errors.rejectValue("password","error.invalid.password" ,"**Password must be between 4 and 15 characters long. \n ** Contain at least one digit.\n **Contain at least one lower case character.\n **Contain at least one upper case character.\n **Contain at least on special character from [ @ # $ % ! . ].");
		}
		
		
		
	}
}