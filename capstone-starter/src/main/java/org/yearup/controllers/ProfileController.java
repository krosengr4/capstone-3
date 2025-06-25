package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@RequestMapping("profile")
@CrossOrigin
public class ProfileController {
   private final ProfileDao profileDao;
   private final UserDao userDao;

   @Autowired
   public ProfileController(ProfileDao profileDao, UserDao userDao) {
	  this.profileDao = profileDao;
	  this.userDao = userDao;
   }

   //GET Controller to get and return the profile of the user that is logged in
   //Endpoint URL = localhost:8080/profile
   @GetMapping("")
   public Profile getByUserId(Principal principal) {
	  try {
		 //get the user that is logged in
		 String userName = principal.getName();
		 User user = userDao.getByUserName(userName);
		 int userId = user.getId();

		 //Use the profileDao to get the users profile
		 return profileDao.getByUserId(userId);
	  } catch(Exception e) {
		 throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad :(");
	  }
   }

   //PUT Controller to update a users profile
   @PutMapping("")
   public void updateProfile(@RequestBody Profile profile, Principal principal) {
	  try {
		 // get the user that is logged in
		 String userName = principal.getName();
		 User user = userDao.getByUserName(userName);
		 int userId = user.getId();

		 //Put the userId of the user logged as the userId of the profile
		 profile.setUserId(userId);
		 profileDao.updateProfile(profile);
	  } catch(Exception e) {
		 throw new RuntimeException(e);
	  }
   }

//   //POST Controller to create a new profile
//   //Endpoint URL = localhost:8080/profile
//   @PostMapping("")
//   public Profile createProfile(@RequestBody Profile profile) {
//	  try {
//		 return profileDao.create(profile);
//	  } catch(Exception e) {
//		 throw new RuntimeException(e);
//	  }
//   }



}
