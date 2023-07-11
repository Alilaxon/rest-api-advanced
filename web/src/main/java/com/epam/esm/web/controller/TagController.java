package com.epam.esm.web.controller;


import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.exception.InvalidGiftDtoException;
import com.epam.esm.model.exception.InvalidTagDtoException;
import com.epam.esm.model.exception.NoSuchTagException;
import com.epam.esm.model.exception.TagNameIsReservedException;
import com.epam.esm.model.service.TagService;
import com.epam.esm.persistance.entity.Tag;
import com.epam.esm.web.utils.TagLinker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    private static final Logger log = LogManager.getLogger(TagController.class);
    private final TagService tagService;


    @Autowired
    public TagController(TagService tagService) {

        this.tagService = tagService;
    }

    @RequestMapping("/get-all")
    public List<TagDTO> getAll() {

        return TagLinker.addLinkToTagDTO(tagService.getAll());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tag getById(@PathVariable Long id) throws NoSuchTagException {

        return tagService.getById(id);

    }

    @PostMapping("/create")
    public ResponseEntity<Long> create(@RequestBody TagDTO tagDto)
            throws TagNameIsReservedException, InvalidTagDtoException, InvalidGiftDtoException {
        log.info("Tag '{}' will be create", tagDto.getName());
        Long id = tagService.create(tagDto).getId();

        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        log.info("Delete Tag by id = '{}'", id);
        tagService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/popularTag")
    public Tag mostPoplarTag() {

        return tagService.getTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders();
    }

    @GetMapping("/fillDataBase")
    public Integer fillDataBase() {
        tagService.fillTable();

        return tagService.getAll().size();
    }

    @GetMapping("/cleanDataBase")
    public Integer cleanDataBase() {
        tagService.cleanTable();
        return tagService.getAll().size();
    }

}
