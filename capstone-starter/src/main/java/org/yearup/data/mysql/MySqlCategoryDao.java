package org.yearup.data.mysql;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao {
   public MySqlCategoryDao(DataSource dataSource) {
	  super(dataSource);
   }

   @Override
   public List<Category> getAllCategories() {
	  List<Category> categoriesList = new ArrayList<>();
	  String query = "SELECT * FROM categories;";

	  try(Connection connection = getConnection()) {
		 PreparedStatement statement = connection.prepareStatement(query);

		 ResultSet results = statement.executeQuery();

		 while(results.next()) {
			int categoryId = results.getInt("category_id");
			String name = results.getString("name");
			String description = results.getString("description");
			Category category = new Category(categoryId, name, description);

			categoriesList.add(category);
		 }
	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }

	  return categoriesList;
   }

   @Override
   public Category getById(int categoryId) {
	  String query = "SELECT * FROM categories WHERE category_id = ?;";

	  try(Connection connection = getConnection()) {
		 PreparedStatement statement = connection.prepareStatement(query);
		 statement.setInt(1, categoryId);

		 ResultSet results = statement.executeQuery();

		 if(results.next()) {
			return mapRow(results);
		 }

	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
	  return null;
   }

   @Override
   public Category create(Category category) {
	  String query = "INSERT INTO categories (name, description) " +
							 "VALUES (?, ?);";

	  try(Connection connection = getConnection()) {
		 PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		 statement.setString(1, category.getName());
		 statement.setString(2, category.getDescription());

		 int rowsAffected = statement.executeUpdate();

		 if(rowsAffected > 0) {
			ResultSet keys = statement.getGeneratedKeys();

			if(keys.next()) {
			   int categoryId = keys.getInt(1);
			   return getById(categoryId);
			}
		 }
	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
	  return null;
   }

   @Override
   public void update(int categoryId, Category category) {
	  String query = "UPDATE categories " +
							 "SET name = ?, " +
							 "description = ? " +
							 "WHERE category_id = ?;";

	  try(Connection connection = getConnection()) {
		 PreparedStatement statement = connection.prepareStatement(query);
		 statement.setString(1, category.getName());
		 statement.setString(2, category.getDescription());
		 statement.setInt(3, categoryId);

		 int rows = statement.executeUpdate();

		 if (rows != 0) {
			System.out.println("Successfully Updated!!!");
		 } else {
			//this would be scary...
			throw new RuntimeException("Something went wrong updating the category info...");
		 }
	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
   }

   @Override
   public void delete(int categoryId) {
	  String query = "DELETE FROM categories " +
							 "WHERE category_id = ?;";

	  try(Connection connection = getConnection()) {
		 PreparedStatement statement = connection.prepareStatement(query);
		 statement.setInt(1, categoryId);

		 int rows = statement.executeUpdate();

		 if (rows != 0) {
			System.out.println("Successfully Deleted!!!");
		 } else {
			//this would be scary...
			throw new RuntimeException("Something went wrong deleting the category...");
		 }
	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
   }

   private Category mapRow(ResultSet row) throws SQLException {
	  int categoryId = row.getInt("category_id");
	  String name = row.getString("name");
	  String description = row.getString("description");

	  return new Category(categoryId, name, description);
   }

}
