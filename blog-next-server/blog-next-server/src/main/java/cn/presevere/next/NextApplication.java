package cn.presevere.next;

import cn.presevere.next.domain.Post;
import cn.presevere.next.domain.Router;
import cn.presevere.next.repo.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class NextApplication {
    public static void main(String[] args) {
        SpringApplication.run(NextApplication.class, args);
    }

    @Bean
    public Post test(PostRepository postRepository) {
        var post = new Post();
        post.setTitle("title2");
        post.setCatalog(Arrays.asList(
            new Router() {{
                setLevel(1);
                setRouterName("111");
            }},
            new Router() {{
                setRouterName("222");
                setLevel(2);
            }}
        ));
        post.setIssuingTime(LocalDateTime.now());
        var r = postRepository.save(post);
        System.out.println(r);
        return r;
    }
}
