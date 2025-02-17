import Domain.BirthdayCake;
import Domain.Order;
import Service.BirthdayCakeService;
import Service.OrderService;
import Repository.Base.MemoryRepository;
import Repository.Base.RepositoryFactory;
import UI.UserInterface;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try {
            RepositoryFactory.Repositories repositories = RepositoryFactory.createRepositories();
            MemoryRepository<String, BirthdayCake> cakeRepository = repositories.cakeRepository;
            MemoryRepository<String, Order> orderRepository = repositories.orderRepository;

            BirthdayCakeService cakeService = new BirthdayCakeService(cakeRepository);
            OrderService orderService = new OrderService(orderRepository, cakeService);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Choose your interface:");
            System.out.println("1: GUI");
            System.out.println("2: Console UI");
            System.out.print("Enter choice (1 or 2): ");
            String choice = scanner.nextLine();

            if ("1".equals(choice)) {
                JavaFxStarter.initServices(cakeService, orderService);
                JavaFxStarter.launchApplication(args);
            } else if ("2".equals(choice)) {
                UserInterface userInterface = new UserInterface(cakeService, orderService);
                userInterface.show();
            } else {
                System.out.println("Invalid choice. Exiting...");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
