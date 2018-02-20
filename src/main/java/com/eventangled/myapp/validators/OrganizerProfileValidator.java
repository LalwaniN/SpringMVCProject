package com.eventangled.myapp.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.eventangled.myapp.pojo.OrganizerProfile;


public class OrganizerProfileValidator implements Validator{
	public boolean supports(Class aClass) {
		return aClass.equals(OrganizerProfile.class);
	}

	public void validate(Object obj, Errors errors) {
		OrganizerProfile user = (OrganizerProfile) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "organizerDescription", "error.invalid.organizerDescription", "Description is required");
		
		if (errors.hasErrors())
			return;
		
		String descPattern = "^[A-Za-z0-9.!'&, ]*{0,300}$";
		if (!user.getOrganizerDescription().isEmpty() && (!user.getOrganizerDescription().matches(descPattern)|| user.getOrganizerDescription().contains("<script>") || user.getOrganizerDescription().contains("</script>") )) {
			errors.rejectValue("organizerDescription", "error.invalid.organizerDescription", "Enter valid organizer Description; only 300 alphanumeric characters allowed");
		}
		
		String urlPattern ="^(http://|https://)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$";
		
		if (!user.getWebsiteUrl().isEmpty() && (!user.getWebsiteUrl().matches(urlPattern)|| user.getWebsiteUrl().contains("<script>") || user.getWebsiteUrl().contains("</script>") )) {
			errors.rejectValue("websiteUrl", "error.invalid.websiteUrl", "Enter valid website address");
		}
		
		String facebookUsername ="^[A-Za-z0-9.]*{5,}$";

		if (!user.getFaceBookPage().isEmpty() && (!user.getFaceBookPage().matches(facebookUsername)|| user.getFaceBookPage().contains("<script>") || user.getFaceBookPage().contains("</script>") )) {
			errors.rejectValue("faceBookPage", "error.invalid.faceBookPage", "Enter valid facebook username");
		}
		
		
		String twitterPattern = "@([A-Za-z0-9_]+)";
		if (!user.getTwitter().isEmpty() && (!user.getTwitter().matches(twitterPattern)|| user.getTwitter().contains("<script>") || user.getTwitter().contains("</script>") )) {
			errors.rejectValue("twitter", "error.invalid.twitter", "Enter valid twitter username");
		}
		
		String instagramPattern =  "([A-Za-z0-9_]+)";
		
		if (!user.getInstagram().isEmpty() && (!user.getInstagram().matches(instagramPattern)|| user.getInstagram().contains("<script>") || user.getInstagram().contains("</script>") )) {
			errors.rejectValue("instagram", "error.invalid.instagram", "Enter valid instagram username");
		}
		
	}
}
