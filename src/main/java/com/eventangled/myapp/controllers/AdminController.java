package com.eventangled.myapp.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.eventangled.myapp.dao.EventDao;
import com.eventangled.myapp.pojo.Event;
import com.google.gson.Gson;



@Controller
@RequestMapping("/admin/*")
public class AdminController {
	
	@Autowired
	EventDao eventDao;

	//Method to redirect the admin to admin home page
	@RequestMapping(value = "/admin/adminhome", method = RequestMethod.GET)
	protected ModelAndView adminHome(HttpServletRequest request) throws Exception {
		
		try{
			System.out.print("Admin home");
			List<Event> allEvents = eventDao.getAllEvents();
			return new ModelAndView("admin-home", "events", allEvents);
		}
		
		catch(Exception e){
			
			System.out.println("error"+ e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while accessing the page");
			
		}
		

	}
	
	
	//method to redirect user to Approve Events page
	@RequestMapping(value = "/admin/approveevents", method = RequestMethod.GET)
	protected ModelAndView approve_Events(HttpServletRequest request) throws Exception {
		System.out.print("Approve Events");
		List<Event> pendingEvents = eventDao.getPendingEvents();
		return new ModelAndView("approve-events", "events", pendingEvents);

	}
	
	
	// method for approving events 
	@RequestMapping(value = "/admin/approveevent", method = RequestMethod.GET)
	//@ResponseBody
	protected ModelAndView approve_Event(HttpServletRequest request) throws Exception {
		try{
			System.out.print("Approve Event");
			int id =Integer.parseInt(request.getParameter("eventId"));
			Event e = eventDao.approveEvent(id);
			List<Event> pendingEvents = eventDao.getPendingEvents();
			return new ModelAndView("approve-events", "events", pendingEvents);
		}
		
		catch(Exception e){
			System.out.println("error"+ e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while accessing the page");
			
		}

	}
	
	//method for rejecting events
	@RequestMapping(value = "/admin/rejectevent", method = RequestMethod.GET)
	//@ResponseBody
	protected ModelAndView reject_Event(HttpServletRequest request) throws Exception {
		try{
			System.out.print("Reject Event");
			int id =Integer.parseInt(request.getParameter("eventId"));
			Event e = eventDao.rejectEvent(id);
			List<Event> pendingEvents = eventDao.getPendingEvents();
			return new ModelAndView("approve-events", "events", pendingEvents);
		}
		catch(Exception e){
			System.out.println("error"+ e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while accessing the page");
			
		}
		
	}
	
	//method for viewing registered user list
	@RequestMapping(value="/admin/userlist.xls", method=RequestMethod.GET)	
	public ModelAndView CreateXLSView() throws Exception{
		try{
			View view = new MyXlsView();
			return new ModelAndView(view);	
		}
		catch(Exception e){
			System.out.println("error"+ e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while accessing the page");
			
		}
		
	}

}
