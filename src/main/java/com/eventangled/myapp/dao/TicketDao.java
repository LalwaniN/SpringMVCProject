package com.eventangled.myapp.dao;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.eventangled.myapp.exceptions.EventException;
import com.eventangled.myapp.exceptions.TicketException;
import com.eventangled.myapp.pojo.Event;
import com.eventangled.myapp.pojo.Ticket;
import com.eventangled.myapp.pojo.TicketType;


public class TicketDao extends Dao {
	
	public Event getEvent(int eventId) {
		 Query q = getSession().createQuery("from Event where eventId =:eventId ");
		 q.setInteger("eventId", eventId);
         Event event = (Event)q.uniqueResult();
         return event;

	}


	public TicketType getTicketType(int ticketTypeId) {
		// TODO Auto-generated method stub
		
		Query q = getSession().createQuery("from TicketType where ticketTypeId =:ticketTypeId ");
		 q.setInteger("ticketTypeId", ticketTypeId);
       TicketType ticketType = (TicketType)q.uniqueResult();
		return ticketType;
	}


	public void saveTicket(Ticket ticket) throws TicketException{
		// TODO Auto-generated method stub
		try {
			begin();
			getSession().save(ticket);
			commit();
			
		}
			catch (HibernateException e) {
				rollback();
				throw new TicketException("Exception while creating Ticket: " + e.getMessage());
			}
		
		
	}


	public void updateTicketType(TicketType ticketType) throws TicketException {
		// TODO Auto-generated method stub
		
		try {
			begin();
			getSession().merge(ticketType);
			commit();
			
		}
			catch (HibernateException e) {
				rollback();
				throw new TicketException("Exception while update ticketType: " + e.getMessage());
			}
		
	}
	
	public void closeSession(){
		close();
	}


	public Event updateEventRevenue(Event event) throws TicketException {
		try {
			Query q = getSession().createQuery("select sum(revenuePerTicketType) from TicketType where eventId =:eventId");
			q.setInteger("eventId", event.getEventId());
			//double revenue =(Double) q.uniqueResult();
			List<Double> list = q.list();
			System.out.println(list.get(0));
			event.setTotalRevenue(list.get(0));
		
			
			Query q2 = getSession().createQuery("select sum(commission) from TicketType where eventId =:eventId");
			q2.setInteger("eventId", event.getEventId());
			//double revenue =(Double) q.uniqueResult();
			List<Double> list2 = q2.list();
			System.out.println(list2.get(0));
			event.setTotalCommission(list2.get(0));

			begin();
			getSession().merge(event);
			commit();
			return event;
			
		}
			catch (HibernateException e) {
				rollback();
				throw new TicketException("Exception while update event: " + e.getMessage());
			}
	}


	public List<Ticket> getAllTickets(int eventId) {
		// TODO Auto-generated method stub
		Event event = getEvent(eventId);
	
		Set<TicketType> ticketTypes =event.getTicketTypes();
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		//get all tickets with tickettypes as above
		
		for (TicketType ticketType : ticketTypes){
			for (Ticket ticket : ticketType.getTickets()){
				tickets.add(ticket);
			}
		}
		return tickets;

	}
	

}
