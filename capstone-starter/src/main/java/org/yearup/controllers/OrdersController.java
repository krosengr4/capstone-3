package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yearup.data.OrderDao;
import org.yearup.data.ShoppingCartDao;

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

}
