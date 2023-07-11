package com.epam.esm.model.service;

import com.epam.esm.model.dto.GiftDTO;
import com.epam.esm.model.exception.*;
import com.epam.esm.persistance.entity.GiftCertificate;

import java.util.List;

public interface GiftService {


    GiftCertificate create(GiftDTO giftDto) throws GiftNameIsReservedException, InvalidGiftDtoException, InvalidTagException;

    List<GiftDTO> getAll();

    List<GiftCertificate> getAllByTag(String tag ,Long page ,Long size) throws NoSuchTagNameException;

    List<GiftCertificate> getAllByDescription(String description);

    GiftDTO get(Long id) throws NoSuchGiftException;

    Long deleteById(Long id);

    GiftCertificate update(Long id, GiftDTO giftDto);
}
