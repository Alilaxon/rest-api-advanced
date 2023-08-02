package com.epam.esm.persistance.dao;

import com.epam.esm.persistance.entity.User;

import java.util.Optional;

public interface UserRepository extends DefaultRepository<User> {


    void deleteByPartOfName(String name);

   Optional<User> findByUserName(String name);


}
