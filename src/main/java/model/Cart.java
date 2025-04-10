package model;
import java.io.Serializable;
import java.util.ArrayList;
public class Cart implements Serializable{
    int CartID;
    ArrayList<ArrayList<Integer>> cartProducts = new ArrayList<>();
    int relatedCustomerID;
    int totalPrice;

    public Cart() {
    }
    public int getCardID(){
        return this.CartID;
     }
     public ArrayList<ArrayList<Integer>> getCartProducts() {
         return cartProducts;
     }
     public int getRelatedCustomerID(){
        return this.relatedCustomerID;
     }
     public int getTotalPrice(){
        return this.totalPrice; 
     }
     
}
