package cn.gmwenterprise.website.config.converters;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;

public class String2LocalTime implements Converter<String, LocalTime> {
    @Override
    public LocalTime convert(String source) {
        return LocalTime.parse(source);
    }
}
