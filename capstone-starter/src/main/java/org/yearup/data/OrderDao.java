package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface OrderDao {

   void checkout(ShoppingCart cart);

}
