package model;
import java.io.Serializable;
import java.util.Date;
public class Payment implements Serializable{
    int paymentID;
    String name;
    String cardNo;
    int cardSecurityCode;
    Date paymentDate;

    public Payment(int paymentID, String name, String cardNo, int cardSecurityCode, Date paymentDate){
        this.paymentID = paymentID;
        this.name = name;
        this.cardNo = cardNo;
        this.cardSecurityCode = cardSecurityCode;
        this.paymentDate = paymentDate;
    }
    public int getPaymentID(){
        return this.paymentID;
    }
    public void setPaymentID(int paymentID){
        this.paymentID = paymentID;
    }
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
    public Date getPaymentDate(){
        return this.paymentDate;
    }
    public void setPaymentDate(Date paymentDate){
        this.paymentDate = paymentDate;
    }



}
