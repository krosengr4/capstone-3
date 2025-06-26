package org.yearup.data.mysql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.yearup.models.Profile;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MySqlProfileDaoTest extends BaseDaoTestClass{
   private MySqlProfileDao profileDao;

   @BeforeEach
   public void setup() {
	  profileDao = new MySqlProfileDao(dataSource);
   }

   @Test
   void getByUserId_shouldReturn_correctProfile() {
	  //arrange
	  Profile expected = new Profile() {{
		 setUserId(1);
		 setFirstName("Joe");
		 setLastName("Joesephus");
		 setPhone("800-555-1234");
		 setEmail("joejoesephus@email.com");
		 setAddress("789 Oak Avenue");
		 setCity("Dallas");
		 setState("TX");
		 setZip("75051");
	  }};

	  //act
	  int userId = 1;
	  var actual = profileDao.getByUserId(userId);

	  //assert
	  assertEquals(expected.getPhone(), actual.getPhone());
   }
}