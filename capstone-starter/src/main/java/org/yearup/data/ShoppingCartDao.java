package org.yearup.data;

import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import java.util.List;

public interface ShoppingCartDao {
   ShoppingCart getByUserId(int userId);

   List<ShoppingCartItem> getItemsInCart(int userId);

   // add additional method signatures here
   ShoppingCart addToCart(int userId, int productId);

   void updateCart(int userId, int productId, ShoppingCartItem cartItem);

   void clearCart(int userId);
}
