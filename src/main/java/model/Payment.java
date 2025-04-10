package model;
import java.io.Serializable;
public class Payment implements Serializable{
    String name;
    String cardNo;
    int cardSecurtiyCode;

    public Payment(){}

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getCardNo(){
        return this.cardNo;
    }
    public void setCardNo(String cardNo){
        this.cardNo = cardNo;
    }
    public int getCardSecurtiyNo(){
        return this.cardSecurtiyCode;
    }
    public void setCardSecurityNo(int cardSecurtiyCode){
        this.cardSecurtiyCode = cardSecurtiyCode;
    }



}
