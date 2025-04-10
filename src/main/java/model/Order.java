package model;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
public class Order implements Serializable{
    int orderID;
    int userID;
    int relatedUserID;
    Date orderDate;
    int totalPrice;
    boolean orderStatus;

    public Order(){}

    public int getOrderID(){
        return this.orderID;
     }
     public ArrayList<ArrayList<Integer>> getCartProducts() {
         return getCartProducts();
     }
     public ArrayList<ArrayList<Integer>> getOrderProducts() {
        return null;
    } 
     public int getRelatedCustomerID(){
        return this.relatedUserID;
     }
     public int getTotalPrice(){
        return this.totalPrice; 
     }
     public Date getOrderDate(){
        return this.orderDate;
     }
     public boolean getOrderStatus(){
        return this.orderStatus;
     }
}
