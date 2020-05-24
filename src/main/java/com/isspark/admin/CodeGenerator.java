package com.isspark.admin;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;


/**
 * @version V1.0
 * @class: CodeGenerator
 * @description: 自动生成代码工具类
 * @author: xuzheng
 * @create: 2020-04-09 11:18
 **/
public class CodeGenerator {

    /**
     * 包名
     */
    private static final String PACKAGE_NAME = "com.isspark.admin";

    /**
     * 模块名称
     */
    private static final String MODULE_NAME = "domain.entity";

    /**
     * 表名
     */
    private static final String[] TABLE_NAMES = new String[] {"sys_role","sys_user_role","sys_resource","sys_role_resource"};

    /**
     * 剔除表名前缀
     */
    private static final String[] PREFIX = new String[]{""};

    /**
     * 代码生成者
     */
    private static final String AUTHOR = "xuzheng";

    /**
     * 数据源配置
     */
    final static  String DB_URL = "jdbc:mysql://localhost:3306/admin?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=CTT&allowMultiQueries=true";
    final static  String USERNAME = "root";
    final static  String PASSWORD = "shouwang13";
//    final static  String DB_URL = "jdbc:mysql://10.31.1.137:3306/rbl_bi?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=CTT&allowMultiQueries=true";
//    final static  String USERNAME = "szqbtest";
//    final static  String PASSWORD = "szqb@test";
    final static  String DRIVER = "com.mysql.cj.jdbc.Driver";


    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
//    public static String scanner(String tip) {
//        Scanner scanner = new Scanner(System.in);
//        StringBuilder help = new StringBuilder();
//        help.append("请输入" + tip + "：");
//        System.out.println(help.toString());
//        if (scanner.hasNext()) {
//            String ipt = scanner.next();
//            if (StringUtils.isNotEmpty(ipt)) {
//                return ipt;
//            }
//        }
//        throw new MybatisPlusException("请输入正确的" + tip + "！");
//    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java/tmp");
        gc.setAuthor(AUTHOR);
        gc.setOpen(false);

        //service 命名方式
        gc.setServiceName("%sService");
        //service impl 命名方式
        gc.setServiceImplName("%sServiceImpl");
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(true);
        gc.setDateType(DateType.ONLY_DATE);

        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(DB_URL);
        // dsc.setSchemaName("public");
        dsc.setDriverName(DRIVER);
        dsc.setUsername(USERNAME);
        dsc.setPassword(PASSWORD);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
        //父包名, 自定义包路径  如果为空，将下面子包名必须写全部， 否则就只需写子包名
        pc.setParent(PACKAGE_NAME);
        pc.setEntity(MODULE_NAME);
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setXml("xml");
        //设置控制器包名
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        // 自定义配置
//        InjectionConfig cfg = new InjectionConfig() {
////            @Override
////            public void initMap() {
////                // to do nothing
////            }
////        };

        // 如果模板引擎是 freemarker
//        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
//        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
//        focList.add(new FileOutConfig(templatePath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
//                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//            }
//        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });
        */
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);

        // 配置模板
//        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

//        templateConfig.setXml(null);
        mpg.setTemplate(new TemplateConfig());

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
//        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");
//        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setInclude(TABLE_NAMES);
        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setTablePrefix(pc.getModuleName() + "_");
        strategy.setTablePrefix(PREFIX);
        mpg.setStrategy(strategy);
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
