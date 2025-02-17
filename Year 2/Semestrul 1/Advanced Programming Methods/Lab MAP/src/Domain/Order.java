package Domain;

import java.io.Serializable;
import java.util.List;

public class Order implements Identifiable<String>, Serializable {
    private String orderId;

    private String customerName;
    private String customerContact;
    private String orderDate;

    private List<Integer> cakeIds;

    public Order(String orderId, String customerName, String customerContact, String orderDate, List<Integer> cakes) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.orderDate = orderDate;
        this.cakeIds = cakes;
    }

    @Override
    public String getId() {
        return orderId;
    }

    @Override
    public void setId(String id) { this.orderId = id; }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public List<Integer> getCakeIds() {
        return cakeIds;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setCakeIds(List<Integer> cakeIds) {
        this.cakeIds = cakeIds;
    }

    public void addCake(Integer cakeId) {
        cakeIds.add(cakeId);
    }

    public boolean hasCake(Integer cakeId) {
        return cakeIds.contains(cakeId);
    }

    public String toString() {
        return "Order " + orderId + "\n" +
                "- customerName: " + customerName + "\n" +
                "- customerContact:" + customerContact + "\n" +
                "- orderDate: " + orderDate + "\n" +
                "- cake ID's: " + cakeIds + "\n";
    }
}
