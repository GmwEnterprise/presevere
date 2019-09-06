package cn.gmwenterprise.website.config.converters;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.util.Date;

import static cn.gmwenterprise.website.util.DateUtils.DATE_TIME_PATTERN;
import static cn.gmwenterprise.website.util.DateUtils.SIMPLE_DATE_FORMAT;

public class String2Date implements Converter<String, Date> {
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
