package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.OrderDao;
import org.yearup.data.ProfileDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

	//GET controller to get all items the user that is logged on has ordered
	//GET url = " https://localhost:8080/orders
	@GetMapping("")
	public List<ShoppingCartItem> getItemsOrdered(Principal principal) {
		try {
			//Get the user that is logged in
			String userName = principal.getName();
			User user = userDao.getByUserName(userName);
			int userId = user.getId();

			//Use OrderDao to get list of items the user has ordered
			return orderDao.getItemsOrdered(userId);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	//GET controller add an order to the orders table
	@PostMapping("")
	public void addOrder(Principal principal) {
		try {
			//get the user that is logged in
			String userName = principal.getName();
			User user = userDao.getByUserName(userName);
			int userId = user.getId();
			//get the logged in users profile
			Profile profile = profileDao.getByUserId(userId);

			//Use cartDao to get all the ShoppingCartItems that are in the users cart
			List<ShoppingCartItem> cartItems = cartDao.getItemsInCart(userId);

			if(!cartItems.isEmpty()) {

				//Create new order and set fields
				Order order = new Order(0, userId, LocalDateTime.now(), profile.getAddress(), profile.getCity(),
						profile.getState(), profile.getZip(), BigDecimal.ZERO); //todo Update shipping price in a future feature

				//Use orderDao to insert order into orders table
				Order addedOrder = orderDao.addOrder(order);

				//loop through list of users shopping cart items, add each item to order_line_items table in the db
				for(ShoppingCartItem item : cartItems) {
					orderDao.insertLineItems(item, addedOrder.getOrderId());
				}

				//clear the shopping cart
				cartDao.clearCart(userId);
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
