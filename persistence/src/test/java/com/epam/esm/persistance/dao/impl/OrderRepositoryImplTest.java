package com.epam.esm.persistance.dao.impl;

import com.epam.esm.persistance.config.EmbeddedJdbcConfig;
import com.epam.esm.persistance.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {EmbeddedJdbcConfig.class})
@ActiveProfiles("integration-test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderRepositoryImplTest {

    private Order order;
    @Autowired
    private DataSource dataSource;

   private OrderRepositoryImpl orderRepository;
    @BeforeEach
    void setUp() {
        orderRepository = new OrderRepositoryImpl(dataSource);

        order = new Order(2L,2L,2L,1000L,"2023-05-25");
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void getAll() {
        assertEquals(1, orderRepository.getAll().size());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void get() {
        assertEquals(1L, orderRepository.get(1L).get().getId());
    }



    @Test
    @org.junit.jupiter.api.Order(3)
    void save() {
        orderRepository.save(order);
        assertEquals(orderRepository.get(2L).get().getGiftId(),order.getGiftId());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void delete() {
        orderRepository.delete(order);
        assertEquals(orderRepository.get(2L), Optional.empty());
    }
}