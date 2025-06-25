package org.yearup.data;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.yearup.models.Order;
import org.yearup.models.Profile;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import java.util.List;

public interface OrderDao {
   List<Order> getAllOrders();

   void insertLineItems(ShoppingCartItem cartItem, int orderId);

   void addOrder(Order order);

}
