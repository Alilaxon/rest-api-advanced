package com.epam.esm.model.service.impl;

import com.epam.esm.model.exception.*;

import com.epam.esm.persistance.dao.GiftRepository;
import com.epam.esm.persistance.dao.TagRepository;
import com.epam.esm.persistance.dao.builders.GiftBuilder;
import com.epam.esm.model.dto.GiftDTO;
import com.epam.esm.persistance.entity.GiftCertificate;
import com.epam.esm.persistance.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class GiftCertificateServiceTest {

    private final GiftRepository giftRepository = mock(GiftRepository.class);

    private final TagRepository tagRepository = mock(TagRepository.class);

    private final DateTimeFormatter FORMATTER =new DateTimeFormatterBuilder()
                .appendPattern("M-d-yyyy[ h:mm:ss]")
                .toFormatter();;

    private final GiftServiceImpl giftCertificateService
            = new GiftServiceImpl(giftRepository,tagRepository,FORMATTER);

    private  GiftCertificate GIFT;

    private GiftDTO GIFT_DTO;

    private final Long ID = 1L;

    private final String NAME = "Gift";
    private final String INVALID_NAME = "";

    private final String DESCRIPTION = "description";

    private final Long PRICE = 1000L;

    private final Long INVALID_PRICE = -1000l;

    private final Long DURATION = 7L;

    private final Long INVALID_DURATION = -7L;

    private final Tag TAG_ONE = new Tag(1L, "red");

    private final Tag TAG_TWO = new Tag(2L,"blue");

    @BeforeEach
    void setUp(){

        GIFT = GiftBuilder.builder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .price(PRICE)
                .duration(DURATION)
                .createDate(String.valueOf(LocalDateTime.now()))
                .lastUpdateDate(String.valueOf(LocalDateTime.now()))
                .tags(List.of(TAG_ONE,TAG_TWO))
                .build();

        GIFT_DTO = new GiftDTO(NAME,DESCRIPTION,PRICE,DURATION,List.of(TAG_ONE));

    }



    @Test
    void create() throws GiftNameIsReservedException, InvalidGiftDtoException, InvalidTagException {
        when(giftRepository.existsByName(GIFT_DTO.getName())).thenReturn(false);
        when(tagRepository.save(TAG_ONE)).thenReturn(TAG_ONE);
        when(giftRepository.save(GIFT)).thenReturn(GIFT);
        assertEquals(giftCertificateService.create(GIFT_DTO),GIFT);

    }

    @Test
    void createThrowGiftNameIsReservedException() {
        when(giftRepository.existsByName(GIFT_DTO.getName())).thenReturn(true);
        assertThrows(GiftNameIsReservedException.class, ()-> giftCertificateService.create(GIFT_DTO));
    }

    @Test
    void createWithInvalidNameThrowInvalidGiftDtoException() {
        when(giftRepository.existsByName(GIFT_DTO.getName())).thenReturn(false);
        GIFT_DTO.setName(INVALID_NAME);
        assertThrows(InvalidGiftDtoException.class, ()-> giftCertificateService.create(GIFT_DTO));
    }

    @Test
    void createWithInvalidPriceThrowInvalidGiftDtoException() {
        when(giftRepository.existsByName(GIFT_DTO.getName())).thenReturn(false);
        GIFT_DTO.setPrice(INVALID_PRICE);
        assertThrows(InvalidGiftDtoException.class, ()-> giftCertificateService.create(GIFT_DTO));
    }

    @Test
    void createWithInvalidDurationThrowInvalidGiftDtoExceptionName() {
        when(giftRepository.existsByName(GIFT_DTO.getName())).thenReturn(false);
        GIFT_DTO.setDuration(INVALID_DURATION);
        assertThrows(InvalidGiftDtoException.class, ()-> giftCertificateService.create(GIFT_DTO));
    }


    @Test
    void getAll() {
        when(giftRepository.findAll()).thenReturn(List.of(GIFT));
        assertEquals(giftCertificateService.getAll(),List.of(GIFT_DTO));
    }

    @Test
    void getAllByTag() throws NoSuchTagNameException {
        when(tagRepository.findByName(TAG_ONE.getName())).thenReturn(Optional.of(TAG_ONE));
       // when(giftRepository.findAllByTag(TAG_ONE.getId(),1L,50L)).thenReturn(List.of(GIFT));
        List<GiftCertificate> giftCertificateList = giftCertificateService.getAllByTag(TAG_ONE.getName(),1L,1L);
        System.out.println(giftCertificateList.toString());
      //  assertEquals(List.of(GIFT),giftCertificateService.getAllByTag(TAG_ONE.getName(),1L,50L));
    }

    @Test
    void get() throws NoSuchGiftException {
        when(giftRepository.findById(ID)).thenReturn(Optional.ofNullable(GIFT));
        assertEquals(giftCertificateService.get(ID),GIFT_DTO);
    }

    @Test
    void deleteById() {
        giftCertificateService.deleteById(ID);
        verify(giftRepository,times(1)).delete(ID);
    }

    @Test
    void update() {
        when(giftRepository.update(GIFT)).thenReturn(GIFT);
        assertEquals(giftCertificateService.update(ID,GIFT_DTO),GIFT);

    }
}