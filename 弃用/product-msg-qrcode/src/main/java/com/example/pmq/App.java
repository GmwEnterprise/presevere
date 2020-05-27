package com.example.pmq;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.example.pmq.mapper", annotationClass = Mapper.class)
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
