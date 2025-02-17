package domain;

public class Topic {
    private int id;
    private String topicName;

    public Topic(int id, String topicName) {
        this.id = id;
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }
}
