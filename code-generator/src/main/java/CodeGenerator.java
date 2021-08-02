import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CodeGenerator
 * @Description TODO
 * @Date 2019-04-01
 * @Version 0.1
 */
public class CodeGenerator {


    public static void main(String[] args) {
        Configuration config = getConfig();

        // 代码生成器
        AutoGenerator gen = new AutoGenerator();

        GlobalConfig globalConfig = new GlobalConfig();
        // 输出目录，必须填写绝对路径
        globalConfig.setOutputDir(config.getString("srcJavaPath"));
        // 是否覆盖文件
        globalConfig.setFileOverride(false);
        // 开启 activeRecord 模式
        globalConfig.setActiveRecord(false);
        // XML 二级缓存
        globalConfig.setEnableCache(false);
        // XML ResultMap
        globalConfig.setBaseResultMap(true);
        // XML columList
        globalConfig.setBaseColumnList(true);
        // 生成后打开文件夹
        globalConfig.setOpen(false);
        globalConfig.setAuthor(config.getString("author"));
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        globalConfig.setMapperName("%sMapper");
        globalConfig.setXmlName("%sMapper");
        globalConfig.setServiceName("I%sService");
        globalConfig.setServiceImplName("%sServiceImpl");
        globalConfig.setControllerName("%sController");
        if (!config.getBoolean("datetimeToLocalDateTime")) {
            globalConfig.setDateType(DateType.ONLY_DATE);
        }

        /**
         * 全局配置
         */
        gen.setGlobalConfig(globalConfig);

        PackageConfig packageConfig = new PackageConfig();
        // 模块名
        packageConfig.setModuleName(config.getString("javaModulePackage"));
        // 自定义包路径
        packageConfig.setParent(config.getString("javaRootPackage"));
        // 这里是控制器包名，默认 web
        packageConfig.setController(config.getString("controllerPackage"));
        // 实体类 包名
        packageConfig.setEntity(config.getString("entityPackage"));
        // mapper 包名
        packageConfig.setMapper(config.getString("mapperPackage"));
        // service 包名
        packageConfig.setService(config.getString("servicePackage"));
        // service 实现类包名
        packageConfig.setServiceImpl(config.getString("serviceImplPackage"));

        /**
         * 包配置
         */
        gen.setPackageInfo(packageConfig);

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        //如果是MySQL新版本得加上时区，否则报错
        dataSourceConfig.setUrl(config.getString("databasUrl") + "?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC");
        dataSourceConfig.setUsername(config.getString("username"));
        dataSourceConfig.setPassword(config.getString("password"));
        dataSourceConfig.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                System.out.println("转换类型：" + fieldType);
                return super.processTypeConvert(globalConfig, fieldType);
            }
        });

        /**
         * 数据库配置
         */
        gen.setDataSource(dataSourceConfig);


        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {

            public void initMap() {
            IFileCreate iFileCreate = new IFileCreate() {

                @Override
                public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                    return !FileType.XML.equals(fileType);
                }
            };

            setFileCreate(iFileCreate);

        }

            public void initTableMap(TableInfo tableInfo) {
                // 子类重写注入表对应补充信息
                getUpperFieldList(tableInfo, config);

            }

            /**
             * 模板待渲染 Object Map 预处理<br>
             * com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine
             * 方法： getObjectMap 结果处理
             */
            public Map<String, Object> prepareObjectMap(Map<String, Object> objectMap) {
                Map<String, Object> map = new HashMap<String, Object>(6);

                if (config.getBoolean("entityLowerName")) {

                    // 小写entity名称
                    map.put("entityLowerName", StrUtil.lowerFirst(String.valueOf(objectMap.get("entity"))));
                }

                if (config.getBoolean("serviceLowerName")) {

                    String serviceName = ((TableInfo) objectMap.get("table")).getServiceName();
                    String serviceLowerName = StrUtil.subSuf(serviceName, 1);

                    // 小写接口实现类名称
                    map.put("serviceLowerName", StrUtil.lowerFirst(serviceLowerName));
                }

                List<Object> customTemplateParameterNames = config.getList("customTemplateParameterNames");
                if (CollectionUtils.isNotEmpty(customTemplateParameterNames)) {
//                    String[] customTemplateParameterNameArray = StringUtils.split(customTemplateParameterNames, ",");
                    for (Object customTemplateParameterName : customTemplateParameterNames) {
                        String parameter = config.getString(customTemplateParameterName.toString());
                        if ("true".equals(parameter)) {

                            map.put(customTemplateParameterName.toString(), true);
                        }
                        else if ("false".equals(parameter)) {

                            map.put(customTemplateParameterName.toString(), false);
                        } else {
                            map.put(customTemplateParameterName.toString(), parameter);
                        }

                    }
                }

                objectMap.putAll(map);
                return objectMap;
            }

        };

        // 如果模板引擎是 freemarker
//        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String templatePath = config.getString("mapperXmlTemplate") + ".vm";

        // 自定义文件输出位置（非必须）
        List<FileOutConfig> fileOutList = new ArrayList<FileOutConfig>();
        // 自定义配置会被优先输出
        fileOutList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String mapperFile = config.getString("resourceMapperXmlPath") + File.separator + config.getString("javaModulePackage");
                File file = new File(mapperFile);
                boolean exist = file.exists();
                if (!exist) {
                    file.mkdirs();
                }
                return mapperFile + File.separator + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        List<Object> customTemplatePackage = config.getList("customTemplatePackage");
        if (CollectionUtils.isNotEmpty(customTemplatePackage)) {
//            String[] customPackages = StringUtils.split(customTemplatePackage, ",");
            for (Object customPackage : customTemplatePackage) {
                String customTemplate = config.getString(customPackage.toString());
                // 自定义配置会被优先输出
                fileOutList.add(new FileOutConfig(customTemplate + ".vm") {
                    @Override
                    public String outputFile(TableInfo tableInfo) {

                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(config.getString("srcJavaPath")).append(File.separator);
                        stringBuilder.append(config.getString("javaRootPackage").replace(".", "/")).append(File.separator);
                        stringBuilder.append(config.getString("javaModulePackage")).append(File.separator);
                        stringBuilder.append(customPackage);
                        File file = new File(stringBuilder.toString());
                        boolean exist = file.exists();
                        if (!exist) {
                            file.mkdirs();
                        }
                        stringBuilder.append(File.separator);
                        String string = config.getString(customPackage + "-file-suffix");
                        System.out.println(string);
                        stringBuilder.append(tableInfo.getEntityName() + string);
                        return stringBuilder.toString();
                    }
                });

            }

        }

        cfg.setFileOutConfigList(fileOutList);
        gen.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        // 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // 默认不用指定
        templateConfig.setController(config.getString("controllerTemplate"));
        templateConfig.setEntity(config.getString("entityTemplate"));
        templateConfig.setService(config.getString("serviceTemplate"));
        templateConfig.setMapper(config.getString("mapperTemplate"));
        templateConfig.setServiceImpl(config.getString("serviceImplTemplate"));
        gen.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 全局大写命名
        strategy.setCapitalMode(false);
        // 需要生成的表的表名前缀
        strategy.setTablePrefix(new String[]{});
        // 需要生成的表的表名的策略  此处为下滑线分隔（具体给什么参数以数据库为准）
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 需要生成的表的字段名的策略  此处为下滑线分隔 （具体给什么参数以数据库为准）
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 需要生成的表的表名
        strategy.setInclude(config.getList("tableName").toArray(new String[config.getList("tableName").size()]));
        // 需要排除生成的表的表名
//        strategy.setExclude(new String[]{"test"});
        // 设置实体类的父类 默认Model
        strategy.setSuperEntityClass(config.getString("entitySuperClass"));
        // 自定义 mapper 父类 默认BaseMapper
        strategy.setSuperMapperClass("com.baomidou.mybatisplus.core.mapper.BaseMapper");
        // 自定义 service 父类 默认IService
        strategy.setSuperServiceClass("com.baomidou.mybatisplus.extension.service.IService");
        // 自定义 service 实现类父类 默认ServiceImpl
        strategy.setSuperServiceImplClass("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl");
        // 自定义 controller 父类
        strategy.setSuperControllerClass(config.getString("controllerSuperClass"));
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        strategy.setEntityColumnConstant(false);
        // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
        strategy.setEntityLombokModel(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
//        strategy.setEntityBuilderModel(true);
        // 添加restController 注解
        strategy.setRestControllerStyle(true);
//        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        // 自定义实体类的公共字段
        strategy.setSuperEntityColumns("id");
        // 驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        gen.setStrategy(strategy);

        gen.setTemplateEngine(new VelocityTemplateEngine());
        gen.execute();
    }

    private static void getUpperFieldList(TableInfo table, Configuration configuration) {
        List<TableField> fields = table.getFields();
        fields.forEach(tableField -> {
            String propertyName = tableField.getPropertyName();
            HashMap<String, Object> fieldMap = new HashMap<>();
            if (configuration.getBoolean("lowerFieldName")) {

                fieldMap.put("lowerFieldName", StrUtil.lowerFirst(propertyName));
            }
            if (configuration.getBoolean("upperFieldName")) {

                fieldMap.put("upperFieldName", StrUtil.upperFirst(propertyName));
            }
            tableField.setCustomMap(fieldMap);
        });

    }

    /**
     * 获取配置信息
     */
    private static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (Exception e) {
            throw new RuntimeException("获取配置文件失败，", e);
        }
    }
}
