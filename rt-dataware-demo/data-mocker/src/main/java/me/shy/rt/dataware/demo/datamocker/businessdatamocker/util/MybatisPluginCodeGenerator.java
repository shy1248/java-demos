/**
 * @Date        : 2021-04-13 21:41:28
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Mybatis-plugin 自动生成代码
 */

package me.shy.rt.dataware.demo.datamocker.businessdatamocker.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class MybatisPluginCodeGenerator {
    public static void main(String[] args) {
        AutoGenerator generator = new AutoGenerator();

        // 全局配置
        GlobalConfig globalConfig= new GlobalConfig();
        // 输出文件路径
        globalConfig.setOutputDir("/Users/shy/Documents/src/java-demos/realtime-dataware-demo/realtime-dataware-mall-data-mocker/src/main/java");
        // 覆盖文件
        globalConfig.setFileOverride(true);
        // 不需要 ActiveRecord 特性的请改为 false
        globalConfig.setActiveRecord(false);
        // XML 二级缓存
        globalConfig.setEnableCache(false);
        // XML ResultMap
        globalConfig.setBaseResultMap(true);
        // XML columList
        globalConfig.setBaseColumnList(false);
        // 作者
        globalConfig.setAuthor("shy");
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        globalConfig.setControllerName("%sController");
        globalConfig.setServiceName("%sService");
        globalConfig.setServiceImplName("%sServiceImpl");
        globalConfig.setMapperName("%sMapper");
        globalConfig.setXmlName("%sMapper");
        globalConfig.setDateType(DateType.ONLY_DATE);
        generator.setGlobalConfig(globalConfig);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUsername("demo");
        dataSourceConfig.setPassword("123456");
        dataSourceConfig.setUrl("jdbc:mysql://minikube:3306/rt-dataware-demo");
        generator.setDataSource(dataSourceConfig);

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        // 此处可以修改为您的表前缀
        // strategy.setTablePrefix(new String[] { "sys_" });
        // 表名生成策略
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        // 需要生成的表
        strategyConfig.setInclude(new String[] { "coupon_info", "coupon_used" });
        strategyConfig.setSuperServiceClass(null);
        strategyConfig.setSuperServiceImplClass(null);
        strategyConfig.setSuperMapperClass(null);
        generator.setStrategy(strategyConfig);
        // 包配置
        PackageConfig pkgConfig = new PackageConfig();
        pkgConfig.setParent("me.shy.realtime.dataware.malldatamocker.dbmocker");
        pkgConfig.setService("service");
        pkgConfig.setServiceImpl("service.impl");
        pkgConfig.setMapper("mapper");
        pkgConfig.setEntity("bean");
        pkgConfig.setXml("xml");
        generator.setPackageInfo(pkgConfig);
        // 执行生成
        generator.execute();
    }
}
