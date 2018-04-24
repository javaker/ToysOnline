package ru.toysonline.dao;


import ru.toysonline.entity.Item;

import ru.toysonline.entity.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;


public class DBHelperImp implements DBHelper {

    @Inject
    private EntityManager entityManager;


    @Override
    public Item getItem(int id) {
        return entityManager.find(Item.class, id);
    }

    @Override
    public List<Item> getItems() {
        Query result = entityManager.createQuery("Select i From Item i");

        return result.getResultList();
    }

    @Override
    public User getUser(String name) {

        try {
            Object result = entityManager.createQuery("select u from User u where u.name = :name")
                    .setParameter("name", name)
                    .getSingleResult();

            return (User) result;
        } catch (NoResultException e) {
            return new User(name);
        }
    }

    @Override
    public void saveUser(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

}
