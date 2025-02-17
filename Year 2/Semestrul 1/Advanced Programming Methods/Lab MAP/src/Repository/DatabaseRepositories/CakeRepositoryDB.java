package Repository.DatabaseRepositories;

import Domain.BirthdayCake;
import Repository.Base.MemoryRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CakeRepositoryDB extends MemoryRepository<String, BirthdayCake> {
    private final String URL;
    private final String user = "root";
    private final String password = "101104D295prg@";

    public CakeRepositoryDB(String URL) {
        this.URL = URL;
    }

    @Override
    public void add(String id, BirthdayCake cake) {
        try (
                Connection conn = DriverManager.getConnection(URL, user, password);
                PreparedStatement ps = conn.prepareStatement("INSERT INTO birthdaycakes VALUES (?, ?, ?, ?, ?);")
        ) {
            ps.setInt(1, Integer.parseInt(id));
            ps.setInt(2, cake.getSize());
            ps.setString(3, cake.getFlavour());
            ps.setInt(4, cake.getCandles());
            ps.setDouble(5, cake.getPrice());

            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<BirthdayCake> delete(String id) {
        try (
                Connection conn = DriverManager.getConnection(URL, user, password);
                PreparedStatement ps = conn.prepareStatement("DELETE FROM birthdaycakes WHERE ID = ?;")
        ) {
            ps.setInt(1, Integer.parseInt(id));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void modify(String id, BirthdayCake cake) {
        try (
                Connection conn = DriverManager.getConnection(URL, user, password);
                PreparedStatement ps = conn.prepareStatement(
                        "UPDATE birthdaycakes SET " +
                                "size = ?, " +
                                "flavour = ?, " +
                                "candles = ?, " +
                                "price = ? " +
                                "WHERE ID = ?;"
                )
        ) {
            ps.setInt(1, cake.getSize());
            ps.setString(2, cake.getFlavour());
            ps.setInt(3, cake.getCandles());
            ps.setDouble(4, cake.getPrice());
            ps.setInt(5, Integer.parseInt(id));

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<BirthdayCake> findById(String id) {
        try (
                Connection conn = DriverManager.getConnection(URL, user, password);
                PreparedStatement ps = conn.prepareStatement("SELECT * from birthdaycakes WHERE id = ?;")
        ) {
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int size = rs.getInt(2);
                String flavour = rs.getString(3);
                int candles = rs.getInt(4);
                double price = rs.getDouble(5);

                return Optional.of(new BirthdayCake(id, size, flavour, candles, price));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<BirthdayCake> getAll() {
        List<BirthdayCake> result = new ArrayList<>();

        try (
                Connection conn = DriverManager.getConnection(URL, user, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * from birthdaycakes;")
        ) {
            while (rs.next()) {
                int id = rs.getInt(1);
                int size = rs.getInt(2);
                String flavour = rs.getString(3);
                int candles = rs.getInt(4);
                double price = rs.getDouble(5);

                BirthdayCake cake = new BirthdayCake(String.valueOf(id), size, flavour, candles, price);

                result.add(cake);
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
                PreparedStatement ps = conn.prepareStatement("SELECT * from birthdaycakes WHERE ID = ?;")
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
