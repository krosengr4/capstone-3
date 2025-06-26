package org.yearup.data.mysql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.yearup.models.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MySqlUserDaoTest extends BaseDaoTestClass{
   private MySqlUserDao userDao;

   @BeforeEach
   public void setup() {
	  userDao = new MySqlUserDao(dataSource);
   }

   @Test
   void getAll_shouldReturn_correctListSize() {
	  //arrange
	  int expected = 2;

	  //act
	  List<User> usersList = userDao.getAll();
	  int actual = usersList.size();

	  //assert
	  assertEquals(expected, actual);
   }

   @Test
   void getUserById_shouldReturn_correctUserName() {
	  //arrange
	  User expected = new User() {{
		 setUsername("user");
	  }};

	  //act
	  int userId = 1;
	  User actual = userDao.getUserById(userId);

	  //assert
	  assertEquals(expected.getUsername(), actual.getUsername());
   }

   @Test
   void getByUserName_shouldReturn_correctUserId() {
	  //arrange
	  User expected = new User() {{
		 setId(1);
		 setUsername("user");
	  }};

	  //act
	  String userName = "user";
	  User actual = userDao.getByUserName(userName);

	  //assert
	  assertEquals(expected.getId(), actual.getId());
   }

   @Test
   void exists_shouldReturn_correctBoolean() {
	  //arrange
	  boolean expected = false;

	  //act
	  boolean actual = userDao.exists("TheGreatBambino");

	  //assert
	  assertEquals(expected, actual);
   }
}