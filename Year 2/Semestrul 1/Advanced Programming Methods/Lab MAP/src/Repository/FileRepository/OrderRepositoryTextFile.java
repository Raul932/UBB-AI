package Repository.FileRepository;

import Domain.BirthdayCake;
import Domain.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderRepositoryTextFile extends FileRepository <String, Order> {
    public OrderRepositoryTextFile(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(", ");

                if (tokens.length != 5)
                    throw new IOException("File not valid.\n");

                else {
                    String[] cakeIds = tokens[4].split(",");

                    List<Integer> cakeIdsListInt = new ArrayList<>();

                    for (String cakeId : cakeIds)
                        cakeIdsListInt.add(Integer.parseInt(cakeId));

                    Order order = new Order(
                            tokens[0],
                            tokens[1],
                            tokens[2],
                            tokens[3],
                            cakeIdsListInt
                    );

                    elements.put(order.getId(), order);
                }
            }

        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void writeToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            Iterable<Order> iter = getAll();
            String separator = ", ";

            for (Order order : iter) {
                bw.write(
                        order.getId() + separator +
                                order.getCustomerName() + separator +
                                order.getCustomerContact() + separator +
                                order.getOrderDate() + separator
                );

                int index = 0;
                List<Integer> cakeIds = order.getCakeIds();

                while (index < cakeIds.size() - 1) {
                    bw.write(cakeIds.get(index) + ",");
                    index++;
                }
                bw.write(cakeIds.get(index) + "\n");
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
