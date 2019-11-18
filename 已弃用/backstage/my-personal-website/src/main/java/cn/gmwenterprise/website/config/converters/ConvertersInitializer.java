package cn.gmwenterprise.website.config.converters;

import org.springframework.format.support.FormattingConversionService;
import org.springframework.stereotype.Component;

@Component
public class ConvertersInitializer {

    public ConvertersInitializer(FormattingConversionService formattingConversionService) {
        formattingConversionService.addConverter(new String2LocalDateTime());
        formattingConversionService.addConverter(new String2LocalDate());
        formattingConversionService.addConverter(new String2LocalTime());
        formattingConversionService.addConverter(new String2Date());
    }
}
