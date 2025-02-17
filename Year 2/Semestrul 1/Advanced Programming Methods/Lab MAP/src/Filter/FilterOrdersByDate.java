package Filter;

import Domain.Order;

public class FilterOrdersByDate implements  AbstractFilter<Order> {
    private final String date;

    public FilterOrdersByDate(String date) {
        this.date = date;
    }

    @Override
    public boolean accept(Order order) {
        return date.equals(order.getOrderDate());
    }
}
