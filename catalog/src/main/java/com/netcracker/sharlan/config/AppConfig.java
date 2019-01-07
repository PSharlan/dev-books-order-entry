package com.netcracker.sharlan.config;

import com.netcracker.sharlan.dao.*;
import com.netcracker.sharlan.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScans(value = {@ComponentScan("com.netcracker.sharlan.dao"),
        @ComponentScan("com.netcracker.sharlan.service")})
public class AppConfig {

    @Bean
    public LocalEntityManagerFactoryBean entityManagerFactory() {
        LocalEntityManagerFactoryBean emf = new LocalEntityManagerFactoryBean();
        emf.setPersistenceUnitName("catalogPersistenceUnit");
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactory().getObject());
        return tm;
    }

    @Bean
    public CategoryDao categoryDaoImpl(){
        return new CategoryDaoImpl();
    }

    @Bean
    public OfferDao offerDaoImpl(){
        return new OfferDaoImpl();
    }

    @Bean
    public TagDao tagDaoImpl(){
        return new TagDaoImpl();
    }

    @Bean
    public OfferService offerServiceImpl (OfferDao offerDao){
        return new OfferServiceImpl();
    }

    @Bean
    public TagService tagServiceImpl (CategoryDao categoryDao){
        return new TagServiceImpl();
    }

    @Bean
    public CategoryService categoryServiceImpl (CategoryDao categoryDao){
        return new CategoryServiceImpl();
    }

}
