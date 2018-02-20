package com.eventangled.myapp.validators;

import java.util.Calendar;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.eventangled.myapp.pojo.Event;

public class EventValidator implements Validator {

	@Override
	public boolean supports(Class aClass) {
		return aClass.equals(Event.class);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		Event event = (Event) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eventTitle", "error.empty.eventTitle",
				"Event Title Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "error.empty.startDate", "Start Date Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDate", "error.empty.endDate", "End Date Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startTime", "error.empty.startTime", "Start Time Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endTime", "error.empty.endTime", "End Time Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eventAddressLine1", "error.empty.eventAddressLine1",
				"Street address Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eventCity", "error.empty.eventCity", "Event City Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eventState", "error.empty.eventState",
				"Event State Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eventCountry", "error.empty.eventCountry",
				"Event Country Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eventZipCode", "error.empty.eventZipCode",
				"Zip Code Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numberOfTickets", "error.empty.numberOfTickets",
				"numberOfTickets Required");

		if (errors.hasErrors()) {
			return;// Skip the rest of the validation rules
		}

		if (!(event.getNumberOfTickets() > 0 && event.getNumberOfTickets() <11)) {
			errors.rejectValue("numberOfTickets", "error.invalid.numberOfTickets",
					"Number of Tickets must be greater than 0");
		}

		// String datePattern =
		// "^(3[01]|[12][0-9]|0?[1-9])/(1[0-2]|0?[1-9])/(?:[0-9]{2})?[0-9]{2}$";

		// if (!(String.valueOf(event.getStartDate()).matches(datePattern))){
		// errors.rejectValue("startDate","error.invalid.startDate" ,"Invalid
		// Date format");

		// }

		// if (!(String.valueOf(event.getEndDate()).matches(datePattern))){
		// errors.rejectValue("endDate","error.invalid.endDate" ,"Invalid Date
		// format");

		// }
		

		String timeFormat = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
		if (!(event.getStartTime().matches(timeFormat))) {
			errors.rejectValue("startTime", "error.invalid.startTime", "Enter time in hh:mm 24 hour format");

		}

		if (!(event.getEndTime().matches(timeFormat))) {
			errors.rejectValue("endTime", "error.invalid.endTime", "Enter time in hh:mm 24 hour format");

		}
		
		String eventAddressLine = "^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$";
		if (!(event.getEventAddressLine1().matches(eventAddressLine)) || event.getEventAddressLine1().contains("<script>") || event.getEventAddressLine1().contains("</script>")) {
			errors.rejectValue("eventAddressLine1", "error.invalid.eventAddressLine1", "Enter a valid address");

		}

		if (!event.getEventAddressLine2().isEmpty() && (!(event.getEventAddressLine2().matches(eventAddressLine)) || event.getEventAddressLine2().contains("<script>") || event.getEventAddressLine2().contains("</script>"))) {
			errors.rejectValue("eventAddressLine2", "error.invalid.eventAddressLine2", "Enter a valid address");

		}

		String zipCodePattern = "^[0-9]{5}(?:-[0-9]{4})?$";

		if (!(event.getEventZipCode().matches(zipCodePattern))) {
			errors.rejectValue("eventZipCode", "error.invalid.eventZipCode", "Enter a zipcode");

		}

		String cityStatePattern = "([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)";

		if (!event.getEventCity().matches(cityStatePattern) || event.getEventCity().contains("<script>") || event.getEventCity().contains("</script>")) {
			errors.rejectValue("eventCity", "error.invalid.eventCity", "Enter valid city");
		}
		if (!event.getEventState().matches(cityStatePattern) || event.getEventState().contains("<script>")|| event.getEventState().contains("</script>")) {
			errors.rejectValue("eventState", "error.invalid.eventState", "Enter valid state");
		}
		if (!event.getEventCountry().matches(cityStatePattern)|| event.getEventCountry().contains("<script>") || event.getEventCountry().contains("</script>")) {
			errors.rejectValue("eventCountry", "error.invalid.country", "Enter valid country");
		}
		String descPattern = "^[A-Za-z0-9.!',& -]*{0,300}$";

		if (!event.getEventTitle().matches(descPattern) || event.getEventTitle().contains("<script>") || event.getEventTitle().contains("</script>")) {
			errors.rejectValue("eventTitle", "error.invalid.eventTitle", "Enter valid event title");
		}

		
		if (!event.getEventDescription().isEmpty() && (!event.getEventDescription().matches(descPattern)|| event.getEventDescription().contains("<script>") || event.getEventDescription().contains("</script>") )) {
			errors.rejectValue("eventDescription", "error.invalid.eventDescription", "Enter valid event Description; only 300 alphanumeric characters allowed");
		}
		if (errors.hasErrors()){
			return;
		}
		String[] start = event.getStartTime().split(":");
		String h1 = start[0]; 
		String m1 = start[1];
		
		String[] end = event.getEndTime().split(":");
		String h2 = end[0]; 
		String m2 = end[1];
		
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(event.getStartDate());
		cal1.set(Calendar.HOUR,Integer.parseInt(h1));
		cal1.set(Calendar.MINUTE,Integer.parseInt(m1));
		
		
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(event.getEndDate());
		cal2.set(Calendar.HOUR,Integer.parseInt(h2));
		cal2.set(Calendar.MINUTE,Integer.parseInt(m2));
		

		if (cal1.compareTo(cal2)>=0){
			errors.rejectValue("startTime", "error.invalid.startTime", "Enter valid date range");
		}
		


	}

}
