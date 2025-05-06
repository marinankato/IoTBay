package model;
import java.io.Serializable;
public class Cart implements Serializable{
      int CartID;
      int totalPrice;

      public Cart(int CartID, int totalPrice) {
            this.CartID = CartID;
            this.totalPrice = totalPrice;
      }
      public int getCardID(){
         return this.CartID;
      }
      public void setCardID(int CartID){
         this.CartID = CartID;
      }
      public int getTotalPrice(){
        return this.totalPrice; 
      }
     
}
