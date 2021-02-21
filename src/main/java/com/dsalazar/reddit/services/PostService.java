package com.dsalazar.reddit.services;

import com.dsalazar.reddit.dto.PostRequest;
import com.dsalazar.reddit.dto.PostResponse;
import com.dsalazar.reddit.exceptions.PostNotFoundException;
import com.dsalazar.reddit.exceptions.SubredditNotFoundException;
import com.dsalazar.reddit.mappers.PostMapper;
import com.dsalazar.reddit.models.Post;
import com.dsalazar.reddit.models.Subreddit;
import com.dsalazar.reddit.models.User;
import com.dsalazar.reddit.repositories.PostRepository;
import com.dsalazar.reddit.repositories.SubredditRepository;
import com.dsalazar.reddit.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public void save(PostRequest postRequest)
    {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));

        postRepository.save(
            postMapper.map(postRequest, subreddit, authService.getCurrentUser())
        );
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id)
    {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts()
    {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId)
    {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));

        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username)
    {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
