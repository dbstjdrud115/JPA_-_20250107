package com.ll.JPA_Detail_2501.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public Post showPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @GetMapping("/findByUsername/{username}")
    public List<Post> findByUsername(@PathVariable("username") String username) {
        return postService.findByUsername(username);
    }

    //DB 읽기 Lock(ShareLock)  테스트
    @GetMapping("/findWithShareLockById/{id}")
    public Post findWithShareLockById(@PathVariable Long id) {
        return postService.findWithShareLockById(id).orElse(null);
    }

    //DB 쓰기 Lock(WriteLcok)  테스트
    @GetMapping("/findWithWriteLockById/{id}")
    public Post findWithWriteLockById(@PathVariable Long id) {
        return postService.findWithWriteLockById(id).orElse(null);
    }


}
