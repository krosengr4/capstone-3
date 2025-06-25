package org.yearup.data;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.yearup.models.Order;
import org.yearup.models.Profile;
import org.yearup.models.ShoppingCart;

import java.util.List;

public interface OrderDao {
   List<Order> getAllOrders();

   void addOrder(ShoppingCart cart, Profile profile);

}
