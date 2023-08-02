package com.epam.esm.persistance.dao.impl;

import com.epam.esm.persistance.config.EmbeddedJdbcConfig;
import com.epam.esm.persistance.entity.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.sql.DataSource;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {EmbeddedJdbcConfig.class})
@ActiveProfiles("integration-test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryImplTest {

    private User user;
    @Autowired
    private DataSource dataSource;

    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl(dataSource);
        Long id = 1L;
        String name = "testUser";
        String password = "123456";
        String email = "email";
        user = new User(id, name, password, email);
    }

    @Order(1)
    @Test
    void getAll() {
        for (User u: userRepository.getAll()) {
            System.out.println(u.toString());
        }
        assertEquals(5, userRepository.getAll().size());
    }

    @Order(2)
    @Test
    void save() {
        userRepository.save(user);
        assertEquals(6L, userRepository.get(6L).get().getId());
    }

    @Test
    @Order(3)
    void get() {

        assertNotNull(userRepository.get(1L).get());
    }

    @Order(4)
    @Test
    void delete() {
        userRepository.delete(user);
        assertEquals(userRepository.get(user.getId()), Optional.empty());
    }

    @Test
    void deleteByPartOfName() {

    }
}