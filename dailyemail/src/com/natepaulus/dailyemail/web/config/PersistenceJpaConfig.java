package com.natepaulus.dailyemail.web.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class PersistenceJpaConfig {

	private final static String driverClassName = "com.mysql.jdbc.Driver";
	private final static String url = "jdbc:mysql://localhost:3306/dailyemail";

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(this.dataSource());
		factoryBean
				.setPackagesToScan(new String[] { "com.natepaulus.dailyemail" });
		factoryBean.setPersistenceProviderClass(HibernatePersistence.class);
	/*	JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter() {
			{
				this.setDatabase(Database.MYSQL);
				this.setShowSql(true);				
			}
		};
		factoryBean.setJpaVendorAdapter(vendorAdapter);*/
		
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		jpaProperties.put("hibernate.format_sql", "true");
		jpaProperties.put("hibernate.show_sql", "true");
		factoryBean.setJpaProperties(jpaProperties);
		return factoryBean;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername("dailyemail");
		dataSource.setPassword("email");
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(this
				.entityManagerFactoryBean().getObject());

		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
		
}
