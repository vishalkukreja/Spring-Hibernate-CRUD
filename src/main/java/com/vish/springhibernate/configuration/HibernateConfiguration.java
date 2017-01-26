package com.vish.springhibernate.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({"com.vish.springhibernate.configuration"})
@EnableTransactionManagement
@PropertySource({"classpath:application.properties"})
public class HibernateConfiguration {
	
	@Autowired
	Environment env;
	
	@Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] {"com.vish.springhibernate..model"});
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
     }
	
	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(env.getRequiredProperty("jdbc.dbusername"));
        dataSource.setPassword(env.getRequiredProperty("jdbc.dbpassword"));
        return dataSource;
	}
	
	private Properties hibernateProperties(){
		Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
        return properties; 
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sf) {
		HibernateTransactionManager txn = new HibernateTransactionManager();
		txn.setSessionFactory(sf);
		return txn;
	}
}
