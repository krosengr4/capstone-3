package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.models.Profile;
import org.yearup.data.ProfileDao;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class MySqlProfileDao extends MySqlDaoBase implements ProfileDao {

   public MySqlProfileDao(DataSource dataSource) {
	  super(dataSource);
   }

   //Get a profile by userID from the database and return it
   @Override
   public Profile getByUserId(int userId) {
	  String query = "SELECT * FROM profiles " +
							 "WHERE user_id = ?;";

	  try(Connection connection = getConnection()) {
		 PreparedStatement statement = connection.prepareStatement(query);
		 statement.setInt(1, userId);

		 ResultSet results = statement.executeQuery();
		 if(results.next()) {
			return mapRow(results);
		 } else {
			System.err.println("ERROR! Could not find a profile with that ID!");
		 }

	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
	  return null;
   }

   @Override
   public Profile create(Profile profile) {
	  String sql = "INSERT INTO profiles (user_id, first_name, last_name, phone, email, address, city, state, zip) " +
						   " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	  try(Connection connection = getConnection()) {
		 PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		 ps.setInt(1, profile.getUserId());
		 ps.setString(2, profile.getFirstName());
		 ps.setString(3, profile.getLastName());
		 ps.setString(4, profile.getPhone());
		 ps.setString(5, profile.getEmail());
		 ps.setString(6, profile.getAddress());
		 ps.setString(7, profile.getCity());
		 ps.setString(8, profile.getState());
		 ps.setString(9, profile.getZip());

		 ps.executeUpdate();

		 return profile;
	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
   }

   //Method to map a row of results as a Profile and return it
   private Profile mapRow(ResultSet results) throws SQLException {
	  int userId = results.getInt("user_id");
	  String firstName = results.getString("first_name");
	  String lastName = results.getString("last_name");
	  String phone = results.getString("phone");
	  String email = results.getString("email");
	  String address = results.getString("address");
	  String city = results.getString("city");
	  String state = results.getString("state");
	  String zip = results.getString("zip");

	  return new Profile(userId, firstName, lastName, phone, email, address, city, state, zip);
   }

}
