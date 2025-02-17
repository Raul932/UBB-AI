package Repository.EntityRepository;

import Domain.BirthdayCake;
import Domain.Order;
import Repository.Base.MemoryRepository;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class OrderRepository extends MemoryRepository<String, Order> {
    public OrderRepository() {
        super();
    }

    public void addOrder(Order order) { add(getNextId(), order); }

    public Optional<Order> removeOrder(int orderId) {
        Order order = super.findById(String.valueOf(orderId)).orElse(null);
        if (order != null) {
            super.delete(String.valueOf(orderId));
            return Optional.of(order);
        }
        return Optional.empty();
    }

    public List<Order> getOrders() {
        return new ArrayList<>((Collection<? extends Order>) getAll());
    }

    public void updateOrder(Order order) {
        modify(order.getId(), order);
    }

    public Optional<Order> getOrderById(String id) {
        return findById(id);
    }
}
