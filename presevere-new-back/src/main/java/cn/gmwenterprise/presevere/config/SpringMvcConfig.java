package cn.gmwenterprise.presevere.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
@EnableWebMvc
public class SpringMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 跨域配置
        registry.addMapping("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 静态资源放行
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器
        registry.addInterceptor(new SecurityInterceptor());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 添加消息转换器
        converters.add(new MappingJackson2HttpMessageConverter(objectMapper()));
    }

    @Bean
    public ObjectMapper objectMapper() {
        // 自定义的objectMapper，添加了时间转换模块
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(javaTimeModule());
        return objectMapper;
    }

    @Bean
    public JavaTimeModule javaTimeModule() {
        // 自定义java8时间转换模块
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        DateTimeFormatter datetime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(datetime));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(date));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(time));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(datetime));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(date));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(time));
        return javaTimeModule;
    }
}
