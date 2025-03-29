package com.sso.mp;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.Scanner;

/**
 * MyBatisPlus代码生成器
 */
public class MyBatisPlusGenerator {

    //todo：修改这里的数据库名到自己的数据库中
    private static String URL = "jdbc:mysql://localhost:3307/{dataBaseName}?useSSL=false";
    //数据库用户名
    private static String USERNAME = "root";
    //数据库密码
    private static String PASSWORD = "root";
    //作者
    private static String AUTHOR = "lihainuo";

    public static void main(String[] args) {
        String projectPath = scanner("请输入生成文件的目标目录（绝对路径）");
        String moduleName = scanner("模块名");
        String dataBaseName = scanner("数据库名");
        String[] tableNames = scanner("表名，多个英文逗号分割").split(",");
        String prefix = scanner("表前缀,不存在输入-1");
        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator(initDataSourceConfig(dataBaseName));
        autoGenerator.global(initGlobalConfig(projectPath));
        autoGenerator.packageInfo(initPackageConfig(projectPath, moduleName));
        autoGenerator.injection(initInjectionConfig(projectPath, moduleName));
        autoGenerator.template(initTemplateConfig());
        autoGenerator.strategy(initStrategyConfig(tableNames, prefix));
        autoGenerator.execute(new VelocityTemplateEngine());
    }

    /**
     * 读取控制台内容信息
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(("请输入" + tip + "："));
        if (scanner.hasNext()) {
            String next = scanner.next();
            if (StringUtils.isNotEmpty(next)) {
                return next;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * 初始化全局配置
     */
    private static GlobalConfig initGlobalConfig(String projectPath) {
        return new GlobalConfig.Builder()
                //添加swagger注解
                //.enableSwagger()
                .outputDir(projectPath + "/src/main/java")
                .author(AUTHOR)
                .disableOpenDir()
                .dateType(DateType.ONLY_DATE)
                .build();
    }

    /**
     * 初始化数据源配置
     */
    private static DataSourceConfig initDataSourceConfig(String dataBaseName) {
        return new DataSourceConfig.Builder(URL.replace("{dataBaseName}", dataBaseName), USERNAME, PASSWORD)
                .dbQuery(new MySqlQuery())
                .build();
    }

    /**
     * 初始化包配置
     */
    private static PackageConfig initPackageConfig(String projectPath, String moduleName) {
        return new PackageConfig.Builder()
                .parent(moduleName)
                .entity("entity")
                .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "/src/main/resources/" + moduleName + "/mapper"))
                .build();
    }

    /**
     * 初始化模板配置
     */
    private static TemplateConfig initTemplateConfig() {
        //可以对controller、service、entity模板进行配置
        return new TemplateConfig.Builder().build();
    }

    /**
     * 初始化策略配置
     */
    private static StrategyConfig initStrategyConfig(String[] tableNames, String prefix) {
        StrategyConfig.Builder builder = new StrategyConfig.Builder();
        //去表前缀
        if (!"-1".equals(prefix)) {
            builder.addTablePrefix(prefix);
        }
        builder
                .entityBuilder()
                .naming(NamingStrategy.underline_to_camel)
                .columnNaming(NamingStrategy.underline_to_camel)
                .enableLombok()
                .formatFileName("%s")
                .mapperBuilder()
                .enableMapperAnnotation()
                .enableBaseResultMap()
                .formatMapperFileName("%sMapper")
                .formatXmlFileName("%sMapper")
                .serviceBuilder()
                .formatServiceFileName("%sService")
                .formatServiceImplFileName("%sServiceImpl")
                .controllerBuilder()
                .enableRestStyle()
                .formatFileName("%sController");
        //当表名中带*号时可以启用通配符模式
        if (tableNames.length == 1 && tableNames[0].contains("*")) {
            String[] likeStr = tableNames[0].split("_");
            String likePrefix = likeStr[0] + "_";
            builder.likeTable(new LikeTable(likePrefix));
        } else {
            builder.addInclude(tableNames);
        }
        return builder.build();
    }

    /**
     * 初始化自定义配置
     */
    private static InjectionConfig initInjectionConfig(String projectPath, String moduleName) {
        // 自定义配置
        return new InjectionConfig.Builder().build();
    }

}