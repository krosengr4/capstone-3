package org.yearup.data;

import org.yearup.models.Order;
import org.yearup.models.ShoppingCartItem;

import java.util.List;

public interface OrderDao {
   Order getById(int orderId);

   List<ShoppingCartItem> getItemsOrdered(int userId);

   void insertLineItems(ShoppingCartItem cartItem, int orderId);

   Order addOrder(Order order);

}
