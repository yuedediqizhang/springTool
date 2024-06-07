//package com.example.jpahibernatedemo;
///*
//关于springboot jpa的配置请参考
//https://github.com/spring-projects/spring-data-examples/blob/main/jpa/multiple-datasources/src/main/java/example/springdata/jpa/multipleds/customer/CustomerConfig.java
//*/
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
//import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
//import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        entityManagerFactoryRef="customerEntityManagerFactory",
//        transactionManagerRef="customerTransactionManager",
//        basePackages= { "com.example.jpahibernatedemo.test.dao" }) //设置Repository所在及其子包位置
//public class DBConfig {
//
//    @Value("jdbc:mysql://${mysql[0].datasource.host}/tree_test?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC")
//    private String host;
//    @Value("${mysql[0].datasource.username}")
//    private String username;
//    @Value("${mysql[0].datasource.password}")
//    private String password;
//
//    // 配置第一个数据源
//    // @Bean("customerDataSource") 这里的customerDataSource是数据源的名称，可以随便取，一般为数据库的名称但是要和下面的方法名一致
//    // 这里是Druid的数据源，如果是其他的数据源，比如Hikari的，那么就要改成HikariDataSource
//    @Bean("customerDataSource")
//    DataSource customerDataSource() {
//        DruidDataSource build = DruidDataSourceBuilder.create().build();
//        build.setUrl(host);
//        build.setUsername(username);
//        build.setPassword(password);
//        // 配置最大连接池数量
//        build.setMaxActive(20);
//        // 配置初始连接池数量
//        build.setInitialSize(1);
//        // 设置最长连接时间 单位毫秒
//        build.setMaxWait(600000);
//        // 设置最小空闲连接数,设了没用
//        build.setMinIdle(1);
//        build.setValidationQuery("select 1");
//        // 申请连接的时候检测，如果空闲时间大于
//        // timeBetweenEvictionRunsMillis，
//        // 执行validationQuery检测连接是否有效。
//        build.setTestWhileIdle(true);
//        build.setTimeBetweenEvictionRunsMillis(60000);
//        // Destory线程中如果检测到当前连接的最后活跃时间和当前时间的差值大于
//        // minEvictableIdleTimeMillis，则关闭当前连接。
//        build.setMinEvictableIdleTimeMillis(300000);
//        // 和上面validationQuery配合使用，设成true会影响性能
//        build.setTestOnBorrow(false);
//        build.setTestOnReturn(false);
//        // 是否缓存preparedStatement，也就是PSCache。
//        // PSCache对支持游标的数据库性能提升巨大，比如说oracle。
//        // 在mysql5.5以下的版本中没有PSCache功能，建议关闭掉。
//        // 5.5及以上版本有PSCache，建议开启。
//        build.setPoolPreparedStatements(true);
//        // 要启用PSCache，必须配置大于0，当大于0时，
//        // poolPreparedStatements自动触发修改为true。
//        // 在Druid中，不会存在Oracle下PSCache占用内存过多的问题，
//        // 可以把这个数值配置大一些，比如说100
//        build.setMaxPoolPreparedStatementPerConnectionSize(20);
//        // 异步初始化
//        build.setAsyncInit(true);
//        return build;
//    }
//
//    /**
//     * 事务管理器
//     */
//    @Bean("customerTransactionManager")
//    PlatformTransactionManager customerTransactionManager(LocalContainerEntityManagerFactoryBean customerEntityManagerFactory) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(customerEntityManagerFactory.getObject());
//        return transactionManager;
//    }
//
//    /**
//     * 配置实体管理器工厂的Bean
//     */
//    @Bean("customerEntityManagerFactory")
//    LocalContainerEntityManagerFactoryBean customerEntityManagerFactory(EntityManagerFactoryBuilder builder,
//                                                                 HibernateProperties hibernateProperties,
//                                                                 JpaProperties jpaProperties) {
//        return builder
//                .dataSource(customerDataSource())
//                .properties(hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings()))
//                .packages("com.example.jpahibernatedemo.test.entity") //设置实体类所在位置
//                .persistenceUnit("customerPersistenceUnit")
//                .build();
//    }
//}
