package controllers;

import domain.Post;
import domain.Topic;
import domain.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import observers.PostObserver;
import services.PostService;
import services.TopicService;

import java.util.List;

public class UserController implements PostObserver {

    private User currentUser;

    @FXML private ListView<String> feedListView;
    @FXML private ListView<String> postListView;
    @FXML private TextField postTextField;
    @FXML private ListView<String> subscriptionListView;
    @FXML private TextField topicSearchField;
    @FXML private ListView<String> topicSearchResults;
    @FXML private Button subscribeButton;
    @FXML private Button editPostButton;

    public void initialize(User user) {
        this.currentUser = user;
        PostService.registerObserver(this);
        loadUserFeed();
        loadUserPosts();
        loadUserSubscriptions();
        setupTopicSearch();
    }

    private void loadUserFeed() {
        List<Post> feedPosts = PostService.getUserFeed(currentUser.getId(), currentUser.getUsername());
        feedListView.setItems(FXCollections.observableArrayList(feedPosts.stream().map(Post::getFormattedPost).toList()));
    }

    private void loadUserPosts() {
        List<Post> posts = PostService.getUserPosts(currentUser.getId());
        postListView.setItems(FXCollections.observableArrayList(posts.stream().map(Post::getFormattedPost).toList()));
    }

    private void loadUserSubscriptions() {
        List<Topic> topics = TopicService.getUserTopics(currentUser.getId());
        subscriptionListView.setItems(FXCollections.observableArrayList(topics.stream().map(Topic::getTopicName).toList()));
    }

    private void setupTopicSearch() {
        topicSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                List<Topic> topics = TopicService.searchTopics(newValue);
                topicSearchResults.setItems(FXCollections.observableArrayList(topics.stream().map(Topic::getTopicName).toList()));
            } else {
                topicSearchResults.getItems().clear();
            }
        });
    }

    @FXML
    private void handlePost() {
        String text = postTextField.getText().trim();
        if (text.length() < 3) {
            showAlert("Post Error", "Post must be at least 3 characters long!");
            return;
        }

        PostService.addPost(currentUser.getId(), text);
        postTextField.clear();
        loadUserPosts();
    }

    @FXML
    private void handleSubscribe() {
        String selectedTopic = topicSearchResults.getSelectionModel().getSelectedItem();
        if (selectedTopic != null) {
            int topicId = TopicService.getTopicIdByName(selectedTopic);
            if (topicId != -1) {
                TopicService.subscribeUserToTopic(currentUser.getId(), topicId);
                loadUserSubscriptions();
            } else {
                showAlert("Subscription Error", "Topic not found!");
            }
        }
    }

    @Override
    public void update(Post newPost) {
        Platform.runLater(() -> feedListView.getItems().add(0, newPost.getFormattedPost()));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
