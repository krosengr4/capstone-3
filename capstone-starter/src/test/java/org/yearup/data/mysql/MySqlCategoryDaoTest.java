package org.yearup.data.mysql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.yearup.models.Category;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MySqlCategoryDaoTest extends BaseDaoTestClass{
   private MySqlCategoryDao categoryDao;

   @BeforeEach
   public void setup() {
	  categoryDao = new MySqlCategoryDao(dataSource);
   }

   @Test
   void getById_shouldReturn_correctCategory() {

	  // arrange
	  Category expected = new Category() {{
		 setCategoryId(1);
		 setName("Electronics");
		 setDescription("Explore the latest gadgets and electronic devices.");
	  }};

	  int categoryId = 1;
	  //act
	  var actual = categoryDao.getById(1);
	  //assert
	  assertEquals(expected.getName(), actual.getName());
   }
}