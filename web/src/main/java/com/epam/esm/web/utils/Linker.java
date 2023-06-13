package com.epam.esm.web.utils;

import com.epam.esm.model.dto.GiftDTO;
import com.epam.esm.model.exception.NoSuchGiftException;
import com.epam.esm.web.controller.GiftController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class Linker {
    public static List<GiftDTO> addLink (List<GiftDTO> list){

        return list.stream().map(giftDTO -> {
            try {
                return giftDTO.add(linkTo(methodOn(GiftController.class).getById(
                        giftDTO.getId())).withSelfRel());

            } catch (NoSuchGiftException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }
}
