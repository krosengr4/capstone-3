package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yearup.data.OrderDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Order;

import java.util.List;

@RestController
@RequestMapping("orders")
@CrossOrigin
public class OrdersController {
   private final ShoppingCartDao cartDao;
   private final OrderDao orderDao;

   @Autowired
   public OrdersController(ShoppingCartDao cartDao, OrderDao orderDao) {
	  this.cartDao = cartDao;
	  this.orderDao = orderDao;
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

}
