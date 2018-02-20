package com.eventangled.myapp.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="event_category")
public class EventCategory {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "eventTypeId", unique=true, nullable = false)
	private int eventTypeId;

	@Column(name = "eventType")
	private String eventType;
	
	@OneToMany( mappedBy = "eventCategory")
	private Set<Event> events = new HashSet<Event>();

	public int getEventTypeId() {
		return eventTypeId;
	}

	public void setEventTypeId(int eventTypeId) {
		this.eventTypeId = eventTypeId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}
	
	
	
	
}
