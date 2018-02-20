package com.eventangled.myapp.controllers;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eventangled.myapp.dao.EventDao;
import com.eventangled.myapp.dao.TicketDao;
import com.eventangled.myapp.dao.UserDao;
import com.eventangled.myapp.exceptions.UserException;
import com.eventangled.myapp.pojo.Event;
import com.eventangled.myapp.pojo.Ticket;
import com.eventangled.myapp.pojo.TicketType;
import com.eventangled.myapp.pojo.TicketTypeCount;
import com.eventangled.myapp.pojo.User;
import com.eventangled.myapp.pojo.UserProfile;

@RequestMapping(value ="/ticket/*")
@Controller
public class TicketsController {
	
	@Autowired
	TicketDao ticketDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	EventDao eventDao;
	
	//method for redirecting user to event details page 
	@RequestMapping(value = "/ticket/eventdetails", method = RequestMethod.GET)
	protected ModelAndView browseEvent(HttpServletRequest request) throws Exception {
		System.out.print("Event Details");
		try{
			User u= (User) request.getSession().getAttribute("user");
			
			if (u==null){
				 return new ModelAndView("Login");
			}
			else{
				int eventId = Integer.parseInt(request.getParameter("eventId"));
				Event event = ticketDao.getEvent(eventId);
				return new ModelAndView("event-details","event", event);
			}
		}catch(Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while booking tickets");
		}
		
		
		

	}
	
	//method for redirecting user to choose tickets count for booking tickets
	@RequestMapping(value = "/ticket/bookticket", method = RequestMethod.GET)
	protected ModelAndView book_ticket_get(HttpServletRequest request) throws Exception {
		try{
			System.out.print("Book Ticket");
			int eventId = Integer.parseInt(request.getParameter("eventId"));
			Event event = ticketDao.getEvent(eventId);
			return new ModelAndView("book-ticket","event", event);
		}
		catch(Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while booking tickets");
		}
	
		
	}
	
	//method for redirecting user to ticket details page for filling in payment details, event attendee details and billing address details
	@RequestMapping(value = "/ticket/bookticket", method = RequestMethod.POST)
	protected ModelAndView book_ticket(HttpServletRequest request) throws Exception {
		System.out.print("Book Ticket");
		
		try{
			int eventId = Integer.parseInt(request.getParameter("eventId"));
			int numberOfTicketTypes= Integer.parseInt(request.getParameter("numberOfTickets"));
			Event event = ticketDao.getEvent(eventId);
			List<TicketTypeCount> typeCount = new ArrayList<TicketTypeCount>();
			double orderTotal = 0;
			StringBuilder build = new StringBuilder();
			
			
			for(int i = 1;i<=numberOfTicketTypes;i++){
				int ticketTypeId = Integer.parseInt(request.getParameter("ticketTypeId"+i));
				int count = Integer.parseInt(request.getParameter("count"+i));
				TicketType ticketType = ticketDao.getTicketType(ticketTypeId);
				if (count>ticketType.getAvailableNumOfTickets()){
					build.append("Only "+ ticketType.getAvailableNumOfTickets() + " tickets are available for ticketType : "+ticketType.getTicketTitle());
				}	
			}
			
			if (!build.toString().isEmpty()){
				request.setAttribute("err", build.toString());
				return new ModelAndView("book-ticket","event", event);
			}

			for(int i = 1;i<=numberOfTicketTypes;i++){
				int ticketTypeId = Integer.parseInt(request.getParameter("ticketTypeId"+i));
				int count = Integer.parseInt(request.getParameter("count"+i));
				TicketType ticketType = ticketDao.getTicketType(ticketTypeId);
				TicketTypeCount t = new TicketTypeCount();
				t.setCount(count);
				t.setTicketType(ticketType);
				t.setTotal((ticketType.getFee()+ticketType.getPrice())*count);
				typeCount.add(t);
				orderTotal=orderTotal+t.getTotal();
				
			}
			
			
			System.out.println(event.getEventId());
			request.getSession().setAttribute("event", event);
			request.getSession().setAttribute("orderTotal", orderTotal);
			request.getSession().setAttribute("typeCount", typeCount);
			return new ModelAndView("ticket-details","typeCount", typeCount);
			
		}
		catch(Exception e) {
				System.out.println("Exception: " + e.getMessage());
				return new ModelAndView("error", "errorMessage", "error while booking tickets");
			}
	
	}
	
	//saving ticket details in database and sending ticket details to user's email
	@RequestMapping(value = "/ticket/ticketlastStep", method = RequestMethod.POST)
	protected ModelAndView book_ticket_lastStep(HttpServletRequest request) throws Exception {
		System.out.println("Ticket Booking last Step");
		try{
		List<TicketTypeCount> typeCount =(List<TicketTypeCount>)request.getSession().getAttribute("typeCount");
		User u = (User) request.getSession().getAttribute("user");
		
			String line1 = request.getParameter("addressLine1");
			String line2 = request.getParameter("addressLine2");
			String city = request.getParameter("city");
			String state= request.getParameter("state");
			String country = request.getParameter("country");
			String zip = request.getParameter("zip");
			String cardType=request.getParameter("cardType");
			String cardNumber=request.getParameter("cardNumber");
			String month=request.getParameter("month");
            String year=request.getParameter("year");
            int cvv= Integer.parseInt(request.getParameter("cvv"));
            
            if(u.getUserProfile()!=null){
            	UserProfile pro = u.getUserProfile();
            	pro.setCardNumber(cardNumber);
            	pro.setCardType(cardType);
            	pro.setCvv(cvv);
            	pro.setMonth(month);
            	pro.setYear(year);
            	pro.setAddressLine1(line1);
            	pro.setAddressLine2(line2);
            	pro.setCity(city);
            	pro.setState(state);
            	pro.setCountry(country);
            	pro.setZip(zip);
            	UserProfile prof = userDao.saveUserProfile(pro,u);
            }
            else{
            	UserProfile userProfile = new UserProfile();
            	userProfile.setCardNumber(cardNumber);
            	userProfile.setCardType(cardType);
            	userProfile.setCvv(cvv);
            	userProfile.setMonth(month);
            	userProfile.setYear(year);
            	userProfile.setAddressLine1(line1);
            	userProfile.setAddressLine2(line2);
            	userProfile.setCity(city);
   			 	userProfile.setState(state);
   			 	userProfile.setCountry(country);
   			 	userProfile.setZip(zip);
            	 UserProfile prof = userDao.saveUserProfile(userProfile,u);
            	
            }
            
		ArrayList <Ticket> ticketList = new ArrayList<Ticket>();
		for (TicketTypeCount t:typeCount){
			
			int ticketTypeId = t.getTicketType().getTicketTypeId();
			int count =t.getCount();
			double total = t.getTotal();
			
			for (int i=1;i<=count;i++){
				Ticket ticket = new Ticket();
				String first= request.getParameter("attendeeFirstName"+i+"ttId"+ticketTypeId);
				System.out.println(first);
				String last = request.getParameter("attendeeLastName"+i+"ttId"+ticketTypeId);
				System.out.println(last);
				ticket.setAttendeeFirstName(first);
				ticket.setAttendeeLastName(last);
				ticket.setTicketType(t.getTicketType());
				ticket.setUser(u);
				ticket.setBookingDate(new Date());
				ticketList.add(ticket);
			}
			
			t.getTicketType().setAvailableNumOfTickets(t.getTicketType().getAvailableNumOfTickets()-count);
			t.getTicketType().setSoldNumOfTickets(t.getTicketType().getSoldNumOfTickets()+count);
			t.getTicketType().setCommission(t.getTicketType().getSoldNumOfTickets()*t.getTicketType().getFee());
			t.getTicketType().setRevenuePerTicketType(t.getTicketType().getRevenuePerTicketType()+t.getTicketType().getTotalPrice()*count);

		}

		Event event = (Event)(request.getSession().getAttribute("event"));
		
		System.out.println(event.getEventId());
		StringBuilder build = new StringBuilder();
		build.append("Ticket Details for event :"+event.getEventTitle()+"\n");
		build.append("Event Date :" + event.getStartDate()+"\n");
		for (Ticket ticket:ticketList){
			ticketDao.saveTicket(ticket);
			build.append(ticket.getTicketType().getTicketTitle()+" "+ticket.getTicketId()+" " + ticket.getAttendeeFirstName()+ " " +ticket.getAttendeeLastName() +"\n");
		}
		build.append("Order total : " + request.getSession().getAttribute("orderTotal")+"\n");
		

		for (TicketTypeCount t:typeCount){
			ticketDao.updateTicketType(t.getTicketType());
		}
		
		
		Event e=ticketDao.updateEventRevenue(event);
		List<Event> approvedEvents = eventDao.getApprovedEvents();
		
		Email email = new SimpleEmail();
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("temporarywebtools2017@gmail.com", "temporary"));
        email.setHostName("smtp.gmail.com");//if a server is capable of sending email.
        email.setSSL(true);//setSSLOnConnect(true);
        email.setFrom("admin@eventory.com");
        email.setSubject("Ticket booking Successfull!!!");
        email.setMsg(build.toString());
        email.addTo(u.getEmail());
        email.setTLS(true);//startTLS.enable.true
        email.send();
        System.out.println("EMAIL SENT ");

		return new ModelAndView("tickets-booked","bookingmessage","Tickets booked successfully! Ticket details have been mailed to the registered email!");
		}
		catch (UserException e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while booking tickets");
		}
	}
}
