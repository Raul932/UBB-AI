package Service;

import Domain.BirthdayCake;
import Domain.Order;
import Filter.AbstractFilter;
import Filter.FilterOrderByCustomerName;
import Filter.FilterOrdersByDate;
import Repository.Base.FilteredRepository;
import Repository.Base.MemoryRepository;
import Validation.OrderValidator;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderService {
    MemoryRepository<String, Order> orderRepository;
    BirthdayCakeService cakeService;
    private final OrderValidator validator;

    public OrderService() {
        orderRepository = new MemoryRepository<>();
        cakeService = new BirthdayCakeService();
        validator = new OrderValidator(cakeService);
    }

    public OrderService(MemoryRepository<String, Order> orderRepository, BirthdayCakeService cakeService) {
        this.orderRepository = orderRepository;
        this.cakeService = cakeService;
        validator = new OrderValidator(cakeService);
    }

    public void addOrder(String customerName, String customerContact, String orderDate, List<Integer> cakeIds) {
        Order order = new Order(orderRepository.getNextId(), customerName, customerContact, orderDate, cakeIds);
        validator.validate(order);
        orderRepository.add(order.getId(), order);
    }

    public void removeOrder(int orderId) {
        orderRepository.delete(Integer.toString(orderId));
    }

    public void updateOrder(int orderId, String newCustomerName, String newCustomerContact, String newDate, List<Integer> newCakeIds) {
        Order order = new Order(Integer.toString(orderId), newCustomerName, newCustomerContact, newDate, newCakeIds);
        validator.validate(order);
        orderRepository.modify(order.getId(), order);
    }

    public Optional<Order> getOrderById(int orderId) {
        return orderRepository.findById(Integer.toString(orderId));
    }

    public List<Order> getOrders() {
        return orderRepository.getAll();
    }

    public List<Order> filterOrdersByDate(String date) {
        return getOrders().stream()
                .filter(order -> order.getOrderDate().equals(date))
                .collect(Collectors.toList());
    }

    public List<Order> filterOrdersByCustomerName(String customerName) {
        return getOrders().stream()
                .filter(order -> order.getCustomerName().equals(customerName))
                .collect(Collectors.toList());
    }

    public double getEarningsFromDate(String date) {
        double totalEarnings = filterOrdersByDate(date).stream()
                .flatMapToDouble(order -> order.getCakeIds().stream()
                        .mapToDouble(id -> {
                            Optional<BirthdayCake> cakeOpt = cakeService.getBirthdayCakeById(id);
                            if (!cakeOpt.isPresent()) {
                                return 0.0;
                            }
                            return cakeOpt.map(BirthdayCake::getPrice).orElse(0.0);
                        })
                ).sum();
        return totalEarnings;
    }
    public List<Order> getOrdersForCake(int cakeId) {
        return getOrders().stream()
                .filter(order -> order.getCakeIds().contains(cakeId))
                .collect(Collectors.toList());
    }
    public List<BirthdayCake> getCakesOrderedByPerson(String customerName) {
        return filterOrdersByCustomerName(customerName).stream()
                .flatMap(order -> order.getCakeIds().stream())
                .distinct()
                .map(cakeId -> cakeService.getBirthdayCakeById(cakeId).orElse(null)) // Access the correct method from CakeService
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    public List<String> getCustomersWhoOrderedCake(int cakeId) {
        return getOrders().stream()
                .filter(order -> order.getCakeIds().contains(cakeId)) // Orders with the specified cake ID
                .map(Order::getCustomerName) // Extract customer names
                .distinct() // Ensure unique customer names
                .collect(Collectors.toList());
    }
    public double getTotalEarningsForCustomer(String customerName) {
        return filterOrdersByCustomerName(customerName).stream()
                .flatMapToDouble(order -> order.getCakeIds().stream()
                        .mapToDouble(cakeId -> cakeService.getBirthdayCakeById(cakeId)
                                .map(BirthdayCake::getPrice)
                                .orElse(0.0)
                        )
                )
                .sum();
    }
    public void removeInvalidOrders(int cakeId) {
        // Removes the orders that have an invalid cake ID.
        for (Order order : orderRepository.getAll()) {
            if (order.hasCake(cakeId))
                orderRepository.delete(order.getId());
        }
    }

    public double getAverageOrderPrice() {
        // Returns the Average Order Price.
        return orderRepository.getAll().stream()
                .mapToDouble(order -> order.getCakeIds().stream()
                        .map(cakeService::getBirthdayCakeById)
                        .map(Optional::orElseThrow)
                        .mapToDouble(BirthdayCake::getPrice)
                        .sum())
                .average()
                .orElse(0.0);
    }

    public double getTotalEarnings() {
        // Returns the total earnings.
        return orderRepository.getAll().stream()
                .flatMap(order -> order.getCakeIds().stream())
                .map(cakeService::getBirthdayCakeById)
                .map(Optional::orElseThrow)
                .mapToDouble(BirthdayCake::getPrice)
                .sum();
    }
}
