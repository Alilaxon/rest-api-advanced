package com.epam.esm.persistance.dao.impl;

import com.epam.esm.persistance.config.EmbeddedJdbcConfig;
import com.epam.esm.persistance.dao.impl.hibernate.HibernateUserRepository;
import com.epam.esm.persistance.entity.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.persistence.EntityManager;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {EmbeddedJdbcConfig.class})
@ActiveProfiles("integration-test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HibernateUserRepositoryTest {

    private HibernateUserRepository userRepository;

    private User user;

    @Autowired
    private EntityManager manager;

    @BeforeEach
    void setUp() {
        userRepository = new HibernateUserRepository(manager);
        Long id = 6L;
        String name = "Hibernate";
        String password = "123456";
        String email = "email";
        user = new User(null, name, password, email);
    }

  @Test
  @Order(1)
    void getAll(){
        assertEquals(5L, userRepository.getAll().size());
    }
    @Order(2)
    @Test
    void save(){
        userRepository.save(user);
    }
    @Test
    @Order(3)
    void get() {
        user.setId(6L);
        assertEquals(userRepository.get( 6L),Optional.ofNullable(user));
    }

    @Test
    @Order(4)
    void findByUserName() {
        assertEquals(user.getUserName(),userRepository.findByUserName(user.getUserName()).get().getUserName());
    }

    @Test
    @Order(5)
    void delete(){
        User userForDelete = userRepository.get(6L).get();
        assertTrue(userRepository.get(6L).isPresent());
        userRepository.delete(userForDelete);
        assertFalse(userRepository.get(6L).isPresent());
    }

}