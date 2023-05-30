package com.epam.esm.model.service.impl;

import com.epam.esm.persistance.dao.GiftRepository;
import com.epam.esm.persistance.dao.TagRepository;
import com.epam.esm.persistance.dao.builders.GiftBuilder;
import com.epam.esm.model.dto.GiftDto;
import com.epam.esm.persistance.entity.GiftCertificate;
import com.epam.esm.persistance.entity.Tag;
import com.epam.esm.model.exception.GiftNameIsReservedException;
import com.epam.esm.model.exception.InvalidGiftDtoException;
import com.epam.esm.model.exception.InvalidTagException;
import com.epam.esm.model.utils.GiftValidator;
import com.epam.esm.model.utils.TagValidator;
import com.epam.esm.model.service.GiftService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GiftServiceImpl implements GiftService {

    private static final Logger log = LogManager.getLogger(GiftServiceImpl.class);

    private final GiftRepository giftRepository;

    private final TagRepository tagRepository;

    @Autowired
    public GiftServiceImpl(GiftRepository giftRepository, TagRepository tagRepository) {

        this.giftRepository = giftRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public GiftCertificate create(GiftDto giftDto) throws GiftNameIsReservedException, InvalidGiftDtoException, InvalidTagException {

        GiftValidator.checkGiftDto(giftDto);

       if (checkGiftName(giftDto)) {
           throw new GiftNameIsReservedException();
       }

        List<Tag> tags = checkNewTags(tagRepository.getAll(), giftDto.getTags());

        log.info("Gift '{}' will be create",giftDto.getName());

        return giftRepository.save(GiftBuilder.builder()
                .name(giftDto.getName())
                .description(giftDto.getDescription())
                .price(giftDto.getPrice())
                .duration(giftDto.getDuration())
                .createDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE))
                .lastUpdateDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE))
                .tags(tags)
                .build());
    }

    public List<GiftCertificate> getAll() {

        return giftRepository.findAll();
    }

    @Override
    public List<GiftCertificate> getAllByTag(String tag) {

        log.info("Find by tag {}",tag);

       Long tagId = tagRepository.findByName(tag).getId();

        return giftRepository.findAllByTag(tagId);
    }

    @Override
    public List<GiftCertificate> getAllByDescription(String description) {
        return giftRepository.findAllByPartOfDescription(description);
    }

    @Override
    public GiftCertificate get(Long id) {

        return giftRepository.findById(id);
    }

    @Override
    public Long deleteById(Long id) {

        log.info("Gift id = '{}' will be delete",id);
        giftRepository.delete(id);

        return id;
    }

    @Override
    public GiftCertificate update(Long id, GiftDto giftDto) {

        log.info("Gift '{}' will be update",giftDto.getName());

        return giftRepository.update(GiftBuilder.builder()
                .id(id)
                .name(giftDto.getName())
                .description(giftDto.getDescription())
                .price(giftDto.getPrice())
                .duration(giftDto.getDuration())
                .createDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE))
                .lastUpdateDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE))
                .tags(giftDto.getTags())
                .build());
    }

    private boolean checkGiftName(GiftDto giftDto) {

        return giftRepository.existsByName(giftDto.getName());
    }

    private List<Tag> checkNewTags(List<Tag> allTags, List<Tag> newTags) throws  InvalidTagException {
        List<Tag> tagList = new ArrayList<>();
        for (Tag newTag : newTags) {
            boolean isExist = false;
            for (Tag tag : allTags) {
                if (Objects.equals(newTag.getName(), tag.getName())) {
                    tagList.add(tag);
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                TagValidator.checkTag(newTag);
                newTag.setId(tagRepository.save(newTag).getId());
                tagList.add(newTag);
            }
        }
        return tagList;
    }


}
