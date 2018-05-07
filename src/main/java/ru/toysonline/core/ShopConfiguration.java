package ru.toysonline.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
@ComponentScan(basePackages = "ru.toysonline")
public class ShopConfiguration {

    @Bean
    public EntityManager entityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("TOYSDB");
        return factory.createEntityManager();
    }
}
