package com.epam.esm.model.service;


import com.epam.esm.model.dto.TagDto;
import com.epam.esm.persistance.entity.Tag;
import com.epam.esm.model.exception.InvalidGiftDtoException;
import com.epam.esm.model.exception.InvalidTagDtoException;
import com.epam.esm.model.exception.TagNameIsReservedException;

import java.util.List;

public interface TagService {



    Tag create (TagDto tagDto) throws TagNameIsReservedException, InvalidGiftDtoException, InvalidTagDtoException;

    Tag getById(Long id);

    List<Tag> getAll();

    Long deleteById(Long id);




}
