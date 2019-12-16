package cn.gmwenterprise.presevere.service.impl;

import cn.gmwenterprise.presevere.service.ThymeleafTemplateService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.Map;

@Service
public class ThymeleafTemplateServiceImpl implements ThymeleafTemplateService {
    final SpringTemplateEngine springTemplateEngine;

    public ThymeleafTemplateServiceImpl(SpringTemplateEngine springTemplateEngine) {
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    public String render(String template, Map<String, Object> params) {
        Context context = new Context(LocaleContextHolder.getLocale());
        context.setVariables(params);
        return springTemplateEngine.process(template, context);
    }
}
