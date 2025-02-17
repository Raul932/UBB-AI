package Filter;

import Domain.Order;

public class FilterOrderByCustomerName implements AbstractFilter<Order> {
    private final String customerName;

    public FilterOrderByCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public boolean accept(Order order) {
        return customerName.equals(order.getCustomerName());
    }
}
