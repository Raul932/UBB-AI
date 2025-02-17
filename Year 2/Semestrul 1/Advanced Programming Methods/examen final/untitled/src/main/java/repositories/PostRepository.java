package repositories;

import database.DatabaseConnection;
import domain.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostRepository {

    public static List<Post> getUserPosts(int userId) {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT p.id, p.post_text, p.timestamp, u.username FROM posts p " +
                "JOIN users u ON p.user_id = u.id " +
                "WHERE p.user_id = ? ORDER BY p.timestamp DESC";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                posts.add(new Post(rs.getInt("id"), rs.getString("post_text"), rs.getTimestamp("timestamp"), rs.getString("username")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public static void addPost(int userId, String postText) {
        String query = "INSERT INTO posts (user_id, post_text, timestamp) VALUES (?, ?, NOW())";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, postText);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT p.id, p.post_text, p.timestamp, u.username FROM posts p " +
                "JOIN users u ON p.user_id = u.id ORDER BY p.timestamp DESC";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                posts.add(new Post(rs.getInt("id"), rs.getString("post_text"), rs.getTimestamp("timestamp"), rs.getString("username")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public static void updatePost(int postId, String newText){
        String query = "UPDATE posts SET post_text = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newText);
            stmt.setInt(2, postId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

