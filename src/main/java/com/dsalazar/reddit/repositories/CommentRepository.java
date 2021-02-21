package com.dsalazar.reddit.repositories;

import com.dsalazar.reddit.models.Comment;
import com.dsalazar.reddit.models.Post;
import com.dsalazar.reddit.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
