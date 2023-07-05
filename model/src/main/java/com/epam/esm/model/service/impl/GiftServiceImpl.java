package com.epam.esm.model.service.impl;

import com.epam.esm.model.dto.GiftDTO;
import com.epam.esm.model.exception.GiftNameIsReservedException;
import com.epam.esm.model.exception.InvalidGiftDtoException;
import com.epam.esm.model.exception.InvalidTagException;
import com.epam.esm.model.exception.NoSuchGiftException;
import com.epam.esm.model.service.GiftService;
import com.epam.esm.model.utils.GiftValidator;
import com.epam.esm.model.utils.TagValidator;
import com.epam.esm.persistance.dao.GiftRepository;
import com.epam.esm.persistance.dao.TagRepository;
import com.epam.esm.persistance.dao.builders.GiftBuilder;
import com.epam.esm.persistance.entity.GiftCertificate;
import com.epam.esm.persistance.entity.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GiftServiceImpl implements GiftService {

    private static final Logger log = LogManager.getLogger(GiftServiceImpl.class);

    private final GiftRepository giftRepository;

    private final TagRepository tagRepository;

    private final DateTimeFormatter dateTimeFormatter;


    @Autowired
    public GiftServiceImpl(GiftRepository giftRepository,
                           TagRepository tagRepository,
                           DateTimeFormatter dateTimeFormatter) {
        this.giftRepository = giftRepository;
        this.tagRepository = tagRepository;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public GiftCertificate create(GiftDTO giftDto)
            throws GiftNameIsReservedException, InvalidGiftDtoException, InvalidTagException {
        GiftValidator.checkGiftDto(giftDto);
        if (checkGiftName(giftDto)) {
            throw new GiftNameIsReservedException(giftDto.getName());
        }
        List<Tag> tags = checkNewTags(tagRepository.getAll(), giftDto.getTags());
        log.info("Gift '{}' will be create", giftDto.getName());

        return giftRepository.save(GiftBuilder.builder()
                .name(giftDto.getName())
                .description(giftDto.getDescription())
                .price(giftDto.getPrice())
                .duration(giftDto.getDuration())
                .createDate(LocalDateTime.now().format(dateTimeFormatter))
                .lastUpdateDate(LocalDateTime.now().format(dateTimeFormatter))
                .tags(tags)
                .build());
    }

    public List<GiftDTO> getAll() {

        return giftRepository.findAll()
                .stream()
                .map(giftCertificate -> new GiftDTO(
                        giftCertificate.getId(),
                        giftCertificate.getName(),
                        giftCertificate.getDescription(),
                        giftCertificate.getPrice(),
                        giftCertificate.getDuration(),
                        giftCertificate.getTags()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificate> getAllByTag(String tag,Long page) {
        log.info("Find by tag {}", tag);
        Long tagId = tagRepository.findByName(tag).getId();

        Integer limit = 50;
        Long offset = limit*(page -1);
        return giftRepository.findAllByTag(tagId,offset);
    }

    @Override
    public List<GiftCertificate> getAllByDescription(String description) {

        return giftRepository.findAllByPartOfDescription(description);
    }

    @Override
    public GiftDTO get(Long id) throws NoSuchGiftException {
        var gift = giftRepository.findById(id).orElseThrow(() -> new NoSuchGiftException(id));

        return new GiftDTO(gift.getId(),
                gift.getName(),
                gift.getDescription(),
                gift.getPrice(),
                gift.getDuration(),
                gift.getTags());
    }

    @Override
    public Long deleteById(Long id) {
        log.info("Gift id = '{}' will be delete", id);
        giftRepository.delete(id);

        return id;
    }

    @Override
    public GiftCertificate update(Long id, GiftDTO giftDto) {
        log.info("Gift '{}' will be update", giftDto.getName());

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

    private boolean checkGiftName(GiftDTO giftDto) {

        return giftRepository.existsByName(giftDto.getName());
    }

    private List<Tag> checkNewTags(List<Tag> allTags, List<Tag> newTags) throws InvalidTagException {
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
