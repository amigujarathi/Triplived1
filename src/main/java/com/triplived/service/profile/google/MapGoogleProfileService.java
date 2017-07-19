package com.triplived.service.profile.google;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.controller.login.google.pojo.GoogleProfile;
import com.triplived.controller.profile.Person;
import com.triplived.controller.profile.UserFrom;
import com.triplived.dao.device.UserAccountsDao;
import com.triplived.dao.device.UserDynamicDao;
import com.triplived.dao.user.UserDao;
import com.triplived.entity.PersonDb;
import com.triplived.entity.UserAccountsDb;
import com.triplived.entity.UserDynamicDb;
import com.triplived.mail.client.WelcomeMail;
import com.triplived.service.profile.MapProfileFromProviderService;
import com.triplived.util.TripLivedUtil;

/**
 * 
 * @author Santosh Joshi
 *
 */
@Service(value="googleprofilesercice")
public class MapGoogleProfileService implements MapProfileFromProviderService<GoogleProfile> {

    private static final Logger logger = LoggerFactory.getLogger(MapGoogleProfileService.class );
	
    @Autowired
	private UserDao personRepository;
    
    @Autowired
    private UserDynamicDao userDeviceMappingDao;
    
    @Autowired
    private UserAccountsDao userAccountsDao;
		
   	/*@Autowired
   	@Qualifier("welcomeMailer")
   	private MailSender sender;*/
    
    @Autowired
	private WelcomeMail wm;
	
	@Override
	@Transactional
	public Person mapProfileInformation(GoogleProfile googleProfile) throws Exception{
		
		PersonDb person = null;
		UserAccountsDb userAccount = null;
		
		userAccount = getUserAccount(googleProfile, userAccount);
		
		//ACTUAL TASK

		if(userAccount == null){
			
			//create a new account
		   logger.warn("Person Signed Up from google :  name is  {}  and person mail id: {}", googleProfile.getName(), googleProfile.getEmail());
			person = new PersonDb();
			person.setUserFrom(googleProfile.getUserFrom());
			if(StringUtils.isNotEmpty(googleProfile.getName())){
				person.setName( WordUtils.capitalizeFully(googleProfile.getName())); 
			}else{
				 logger.warn("Person name is missing from google API for user email {} ", googleProfile.getEmail());
				person.setName(googleProfile.getEmail().split("@")[0]);
			}
			Long randomNum = (long) ((Math.random()*90000)+10000);
			person.setId(TripLivedUtil.generateId(randomNum));
			logger.warn("Person id set to be {} ", randomNum);
			person.setEmail(googleProfile.getEmail());
			person.setDeviceId(googleProfile.getDeviceId());
			person.setAboutMe("");
			person.setMobile("");
			person.setStatus("A");
			//person.setFbAccessToken(faceBookProfile.getFb_access_token());
			person.setCreatedDate(new Date());
			
			personRepository.save(person);
			
			//Update devices database
			if(null != googleProfile.getDeviceId()) {
				UserDynamicDb obj = new UserDynamicDb();
				obj.setUserId(person.getPersonId());
				obj.setDeviceId(person.getDeviceId());
				obj.setUpdateDate(new Date());
				userDeviceMappingDao.updateUserDeviceMapping(obj);
			}
			
			
			//Update secondary table 
			userAccount = new UserAccountsDb();
			userAccount.setAccountId(googleProfile.getId());
			userAccount.setEmail(googleProfile.getEmail());
			userAccount.setUserFrom(UserFrom.GOOGLE);
			userAccount.setCreatedDate(new Date());
			userAccount.setPerson(person);
			userAccount.setUserImage(googleProfile.getPersonImage());
			
			
			userAccountsDao.save(userAccount);
			
			//sending E-mails
			if(StringUtils.isNotEmpty(googleProfile.getEmail())) {
				wm.sendMail(googleProfile.getEmail(), person.getName());
			}
			
		}else{

			person = userAccount.getPerson(); 
			
			if(StringUtils.isNotEmpty(googleProfile.getPersonImage())){
				userAccount.setUserImage(googleProfile.getPersonImage());	
				userAccountsDao.saveObject(userAccount);
			}
			
			logger.warn("Person entered else part of Google service method {}", googleProfile.getDeviceId());
			setUpdatedData(googleProfile, person);
			person.setUpdatedDate(new Date());
			personRepository.update(person);
			
			if(StringUtils.isNotEmpty(googleProfile.getDeviceId())) {
				UserDynamicDb obj = userDeviceMappingDao.getMapping(person.getPersonId(), googleProfile.getDeviceId());
				if(null == obj) {
					obj = new UserDynamicDb();
					obj.setUserId(person.getPersonId());
					obj.setDeviceId(googleProfile.getDeviceId());
					obj.setUpdateDate(new Date());
					//obj.setFbAccessToken(faceBookProfile.getFb_access_token());
					userDeviceMappingDao.updateUserDeviceMapping(obj);
				}else {
					logger.warn(" Device Id date updated {} ", googleProfile.getDeviceId());
					obj.setUpdateDate(new Date());
				//	obj.setFbAccessToken(faceBookProfile.getFb_access_token());
					userDeviceMappingDao.updateUserDeviceMapping(obj);
				}
			}
		}
	 
		
		Person p = new Person();
		p.setName(person.getName());
		p.setNickName(person.getNickName());
		p.setGender(person.getGender());
		p.setEmail(person.getEmail());
	//	p.setLastName(person.getLastName());
	//	p.setMiddleName(person.getMiddleName());
		p.setId(person.getId());
		p.setPersonId(person.getPersonId());
		p.setUserFrom(UserFrom.GOOGLE);
		p.setAboutMe(person.getAboutMe());
		p.setUserImage(userAccount.getUserImage());
		p.setMobile(person.getMobile());
		p.setAddress(person.getLocation());
		p.setLink(person.getWebsite());
		try {
			if(person.getDateOfBirth() != null){
				p.setDateOfBirth( new SimpleDateFormat("dd/MM/yyyy").format(person.getDateOfBirth()));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return p;
		
	}

	private void setUpdatedData(GoogleProfile googleProfile, PersonDb person) {
		if(StringUtils.isNotEmpty(googleProfile.getLast_name())){
			person.setLastName(googleProfile.getLast_name().trim());
		}
		
		if(StringUtils.isEmpty(person.getEmail()) && StringUtils.isNotEmpty(googleProfile.getEmail())) {
			person.setEmail(googleProfile.getEmail().trim());
		}
		
		if(StringUtils.isNotEmpty(googleProfile.getAboutMe())){
			person.setAboutMe(googleProfile.getAboutMe().trim());
		}
		
		if(StringUtils.isNotEmpty(googleProfile.getAddress())){
			person.setLocation(googleProfile.getAddress().trim());
		}
		
		if(StringUtils.isNotEmpty(googleProfile.getMobile())){
			person.setMobile(googleProfile.getMobile().trim());
		}
		
		try {
			if(StringUtils.isNotEmpty(googleProfile.getBirthday())){
				//System.out.println(faceBookProfile.getBirthday());
				person.setDateOfBirth(new java.util.Date(googleProfile.getBirthday()));
			}
		}catch(Exception e) {
			logger.error("Problems in birthday field for user - {} - {}",googleProfile.getFirst_name(), googleProfile.getLast_name());
		}
		
		if(StringUtils.isNotEmpty(googleProfile.getLink())){
			person.setWebsite(googleProfile.getLink().trim());
		}
	}
	
	private UserAccountsDb getUserAccount(GoogleProfile googleProfile,
			UserAccountsDb userAccount) {
		PersonDb person;
		List<UserAccountsDb> userAccounts;
		if(StringUtils.isNotEmpty(googleProfile.getEmail())){
			userAccounts = userAccountsDao.getUserAccountByEmails(googleProfile.getEmail());
			
			if(userAccounts != null && userAccounts.size() > 0){
				
				for (UserAccountsDb userAccountsDb : userAccounts) {
					 if(userAccountsDb.getAccountId().equalsIgnoreCase(googleProfile.getId())){
						 userAccount = userAccountsDb ;
						 break;
					 }
				}
				
				//we have some accounts but we were not able to find a GOOGLE ACCOUNT
				if(userAccount == null){
					
					//CREATE A GOOGLE ACCOUNT 
					UserAccountsDb userAccountOther = userAccounts.get(0);
					person = userAccountOther.getPerson();
					
					//we might not get email before so might have missed this
					UserAccountsDb newUserAccountDb = userAccountsDao.getUserAccount(googleProfile.getId(), null);
					
					if(newUserAccountDb == null){
						userAccount = new UserAccountsDb();
						userAccount.setAccountId(googleProfile.getId());
						userAccount.setEmail(googleProfile.getEmail());
						userAccount.setUserFrom(UserFrom.GOOGLE);
						userAccount.setCreatedDate(new Date());
						userAccount.setUserImage(googleProfile.getPersonImage());
						userAccount.setPerson(person);
						
						userAccountsDao.save(userAccount);
					}else{
						
						if(StringUtils.isEmpty(newUserAccountDb.getEmail())){
							newUserAccountDb.setEmail(userAccountOther.getEmail());
							userAccountsDao.updateObject(newUserAccountDb);
							
							userAccount = newUserAccountDb;
						}
					}
				} 
			} else {

				// check with face book id if it exists: with email we got no
				// results
				// we need to update email ids to all account
				userAccount = userAccountsDao.getUserAccount(googleProfile.getId(), null);
				if(userAccount != null){
					userAccount.setEmail(googleProfile.getEmail());
					// updating email
					userAccountsDao.update(userAccount);
				}
			}
		} else {
			userAccount = userAccountsDao.getUserAccount(googleProfile.getId(), googleProfile.getEmail());	
		}
		return userAccount;
	}
  
}
