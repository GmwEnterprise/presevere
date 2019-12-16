package cn.gmwenterprise.presevere.service;

import java.util.Map;

public interface ThymeleafTemplateService {

    String render(String template, Map<String, Object> params);
}
