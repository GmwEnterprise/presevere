package cn.gmwenterprise.website.config.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    private final JavaTimeModule javaTimeModule;

    public RedisConfig(JavaTimeModule javaTimeModule) {
        this.javaTimeModule = javaTimeModule;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        ObjectMapper objectMapper = new ObjectMapper()
            .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
            .enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.registerModule(javaTimeModule);

        StringRedisSerializer key = new StringRedisSerializer();
        Jackson2JsonRedisSerializer value = new Jackson2JsonRedisSerializer<Object>(Object.class);
        value.setObjectMapper(objectMapper);

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(key);
        template.setValueSerializer(value);
        template.setHashKeySerializer(key);
        template.setHashValueSerializer(value);
        template.afterPropertiesSet();
        return template;
    }
}
