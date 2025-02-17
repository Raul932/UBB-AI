package UI.Scenes;

import Domain.Order;
import Service.BirthdayCakeService;
import Service.OrderService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class OrderController {
    @FXML
    private ListView<Order> listView;
    @FXML
    private TextField orderIdField, cakeIdsField, orderDateField, customerContactField, customerNameField, filterDateField;
    @FXML
    private DatePicker orderDatePicker, filterDatePicker;
    @FXML
    private Button addButton, updateButton, deleteButton, filterButton, showAllButton, backButton;

    private OrderService orderService;

    private BirthdayCakeService cakeService;


    public void setOrderService(OrderService service, BirthdayCakeService service2) {
        System.out.println("setOrderService called");
        System.out.println("OrderService: " + (orderService == null ? "null" : "initialized"));
        System.out.println("CakeService: " + (cakeService == null ? "null" : "initialized"));

        this.orderService = service;
        this.cakeService = service2;
        initializeListView();
    }

    private void initializeListView() {
        if (orderService == null) {
            System.out.println("Error: OrderService is null in initializeListView");
            return;
        }
        System.out.println("OrderService is properly initialized, populating ListView");
        listView.getItems().setAll(orderService.getOrders());
    }

    @FXML
    public void initialize() {
        setupButtons();
        initializeListView();
    }

    private void setupButtons() {
        addButton.setOnAction(e -> addOrder());
        updateButton.setOnAction(e -> updateOrder());
        deleteButton.setOnAction(e -> deleteOrder());
        filterButton.setOnAction(e -> filterOrdersByDate());
        showAllButton.setOnAction(e -> showAllOrders());
    }

    private void addOrder() {
        List<Integer> cakeIds = parseCakeIds(cakeIdsField.getText());
        Order newOrder = new Order(orderIdField.getText(), customerNameField.getText(),
                customerContactField.getText(), orderDateField.getText(), cakeIds);
        orderService.addOrder(newOrder.getCustomerName(), newOrder.getCustomerContact(), newOrder.getOrderDate(), newOrder.getCakeIds());
        refreshOrders();
    }

    private void updateOrder() {
        List<Integer> cakeIds = parseCakeIds(cakeIdsField.getText());
        orderService.updateOrder(Integer.parseInt(orderIdField.getText()), customerNameField.getText(),
                customerContactField.getText(), orderDateField.getText(), cakeIds);
        refreshOrders();
    }

    private void deleteOrder() {
        orderService.removeOrder(Integer.parseInt(orderIdField.getText()));
        refreshOrders();
    }

    private void filterOrdersByDate() {
        String date = filterDateField.getText();
        List<Order> filteredOrders = orderService.filterOrdersByDate(date);
        listView.setItems(FXCollections.observableArrayList(filteredOrders));
    }

    private void showAllOrders() {
        List<Order> allOrders = orderService.getOrders();
        listView.setItems(FXCollections.observableList(allOrders));
    }

    private List<Integer> parseCakeIds(String ids) {
        return List.of(ids.split(",")).stream()
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private void refreshOrders() {
        listView.setItems(FXCollections.observableArrayList(orderService.getOrders()));
    }

    @FXML
    private void switchToCakes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Cake.fxml"));
            Parent root = loader.load();

            // Inject services into CakeController
            CakeController cakeController = loader.getController();
            cakeController.setCakeService(cakeService, orderService);

            // Switch screen
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Cake Shop Management");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
