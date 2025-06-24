package org.yearup.data.mysql;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.yearup.models.Product;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MySqlProductDaoTest extends BaseDaoTestClass {
   private MySqlProductDao dao;

   @BeforeEach
   public void setup() {
	  dao = new MySqlProductDao(dataSource);
   }

   @Test
   public void getById_shouldReturn_theCorrectProduct() {
	  // arrange
	  int productId = 1;
	  Product expected = new Product() {{
		 setProductId(1);
		 setName("Smartphone");
		 setPrice(new BigDecimal("499.99"));
		 setCategoryId(1);
		 setDescription("A powerful and feature-rich smartphone for all your communication needs.");
		 setColor("Black");
		 setStock(50);
		 setFeatured(false);
		 setImageUrl("smartphone.jpg");
	  }};

	  // act
	  var actual = dao.getById(productId);

	  // assert
	  assertEquals(expected.getPrice(), actual.getPrice(), "Because I tried to get product 1 from the database.");
   }

   @Test
   public void getByPrice_shouldReturn_theCorrectProducts() {
	  //arrange
	  Product expected = new Product() {{
		 setProductId(1);
		 setName("Smartphone");
		 setPrice(new BigDecimal("499.99"));
		 setCategoryId(1);
		 setDescription("A powerful and feature-rich smartphone for all your communication needs.");
		 setColor("Black");
		 setStock(50);
		 setFeatured(false);
		 setImageUrl("smartphone.jpg");
	  }};

	  BigDecimal minPrice = new BigDecimal("499.98");
	  BigDecimal maxPrice = new BigDecimal("500.00");

	  //act
	  List<Product> productList = dao.search(null, minPrice, maxPrice, null);
	  Product actual = productList.get(0);

	  //assert
	  assertEquals(expected.getProductId(), actual.getProductId());

   }

}