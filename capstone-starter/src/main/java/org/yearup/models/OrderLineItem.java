package org.yearup.models;

import java.math.BigDecimal;

public class OrderLineItem {

   int orderLineItemId;
   int orderId;
   int productId;
   BigDecimal salesPrice;
   int quantity;
   BigDecimal discount;

   public OrderLineItem() {}

   public OrderLineItem(int orderLineItemId, int orderId, int productId, BigDecimal sales_price, int quantity, BigDecimal discount) {
	  this.orderLineItemId = orderLineItemId;
	  this.orderId = orderId;
	  this.productId = productId;
	  this.salesPrice = sales_price;
	  this.quantity = quantity;
	  this.discount = discount;
   }

   //region getters and setters
   public int getOrderLineItemId() {
	  return orderLineItemId;
   }

   public void setOrderLineItemId(int orderLineItemId) {
	  this.orderLineItemId = orderLineItemId;
   }

   public int getOrderId() {
	  return orderId;
   }

   public void setOrderId(int orderId) {
	  this.orderId = orderId;
   }

   public int getProductId() {
	  return productId;
   }

   public void setProductId(int productId) {
	  this.productId = productId;
   }

   public BigDecimal getSalesPrice() {
	  return salesPrice;
   }

   public void setSalesPrice(BigDecimal salesPrice) {
	  this.salesPrice = salesPrice;
   }

   public int getQuantity() {
	  return quantity;
   }

   public void setQuantity(int quantity) {
	  this.quantity = quantity;
   }

   public BigDecimal getDiscount() {
	  return discount;
   }

   public void setDiscount(BigDecimal discount) {
	  this.discount = discount;
   }
   //endregion

   public BigDecimal calculatePrice() {
	  double salesPriceDouble = Double.parseDouble(String.valueOf(this.salesPrice));
	  double discountDouble = Double.parseDouble(String.valueOf(this.discount));

	  double totalNoDiscount = salesPriceDouble * this.quantity;
	  double saleDiscount = discountDouble * totalNoDiscount;
	  double totalWithDiscount = totalNoDiscount - saleDiscount;

	  return BigDecimal.valueOf(totalWithDiscount);
   }

}
