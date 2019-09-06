package cn.gmwenterprise.website.config.converters;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

public class String2LocalDate implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        return LocalDate.parse(source);
    }
}
