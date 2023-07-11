package com.epam.esm.model.service.impl;

import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.exception.*;
import com.epam.esm.model.service.TagService;
import com.epam.esm.model.utils.TagValidator;
import com.epam.esm.persistance.dao.TagRepository;
import com.epam.esm.persistance.dao.builders.TagBuilder;
import com.epam.esm.persistance.entity.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private static final Logger log = LogManager.getLogger(TagServiceImpl.class);

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {

        this.tagRepository = tagRepository;
    }

    @Override
    public Tag create(TagDTO tagDto)
            throws TagNameIsReservedException,
            InvalidGiftDtoException,
            InvalidTagDtoException {
        TagValidator.checkTagDto(tagDto);
        if (checkTagName(tagDto)) {
            throw new TagNameIsReservedException(tagDto.getName());
        }
        log.info("Tag '{}' will be create", tagDto.getName());
        return tagRepository.save(TagBuilder.builder().name(tagDto.getName()).build());
    }
    @Override
    public Tag getById(Long id) throws NoSuchTagException {
        return tagRepository.findById(id).orElseThrow(() -> new NoSuchTagException(id));
    }
    @Override
    public List<TagDTO> getAll() {

        return tagRepository
                .getAll().stream()
                .map(tag -> new TagDTO(
                        tag.getId(),
                        tag.getName()))
                .collect(Collectors.toList());
    }


    @Override
    public Long deleteById(Long id) {
        log.info("Tag id= '{}' will be create", id);

        return tagRepository.delete(id);
    }

    @Override
    public Tag getTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders() {
        return tagRepository.getTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders().get();
    }

    private boolean checkTagName(TagDTO tagDto) {

        return tagRepository.existsByName(tagDto.getName());
    }
    @Override
    public void fillTable() {
        for (int i = 0; i < 1000; i++) {
            String name = "TestTag" + i;
            tagRepository.save(TagBuilder.builder().name(name).build());
        }
    }
    @Override
    public void cleanTable(){
        tagRepository.deleteByPartOfName("estTag");
    }
}
