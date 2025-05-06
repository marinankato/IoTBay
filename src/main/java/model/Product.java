package model;
import java.io.Serializable;

public class Product implements Serializable{
    int productID;
    String ProductName;
    String description;
    int price;
    int stockOnHand;

    public Product(int productID, String ProductName, String description, int price, int stockOnHand) {
        this.productID = productID;
        this.ProductName = ProductName;
        this.description = description;
        this.price = price;
        this.stockOnHand = stockOnHand;
    }
     public int getProductID(){
        return this.productID;
     }
     public void setProductID(int productID){
        this.productID = productID;
     }
     public String getProductName(){
        return this.ProductName;
     }
     public void setProductName(String ProductName){
        this.ProductName = ProductName;
     }    
     public String getDescription(){
        return this.description;
     }
     public void setDescription(String description){
        this.description = description;
     }  
     public int getPrice(){
        return this.price;
     }
     public void setPrice(int price){
        this.price = price;
     }  
     public int getStockOnHand(){
        return this.stockOnHand;
     }    

}
