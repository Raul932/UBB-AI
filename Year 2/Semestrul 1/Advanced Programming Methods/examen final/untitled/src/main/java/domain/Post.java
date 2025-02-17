package domain;

import java.sql.Timestamp;

public class Post {
    private int id;
    private String postText;
    private Timestamp timestamp;
    private String username;

    public Post(int id, String postText, Timestamp timestamp, String username) {
        this.id = id;
        this.postText = postText;
        this.timestamp = timestamp;
        this.username = username;
    }

    public String getFormattedPost() {
        return username + ": " + postText + " [" + timestamp + "]";
    }

    public String getPostText() {
        return postText;
    }
}
