package services;

import domain.Topic;
import repositories.TopicRepository;

import java.util.List;

public class TopicService {

    public static List<Topic> getUserTopics(int userId) {
        return TopicRepository.getUserTopics(userId);
    }

    public static List<Topic> searchTopics(String searchText) {
        return TopicRepository.searchTopics(searchText);
    }

    public static void subscribeUserToTopic(int userId, int topicId) {
        TopicRepository.subscribeUserToTopic(userId, topicId);
    }

    public static int getTopicIdByName(String topicName) {
        return TopicRepository.getTopicIdByName(topicName);
    }
}
