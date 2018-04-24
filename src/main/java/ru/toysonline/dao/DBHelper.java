package ru.toysonline.dao;

import ru.toysonline.entity.Category;
import ru.toysonline.entity.Item;
import ru.toysonline.entity.Order;
import ru.toysonline.entity.User;

import java.util.List;

public interface DBHelper {

    Item getItem(int id);

    List<Item> getItems();

    User getUser(String name);

    void saveUser(User user);
}
