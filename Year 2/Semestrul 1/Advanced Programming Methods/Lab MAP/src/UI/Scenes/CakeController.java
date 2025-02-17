package UI.Scenes;

import Domain.BirthdayCake;
import Domain.Order;
import Service.BirthdayCakeService;
import Service.OrderService;
import Service.ThreadsOps;
import Threads.ParallelMergeSort;
import Validation.ValidatorException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class CakeController {
    @FXML
    private ListView<BirthdayCake> cakeListView;
    @FXML
    private TextField sizeField, flavourField, priceField, candlesField;
    @FXML
    private TextField filterFlavourField, filterSizeField;
    @FXML
    private Button createButton, updateButton, deleteButton, showAllButton, sortCakes;
    @FXML
    private Button  priceReportButton, filterBySizeButton, filterByFlavourButton;
    @FXML
    private Button add100kCakesButton, updatePricesButton, updatePricesButton2;
    @FXML
    private Button goToOrdersButton;

    private static final int NUM_THREADS = 8;

    private BirthdayCakeService cakeService;
    private OrderService orderService;

    public void setCakeService(BirthdayCakeService service, OrderService service2) {
        this.cakeService = service;
        this.orderService = service2;
        initializeListView();
    }

    private void initializeListView() {
        cakeListView.getItems().setAll(cakeService.getAllBirthdayCakes());
    }

    @FXML
    public void initialize() {
        // Initialize button handlers
        createButton.setOnAction(e -> createCake());
        updateButton.setOnAction(e -> updateCake());
        deleteButton.setOnAction(e -> deleteCake());
        showAllButton.setOnAction(e -> loadAllCakes());

        priceReportButton.setOnAction(e -> filterCakesByPrice());

        sortCakes.setOnAction(e -> sortCakes());

        add100kCakesButton.setOnAction(e -> add100kCakesUsingThreads());
        updatePricesButton.setOnAction(e -> {
            try {
                updateCakePricesUsingExecutorService();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        updatePricesButton2.setOnAction(e -> {
            try {
                updateCakePricesUsingThreads();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        filterByFlavourButton.setOnAction(e -> filterByFlavour());

        filterBySizeButton.setOnAction(e -> filterBySize());

    }

    private void createCake() {
        try {
            int size = Integer.parseInt(sizeField.getText());
            double price = Double.parseDouble(priceField.getText());
            int candles = Integer.parseInt(candlesField.getText());
            String flavour = flavourField.getText();
            cakeService.addBirthdayCake(size, flavour, candles, price);
            loadAllCakes();
        } catch (NumberFormatException ex) {
            showAlert("Error", "Please check your number inputs.");
        }
    }

    private void updateCake() {
        BirthdayCake selectedCake = cakeListView.getSelectionModel().getSelectedItem();
        if (selectedCake != null) {
            try {
                selectedCake.setSize(Integer.parseInt(sizeField.getText()));
                selectedCake.setFlavour(flavourField.getText());
                selectedCake.setPrice(Double.parseDouble(priceField.getText()));
                selectedCake.setCandles(Integer.parseInt(candlesField.getText()));
                cakeService.updateBirthdayCake(Integer.parseInt(selectedCake.getId()), selectedCake.getSize(), selectedCake.getFlavour(), selectedCake.getCandles(), selectedCake.getPrice());
                loadAllCakes();
            } catch (NumberFormatException ex) {
                showAlert("Error", "Please check your number inputs.");
            }
        } else {
            showAlert("Error", "No cake selected.");
        }
    }

    private void deleteCake() {
        BirthdayCake selectedCake = cakeListView.getSelectionModel().getSelectedItem();
        if (selectedCake != null) {
            List<Order> allOrders = orderService.getOrders();

            allOrders.forEach(order -> {
                        List<Integer> cakeIds = new ArrayList<>(order.getCakeIds());
                        if (cakeIds.contains(selectedCake.getId())) {
                            cakeIds.remove(Integer.valueOf(selectedCake.getId()));
                            orderService.updateOrder(Integer.parseInt(order.getId()), order.getCustomerName(),
                                    order.getCustomerContact(), order.getOrderDate(), cakeIds);
                        }
                        cakeService.removeBirthdayCake(Integer.valueOf(selectedCake.getId()));
                    });
            loadAllCakes();
        } else {
            showAlert("Error", "No cake selected.");
        }
    }

    @FXML
    private void filterByFlavour() {
        String flavour = filterFlavourField.getText().trim();
        if (!flavour.isEmpty()) {
            var filteredCakes = cakeService.getAllBirthdayCakes().stream()
                    .filter(cake -> cake.getFlavour().equalsIgnoreCase(flavour))
                    .collect(Collectors.toList());
            cakeListView.getItems().setAll(filteredCakes);
        }
    }

    @FXML
    private void filterBySize() {
        try {
            int size = Integer.parseInt(filterSizeField.getText().trim());
            var filteredCakes = cakeService.getAllBirthdayCakes().stream()
                    .filter(cake -> cake.getSize() == size)
                    .collect(Collectors.toList());
            cakeListView.getItems().setAll(filteredCakes);
        } catch (NumberFormatException e) {
            System.out.println("Invalid size input: " + e.getMessage());
            // Optionally alert the user to incorrect input
        }
    }

    private void filterCakesByPrice() {
        try {
            double price = Double.parseDouble(priceField.getText());
            cakeListView.getItems().setAll(cakeService.filterBirthdayCakesByPrice(price));
        } catch (NumberFormatException ex) {
            showAlert("Error", "Please enter a valid price.");
        }
    }

    private void loadAllCakes() {
        List<BirthdayCake> sortedCakes = cakeService.getSortedCakes();
        cakeListView.setItems(FXCollections.observableArrayList(sortedCakes));
    }

    @FXML
    private void sortCakes() {
        // Convert List to an array
        BirthdayCake[] cakesArray = cakeListView.getItems().toArray(new BirthdayCake[0]);

        // Create a ForkJoinPool and execute the ParallelMergeSort task
        ForkJoinPool pool = new ForkJoinPool();
        ParallelMergeSort task = new ParallelMergeSort(cakesArray, 0, cakesArray.length);
        pool.invoke(task);
        pool.shutdown();

        // Update ListView on the JavaFX thread
        Platform.runLater(() -> {
            cakeListView.getItems().setAll(cakesArray);
        });
    }

    private void add100kCakesUsingThreads() {
        final int totalCakes = 100000;
        int threadsCount = 10; // Number of threads
        int cakesPerThread = totalCakes / threadsCount;

        Thread[] threads = new Thread[threadsCount];
        for (int i = 0; i < threadsCount; i++) {
            final int threadIndex = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < cakesPerThread; j++) {
                    int index = threadIndex * cakesPerThread + j;
                    int id = index + 1; // ID starts from 1
                    int size = (index % 100) + 1; // Sizes between 1 and 100
                    String flavour = getValidFlavour(index % 5);
                    int candles = (index % 100) + 1; // Candles between 1 and 100
                    double price = (index % 100) + 1.0; // Prices between 1.0 and 100.0

                    try {
                        cakeService.addBirthdayCake(size, flavour, candles, price);
                    } catch (Exception ex) {
                        System.out.println("Failed to add cake: " + ex.getMessage());
                    }
                }
            });
            threads[i].start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                if (thread != null) thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
            }
        }
        loadAllCakes(); // Refresh the list of cakes
    }

    private String getValidFlavour(int index) {
        switch (index) {
            case 0: return "chocolate";
            case 1: return "vanilla";
            case 2: return "caramel";
            case 3: return "strawberry";
            case 4: return "coconut";
            default: return "vanilla"; // Default case to handle unexpected index
        }
    }

    private void updateCakePricesUsingExecutorService() throws SQLException, IOException {
        // Implementation to update prices using ExecutorService
        ThreadsOps.updateCakePricesUsingExecutorService(cakeService, NUM_THREADS); // Assuming such a method exists
        loadAllCakes();
    }

    private void updateCakePricesUsingThreads() throws SQLException, IOException {
        // Implementation to update prices using traditional threads
        ThreadsOps.updateCakePricesThreads(cakeService, NUM_THREADS); // Assuming such a method exists
        loadAllCakes();
    }

    @FXML
    public void goToOrdersScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Order.fxml"));
            Parent root = loader.load();

            // Inject services into OrderController
            OrderController orderController = loader.getController();
            orderController.setOrderService(orderService, cakeService);

            // Switch screen
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Order Management");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
