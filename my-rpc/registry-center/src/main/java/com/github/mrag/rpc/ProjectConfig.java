package com.github.mrag.rpc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProjectConfig {

    private static ObjectMapper objectMapper = null;

    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            synchronized (ProjectConfig.class) {
                if (objectMapper == null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    JavaTimeModule javaTimeModule = new JavaTimeModule();
                    javaTimeModule.addSerializer(
                            LocalDateTime.class, new LocalDateTimeSerializer(formatter));
                    javaTimeModule.addDeserializer(
                            LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
                    objectMapper = new ObjectMapper();
                    objectMapper.registerModule(javaTimeModule);
                }
            }
        }
        return objectMapper;
    }

    @Configuration
    @EnableWebMvc
    public static class ConfigWebMvc implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**").allowedMethods(
                    RequestMethod.GET.name(),
                    RequestMethod.POST.name(),
                    RequestMethod.PUT.name(),
                    RequestMethod.PATCH.name(),
                    RequestMethod.OPTIONS.name(),
                    RequestMethod.HEAD.name(),
                    RequestMethod.DELETE.name()
            );
        }

        @Override
        public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
            converters.add(new MappingJackson2HttpMessageConverter(getObjectMapper()));
        }
    }

    @Configuration
    @EnableConfigurationProperties({
            RpcProperties.class
    })
    public static class ConfigProperties {
    }
}
