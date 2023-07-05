package com.epam.esm.persistance.dao.impl;

import com.epam.esm.persistance.dao.UserRepository;
import com.epam.esm.persistance.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
@Repository
public class HibernateUserRepositoryImpl implements UserRepository {


   private final EntityManager entityManager;
    @Autowired
    public HibernateUserRepositoryImpl(EntityManager entityManager) {

        this.entityManager = entityManager;
    }

    @Override
    public Optional<User> get(long id) {

        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> getAll() {

        return null;
        //       entityManager.createQuery("SELECT e FROM users e").getResultList();
    }

    @Override
    public void save(User user) {
     entityManager.persist(user);
    }

    @Override
    public void delete(User user) {
      entityManager.remove(user);
    }

    @Override
    public void deleteByPartOfName(String name) {
     //TODO
    }
}
