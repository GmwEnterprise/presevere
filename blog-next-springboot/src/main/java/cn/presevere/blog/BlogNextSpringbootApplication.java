package cn.presevere.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class BlogNextSpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogNextSpringbootApplication.class, args);
    }

    @RestController
    @RequestMapping("/testCtrl")
    static class TestController {
        @GetMapping("/returnTime1")
        LocalDateTime returnTime1() {
            return LocalDateTime.now();
        }
        @GetMapping("/returnTime2")
        Map<Long, LocalDateTime> returnTime2() {
            var map = new HashMap<Long, LocalDateTime>(2);
            map.put(1L, LocalDateTime.now());
            map.put(2L, LocalDateTime.now().plusDays(1L));
            return map;
        }
    }
}
