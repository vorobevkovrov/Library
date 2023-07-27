package ru.vorobev.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.Properties;
@RequiredArgsConstructor
@Configuration
@EnableWebMvc
@ComponentScan("ru.vorobev")
@PropertySource("classpath:hibernate.properties")
@EnableTransactionManagement
@EnableJpaRepositories("ru.vorobev.repositories")
public class AppConfig {
    private final ApplicationContext applicationContext;

    private final Environment env;
    //TODO new need to finish
//    private static HikariConfig config = new HikariConfig();
//    private static HikariDataSource ds;

// lombok annotation
//    @Autowired
//    public AppConfig(ApplicationContext applicationContext, Environment env) {
//        System.out.println("SpringConfig constructor");
//        this.applicationContext = applicationContext;
//        this.env = env;
//    }

    // Old
    @Bean
    public DataSource dataSource() {
        System.out.println("DataSource");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("hibernate.driver_class"));
        dataSource.setUrl(env.getRequiredProperty("hibernate.connection.url"));
        dataSource.setUsername(env.getRequiredProperty("hibernate.connection.username"));
        dataSource.setPassword(env.getRequiredProperty("hibernate.connection.password"));

        dataSource.setSchema(env.getRequiredProperty("hibernate.hbm2ddl.auto"));

        return dataSource;
    }
//    @Bean
//    public DataSource dataSource() {
//        System.out.println("HikariDataSource");
//        HikariDataSource hikariDataSource = new HikariDataSource();
//
//        hikariDataSource.setDriverClassName(env.getRequiredProperty("db.driver_class"));
//        hikariDataSource.setJdbcUrl(env.getProperty("db.connection.url"));
//        hikariDataSource.setUsername(env.getProperty("db.connection.username"));
//        hikariDataSource.setPassword(env.getProperty("db.connection.password"));
//        hikariDataSource.setMaximumPoolSize(env.getProperty("db.hikariMaxPoolSize",Integer.class));
//
//        hikariDataSource.setSchema(env.getProperty("db.hbm2ddl.auto"));
//
//        return hikariDataSource;
//    }

    private Properties hibernateProperties() {
        System.out.println("hibernateProperties");
        Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        return properties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        System.out.println("LocalContainerEntityManagerFactoryBean");
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("ru.vorobev.models");

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        Properties additionalProperties = new Properties();
        additionalProperties.put("hibernate.default_schema", "Public");
        em.setJpaProperties(additionalProperties);
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        System.out.println("PlatformTransactionManager");
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}