package cn.presevere.blog.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Configuration
@EnableWebMvc
public class ConfigWeb implements WebMvcConfigurer {
    private static final Logger log = LoggerFactory.getLogger(ConfigWeb.class);

    /**
     * 配置静态资源放行
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
            .addResourceLocations("classpath:static/");
    }

    /**
     * 配置跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE", "OPTION")
            .allowCredentials(true);
    }

    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }

    /**
     * 自定义消息处理器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter(provideObjectMapper()));
        log.info("configureMessageConverters completed: {}", converters);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("extendMessageConverters completed: {}", converters);
    }

    /**
     * 自定义objMapper实现对日期类型的处理
     */
    @Bean(name = "objectMapper")
    ObjectMapper provideObjectMapper() {
        var mapper = new ObjectMapper();
        mapper.registerModule(generateJavaTimeModule());
        return mapper;
    }

    @Bean(name = "javaTimeModule")
    JavaTimeModule generateJavaTimeModule() {
        var module = new JavaTimeModule();
        module.addSerializer(
            LocalDateTime.class,
            new LocalDateTimeSerializer(DATE_TIME_FORMATTER));
        module.addSerializer(
            LocalDate.class,
            new LocalDateSerializer(DATE_FORMATTER));
        module.addSerializer(
            LocalTime.class,
            new LocalTimeSerializer(TIME_FORMATTER));
        module.addDeserializer(
            LocalDateTime.class,
            new LocalDateTimeDeserializer(DATE_TIME_FORMATTER));
        module.addDeserializer(
            LocalDate.class,
            new LocalDateDeserializer(DATE_FORMATTER));
        module.addDeserializer(
            LocalTime.class,
            new LocalTimeDeserializer(TIME_FORMATTER));
        return module;
    }

    /**
     * 定义表单参数全局自动转化
     */
    @Component
    static class ConvertersInitializer {
        @Autowired
        public ConvertersInitializer(FormattingConversionService fcs) {
            fcs.addConverter(new String2Date());
            fcs.addConverter(new String2LocalDate());
            fcs.addConverter(new String2LocalTime());
            fcs.addConverter(new String2LocalDateTime());
        }
    }

    private static class String2LocalDateTime implements Converter<String, LocalDateTime> {
        @Override
        public LocalDateTime convert(String source) {
            return LocalDateTime.parse(source, DATE_TIME_FORMATTER);
        }
    }

    private static class String2LocalDate implements Converter<String, LocalDate> {
        @Override
        public LocalDate convert(String source) {
            return LocalDate.parse(source, DATE_FORMATTER);
        }
    }

    private static class String2LocalTime implements Converter<String, LocalTime> {
        @Override
        public LocalTime convert(String source) {
            return LocalTime.parse(source, TIME_FORMATTER);
        }
    }

    private static class String2Date implements Converter<String, Date> {
        @Override
        public Date convert(String source) {
            try {
                return SIMPLE_DATE_FORMAT.parse(DATE_TIME_PATTERN);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String TIME_PATTERN = "HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_TIME_PATTERN);
}
