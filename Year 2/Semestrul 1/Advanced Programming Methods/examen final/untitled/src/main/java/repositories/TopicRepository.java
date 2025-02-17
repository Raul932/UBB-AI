package repositories;

import database.DatabaseConnection;
import domain.Topic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopicRepository {

    public static List<Topic> getUserTopics(int userId) {
        List<Topic> topics = new ArrayList<>();
        String query = "SELECT t.id, t.topic_name FROM topics t " +
                "JOIN user_topics ut ON t.id = ut.topic_id " +
                "WHERE ut.user_id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                topics.add(new Topic(rs.getInt("id"), rs.getString("topic_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topics;
    }

    public static List<Topic> searchTopics(String searchText) {
        List<Topic> topics = new ArrayList<>();
        String query = "SELECT id, topic_name FROM topics WHERE topic_name LIKE ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + searchText + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                topics.add(new Topic(rs.getInt("id"), rs.getString("topic_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topics;
    }

    public static void subscribeUserToTopic(int userId, int topicId) {
        String query = "INSERT INTO user_topics (user_id, topic_id) VALUES (?, ?) ON DUPLICATE KEY UPDATE user_id=user_id";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, topicId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getTopicIdByName(String topicName) {
        String query = "SELECT id FROM topics WHERE topic_name = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, topicName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id"); // Return topic ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // If topic not found
    }
}
