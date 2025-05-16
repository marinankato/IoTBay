package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart implements Serializable {
    private final List<CartItem> items = new ArrayList<>();

    public void addItem(CartItem item) {
        for (CartItem ci : items) {
            if (ci.getDeviceId() == item.getDeviceId()) {
                ci.setQuantity(ci.getQuantity() + item.getQuantity());
                return;
            }
        }
        items.add(item);
    }

    public void updateQuantity(int deviceId, int quantity) {
        for (CartItem ci : items) {
            if (ci.getDeviceId() == deviceId) {
                ci.setQuantity(quantity);
                return;
            }
        }
    }

    public void removeItem(int deviceId) {
        items.removeIf(ci -> ci.getDeviceId() == deviceId);
    }

    public int getQuantity(int deviceId) {
        for (CartItem ci : items) {
            if (ci.getDeviceId() == deviceId) {
                return ci.getQuantity();
            }
        }
        return 0;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotal() {
        return items.stream()
                    .mapToDouble(CartItem::getLineTotal)
                    .sum();
    }
}