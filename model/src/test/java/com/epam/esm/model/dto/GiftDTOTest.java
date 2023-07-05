package com.epam.esm.model.dto;

import com.epam.esm.persistance.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GiftDTOTest {

    private final Long ID = 1L;
    private final String NAME = "Gift";
    private final String DESCRIPTION = "description";
    private final Long PRICE = 1000L;
    private final Long DURATION = 7L;
    private final Tag TAG = new Tag(1L, "red");
    private GiftDTO GIFT_DTO;

    @BeforeEach
    void setUp() {
        GIFT_DTO = new GiftDTO();
    }

    @Test
    void giftDtoSettersAndGettersTest() {
        GIFT_DTO.setId(ID);
        GIFT_DTO.setName(NAME);
        GIFT_DTO.setDescription(DESCRIPTION);
        GIFT_DTO.setPrice(PRICE);
        GIFT_DTO.setDuration(DURATION);
        GIFT_DTO.setTags(List.of(TAG));
        assertEquals(GIFT_DTO.getId(), ID);
        assertEquals(GIFT_DTO.getName(), NAME);
        assertEquals(GIFT_DTO.getDescription(), DESCRIPTION);
        assertEquals(GIFT_DTO.getPrice(), PRICE);
        assertEquals(GIFT_DTO.getDuration(), DURATION);
        assertEquals(GIFT_DTO.getTags(), List.of(TAG));
    }
}