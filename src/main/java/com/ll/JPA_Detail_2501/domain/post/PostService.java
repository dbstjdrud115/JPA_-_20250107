package com.ll.JPA_Detail_2501.domain.post;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post not found"));
    }

    public List<Post> findByUsername(String userName) {
        postRepository.findById(1L);
        postRepository.findByUsername(userName);
        return postRepository.findByUsername(userName);
    }

    @SneakyThrows
    public Optional<Post> findWithShareLockById(Long id) {
        postRepository.findWithShareLockById(id);
        Thread.sleep(40000);
        return postRepository.findWithShareLockById(id);
    }

    public Post create(String subject, String content, String username) {
        Post post =  Post.builder()
                .subject(subject)
                .content(content)
                .username(username)
                .build();
        return postRepository.save(post);
    }

    public Optional<Post> findWithWriteLockById(Long id) {
        return postRepository.findWithWriteLockById(id);
    }


    @SneakyThrows
    @Transactional
    public Post modifyOptimistic(Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        Thread.sleep(5_000);
        post.setUsername(post.getUsername() + "!");
        return post;
    }

}