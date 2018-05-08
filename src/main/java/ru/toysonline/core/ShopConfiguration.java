package ru.toysonline.core;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

//Long path because cross with Configuration in Hibernate
@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages = "ru.toysonline")
public class ShopConfiguration {

    @Bean()
    public EntityManager getEM() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("TOYSDB");
        return factory.createEntityManager();
    }

    @Bean()
    public Session getSession() {
        // Creating Configuration Instance & Passing Hibernate Configuration File
        org.hibernate.cfg.Configuration configObj = new org.hibernate.cfg.Configuration();
        configObj.configure("hibernate.cfg.xml");
        // Since Hibernate Version 4.x, Service Registry Is Being Used
        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build();

        // Creating Hibernate Session Factory Instance
        SessionFactory factoryObj = configObj.buildSessionFactory(serviceRegistryObj);

        return factoryObj.openSession();
    }
}
