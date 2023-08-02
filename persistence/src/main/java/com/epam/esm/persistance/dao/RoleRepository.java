package com.epam.esm.persistance.dao;

import com.epam.esm.persistance.entity.Role;

import java.util.Optional;

public interface RoleRepository extends DefaultRepository<Role> {

    Optional<Role> findByName(String name);
}
