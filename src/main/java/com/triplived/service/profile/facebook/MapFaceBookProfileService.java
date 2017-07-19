package com.triplived.service.profile.facebook;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.triplived.controller.login.facebook.pojo.FaceBookProfile;
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

/**
 * 
 * @author Santosh Joshi
 *
 */
@Service(value="facebokprofilesercice")
public class MapFaceBookProfileService implements MapProfileFromProviderService<FaceBookProfile> {

    private static final Logger logger = LoggerFactory.getLogger(MapFaceBookProfileService.class );
	
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
	public Person mapProfileInformation(FaceBookProfile faceBookProfile) throws Exception{
		
		//to check facebook email with email existing in our Database
		//if exists then user is from site not facebook
		
		PersonDb person = null;
		UserAccountsDb userAccount = null;
		
		userAccount = getUserAccount(faceBookProfile, userAccount);
		
		logger.warn("Person entered Fb service method");
		if(userAccount == null) {
		    logger.warn("Person Signed Up from face book :  name is  {}  - {} and person mail id: {}",  faceBookProfile.getFirst_name(), faceBookProfile.getLast_name(), faceBookProfile.getEmail());
			person = new PersonDb();
			person.setUserFrom(faceBookProfile.getUserFrom());
			person.setGender(getGender(faceBookProfile));
			person.setName(faceBookProfile.getFirst_name());
			person.setLastName(faceBookProfile.getLast_name());
			person.setId(Long.parseLong(faceBookProfile.getId()));
			person.setEmail(faceBookProfile.getEmail());
			person.setDeviceId(faceBookProfile.getDeviceId());
			person.setAboutMe("");
			person.setMobile("");
			person.setStatus("A");
			//person.setFbAccessToken(faceBookProfile.getFb_access_token());
			person.setCreatedDate(new Date());
			
			try {
				if(!StringUtils.isEmpty(faceBookProfile.getBirthday())){
					//System.out.println(faceBookProfile.getBirthday());
					person.setDateOfBirth(new java.util.Date(faceBookProfile.getBirthday()));
				}
			}catch(Exception e) {
				logger.error("Problems in birthday field for user - {} - {}",faceBookProfile.getFirst_name(), faceBookProfile.getLast_name());
			}
			
			personRepository.save(person);
			
			if(null != faceBookProfile.getDeviceId()) {
				UserDynamicDb obj = new UserDynamicDb();
				obj.setUserId(person.getPersonId());
				obj.setDeviceId(person.getDeviceId());
				obj.setUpdateDate(new Date());
				obj.setFbAccessToken(faceBookProfile.getFb_access_token());
				userDeviceMappingDao.updateUserDeviceMapping(obj);
			}
			

			//Update secondary table 
			userAccount = new UserAccountsDb();
			userAccount.setAccountId(faceBookProfile.getId());
			userAccount.setEmail(faceBookProfile.getEmail());
			userAccount.setUserFrom(UserFrom.FACEBOOK);
			userAccount.setCreatedDate(new Date());
			userAccount.setUserImage(faceBookProfile.getPersonImage());
			userAccount.setPerson(person);
			
			userAccountsDao.save(userAccount);
			
			if(StringUtils.isNotEmpty(faceBookProfile.getEmail())) {
				wm.sendMail(faceBookProfile.getEmail(), faceBookProfile.getFirst_name());
			}
			
			/*
			Integer personIndex = personRepository.checkInterestifyName(checkName) + 1;
			person.setInterestifyName(person.getName()+"."+person.getLastName()+"."+ personIndex.toString());
			person.setPassword(password);
			person = personRepository.save(person);*/

		}else{
			logger.warn("Person entered else part of Fb service method {}", faceBookProfile.getDeviceId());
			person = userAccount.getPerson();
			
			if(StringUtils.isNotEmpty(faceBookProfile.getPersonImage())){
				userAccount.setUserImage(faceBookProfile.getPersonImage());	
				userAccountsDao.saveObject(userAccount);
			}
			
			setUpdatedData(faceBookProfile, person);
			person.setUpdatedDate(new Date());
			personRepository.update(person);
			
			if(null != faceBookProfile.getDeviceId()) {
				UserDynamicDb obj = userDeviceMappingDao.getMapping(person.getPersonId(), faceBookProfile.getDeviceId());
				if(null == obj) {
					obj = new UserDynamicDb();
					obj.setUserId(person.getPersonId());
					obj.setDeviceId(faceBookProfile.getDeviceId());
					obj.setUpdateDate(new Date());
					if(StringUtils.isNotEmpty(faceBookProfile.getFb_access_token())){
						obj.setFbAccessToken(faceBookProfile.getFb_access_token());
					}
					userDeviceMappingDao.updateUserDeviceMapping(obj);
				}else {
					logger.warn(" Fb Token Updated service method {} ", faceBookProfile.getDeviceId());
					obj.setUpdateDate(new Date());
					
					if(StringUtils.isNotEmpty(faceBookProfile.getFb_access_token())){
						obj.setFbAccessToken(faceBookProfile.getFb_access_token());
					}
					userDeviceMappingDao.updateUserDeviceMapping(obj);
				}
			}
		}
		
		
		Person p = new Person();
		p.setName(person.getName());
		p.setNickName(person.getNickName());
		p.setEmail(person.getEmail());
		p.setGender(person.getGender());
		p.setLastName(person.getLastName());
		p.setMiddleName(person.getMiddleName());
		p.setId(person.getId());
		p.setPersonId(person.getPersonId());
		p.setUserFrom(UserFrom.FACEBOOK);
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
		
		// as there are more information of FaceBookProfile we also need to maintain that information
	}

	private void setUpdatedData(FaceBookProfile faceBookProfile, PersonDb person) {
		if(StringUtils.isNotEmpty(faceBookProfile.getLast_name())){
			person.setLastName(faceBookProfile.getLast_name().trim());
		}
		
		if(StringUtils.isEmpty(person.getEmail()) && StringUtils.isNotEmpty(faceBookProfile.getEmail())) {
			person.setEmail(faceBookProfile.getEmail().trim());
		}
		
		if(StringUtils.isNotEmpty(faceBookProfile.getAboutMe())){
			person.setAboutMe(faceBookProfile.getAboutMe().trim());
		}
		
		if(StringUtils.isNotEmpty(faceBookProfile.getAddress())){
			person.setLocation(faceBookProfile.getAddress().trim());
		}
		
		if(StringUtils.isNotEmpty(faceBookProfile.getMobile())){
			person.setMobile(faceBookProfile.getMobile().trim());
		}
		
		try {
			if(StringUtils.isNotEmpty(faceBookProfile.getBirthday())){
				//System.out.println(faceBookProfile.getBirthday());
				person.setDateOfBirth(new java.util.Date(faceBookProfile.getBirthday()));
			}
		}catch(Exception e) {
			logger.error("Problems in birthday field for user - {} - {}",faceBookProfile.getFirst_name(), faceBookProfile.getLast_name());
		}
		
		if(StringUtils.isNotEmpty(faceBookProfile.getLink())){
			person.setWebsite(faceBookProfile.getLink().trim());
		}
	}

	private UserAccountsDb getUserAccount(FaceBookProfile facebookProfile, UserAccountsDb userAccount) {
		PersonDb person;
		List<UserAccountsDb> userAccounts;
		if(StringUtils.isNotEmpty(facebookProfile.getEmail())){
			userAccounts = userAccountsDao.getUserAccountByEmails(facebookProfile.getEmail());
			
			if(userAccounts != null && userAccounts.size() > 0){
				
				for (UserAccountsDb userAccountsDb : userAccounts) {
					 if(userAccountsDb.getAccountId().equalsIgnoreCase(facebookProfile.getId())){
						 userAccount = userAccountsDb ;
						 break;
					 }
				}
				
				//we have some accounts but we were not able to find a FACEBOOK ACCOUNT
				if(userAccount == null){
					
					//CREATE A FACEBOOK ACCOUNT 
					UserAccountsDb userAccountOther = userAccounts.get(0);
					person = userAccountOther.getPerson();
					
					//we might not get email before so might have missed this
					UserAccountsDb newUserAccountDb = userAccountsDao.getUserAccount(facebookProfile.getId(), null);
					
					if(newUserAccountDb == null){
						userAccount = new UserAccountsDb();
						userAccount.setAccountId(facebookProfile.getId());
						userAccount.setEmail(facebookProfile.getEmail());
						userAccount.setUserFrom(UserFrom.FACEBOOK);
						userAccount.setCreatedDate(new Date());
						userAccount.setUserImage(facebookProfile.getPersonImage());
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
			} else{
				
				// check with face book id if it exists: with email we got no results
				// we need to update email ids to all account
				userAccount = userAccountsDao.getUserAccount(facebookProfile.getId(), null);
				if(userAccount != null){
					userAccount.setEmail(facebookProfile.getEmail());
					//updating email
					userAccountsDao.update(userAccount);
				}
			}
			
		}else{
			userAccount = userAccountsDao.getUserAccount(facebookProfile.getId(), facebookProfile.getEmail());	
		}
		return userAccount;
	}
	

	private String getGender(FaceBookProfile faceBookProfile) {
		if(StringUtils.isNotEmpty(faceBookProfile.getGender())){
			
			if(faceBookProfile.getGender().toLowerCase().equals("male")){
				return "M";
			}else{
				return "F";
			}
		}
		return "";
	}
/*
	@Override
	public void updateProfile(Person person, PersonProfile profile) {
		// TODO Auto-generated method stub
		
	}*/

}
