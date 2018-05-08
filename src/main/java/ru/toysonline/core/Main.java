package ru.toysonline.core;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.toysonline.entity.Basket;
import ru.toysonline.entity.Order;
import ru.toysonline.entity.User;

import javax.persistence.EntityManager;
import java.io.IOException;

public class Main {
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) throws IOException {
        context = new AnnotationConfigApplicationContext(ShopConfiguration.class);

        Shop shop = (Shop) context.getBean("Pets");

//        EntityManager em = (EntityManager) context.getBean("EnMan");

//        System.out.println(em);

        User user = shop.userLogin();

        boolean newUser = true;

        if (user.getBasket() == null) {
            user.setBasket(new Basket());
        } else {
            for (Order order : user.getBasket().getOrders()) {
                if (order.getStatus().equals("PAUSE")) {
                    newUser = false;
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
