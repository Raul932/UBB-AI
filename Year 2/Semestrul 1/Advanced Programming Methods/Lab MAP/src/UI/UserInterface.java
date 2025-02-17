package UI;

import Domain.BirthdayCake;
import Domain.Order;
import Service.*;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class UserInterface {
    private final BirthdayCakeService cakeService;
    private final OrderService orderService;

    public UserInterface() {
        cakeService = new BirthdayCakeService();
        orderService = new OrderService();
    }

    public UserInterface(BirthdayCakeService cakeService, OrderService orderService) {
        this.cakeService = cakeService;
        this.orderService = orderService;
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;

        while (flag) {
            System.out.println("Welcome to the Cake-Shop!");
            System.out.println("[1] Manage Cake Shop");
            System.out.println("[2] Manage Orders");
            System.out.println("[0] Exit");
            System.out.print("Please enter your choice: ");

            String choice = scanner.next();

            System.out.println();

            switch (choice) {
                case "1": {
                    // Cake service
                    boolean cakeFlag = true;

                    while (cakeFlag) {
                        System.out.println("Cake Shop Management Menu");
                        System.out.println("[1] Get all Cakes");
                        System.out.println("[2] Add a New Cake");
                        System.out.println("[3] Remove a Cake");
                        System.out.println("[4] Get Cake By ID");
                        System.out.println("[5] Update a Cake");
                        System.out.println("[6] Filter Cakes by Flavour");
                        System.out.println("[7] Filter Cakes by Price");
                        System.out.println("[8] Increase Price of Cake");
                        System.out.println("[9] Decrease Price of Cake");
                        System.out.println("[0] Back");
                        System.out.print("Please enter your choice: ");

                        String cakeChoice = scanner.next();

                        System.out.println();

                        switch (cakeChoice) {
                            case "1": {
                                cakeService.getAllBirthdayCakes().forEach(cake -> System.out.println(cake));
                                break;
                            }

                            case "2": {
                                // Add cake
                                try {
                                    System.out.print("Enter the Cake size: ");
                                    int size = scanner.nextInt();
                                    System.out.println();

                                    System.out.print("Enter the Cake flavour: ");
                                    String flavour = scanner.next();
                                    System.out.println();

                                    System.out.print("Enter the number of candles: ");
                                    int candles = scanner.nextInt();
                                    System.out.println();

                                    System.out.print("Enter the price: ");
                                    double price = scanner.nextDouble();
                                    System.out.println();

                                    cakeService.addBirthdayCake(size, flavour, candles, price);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }

                            case "3": {
                                // Remove cake and order containing cake
                                try {
                                    System.out.print("Enter the Cake ID: ");
                                    int id = scanner.nextInt();
                                    System.out.println();

                                    // First, remove the cake from all orders
                                    List<Order> allOrders = orderService.getOrders();

                                    allOrders.forEach(order -> {
                                        List<Integer> cakeIds = new ArrayList<>(order.getCakeIds());
                                        if (cakeIds.contains(id)) {
                                            cakeIds.remove(Integer.valueOf(id));
                                            orderService.updateOrder(Integer.parseInt(order.getId()), order.getCustomerName(),
                                                    order.getCustomerContact(), order.getOrderDate(), cakeIds);
                                        }
                                        cakeService.removeBirthdayCake(id);
                                    });
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }

                            case "4": {
                                // Get cake by id
                                try {
                                    System.out.print("Enter the Cake ID: ");
                                    int id = scanner.nextInt();
                                    System.out.println();

                                    System.out.println(cakeService.getBirthdayCakeById(id));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }

                            case "5": {
                                // Update cake by id
                                try {
                                    System.out.print("Enter the Cake ID: ");
                                    int id = scanner.nextInt();
                                    System.out.println();

                                    System.out.print("Enter the New Cake size: ");
                                    int size = scanner.nextInt();
                                    System.out.println();

                                    System.out.print("Enter the New Cake flavour: ");
                                    String flavour = scanner.next();
                                    System.out.println();

                                    System.out.print("Enter the New number of candles: ");
                                    int candles = scanner.nextInt();
                                    System.out.println();

                                    System.out.print("Enter the New price: ");
                                    double price = scanner.nextDouble();
                                    System.out.println();

                                    cakeService.updateBirthdayCake(id, size, flavour, candles, price);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }

                            case "6": {
                                // Filter cakes by flavour
                                try {
                                    System.out.print("Enter the Flavour: ");
                                    String flavour = scanner.next();
                                    System.out.println();

                                    for (BirthdayCake cake : cakeService.filterBirthdayCakesByFlavour(flavour))
                                        System.out.println(cake);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }

                            case "7": {
                                // Filter cakes by price
                                try {
                                    System.out.print("Enter the Price: ");
                                    double price = scanner.nextDouble();
                                    System.out.println();

                                    for (BirthdayCake cake : cakeService.filterBirthdayCakesByPrice(price))
                                        System.out.println(cake);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }

                            case "8": {
                                // Increase price of cake
                                try {
                                    System.out.print("Enter the Cake ID: ");
                                    int id = scanner.nextInt();
                                    System.out.println();

                                    System.out.println("Enter the percentage by which to increase the price: ");
                                    double percentage = scanner.nextDouble();
                                    System.out.println();

                                    cakeService.increasePriceByPercent(id, percentage);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }

                            case "9": {
                                // Decrease price of cake
                                try {
                                    System.out.print("Enter the Cake ID: ");
                                    int id = scanner.nextInt();
                                    System.out.println();

                                    System.out.println("Enter the percentage by which to decrease the price: ");
                                    double percentage = scanner.nextDouble();
                                    System.out.println();

                                    cakeService.decreasePriceByPercent(id, percentage);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }

                            case "0": {
                                // Exit
                                cakeFlag = false;
                                break;
                            }

                            default:
                                System.out.println("Invalid choice.");
                                break;
                        }
                    }
                    break;
                }

                case "2": {
                    // Order service
                    boolean orderFLag = true;

                    while (orderFLag) {
                        System.out.println("Order Management Menu");
                        System.out.println("[1] Get all Orders");
                        System.out.println("[2] Add a New Order");
                        System.out.println("[3] Remove an Order");
                        System.out.println("[4] Update an Order");
                        System.out.println("[5] Get Order by ID");
                        System.out.println("[6] Filter Orders by Date");
                        System.out.println("[7] Filter Orders by Customer Name");
                        System.out.println("[8] Get Earnings from a Date");
                        System.out.println("[9] Get Orders for a Cake");
                        System.out.println("[10] Get Cakes ordered by a Person");
                        System.out.println("[11] Get Customers who ordered a Cake");
                        System.out.println("[12] Get Total Earnings from a Customer");
                        System.out.println("[0] Back");
                        System.out.print("Please enter your choice: ");

                        String orderChoice = scanner.next();
                        System.out.println();

                        switch (orderChoice) {
                            case "1": {
                                // Get all orders
                                try {
                                    for (Order order : orderService.getOrders())
                                        System.out.println(order);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }

                            case "2": {
                                // Add new order
                                try {
                                    System.out.print("Enter the Customer's Name: ");
                                    String name = scanner.next();
                                    System.out.println();

                                    System.out.print("Enter the Customer Contact Information: ");
                                    String contactInfo = scanner.next();
                                    System.out.println();

                                    System.out.print("Enter the Date of the Order: ");
                                    String dateOfOrder = scanner.next();
                                    System.out.println();

                                    System.out.print("Enter the number of cakes on the order: ");
                                    int cakeCount = scanner.nextInt();
                                    System.out.println();

                                    List<Integer> cakeIdList = new ArrayList<>();

                                    for (int index = 0; index < cakeCount; index++) {
                                        System.out.print("Enter the Cake Id: ");
                                        int cakeId = scanner.nextInt();
                                        System.out.println();

                                        cakeIdList.add(cakeId);
                                    }

                                    orderService.addOrder(name, contactInfo, dateOfOrder, cakeIdList);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }

                            case "3": {
                                // Remove order
                                try {
                                    System.out.print("Enter the Order ID: ");
                                    int orderId = scanner.nextInt();
                                    System.out.println();

                                    orderService.removeOrder(orderId);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }

                            case "4": {
                                // Update order
                                try {
                                    System.out.print("Enter the Order ID: ");
                                    int orderId = scanner.nextInt();
                                    System.out.println();

                                    System.out.print("Enter the New Customer's Name: ");
                                    String name = scanner.next();
                                    System.out.println();

                                    System.out.print("Enter the New Customer Contact Information: ");
                                    String contactInfo = scanner.next();
                                    System.out.println();

                                    System.out.print("Enter the New Date of the Order: ");
                                    String dateOfOrder = scanner.next();
                                    System.out.println();

                                    System.out.print("Enter the New number of cakes on the order: ");
                                    int cakeCount = scanner.nextInt();
                                    System.out.println();

                                    List<Integer> cakeIdList = new ArrayList<>();

                                    for (int index = 0; index < cakeCount; index++) {
                                        System.out.print("Enter the Cake ID: ");
                                        int cakeId = scanner.nextInt();
                                        System.out.println();

                                        cakeIdList.add(cakeId);
                                    }

                                    orderService.updateOrder(orderId, name, contactInfo, dateOfOrder, cakeIdList);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }

                            case "5": {
                                // Get order by id
                                try {
                                    System.out.print("Enter the Order Id: ");
                                    int orderId = scanner.nextInt();
                                    System.out.println();

                                    System.out.println(orderService.getOrderById(orderId));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }

                            case "6": {
                                // Filter orders by date
                                try {
                                    System.out.print("Enter the Date: ");
                                    String date = scanner.next();
                                    System.out.println();

                                    for (Order order : orderService.filterOrdersByDate(date))
                                        System.out.println(order);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }

                            case "8": {
                                // Get earnings from date
                                try {
                                    System.out.print("Enter the Date: ");
                                    String date = scanner.next();
                                    System.out.println();

                                    System.out.println(orderService.getEarningsFromDate(date));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }

                            case "7": {
                                // Filter orders by customer
                                try {
                                    System.out.print("Enter the Name of the Customer: ");
                                    String name = scanner.next();
                                    System.out.println();

                                    for (Order order : orderService.filterOrdersByCustomerName(name))
                                        System.out.println(order);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }

                            case "9": {
                                // Get orders for a specific cake
                                try {
                                    System.out.print("Enter the Cake ID: ");
                                    int cakeId = scanner.nextInt();
                                    System.out.println();

                                    List<Order> ordersForCake = orderService.getOrdersForCake(cakeId);
                                    if (ordersForCake.isEmpty()) {
                                        System.out.println("No orders found for the specified cake.");
                                    } else {
                                        ordersForCake.forEach(order -> System.out.println(order));
                                    }
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }

                            case "10": {
                                // Get cakes ordered by a person
                                try {
                                    System.out.print("Enter the Customer's Name: ");
                                    String customerName = scanner.next();
                                    System.out.println();

                                    List<BirthdayCake> cakesOrdered = orderService.getCakesOrderedByPerson(customerName);
                                    if (cakesOrdered.isEmpty()) {
                                        System.out.println("No cakes found for the specified customer.");
                                    } else {
                                        cakesOrdered.forEach(cake -> System.out.println(cake));
                                    }

                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }

                            case "11": {
                                // Get customers who ordered a specific cake
                                try {
                                    System.out.print("Enter the Cake ID: ");
                                    int cakeId = scanner.nextInt();
                                    System.out.println();

                                    List<String> customers = orderService.getCustomersWhoOrderedCake(cakeId);
                                    if (customers.isEmpty()) {
                                        System.out.println("No customers found for the specified cake.");
                                    } else {
                                        System.out.println("Customers who ordered the cake:");
                                        for (String customer : customers) {
                                            System.out.println(customer);
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }

                            case "12": {
                                // Get total earnings from a customer
                                try {
                                    System.out.print("Enter the Customer's Name: ");
                                    String customerName = scanner.next();
                                    System.out.println();

                                    double totalEarnings = orderService.getTotalEarningsForCustomer(customerName);
                                    System.out.println("Total earnings from " + customerName + ": $" + totalEarnings);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            }


                            case "0": {
                                orderFLag = false;
                                break;
                            }

                            default: {
                                System.out.println("Invalid choice.");
                                break;
                            }
                        }
                    }
                    break;
                }

                case "0": {
                    flag = false;
                    break;
                }

                default: {
                    System.out.println("Invalid choice.");
                    break;
                }
            }
        }
    }
}
