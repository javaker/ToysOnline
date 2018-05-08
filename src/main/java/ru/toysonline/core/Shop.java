package ru.toysonline.core;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.toysonline.Pay;
import ru.toysonline.dao.DBHelper;
import ru.toysonline.entity.Item;
import ru.toysonline.entity.Order;
import ru.toysonline.entity.OrderItem;
import ru.toysonline.entity.User;

import javax.inject.Named;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("Pets")
public class Shop {

    /*
    We can omit @Autowired. Spring find our bean DBHelperImpl
    and inject he here.
     */
    @Autowired
    public Shop(@Qualifier("HibernateImpl") DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    private DBHelper dbHelper;

    private BufferedReader reader;


    public List getAllItems() {
        return dbHelper.getItems();
    }

    public void printItems(List<Item> items) {
        System.out.println("Toys in our shop");
        items.forEach(i -> {
            System.out.println();
            System.out.println("ID: " + i.getId());
            System.out.println("Name: " + i.getName());
            System.out.println("Price: " + i.getPrice());
            System.out.println("Description: " + i.getDescription());
            System.out.println("Category: " + i.getCategory_id().getName());
        });
    }

    public Order createOrder(Date date, String address, String pay, List<OrderItem> orderItem, User user, String status) {
        Order order = new Order();
        order.setDate(date);
        order.setAddress(address);
        order.setPay(pay);
        order.setOrderItems(orderItem);
        order.setBasket_id(user.getBasket());
        order.setStatus(status);
        order.setCost(order.getCost());
        return order;
    }


    public OrderItem createOrderItem(Item item, int quantity) {
        return new OrderItem(item, quantity);
    }

    public User userLogin() throws IOException {

        System.out.println("Welcome in Toys Shop");
        System.out.println("If you come first we create new session or load exist");
        System.out.println("Input your name");

        reader = new BufferedReader(new InputStreamReader(System.in));

        return dbHelper.getUser(reader.readLine());
    }

    public void beginBuy(User user, boolean begin) throws IOException {

        boolean finish = false;
        String status = null;
        String address = null;
        String pay = null;
        List<OrderItem> orderItems = null;

        if (begin) {

            System.out.println("We need some information for Destination and Pay");
            System.out.println("Input destination address");
            reader = new BufferedReader(new InputStreamReader(System.in));
            address = reader.readLine();

            System.out.println("Input type pay");
            System.out.println("1 - CARD");
            System.out.println("2 = CASH");

            reader = new BufferedReader(new InputStreamReader(System.in));

            switch (Integer.valueOf(reader.readLine())) {
                case 1:
                    pay = Pay.CARD.toString();
                    break;
                case 2:
                    pay = Pay.CASH.toString();
                    break;
                default:
                    System.out.println("Select validate system");
            }

            orderItems = new ArrayList<>();
            status = "PAUSE";

        } else {
            orderItems = (user.getBasket().getOrders().get(0).getOrderItems());
        }

        System.out.println("Begin buy");
        System.out.println("------------------------------->>>>");

        while (true) {

            printItems(getAllItems());
            System.out.println();
            System.out.println("Select items id");
            reader = new BufferedReader(new InputStreamReader(System.in));
            Item item = dbHelper.getItem(Integer.valueOf(reader.readLine()));

            System.out.println("input quantity");
            reader = new BufferedReader(new InputStreamReader(System.in));
            int quantity = Integer.valueOf(reader.readLine());

            orderItems.add(createOrderItem(item, quantity));

            System.out.println("Order add in Basket, continue (y/n), or type \"exit\" for come back later");

            reader = new BufferedReader(new InputStreamReader(System.in));
            String answer = reader.readLine();

            if (answer.equals("n")) {
                finish = true;
                break;
            } else if (answer.equals("exit")) {
                System.out.println("You make come back later as you wish");
                finish = false;
                break;
            }
        }
        if (finish) {
            status = "COMPLETE";
        }

        completeOrder(orderItems, user, status, address, pay);
    }

    public void completeOrder(List<OrderItem> orderItems, User user, String status, String address, String pay) throws IOException {

        List<Order> orders = new ArrayList<>();

        if (user.getBasket().getOrders() != null) {
            user.getBasket().getOrders().get(0).setStatus(status);
            user.getBasket().getOrders().get(0).setOrderItems(orderItems);
            orders.add(user.getBasket().getOrders().get(0));
        } else {
            Order order = createOrder(new Date(), address, pay, orderItems, user, status);
            orders.add(order);
            user.getBasket().setOrders(orders);
        }

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder_id(orders.get(0));
        }

        saveAndGoobye(user, user.getBasket().getOrders().get(0));
    }

    public void printOrderItem(User user) {
        for (Order order : user.getBasket().getOrders()) {
            for (OrderItem orderItem : order.getOrderItems()) {
                System.out.println("-------------------------------");
                System.out.println("Name: " + orderItem.getItem().getName());
                System.out.println("Article: " + orderItem.getItem().getArticle());
                System.out.println("Price; " + orderItem.getItem().getPrice());
                System.out.println("Quantity: " + orderItem.getQuantity());
            }
        }
    }

    public void continueBuy(User user) throws IOException {
        System.out.println("Let's continue buy");
        System.out.println("You basket content: ");

        printOrderItem(user);

        System.out.println("Complete order or continue buy? (y/n)");

        reader = new BufferedReader(new InputStreamReader(System.in));
        String answer = reader.readLine();

        if (answer.equals("y")) {
            for (Order order : user.getBasket().getOrders()) {
                order.setStatus("COMPLETE");
            }
            saveAndGoobye(user, user.getBasket().getOrders().get(0));

        } else if (answer.equals("n")) {
            beginBuy(user, false);
        }
    }

    public void saveAndGoobye(User user, Order order) {
        dbHelper.saveUser(user);

        System.out.println("You want buy: ");

        printOrderItem(user);

        System.out.println("<<<<<---------------------------------->>>>>");
        System.out.println("Amount price: " + order.getCost());
        System.out.println("Good Buy, come back)");
        System.exit(0);
    }

}

