package com.example.pmq.generator;

import org.apache.ibatis.io.Resources;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MapperGenerator {
    /**
     * 是否覆盖已生成的代码
     */
    static boolean overwrite = true;

    public static void main(String[] args) throws Exception {
        // mbg执行过程中的警告信息
        List<String> warnings = new ArrayList<>();
        // mbg配置文件
        InputStream resource = Resources.getResourceAsStream("mybatis-generator-config.xml");

        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration conf = cp.parseConfiguration(resource);
        resource.close();

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);

        // 创建mbg
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(conf, callback, warnings);
        // 执行
        myBatisGenerator.generate(null);
        warnings.forEach(System.out::println);
    }
}
