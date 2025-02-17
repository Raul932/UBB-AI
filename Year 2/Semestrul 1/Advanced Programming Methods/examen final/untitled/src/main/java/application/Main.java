package application;

import controllers.UserController;
import domain.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.UserService;

import java.io.IOException;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        List<User> users = UserService.getAllUsers();
        for (User user : users) {
            openUserWindow(user);
        }
    }

    private void openUserWindow(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/user_window.fxml"));
            Parent root = loader.load();

            UserController controller = loader.getController();
            controller.initialize(user);

            Stage userStage = new Stage();
            userStage.setTitle("Social Network - " + user.getUsername());
            userStage.setScene(new Scene(root, 500, 400));
            userStage.show();
        } catch (IOException e) {
            System.out.println("Could not load fxml file");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
