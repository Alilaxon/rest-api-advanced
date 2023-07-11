package com.epam.esm.model.service;


import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.exception.NoSuchTagException;
import com.epam.esm.persistance.entity.Tag;
import com.epam.esm.model.exception.InvalidGiftDtoException;
import com.epam.esm.model.exception.InvalidTagDtoException;
import com.epam.esm.model.exception.TagNameIsReservedException;

import java.util.List;

public interface TagService extends TableFiller {



    Tag create (TagDTO tagDto) throws TagNameIsReservedException, InvalidGiftDtoException, InvalidTagDtoException;

    Tag getById(Long id) throws NoSuchTagException;

    List<TagDTO> getAll();

    Long deleteById(Long id);

    Tag getTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders();






}
