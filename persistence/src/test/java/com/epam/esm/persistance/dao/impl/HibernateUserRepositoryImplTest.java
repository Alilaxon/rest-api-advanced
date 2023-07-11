package com.epam.esm.persistance.dao.impl;

import com.epam.esm.persistance.config.EmbeddedJdbcConfig;
import com.epam.esm.persistance.entity.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {EmbeddedJdbcConfig.class})
@ActiveProfiles("integration-test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HibernateUserRepositoryImplTest {

    private HibernateUserRepositoryImpl userRepository;

    private User user;

    @Autowired
    private EntityManager manager;

    @BeforeEach
    void setUp() {
        userRepository = new HibernateUserRepositoryImpl(manager);
        Long id = 6L;
        String name = "testUser";
        String password = "123456";
        String email = "email";
        user = new User(null, name, password, email);
    }

  @Test
  @Order(1)
    void getAll(){
        assertEquals(userRepository.getAll().size(),5L);
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
    void delete(){
        User userForDelete = userRepository.get(6L).get();
        assertEquals(userRepository.get(6L).get().getUserName(),user.getUserName());
        userRepository.delete(userForDelete);
        assertEquals(userRepository.get(6L), Optional.empty());
    }
}