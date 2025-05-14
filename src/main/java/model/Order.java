package model;
import java.io.Serializable;
import java.util.*;
public class Order implements Serializable{
      int orderID;
      int userID;
      Date orderDate;
      double totalPrice;
      boolean orderStatus;
 
   public Order(int orderID, int userID, Date orderDate, double totalPrice, boolean orderStatus) {
      this.orderID = orderID;
      this.userID = userID;
      this.orderDate = orderDate;
      this.totalPrice = totalPrice;
      this.orderStatus = orderStatus;
   }

      public int getOrderID(){
         return this.orderID;
      }
      public int getRelatedCustomer(){
         return this.userID;
      }
      public double getTotalPrice(){
         return this.totalPrice; 
      }
      public Date getOrderDate(){
         return this.orderDate;
      }
      public boolean getOrderStatus(){
         return this.orderStatus;
      }
}
