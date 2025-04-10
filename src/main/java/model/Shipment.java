package model;
import java.util.Date;
import java.io.Serializable;
public class Shipment implements Serializable{
    String shipmentID;
    int houseNO;
    String street;
    String suburb;
    String postcode;
    String state;
    String city;
    String Country;
    String shipmentStatus;
    Date shipmentDate;
    int trackingNO;
    
    public Shipment(){}
    public String getShipmentID(){
        return this.shipmentID;
    }
    public int getHouseNO(){
        return this.houseNO;
    }
    public String getStreet(){
        return this.street;
    }
    public String getSuburb(){
        return this.suburb;
    }
    public String getPostcode(){
        return this.postcode;
    }
    public String getState(){
        return this.state;
    }
    public String getCity(){
        return this.city;
    }
    public String getCountry(){
        return this.Country;
    }
    public String getShipmentStatus(){
        return this.shipmentStatus;
    }
    public Date getShipmentDate(){
        return this.shipmentDate;
    }
    public int getTrackingNO(){
        return this.trackingNO;
    }
    public void setShipmentID(String shipmentID){
        this.shipmentID = shipmentID;
    }
    public void setHouseNO(int houseNO){
        this.houseNO = houseNO;
    }
    public void setStreet(String street){
        this.street = street;
    }
    public void setSuburb(String suburb){
        this.suburb = suburb;
    }
    public void setPostcode(String postcode){
        this.postcode = postcode;
    }
    public void setState(String state){
        this.state = state;
    }
    public void setCity(String city){
        this.city = city;
    }
    public void setCountry(String country){
        this.Country = country;
    }
    public void setShipmentStatus(String shipmentStatus){
        this.shipmentStatus = shipmentStatus;
    }
    public void setShipmentDate(Date shipmentDate){
        this.shipmentDate = shipmentDate;
    }
    public void setTrackingNO(int trackingNO){
        this.trackingNO = trackingNO;
    }
    

}
