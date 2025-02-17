package observers;

import domain.Post;

public interface PostObserver {
    void update(Post newPost);
}
