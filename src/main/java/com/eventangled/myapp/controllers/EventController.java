package com.eventangled.myapp.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.Session;
import javax.servlet.ServletContext;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.eventangled.myapp.dao.EventDao;
import com.eventangled.myapp.dao.OrganizerDao;
import com.eventangled.myapp.exceptions.EventException;
import com.eventangled.myapp.pojo.Event;
import com.eventangled.myapp.pojo.OrganizerProfile;
import com.eventangled.myapp.pojo.TicketType;
import com.eventangled.myapp.pojo.User;
import com.eventangled.myapp.validators.EventValidator;
import com.eventangled.myapp.validators.UserValidator;
import com.google.gson.Gson;

@Controller
@RequestMapping("/event/*")
public class EventController {
	
	@Autowired
	EventDao eventDao;
	
	@Autowired
	ServletContext servletContext;
	
	@Autowired
	@Qualifier("eventValidator")
	EventValidator validator;
	
	@Autowired
	@Qualifier("organizerDao")
	OrganizerDao organizerDao;
	
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	
	//method to redirect organizer to create event page
	@RequestMapping(value = "/event/createevent", method = RequestMethod.GET)
	protected ModelAndView create_Event(HttpServletRequest request) throws Exception {
		
		try{
			System.out.print("Create Event");
			User user =(User)request.getSession().getAttribute("user");
			if (user.isOrganizerFlag()){
				List<String> eventTypes = eventDao.getEventCategories();
				request.getSession().setAttribute("eventTypes", eventTypes);
				return new ModelAndView("create-event", "event", new Event());
			}
			else{
				return new ModelAndView("error", "errorMessage", "Please update your organizer profile before creating an event");
			}
			
		}
		catch(Exception e){
			System.out.println("error"+ e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while accessing the page");
			
		}
		
		

	}

	//method to map event details to Event object and redirect to create ticket types for the event
	@RequestMapping(value = "/event/createevent", method = RequestMethod.POST)
	protected ModelAndView createEvent(HttpServletRequest request,@ModelAttribute("event") Event event, BindingResult result) throws Exception {
		System.out.print("Create Event");
		validator.validate(event, result);

		if (result.hasErrors()) {
			return new ModelAndView("create-event", "event", event);
		}

		
		try {
			
			File directory;
			String check = File.separator; // Checking if system is linux
											// based or windows based by
											// checking seprator used.
			String path = null;
			if (check.equalsIgnoreCase("\\")) {
				path="E:\\springprojects\\FinalProjectWebTools\\src\\main\\webapp\\resources";
				//path = servletContext.getRealPath("").replace("build\\", ""); // gives real path as Lab9/build/web/
																			  // so we need to replace build in the path
																					}

			if (check.equalsIgnoreCase("/")) {
				path = servletContext.getRealPath("").replace("build/", "");
				path += "/"; // Adding trailing slash for Mac systems.
			}
			
		//	User user =(User)request.getSession().getAttribute("user");
		//	System.out.println("User id: "+user.getUserId());
			
			directory = new File(path + "\\" + "eventimages");
			boolean temp = directory.exists();
			if (!temp) {
				temp = directory.mkdir();
			}
			if (temp) {
				// We need to transfer to a file
				CommonsMultipartFile photoInMemory = event.getEventPhoto();

				String fileName = photoInMemory.getOriginalFilename();
				// could generate file names as well
				
				System.out.println(fileName);

				File localFile = new File(directory.getPath(), fileName);

				// move the file from memory to the file

				photoInMemory.transferTo(localFile);
			//	event.setEventPhotoFileName(localFile.getPath());
				event.setEventPhotoFileName(fileName);
				System.out.println("File is stored at" + localFile.getPath());

			} else {
				System.out.println("Failed to create directory!");
			}
			request.getSession().setAttribute("event", event);

			//User user = (User)request.getSession().getAttribute("user");
			//Event ev = eventDao.saveEvent(event,user);
			//System.out.println(ev.getEventId());
		
			return new ModelAndView("create-tickets", "event", event);
		

	} catch (IllegalStateException e) {
		System.out.println("*** IllegalStateException: " + e.getMessage());
		return new ModelAndView("error", "errorMessage", "error while updating event");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println("*** IOException: " + e.getMessage());
		return new ModelAndView("error", "errorMessage", "error while updating event");
	}

	}
		
		
	
	//save the event and ticket type details in database
	@RequestMapping(value = "/event/createtickets", method = RequestMethod.POST)
	protected ModelAndView createTicketsForEvent(HttpServletRequest request) throws Exception {
		System.out.print("Create Ticket");
		try{
		//int eventId = Integer.parseInt(request.getParameter("eventId"));
		Event event = (Event)request.getSession().getAttribute("event");
		int num = Integer.parseInt(request.getParameter("numberOfTickets"));
		Set<TicketType> ticketTypes = new HashSet<TicketType>();
		for (int i=1;i<=num;i++){
	          String ticketTitle= request.getParameter("ticketTypes["+i+"].tickettitle");      
	          String freeOrPaid=request.getParameter("ticketTypes["+i+"].freeOrPaid");       
	          int ticketCount=Integer.parseInt(request.getParameter("ticketTypes["+i+"].ticketCount"));
	          double price =0.0;
	          if (freeOrPaid.equals("paid")){
	        	 price = Double.parseDouble(request.getParameter("ticketTypes["+i+"].price"));
	          }
	        
	          TicketType ticketType = new TicketType();
	          ticketType.setTicketTitle(ticketTitle);
	          ticketType.setPrice(price);
	          ticketType.setFreeOrPaid(freeOrPaid);
	          ticketType.setTicketNumForTicketType(ticketCount);
	          ticketType.setAvailableNumOfTickets(ticketCount);
	          ticketTypes.add(ticketType);
	        }
			event.setTicketTypes(ticketTypes);
			User user = (User)request.getSession().getAttribute("user");
			Event ev = eventDao.saveEvent(event,user);
			
			OrganizerProfile profile =user.getOrganizerProfile();
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
		catch(EventException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while updating event");
		}
	       

	}
	
	//method to redirect user to browse- events page
	@RequestMapping(value = "/event/browseevents", method = RequestMethod.GET)
	protected ModelAndView browse_Event(HttpServletRequest request) throws Exception {
		try{
			System.out.print("browse Events");
			List<String> eventTypes = eventDao.getEventCategories();
			request.getSession().setAttribute("eventTypes", eventTypes);		
			List<Event> events = eventDao.getApprovedEvents();
			 return new ModelAndView("browse-events","events", events);
		}
		
		 catch(Exception e){
				System.out.println("error"+ e.getMessage());
				return new ModelAndView("error", "errorMessage", "error while accessing the page");
				
			}
	}
	
	// method to return search list to the browse- events page based on the criteria chosen by the user 
	@RequestMapping(value = "/event/browseevents", method = RequestMethod.POST)
	protected ModelAndView browse_Events(HttpServletRequest request) throws Exception {
		
		try{
		System.out.print("Search Events");
		String keyword = request.getParameter("keyword");
		String eventTypeSelected=request.getParameter("cat");
		String freeOrPaid=request.getParameter("freeOrPaid");
		System.out.println(freeOrPaid);
		request.setAttribute("keyword", keyword);
		request.setAttribute("eventTypeSelected", eventTypeSelected);
		request.setAttribute("freeOrPaid", freeOrPaid);
		List<Event> events = eventDao.getApprovedEventsByKeyword(keyword,eventTypeSelected,freeOrPaid);
		if (events.isEmpty()){
			return new ModelAndView("browse-events","er", "Sorry there are no events with title \" "+keyword+ "\"");
		}
		else{
			 return new ModelAndView("browse-events","events", events);
		}
	}
		catch(Exception e){
			System.out.println("error"+ e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while accessing the page");
			
		}
		


	}
	
	//ajax method for providing suggestions of the event title present with the given keyword, event category and free/paid event
	@RequestMapping(value = "/event/searchevents", method = RequestMethod.GET)
    public @ResponseBody List<String> search_Event (HttpServletRequest request){
		 List<String> result = null;
		try{
        System.out.println("******INSIDE CONTROLLER FOR AUTO COMPLETE********");
        String name = request.getParameter("term");
        String cat = request.getParameter("cat");
        String freeOrPaid=request.getParameter("freeOrPaid");
        System.out.println(freeOrPaid);
        try {
			result = eventDao.search(name,cat,freeOrPaid);
		} catch (EventException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.println(result.size());

		}
		
		catch(Exception e){
			System.out.println("error"+ e.getMessage());

		}
		
		return result;
    }
	

	

}
