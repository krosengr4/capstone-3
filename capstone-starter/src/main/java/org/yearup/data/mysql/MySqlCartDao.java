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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MySqlCartDao extends MySqlDaoBase implements ShoppingCartDao {
   ProductDao productDao;

   @Autowired //<--- You may not need this
   public MySqlCartDao(DataSource datasource, ProductDao productDao) {
	  super(datasource);
	  this.productDao = productDao;
   }

   @Override
   public ShoppingCart getByUserId(int userId) {

	  List<ShoppingCartItem> cartItemsList = getItemsInCart(userId);
	  Map<Integer, ShoppingCartItem> cartItemsMap = new HashMap<>();
	  ShoppingCart shoppingCart = new ShoppingCart();

	  for(ShoppingCartItem item : cartItemsList) {
		 cartItemsMap.put(item.getProductId(), item);
	  }

	  shoppingCart.setItems(cartItemsMap);
	  return shoppingCart;

//	  String query = "SELECT * FROM shopping_cart WHERE user_id = ?;";
//
//	  try(Connection connection = getConnection()) {
//		 PreparedStatement statement = connection.prepareStatement(query);
//		 statement.setInt(1, userId);
//
//		 ResultSet results = statement.executeQuery();
//		 while(results.next()) {
//			int productId = results.getInt("product_id");
//			int quantity = results.getInt("quantity");
//
//			Product product = productDao.getById(productId);
//
//			ShoppingCartItem cartItem = new ShoppingCartItem();
//			cartItem.setProduct(product);
//			cartItem.setQuantity(quantity);
//
//			cartItemsMap.put(productId, cartItem);
//		 }
//
//	  } catch (SQLException e) {
//		 throw new RuntimeException(e);
//	  }
   }

   @Override
   public List<ShoppingCartItem> getItemsInCart(int userId) {
	  List<ShoppingCartItem> cartItemsList = new ArrayList<>();
	  String query = "SELECT * FROM shopping_cart WHERE user_id = ?;";

	  try(Connection connection = getConnection()) {
		 PreparedStatement statement = connection.prepareStatement(query);
		 statement.setInt(1, userId);

		 ResultSet results = statement.executeQuery();
		 while(results.next()) {
			int productId = results.getInt("product_id");
			int quantity = results.getInt("quantity");
			Product product = productDao.getById(productId);

			ShoppingCartItem cartItem = new ShoppingCartItem();
			cartItem.setProduct(product);
			cartItem.setQuantity(quantity);

			cartItemsList.add(cartItem);
		 }

	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
	  return cartItemsList;
   }

   @Override
   public ShoppingCart addToCart(int userId, int productId) {
	  String query = "";
	  int quantity = getQuantityInCart(userId, productId);

	  if(quantity > 0) {
		 query = "UPDATE shopping_cart SET quantity = ? " +
						 "WHERE user_id = ? AND product_id = ?;";
	  } else {
		 query = "INSERT INTO shopping_cart (quantity, user_id, product_id) " +
						 "VALUES (?, ?, ?);";
	  }
	  quantity += 1;

	  try(Connection connection = getConnection()) {
		 PreparedStatement statement = connection.prepareStatement(query);
		 statement.setInt(1, quantity);
		 statement.setInt(2, userId);
		 statement.setInt(3, productId);

		 int rows = statement.executeUpdate();
		 if(rows > 0) {
			System.out.println("Added item to cart!");
		 } else {
			System.err.println("ERROR! Could not add item to cart!!!");
		 }

	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
	  return getByUserId(userId);
   }

   private int getQuantityInCart(int userId, int productId) {
	  String query = "SELECT * FROM shopping_cart " +
							 "WHERE user_id = ? " +
							 "AND product_id = ?;";

	  try(Connection connection = getConnection()) {
		 PreparedStatement statement = connection.prepareStatement(query);
		 statement.setInt(1, userId);
		 statement.setInt(2, productId);

		 ResultSet results = statement.executeQuery();
		 if(results.next()) {
			return results.getInt("quantity");
		 } else {
			return 0;
		 }

	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
   }

   @Override
   public void updateCart(int userId, int productId, ShoppingCartItem cartItem) {
	  System.out.println("Attempting to update the cart!");
	  String query = "UPDATE shopping_cart " +
							 "SET quantity = ? " +
							 "WHERE user_id = ? AND product_id = ?;";

	  int quantity = cartItem.getQuantity();
	  try(Connection connection = getConnection()) {
		 PreparedStatement statement = connection.prepareStatement(query);
		 statement.setInt(1, quantity);
		 statement.setInt(2, userId);
		 statement.setInt(3, productId);

		 int rows = statement.executeUpdate();
		 if (rows > 0) {
			System.out.println("Successfully updated cart info!");
		 } else {
			System.out.println("That item is not in your cart!!!");
		 }

	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
   }

   @Override
   public void clearCart(int userId) {
	  String query = "DELETE FROM shopping_cart " +
							 "WHERE user_id = ?;";
//	  ShoppingCart cart = getByUserId(userId);

	  try(Connection connection = getConnection()) {
		 PreparedStatement statement = connection.prepareStatement(query);
		 statement.setInt(1, userId);

		 int rows = statement.executeUpdate();
		 if (rows > 0) {
			System.out.println("\nCart successfully deleted!");
		 } else {
			System.err.println("ERROR! Cart could not be cleared!!!");
		 }

	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
//	  return cart;
   }
}
