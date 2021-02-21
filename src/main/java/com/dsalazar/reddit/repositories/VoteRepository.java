package com.dsalazar.reddit.repositories;

import com.dsalazar.reddit.models.Post;
import com.dsalazar.reddit.models.User;
import com.dsalazar.reddit.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByIdDesc(Post post, User currentUser);
}
