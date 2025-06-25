package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.OrderDao;
import org.yearup.models.Order;
import org.yearup.models.Profile;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlOrdersDao extends MySqlDaoBase implements OrderDao {
//   ShoppingCartDao shoppingCartDao;

   public MySqlOrdersDao(DataSource dataSource) {
	  super(dataSource);
   }

   //Get and return all orders in the database
   @Override
   public List<Order> getAllOrders() {
	  List<Order> ordersList = new ArrayList<>();

	  String query = "SELECT * FROM orders;";

	  try(Connection connection = getConnection()) {
		 PreparedStatement statement = connection.prepareStatement(query);

		 ResultSet results = statement.executeQuery();
		 while(results.next()) {
			Order order = mapRow(results);
			ordersList.add(order);
		 }

	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
	  return ordersList;
   }

   @Override
   public Order getById(int orderId) {
	  String query = "SELECT * FROM orders WHERE order_id = ?;";

	  try(Connection connection = getConnection()) {
		 PreparedStatement statement = connection.prepareStatement(query);
		 statement.setInt(1, orderId);

		 ResultSet results = statement.executeQuery();
		 if(results.next()) {
			return mapRow(results);
		 }

	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
	  return null;
   }

   //Insert a new order into the orders table in the database
   @Override
   public Order addOrder(Order order) {
	  String query = "INSERT INTO orders (user_id, date, address, city, state, zip) " +
							 "VALUES (?, ?, ?, ?, ?, ?);";

	  try(Connection connection = getConnection()) {
		 PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		 statement.setInt(1, order.getUserId());
		 statement.setDate(2, (Date) order.getDate());
		 statement.setString(3, order.getAddress());
		 statement.setString(4, order.getCity());
		 statement.setString(5, order.getState());
		 statement.setString(6, order.getZip());

		 int rows = statement.executeUpdate();

		 if(rows > 0) {
			ResultSet key = statement.getGeneratedKeys();

			if(key.next()) {
			   int orderId = key.getInt(1);
			   return getById(orderId);
			}
		 }
	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
	  return null;
   }

   //Insert a new order line item into the order_line_items in the database
   @Override
   public void insertLineItems(ShoppingCartItem cartItem, int orderId) {
	  String query = "INSERT INTO order_line_items (order_id, product_id, sales_price, quantity, discount) " +
							 "VALUES (?, ?, ?, ?, ?);";

	  try(Connection connection = getConnection()) {
		 PreparedStatement statement = connection.prepareStatement(query);
		 statement.setInt(1, orderId);
		 statement.setInt(2, cartItem.getProductId());
		 statement.setBigDecimal(3, cartItem.getLineTotal());
		 statement.setInt(4, cartItem.getQuantity());
		 statement.setBigDecimal(5, cartItem.getDiscountPercent());

		 statement.executeUpdate();

	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
   }


   private Order mapRow(ResultSet results) throws SQLException {
	  int orderId = results.getInt("order_id");
	  int userId = results.getInt("user_id");
	  Date date = results.getDate("date");
	  String address = results.getString("address");
	  String city = results.getString("city");
	  String state = results.getString("state");
	  String zip = results.getString("zip");
	  BigDecimal shippingAmount = results.getBigDecimal("shipping_amount");

	  return new Order(orderId, userId, date, address, city, state, zip, shippingAmount);
   }


}
