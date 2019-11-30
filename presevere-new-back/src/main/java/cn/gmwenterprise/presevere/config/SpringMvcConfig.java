package cn.gmwenterprise.presevere.config;

import cn.gmwenterprise.presevere.config.security.SecurityInterceptor;
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
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import static cn.gmwenterprise.presevere.common.DateUtils.*;

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

    @Resource
    SecurityInterceptor securityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器
        registry.addInterceptor(securityInterceptor).order(3);
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
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DATE_TIME_FORMATTER));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DATE_FORMATTER));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(TIME_FORMATTER));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DATE_TIME_FORMATTER));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DATE_FORMATTER));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(TIME_FORMATTER));
        return javaTimeModule;
    }

    interface String2LocalDateTime extends Converter<String, LocalDateTime> {
    }

    interface String2LocalDate extends Converter<String, LocalDate> {
    }

    interface String2LocalTime extends Converter<String, LocalTime> {
    }

    interface String2Date extends Converter<String, Date> {
    }

    /**
     * 定义请求参数进入controller后的转换方式
     */
    @Component
    static class ConvertersInitializer {
        public ConvertersInitializer(FormattingConversionService formattingConversionService) {
            formattingConversionService.addConverter((String2LocalDateTime) source -> LocalDateTime.parse(source, DATE_TIME_FORMATTER));
            formattingConversionService.addConverter((String2LocalDate) source -> LocalDate.parse(source, DATE_TIME_FORMATTER));
            formattingConversionService.addConverter((String2LocalTime) source -> LocalTime.parse(source, DATE_TIME_FORMATTER));
            formattingConversionService.addConverter((String2Date) source -> {
                try {
                    return SIMPLE_DATE_FORMAT.parse(DATE_TIME_PATTERN);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return null;
                }
            });
        }
    }
}
