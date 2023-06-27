package com.epam.esm.persistance.dao;


import com.epam.esm.persistance.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {

    List<Tag> getAll();

    Tag save(Tag tag);

    Tag findByName(String name);

    Tag findById(Long id);

    Long delete(Long id);

    void deleteByPartOfName(String part);

    boolean existsByName (String name);

    Optional <Tag> GetTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders();
}
