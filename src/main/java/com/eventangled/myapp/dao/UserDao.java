package com.eventangled.myapp.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.eventangled.myapp.exceptions.UserException;
import com.eventangled.myapp.pojo.User;
import com.eventangled.myapp.pojo.UserProfile;



public class UserDao extends Dao {

	public User register(User u)
			throws UserException {
		try {
			begin();
			System.out.println("inside DAO");
			getSession().save(u);
			commit();
			return u;

		} catch (HibernateException e) {
			rollback();
			throw new UserException("Exception while creating user: " + e.getMessage());
		}
	}
	
	
	public UserProfile getUserProfile(User u)throws UserException {
		try{
			
			String email = u.getEmail();
			String password = u.getPassword();
			
			Query q = getSession().createQuery("from User where email = :email and password = :password");
			q.setString("email", email);
			q.setString("password", password);			
			User userr = (User) q.uniqueResult();
			int userProfileId = userr.getUserId();
			
			Query q2 = getSession().createQuery("from UserProfile where userProfileId = :userProfileId");
			q2.setInteger("userProfileId", userProfileId);
			UserProfile profile = (UserProfile)q2.uniqueResult();
			return profile;
			
		}
		catch(HibernateException e){
			throw new UserException("Exception while creating user: " + e.getMessage());
		}
	}
	
	public UserProfile saveUserProfile(UserProfile u, User user)
			throws UserException {
		try {
			begin();
			System.out.println("inside DAO");
			
			UserProfile profileIsPresent =getUserProfile(user);
			if (profileIsPresent==null){
				UserProfile profile = new UserProfile();
				String email = user.getEmail();
				String password = user.getPassword();
				Query q = getSession().createQuery("from User where email = :email and password = :password");
				q.setString("email", email);
				q.setString("password", password);			
				User userr = (User) q.uniqueResult();
				
				u.setUser(userr);
				userr.setUserProfile(u);
				getSession().save(userr);
				commit();
				return profile;
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
			
					profileIsPresent.setAddressLine1(u.getAddressLine1());
					profileIsPresent.setAddressLine2(u.getAddressLine2());
					profileIsPresent.setCity(u.getCity());
					profileIsPresent.setContact(u.getContact());
					profileIsPresent.setCountry(u.getCountry());
					profileIsPresent.setState(u.getState());
					profileIsPresent.setZip(u.getZip());
					profileIsPresent.setCardNumber(u.getCardNumber());
					profileIsPresent.setCardType(u.getCardType());
					profileIsPresent.setCvv(u.getCvv());
					profileIsPresent.setMonth(u.getMonth());
					profileIsPresent.setYear(u.getYear());
					
					
					userr.setUserProfile(profileIsPresent);
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
	
	
	
	public User get(String email , String password)throws UserException {
		
		try {
			begin();
			Query q = getSession().createQuery("from User where email = :email and password = :password");
			q.setString("email", email);
			q.setString("password", password);			
			User user = (User) q.uniqueResult();
			commit();
			return user;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get user " + email, e);
		}
		
		
	}
	
	
	public User updateUser(User u) throws UserException{
		try {
			begin();		
			getSession().update(u);
			commit();
			return u;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not update user " + u.getUserId(), e);
		}
		
		
		
	}
	
	

	public List<User> getAllUsers() {
		Query q = getSession().createQuery("from User");		
		List<User> users =q.list();
		return users;
	}
	
	public User checkIfUserPresent(String email){
	
		Query q = getSession().createQuery("from User where email = :email");
		q.setString("email", email);
		User user =(User) q.uniqueResult();
		return user;
	}

}
