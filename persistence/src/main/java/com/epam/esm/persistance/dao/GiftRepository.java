package com.epam.esm.persistance.dao;


import com.epam.esm.persistance.entity.GiftCertificate;

import java.util.List;

public interface GiftRepository {

    GiftCertificate save (GiftCertificate giftCertificate);

    GiftCertificate findById(Long id);

    GiftCertificate findByName(String name);

    boolean existsByName (String name);

    List<GiftCertificate> findAll();

    List<GiftCertificate> findAllByTag(Long id);

    List<GiftCertificate> findAllByPartOfDescription(String part);

    GiftCertificate update (GiftCertificate giftCertificate);

    void delete(Long id);


}
