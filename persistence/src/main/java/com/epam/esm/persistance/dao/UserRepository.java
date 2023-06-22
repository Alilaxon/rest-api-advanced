package com.epam.esm.persistance.dao;

import com.epam.esm.persistance.entity.Tag;
import com.epam.esm.persistance.entity.User;

public interface UserRepository extends DefaultRepository<User> {


    void DeleteByPartOfName(String name);


}
