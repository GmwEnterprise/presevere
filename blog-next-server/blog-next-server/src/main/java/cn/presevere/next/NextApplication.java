package cn.presevere.next;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NextApplication {
    private static final Logger log = LoggerFactory.getLogger(NextApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(NextApplication.class, args);
    }
}
