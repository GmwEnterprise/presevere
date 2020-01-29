package cn.gmwenterprise.website.generator;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;

class FreemarkerConfig {

    private static final Configuration CONF = new Configuration(Configuration.VERSION_2_3_28);

    static {
        CONF.setTemplateLoader(new ClassTemplateLoader(FreemarkerConfig.class, "/ftl"));
        CONF.setDefaultEncoding("UTF-8");
    }

    static Template getTemplate(String templateName) {
        try {
            return CONF.getTemplate(templateName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void clearCache() {
        CONF.clearTemplateCache();
    }
}
