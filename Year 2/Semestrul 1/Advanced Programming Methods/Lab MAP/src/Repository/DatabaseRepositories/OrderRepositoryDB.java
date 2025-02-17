package Repository.DatabaseRepositories;

import Domain.Order;
import Repository.Base.MemoryRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OrderRepositoryDB extends MemoryRepository<String, Order> {
    private final String URL;
    private final String user = "root";
    private final String password = "101104D295prg@";

    public OrderRepositoryDB(String URL) {
        this.URL = URL;
    }

    @Override
    public void add(String id, Order order) {
        try (
                Connection conn = DriverManager.getConnection(URL, user, password);
                PreparedStatement ps = conn.prepareStatement("INSERT INTO orders VALUES (?, ?, ?, ?, ?);")
        ) {
            ps.setInt(1, Integer.parseInt(id));
            ps.setString(2, order.getCustomerName());
            ps.setString(3, order.getCustomerContact());
            ps.setString(4, order.getOrderDate());
            ps.setInt(5, order.getCakeIds().get(0));

            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Order> delete(String id) {
        try (
                Connection conn = DriverManager.getConnection(URL, user, password);
                PreparedStatement ps = conn.prepareStatement("DELETE FROM orders WHERE ID = ?;")
        ) {
            ps.setInt(1, Integer.parseInt(id));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void modify(String id, Order order) {
        try (
                Connection conn = DriverManager.getConnection(URL, user, password);
                PreparedStatement ps = conn.prepareStatement(
                        "UPDATE orders SET " +
                                "customer_name = ?, " +
                                "customer_contact = ?, " +
                                "date = ?, " +
                                "cake_id = ? " +
                                "WHERE ID = ?;"
                )
        ) {
            ps.setString(1, order.getCustomerName());
            ps.setString(2, order.getCustomerContact());
            ps.setString(3, order.getOrderDate());
            ps.setInt(4, order.getCakeIds().get(0));
            ps.setInt(5, Integer.parseInt(id));

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Order> findById(String id) {
        try (
                Connection conn = DriverManager.getConnection(URL, user, password);
                PreparedStatement ps = conn.prepareStatement("SELECT * from orders WHERE id = ?;")
        ) {
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String customerName = rs.getString(2);
                String customerContact = rs.getString(3);
                String date = rs.getString(4);
                int cakeId = rs.getInt(5);

                return Optional.of(new Order(id, customerName, customerContact, date, Collections.singletonList(cakeId)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<Order> getAll() {
        List<Order> result = new ArrayList<>();

        try (
                Connection conn = DriverManager.getConnection(URL, user, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * from orders;")
        ) {
            while (rs.next()) {
                int id = rs.getInt(1);
                String customerName = rs.getString(2);
                String customerContact = rs.getString(3);
                String date = rs.getString(4);
                int cakeId = rs.getInt(5);

                Order order = new Order(String.valueOf(id), customerName, customerContact, date, Collections.singletonList(cakeId));

                result.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public String getNextId() {
        try (
                Connection conn = DriverManager.getConnection(URL, user, password);
                PreparedStatement ps = conn.prepareStatement("SELECT * from orders WHERE ID = ?;")
        ) {
            int candidate = 1;
            ps.setInt(1, candidate);

            while (ps.executeQuery().next()) {
                candidate++;
                ps.setInt(1, candidate);
            }

            return Integer.toString(candidate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
