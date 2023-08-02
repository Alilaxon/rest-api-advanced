package com.epam.esm.persistance.dao.builders;

import com.epam.esm.persistance.entity.Role;
import com.epam.esm.persistance.entity.User;

import java.util.Collection;
import java.util.List;

public class UserBuilder {

    private Long id;

    private String userName;

    private String email;

    private String password;

    private Collection<Role> roles;

    public static UserBuilder builder() {

        return new UserBuilder();
    }

    public UserBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder userName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder roles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public User build (){
        return new User(id,userName,email,password,roles);
    }
}
