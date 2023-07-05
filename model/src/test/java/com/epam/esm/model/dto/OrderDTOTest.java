package com.epam.esm.model.dto;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class OrderDTOTest {
    private OrderDTO ORDER_DTO;

    @BeforeEach
    void setUp() {
        ORDER_DTO = new OrderDTO();
    }

    @Test
    void OrderDTOSettersAndGettersTest() {
        Long ID = 1L;
        Long GIFT_ID = 2L;
        Long USER_ID = 3L;
        Long PRICE = 1000L;
        String TIMESTAMP = "6-25-2023 8:56:58";
        ORDER_DTO.setId(ID);
        ORDER_DTO.setGiftId(GIFT_ID);
        ORDER_DTO.setUserId(USER_ID);
        ORDER_DTO.setPrice(PRICE);
        ORDER_DTO.setTimestamp(TIMESTAMP);
        assertEquals(ORDER_DTO.getId(), ID);
        assertEquals(ORDER_DTO.getGiftId(), GIFT_ID);
        assertEquals(ORDER_DTO.getUserId(), USER_ID);
        assertEquals(ORDER_DTO.getPrice(), PRICE);
        assertEquals(ORDER_DTO.getTimestamp(), TIMESTAMP);
    }
}