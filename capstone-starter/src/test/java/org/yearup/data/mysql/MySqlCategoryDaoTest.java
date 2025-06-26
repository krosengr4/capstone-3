package org.yearup.data.mysql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.yearup.models.Category;

import java.util.List;

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

	  //act
	  int categoryId = 1;
	  var actual = categoryDao.getById(categoryId);
	  //assert
	  assertEquals(expected.getName(), actual.getName());
   }

   @Test
   void getAllCategories_shouldReturn_correctListSize() {
	  //arrange
	  int expected = 3;

	  //act
	  List<Category> categoryList = categoryDao.getAllCategories();
	  int actual = categoryList.size();

	  //assert
	  assertEquals(expected, actual);
   }
}