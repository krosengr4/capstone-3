package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;

import javax.sql.DataSource;

@Component
public class MySqlOrdersDao extends MySqlDaoBase implements ShoppingCartDao {
//   ShoppingCartDao shoppingCartDao;

   public MySqlOrdersDao(DataSource dataSource) {
	  super(dataSource);
   }


}
