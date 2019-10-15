package cn.gmwenterprise.website.generator;

import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

@Slf4j
public class CodeHelper {

    private static final String DOMAIN_PATH = "/src/main/java/cn/gmwenterprise/website/domain/";
    private static final String VO_PATH = "/src/main/java/cn/gmwenterprise/website/vo/";
    private static final String DAO_PATH = "/src/main/java/cn/gmwenterprise/website/dao/";
    private static final String MAPPER_PATH = "/src/main/resources/dao-mapping/";
    private static final String SERVICE_PATH = "/src/main/java/cn/gmwenterprise/website/service/";
    private static final String SERVICE_IMPL_PATH = "/src/main/java/cn/gmwenterprise/website/service/impl/";
    private static final String CONTROLLER_PATH = "/src/main/java/cn/gmwenterprise/website/web/";

    public static void main(String[] args) throws Exception {
//        generateTable("pre_article_msg");
//        generateTable("pre_article_body");
//        generateTable("pre_article_draft");
    }

    private static void generateTable(String tableName) throws Exception {
        TableStruct tableStruct = DatabaseHelper.getInstance().getTableStruct(tableName);
        if (tableStruct == null) {
            System.err.println(tableName + "表不存在");
            return;
        }
        generateAllFiles(tableStruct);
    }

    private static void generateAllFiles(TableStruct ts) throws Exception {
        if (ts == null) {
            System.out.println("滚");
            return;
        }
        Map<String, Object> tsMap = ts.toMap();
        // 前端
        generateVueCode(tsMap);
        // 后端
        generateControllerFile(tsMap);
        generateDomainFile(tsMap);
        generateBoFile(tsMap);
        generateDaoFile(tsMap);
        generateMapperFile(tsMap);
        generateServiceFile(tsMap);
        generateServiceImplFile(tsMap);
    }

    private static void generateVueCode(Map<String, Object> ts) throws Exception {
        String path = System.getProperty("user.dir") + "/src/main/resources/vue-code/" + ts.get("entityAlias") + "/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        serviceJs(path, ts);
        routerJs(path, ts);
        listJs(path, ts);
        editJs(path, ts);
    }

    /**
     * 生成domain
     *
     * @throws Exception 异常
     */
    private static void generateDomainFile(Map<String, Object> ts) throws Exception {
        Template template = FreemarkerConfig.getTemplate("domain.ftl");
        File po = new File(System.getProperty("user.dir") + DOMAIN_PATH + ts.get("entityName") + ".java");
        Writer out = new OutputStreamWriter(new FileOutputStream(po));
        template.process(ts, out);
        out.flush();
        log.info("生成{}.java", ts.get("entityName"));
    }

    /**
     * 生成vo
     *
     * @throws Exception 异常
     */
    private static void generateBoFile(Map<String, Object> ts) throws Exception {
        Template template = FreemarkerConfig.getTemplate("vo.ftl");
        File po = new File(System.getProperty("user.dir") + VO_PATH + ts.get("entityName") + "Vo.java");
        Writer out = new OutputStreamWriter(new FileOutputStream(po));
        template.process(ts, out);
        out.flush();
        log.info("生成{}Vo.java", ts.get("entityName"));
    }

    /**
     * 生成dao
     *
     * @throws Exception 异常
     */
    private static void generateDaoFile(Map<String, Object> ts) throws Exception {
        Template template = FreemarkerConfig.getTemplate("dao.ftl");
        File po = new File(System.getProperty("user.dir") + DAO_PATH + ts.get("entityName") + "Dao.java");
        Writer out = new OutputStreamWriter(new FileOutputStream(po));
        template.process(ts, out);
        out.flush();
        log.info("生成{}Dao.java", ts.get("entityName"));
    }

    /**
     * 生成mapper
     *
     * @throws Exception 异常
     */
    private static void generateMapperFile(Map<String, Object> ts) throws Exception {
        Template template = FreemarkerConfig.getTemplate("mapper.ftl");
        File po = new File(System.getProperty("user.dir") + MAPPER_PATH + ts.get("entityName") + "Mapper.xml");
        Writer out = new OutputStreamWriter(new FileOutputStream(po));
        template.process(ts, out);
        out.flush();
        log.info("生成{}Mapper.xml", ts.get("entityName"));
    }

    /**
     * 生成service
     *
     * @throws Exception 异常
     */
    private static void generateServiceFile(Map<String, Object> ts) throws Exception {
        Template template = FreemarkerConfig.getTemplate("service.ftl");
        File po = new File(System.getProperty("user.dir") + SERVICE_PATH + ts.get("entityName") + "Service.java");
        Writer out = new OutputStreamWriter(new FileOutputStream(po));
        template.process(ts, out);
        out.flush();
        log.info("生成{}Service.java", ts.get("entityName"));
    }

    /**
     * 生成serviceImpl
     *
     * @throws Exception 异常
     */
    private static void generateServiceImplFile(Map<String, Object> ts) throws Exception {
        Template template = FreemarkerConfig.getTemplate("service-impl.ftl");
        File po = new File(System.getProperty("user.dir") + SERVICE_IMPL_PATH + ts.get("entityName") + "ServiceImpl.java");
        Writer out = new OutputStreamWriter(new FileOutputStream(po));
        template.process(ts, out);
        out.flush();
        log.info("生成{}ServiceImpl.java", ts.get("entityName"));
    }

    /**
     * 生成controller
     *
     * @throws Exception 异常
     */
    private static void generateControllerFile(Map<String, Object> ts) throws Exception {
        Template template = FreemarkerConfig.getTemplate("controller.ftl");
        File po = new File(System.getProperty("user.dir") + CONTROLLER_PATH + ts.get("entityName") + "Controller.java");
        Writer out = new OutputStreamWriter(new FileOutputStream(po));
        template.process(ts, out);
        out.flush();
        log.info("生成{}Controller.java", ts.get("entityName"));
    }

    private static void serviceJs(String path, Map<String, Object> ts) throws Exception {
        Template template = FreemarkerConfig.getTemplate("vue-service-js.ftl");
        File po = new File(path + ts.get("entityAlias") + ".service.js");
        Writer out = new OutputStreamWriter(new FileOutputStream(po));
        template.process(ts, out);
        out.flush();
        log.info("前端 生成{}.service.js", ts.get("entityAlias"));
    }

    private static void routerJs(String path, Map<String, Object> ts) throws Exception {
        Template template = FreemarkerConfig.getTemplate("vue-router-js.ftl");
        File po = new File(path + ts.get("entityAlias") + ".router.js");
        Writer out = new OutputStreamWriter(new FileOutputStream(po));
        template.process(ts, out);
        out.flush();
        log.info("前端 生成{}.router.js", ts.get("entityAlias"));
    }

    private static void listJs(String path, Map<String, Object> ts) throws Exception {
        Template template = FreemarkerConfig.getTemplate("vue-list-js.ftl");
        File po = new File(path + ts.get("entityName") + "List.vue");
        Writer out = new OutputStreamWriter(new FileOutputStream(po));
        template.process(ts, out);
        out.flush();
        log.info("前端 生成{}List.vue", ts.get("entityName"));
    }

    private static void editJs(String path, Map<String, Object> ts) throws Exception {
        Template template = FreemarkerConfig.getTemplate("vue-edit-js.ftl");
        File po = new File(path + ts.get("entityName") + "Edit.vue");
        Writer out = new OutputStreamWriter(new FileOutputStream(po));
        template.process(ts, out);
        out.flush();
        log.info("前端 生成{}Edit.vue", ts.get("entityName"));
    }
}
