package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.OrderDao;
import org.yearup.models.Order;
import org.yearup.models.Profile;
import org.yearup.models.ShoppingCart;

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

	  } catch (SQLException e) {
		 throw new RuntimeException(e);
	  }
	  return ordersList;
   }

   public void addOrder(Order order) {
	  String query = "INSERT INTO orders (user_id, date, address, city, state, zip) " +
							 "VALUES (?, ?, ?, ?, ?, ?);";

	  try (Connection connection = getConnection()) {
		 PreparedStatement statement = connection.prepareStatement(query);
		 statement.setInt(1, order.getUserId());
		 statement.setDate(2, (Date) order.getDate());
		 statement.setString(3, order.getAddress());
		 statement.setString(4, order.getCity());
		 statement.setString(5, order.getState());
		 statement.setString(6, order.getZip());



		 int rows = statement.executeUpdate();
		 if(rows > 0) {
			System.out.println("Order was successfully added!");
		 } else {
			System.err.println("ERROR! Could not add order!!!");
		 }
	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
   }

   private Order mapRow(ResultSet results) throws SQLException {
	  int orderId = results.getInt("order_id");
	  int userId = results.getInt("user_id");
	  Date date = results.getDate("date");
	  String address = results.getString("address");
	  String city  = results.getString("city");
	  String state = results.getString("state");
	  String zip = results.getString("zip");
	  BigDecimal shippingAmount = results.getBigDecimal("shipping_amount");

	  return new Order(orderId, userId, date, address, city, state, zip, shippingAmount);
   }


}
