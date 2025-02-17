import Service.BirthdayCakeService;
import Service.OrderService;
import UI.Scenes.CakeController;
import UI.Scenes.OrderController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFxStarter extends Application {

    private static BirthdayCakeService cakeService;
    private static OrderService orderService;

    public static void initServices(BirthdayCakeService cakeSvc, OrderService orderSvc) {
        cakeService = cakeSvc;
        orderService = orderSvc;
    }
    private static String[] savedArgs;

    public static void launchApplication(String[] args) {
        savedArgs = args;
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/Scenes/Order.fxml"));
        Parent root = loader.load();

        // Set services manually for testing
        OrderController controller = loader.getController();
        controller.setOrderService(orderService, cakeService);

        primaryStage.setTitle("Order Management");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    public static String[] getSavedArgs() {
        return savedArgs;
    }
}
