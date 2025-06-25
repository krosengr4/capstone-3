package org.yearup.models;

import java.math.BigDecimal;
import java.util.Date;

public class Order {

   private int orderId;
   private int userId;
   private Date date;
   private String address;
   private String city;
   private String zip;
   private BigDecimal shoppingAmount;

   public Order() {
   }

   public Order(int orderId, int userId, Date date, String address, String city, String zip, BigDecimal shoppingAmount) {
	  this.orderId = orderId;
	  this.userId = userId;
	  this.date = date;
	  this.address = address;
	  this.city = city;
	  this.zip = zip;
	  this.shoppingAmount = shoppingAmount;
   }

   //region getters and setters
   public int getOrderId() {
	  return orderId;
   }

   public void setOrderId(int orderId) {
	  this.orderId = orderId;
   }

   public int getUserId() {
	  return userId;
   }

   public void setUserId(int userId) {
	  this.userId = userId;
   }

   public Date getDate() {
	  return date;
   }

   public void setDate(Date date) {
	  this.date = date;
   }

   public String getAddress() {
	  return address;
   }

   public void setAddress(String address) {
	  this.address = address;
   }

   public String getCity() {
	  return city;
   }

   public void setCity(String city) {
	  this.city = city;
   }

   public String getZip() {
	  return zip;
   }

   public void setZip(String zip) {
	  this.zip = zip;
   }

   public BigDecimal getShoppingAmount() {
	  return shoppingAmount;
   }

   public void setShoppingAmount(BigDecimal shoppingAmount) {
	  this.shoppingAmount = shoppingAmount;
   }
   //endregion
}
