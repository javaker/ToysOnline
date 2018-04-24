package ru.toysonline.core;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.toysonline.entity.Basket;
import ru.toysonline.entity.Item;
import ru.toysonline.entity.Order;
import ru.toysonline.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.List;

public class Main {
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) throws IOException {
        context = new AnnotationConfigApplicationContext(ShopConfiguration.class);

        Shop shop = context.getBean(Shop.class);
        User user = shop.userLogin();

        boolean newUser = false;

        if (user.getBasket() == null) {
            user.setBasket(new Basket());
            newUser = true;
        } else {
            for (Order order : user.getBasket().getOrders()) {
                if (order.getStatus().equals("PAUSE")) {
                    break;
                }
            }
        }

        if (newUser) {
            System.out.println("You basket is clean, let's begin buy");
            shop.beginBuy(user, true);
        } else {
            System.out.println("You not complete previous Order");
            shop.continueBuy(user);
        }
    }
}
