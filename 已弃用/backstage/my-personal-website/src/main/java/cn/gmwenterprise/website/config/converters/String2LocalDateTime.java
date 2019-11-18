package cn.gmwenterprise.website.config.converters;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;

import static cn.gmwenterprise.website.util.DateUtils.DATE_TIME_FORMATTER;

public class String2LocalDateTime implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String source) {
        return LocalDateTime.parse(source, DATE_TIME_FORMATTER);
    }
}
