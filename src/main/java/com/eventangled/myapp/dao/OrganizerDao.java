package com.eventangled.myapp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.eventangled.myapp.exceptions.UserException;
import com.eventangled.myapp.pojo.Event;
import com.eventangled.myapp.pojo.OrganizerProfile;
import com.eventangled.myapp.pojo.User;
import com.eventangled.myapp.pojo.UserProfile;

public class OrganizerDao extends Dao {
	
	public User getUser(int userId){
		Query q = getSession().createQuery("from User where userId = :userId");
		q.setInteger("userId", userId);			
		User userr = (User) q.uniqueResult();
		return userr;
	}
	
	
	public OrganizerProfile getOrganizerProfile(User u)throws UserException {
		try{
			
			String email = u.getEmail();
			String password = u.getPassword();
			
			Query q = getSession().createQuery("from User where email = :email and password = :password");
			q.setString("email", email);
			q.setString("password", password);			
			User userr = (User) q.uniqueResult();
			int userId = userr.getUserId();
			
			Query q2 = getSession().createQuery("from OrganizerProfile where userId = :userId");
			q2.setInteger("userId", userId);
			OrganizerProfile profile = (OrganizerProfile)q2.uniqueResult();
			return profile;
			
		}
		catch(HibernateException e){
			throw new UserException("Exception while creating user: " + e.getMessage());
		}
	}
	
	public OrganizerProfile saveOrganizerProfile(OrganizerProfile u, User user)
			throws UserException {
		try {
			begin();
			System.out.println("inside DAO");
			
			OrganizerProfile profileIsPresent =getOrganizerProfile(user);
			if (profileIsPresent==null){
				String email = user.getEmail();
				String password = user.getPassword();
				Query q = getSession().createQuery("from User where email = :email and password = :password");
				q.setString("email", email);
				q.setString("password", password);			
				User userr = (User) q.uniqueResult();
				u.setUser(userr);
				userr.setOrganizerProfile(u);
				userr.setOrganizerFlag(true);
				getSession().save(userr);
				commit();
				return u;
			}
			else{
				
				try {
		            begin();
					String email = user.getEmail();
					String password = user.getPassword();
					Query q = getSession().createQuery("from User where email = :email and password = :password");
					q.setString("email", email);
					q.setString("password", password);			
					User userr = (User) q.uniqueResult();
					
					profileIsPresent.setFaceBookPage(u.getFaceBookPage());
					profileIsPresent.setFilename(u.getFilename());
					profileIsPresent.setTwitter(u.getTwitter());
					profileIsPresent.setInstagram(u.getInstagram());
					profileIsPresent.setOrganizerDescription(u.getOrganizerDescription());
					profileIsPresent.setWebsiteUrl(u.getWebsiteUrl());
					userr.setOrganizerProfile(profileIsPresent);
		            getSession().update(userr);
		            commit();
		            return u;
		        } catch (HibernateException e) {
		            rollback();
		            throw new UserException("Exception while updating user: " + e.getMessage());
		        }
				
			}
			
			

		} catch (HibernateException e) {
			rollback();
			throw new UserException("Exception while creating user: " + e.getMessage());
		}
	}


	public List<Event> getOrganizerEvents(int id) {
		//Criteria crit = getSession().createCriteria(Event.class).add(Restrictions.eq("organizerId",id) );

		Query q = getSession().createQuery("from Event where organizerId = :id");
		q.setInteger("id", id);
		
		List events = q.list();
		return events;
	}
	
	
	
	
}
