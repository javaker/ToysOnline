package ru.toysonline.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.toysonline.dao.DBHelper;
import ru.toysonline.dao.DBHelperImp;
import ru.toysonline.entity.Basket;
import ru.toysonline.entity.Category;
import ru.toysonline.entity.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
public class ShopConfiguration {

    @Bean
    public Shop shop() {
        return new Shop();
    }

    @Bean
    public DBHelper dbHelper() {
        return new DBHelperImp();
    }

    @Bean
    public Basket basket() {
        return new Basket();
    }

    @Bean
    public Order order() {
        return new Order();
    }

    @Bean
    public Category category() {
        return new Category();
    }

    @Bean
    public EntityManager entityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("TOYSDB");
        return factory.createEntityManager();
    }
}
