package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.OrderDao;
import org.yearup.data.ProfileDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("orders")
@CrossOrigin
public class OrdersController {
   private final ShoppingCartDao cartDao;
   private final OrderDao orderDao;
   private final UserDao userDao;
   private final ProfileDao profileDao;

   @Autowired
   public OrdersController(ShoppingCartDao cartDao, OrderDao orderDao, UserDao userDao, ProfileDao profileDao) {
	  this.cartDao = cartDao;
	  this.orderDao = orderDao;
	  this.userDao = userDao;
	  this.profileDao = profileDao;
   }

   //GET controller to get all orders. Must be admin to access
   //GET url = " https://localhost:8080/orders
   @GetMapping("")
   @PreAuthorize("hasRole('ROLE_ADMIN')")
   public List<Order> getAllOrders() {
	  try {
		 return orderDao.getAllOrders();
	  } catch(Exception e) {
		 throw new RuntimeException(e);
	  }
   }

   @PostMapping("")
   public void addOrder(Principal principal) {
	  try {
		 //get the user that is logged in
		 String userName = principal.getName();
		 User user = userDao.getByUserName(userName);
		 int userId = user.getId();
		 //get the logged in users profile
		 Profile profile = profileDao.getByUserId(userId);

		 //Create new order and set fields
		 Order order = new Order(0, userId, Date.valueOf(LocalDate.now()), profile.getAddress(), profile.getCity(),
				 profile.getState(), profile.getZip(), BigDecimal.ZERO); //todo Update shipping price in a future feature

		 //Use orderDao to create a new order
		 Order addedOrder = orderDao.addOrder(order);

		 //Loop through list of items in the cart and use orderDao to insert each item into order_line_items
		 List<ShoppingCartItem> cartItems = cartDao.getItemsInCart(userId);
		 for(ShoppingCartItem item : cartItems) {
			orderDao.insertLineItems(item, addedOrder.getOrderId());
		 }

		 //clear the shopping cart
		 cartDao.clearCart(userId);
	  } catch(Exception e) {
		 throw new RuntimeException(e);
	  }
   }

}
