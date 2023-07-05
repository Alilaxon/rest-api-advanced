package com.epam.esm.persistance.dao.impl;

import com.epam.esm.persistance.config.EmbeddedJdbcConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {EmbeddedJdbcConfig.class})
@ActiveProfiles("integration-test")
class HibernateUserRepositoryImplTest {

    private HibernateUserRepositoryImpl userRepository;

    @Autowired
    private EntityManager manager;
    @BeforeEach
    void setUp() {
        userRepository = new HibernateUserRepositoryImpl(manager);
    }

    @Test
    void get() {
        assertNotNull(userRepository.get(1L));
    }
}