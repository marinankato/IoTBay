package model;
import java.io.Serializable;
import java.util.*;
public class Order implements Serializable{
   public static final int SAVED     = 0;
   public static final int SUBMITTED = 1;
   public static final int CANCELLED = 2;

      int orderID;
      int userID;
      Date orderDate;
      double totalPrice;
      int orderStatus;
 
   public Order(int orderID, int userID, Date orderDate, double totalPrice, int orderStatus) {
      this.orderID = orderID;
      this.userID = userID;
      this.orderDate = orderDate;
      this.totalPrice = totalPrice;
      this.orderStatus = orderStatus;
   }

      public int getOrderID(){
         return this.orderID;
      }
      
      public int getUserID(){
         return this.userID;
      }

      public double getTotalPrice(){
         return this.totalPrice; 
      }

      public Date getOrderDate(){
         return this.orderDate;
      }

      public int getOrderStatus(){
         return this.orderStatus;
      }

      public boolean isSaved()     { 
         return orderStatus == SAVED; 
      }

      public boolean isSubmitted() { 
         return orderStatus == SUBMITTED; 
      }

      public boolean isCancelled() { 
         return orderStatus == CANCELLED; 
      }
}
