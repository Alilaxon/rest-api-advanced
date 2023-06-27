package com.epam.esm.persistance.dao.impl;

import com.epam.esm.persistance.dao.UserRepository;
import com.epam.esm.persistance.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class HibernateUserRepositoryImpl implements UserRepository {


    @Override
    public Optional<User> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        
        return null;
    }

    @Override
    public void save(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void deleteByPartOfName(String name) {

    }
}
