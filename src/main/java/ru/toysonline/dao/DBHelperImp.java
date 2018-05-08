package ru.toysonline.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.toysonline.entity.Item;
import ru.toysonline.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Service("JPAImpl")
public class DBHelperImp implements DBHelper {

    private EntityManager entityManager;

    /*
    We can omit @Autowired. Spring find our bean DBHelperImpl
    and inject he here.
     */
    @Autowired
    public DBHelperImp(EntityManager getEM) {
        this.entityManager = getEM;
    }

    @Override
    public Item getItem(int id) {
        return entityManager.find(Item.class, id);
    }

    @Override
    public List getItems() {
        return entityManager.createQuery("Select i From Item i").getResultList();
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
