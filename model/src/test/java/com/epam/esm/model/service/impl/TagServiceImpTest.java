package com.epam.esm.model.service.impl;

import com.epam.esm.model.exception.NoSuchTagException;
import com.epam.esm.model.service.impl.TagServiceImpl;
import com.epam.esm.persistance.dao.TagRepository;
import com.epam.esm.persistance.dao.builders.TagBuilder;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.persistance.entity.Tag;
import com.epam.esm.model.exception.InvalidGiftDtoException;
import com.epam.esm.model.exception.InvalidTagDtoException;
import com.epam.esm.model.exception.TagNameIsReservedException;
import com.epam.esm.model.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TagServiceImpTest {

    private final TagRepository tagRepository = mock(TagRepository.class);

    private final TagService tagService = new TagServiceImpl(tagRepository);

    private final Long ID = 1L;

    private final String NAME = "Tag";
    private final String INVALID_NAME = "";

    private Tag TAG;

    private TagDTO TAG_DTO;

    @BeforeEach
   void setUp(){

        TAG_DTO = new TagDTO(NAME);

        TAG = TagBuilder.builder().id(ID).name(NAME).build();

    }

    @Test
    void create() throws TagNameIsReservedException, InvalidTagDtoException, InvalidGiftDtoException {
        when(tagRepository.save(TAG)).thenReturn(TAG);
        assertEquals(tagService.create(TAG_DTO),TAG);

    }

    @Test
    void CreateThrowsTagNameIsReservedException() {
        TAG_DTO.setName(INVALID_NAME);
        assertThrows(InvalidTagDtoException.class,()-> tagService.create(TAG_DTO));
    }

    @Test
    void getById() throws NoSuchTagException {
        when(tagRepository.findById(ID)).thenReturn(Optional.of(TAG));
        assertEquals(tagService.getById(ID),TAG);
    }

    @Test
    void getAll() {
        when(tagRepository.getAll()).thenReturn(List.of(TAG));
        assertEquals(tagService.getAll(),List.of(TAG_DTO));
    }

    @Test
    void deleteById() {
        when(tagRepository.delete(ID)).thenReturn(ID);
        tagService.deleteById(ID);
        verify(tagRepository,times(1)).delete(ID);
    }
}