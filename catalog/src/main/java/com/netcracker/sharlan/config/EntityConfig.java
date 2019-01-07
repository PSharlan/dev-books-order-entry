package com.netcracker.sharlan.config;

import com.netcracker.sharlan.entities.Category;
import com.netcracker.sharlan.entities.Offer;
import com.netcracker.sharlan.entities.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(basePackages = "com.netcracker.sharlan.entities")
public class EntityConfig {

    @Bean
    @Scope("prototype")
    public Offer offer(){
        return new Offer();
    }

    @Bean
    @Scope("prototype")
    public Category category(){
        return new Category();
    }

    @Bean
    @Scope("prototype")
    public Tag tag(){
        return new Tag();
    }

}
