package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import java.security.Principal;

// convert this class to a REST controller
// only logged in users should have access to these actions
@RestController
@RequestMapping("cart")
@CrossOrigin
public class ShoppingCartController {
   // a shopping cart requires
   private final ShoppingCartDao shoppingCartDao;
   private final UserDao userDao;
   private final ProductDao productDao;

   @Autowired
   public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao) {
	  this.shoppingCartDao = shoppingCartDao;
	  this.userDao = userDao;
	  this.productDao = productDao;
   }


   // each method in this controller requires a Principal object as a parameter
   @GetMapping("")
   public ShoppingCart getCart(Principal principal) {
	  try {
		 // get the currently logged in username
		 String userName = principal.getName();
		 // find database user by userId
		 User user = userDao.getByUserName(userName);
		 int userId = user.getId();

		 // use the shoppingcartDao to get all items in the cart and return the cart
		 return shoppingCartDao.getByUserId(userId);
	  } catch(Exception e) {
		 throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
	  }
   }

   // add a POST method to add a product to the cart - the url should be
   // https://localhost:8080/cart/products/15 (15 is the productId to be added
   @PostMapping("products/{productId}")
   @ResponseStatus(value = HttpStatus.OK)
   public ShoppingCart addItemToCart(@PathVariable int productId, Principal principal) {
	  try {
		 //get the user that is logged in
		 String userName = principal.getName();
		 User user = userDao.getByUserName(userName);
		 int userId = user.getId();

		 return shoppingCartDao.addToCart(userId, productId);
	  } catch(Exception e) {
		 throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
	  }
   }


   // add a PUT method to update an existing product in the cart - the url should be
   // https://localhost:8080/cart/products/15 (15 is the productId to be updated)
   // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated
   @PutMapping("products/{productId}")
   public void updateItemInCart(@PathVariable int productId, @RequestBody ShoppingCartItem cartItem, Principal principal) {
	  System.out.println("You clicked update cart!");
	  try {
		 //get the user that is logged in
		 String userName = principal.getName();
		 User user = userDao.getByUserName(userName);
		 int userId = user.getId();

		 shoppingCartDao.updateCart(userId, productId, cartItem);
	  } catch(Exception e) {
		 throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "oops... our bad.");
	  }
   }


   // add a DELETE method to clear all products from the current users cart
   // https://localhost:8080/cart
   @DeleteMapping("")
//   @ResponseStatus(value = HttpStatus.OK)
   public ShoppingCart clearCart(Principal principal) {
	  try {
		 //get the user that is logged in
		 String userName = principal.getName();
		 User user = userDao.getByUserName(userName);
		 int userId = user.getId();

		 shoppingCartDao.clearCart(userId);

		 return shoppingCartDao.getByUserId(userId);

	  } catch(Exception e) {
		 throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
	  }

   }

}
