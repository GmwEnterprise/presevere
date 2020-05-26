package com.github.mrag.wechat;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public final class AppConfig {

    @Configuration
    @EnableWebMvc
    public static class WebConfig implements WebMvcConfigurer {
        // TODO
    }

    @Configuration
    @MapperScan(annotationClass = Mapper.class)
    public static class DataAccessConfig {

        @Bean(name = "redisTemplate")
        public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
            RedisTemplate<String, Object> template = new RedisTemplate<>();
            template.setConnectionFactory(factory);

            StringRedisSerializer keySerializer = new StringRedisSerializer();
            template.setKeySerializer(keySerializer);
            template.setHashKeySerializer(keySerializer);

            FastJsonRedisSerializer<Object> valueSerializer = new FastJsonRedisSerializer<>(Object.class);
            template.setValueSerializer(valueSerializer);
            template.setHashValueSerializer(valueSerializer);
            return template;
        }
    }
}
