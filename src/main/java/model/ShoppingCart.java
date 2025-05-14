package model;
import java.io.Serializable;
import java.util.*;

public class ShoppingCart implements Serializable {
    private final Map<Integer,CartItem> items = new LinkedHashMap<>();

    public void addItem(CartItem it) {
        CartItem existing = items.get(it.getDeviceId());
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + it.getQuantity());
        } else {
            items.put(it.getDeviceId(), it);
        }
    }
    
    public void removeItem(int deviceId) {
        items.remove(deviceId);
    }

    public Collection<CartItem> getItems() {
        return items.values();
    }

    public double getTotal() {
        return items.values()
                    .stream()
                    .mapToDouble(CartItem::getLineTotal)
                    .sum();
    }

    public boolean isEmpty() { 
        return items.isEmpty(); 

    }
    public void clear() { 
        items.clear(); 
    }
}