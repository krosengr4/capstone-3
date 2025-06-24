package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MySqlCartDao extends MySqlDaoBase implements ShoppingCartDao {
   ProductDao productDao;

   @Autowired
   public MySqlCartDao(DataSource datasource, ProductDao productDao) {
	  super(datasource);
	  this.productDao = productDao;
   }

   @Override
   public ShoppingCart getByUserId(int userId) {

	  Map<Integer, ShoppingCartItem> cart = new HashMap<>();

	  String query = "SELECT * FROM shopping_cart WHERE user_id = ?;";

	  try(Connection connection = getConnection()) {
		 PreparedStatement statement = connection.prepareStatement(query);
		 statement.setInt(1, userId);

		 int i = 1;

		 ResultSet results = statement.executeQuery();
		 while(results.next()) {
			int productId = results.getInt("product_id");
			Product product = productDao.getById(productId);

			ShoppingCartItem cartItem = new ShoppingCartItem();
			cartItem.setProduct(product);
			cart.put(i, cartItem);
			i++;
		 }

	  } catch (SQLException e) {
		 throw new RuntimeException(e);
	  }
	  ShoppingCart shoppingCart = new ShoppingCart();
	  shoppingCart.setItems(cart);
	  return shoppingCart;
   }

   private int checkIfItemInCart(int userId, int productId) {
	  String query = "SELECT * FROM shopping_cart " +
							 "WHERE user_id = ? " +
							 "AND product_id = ?;";
	  int numberOfProduct = 0;

	  try(Connection connection = getConnection()) {
		 PreparedStatement statement = connection.prepareStatement(query);
		 statement.setInt(1, userId);
		 statement.setInt(2, productId);

		 ResultSet results = statement.executeQuery();


		 while(results.next()) {
			numberOfProduct++;
		 }
	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
	  return numberOfProduct;
   }

   public ShoppingCartItem addToCart(int userId, int productId){
	  String query = "INSERT INTO shopping_cart (user_id, product_id, quantity) " +
							 "VALUES (?, ?, 1);";

	  try(Connection connection = getConnection()) {
		 PreparedStatement statement = connection.prepareStatement(query);
		 statement.setInt(1, userId);
		 statement.setInt(2, productId);

		 int rows = statement.executeUpdate();

		 if (rows > 0)
			System.out.println("Item successfully added to cart!");
		 else
			System.err.println("ERROR! Could not add item to the cart...");

	  } catch (SQLException e) {
		 throw new RuntimeException(e);
	  }
	  Product product = productDao.getById(productId);
	  ShoppingCartItem cartItem = new ShoppingCartItem();
	  cartItem.setProduct(product);

	  return cartItem;
   }




}
