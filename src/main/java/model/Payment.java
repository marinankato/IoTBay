package model;
import java.io.Serializable;
public class Payment implements Serializable{
    String name;
    String cardNo;
    int cardSecurityCode;

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
    public int getCardSecurityNo(){
        return this.cardSecurityCode;
    }
    public void setCardSecurityNo(int cardSecurityCode){
        this.cardSecurityCode = cardSecurityCode;
    }



}
