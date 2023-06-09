package com.epam.esm.dto;


import com.epam.esm.model.dto.TagDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TagDtoTest {

    TagDTO tagDto;

    Long ID = 1L;

    String NAME = "red";
    @BeforeEach
    void setUp(){
        tagDto = new TagDTO();
    }
    @Test
    void tagDtoSettersAndGettersTest(){
        tagDto.setId(ID);
        tagDto.setName(NAME);
        assertEquals(tagDto.getId(),ID);
        assertEquals(tagDto.getName(),NAME);

    }

}