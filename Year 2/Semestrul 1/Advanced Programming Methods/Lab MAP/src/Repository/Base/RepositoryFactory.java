package Repository.Base;

import Domain.BirthdayCake;
import Domain.Order;
import Repository.DatabaseRepositories.CakeRepositoryDB;
import Repository.DatabaseRepositories.OrderRepositoryDB;
import Repository.EntityRepository.BirthdayCakeRepository;
import Repository.EntityRepository.OrderRepository;
import Repository.FileRepository.CakeRepositoryBinaryFile;
import Repository.FileRepository.CakeRepositoryTextFile;
import Repository.FileRepository.OrderRepositoryBinaryFile;
import Repository.FileRepository.OrderRepositoryTextFile;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepositoryFactory {
    private static final String databaseUrl = "jdbc:mysql://localhost:3306/CakeDatabase";
    private static final String propertiesPath = "src/Config/settings.properties";

    public static class Repositories {
        public MemoryRepository<String, BirthdayCake> cakeRepository;
        public MemoryRepository<String, Order> orderRepository;
    }

    public static Repositories createRepositories() throws IOException {
        Properties props = new Properties();
        props.load(new FileReader(propertiesPath));

        String repositoryType = props.getProperty("Repository");
        String cakesPath = props.getProperty("BirthdayCakes");
        String ordersPath = props.getProperty("Orders");

        if (repositoryType == null) {
            throw new RuntimeException("Repository type not found");
        }

        Repositories repositories = new Repositories();

        switch (repositoryType) {
            case "console": {
                repositories.cakeRepository = new BirthdayCakeRepository();
                repositories.orderRepository = new OrderRepository();

                BirthdayCake cake1 = new BirthdayCake("1", 8, "chocolate", 15, 50.50);
                BirthdayCake cake2 = new BirthdayCake("2", 12, "vanilla", 28, 94.25);
                BirthdayCake cake3 = new BirthdayCake("3", 8, "caramel", 35, 63.99);
                BirthdayCake cake4 = new BirthdayCake("4", 14, "strawberry", 20, 102.99);
                BirthdayCake cake5 = new BirthdayCake("5", 10, "chocolate", 30, 78.50);

                repositories.cakeRepository.add(cake1.getId(), cake1);
                repositories.cakeRepository.add(cake2.getId(), cake2);
                repositories.cakeRepository.add(cake3.getId(), cake3);
                repositories.cakeRepository.add(cake4.getId(), cake4);
                repositories.cakeRepository.add(cake5.getId(), cake5);

                List<Integer> list1 = new ArrayList<>();
                list1.add(1);

                List<Integer> list2 = new ArrayList<>();
                list2.add(2);
                list2.add(3);

                List<Integer> list3 = new ArrayList<>();
                list3.add(4);
                list3.add(1);

                List<Integer> list4 = new ArrayList<>();
                list4.add(5);
                list4.add(2);

                List<Integer> list5 = new ArrayList<>();
                list5.add(3);
                list5.add(4);

                Order order1 = new Order("1", "Jon-Smith", "phone", "23/05/2024", list1);
                Order order2 = new Order("2", "Will-Stones", "email", "05/09/2024", list2);
                Order order3 = new Order("3", "Hannah-Wilkins", "phone", "14/08/2024", list3);
                Order order4 = new Order("4", "Filip-Lesser", "email", "15/10/2024", list4);
                Order order5 = new Order("5", "Ana-White", "email", "12/07/2024", list5);

                repositories.orderRepository.add(order1.getId(), order1);
                repositories.orderRepository.add(order2.getId(), order2);
                repositories.orderRepository.add(order3.getId(), order3);
                repositories.orderRepository.add(order4.getId(), order4);
                repositories.orderRepository.add(order5.getId(), order5);
                break;
            }

            case "binary": {
                repositories.cakeRepository = new CakeRepositoryBinaryFile(cakesPath);
                repositories.orderRepository = new OrderRepositoryBinaryFile(ordersPath);
                break;
            }

            case "text": {
                repositories.cakeRepository = new CakeRepositoryTextFile(cakesPath);
                repositories.orderRepository = new OrderRepositoryTextFile(ordersPath);
                break;
            }

            case "database": {
                repositories.cakeRepository = new CakeRepositoryDB(databaseUrl);
                repositories.orderRepository = new OrderRepositoryDB(databaseUrl);
                break;
            }

            default: {
                throw new RuntimeException("Invalid repository type.\n");
            }
        }

        return repositories;
    }
}
