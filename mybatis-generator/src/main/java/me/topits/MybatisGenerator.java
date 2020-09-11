package me.topits;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wang Ziyue
 * @date 2020/5/18 21:40
 */
@Data
@Accessors(chain = true)
public class MybatisGenerator {
    private String projectPath;
    private String packageName;
    private String moduleName;
    private String tableName;
    private String author;
    private String databaseUrl;
    private String databaseUser;
    private String databasePassword;
    private AutoGenerator autoGenerator;

    public void execute() {
        AutoGenerator autoGenerator = new AutoGenerator();
        this.setAutoGenerator(autoGenerator);
        // 全局配置
        autoGenerator.setGlobalConfig(this.getGlobalConfig());
        // 数据源
        autoGenerator.setDataSource(this.getDataSourceConfig());
        // 包配置
        autoGenerator.setPackageInfo(this.getPackageConfig());
        // 自定义配置
        autoGenerator.setCfg(this.getInjectionConfig());
        // 模板配置
        autoGenerator.setTemplate(this.getTemplateConfig());
        // 策略配置
        autoGenerator.setStrategy(this.getStrategyConfig());

        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        autoGenerator.execute();
    }

    private GlobalConfig getGlobalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        // 输出目录 projectPath + "/src/main/java"
        globalConfig.setOutputDir(projectPath + "/src/main/java");
        // 覆盖原有文件
        globalConfig.setFileOverride(true);
        // 是否打开输出目录
        globalConfig.setOpen(false);
        // 开发人员
        globalConfig.setAuthor(author);
        // 开启 BaseResultMap
        globalConfig.setBaseResultMap(true);
        // 时间类型
        globalConfig.setDateType(DateType.ONLY_DATE);
        // 开启 baseColumnList
        globalConfig.setBaseColumnList(true);
        return globalConfig;
    }

    private DataSourceConfig getDataSourceConfig() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setUrl(databaseUrl);
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername(databaseUser);
        dataSourceConfig.setPassword(databasePassword);
        return dataSourceConfig;
    }

    private PackageConfig getPackageConfig() {
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(packageName);
        // 父包模块名
        packageConfig.setModuleName(moduleName);
        return packageConfig;
    }

    private InjectionConfig getInjectionConfig() {
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 模板引擎
        String templatePath = "/templates/mapper.xml.ftl";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/" +
                        autoGenerator.getPackageInfo().getParent().replaceAll("\\.", "/") + "/" +
                        autoGenerator.getPackageInfo().getMapper() + "/" +
                        tableInfo.getEntityName() + "Mapper" +
                        StringPool.DOT_XML;
            }
        });
        injectionConfig.setFileOutConfigList(focList);
        return injectionConfig;
    }

    private TemplateConfig getTemplateConfig() {
        TemplateConfig templateConfig = new TemplateConfig();
        // 配置自定义输出模板
        // 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // 已定制化 只保留.ftl
        templateConfig.setEntity("templates/entity.java");
        templateConfig.setController(null);
        // src 下 mapper目录不生成xml
        templateConfig.setXml(null);
        return templateConfig;
    }

    private StrategyConfig getStrategyConfig() {
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        // strategyConfig.setSuperEntityClass("com.pgy.BaseEntity");
        strategyConfig.setEntityLombokModel(true);
        // strategyConfig.setSuperControllerClass("com.pgy.BaseController");
        strategyConfig.setRestControllerStyle(false);
        // 表名
        strategyConfig.setInclude(tableName);
        strategyConfig.setControllerMappingHyphenStyle(true);
        // 字段常量
        strategyConfig.setEntityColumnConstant(true);
        // 会在字段属性上加上@TableField
        strategyConfig.setEntityTableFieldAnnotationEnable(true);
//        strategy.setTablePrefix(pc.getModuleName() + "_");//会在类上加上@TableName
        return strategyConfig;
    }


}
