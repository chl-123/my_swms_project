package com.fs.swms;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.*;

/**
 * <p>
 * 代码生成器演示
 * </p>
 */
public class CodeGenerator {

    final static String  dirPath = "H:/project";
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }


    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        // 选择 freemarker 引擎，默认 Veloctiy
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
//        mpg.setTemplateEngine(new BeetlTemplateEngine());


        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(dirPath + "/my_swms_project/src/main/java");
        System.out.println( "路径为" + gc.getOutputDir());
        gc.setAuthor("chl");
        gc.setDateType(DateType.ONLY_DATE);
        gc.setFileOverride(true); //是否覆盖
        gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setSwagger2(true);
        gc.setOpen(false);

        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.ORACLE);
        dsc.setDriverName("oracle.jdbc.OracleDriver");
        dsc.setUsername("ROOT");
        dsc.setPassword("82042808");
        dsc.setUrl("jdbc:oracle:thin:@localhost:1521:ORCL");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setEntityLombokModel(true);
//        strategy.setCapitalMode( !strategy.isCapitalMode());// 全局大写命名 ORACLE 注意
        strategy.setTablePrefix(new String[] { "T_BD_", "T_MM_","T_SYS_" });// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//列名规则

        strategy.setEntityLombokModel(true);//是否生成lombok注解
        //自动填充的配置
        TableFill create_time = new TableFill("create_time", FieldFill.INSERT);//设置时的生成策略
        TableFill update_time = new TableFill("update_time", FieldFill.INSERT_UPDATE);//设置更新时间的生成策略
        ArrayList<TableFill> list = new ArrayList<>();
        list.add(create_time);
        list.add(update_time);
        strategy.setTableFillList(list);
        strategy.setRestControllerStyle(true);//开启驼峰命名
        // 如果 setInclude() 不加参数, 会自定查找所有表
        strategy.setInclude(new String[] { "T_BD_PRODUCT" }); // 需要生成的表
        //strategy.setInclude(scanner("表名"));
//        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        // 自定义实体父类
         strategy.setSuperEntityClass("com.fs.swms.common.entity.BaseEntity");
        // 自定义实体，公共字段
//        strategy.setSuperEntityColumns(new String[] { "CREATE_TIME", "CREATOR","UPDATE_TIME","OPERATOR" });
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        strategy.setEntityBuilderModel(true);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com");
        pc.setModuleName("fs.swms.mainData");
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        /*pc.setXml("mapperXml");*/

        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.zwh 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("chl", this.getConfig().getGlobalConfig().getAuthor() + "的模板生成完成！");
                this.setMap(map);
            }
        };

        // 自定义 xxList.jsp 生成
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();


        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";


        //自定义配置会优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return dirPath + "/my_swms_project/src/main/resources/com/fs/swms/mainData/mapper/"
                        //+ tableInfo.getEntityName() + "Mapper.xml";
                        + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);



        // 执行生成
        mpg.execute();

        // 打印注入设置【可无】
        System.err.println(mpg.getCfg().getMap().get("chl"));
    }

}