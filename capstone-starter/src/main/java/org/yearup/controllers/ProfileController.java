package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
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

   //Controller to get and return the profile of the user that is logged in
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




}
