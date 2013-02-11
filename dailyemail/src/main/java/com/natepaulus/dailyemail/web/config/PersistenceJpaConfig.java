package com.natepaulus.dailyemail.web.config;
 
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The Class PersistenceJpaConfig is the java configuration for setting up JPA
 * persistence for the daily email web application.
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:database.properties")
public class PersistenceJpaConfig {

        @Resource
    private Environment environment;
        
        /** The database configuration parameters. */
        private final static String DATABASE_DRIVER_CLASS_NAME = "database.driver";     
        private final static String DATABASE_URL = "database.url";      
        private final static String DATABASE_USER_NAME = "database.username";   
        private final static String DATABASE_PASSWORD = "database.password";
        
        /** The hibernate configuration parameters */
        private final static String HIBERNATE_DIALECT = "hibernate.dialect";
        private final static String HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
        private final static String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
        
        /** The packages to scan for entity classes */
        private final static String PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
        
        /**
         * Entity manager factory bean.
         * 
         * @return the local container entity manager factory bean
         */
        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
                LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
                factoryBean.setDataSource(this.dataSource());
                factoryBean
                                .setPackagesToScan(environment.getRequiredProperty(PACKAGES_TO_SCAN));
                factoryBean.setPersistenceProviderClass(HibernatePersistence.class);

                Properties jpaProperties = new Properties();
                jpaProperties.put(HIBERNATE_DIALECT,
                                environment.getRequiredProperty(HIBERNATE_DIALECT));
                jpaProperties.put(HIBERNATE_FORMAT_SQL, environment.getRequiredProperty(HIBERNATE_FORMAT_SQL));
                jpaProperties.put(HIBERNATE_SHOW_SQL, environment.getRequiredProperty(HIBERNATE_SHOW_SQL));
                
                factoryBean.setJpaProperties(jpaProperties);
                return factoryBean;
        }

        /**
         * Data source that is used for connections to the database.
         * 
         * @return the data source
         */
        @Bean
        public DataSource dataSource() {
                DriverManagerDataSource dataSource = new DriverManagerDataSource();
                dataSource.setDriverClassName(environment.getRequiredProperty(DATABASE_DRIVER_CLASS_NAME));
                dataSource.setUrl(environment.getRequiredProperty(DATABASE_URL));
                dataSource.setUsername(environment.getRequiredProperty(DATABASE_USER_NAME));
                dataSource.setPassword(environment.getRequiredProperty(DATABASE_PASSWORD));
                return dataSource;
        }

        /**
         * Transaction manager.
         * 
         * @return the platform transaction manager
         */
        @Bean
        public PlatformTransactionManager transactionManager() {
                JpaTransactionManager transactionManager = new JpaTransactionManager();
                transactionManager.setEntityManagerFactory(this
                                .entityManagerFactoryBean().getObject());

                return transactionManager;
        }

        /**
         * Exception translation.
         * 
         * @return the persistence exception translation post processor
         */
        @Bean
        public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
                return new PersistenceExceptionTranslationPostProcessor();
        }

}