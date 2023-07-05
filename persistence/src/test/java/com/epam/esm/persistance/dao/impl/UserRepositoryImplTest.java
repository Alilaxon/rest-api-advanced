package com.epam.esm.persistance.dao.impl;

import com.epam.esm.persistance.config.EmbeddedJdbcConfig;
import com.epam.esm.persistance.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {EmbeddedJdbcConfig.class})
@ActiveProfiles("integration-test")
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

        user = new User(id,name,password,email);
    }

    @Test
    void get() {
        assertNotNull(userRepository.get(1L).get());
    }

    @Test
    void getAll() {
        assertEquals(userRepository.getAll().size(),5);
    }

    @Test
    void save() {
        userRepository.save(user);
        assertEquals(userRepository.get(6L).get().getId(),6L);
    }

    @Test
    void delete() {
    }

    @Test
    void deleteByPartOfName() {

    }
}