package com.eventangled.myapp.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


@Entity
@Table(name="event_table")
public class Event {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "eventId", unique=true, nullable = false)
	private int eventId;
	
	@Column(name = "approvalStatus")
	private String approvalStatus ="Pending";
	
	@Column(name = "eventTitle")
	private String eventTitle;
	
	@Column(name = "eventDescription",length=200)
	private String eventDescription;
	
	//@DateTimeFormat(pattern="MM/dd/yy")
	@Column(name = "startDate")
	private Date startDate;
	
	@Column(name="startTime")
	private String startTime;
	
	//@DateTimeFormat(pattern="MM/dd/yy")
	@Column(name = "endDate")
	private Date endDate;
	
	@Column(name="endTime")
	private String endTime;
		
	@Column(name = "numberOfTickets")
	private int numberOfTickets;
	
	
	@Column(name = "eventAddressLine1")
	private String eventAddressLine1;
	
	@Column(name = "eventAddressLine2")
	private String eventAddressLine2;
	
	@Column(name = "eventCity")
	private String eventCity;
	
	@Column(name = "eventState")
	private String eventState;

	@Column(name = "eventCountry")
	private String eventCountry;
	
	@Column(name = "eventZipCode")
	private String eventZipCode;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="organizerId")
	private OrganizerProfile organizerProfile;
		
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="eventCategoryId")
	private EventCategory eventCategory;
	
	@OneToMany( mappedBy = "event",cascade = CascadeType.ALL)
	private Set<TicketType> ticketTypes = new HashSet<TicketType>();
	
	@Transient
	private String eventCategoryName;
	
	@Transient
	private CommonsMultipartFile eventPhoto;
	
	@Column(name="eventPhotoFileName")
	private String eventPhotoFileName;

	@Column(name="totalCommission")
	private double totalCommission;
	
	@Column(name="totalRevenue")
	private double totalRevenue;
	
	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getNumberOfTickets() {
		return numberOfTickets;
	}

	public void setNumberOfTickets(int numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
	}

	public OrganizerProfile getOrganizerProfile() {
		return organizerProfile;
	}

	public void setOrganizerProfile(OrganizerProfile organizerProfile) {
		this.organizerProfile = organizerProfile;
	}

	public EventCategory getEventCategory() {
		return eventCategory;
	}

	public void setEventCategory(EventCategory eventCategory) {
		this.eventCategory = eventCategory;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getEventAddressLine1() {
		return eventAddressLine1;
	}

	public void setEventAddressLine1(String eventAddressLine1) {
		this.eventAddressLine1 = eventAddressLine1;
	}

	public String getEventAddressLine2() {
		return eventAddressLine2;
	}

	public void setEventAddressLine2(String eventAddressLine2) {
		this.eventAddressLine2 = eventAddressLine2;
	}

	public String getEventCity() {
		return eventCity;
	}

	public void setEventCity(String eventCity) {
		this.eventCity = eventCity;
	}

	public String getEventState() {
		return eventState;
	}

	public void setEventState(String eventState) {
		this.eventState = eventState;
	}

	public String getEventCountry() {
		return eventCountry;
	}

	public void setEventCountry(String eventCountry) {
		this.eventCountry = eventCountry;
	}

	public String getEventZipCode() {
		return eventZipCode;
	}

	public void setEventZipCode(String eventZipCode) {
		this.eventZipCode = eventZipCode;
	}

	public Set<TicketType> getTicketTypes() {
		return ticketTypes;
	}

	public void setTicketTypes(Set<TicketType> ticketTypes) {
		this.ticketTypes = ticketTypes;
	}

	public String getEventCategoryName() {
		return eventCategoryName;
	}

	public void setEventCategoryName(String eventCategoryName) {
		this.eventCategoryName = eventCategoryName;
	}

	public CommonsMultipartFile getEventPhoto() {
		return eventPhoto;
	}

	public void setEventPhoto(CommonsMultipartFile eventPhoto) {
		this.eventPhoto = eventPhoto;
	}

	public String getEventPhotoFileName() {
		return eventPhotoFileName;
	}

	public void setEventPhotoFileName(String eventPhotoFileName) {
		this.eventPhotoFileName = eventPhotoFileName;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public double getTotalCommission() {
		return totalCommission;
	}

	public void setTotalCommission(double totalCommission) {
		this.totalCommission = totalCommission;
	}

	public double getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}
	
	
	
	
}
