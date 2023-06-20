package com.epam.esm.model.service.impl;

import com.epam.esm.persistance.dao.TagRepository;
import com.epam.esm.persistance.dao.builders.TagBuilder;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.persistance.entity.Tag;
import com.epam.esm.model.exception.InvalidGiftDtoException;
import com.epam.esm.model.exception.InvalidTagDtoException;
import com.epam.esm.model.exception.TagNameIsReservedException;
import com.epam.esm.model.utils.TagValidator;
import com.epam.esm.model.service.TagService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private static final Logger log = LogManager.getLogger(TagServiceImpl.class);

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {

        this.tagRepository = tagRepository;
    }

    @Override
    public Tag create(TagDTO tagDto) throws TagNameIsReservedException, InvalidGiftDtoException, InvalidTagDtoException {

        TagValidator.checkTagDto(tagDto);

        if (checkTagName(tagDto)) {
            throw new TagNameIsReservedException();
        }

        log.info("Tag '{}' will be create", tagDto.getName());

        return tagRepository.save(TagBuilder.builder().name(tagDto.getName()).build());
    }

    @Override
    public Tag getById(Long id) {

        return tagRepository.findById(id);
    }

    @Override
    public List<Tag> getAll() {


        return tagRepository.getAll();
    }


    @Override
    public Long deleteById(Long id) {
        log.info("Tag id= '{}' will be create", id);

        return tagRepository.Delete(id);
    }

    @Override
    public Tag GetTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders() {
        return tagRepository.GetTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders().get();
    }

    private boolean checkTagName(TagDTO tagDto) {

        return tagRepository.existsByName(tagDto.getName());
    }
}
