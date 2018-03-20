package com.teamtreehouse.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
// @EnableJpaRepositories için pom.xml'e spring-data-jpa ekle.
@EnableJpaRepositories(basePackages="com.teamtreehouse.dao")
@PropertySource("application.properties")
public class DataConfig {
	
	@Autowired
	private Environment env;
	
	/*
	dao-service pattern'de, dao hibernate ile database bağlanır. save vb. metodları elle kendin yazarsın. repository 
	olarak implement edilmiş concrete class işaretlenir.localsessionfactorybean bean olarak oluşturulur.

	fakat spring data jpa kullanırsan dao interface repository olarak işaretlenir(interface'i implement eden class değil) 
	ve bu interface crudrepository'yi extend eder. save vb. için metotlar otomatik olarak gelir.(smart method naming) 
	Config class'ta localsessionfactorybean yerine localcontainerentitymanagerfactorybean oluştulur ve bu class
	@EnableJpaRepositories ile annotate edilir.
	*/
		
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
		LocalContainerEntityManagerFactoryBean factory=new LocalContainerEntityManagerFactoryBean();
		HibernateJpaVendorAdapter vendorAdapter=new HibernateJpaVendorAdapter();
		
		factory.setDataSource(dataSource());
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan(env.getProperty("todotoday"));
		factory.setJpaProperties(getHibernateProperties());
		
		return factory;
	}
	
	private Properties getHibernateProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Bean
	public DataSource dataSource() {
		//BasicDataSource için pom.xml'e apache commons-dbcp ekle.
		BasicDataSource ds=new BasicDataSource();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
