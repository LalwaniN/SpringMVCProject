package com.eventangled.myapp.pojo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.commons.CommonsMultipartFile;


@Entity
@Table(name="organizer_table")
public class OrganizerProfile {
	
	public OrganizerProfile() {
		super();
	}
     @Id
     @GeneratedValue(strategy=GenerationType.IDENTITY)
	 @Column(name = "organizerId", unique = true, nullable = false)
		int organizerId;
	 
	 @Column(name = "organizerDescription")
	 String organizerDescription;
	 
	 @Column(name = "websiteUrl")
	 String websiteUrl;
	 
	 @Column(name = "faceBookPage")
	 String faceBookPage;
	 
	 @Column(name = "twitter")
	 String twitter;
	 
	 @Column(name = "instagram")
	 String instagram;
	 
	@Transient
	private CommonsMultipartFile organizerPhoto;
	 
	 @Column(name = "filename")
		String filename; 
	 
	 @OneToOne(cascade = CascadeType.ALL)
	 @JoinColumn(name="userId")
		private User user;
	 
		public String getFilename() {
			return filename;
		}
		public void setFilename(String filename) {
			this.filename = filename;
		}
		
		public int getOrganizerId() {
			return organizerId;
		}
		public void setOrganizerId(int organizerId) {
			this.organizerId = organizerId;
		}
		public String getOrganizerDescription() {
			return organizerDescription;
		}
		public void setOrganizerDescription(String organizerDescription) {
			this.organizerDescription = organizerDescription;
		}
		public String getWebsiteUrl() {
			return websiteUrl;
		}
		public void setWebsiteUrl(String websiteUrl) {
			this.websiteUrl = websiteUrl;
		}
		public String getFaceBookPage() {
			return faceBookPage;
		}
		public void setFaceBookPage(String faceBookPage) {
			this.faceBookPage = faceBookPage;
		}
		public String getTwitter() {
			return twitter;
		}
		public void setTwitter(String twitter) {
			this.twitter = twitter;
		}
		public String getInstagram() {
			return instagram;
		}
		public void setInstagram(String instagram) {
			this.instagram = instagram;
		}
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
		public CommonsMultipartFile getOrganizerPhoto() {
			return organizerPhoto;
		}
		public void setOrganizerPhoto(CommonsMultipartFile organizerPhoto) {
			this.organizerPhoto = organizerPhoto;
		}
	 
	
}
