package com.eventangled.myapp.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name = "user_table")
public class User{
	

	@Column(name = "firstName")
	String firstName;
	
	@Column(name = "lastName")
	String lastName;

	@Column(name = "userEmail")
	String email;
	
	@Column(name = "password")
	String password;
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "userId", unique=true, nullable = false)
	int userId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private UserProfile userProfile;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="organizerProfileId")
	private OrganizerProfile organizerProfile;
	
	@Column(name = "isOrganizerFlag")
	boolean isOrganizerFlag=false;
	
	@OneToMany( mappedBy = "user")
	private Set<Ticket> tickets = new HashSet<Ticket>();
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public UserProfile getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	public OrganizerProfile getOrganizerProfile() {
		return organizerProfile;
	}
	public void setOrganizerProfile(OrganizerProfile organizerProfile) {
		this.organizerProfile = organizerProfile;
	}
	
	public boolean isOrganizerFlag() {
		return isOrganizerFlag;
	}
	public void setOrganizerFlag(boolean isOrganizerFlag) {
		this.isOrganizerFlag = isOrganizerFlag;
	}
	public Set<Ticket> getTickets() {
		return tickets;
	}
	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	
}
