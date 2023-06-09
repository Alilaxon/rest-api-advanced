package com.epam.esm.model.service;

import com.epam.esm.model.dto.GiftDTO;
import com.epam.esm.persistance.entity.GiftCertificate;
import com.epam.esm.model.exception.GiftNameIsReservedException;
import com.epam.esm.model.exception.InvalidGiftDtoException;
import com.epam.esm.model.exception.InvalidTagException;

import java.util.List;

public interface GiftService {


    GiftCertificate create(GiftDTO giftDto) throws GiftNameIsReservedException, InvalidGiftDtoException, InvalidTagException;

    List<GiftCertificate> getAll();

    List<GiftCertificate> getAllByTag(String tag);

    List<GiftCertificate> getAllByDescription(String description);

    GiftCertificate get(Long id);

    Long deleteById(Long id);

    GiftCertificate update(Long id, GiftDTO giftDto);
}
