package org.yearup.data;

import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

public interface ShoppingCartDao {
   ShoppingCart getByUserId(int userId);

   // add additional method signatures here
   void addToCart(int userId, int productId);

   void updateCart(int userId, int productId, ShoppingCartItem cartItem);

   void clearCart(int userId);
}
