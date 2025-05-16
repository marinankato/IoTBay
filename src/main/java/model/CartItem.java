package model;

import java.io.Serializable;

public class CartItem implements Serializable {
    private final int    deviceId;
    private final String name;
    private final double unitPrice;
    private int          quantity;

    public CartItem(int deviceId, String name, double unitPrice, int quantity) {
        this.deviceId  = deviceId;
        this.name      = name;
        this.unitPrice = unitPrice;
        this.quantity  = quantity;
    }

    public int    getDeviceId()   { return deviceId; }
    public String getName()       { return name; }
    public double getUnitPrice()  { return unitPrice; }
    public int    getQuantity()   { return quantity; }
    public void   setQuantity(int q) { this.quantity = q; }

    /** price × qty */
    public double getLineTotal() {
        return unitPrice * quantity;
    }
}