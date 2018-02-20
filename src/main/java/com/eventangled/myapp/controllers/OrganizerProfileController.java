package com.eventangled.myapp.controllers;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.eventangled.myapp.dao.EventDao;
import com.eventangled.myapp.dao.OrganizerDao;
import com.eventangled.myapp.dao.UserDao;
import com.eventangled.myapp.exceptions.UserException;
import com.eventangled.myapp.pojo.OrganizerProfile;
import com.eventangled.myapp.pojo.Ticket;
import com.eventangled.myapp.pojo.TicketType;
import com.eventangled.myapp.pojo.User;
import com.eventangled.myapp.pojo.Event;
import com.eventangled.myapp.pojo.UserProfile;
import com.eventangled.myapp.validators.OrganizerProfileValidator;
import com.eventangled.myapp.validators.UserProfileValidator;

@Controller
@RequestMapping("/organizerprofile/*")
public class OrganizerProfileController {

	
	@Autowired
	@Qualifier("organizerDao")
	OrganizerDao organizerDao;
	
	@Autowired
	EventDao eventDao;
	
	@Autowired
	@Qualifier("organizerProfileValidator")
	OrganizerProfileValidator validator;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	@Autowired
	ServletContext servletContext;
	
	
	//method for redirecting user to organizer profile page for saving and updating organizer profile
	@RequestMapping(value = "/organizerprofile/updateOrganizerProfile", method = RequestMethod.GET)
	protected ModelAndView updateProfile(HttpServletRequest request) throws Exception {
		System.out.print("update organizer profile");
		try{
		User user =(User)request.getSession().getAttribute("user");
		OrganizerProfile profilePresent = organizerDao.getOrganizerProfile(user);
		
		if (profilePresent==null){
			return new ModelAndView("organizerprofile", "organizerprofile", new OrganizerProfile());
		}
		else{
			return new ModelAndView("organizerprofile", "organizerprofile", profilePresent);
		}
		}
		catch(Exception e){
			System.out.println("error"+ e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while accessing the page");
			
		}
		

	}
	
	//method for saving and updating organizer profile to database 
	@RequestMapping(value = "/organizerprofile/updateOrganizerProfile", method = RequestMethod.POST)
	protected ModelAndView updateOrganizerProfileDetails(HttpServletRequest request,  @ModelAttribute("organizerprofile") OrganizerProfile organizerprofile, BindingResult result) throws Exception {

		validator.validate(organizerprofile, result);

	if (result.hasErrors()) {
		return new ModelAndView("organizerprofile", "organizerprofile", organizerprofile);
		}
	User user =(User)request.getSession().getAttribute("user");
	System.out.println("User id: "+user.getUserId());

	try{
		System.out.print("Save Organizer Profile");
		
		OrganizerProfile profile = organizerDao.saveOrganizerProfile(organizerprofile,user);
		User u = organizerDao.getUser(user.getUserId());
		request.getSession().setAttribute("user", u);
			
		return new ModelAndView("organizerprofile", "organizerprofile", profile);
	

	} catch (IllegalStateException e) {
		System.out.println("*** IllegalStateException: " + e.getMessage());
		return new ModelAndView("error", "errorMessage", "error while updating organizer profile");
	}  catch (UserException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("Exception: " + e.getMessage());
		return new ModelAndView("error", "errorMessage", "error while updating organizer profile");
	}

	}
	
	//method to redirect user to his events page
	@RequestMapping(value = "/organizerprofile/myevents", method = RequestMethod.GET)
	protected ModelAndView viewMyEvents(HttpServletRequest request) throws Exception {

		User u=(User) request.getSession().getAttribute("user");
		OrganizerProfile profile =u.getOrganizerProfile();
		if (profile!=null){
			List<Event> events = organizerDao.getOrganizerEvents(profile.getOrganizerId());
			if(events.isEmpty()){
				return new ModelAndView("error", "errorMessage", "You have not created any events yet");
			}
			else{
				return new ModelAndView("my-events","events",events);
			}
		}
		else{
			return new ModelAndView("error", "errorMessage", "You have not completed your organizer profile, and hence dont have any events");
		}
		
		
		
	}
	
	//AJAX method to cancel events and send emails to all users who have booked tickets for the same
	@RequestMapping(value = "/organizerprofile/myevents", method = RequestMethod.POST)
	@ResponseBody
	protected String cancelEvent(HttpServletRequest request,@RequestParam(value = "eventID") int eventID) throws Exception {
		System.out.println("in controller");
		Event event = eventDao.cancelEvent(eventID);
		HashSet<User> users = new HashSet<User>();
		
		for (TicketType ticketType : event.getTicketTypes()){
			for (Ticket ticket : ticketType.getTickets()){
				users.add(ticket.getUser());
			}
		}
		
		for (User user : users){
			try
            {
            Email email = new SimpleEmail();
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("temporarywebtools2017@gmail.com", "temporary"));
            email.setHostName("smtp.gmail.com");//if a server is capable of sending email.
            email.setSSL(true);//setSSLOnConnect(true);
            email.setFrom("admin@eventory.com");
            email.setSubject("Event Cancelled!!!");
            email.setMsg("The event "+event.getEventTitle()+" has been cancelled unfortunately ! \n A refund for your tickets has been initiated.\n We apologize for the inconvenience caused.");
            email.addTo(user.getEmail());
            email.setTLS(true);//startTLS.enable.true
            email.send();
            System.out.println("EMAIL SENT ");
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            System.out.println("error while sending cancel email");
        }
		}
		
		return "done";
		
	}

	//method for accessing tickets list for a particular event 
	@RequestMapping(value = "/organizerprofile/ticketsexcel", method = RequestMethod.GET)
	protected ModelAndView viewAllTickets(HttpServletRequest request) throws Exception {
		try{
		View view = new TicketsExcelView();
		return new ModelAndView(view);		
	
		}
		catch(Exception e){
			System.out.println("error"+ e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while accessing the page");
			
		}
	}
	
	
	
}
