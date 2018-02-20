package com.eventangled.myapp.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.eventangled.myapp.pojo.UserProfile;

public class UserProfileValidator implements Validator{

	public boolean supports(Class aClass) {
		return aClass.equals(UserProfile.class);
	}

	public void validate(Object obj, Errors errors) {
		UserProfile user = (UserProfile) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contact", "error.invalid.contact", "Contact number required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "addressLine1", "error.invalid.addressLine1","address Line Required");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "addressLine2", "error.invalid.addressLine2","address Line Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "error.invalid.city","city Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "state", "error.invalid.state","state Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "error.invalid.country","country Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "zip", "error.invalid.zip","zip Required");
		
		if (errors.hasErrors())
			return;// Skip the rest of the validation rules
		

		String phoneNo=user.getContact();

		if (!phoneNo.matches("[0-9]{10}")) 
			errors.rejectValue("contact", "error.invalid.contact", "Enter valid contact number");
       
		String eventAddressLine = "^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$";
		if (!(user.getAddressLine1().matches(eventAddressLine)) || user.getAddressLine1().contains("<script>") || user.getAddressLine1().contains("</script>")) {
			errors.rejectValue("addressLine1", "error.invalid.addressLine1", "Enter a valid address");

		}

		if (!user.getAddressLine2().isEmpty() && (!(user.getAddressLine2().matches(eventAddressLine)) || user.getAddressLine2().contains("<script>") || user.getAddressLine2().contains("</script>"))) {
			errors.rejectValue("addressLine2", "error.invalid.addressLine2", "Enter a valid address");

		}

		
		String zipCodePattern = "^[0-9]{5}(?:-[0-9]{4})?$";

		if (!(user.getZip().matches(zipCodePattern))) {
			errors.rejectValue("zip", "error.invalid.zip", "Enter a zipcode");

		}

		String cityStatePattern = "([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)";

		if (!user.getCity().matches(cityStatePattern) || user.getCity().contains("<script>") || user.getCity().contains("</script>")) {
			errors.rejectValue("city", "error.invalid.city", "Enter valid city");
		}
		if (!user.getState().matches(cityStatePattern) || user.getState().contains("<script>")|| user.getState().contains("</script>")) {
			errors.rejectValue("state", "error.invalid.state", "Enter valid state");
		}
		if (!user.getCountry().matches(cityStatePattern)|| user.getCountry().contains("<script>") || user.getCountry().contains("</script>")) {
			errors.rejectValue("country", "error.invalid.country", "Enter valid country");
		}
        
	}

	
}
