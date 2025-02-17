package services;

import domain.Post;
import domain.Topic;
import observers.PostObserver;
import repositories.PostRepository;
import repositories.TopicRepository;
import repositories.UserRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostService {

    private static final List<PostObserver> observers = new ArrayList<>();

    public static List<Post> getUserPosts(int userId) {
        return PostRepository.getUserPosts(userId);
    }

    public static void addPost(int userId, String postText) {
        if (postText.length() < 3){
            return;
        }

        PostRepository.addPost(userId, postText);

        String username = UserRepository.getUsernameById(userId);
        Post newPost = new Post(0, postText, new Timestamp(System.currentTimeMillis()), username);
        notifyObservers(newPost);
    }

    public static List<Post> getUserFeed(int userId, String username) {
        List<Post> allPosts = PostRepository.getAllPosts();
        List<Topic> userTopics = TopicRepository.getUserTopics(userId);

        return allPosts.stream()
                .filter(post -> containsUserTopic(post.getPostText(), userTopics) ||
                        containsMention(post.getPostText(), username))
                .collect(Collectors.toList());
    }

    public static void updatePost(int postId, String newText, String username) {
        if (newText.length() < 3) {
            return;
        }

        PostRepository.updatePost(postId, newText);

        Post updatedPost = new Post(postId, newText, new Timestamp(System.currentTimeMillis()), username);

        notifyObservers(updatedPost);
    }

    private static boolean containsUserTopic(String postText, List<Topic> topics) {
        for (Topic topic : topics) {
            if (postText.contains("#" + topic.getTopicName())) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsMention(String postText, String username) {
        return postText.contains("@" + username);
    }

    public static void registerObserver(PostObserver observer) {
        observers.add(observer);
    }

    private static void notifyObservers(Post newPost) {
        for (PostObserver observer : observers) {
            observer.update(newPost);
        }
    }
}
