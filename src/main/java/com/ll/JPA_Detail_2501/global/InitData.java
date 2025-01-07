package com.ll.JPA_Detail_2501.global;

import com.ll.JPA_Detail_2501.domain.post.Post;
import com.ll.JPA_Detail_2501.domain.post.PostService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class InitData {
    @Bean
    public ApplicationRunner applicationRunner(PostService chatRoomService) {
        return args -> {
            Post post = chatRoomService.create("제목", "내용", "작성자");
            System.out.println("서버동작");
        };
    }
}
