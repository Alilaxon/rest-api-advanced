package com.epam.esm.persistance.dao.impl.hibernate;

import com.epam.esm.persistance.dao.RoleRepository;
import com.epam.esm.persistance.entity.Role;
import com.epam.esm.persistance.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class HibernateRoleRepository implements RoleRepository {

    private static final Logger log = LogManager.getLogger(HibernateUserRepository.class);

    private final EntityManager entityManager;

    public HibernateRoleRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Role> get(long id) {

        return Optional.ofNullable(entityManager.find(Role.class,id));
    }

    @Override
    public List<Role> getAll() {
        return entityManager.createQuery("SELECT r FROM Role r").getResultList();
    }

    @Override
    public void save(Role role) {
      entityManager.persist(role);
    }

    @Override
    public void delete(Role role) {
     entityManager.remove(role);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return Optional.ofNullable(
                (Role) entityManager.createQuery("SELECT u FROM Role u WHERE u.roleName = ?1")
                        .setParameter(1, name)
                        .getSingleResult());
    }
}
