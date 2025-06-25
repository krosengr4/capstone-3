package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yearup.data.ProductDao;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;

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




}
