package org.yearup.models;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
	//ShoppingCart consists of hashmap
   private Map<Integer, ShoppingCartItem> items = new HashMap<>();

   public Map<Integer, ShoppingCartItem> getItems() {
	  return items;
   }

   public void setItems(Map<Integer, ShoppingCartItem> items) {
	  this.items = items;
   }

   public boolean contains(int productId) {
	  return items.containsKey(productId);
   }

   public void add(ShoppingCartItem item) {
	  items.put(item.getProductId(), item);
   }

   public ShoppingCartItem get(int productId) {
	  return items.get(productId);
   }

   //Get total price of the ShoppingCart
   public BigDecimal getTotal() {
	   //Uses a stream to iterate through each item,
	   // map each item to the items total
	   // reduce the list to the sum of all items total
	  return items.values()
								 .stream()
								 .map(i -> i.getLineTotal())
								 .reduce(BigDecimal.ZERO, (lineTotal, subTotal) -> subTotal.add(lineTotal));
   }

}
