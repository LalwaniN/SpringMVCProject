package com.eventangled.myapp.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.eventangled.myapp.exceptions.EventException;

import com.eventangled.myapp.pojo.Event;
import com.eventangled.myapp.pojo.EventCategory;
import com.eventangled.myapp.pojo.OrganizerProfile;
import com.eventangled.myapp.pojo.Ticket;
import com.eventangled.myapp.pojo.TicketType;
import com.eventangled.myapp.pojo.User;



public class EventDao extends Dao{
	
	@Autowired
	OrganizerDao orgDao;
	
	public List<String> getEventCategories(){
		
		 Query q = getSession().createQuery("select eventType from EventCategory");
         List<String> eventCategories = q.list();
         return eventCategories;

	}
	
	
	public Event saveEvent(Event ev,User u) throws EventException{
		try {
				begin();
				System.out.println("inside DAO");
				String eventType = ev.getEventCategoryName();
				 Query q = getSession().createQuery("from EventCategory where eventType = :eventType");
				 q.setString("eventType", eventType);
				 EventCategory cat = (EventCategory) q.uniqueResult();
				 ev.setEventCategory(cat);
				 OrganizerProfile orgProfile = getOrganizerProfile2(u);
				 ev.setOrganizerProfile(orgProfile);
				 getSession().save(ev);
				 
				 for (TicketType type : ev.getTicketTypes()){
					 type.setEvent(ev);
					 type.setFee(0.05*type.getPrice());
					 if (!type.getFreeOrPaid().equals("free")){
						 type.setTotalPrice(type.getPrice()+type.getFee());
					 }
					 else{
						 type.setTotalPrice(0);
					 }
					 
					 getSession().update(type);
				 }
				 
				commit();
				return ev;
			}
			
		catch (HibernateException e) {
			rollback();
			throw new EventException("Exception while creating user: " + e.getMessage());
		}
	}
	
	
	public OrganizerProfile getOrganizerProfile2(User u){
		int userId = u.getUserId();
		Query q = getSession().createQuery("from OrganizerProfile where userId =:userId");
		q.setInteger("userId", userId);
		OrganizerProfile organizerProfile = (OrganizerProfile) q.uniqueResult();
		return organizerProfile;
		
	}
	
	
	public Event saveTicketTypes(Set<TicketType> ticketTypes,int eventId) throws EventException{
		System.out.println("inside DAO");
		Query q = getSession().createQuery("from Event where eventId =:eventId");
		q.setInteger("eventId", eventId);
		Event event = (Event) q.uniqueResult();
		
		for (TicketType ticket : ticketTypes){
			ticket.setEvent(event);
		}
		try {
			begin();
			event.setTicketTypes(ticketTypes);
			getSession().save(event);
			commit();
		}
			catch (HibernateException e) {
				rollback();
				throw new EventException("Exception while creating event: " + e.getMessage());
			}
		return event;
		
	}
	
	
	public List<Event> getApprovedEvents(){
		
		Query q = getSession().createQuery("from Event e where e.approvalStatus='Approved'");
        List<Event> event = q.list();
        List<Event> events = new ArrayList<Event>();
        for (Event e : event){
        	for (TicketType tt : e.getTicketTypes()){
        		if (tt.getAvailableNumOfTickets()>0){
        			events.add(e);
        			break;
        		}
        	}
        }
        
        return events;

	}
	

	public List<Event> getPendingEvents(){
		
		Query q = getSession().createQuery("from Event e where e.approvalStatus='Pending'");
        List<Event> event = q.list();
        return event;

	}
	
	public List<Event> getAllEvents(){
		
		Query q = getSession().createQuery("from Event ");
        List<Event> event = q.list();
        return event;

	}
	
	
	public Event approveEvent(int eventId) throws EventException{
		
		Query q = getSession().createQuery("from Event where eventId =:eventId");
		q.setInteger("eventId", eventId);
		Event event = (Event) q.uniqueResult();
		try {
			begin();
			event.setApprovalStatus("Approved");
			getSession().update(event);
			commit();
			
		}
			catch (HibernateException e) {
				rollback();
				throw new EventException("Exception while approving event: " + e.getMessage());
			}
		
		return event;
			
	}
	
	
	
	public Event rejectEvent(int eventId) throws EventException{
		
		Query q = getSession().createQuery("from Event where eventId =:eventId");
		q.setInteger("eventId", eventId);
		Event event = (Event) q.uniqueResult();
		try {
			begin();
			event.setApprovalStatus("Rejected");
			getSession().update(event);
			commit();
			
		}
			catch (HibernateException e) {
				rollback();
				throw new EventException("Exception while Rejected event: " + e.getMessage());
			}
		
		return event;
			
	}
	
	
public Event cancelEvent(int eventId) throws EventException{
		
		Query q = getSession().createQuery("from Event where eventId =:eventId");
		q.setInteger("eventId", eventId);
		Event event = (Event) q.uniqueResult();
		try {
			begin();
			event.setApprovalStatus("Cancelled");
			getSession().update(event);
			commit();
			
		}
			catch (HibernateException e) {
				rollback();
				throw new EventException("Exception while creating event: " + e.getMessage());
			}
		
		return event;
			
	}
	
	public List<String> search( String keyword,String cat,String freeOrPaid) throws EventException{
		/*
		if (!cat.equals("all")){
			Query q = getSession().createQuery("from EventCategory where eventType =:cat");
			q.setString("cat", cat);
			EventCategory eventType=(EventCategory)q.uniqueResult();

			Criteria crit = getSession().createCriteria(Event.class);
			if(!keyword.equals("")){
				crit.add(Restrictions.ilike("eventTitle", keyword,MatchMode.START));
			}
			crit.add(Restrictions.eq("approvalStatus", "Approved")).add(Restrictions.eq("eventCategory", eventType));
			crit.setProjection(Projections.property("eventTitle"));
			List<String> events =crit.list(); 
			return events;
		}
		else{
			Criteria crit = getSession().createCriteria(Event.class);
			if(!keyword.equals("")){
				crit.add(Restrictions.ilike("eventTitle", keyword,MatchMode.START));
			}
			crit.add(Restrictions.eq("approvalStatus", "Approved"));
			crit.setProjection(Projections.property("eventTitle"));
			List<String> events =crit.list();
			return events;
		}
		*/
		
		List<Event> eventList = getApprovedEventsByKeyword(keyword, cat, freeOrPaid);
		List<String> events = new ArrayList<String>();
		for (Event event : eventList){
			events.add(event.getEventTitle());
		}
		
		return events;
		
	}
	
	
	public List<Event> getApprovedEventsByKeyword(String keyword,String eventTypeSelected, String freeOrPaid){
		
		if (!eventTypeSelected.equals("all")){
			if (freeOrPaid.equals("all")){
			Query q = getSession().createQuery("from EventCategory where eventType =:cat");
			q.setString("cat", eventTypeSelected);
			EventCategory eventType=(EventCategory)q.uniqueResult();

			Criteria crit = getSession().createCriteria(Event.class);
			if(!keyword.equals("")){
				crit.add(Restrictions.ilike("eventTitle", keyword,MatchMode.ANYWHERE));
			}
			crit.add(Restrictions.eq("approvalStatus", "Approved")).add(Restrictions.eq("eventCategory", eventType));
			List<Event> events =crit.list();
			
			//available events
			List<Event> listOfEvents = new ArrayList<Event>();
			for(Event event :events){
				for(TicketType ticketType: event.getTicketTypes()){
		        		if (ticketType.getAvailableNumOfTickets()>0){
		        			listOfEvents.add(event);
		        			break;
		        		}
				}
			}

			return listOfEvents;

			}
			else{
				//freeOrPaid
				Query q = getSession().createQuery("from EventCategory where eventType =:cat");
				q.setString("cat", eventTypeSelected);
				EventCategory eventType=(EventCategory)q.uniqueResult();

				Criteria crit = getSession().createCriteria(Event.class);
				if(!keyword.equals("")){
					crit.add(Restrictions.ilike("eventTitle", keyword,MatchMode.ANYWHERE));
				}
				crit.add(Restrictions.eq("approvalStatus", "Approved")).add(Restrictions.eq("eventCategory", eventType));
				List<Event> events =crit.list();
				
				List<Event> eventList = new ArrayList<Event>();
				for(Event event :events){
					for(TicketType ticketType: event.getTicketTypes()){
						if (ticketType.getFreeOrPaid().equals(freeOrPaid)){
							eventList.add(event);
							break;
						}
					}
				}
				
				List<Event> listOfEvents = new ArrayList<Event>();
				for(Event event :eventList){
					for(TicketType ticketType: event.getTicketTypes()){
			        		if (ticketType.getAvailableNumOfTickets()>0){
			        			listOfEvents.add(event);
			        			break;
			        		}
					}
				}

				
				return listOfEvents;
			}
		}
		else{
			if (freeOrPaid.equals("all")){
			Criteria crit = getSession().createCriteria(Event.class);
			if(!keyword.equals("")){
				crit.add(Restrictions.ilike("eventTitle", keyword,MatchMode.ANYWHERE));
			}
			crit.add(Restrictions.eq("approvalStatus", "Approved"));
			List<Event> events =crit.list();

			List<Event> listOfEvents = new ArrayList<Event>();
			for(Event event :events){
				for(TicketType ticketType: event.getTicketTypes()){
		        		if (ticketType.getAvailableNumOfTickets()>0){
		        			listOfEvents.add(event);
		        			break;
		        		}
				}
			}

			
			return listOfEvents;
			
			
			}
			else{
				Criteria crit = getSession().createCriteria(Event.class);
				if(!keyword.equals("")){
					crit.add(Restrictions.ilike("eventTitle", keyword,MatchMode.ANYWHERE));
				}
				crit.add(Restrictions.eq("approvalStatus", "Approved"));
				List<Event> events =crit.list();
				
				List<Event> eventList = new ArrayList<Event>();
				for(Event event :events){
					for(TicketType ticketType: event.getTicketTypes()){
						if (ticketType.getFreeOrPaid().equals(freeOrPaid)){
							eventList.add(event);
							break;
						}
					}
				}
				
				//checking if tickets are available
				List<Event> listOfEvents = new ArrayList<Event>();
				for(Event event :eventList){
					for(TicketType ticketType: event.getTicketTypes()){
			        		if (ticketType.getAvailableNumOfTickets()>0){
			        			listOfEvents.add(event);
			        			break;
			        		}
					}
				}

				
				return listOfEvents;
			}
		}
	}
	
	
	
	

}
