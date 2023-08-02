package com.epam.esm.persistance.dao.impl.hibernate;

import com.epam.esm.persistance.dao.UserRepository;
import com.epam.esm.persistance.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
public class HibernateUserRepository implements UserRepository {

    private static final Logger log = LogManager.getLogger(HibernateUserRepository.class);

    private final EntityManager entityManager;

    @Autowired
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
        try {
            return Optional.ofNullable(
                    (User) entityManager.createQuery("SELECT u FROM User u WHERE u.userName = ?1")
                            .setParameter(1, name)
                            .getSingleResult());

        } catch (NoResultException e){
            return Optional.empty();
        }
    }
}
