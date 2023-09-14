package com.epam.esm.persistance.dao.impl.hibernate;

import com.epam.esm.persistance.dao.UserRepository;
import com.epam.esm.persistance.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class HibernateUserRepository implements UserRepository {

    private static final Logger log = LogManager.getLogger(HibernateUserRepository.class);

    private final EntityManager entityManager;


    public HibernateUserRepository(EntityManager entityManager) {

        this.entityManager = entityManager;
    }

    @Override
    public Optional<User> get(long id) {

        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return entityManager.createQuery("SELECT u FROM User u").getResultList();
    }

    @Override
    public void save(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(User user) {
        entityManager.remove(user);
    }

    @Override
    public void deleteByPartOfName(String name) {
        String partOfName = "%" + name + "%";
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("DELETE FROM users WHERE user_name LIKE ?")
                .setParameter(1, partOfName)
                .executeUpdate();
        entityManager.getTransaction().commit();

    }

    @Override
    public Optional<User> findByUserName(String name) {
        List<User> userList = entityManager.createQuery("SELECT u FROM User u WHERE u.userName = ?1")
                .setParameter(1, name)
                .getResultList();
        if (userList.size() == 0) return Optional.empty();
         log.info(userList.get(0).toString());
        return Optional.ofNullable(userList.get(0));
    }
}

